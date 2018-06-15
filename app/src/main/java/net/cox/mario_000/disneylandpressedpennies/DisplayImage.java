package net.cox.mario_000.disneylandpressedpennies;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by mario_000 on 7/7/2016.
 * Description: Class to display image in image view
 */

public class DisplayImage extends MainActivity {
    private final LruCache<String, Bitmap> mMemoryCache;
    private int resId = 0;
    private int minX = 0;
    private int minY = 0;
    private Resources resources;
    private int resIdFront;

    public DisplayImage() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/4th of the available memory for memory cache
        final int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than number of items
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void loadBitmap(int resId, Resources resources, int minX, int minY, ImageView img, int resIdFront) {
        this.resources = resources;
        this.resId = resId;
        this.resIdFront = resIdFront;

        // Check dimensions of front image
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(resources, resIdFront, dimensions);
        int height = dimensions.outHeight;
        int width = dimensions.outWidth;

        // Resource not found
        if (resId == 0) {
            img.setImageResource(R.drawable.new_searching);
        } else {
            // Find bitmap from resId
            final String imageKey = String.valueOf(resId);
            Bitmap bitmap = getBitmapFromMemCache(imageKey);

            if (bitmap != null) {
                // Check if front image is portrait and rotate back image
                if (width < height && bitmap.getWidth() >= bitmap.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    img.setImageBitmap(bitmap2);
                } else {
                    img.setImageBitmap(bitmap);
                }

            } else {
                // Create new bitmap task
                BitmapWorkerTask task = new BitmapWorkerTask(img);
                // Execute doInBackground
                task.execute(resId, minX, minY);
            }
        }
    }

    // Calculate smallest size the image can be based on min req
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        // Check if height or width is above requirement
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // Set imageview to gray
    public void setToGray(ImageView v) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    // Set imageview to color
    public void setToColor(ImageView v) {
        v.setColorFilter(null);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            resId = params[0];
            minX = params[1];
            minY = params[2];
            final Bitmap bitmap = decodeSampledBitmapFromResource(resources, resId, minX, minY);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = imageViewReference.get();
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;
            Bitmap mBitmap = BitmapFactory.decodeResource(resources, resIdFront, dimensions);
            int height = dimensions.outHeight;
            int width = dimensions.outWidth;

            if (width < height && bitmap.getWidth() >= bitmap.getHeight()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap2);
            } else {
                imageView.setImageBitmap(bitmap);
            }

        }
    }
}