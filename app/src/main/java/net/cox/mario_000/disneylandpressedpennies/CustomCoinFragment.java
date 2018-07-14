package net.cox.mario_000.disneylandpressedpennies;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 7/1/2018.
 */

public class CustomCoinFragment extends Fragment {
    // Views
    private Tracker mTracker;
    private ImageView spinningCoinFront;
    private ImageView spinningCoinBack;
    private ImageView editCoinFront;
    private ImageView editCoinBack;
    private EditText editTitle;
    private TextView txtDateCollected;
    private EditText editNotes;
    private Button btnSave;
    private Button btnDelete;
    private ConstraintLayout editCoinDisplay;
    private ConstraintLayout spinningCoinDisplay;
    boolean storageApproved;
    private String frontImageName;
    private String backImageName;
    private String selectedSide;


    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener swipeListener;

    // Rotation animation
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private final int mInterval = 2000; // Duration of spin animation
    private Handler mHandler;
    private boolean mIsBackVisible = false;

    @Override
    public void onResume() {

        super.onResume();
        mTracker.setScreenName("Page - Custom Coin");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_coin_new_coin, container, false);
        getActivity().setTitle("Custom Coin");

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();

        spinningCoinFront = view.findViewById(R.id.spinningCoinFront);
        spinningCoinBack = view.findViewById(R.id.spinningCoinBack);
        editCoinFront = view.findViewById(R.id.editCoinFront);
        editCoinBack = view.findViewById(R.id.editCoinBack);
        editTitle = view.findViewById(R.id.edit_title);
        txtDateCollected = view.findViewById(R.id.txtDateCollected);
        editNotes = view.findViewById(R.id.txt_notes);
        btnSave = view.findViewById(R.id.btn_save_coin);
        btnDelete = view.findViewById(R.id.btn_delete_coin);
        editCoinDisplay = view.findViewById(R.id.editCoinDisplay);
        spinningCoinDisplay = view.findViewById(R.id.spinningCoinDisplay);

        // Start rotation of coin
        mHandler = new Handler();



        int resId = view.getContext().getResources().getIdentifier("bazaar_1_jungle_boat", "drawable", view.getContext().getPackageName());
        int resId2 = view.getContext().getResources().getIdentifier("bazaar_2_backstamp", "drawable", view.getContext().getPackageName());

        img.loadBitmap(resId, getResources(), 800, 800, spinningCoinFront, 0);
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(getActivity().getResources(), resId, dimensions);
        int height = dimensions.outHeight;
        int width = dimensions.outWidth;

        //Set orientation based on coin direction
        if (width > height) {
            Bitmap mBitmap2 = BitmapFactory.decodeResource(getActivity().getResources(), resId2);
            if (mBitmap2 == null) {
                int resId3 = view.getContext().getResources().getIdentifier("new_searching", "drawable", view.getContext().getPackageName());
                mBitmap2 = BitmapFactory.decodeResource(getActivity().getResources(), resId3);
            }
            if (mBitmap2.getWidth() < mBitmap2.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap = Bitmap.createBitmap(mBitmap2, 0, 0, mBitmap2.getWidth(), mBitmap2.getHeight(), matrix, true);
                spinningCoinBack.setImageBitmap(bitmap);
            } else {
                img.loadBitmap(resId2, getResources(), 800, 800, spinningCoinBack, resId);
            }

        } else {
            img.loadBitmap(resId2, getResources(), 800, 800, spinningCoinBack, resId);
        }


        // Set animations
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip1);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip2);

        // Set camera distance
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        spinningCoinFront.setCameraDistance(scale);
        spinningCoinBack.setCameraDistance(scale);


        // Gesture detection
        gestureDetector = new GestureDetector(getActivity(), new MyGestureDetector());
        swipeListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if((!gestureDetector.onTouchEvent(event) && event.getAction() == MotionEvent.ACTION_UP) && (v instanceof Button || v instanceof ImageView)){
                    switch (v.getId()){
                        case R.id.editCoinFront:
                            selectedSide = "Front";
                            takePicture();
                            break;
                        case R.id.editCoinBack:
                            selectedSide = "Back";
                            takePicture();
                            break;
                        case R.id.spinningCoinBack:
                            Toast.makeText(getActivity(), "spinningCoinBack", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                //return gestureDetector.onTouchEvent(event);
                return true;
            }
        };

// Set listeners for coin


        editCoinDisplay.setOnTouchListener(swipeListener);
        spinningCoinDisplay.setOnTouchListener(swipeListener);
        spinningCoinFront.setOnTouchListener(swipeListener);
        spinningCoinBack.setOnTouchListener(swipeListener);
        editCoinFront.setOnTouchListener(swipeListener);
        editCoinBack.setOnTouchListener(swipeListener);

        rotate();


        //Link views and set on click listener


        return view;
    }

    private final Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                flipCoin();
            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    private void rotate() {
        mStatusChecker.run();
    }

    private void flipCoin() {
        spinningCoinBack.setVisibility(View.VISIBLE);
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(spinningCoinFront);
            mSetLeftIn.setTarget(spinningCoinBack);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(spinningCoinBack);
            mSetLeftIn.setTarget(spinningCoinFront);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    public void takePicture() {
        isStoragePermissionGranted();
        //dispatchTakePictureIntent();

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(290,520)
                .setMinCropResultSize(290,520)
                .setAspectRatio(29,52)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAutoZoomEnabled(true)
                .start(getActivity(), this);
    }

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    Bitmap newBitmap = CropImage.toOvalBitmap(bitmap);
                    Bitmap b = Bitmap.createBitmap(400, 600, Bitmap.Config.ARGB_8888);

                    Bitmap bmOverlay = Bitmap.createBitmap(b.getWidth(), b.getHeight(), b.getConfig());
                    Canvas canvas = new Canvas(bmOverlay);
                    canvas.drawBitmap(b, new Matrix(), null);
                    canvas.drawBitmap(newBitmap, (b.getWidth() - newBitmap.getWidth()) / 2, (b.getHeight() - newBitmap.getHeight()) / 2, null);

                    SaveImage(bmOverlay);


//                    Coin[] b = new Coin[]{
//                            new Coin("Minnie & Mickey Happy Holidays 2017", "penny_arcade_3_mickey_2017", null)};
//                    Machine a = new Machine("Main Street", "Peter Pan", "Quarter", null, "image", null, b, null);
//                    List<Coin> c = Arrays.asList(a.getCoins());
//                    Coin d = new Coin("Minnie & Mickey Happy Holidays 2017", "penny_arcade_3_mickey_2017", null);
//                    c.add(d);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File dir = new File(root + "/Pressed Coins at Disneyland/Coins");
        dir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".png";
        File file = new File (dir, fname);
        if(selectedSide.equals("Front")){
            frontImageName = fname;
        } else {
            backImageName = fname;
        }
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            if(selectedSide.equals("Front")){
                editCoinFront.setImageBitmap(finalBitmap);
                spinningCoinFront.setImageBitmap(finalBitmap);
            } else {
                editCoinBack.setImageBitmap(finalBitmap);
                spinningCoinBack.setImageBitmap(finalBitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                FragmentCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            } else {
                storageApproved = true;
            }
        } else {
            storageApproved = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            storageApproved = true;
        } else {
            getFragmentManager().popBackStackImmediate();
        }
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {

                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(getActivity(), "Left Swipe", Toast.LENGTH_SHORT).show();
                    swipeLeft();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(getActivity(), "Right Swipe", Toast.LENGTH_SHORT).show();
                    swipeRight();
                    return true;
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        private void swipeRight() {
            if (spinningCoinDisplay.getVisibility() == View.VISIBLE) {
                slideToRight(spinningCoinDisplay);
                slideToRightFromEdge(editCoinDisplay);
                mHandler.removeCallbacks(mStatusChecker);
            } else {
                slideToRight(editCoinDisplay);
                slideToRightFromEdge(spinningCoinDisplay);
                rotate();
            }
        }
        private void swipeLeft(){
            if(spinningCoinDisplay.getVisibility() == View.VISIBLE){
                    slideToLeft(spinningCoinDisplay);
                    slideToLeftFromEdge(editCoinDisplay);
                    mHandler.removeCallbacks(mStatusChecker);
                }
                else
                {
                    slideToLeft(editCoinDisplay);
                    slideToLeftFromEdge(spinningCoinDisplay);
                    rotate();
                }
        }
    }

    public void slideToRight(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }

    public void slideToRightFromEdge(View view) {
        TranslateAnimation animate = new TranslateAnimation(-view.getWidth(), 0, 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    // To animate view slide out from right to left
    public void slideToLeft(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }

    public void slideToLeftFromEdge(View view) {
        TranslateAnimation animate = new TranslateAnimation(view.getWidth(), 0, 0, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

}
