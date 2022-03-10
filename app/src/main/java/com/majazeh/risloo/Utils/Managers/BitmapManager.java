package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Base64;

import androidx.exifinterface.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BitmapManager {

    /*
    ---------- Convert's ----------
    */

    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /*
    ---------- Convert's ----------
    */

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] encodedByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(encodedByte, Base64.DEFAULT);
    }

    public static Bitmap stringToBitmap(String string) {
        byte[] decodedByte = Base64.decode(string, 0);

        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /*
    ---------- Convert's ----------
    */

    public static Bitmap pathToBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = Math.max(1, 2);

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap uriToBitmap(Activity activity, Uri uri) {
        try {
            InputStream imageStream = activity.getContentResolver().openInputStream(uri);

            return BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
    ---------- Func's ----------
    */

    public static Bitmap imageModify(Bitmap bitmap, String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return orientationRotate(bitmap, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return orientationRotate(bitmap, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return orientationRotate(bitmap, 270);
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    return orientationFlip(bitmap, true, false);
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    return orientationFlip(bitmap, false, true);
                default:
                    return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Bitmap imageScaleToCenter(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int dimension = Math.min(width, height);

        if (dimension > 960) {
            dimension = 960;
        }

        int left = (dimension - width) / 2;
        int top = (dimension - height) / 2;

        int right = left + width;
        int bottom =top + height;

        RectF targetRect = new RectF(left, top, right, bottom);
        Bitmap image = Bitmap.createBitmap(dimension, dimension, bitmap.getConfig());

        Canvas canvas = new Canvas(image);
        canvas.drawBitmap(bitmap, null, targetRect, null);

        return image;
    }

    /*
    ---------- Orientation's ----------
    */

    private static Bitmap orientationRotate(Bitmap bitmap, float degrees) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    private static Bitmap orientationFlip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

}