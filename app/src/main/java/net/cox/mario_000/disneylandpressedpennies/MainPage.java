package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    ImageView picturePreview;
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
        picturePreview = myFragmentView.findViewById(R.id.picturePreview);

        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStoragePermissionGranted();
                dispatchTakePictureIntent();
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

    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

                try {
                    ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap;
                    switch (orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(myBitmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(myBitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(myBitmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = myBitmap;
                    }

                    picturePreview.setImageBitmap(rotatedBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity().getApplicationContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST";
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/Pressed Coins at Disneyland/Coins");
        dir.mkdirs();

        File image = new File(dir, imageFileName + "_" + timeStamp + ".jpg");

        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
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