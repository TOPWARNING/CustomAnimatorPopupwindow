package renhe.cn.demotestapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import jp.wasabeef.blurry.Blurry;


/**
 * Title: SharePopupWindow.java<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2014    <br>
 * Create DateTime: 2014-10-8 下午6:50:00 <br>
 *
 * @author wangning
 */
public class MenuPopupWindow extends PopupWindow {
    private Context ct;
    private View view;
    private View parent;
    private LinearLayout ll_popup;
    private AnimatorSet animSet;
    private int statusBarHeight;
    private ObjectAnimator animatorAlpha;
    private ObjectAnimator animatorScalex;
    private ImageView voerlyIv;

    @SuppressWarnings("deprecation")
    public MenuPopupWindow(Context mContext, View parent) {
        ct = mContext;
        view = View.inflate(mContext, R.layout.menu_popupwindows_layout, null);
        this.parent = parent;
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        voerlyIv = (ImageView) view.findViewById(R.id.overlay);
        setWidth(LayoutParams.FILL_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(-0000);
        setBackgroundDrawable(cd);
        //			setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        setupBlurView();
        view.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.ll_popup).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });

    }

    private void setupBlurView() {
        Blurry.with(ct)
                .radius(10)
                .sampling(8)
                .async()
                .capture(voerlyIv)
                .into((ImageView) voerlyIv);
    }

    public void show() {
        animatorAlpha = ObjectAnimator.ofFloat(ll_popup, "alpha", 0, 1, 1, 1);
        int animateHeight = ll_popup.getHeight();
        if (animateHeight <= 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            ll_popup.measure(w, h);
            animateHeight = ll_popup.getMeasuredHeight();
        }
        animatorScalex = ObjectAnimator.ofFloat(ll_popup, "translationY", -animateHeight, 30, -10, 0);

        animSet = new AnimatorSet();
        animSet.play(animatorAlpha).with(animatorScalex);
        animSet.setDuration(600);
        animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        Rect frame = new Rect();
        ((AppCompatActivity) ct).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        animSet.start();
        showAtLocation(parent, Gravity.TOP, 20,
                ((AppCompatActivity) ct).getSupportActionBar().getHeight() + statusBarHeight);
//        update();
    }

}
