package net.cox.mario_000.disneylandpressedpennies;

/*
 * Created by mario_000 on 9/5/2016.
 * Description: Adapter class for displaying grid view
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.io.File;
import java.util.List;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.COIN_PATH;

public class GridViewAdapter extends BaseDynamicGridAdapter
{

    public GridViewAdapter( Context context, List< ? > items, int columnCount )
    {
        super( context, items, columnCount );
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        ViewHolder holder;
        if ( convertView == null )
        {
            convertView = LayoutInflater.from( getContext() ).inflate( R.layout.item_grid, null );
            holder = new ViewHolder( convertView );
            convertView.setTag( holder );
        } else
        {
            holder = ( ViewHolder ) convertView.getTag();
        }

        if( getItem( position ) instanceof Coin )
        {
            holder.build( ( Coin ) getItem( position ) );
        }
        else if( getItem( position ) instanceof CustomCoin )
        {
            holder.build( ( CustomCoin ) getItem( position ) );
        }

        return convertView;
    }

    private class ViewHolder
    {
        private ImageView gridImageView;

        private ViewHolder( View view )
        {
            gridImageView = view.findViewById( R.id.image_grid );
        }

        void build( Coin gridViewCoin )
        {
            // Get coin image
            int frontResId = getContext().getResources().getIdentifier( gridViewCoin.getCoinFrontImg(), "drawable", getContext().getPackageName() );

            // Create dimensions
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;

            // Check dimensions of front image
            Bitmap frontBitmap = BitmapFactory.decodeResource( getContext().getResources(), frontResId, dimensions );
            int frontHeight = dimensions.outHeight;
            int frontWidth = dimensions.outWidth;

            if ( frontResId == 0 )
            {
                Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + gridViewCoin.getCoinFrontImg() ) );
                Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( gridImageView );
            } else
            {
                if ( frontWidth > frontHeight )
                {
                    Picasso.get().load( frontResId ).rotate( 90 ).error( R.drawable.new_penny ).into( gridImageView );
                } else
                {
                    Picasso.get().load( frontResId ).error( R.drawable.new_penny ).into( gridImageView );
                }
            }
        }

        void build( CustomCoin gridViewCoin )
        {
            // Get coin image
            int frontResId = getContext().getResources().getIdentifier( gridViewCoin.getCoinFrontImg(), "drawable", getContext().getPackageName() );

            // Create dimensions
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;

            // Check dimensions of front image
            Bitmap frontBitmap = BitmapFactory.decodeResource( getContext().getResources(), frontResId, dimensions );
            int frontHeight = dimensions.outHeight;
            int frontWidth = dimensions.outWidth;

            if ( frontResId == 0 )
            {
                Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + gridViewCoin.getCoinFrontImg() ) );
                Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( gridImageView );
            } else
            {
                if ( frontWidth > frontHeight )
                {
                    Picasso.get().load( frontResId ).rotate( 90 ).error( R.drawable.new_penny ).into( gridImageView );
                } else
                {
                    Picasso.get().load( frontResId ).error( R.drawable.new_penny ).into( gridImageView );
                }
            }
        }
    }
}