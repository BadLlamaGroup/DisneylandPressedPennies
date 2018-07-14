package net.cox.mario_000.disneylandpressedpennies;

/**
 * Created by mario_000 on 1/21/2017.
 */

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

class CoinBookImageAdapter extends PagerAdapter {

    private static FragmentManager fragmentManager;


    Context context;
    List<Coin> c;
    LayoutInflater mLayoutInflater;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;


    CoinBookImageAdapter(Context context, List<Coin> c) {
        this.context = context;
        this.c = c;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Set animations
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip1);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip2);
    }

    @Override
    public int getCount() {
        return c.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //ImageView imageView = new ImageView(context);
        //int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        //imageView.setPadding(padding, padding, padding, padding);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        final Coin currentCoin = c.get(position);
        Machine currentMac = find(currentCoin);
        final int resId3 = context.getResources().getIdentifier(currentCoin.getCoinFrontImg(), "drawable", context.getPackageName());
        final int resId4;
        if(currentMac.getBackstampImg() == null){
            resId4 = context.getResources().getIdentifier(currentCoin.getCoinFrontImg() + "_backstamp", "drawable", context.getPackageName());
        }else {
            resId4 = context.getResources().getIdentifier(currentMac.getBackstampImg(), "drawable", context.getPackageName());
        }

        View itemView = mLayoutInflater.inflate(R.layout.big_image, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.editCoinFront);
        final ImageView imageView2 = (ImageView) itemView.findViewById(R.id.editCoinBack);

        // Display image
        img = new DisplayImage();
        img.loadBitmap(resId3, context.getResources(), 1200, 1200, imageView, 0);
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(context.getResources(), resId3, dimensions);
        int height = dimensions.outHeight;
        int width = dimensions.outWidth;

        //Set orientation based on coin direction
        if (width > height) {
            Bitmap mBitmap2 = BitmapFactory.decodeResource(context.getResources(), resId4);
            if (mBitmap2.getWidth() < mBitmap2.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap = Bitmap.createBitmap(mBitmap2, 0, 0, mBitmap2.getWidth(), mBitmap2.getHeight(), matrix, true);
                imageView2.setImageBitmap(bitmap);
            } else {
                img.loadBitmap(resId4, context.getResources(), 1200, 1200, imageView2, resId3);
            }

        } else {
            img.loadBitmap(resId4, context.getResources(), 1200, 1200, imageView2, resId3);
        }

        // Set camera distance
        // TODO: 1/28/2017 Figure out distance

        int distance = 4000;
        float scale = context.getResources().getDisplayMetrics().density * distance;
        imageView.setCameraDistance(scale);
        imageView2.setCameraDistance(scale);

//        imageView.setImageResource(resId3);
//        imageView2.setImageResource(resId4);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(mSetRightOut.isRunning() || mSetLeftIn.isRunning()) && resId3 != resId4) {
                    mSetRightOut.setTarget(imageView);
                    mSetLeftIn.setTarget(imageView2);
                    mSetRightOut.start();
                    mSetLeftIn.start();

                    imageView2.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setVisibility(View.INVISIBLE);///////
                        }
                    }, 1000);
                }

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(mSetRightOut.isRunning() || mSetLeftIn.isRunning()) && resId3 != resId4) {
                    mSetRightOut.setTarget(imageView2);
                    mSetLeftIn.setTarget(imageView);
                    mSetRightOut.start();
                    mSetLeftIn.start();

                    imageView.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView2.setVisibility(View.INVISIBLE);/////////

                        }
                    }, 1000);
                }
            }
        });

//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                fragmentManager = MainActivity.fragmentManager;
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                CoinBookImageOverlay fragment = new CoinBookImageOverlay();
//
//                Bundle args = new Bundle();
//
//                Gson gson = new Gson();
//                String jsonCoin = gson.toJson(currentCoin);
//                args.putString("pos", jsonCoin);
//                fragment.setArguments(args);
//                fragmentTransaction.setCustomAnimations(
//                        R.animator.fade_in,
//                        R.animator.fade_out,
//                        R.animator.fade_in,
//                        R.animator.fade_out);
//
//                fragmentTransaction.replace(R.id.mainFrag, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                return false;
//            }
//        });
//
//        imageView2.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                fragmentManager = MainActivity.fragmentManager;
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                CoinBookImageOverlay fragment = new CoinBookImageOverlay();
//
//                Bundle args = new Bundle();
//
//                Gson gson = new Gson();
//                String jsonCoin = gson.toJson(currentCoin);
//                args.putString("pos", jsonCoin);
//                fragment.setArguments(args);
//                fragmentTransaction.setCustomAnimations(
//                        R.animator.fade_in,
//                        R.animator.fade_out,
//                        R.animator.fade_in,
//                        R.animator.fade_out);
//
//                fragmentTransaction.replace(R.id.mainFrag, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                return false;
//            }
//        });


        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
