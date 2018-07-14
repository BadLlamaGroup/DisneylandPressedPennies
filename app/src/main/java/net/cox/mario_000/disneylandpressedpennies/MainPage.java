package net.cox.mario_000.disneylandpressedpennies;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.provider.MediaStore;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.confetto.BitmapConfetto;
import com.github.jinatonic.confetti.confetto.Confetto;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numArcCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCurrentCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCurrentCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsTotal;


/**
 * Created by mario_000 on 7/11/2016.
 * Description: Main page for app
 */
public class MainPage extends Fragment implements View.OnClickListener {
    TextView total;
    TextView disneyCollected;
    TextView calCollected;
    TextView downtownCollected;
    List<Coin> savedCoins;
    SharedPreference sharedPreference;
    int numClicked;
    MainActivity application;
    private Tracker mTracker;
    Button pictureBtn;
    ImageView newImage;
    boolean storageApproved;


    @Override
    public void onResume() {
        super.onResume();
        savedCoins = sharedPreference.getCoins(getActivity().getApplicationContext());
        numCurrentCoinsCollected = savedCoins.size() - numArcCoinsCollected;
        total.setText(numCurrentCoinsCollected + " / " + numCurrentCoinsTotal);
        disneyCollected.setText(numDisneyCoinsCollected + " / " + numDisneyCoinsTotal);
        calCollected.setText(numCalCoinsCollected + " / " + numCalCoinsTotal);
        downtownCollected.setText(numDowntownCoinsCollected + " / " + numDowntownCoinsTotal);
    }

    @TargetApi(23)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.main_page, container, false);
        getActivity().setTitle("Main Page");

        //Link buttons and set on click listener
        total = (TextView) myFragmentView.findViewById(R.id.txt_total_collected);
        disneyCollected = (TextView) myFragmentView.findViewById(R.id.txt_disney_collected);
        calCollected = (TextView) myFragmentView.findViewById(R.id.txt_cal_collected);
        downtownCollected = (TextView) myFragmentView.findViewById(R.id.txt_downtown_collected);


        pictureBtn = myFragmentView.findViewById(R.id.picture);
        //picturePreview = myFragmentView.findViewById(R.id.cropImageView);
        newImage = myFragmentView.findViewById(R.id.newImage);

        //pictureBtn.setOnClickListener(this);

        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStoragePermissionGranted();
                //dispatchTakePictureIntent();

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setRequestedSize(290,520)
                        .setAspectRatio(29,52)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setAutoZoomEnabled(true)
                        .start(getContext(), MainPage.this);
            }
        });

        LinearLayout disneyLayout = (LinearLayout) myFragmentView.findViewById(R.id.disneyMain);
        disneyLayout.setOnClickListener(this);

        LinearLayout californiaLayout = (LinearLayout) myFragmentView.findViewById(R.id.californiaMain);
        californiaLayout.setOnClickListener(this);

        LinearLayout downtownLayout = (LinearLayout) myFragmentView.findViewById(R.id.downtownMain);
        downtownLayout.setOnClickListener(this);

        sharedPreference = new SharedPreference();
        savedCoins = sharedPreference.getCoins(getActivity().getApplicationContext());
        numCurrentCoinsCollected = savedCoins.size() - numArcCoinsCollected;

        total.setText(numCurrentCoinsCollected + " / " + numCurrentCoinsTotal);
        disneyCollected.setText(numDisneyCoinsCollected + " / " + numDisneyCoinsTotal);
        calCollected.setText(numCalCoinsCollected + " / " + numCalCoinsTotal);
        downtownCollected.setText(numDowntownCoinsCollected + " / " + numDowntownCoinsTotal);


        View v = myFragmentView.findViewById(R.id.txtName);
        numClicked = 0;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numClicked == 2) {
                    application = (MainActivity) getActivity();
                    mTracker = application.getDefaultTracker();
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Hidden Mickeys")
                            .setAction("Activated")
                            .setValue(1)
                            .build());
                    Toast.makeText(getActivity().getApplicationContext(), "HIDDEN MICKEYS!!!", Toast.LENGTH_SHORT).show();
                    final ConfettoGenerator confettoGenerator = new ConfettoGenerator() {
                        @Override
                        public Confetto generateConfetto(Random random) {
                            final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mickey);
                            return new BitmapConfetto(bitmap);
                        }
                    };
                    final ConfettiSource confettiSource = new ConfettiSource(0, -200, container.getWidth(), -200);
                    new ConfettiManager(getActivity().getApplicationContext(), confettoGenerator, confettiSource, container)
                            .setEmissionDuration(6000)
                            .setEmissionRate(5)
                            .setVelocityX(0, 50)
                            .setVelocityY(500)
                            .setRotationalVelocity(180, 180)
                            .animate();
                    numClicked = 0;
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Only " + (2 - numClicked) + " more click(s)!", Toast.LENGTH_SHORT).show();
                    numClicked++;

                }

            }
        });


        return myFragmentView;
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int land = v.getId();
        Fragment fragment = new Fragment();
        switch (land) {
            case (R.id.disneyMain):
                fragment = new DisneyPage();
                break;
            case (R.id.californiaMain):
                fragment = new CaliforniaPage();
                break;
            case (R.id.downtownMain):
                fragment = new DowntownPage();
                break;
            case (R.id.picture):
                fragment = new CustomCoinFragment();
                break;
        }
        fragmentTransaction.setCustomAnimations(
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out);
        fragmentTransaction.replace(R.id.mainFrag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

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
}