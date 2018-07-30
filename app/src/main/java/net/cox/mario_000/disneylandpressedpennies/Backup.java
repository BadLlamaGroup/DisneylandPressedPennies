package net.cox.mario_000.disneylandpressedpennies;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static net.cox.mario_000.disneylandpressedpennies.Data.calMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.disneyMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.downtownMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.retiredDisneylandMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.COIN_PATH;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.PREFS_NAME;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numArcCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numArcCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCoins;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCurrentCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCurrentCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.R.id.backupList;

/**
 * Created by mario_000 on 5/10/2017.
 */

public class Backup extends Fragment
{
    private Tracker mTracker;
    private SharedPreference sharedPreference;
    boolean storageApproved;
    List< Coin > savedCoins;
    LinearLayout backupBtn;
    LinearLayout restoreBtn;
    LinearLayout exportBtn;
    TextView txtCheck;
    MainActivity application;
    public static SharedPreferences pref;
    private SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );

    @Override
    public void onResume()
    {
        mTracker.setScreenName( "Page - Backup/Restore" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );
        super.onResume();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.backup, container, false );
        getActivity().setTitle( "Backup / Restore" );

        application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();

        sharedPreference = new SharedPreference();
        savedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );
        pref = application.getApplicationContext().getSharedPreferences( PREFS_NAME, MODE_PRIVATE );


        isStoragePermissionGranted();

        backupBtn = view.findViewById( backupList );
        restoreBtn = view.findViewById( R.id.restoreList );
        exportBtn = view.findViewById( R.id.exportList );
        txtCheck = view.findViewById( R.id.txtResult );

        checkBackup();

        backupBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                showNoticeDialogBackup();
            }
        } );

        restoreBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                showNoticeDialogRestore();
            }
        } );

        exportBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                createandDisplayPdf();
            }
        } );

        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void backupApp()
    {
        ObjectOutputStream output = null;
        try
        {
            File sdCard = Environment.getExternalStorageDirectory();
            // TODO: 9/20/2017 Check if dir already exists
            File dir = new File( sdCard.getAbsolutePath() + "/Pressed Coins at Disneyland" );
            dir.mkdirs();
            File file = new File( dir, "coinsBackup.ser" );
            // TODO: 9/20/2017 Make file non-editable
            output = new ObjectOutputStream( new FileOutputStream( file ) );
            output.writeObject( pref.getAll() );
            Toast.makeText( getActivity().getApplicationContext(), "Backup created in " + sdCard.getAbsolutePath() + "/Pressed Coins at Disneyland", Toast.LENGTH_LONG ).show();
        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if ( output != null )
                {
                    output.flush();
                    output.close();
                }
            } catch ( IOException ex )
            {
                ex.printStackTrace();
            }
        }
        checkBackup();
    }

    @SuppressWarnings( { "unchecked" } )
    private void restoreApp()
    {
        ObjectInputStream input = null;
        try
        {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File( sdCard.getAbsolutePath() + "/Pressed Coins at Disneyland" );
            dir.mkdirs();
            File file = new File( dir, "coinsBackup.ser" );
            input = new ObjectInputStream( new FileInputStream( file ) );
            SharedPreferences.Editor prefEdit = pref.edit();
            prefEdit.clear();
            Map< String, ? > entries = ( Map< String, ? > ) input.readObject();
            for ( Map.Entry< String, ? > entry : entries.entrySet() )
            {
                Object v = entry.getValue();
                String key = entry.getKey();

                if ( v instanceof Boolean )
                    prefEdit.putBoolean( key, ( ( Boolean ) v ).booleanValue() );
                else if ( v instanceof Float )
                    prefEdit.putFloat( key, ( ( Float ) v ).floatValue() );
                else if ( v instanceof Integer )
                    prefEdit.putInt( key, ( ( Integer ) v ).intValue() );
                else if ( v instanceof Long )
                    prefEdit.putLong( key, ( ( Long ) v ).longValue() );
                else if ( v instanceof String )
                    prefEdit.putString( key, ( ( String ) v ) );
            }
            prefEdit.apply();
            Toast.makeText( getActivity().getApplicationContext(), "Coins restored.", Toast.LENGTH_SHORT ).show();
        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        } catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
        } finally
        {
            updateCoinTotals();
            try
            {
                if ( input != null )
                {
                    input.close();
                }
            } catch ( IOException ex )
            {
                ex.printStackTrace();
            }
        }
    }

    public void updateCoinTotals()
    {
        SharedPreference sharedPreference = new SharedPreference();
        savedCoins = sharedPreference.getCoins( getActivity().getApplication() );
        numDisneyCoinsTotal = 0;
        numCalCoinsTotal = 0;
        numDowntownCoinsTotal = 0;
        numCurrentCoinsTotal = 0;
        numCoinsTotal = 0;
        numArcCoinsTotal = 0;
        numDisneyCoinsCollected = 0;
        numCalCoinsCollected = 0;
        numDowntownCoinsCollected = 0;
        numArcCoinsCollected = 0;
        numCoins = 0;
        for ( Machine[] m : disneyMachines )
        {
            for ( Machine mach : m )
            {
                numDisneyCoinsTotal += mach.getCoins().length;
                for ( Coin c : mach.getCoins() )
                {
                    if ( savedCoins.contains( c ) )
                    {
                        numDisneyCoinsCollected++;
                    }
                }
            }
        }

        for ( Machine[] m : calMachines )
        {
            for ( Machine mach : m )
            {
                numCalCoinsTotal += mach.getCoins().length;
                for ( Coin c : mach.getCoins() )
                {
                    if ( savedCoins.contains( c ) )
                    {
                        numCalCoinsCollected++;
                    }
                }
            }

        }

        for ( Machine[] m : downtownMachines )
        {
            for ( Machine mach : m )
            {
                numDowntownCoinsTotal += mach.getCoins().length;
                for ( Coin c : mach.getCoins() )
                {
                    if ( savedCoins.contains( c ) )
                    {
                        numDowntownCoinsCollected++;
                    }
                }
            }

        }

        for ( Machine mach : retiredDisneylandMachines )
        {
            numArcCoinsTotal += mach.getCoins().length;
            for ( Coin c : mach.getCoins() )
            {
                if ( savedCoins.contains( c ) )
                {
                    numArcCoinsCollected++;
                }
            }
        }

        numCoins = savedCoins.size();
        numCurrentCoinsCollected = savedCoins.size() - numArcCoinsCollected;
        numCurrentCoinsTotal += numDisneyCoinsTotal + numCalCoinsTotal + numDowntownCoinsTotal;
        numCoinsTotal += numCurrentCoinsTotal + numArcCoinsTotal;
    }

    private boolean checkBackup()
    {

        boolean backupExists = false;

        if ( Environment.MEDIA_MOUNTED.equals( Environment.getExternalStorageState() ) )
        {
            try
            {
                File file = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pressed Coins at Disneyland", "coinsBackup.ser" );
                if ( file.exists() )
                {
                    backupExists = true;
                    txtCheck.setText( "Tap to restore your coins backed up on " + dateFormat.format( file.lastModified() ) + "." );
                } else
                {
                    backupExists = false;
                }
            } catch ( Exception e )
            {
                e.printStackTrace();
            }

        }
        return backupExists;
    }

    public void isStoragePermissionGranted()
    {
        if ( Build.VERSION.SDK_INT >= 23 )
        {
            if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED )
            {
                FragmentCompat.requestPermissions( this, new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1 );

            } else
            {
                storageApproved = true;
            }
        } else
        {
            storageApproved = true;
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults )
    {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if ( grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED )
        {
            storageApproved = true;
        } else
        {
            getFragmentManager().popBackStackImmediate();
        }
    }

    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayPdf()
    {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File( sdCard.getAbsolutePath() + "/Pressed Coins at Disneyland" );
        dir.mkdir();

        mTracker.send( new HitBuilders.EventBuilder()
                .setCategory( "Export" )
                .setAction( "Exported" )
                .setValue( 1 )
                .build() );

        File myFile = new File( dir, "Coin-list.pdf" );

        try
        {
            // Create document
            OutputStream output = new FileOutputStream( myFile );
            Document document = new Document( PageSize.LETTER );
            PdfWriter.getInstance( document, output );
            document.open();

            // Create title
            Font titleFont = new Font( Font.FontFamily.HELVETICA, 20, Font.BOLD );
            Paragraph title = new Paragraph( "Pressed Coins at Disneyland", titleFont );
            title.setAlignment( Element.ALIGN_CENTER );
            document.add( title );
            document.add( Chunk.NEWLINE );

            // Get coins
            List< Coin > collectedCoins = sharedPreference.getCoins( getActivity() );
            List< Coin > wantCoins = sharedPreference.getWantedCoins( getActivity() );
            List< Coin > customCoins = sharedPreference.getCustomCoins( getActivity() );

            // Prepare collected coins
            Font coinHeaderFont = new Font( Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD );
            DottedLineSeparator separator = new DottedLineSeparator();
            separator.setGap( 7 );
            separator.setLineWidth( 3 );
            Chunk linebreak = new Chunk( separator );
            document.add( linebreak );
            document.add( Chunk.NEWLINE );
            document.add( new Paragraph( "Collected coins:", coinHeaderFont ) );
            document.add( linebreak );
            document.add( Chunk.NEWLINE );

            Collections.sort( collectedCoins, new Comparator< Coin >()
            {
                @Override
                public int compare( Coin c1, Coin c2 )
                {
                    return c1.getDateCollected().compareTo( c2.getDateCollected() );
                }
            } );

            int count = 1;
            for ( Coin coin : collectedCoins )
            {
                if ( count % 6 == 0 )
                {
                    document.newPage();
                }

                document.add( new Paragraph( coin.getTitleCoin() + " --- Date: " + dateFormat.format( coin.getDateCollected() ) ) );
                Context context = getActivity().getApplicationContext();
                int imgId = context.getResources().getIdentifier( coin.getCoinFrontImg(), "drawable", context.getPackageName() );
                if ( imgId == 0 )
                {
                    imgId = context.getResources().getIdentifier( "searching", "drawable", context.getPackageName() );
                }
                BitmapFactory.Options dimensions = new BitmapFactory.Options();
                dimensions.inJustDecodeBounds = true;
                Bitmap bitDim = BitmapFactory.decodeResource( context.getResources(), imgId, dimensions );
                Bitmap mBitmap = BitmapFactory.decodeResource( context.getResources(), imgId );
                int height = dimensions.outHeight;
                int width = dimensions.outWidth;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress( Bitmap.CompressFormat.PNG, 100, stream );
                Image myImg = Image.getInstance( stream.toByteArray() );
                myImg.setTransparency( new int[]{ 0x00, 0x10 } );
                if ( width > height )
                {
                    myImg.scaleAbsolute( 75f, 50f );
                } else
                {
                    myImg.scaleAbsolute( 50f, 75f );
                }


                /*//Bitmap bitDim2 = BitmapFactory.decodeResource( context.getResources(), imgId, dimensions );
                Bitmap mBitmap2 = BitmapFactory.decodeResource( context.getResources(), imgId2 );
                int height2 = dimensions.outHeight;
                int width2 = dimensions.outWidth;

                mBitmap2.compress( Bitmap.CompressFormat.PNG, 100, stream );
                Image myImg2 = Image.getInstance( stream.toByteArray() );
                myImg2.setTransparency( new int[]{ 0x00, 0x10 } );
                if ( width2 > height2 )
                {
                    myImg2.scaleAbsolute( 75f, 50f );
                } else
                {
                    myImg2.scaleAbsolute( 50f, 75f );
                }

                PdfPTable table = new PdfPTable(2);
                PdfPCell cell = new PdfPCell();
                Paragraph p = new Paragraph();
                p.add(new Chunk(myImg, 0, 0));
                p.add(new Chunk(myImg2, 0, 0));
                cell.addElement(p);
                table.addCell(cell);
                table.addCell(new PdfPCell(new Phrase(coin.getTitleCoin())));
                document.add(table);*/

                document.add( myImg );

                count++;
            }

            document.newPage();
            count = 1;

            // Prepare wanted coins
            if ( wantCoins.size() != 0 )
            {
                document.add( linebreak );
                document.add( Chunk.NEWLINE );
                document.add( new Paragraph( "Wanted coins:", coinHeaderFont ) );
                document.add( linebreak );
                document.add( Chunk.NEWLINE );
            }
            for ( Coin coin : wantCoins )
            {
                if ( count % 6 == 0 )
                {
                    document.newPage();
                }
                document.add( new Paragraph( coin.getTitleCoin() ) );
                Context context = getActivity().getApplicationContext();
                int imgId = context.getResources().getIdentifier( coin.getCoinFrontImg(), "drawable", context.getPackageName() );
                BitmapFactory.Options dimensions = new BitmapFactory.Options();
                dimensions.inJustDecodeBounds = true;
                Bitmap bitDim = BitmapFactory.decodeResource( context.getResources(), imgId, dimensions );
                Bitmap mBitmap = BitmapFactory.decodeResource( context.getResources(), imgId );
                int height = dimensions.outHeight;
                int width = dimensions.outWidth;


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress( Bitmap.CompressFormat.PNG, 100, stream );
                Image myImg = Image.getInstance( stream.toByteArray() );
                myImg.setTransparency( new int[]{ 0x00, 0x10 } );
                if ( width > height )
                {
                    myImg.scaleAbsolute( 75f, 50f );
                } else
                {
                    myImg.scaleAbsolute( 50f, 75f );
                }

                document.add( myImg );

                count++;
            }

            document.newPage();
            count = 1;

            Collections.sort( customCoins, new Comparator< Coin >()
            {
                @Override
                public int compare( Coin c1, Coin c2 )
                {
                    return c1.getDateCollected().compareTo( c2.getDateCollected() );
                }
            } );

            // Prepare custom coins
            if ( customCoins.size() != 0 )
            {
                document.add( linebreak );
                document.add( Chunk.NEWLINE );
                document.add( new Paragraph( "Custom coins:", coinHeaderFont ) );
                document.add( linebreak );
                document.add( Chunk.NEWLINE );
            }
            for ( Coin coin : customCoins )
            {
                if ( count % 6 == 0 )
                {
                    document.newPage();
                }

                document.add( new Paragraph( coin.getTitleCoin() + " --- Date: " + dateFormat.format( coin.getDateCollected() ) ) );
                String filePath = COIN_PATH + "/" + coin.getCoinFrontImg();

                BitmapFactory.Options dimensions = new BitmapFactory.Options();
                dimensions.inJustDecodeBounds = true;
                Bitmap bitDim = BitmapFactory.decodeFile( filePath, dimensions );
                Bitmap mBitmap = BitmapFactory.decodeFile( filePath );
                int height = dimensions.outHeight;
                int width = dimensions.outWidth;


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress( Bitmap.CompressFormat.PNG, 100, stream );
                Image myImg = Image.getInstance( stream.toByteArray() );
                myImg.setTransparency( new int[]{ 0x00, 0x10 } );
                if ( width > height )
                {
                    myImg.scaleAbsolute( 75f, 50f );
                } else
                {
                    myImg.scaleAbsolute( 50f, 75f );
                }

                document.add( myImg );

                count++;
            }

            document.close();
            Toast.makeText( getActivity(), "PDF Created", Toast.LENGTH_SHORT ).show();
            showNoticeDialogExport( myFile );
        } catch ( DocumentException e )
        {
            e.printStackTrace();
        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private void viewPdf( File myFile )
    {
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setDataAndType( Uri.fromFile( myFile ), "application/pdf" );
        intent.setFlags( Intent.FLAG_ACTIVITY_NO_HISTORY );

        Intent chooser = Intent.createChooser( intent, "Open with:" );

        if ( intent.resolveActivity( getActivity().getPackageManager() ) != null )
        {
            startActivity( chooser );
        } else
        {
            Toast.makeText( getActivity(), "PDF Reader not installed...", Toast.LENGTH_LONG ).show();
            Intent goToMarket = new Intent( Intent.ACTION_VIEW ).setData( Uri.parse( "market://search?q=PDF" ) );
            startActivity( goToMarket );
        }
    }

    public void showNoticeDialogBackup()
    {
        final Dialog removeDialog = new Dialog( getActivity(), R.style.CustomDialog );
        // Set up dialog window
        removeDialog.setContentView( R.layout.backup_restore_dialog );
        removeDialog.setTitle( "Title..." );
        removeDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        removeDialog.setCancelable( false );
        TextView txtOption = removeDialog.findViewById( R.id.txt_option );
        // Link buttons
        Button noBtn = removeDialog.findViewById( R.id.btn_no );
        Button backupBtn = removeDialog.findViewById( R.id.btn_yes );

        txtOption.setText( "Are you sure you want to backup?" );

        // Listeners for buttons
        noBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                removeDialog.dismiss();
            }
        } );

        backupBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                backupApp();
                mTracker.send( new HitBuilders.EventBuilder()
                        .setCategory( "Backup/Restore" )
                        .setAction( "Backed up" )
                        .setValue( 1 )
                        .build() );
                removeDialog.dismiss();
            }
        } );

        removeDialog.show();
    }

    public void showNoticeDialogRestore()
    {
        final Dialog removeDialog = new Dialog( getActivity(), R.style.CustomDialog );
        // Set up dialog window
        removeDialog.setContentView( R.layout.backup_restore_dialog );
        removeDialog.setTitle( "Title..." );
        removeDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        removeDialog.setCancelable( false );
        TextView txtOption = removeDialog.findViewById( R.id.txt_option );
        // Link buttons
        Button noBtn = removeDialog.findViewById( R.id.btn_no );
        Button restoreBtn = removeDialog.findViewById( R.id.btn_yes );

        txtOption.setText( "Are you sure you want to restore?" );

        // Listeners for buttons
        noBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                removeDialog.dismiss();
            }
        } );

        restoreBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                restoreApp();
                mTracker.send( new HitBuilders.EventBuilder()
                        .setCategory( "Backup/Restore" )
                        .setAction( "Restored" )
                        .setValue( 1 )
                        .build() );
                removeDialog.dismiss();
            }
        } );

        removeDialog.show();
    }

    public void showNoticeDialogExport( final File myFile )
    {
        final Dialog removeDialog = new Dialog( getActivity(), R.style.CustomDialog );
        // Set up dialog window
        removeDialog.setContentView( R.layout.backup_restore_dialog );
        removeDialog.setTitle( "Title..." );
        removeDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        removeDialog.setCancelable( false );
        TextView txtOption = removeDialog.findViewById( R.id.txt_option );
        // Link buttons
        Button noBtn = removeDialog.findViewById( R.id.btn_no );
        Button exportBtn = removeDialog.findViewById( R.id.btn_yes );

        txtOption.setText( "Would you like to view the PDF?" );

        // Listeners for buttons
        noBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                removeDialog.dismiss();
            }
        } );

        exportBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                viewPdf( myFile );
                mTracker.send( new HitBuilders.EventBuilder()
                        .setCategory( "Export" )
                        .setAction( "Viewed" )
                        .setValue( 1 )
                        .build() );
                removeDialog.dismiss();
            }
        } );

        removeDialog.show();
    }

}
