package net.cox.mario_000.disneylandpressedpennies;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mario_000 on 7/1/2018.
 */

public class CustomCoinFragment extends Fragment
{
    // Images
    private ImageView spinningCoinFront;
    private ImageView spinningCoinBack;
    private ImageView editCoinFront;
    private ImageView editCoinBack;
    private String frontImageName;
    private String backImageName;
    private String selectedSide;

    // EditTexts
    private EditText editTitle;
    private EditText editNotes;

    // Calendar
    private TextView txtDateCollected;
    private SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar myCalendar;

    // Buttons
    private Button btnSave;
    private Button btnDelete;

    // Spinners
    private Spinner coinTypeSpinner;
    private Spinner parkSpinner;

    // Coins
    private Coin newCustomCoin;
    private Coin savedCoin;

    // References
    private final SharedPreference sharedPreference = new SharedPreference();
    private Context context;
    private Tracker mTracker;
    private ScrollView customCoinScrollview;

    // Swipe
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    private View.OnTouchListener swipeListener;
    private ConstraintLayout editCoinDisplay;
    private ConstraintLayout spinningCoinDisplay;
    private TextView slideHeader;

    // Rotation animation
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private final int mInterval = 2000; // Duration of spin animation
    private Handler mHandler;
    private boolean mIsBackVisible = false;


    @Override
    public void onResume()
    {
        super.onResume();
        mTracker.setScreenName( "Page - Custom Coin" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.custom_coin_new_coin, container, false );
        getActivity().setTitle( "Custom Coin" );
        context = view.getContext();

        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();

        isStoragePermissionGranted();

        // Link views
        spinningCoinFront = view.findViewById( R.id.spinningCoinFront );
        spinningCoinBack = view.findViewById( R.id.spinningCoinBack );
        editCoinFront = view.findViewById( R.id.editCoinFront );
        editCoinBack = view.findViewById( R.id.editCoinBack );
        editTitle = view.findViewById( R.id.edit_title );
        txtDateCollected = view.findViewById( R.id.txtDateCollected );
        editNotes = view.findViewById( R.id.txt_notes );
        btnSave = view.findViewById( R.id.btn_save_coin );
        btnDelete = view.findViewById( R.id.btn_delete_coin );
        editCoinDisplay = view.findViewById( R.id.editCoinDisplay );
        spinningCoinDisplay = view.findViewById( R.id.spinningCoinDisplay );
        coinTypeSpinner = view.findViewById( R.id.type_picker );
        parkSpinner = view.findViewById( R.id.park_picker );
        customCoinScrollview = view.findViewById( R.id.custom_coin_fields );
        slideHeader = view.findViewById( R.id.slideHeader );

        InputFilter filter = new InputFilter()
        {
            public CharSequence filter( CharSequence source, int start, int end,
                                        Spanned dest, int dstart, int dend
            )
            {
                for ( int i = start; i < end; i++ )
                {
                    if ( !Character.isLetterOrDigit( source.charAt( i ) ) && !Character.isSpaceChar( source.charAt( i ) ) )
                    {
                        return "";
                    }
                }
                return null;
            }
        };

        editTitle.setFilters( new InputFilter[]{ filter } );

        // Listeners

        editTitle.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                editTitle.setTextColor( Color.BLACK );
            }
        } );

        btnSave.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if ( checkCoinInfo() )
                {
                    saveCoin();
                    // Remove old images
                    removeOldCoinImages();
                }
            }
        } );

        btnDelete.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if ( savedCoin != null )
                {
                    removeSavedCoinDialog();
                } else
                {
                    cancelDialog();
                }
            }
        } );

        // Handle back press action when action mode is on.
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
                    cancelDialog();
                }
                return true;
            }
        });

        txtDateCollected.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                new DatePickerDialog( getActivity(), onDateSetListener, myCalendar
                        .get( Calendar.YEAR ), myCalendar.get( Calendar.MONTH ),
                        myCalendar.get( Calendar.DAY_OF_MONTH ) ).show();
            }
        } );

        onDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth )
            {
                myCalendar.set( Calendar.YEAR, year );
                myCalendar.set( Calendar.MONTH, monthOfYear );
                myCalendar.set( Calendar.DAY_OF_MONTH, dayOfMonth );
                txtDateCollected.setText( dateFormat.format( myCalendar.getTime() ) );
            }

        };

        // Gesture detection
        gestureDetector = new GestureDetector( getActivity(), new MyGestureDetector() );

        swipeListener = new View.OnTouchListener()
        {
            @Override
            public boolean onTouch( View v, MotionEvent event )
            {
                if ( ( !gestureDetector.onTouchEvent( event ) && event.getAction() == MotionEvent.ACTION_UP ) && ( v instanceof Button || v instanceof ImageView ) )
                {
                    switch ( v.getId() )
                    {
                        case R.id.editCoinFront:
                            selectedSide = "Front";
                            takePicture();
                            break;
                        case R.id.editCoinBack:
                            selectedSide = "Back";
                            takePicture();
                            break;
                        case R.id.spinningCoinBack:
                            Intent bigImageIntent = new Intent( context, BigImage.class );
                            bigImageIntent.putExtra( "frontImg", frontImageName );
                            bigImageIntent.putExtra( "backImg", backImageName );
                            bigImageIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                            context.startActivity( bigImageIntent );
                            break;
                    }
                }
                return true;
            }
        };

        // Set listeners for coin
        editCoinDisplay.setOnTouchListener( swipeListener );
        spinningCoinDisplay.setOnTouchListener( swipeListener );
        spinningCoinFront.setOnTouchListener( swipeListener );
        spinningCoinBack.setOnTouchListener( swipeListener );
        editCoinFront.setOnTouchListener( swipeListener );
        editCoinBack.setOnTouchListener( swipeListener );

        // Get calendar
        myCalendar = Calendar.getInstance();

        // Start rotation of coin
        mHandler = new Handler();

        // Set animations
        mSetRightOut = ( AnimatorSet ) AnimatorInflater.loadAnimator( view.getContext(), R.animator.flip1 );
        mSetLeftIn = ( AnimatorSet ) AnimatorInflater.loadAnimator( view.getContext(), R.animator.flip2 );

        // Set camera distance
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        spinningCoinFront.setCameraDistance( scale );
        spinningCoinBack.setCameraDistance( scale );

        // Set adapters
        final ArrayAdapter< CharSequence > coinTypeAdapter = ArrayAdapter.createFromResource( getActivity(), R.array.Coin_Type, R.layout.spinner_item );
        coinTypeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        coinTypeSpinner.setAdapter( coinTypeAdapter );

        final ArrayAdapter< CharSequence > parkAdapter = ArrayAdapter.createFromResource( getActivity(), R.array.Park, R.layout.spinner_item );
        parkAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        parkSpinner.setAdapter( parkAdapter );

        // Set coin info
        Bundle extras = getArguments();
        if ( extras != null )
        {
            Gson gson = new Gson();
            String jsonCoin = extras.getString( "selectedCoin" );
            savedCoin = gson.fromJson( jsonCoin, Coin.class );

            // Display coin name in nav bar
            getActivity().setTitle( savedCoin.getTitleCoin() );

            // Set data
            editTitle.setText( savedCoin.getTitleCoin() );
            editNotes.setText( savedCoin.getNotes() );
            myCalendar.setTime( savedCoin.getDateCollected() );
            txtDateCollected.setText( dateFormat.format( savedCoin.getDateCollected() ) );

            // Set spinners
            int coinPos = coinTypeAdapter.getPosition( savedCoin.getCoinType() );
            coinTypeSpinner.setSelection( coinPos );
            int parkPos = parkAdapter.getPosition( savedCoin.getCoinPark() );
            parkSpinner.setSelection( parkPos );

            // Set filepath
            String root = Environment.getExternalStorageDirectory().toString();
            File dir = new File( root + "/Pressed Coins at Disneyland/Coins" );

            // Set images
            Uri frontImage = Uri.fromFile( new File( dir + "/" + savedCoin.getCoinFrontImg() ) );
            Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( spinningCoinFront );
            Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( editCoinFront );
            frontImageName = savedCoin.getCoinFrontImg();

            Uri backImage = Uri.fromFile( new File( dir + "/" + savedCoin.getCoinBackImg() ) );
            Picasso.get().load( backImage ).error( R.drawable.new_penny_back ).fit().into( spinningCoinBack );
            Picasso.get().load( backImage ).error( R.drawable.new_penny_back ).fit().into( editCoinBack );
            backImageName = savedCoin.getCoinBackImg();

            // Set buttons
            btnSave.setText( "Update" );
            btnDelete.setText( "Delete" );

            // Begin rotation
            Handler handler = new Handler();
            handler.postDelayed( new Runnable()
            {
                public void run()
                {
                    rotate();
                }
            }, 500 );

            slideHeader.setText( "Swipe to edit..." );

        } else
        {
            // Load default images
            int resId = view.getContext().getResources().getIdentifier( "new_penny", "drawable", view.getContext().getPackageName() );
            int resId2 = view.getContext().getResources().getIdentifier( "new_penny_back", "drawable", view.getContext().getPackageName() );

            Picasso.get().load( resId ).resize( 200, 300 ).into( spinningCoinFront );
            Picasso.get().load( resId ).resize( 200, 300 ).into( editCoinFront );
            Picasso.get().load( resId2 ).resize( 200, 300 ).into( spinningCoinBack );
            Picasso.get().load( resId2 ).resize( 200, 300 ).into( editCoinBack );

            frontImageName = "new_penny";
            backImageName = "new_penny_back";

            // Set button text
            btnSave.setText( "Save" );
            btnDelete.setText( "Cancel" );

            // Set date to current date
            txtDateCollected.setText( dateFormat.format( myCalendar.getTime() ) );

            // Set view to edit
            spinningCoinDisplay.setVisibility( View.INVISIBLE );
            editCoinDisplay.setVisibility( View.VISIBLE );
            slideHeader.setText( "Swipe to show animation..." );
        }

        return view;
    }

    public void removeSavedCoinDialog()
    {
        final Dialog removeDialog = new Dialog( context, R.style.CustomDialog );
        // Set up dialog window
        removeDialog.setContentView( R.layout.coin_remove_dialog );
        removeDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        removeDialog.setCancelable( false );
        TextView coinName = removeDialog.findViewById( R.id.txt_coin );
        coinName.setText( savedCoin.getTitleCoin() );
        // Link buttons
        Button noBtn = removeDialog.findViewById( R.id.btn_no );
        Button overwriteBtn = removeDialog.findViewById( R.id.btn_yes );

        // Listeners for buttons
        noBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                removeDialog.dismiss();
            }
        } );

        overwriteBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if ( nameExists( savedCoin.getTitleCoin() ) )
                {
                    sharedPreference.removeCustomCoin( getActivity(), savedCoin );
                    deleteCoinImages();
                    getFragmentManager().popBackStackImmediate();
                }

                /*mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Coin Removed")
                        .setAction("Removed")
                        .setLabel(coin.getTitleCoin())
                        .setValue(1)
                        .build());*/
                removeDialog.dismiss();
            }
        } );

        removeDialog.show();
    }

    public void cancelDialog()
    {
        final Dialog removeDialog = new Dialog( context, R.style.CustomDialog );
        // Set up dialog window
        removeDialog.setContentView( R.layout.coin_remove_dialog );
        removeDialog.setTitle( "Title..." );
        removeDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        removeDialog.setCancelable( false );
        TextView description = removeDialog.findViewById( R.id.txt_dia );
        TextView warningMsg = removeDialog.findViewById( R.id.txt_coin );
        description.setText( "Are you sure you wish to cancel?" );
        warningMsg.setText( "You will lose all changes for this coin." );
        // Link buttons
        Button noBtn = removeDialog.findViewById( R.id.btn_no );
        Button overwriteBtn = removeDialog.findViewById( R.id.btn_yes );

        // Listeners for buttons
        noBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                removeDialog.dismiss();
            }
        } );

        overwriteBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                // Remove old images
                removeOldCoinImages();
                getFragmentManager().popBackStackImmediate();
                /*mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Coin Removed")
                        .setAction("Removed")
                        .setLabel(coin.getTitleCoin())
                        .setValue(1)
                        .build());*/
                removeDialog.dismiss();
            }
        } );

        removeDialog.show();
    }

    private void removeOldCoinImages()
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File dir = new File( root + "/Pressed Coins at Disneyland/Coins" );
        final File[] files = dir.listFiles( new FilenameFilter()
        {
            @Override
            public boolean accept( final File dir,
                                   final String name
            )
            {
                return name.matches( "Image.*\\.png" );
            }
        } );
        for ( final File file : files )
        {
            if ( !file.delete() )
            {
                System.err.println( "Can't remove " + file.getAbsolutePath() );
            }
        }
    }

    private boolean checkCoinInfo()
    {
        if ( frontImageName == null )
        {
            Toast.makeText( getActivity(), "Please select an image for the front...", Toast.LENGTH_SHORT ).show();
            return false;
        } else if ( backImageName == null )
        {
            Toast.makeText( getActivity(), "Please select an image for the back...", Toast.LENGTH_SHORT ).show();
            return false;
        } else if ( editTitle.getText().toString().equals( "" ) )
        {
            Toast.makeText( getActivity(), "Please enter a coin name...", Toast.LENGTH_SHORT ).show();
            return false;
        } else if ( nameExists( editTitle.getText().toString() ) && savedCoin == null )
        {
            Toast.makeText( getActivity(), "Coin already exists...", Toast.LENGTH_SHORT ).show();
            editTitle.setTextColor( getResources().getColor( R.color.colorRed ) );
            customCoinScrollview.fullScroll( ScrollView.FOCUS_UP );
            return false;
        }
        return true;
    }

    // Check if coin is in collected coins
    private boolean nameExists( String checkName )
    {
        boolean check = false;
        // Get collected coins list
        List< Coin > coins = sharedPreference.getCustomCoins( getActivity().getApplicationContext() );
        if ( coins != null )
        {
            for ( Coin coin : coins )
            {
                // Check if coin matches coin already collected
                if ( String.valueOf( coin.getTitleCoin() ).equals( checkName ) )
                {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    private void renameImages()
    {
        try
        {
            String root = Environment.getExternalStorageDirectory().toString();
            File dir = new File( root + "/Pressed Coins at Disneyland/Coins/" );
            File frontFrom = new File( dir, frontImageName );
            String newFrontImage = editTitle.getText().toString().trim().replaceAll( " ", "_" ).toLowerCase();
            File frontTo = new File( dir, newFrontImage + "_front.png" );
            if ( frontFrom.renameTo( frontTo ) )
            {
                frontImageName = frontTo.getName();
            }

            File backFrom = new File( dir, backImageName );
            String newBackImage = editTitle.getText().toString().trim().replaceAll( " ", "_" ).toLowerCase();
            File backTo = new File( dir, newBackImage + "_back.png" );
            if ( backFrom.renameTo( backTo ) )
            {
                backImageName = backTo.getName();
            }
        } catch ( Exception e )
        {
            e.printStackTrace();
            Toast.makeText( context, "Error renaming images", Toast.LENGTH_SHORT ).show();
            getFragmentManager().popBackStackImmediate();
        }
    }

    private void deleteCoinImages()
    {
        try
        {
            String root = Environment.getExternalStorageDirectory().toString();
            File dir = new File( root + "/Pressed Coins at Disneyland/Coins/" );
            File frontImage = new File( dir, frontImageName );
            frontImage.delete();

            File backImage = new File( dir, backImageName );
            backImage.delete();
        } catch ( Exception e )
        {
            e.printStackTrace();
            Toast.makeText( context, "Error deleting images", Toast.LENGTH_SHORT ).show();
            getFragmentManager().popBackStackImmediate();
        }
    }

    private void saveCoin()
    {

        String newTitle = editTitle.getText().toString();
        String newNotes = editNotes.getText().toString();
        String coinType = coinTypeSpinner.getSelectedItem().toString();
        String parkSelected = parkSpinner.getSelectedItem().toString();

        if ( savedCoin != null )
        {
            // UPDATING COIN
            if ( savedCoin.getTitleCoin().equals( newTitle ) || !nameExists( newTitle ) )
            {
                sharedPreference.removeCustomCoin( getActivity(), savedCoin );
                renameImages();
                savedCoin = new Coin( newTitle, frontImageName, backImageName, coinType, parkSelected, newNotes, myCalendar.getTime() );
                sharedPreference.addCustomCoin( getActivity(), savedCoin );
                getFragmentManager().popBackStackImmediate();
            } else
            {
                Toast.makeText( getActivity(), "Coin already exists...", Toast.LENGTH_SHORT ).show();
                editTitle.setTextColor( getResources().getColor( R.color.colorRed ) );
                customCoinScrollview.fullScroll( ScrollView.FOCUS_UP );
            }
        } else
        {
            // NEW COIN
            renameImages();
            newCustomCoin = new Coin( newTitle, frontImageName, backImageName, coinType, parkSelected, newNotes, myCalendar.getTime() );
            if ( !nameExists( newTitle ) )
            {
                sharedPreference.addCustomCoin( getActivity(), newCustomCoin );
                getFragmentManager().popBackStackImmediate();
            }
        }
    }

    private final Runnable mStatusChecker = new Runnable()
    {
        @Override
        public void run()
        {
            try
            {
                flipCoin();
            } finally
            {
                mHandler.postDelayed( mStatusChecker, mInterval );
            }
        }
    };

    private void rotate()
    {
        mStatusChecker.run();
    }

    private void flipCoin()
    {
        spinningCoinBack.setVisibility( View.VISIBLE );
        if ( !mIsBackVisible )
        {
            mSetRightOut.setTarget( spinningCoinFront );
            mSetLeftIn.setTarget( spinningCoinBack );
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else
        {
            mSetRightOut.setTarget( spinningCoinBack );
            mSetLeftIn.setTarget( spinningCoinFront );
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    public void takePicture()
    {
        CropImage.activity()
                .setGuidelines( CropImageView.Guidelines.ON )
                .setRequestedSize( 290, 520 )
                .setMinCropResultSize( 290, 520 )
                .setAspectRatio( 29, 52 )
                .setCropShape( CropImageView.CropShape.OVAL )
                .setAutoZoomEnabled( true )
                .start( getActivity(), this );
    }

    public void onActivityResult( int requestCode,
                                  int resultCode, Intent data
    )
    {
        if ( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE )
        {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if ( resultCode == RESULT_OK )
            {
                Uri resultUri = result.getUri();
                try
                {
                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), resultUri );
                    Bitmap newBitmap = CropImage.toOvalBitmap( originalBitmap );
                    Bitmap holderBitmap = Bitmap.createBitmap( 400, 600, Bitmap.Config.ARGB_8888 );

                    Bitmap finishedBitmap = Bitmap.createBitmap( holderBitmap.getWidth(), holderBitmap.getHeight(), holderBitmap.getConfig() );
                    Canvas canvas = new Canvas( finishedBitmap );
                    canvas.drawBitmap( holderBitmap, new Matrix(), null );
                    canvas.drawBitmap( newBitmap, ( holderBitmap.getWidth() - newBitmap.getWidth() ) / 2, ( holderBitmap.getHeight() - newBitmap.getHeight() ) / 2, null );

                    SaveImage( finishedBitmap );

                } catch ( IOException e )
                {
                    e.printStackTrace();
                }
            } else if ( resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE )
            {
                Exception error = result.getError();
            }
        }
    }

    private void SaveImage( Bitmap finalBitmap )
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File dir = new File( root + "/Pressed Coins at Disneyland/Coins" );
        dir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt( n );
        String fname = "Image-" + n + ".png";
        File file = new File( dir, fname );
        if ( selectedSide.equals( "Front" ) )
        {
            frontImageName = fname;
        } else
        {
            backImageName = fname;
        }
        if ( file.exists() ) file.delete();
        try
        {
            FileOutputStream out = new FileOutputStream( file );
            finalBitmap.compress( Bitmap.CompressFormat.PNG, 100, out );
            out.flush();
            out.close();
            if ( selectedSide.equals( "Front" ) )
            {
                editCoinFront.setImageBitmap( finalBitmap );
                spinningCoinFront.setImageBitmap( finalBitmap );
            } else
            {
                editCoinBack.setImageBitmap( finalBitmap );
                spinningCoinBack.setImageBitmap( finalBitmap );
            }

        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void isStoragePermissionGranted()
    {
        if ( Build.VERSION.SDK_INT >= 23 )
        {
            if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED )
            {
                FragmentCompat.requestPermissions( this, new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1 );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults )
    {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if ( grantResults[ 0 ] != PackageManager.PERMISSION_GRANTED )
        {
            getFragmentManager().popBackStackImmediate();
        }
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY )
        {
            try
            {

                // right to left swipe
                if ( e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs( velocityX ) > SWIPE_THRESHOLD_VELOCITY )
                {
                    swipeLeft();
                    return true;
                } else if ( e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs( velocityX ) > SWIPE_THRESHOLD_VELOCITY )
                {
                    swipeRight();
                    return true;
                }
            } catch ( Exception e )
            {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown( MotionEvent e )
        {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed( MotionEvent e )
        {
            return true;
        }

        private void swipeRight()
        {
            if ( spinningCoinDisplay.getVisibility() == View.VISIBLE )
            {
                slideToRight( spinningCoinDisplay );
                slideToRightFromEdge( editCoinDisplay );
                mHandler.removeCallbacks( mStatusChecker );
                slideHeader.setText( "Swipe to show animation..." );
            } else
            {
                slideToRight( editCoinDisplay );
                slideToRightFromEdge( spinningCoinDisplay );
                Handler handler = new Handler();
                handler.postDelayed( new Runnable()
                {
                    public void run()
                    {
                        rotate();
                    }
                }, 500 );
                slideHeader.setText( "Swipe to edit..." );
            }
        }

        private void swipeLeft()
        {
            if ( spinningCoinDisplay.getVisibility() == View.VISIBLE )
            {
                slideToLeft( spinningCoinDisplay );
                slideToLeftFromEdge( editCoinDisplay );
                mHandler.removeCallbacks( mStatusChecker );
                slideHeader.setText( "Swipe to show animation..." );
            } else
            {
                slideToLeft( editCoinDisplay );
                slideToLeftFromEdge( spinningCoinDisplay );
                Handler handler = new Handler();
                handler.postDelayed( new Runnable()
                {
                    public void run()
                    {
                        rotate();
                    }
                }, 500 );
                slideHeader.setText( "Swipe to edit..." );
            }
        }
    }

    public void slideToRight( View view )
    {
        TranslateAnimation animate = new TranslateAnimation( 0, view.getWidth(), 0, 0 );
        animate.setDuration( 500 );
        animate.setFillAfter( false );
        view.startAnimation( animate );
        view.setVisibility( View.INVISIBLE );
    }

    public void slideToRightFromEdge( View view )
    {
        TranslateAnimation animate = new TranslateAnimation( -view.getWidth(), 0, 0, 0 );
        animate.setDuration( 500 );
        animate.setFillAfter( true );
        view.startAnimation( animate );
        view.setVisibility( View.VISIBLE );
    }

    public void slideToLeft( View view )
    {
        TranslateAnimation animate = new TranslateAnimation( 0, -view.getWidth(), 0, 0 );
        animate.setDuration( 500 );
        animate.setFillAfter( false );
        view.startAnimation( animate );
        view.setVisibility( View.INVISIBLE );
    }

    public void slideToLeftFromEdge( View view )
    {
        TranslateAnimation animate = new TranslateAnimation( view.getWidth(), 0, 0, 0 );
        animate.setDuration( 500 );
        animate.setFillAfter( true );
        view.startAnimation( animate );
        view.setVisibility( View.VISIBLE );
    }

}