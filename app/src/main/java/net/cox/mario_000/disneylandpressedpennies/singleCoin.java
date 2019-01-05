package net.cox.mario_000.disneylandpressedpennies;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCoins;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Fragment for displaying information for single coin
 */
public class singleCoin extends Fragment implements Data, View.OnClickListener
{
    // Views
    private TextView txtDate;
    private TextView txtMac;
    private CheckBox haveCoinBox;
    private CheckBox wantItBox;
    private ImageView coinFront;
    private ImageView coinBack;
    private Calendar myCalendar;
    private TextView titleCoin;
    private TextView machineName;

    // Animation
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private final int mInterval = 2000; // Duration of spin animation
    private Handler mHandler;

    // Data
    private Coin coin;
    private Machine machine;
    private List< Coin > savedCoins;

    // References
    private final SharedPreference sharedPreference = new SharedPreference();
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );
    private Tracker mTracker;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    // Swipe
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener swipeListener;
    private ScrollView scrollView;

    public void showNoticeDialog()
    {
        final Dialog removeDialog = new Dialog( context, R.style.CustomDialog );
        // Set up dialog window
        removeDialog.setContentView( R.layout.coin_remove_dialog );
        removeDialog.setTitle( "Title..." );
        removeDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        removeDialog.setCancelable( false );
        TextView coinName = removeDialog.findViewById( R.id.txt_coin );
        coinName.setText( coin.getTitleCoin() );

        // Link buttons
        Button noBtn = removeDialog.findViewById( R.id.btn_no );
        Button removeBtn = removeDialog.findViewById( R.id.btn_yes );

        // Listeners for buttons
        noBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                haveCoinBox.setChecked( true );
                removeDialog.dismiss();
            }
        } );

        removeBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                haveCoinBox.setChecked( false );
                wantItBox.setEnabled( true );
                txtDate.setText( "Not yet collected" );
                sharedPreference.removeCoin( getActivity().getApplicationContext(), coin );
                MainActivity.updateCoinTotals();
                mTracker.send( new HitBuilders.EventBuilder()
                        .setCategory( "Coin Removed" )
                        .setAction( "Removed" )
                        .setLabel( coin.getTitleCoin() )
                        .setValue( 1 )
                        .build() );
                removeDialog.dismiss();
            }
        } );

        removeDialog.show();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.activity_single_coin, container, false );
        context = view.getContext();
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();

        //Link views
        titleCoin = view.findViewById( R.id.txt_title );
        machineName = view.findViewById( R.id.txt_land );
        txtDate = view.findViewById( R.id.txtDateCollected );
        coinFront = view.findViewById( R.id.editCoinFront );
        coinBack = view.findViewById( R.id.editCoinBack );
        haveCoinBox = view.findViewById( R.id.boxCollected );
        wantItBox = view.findViewById( R.id.boxWant );
        txtMac = view.findViewById( R.id.txtMachine );

        // Get calendar
        myCalendar = Calendar.getInstance();

        // Update coin count
        numCoins = sharedPreference.getCoins( context ).size();

        onDateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth )
            {
                myCalendar.set( Calendar.YEAR, year );
                myCalendar.set( Calendar.MONTH, monthOfYear );
                myCalendar.set( Calendar.DAY_OF_MONTH, dayOfMonth );

                // Update coin date info
                sharedPreference.removeCoin( getActivity().getApplicationContext(), coin );
                if ( haveCoinBox.isChecked() )
                {
                    Toast.makeText( getActivity().getApplicationContext(), "Date Updated", Toast.LENGTH_SHORT ).show();
                    coin.setDateCollected( myCalendar.getTime() );
                    sharedPreference.addCoin( getActivity().getApplicationContext(), coin );
                    txtDate.setText( dateFormat.format( myCalendar.getTime() ) );
                } else
                {
                    Toast.makeText( getActivity().getApplicationContext(), "Date Selected", Toast.LENGTH_SHORT ).show();
                    haveCoinBox.setChecked( true );
                }
            }

        };

        txtDate.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                // Open calendar window
                if ( coin.getDateCollected() != null )
                {
                    myCalendar.setTime( coin.getDateCollected() );
                }

                new DatePickerDialog( getActivity(), onDateSetListener, myCalendar
                        .get( Calendar.YEAR ), myCalendar.get( Calendar.MONTH ),
                        myCalendar.get( Calendar.DAY_OF_MONTH ) ).show();
            }
        } );

        Bundle extras = getArguments();
        if ( extras != null )
        {
            Gson gson = new Gson();
            String jsonCoin = extras.getString( "selectedCoin" );
            String jsonMac = extras.getString( "selectedMachine" );
            machine = gson.fromJson( jsonMac, Machine.class );
            coin = gson.fromJson( jsonCoin, Coin.class );

            savedCoins = sharedPreference.getCoins( context );

            // Check if coin is collected
            for ( Coin c : savedCoins )
            {
                if ( c.equals( coin ) )
                {
                    coin = savedCoins.get( savedCoins.indexOf( c ) );
                }
            }

            // Display machine name in nav bar
            getActivity().setTitle( machine.getMachineName() );

            // Display images
            int frontResId = view.getContext().getResources().getIdentifier( coin.getCoinFrontImg(), "drawable", view.getContext().getPackageName() );
            int backResId;
            if ( machine.getBackstampImg() == null )
            {
                backResId = view.getContext().getResources().getIdentifier( coin.getCoinFrontImg() + "_backstamp", "drawable", view.getContext().getPackageName() );
            } else
            {
                backResId = view.getContext().getResources().getIdentifier( machine.getBackstampImg(), "drawable", view.getContext().getPackageName() );
            }

            if ( frontResId == 0 )
            {
                Picasso.get().load( R.drawable.new_penny ).into( coinFront );
            } else
            {
                // Display front image
                Picasso.get().load( frontResId ).error( R.drawable.new_penny ).into( coinFront );
            }

            if ( backResId == 0 )
            {
                backResId = view.getContext().getResources().getIdentifier( "new_penny_back", "drawable", view.getContext().getPackageName() );
            }

            // Create dimensions
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;

            // Check dimensions of front image
            Bitmap frontBitmap = BitmapFactory.decodeResource( context.getResources(), frontResId, dimensions );
            int frontHeight = dimensions.outHeight;
            int frontWidth = dimensions.outWidth;

            // Check dimensions of back image
            Bitmap backBitmap = BitmapFactory.decodeResource( context.getResources(), backResId, dimensions );
            int backHeight = dimensions.outHeight;
            int backWidth = dimensions.outWidth;

            // Set orientation based on coin direction
            if ( frontWidth > frontHeight )
            {
                if ( backWidth < backHeight )
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).rotate( 90 ).into( coinBack );
                } else
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).into( coinBack );
                }
            } else
            {
                if ( backWidth > backHeight )
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).rotate( 90 ).into( coinBack );
                } else
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).into( coinBack );
                }
            }

            // Set animations
            mSetRightOut = ( AnimatorSet ) AnimatorInflater.loadAnimator( view.getContext(), R.animator.flip1 );
            mSetLeftIn = ( AnimatorSet ) AnimatorInflater.loadAnimator( view.getContext(), R.animator.flip2 );

            // Set camera distance
            int distance = 8000;
            float scale = getResources().getDisplayMetrics().density * distance;
            coinFront.setCameraDistance( scale );
            coinBack.setCameraDistance( scale );

            // Start rotation of coin
            mHandler = new Handler();

            // Set data
            titleCoin.setText( coin.getTitleCoin() );
            machineName.setText( machine.getLand() );
            txtMac.setText( machine.getMachineName() );

            // Set listeners for coin
            coinFront.setOnClickListener( this );
            coinBack.setOnClickListener( this );

            // Set state of checkbox based on if coin is in collected
            if ( checkCoin( coin ) )
            {
                haveCoinBox.setChecked( true );
                wantItBox.setEnabled( false );
                txtDate.setText( dateFormat.format( coin.getDateCollected() ) );
            } else
            {
                wantItBox.setEnabled( true );
                haveCoinBox.setChecked( false );
            }
            if ( checkWant( coin ) )
            {
                wantItBox.setChecked( true );
                txtDate.setText( "Want It" );
            } else
            {
                wantItBox.setChecked( false );
            }

            wantItBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged( CompoundButton compoundButton, boolean isChecked )
                {
                    if ( isChecked )
                    {
                        // Add coin to want list
                        if ( !checkCoin( coin ) )
                        { // Check if coin is already collected
                            txtDate.setText( "Want It" );
                            sharedPreference.addWantedCoin( getActivity().getApplicationContext(), coin );
                        }
                    } else
                    {
                        if ( txtDate.getText().equals( "Want It" ) )
                        {
                            txtDate.setText( "Not yet collected" );
                        }
                        sharedPreference.removeWantedCoin( getActivity().getApplicationContext(), coin );
                    }
                }
            } );

            haveCoinBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged( CompoundButton buttonView, boolean isChecked )
                {
                    if ( isChecked )
                    {
                        // Check if it's already in list
                        if ( !checkCoin( coin ) )
                        {
                            // Add coin to collected list
                            coin.setDateCollected( myCalendar.getTime() );
                            txtDate.setText( dateFormat.format( myCalendar.getTime() ) );

                            if ( wantItBox.isChecked() )
                            {
                                wantItBox.setChecked( false );
                                wantItBox.setEnabled( false );
                                sharedPreference.removeWantedCoin( getActivity().getApplicationContext(), coin );
                            } else
                            {
                                wantItBox.setEnabled( false );
                            }

                            sharedPreference.addCoin( getActivity().getApplicationContext(), coin );
                            MainActivity.updateCoinTotals();

                            mTracker.send( new HitBuilders.EventBuilder()
                                    .setCategory( "Coin Collected" )
                                    .setAction( "Collected" )
                                    .setLabel( coin.getTitleCoin() )
                                    .setValue( 1 )
                                    .build() );
                        }
                    } else
                    {
                        showNoticeDialog();
                    }
                }
            } );

            TextView txtParkBanner = view.findViewById( R.id.parkBanner );
            txtParkBanner.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View view )
                {
                    String url = "http://www.parkpennies.com";
                    mTracker.send( new HitBuilders.EventBuilder()
                            .setCategory( "Website" )
                            .setAction( "Clicked" )
                            .setLabel( coin.getTitleCoin() )
                            .setValue( 1 )
                            .build() );
                    Intent intent = new Intent( Intent.ACTION_VIEW );
                    intent.setData( Uri.parse( url ) );
                    startActivity( intent );
                }
            } );
        }

        /*// Gesture detection
        gestureDetector = new GestureDetector(getActivity(), new MyGestureDetector());
        swipeListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        scrollView = view.findViewById(R.id.normal_coin);

        scrollView.setOnTouchListener(swipeListener);*/

        return view;
    }

    private final Runnable mStatusChecker = new Runnable()
    {
        @Override
        public void run()
        {
            try
            {
                flipCard();
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

    private void flipCard()
    {
        coinBack.setVisibility( View.VISIBLE );
        if ( !mIsBackVisible )
        {
            mSetRightOut.setTarget( coinFront );
            mSetLeftIn.setTarget( coinBack );
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else
        {
            mSetRightOut.setTarget( coinBack );
            mSetLeftIn.setTarget( coinFront );
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    // Check if coin is in collected coins
    private boolean checkCoin( Coin coinToCheck )
    {
        boolean check = false;

        // Get collected coins list
        List< Coin > coins = sharedPreference.getCoins( getActivity().getApplicationContext() );
        if ( coins != null )
        {
            for ( Coin coin : coins )
            {
                // Check if coin matches coin already collected
                if ( String.valueOf( coin.getTitleCoin() ).equals( String.valueOf( coinToCheck.getTitleCoin() ) ) )
                {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    // Check if coin is in collected coins
    private boolean checkWant( Coin checkCoin )
    {
        boolean check = false;
        // Get collected coins list
        List< Coin > wantCoins = sharedPreference.getWantedCoins( getActivity().getApplicationContext() );

        if ( wantCoins != null )
        {
            for ( Coin coin : wantCoins )
            {
                // Check if coin matches coin already collected
                if ( String.valueOf( coin.getTitleCoin() ).equals( String.valueOf( checkCoin.getTitleCoin() ) ) )
                {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void onClick( View coinImage )
    {
        //Get which row was clicked
        Intent intent = new Intent( coinImage.getContext(), BigImage.class );
        intent.putExtra( "frontImg", coin.getCoinFrontImg() );
        intent.putExtra( "backImg", machine.getBackstampImg() );
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        coinImage.getContext().startActivity( intent );
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mHandler.removeCallbacks( mStatusChecker );
        super.onDestroy();
    }

    @Override
    public void onResume()
    {
        rotate();
        super.onResume();
    }

    /*class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
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
            Toast.makeText( getActivity(), "Right Swipe", Toast.LENGTH_SHORT ).show();
            Coin[] a = machine.getCoins();

            List< Coin > b = Arrays.asList( a );
            int pos = b.indexOf( coin );
            if ( pos > 0 )
            {
                pos -= 1;
            } else
            {
                pos = b.size() - 1;
            }
            coin = machine.getCoins()[ pos ];


            // Display machine name in nav bar
            getActivity().setTitle( machine.getMachineName() );
            // Display images
            int resId = context.getResources().getIdentifier( coin.getCoinFrontImg(), "drawable", context.getPackageName() );
            int resId2;
            if ( machine.getBackstampImg() == null )
            {
                resId2 = context.getResources().getIdentifier( coin.getCoinFrontImg() + "_backstamp", "drawable", context.getPackageName() );
            } else
            {
                resId2 = context.getResources().getIdentifier( machine.getBackstampImg(), "drawable", context.getPackageName() );
            }
            img.loadBitmap( resId, getResources(), 800, 800, coinFront, 0 );
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;
            Bitmap mBitmap = BitmapFactory.decodeResource( getActivity().getResources(), resId, dimensions );
            int height = dimensions.outHeight;
            int width = dimensions.outWidth;

            //Set orientation based on coin direction
            if ( width > height )
            {
                Bitmap mBitmap2 = BitmapFactory.decodeResource( getActivity().getResources(), resId2 );
                if ( mBitmap2 == null )
                {
                    int resId3 = context.getResources().getIdentifier( "new_searching", "drawable", context.getPackageName() );
                    mBitmap2 = BitmapFactory.decodeResource( getActivity().getResources(), resId3 );
                }
                if ( mBitmap2.getWidth() < mBitmap2.getHeight() )
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate( 90 );
                    Bitmap bitmap = Bitmap.createBitmap( mBitmap2, 0, 0, mBitmap2.getWidth(), mBitmap2.getHeight(), matrix, true );
                    coinBack.setImageBitmap( bitmap );
                } else
                {
                    img.loadBitmap( resId2, getResources(), 800, 800, coinBack, resId );
                }

            } else
            {
                img.loadBitmap( resId2, getResources(), 800, 800, coinBack, resId );
            }


            // Set data
            titleCoin.setText( coin.getTitleCoin() );
            machineName.setText( machine.getLand() );
            txtMac.setText( machine.getMachineName() );

            // Set state of checkbox based on if coin is in collected
            if ( checkCoin( coin ) )
            {
                haveCoinBox.setChecked( true );
                wantItBox.setEnabled( false );
                txtDate.setText( dateFormat.format( coin.getDateCollected() ) );
            } else
            {
                wantItBox.setEnabled( true );
                haveCoinBox.setChecked( false );
            }
            if ( checkWant( coin ) )
            {
                wantItBox.setChecked( true );
                txtDate.setText( "Want It" );
            } else
            {
                wantItBox.setChecked( false );
            }
        }

        private void swipeLeft()
        {
            Toast.makeText( getActivity(), "Left Swipe", Toast.LENGTH_SHORT ).show();
            slideToLeft( scrollView );
            slideToLeftFromEdge( scrollView );
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            singleCoin fragment = new singleCoin();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            Coin[] a = machine.getCoins();
            List< Coin > b = Arrays.asList( a );
            int pos = b.indexOf( coin );
            if ( pos < b.size() - 1 )
            {
                pos += 1;
            } else
            {
                pos = 0;
            }
            coin = machine.getCoins()[ pos ];
            String jsonCoin = gson.toJson( machine.getCoins()[ pos ] );
            String jsonMachine = gson.toJson( machine );
            bundle.putString( "selectedCoin", jsonCoin );
            bundle.putString( "selectedMachine", jsonMachine );
            fragment.setArguments( bundle );
            fragmentTransaction.setCustomAnimations(
                    R.animator.fade_in,
                    R.animator.fade_out,
                    R.animator.fade_in,
                    R.animator.fade_out );
            fragmentTransaction.replace( R.id.mainFrag, fragment );
            fragmentTransaction.commit();
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

    // To animate view slide out from right to left
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
    }*/


}