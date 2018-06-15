package net.cox.mario_000.disneylandpressedpennies;

/**
 * Created by mario_000 on 9/5/2016.
 * Description: Adapter class for displaying grid view
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

public class GridViewAdapter extends BaseDynamicGridAdapter {

    public GridViewAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.build((Coin) getItem(position));
        return convertView;
    }

    private class ViewHolder {
        private ImageView letterText;

        private ViewHolder(View view) {
            letterText = (ImageView) view.findViewById(R.id.image_grid);
        }

        void build(Coin title) {
            String coinImage = title.getCoinFrontImg();

            int resId = getContext().getResources().getIdentifier(coinImage, "drawable", getContext().getPackageName());

            if(resId == 0){
                resId = getContext().getResources().getIdentifier("new_searching", "drawable", getContext().getPackageName());
            }

            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            Bitmap mBitmap = BitmapFactory.decodeResource(getContext().getResources(), resId, dimensions);

            if (mBitmap.getWidth() > mBitmap.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap2 = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                letterText.setImageBitmap(bitmap2);
            } else {
                letterText.setImageBitmap(mBitmap);
            }
        }
    }
}