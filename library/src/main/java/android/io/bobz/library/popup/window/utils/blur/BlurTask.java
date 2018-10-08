package android.io.bobz.library.popup.window.utils.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.io.bobz.library.popup.window.PopupWindow;
import android.io.bobz.library.popup.window.utils.blur.listener.BlurTaskCallback;
import android.os.AsyncTask;
import android.view.View;

import java.lang.ref.WeakReference;

public class BlurTask extends AsyncTask<Void, Void, Bitmap> {

    private WeakReference<Context> mContextRef;
    private WeakReference<PopupWindow> mPopupWindowRef;
    private Bitmap mSourceBitmap;
    private BlurTaskCallback mBlurTaskCallback;


    public BlurTask(View sourceView, int statusBarHeight, int navigationBarheight, PopupWindow popupWindow, BlurTaskCallback blurTaskCallback) {

        mContextRef = new WeakReference<>(sourceView.getContext());
        mPopupWindowRef = new WeakReference<>(popupWindow);
        mBlurTaskCallback = blurTaskCallback;

        int height = sourceView.getHeight() - statusBarHeight - navigationBarheight;
        if (height < 0) {
            height = sourceView.getHeight();
        }

        Drawable background = sourceView.getBackground();
        mSourceBitmap = Bitmap.createBitmap(sourceView.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mSourceBitmap);
        int saveCount = 0;
        if (statusBarHeight != 0) {
            saveCount = canvas.save();
            canvas.translate(0, -statusBarHeight);
        }
        if (popupWindow.getBlurRadius() > 0) {
            if (background == null) {
                canvas.drawColor(0xffffffff);
            }
            sourceView.draw(canvas);
        }
        if (popupWindow.getTintColor() != 0) {
            canvas.drawColor(popupWindow.getTintColor());
        }
        if (statusBarHeight != 0 && saveCount != 0) {
            canvas.restoreToCount(saveCount);
        }
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        Context context = mContextRef.get();
        PopupWindow popupWindow = mPopupWindowRef.get();
        if (context == null || popupWindow == null) {
            return null;
        }
        float scaleRatio = popupWindow.getScaleRatio();
        if (popupWindow.getBlurRadius() == 0) {
            return mSourceBitmap;
        }
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mSourceBitmap, (int) (mSourceBitmap.getWidth() * scaleRatio), (int) (mSourceBitmap.getHeight() * scaleRatio), false);
        float radius = popupWindow.getBlurRadius();
        Bitmap blurred = Blur.blur(context, scaledBitmap, radius);
        return Bitmap.createScaledBitmap(blurred, mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), true);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        PopupWindow popupWindow = mPopupWindowRef.get();
        if (popupWindow != null && popupWindow.getAnchorView() != null) {
            Canvas canvas = new Canvas(bitmap);
            View anchorView = popupWindow.getAnchorView();
            int[] location = new int[2];
            anchorView.getLocationInWindow(location);
            canvas.save();
            canvas.translate(location[0], location[1]);
            popupWindow.getAnchorView().draw(canvas);
            canvas.restore();
        }
        if (mBlurTaskCallback != null) {
            mBlurTaskCallback.onBlurFinish(bitmap);
        }
    }
}
