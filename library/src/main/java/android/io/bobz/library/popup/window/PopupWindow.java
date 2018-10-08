package android.io.bobz.library.popup.window;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.io.bobz.library.popup.window.utils.blur.BlurTask;
import android.io.bobz.library.popup.window.utils.blur.listener.BlurTaskCallback;
import android.os.Build;
import android.support.annotation.AnyThread;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.reflect.Method;

@SuppressWarnings("ALL")
public class PopupWindow extends FrameLayout {

    private static final String TAG = "PopupWindow";

    public static final float DEFAULT_BLUR_RADIUS = 10;
    public static final float DEFAULT_SCALE_RATIO = 0.4f;
    public static final long DEFAULT_ANIMATION_DURATION = 300;

    public interface OnDismissListener {

        void onDismiss(PopupWindow popupWindow);
    }

    private Activity mActivity;
    protected ImageView mBlurView;
    protected FrameLayout mContentLayout;
    private boolean mAnimating;

    private WindowManager mWindowManager;

    private View mContentView;
    private int mTintColor;
    private View mAnchorView;
    private float mBlurRadius;
    private float mScaleRatio;
    private long mAnimationDuration;
    private boolean mDismissOnTouchBackground;
    private boolean mDismissOnClickBack;
    private OnDismissListener mOnDismissListener;

    public PopupWindow(@NonNull Context context) {

        super(context);
        init();
    }

    private void init() {

        if (!(getContext() instanceof Activity)) {
            throw new IllegalArgumentException("Context must be Activity");
        }
        mActivity = (Activity) getContext();
        mWindowManager = mActivity.getWindowManager();

        mBlurRadius = DEFAULT_BLUR_RADIUS;
        mScaleRatio = DEFAULT_SCALE_RATIO;
        mAnimationDuration = DEFAULT_ANIMATION_DURATION;

        setFocusable(true);
        setFocusableInTouchMode(true);

        mContentLayout = new FrameLayout(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mContentLayout, lp);

        mBlurView = new ImageView(mActivity);
        mBlurView.setScaleType(ImageView.ScaleType.FIT_XY);
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.BOTTOM;
        mBlurView.setLayoutParams(lp);
        mContentLayout.addView(mBlurView);

        mContentView = createContentView(mContentLayout);
        if (mContentView != null) {
            mContentLayout.addView(mContentView);
        }
    }

    /**
     * Override this to create custom content.
     *
     * @param parent the parent where content view would be.
     * @return
     */
    protected View createContentView(ViewGroup parent) {

        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mAnimating || !mDismissOnTouchBackground) {
            return super.onTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            dismiss();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (mAnimating || !mDismissOnClickBack) {
            return super.onKeyUp(keyCode, event);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void setContentView(View contentView) {

        if (contentView == null) {
            throw new IllegalArgumentException("contentView can not be null");
        }
        if (mContentView != null) {
            if (mContentView.getParent() != null) {
                ((ViewGroup) mContentView.getParent()).removeView(mContentView);
            }
            mContentView = null;
        }
        mContentView = contentView;
        mContentLayout.addView(mContentView);
    }

    public View getContentView() {

        return mContentView;
    }

    public void show() {

        if (mAnimating) {
            return;
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.RGBA_8888;

        int statusBarHeight = 0;
        int navigationBarHeight = PopupWindow.getNaviHeight(mActivity);
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int trimTopHeight = statusBarHeight;
        int trimBottomHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            // No need to trim status bar height in SDK > 21.
            trimTopHeight = 0;

            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            if ((lp.flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) == 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                trimBottomHeight = navigationBarHeight;
            }

            // This line will cause decor view fill all the screen, even if FLAG_TRANSLUCENT_NAVIGATION
            // was not set.
            params.flags = lp.flags;

            if (trimBottomHeight > 0) {

                // If trimBottomHeight > 0, it means that we cut navigation bar off and we need shrink
                // popup windows' content height by increase bottom padding.
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom() + navigationBarHeight);
            } else {

                // If navigation is showing on the screen, whether translucent or not, we should move contentView
                // on top of it.
                boolean moveContent = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    moveContent = true;
                } else if (navigationBarHeight > 0 && (lp.flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) != 0) {
                    // Navigation feature diffs from v19 to v21.
                    moveContent = true;
                }
                if (navigationBarHeight > 0 && moveContent) {
                    if (mContentView != null) {
                        MarginLayoutParams layoutParams = (MarginLayoutParams) mContentView.getLayoutParams();
                        layoutParams.bottomMargin += navigationBarHeight;
                    }
                }
            }
        }

        new BlurTask(mActivity.getWindow().getDecorView(), trimTopHeight, trimBottomHeight, this, new BlurTaskCallback() {

            @Override
            public void onBlurFinish(Bitmap bitmap) {

                onBlurredImageGot(bitmap);
            }
        }).execute();

        mWindowManager.addView(this, params);

        ObjectAnimator showAnimator = createShowAnimator();
        if (showAnimator != null) {
            mAnimating = true;
            showAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationCancel(Animator animation) {

                    mAnimating = false;
                    requestFocus();
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    mAnimating = false;
                    requestFocus();
                }
            });
            showAnimator.start();
        }
        onShow();
    }

    public void dismiss() {

        if (mAnimating) {
            return;
        }
        onDismiss();
        ObjectAnimator animator = createDismissAnimator();
        if (animator == null) {
            mWindowManager.removeView(this);
        } else {
            mAnimating = true;
            ObjectAnimator.ofFloat(mBlurView, "alpha", mBlurView.getAlpha(), 0).setDuration(getAnimationDuration()).start();
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {

                    removeSelf();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                    removeSelf();
                }

                private void removeSelf() {

                    try {
                        mWindowManager.removeView(PopupWindow.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mAnimating = false;
                    }
                }
            });
            animator.start();
        }
    }

    protected void onBlurredImageGot(Bitmap bitmap) {

        mBlurView.setImageBitmap(bitmap);
        if (!mAnimating) {
            ObjectAnimator.ofFloat(mBlurView, "alpha", 0, 1f).setDuration(getAnimationDuration()).start();
        }
    }

    /**
     * When executing show method in this method, should override {@link PopupWindow#createShowAnimator()}
     * and return null as well.
     */
    protected void onShow() {

    }

    /**
     * Do not start any animation in this method. use {@link PopupWindow#createDismissAnimator()} instead.
     */
    @CallSuper
    protected void onDismiss() {

        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(this);
        }
    }

    protected ObjectAnimator createShowAnimator() {

        return ObjectAnimator.ofFloat(mContentLayout, "alpha", 0, 1.f).setDuration(getAnimationDuration());
    }

    protected ObjectAnimator createDismissAnimator() {

        return ObjectAnimator.ofFloat(mContentLayout, "alpha", mContentLayout.getAlpha(), 0).setDuration(getAnimationDuration());
    }

    public int getTintColor() {

        return mTintColor;
    }

    public void setTintColor(int tintColor) {

        mTintColor = tintColor;
    }

    public View getAnchorView() {

        return mAnchorView;
    }

    public void setAnchorView(View anchorView) {

        mAnchorView = anchorView;
    }

    @AnyThread
    public float getBlurRadius() {

        return mBlurRadius;
    }

    public void setBlurRadius(float blurRadius) {

        mBlurRadius = blurRadius;
    }

    @AnyThread
    public float getScaleRatio() {

        return mScaleRatio;
    }

    public void setScaleRatio(float scaleRatio) {

        mScaleRatio = scaleRatio;
    }

    public long getAnimationDuration() {

        return mAnimationDuration;
    }

    public void setAnimationDuration(long animationDuration) {

        mAnimationDuration = animationDuration;
    }

    public boolean isDismissOnTouchBackground() {

        return mDismissOnTouchBackground;
    }

    public void setDismissOnTouchBackground(boolean dismissOnTouchBackground) {

        mDismissOnTouchBackground = dismissOnTouchBackground;
    }

    public boolean isDismissOnClickBack() {

        return mDismissOnClickBack;
    }

    public void setDismissOnClickBack(boolean dismissOnClickBack) {

        mDismissOnClickBack = dismissOnClickBack;
    }

    public OnDismissListener getOnDismissListener() {

        return mOnDismissListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {

        mOnDismissListener = onDismissListener;
    }


    private static int getNaviHeight(Activity activity) {

        if (activity == null) {
            return 0;
        }
        Display display = activity.getWindowManager().getDefaultDisplay();
        int contentHeight = activity.getResources().getDisplayMetrics().heightPixels;
        int realHeight = 0;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            final DisplayMetrics metrics = new DisplayMetrics();
            display.getRealMetrics(metrics);
            realHeight = metrics.heightPixels;
        } else {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                realHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return realHeight - contentHeight;
    }

}
