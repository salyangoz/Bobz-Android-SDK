package android.io.bobz.library.popup.window;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class PopupWindowBuilder<T extends PopupWindow> {

    private static final String TAG = "PopupWindow.Builder";
    protected Context mContext;
    public View mContentView;
    private int mTintColor;
    private float mBlurRadius;
    private float mScaleRatio;
    private long mAnimationDuration;
    private boolean mDismissOnTouchBackground = true;
    private boolean mDismissOnClickBack = true;
    private int mGravity = -1;
    private PopupWindow.OnDismissListener mOnDismissListener;

    public PopupWindowBuilder(Context context) {

        mContext = context;

        mBlurRadius = PopupWindow.DEFAULT_BLUR_RADIUS;
        mScaleRatio = PopupWindow.DEFAULT_SCALE_RATIO;
        mAnimationDuration = PopupWindow.DEFAULT_ANIMATION_DURATION;
    }

    public PopupWindowBuilder<T> setContentView(View contentView) {

        mContentView = contentView;
        return this;
    }

    public PopupWindowBuilder<T> setContentView(int resId) {

        View view = LayoutInflater.from(mContext).inflate(resId, new FrameLayout(mContext), false);
        mContentView = view;
        return this;
    }

    public PopupWindowBuilder<T> bindContentViewClickListener(View.OnClickListener listener) {

        if (mContentView != null) {
            mContentView.setClickable(true);
            mContentView.setOnClickListener(listener);
        }
        return this;
    }

    public PopupWindowBuilder<T> bindClickListener(View.OnClickListener listener, int... views) {

        if (mContentView != null) {
            for (int viewId : views) {
                View view = mContentView.findViewById(viewId);
                if (view != null) {
                    view.setOnClickListener(listener);
                }
            }
        }
        return this;
    }

    public PopupWindowBuilder<T> setGravity(int gravity) {

        mGravity = gravity;
        return this;
    }

    public PopupWindowBuilder<T> setTintColor(int tintColor) {

        mTintColor = tintColor;
        return this;
    }

    public PopupWindowBuilder<T> setScaleRatio(float scaleRatio) {

        if (scaleRatio <= 0 || scaleRatio > 1) {
            Log.w(TAG, "scaleRatio invalid: " + scaleRatio + ". It can only be (0, 1]");
            return this;
        }
        mScaleRatio = scaleRatio;
        return this;
    }

    public PopupWindowBuilder<T> setBlurRadius(float blurRadius) {

        if (blurRadius < 0 || blurRadius > 25) {
            Log.w(TAG, "blurRadius invalid: " + blurRadius + ". It can only be [0, 25]");
            return this;
        }
        mBlurRadius = blurRadius;
        return this;
    }

    public PopupWindowBuilder<T> setAnimationDuration(long animatingDuration) {

        if (animatingDuration < 0) {
            Log.w(TAG, "animatingDuration invalid: " + animatingDuration + ". It can only be (0, ..)");
            return this;
        }
        mAnimationDuration = animatingDuration;
        return this;
    }

    public PopupWindowBuilder<T> setDismissOnTouchBackground(boolean dismissOnTouchBackground) {

        mDismissOnTouchBackground = dismissOnTouchBackground;
        return this;
    }

    public PopupWindowBuilder<T> setDismissOnClickBack(boolean dismissOnClickBack) {

        mDismissOnClickBack = dismissOnClickBack;
        return this;
    }

    public PopupWindowBuilder<T> setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {

        mOnDismissListener = onDismissListener;
        return this;
    }

    protected T createPopupWindow() {
        //noinspection unchecked
        return (T) new PopupWindow(mContext);
    }

    public T build() {

        T popupWindow = createPopupWindow();
        if (mContentView != null) {
            ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
            if (layoutParams == null || !(layoutParams instanceof FrameLayout.LayoutParams)) {
                layoutParams = new FrameLayout.LayoutParams(layoutParams.width, layoutParams.height);
            }
            if (mGravity != -1) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = mGravity;
            }
            mContentView.setLayoutParams(layoutParams);
            popupWindow.setContentView(mContentView);
        }
        popupWindow.setTintColor(mTintColor);
        popupWindow.setAnimationDuration(mAnimationDuration);
        popupWindow.setBlurRadius(mBlurRadius);
        popupWindow.setScaleRatio(mScaleRatio);
        popupWindow.setDismissOnTouchBackground(mDismissOnTouchBackground);
        popupWindow.setDismissOnClickBack(mDismissOnClickBack);
        popupWindow.setOnDismissListener(mOnDismissListener);
        return popupWindow;
    }
}
