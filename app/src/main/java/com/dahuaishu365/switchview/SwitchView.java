package com.dahuaishu365.switchview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lin82 on 2018/12/17.
 */

public class SwitchView extends View {

    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private int mSw_circle_width;
    private int mCircle_left;
    private int mMoveDistance;
    private float mMoveX;
    private float mMoveY;
    private float mDownX;
    private float mDownY;
    private boolean isClick;
    private boolean isPlaying;
    private boolean moveRight = true;//是否可以向右移动
    private int mAnimation_duration;//动画持续时间 默认500
    private int mOn_color;
    private int mOff_color;
    private int mCircle_color;
    private int mBg_color;
    private int mSw_circle_margin;

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySwitchView);
        mSw_circle_width = (int) typedArray.getDimension(R.styleable.MySwitchView_sw_circle_width, -1);
        mSw_circle_margin = (int) typedArray.getDimension(R.styleable.MySwitchView_sw_circle_margin, 0);
        mAnimation_duration = typedArray.getInt(R.styleable.MySwitchView_animation_duration, 500);
        mOn_color = typedArray.getColor(R.styleable.MySwitchView_on_color, Color.parseColor("#EA3385"));
        mBg_color = typedArray.getColor(R.styleable.MySwitchView_bg_color, Color.parseColor("#f5f5f5"));
        mOff_color = typedArray.getColor(R.styleable.MySwitchView_on_color, Color.parseColor("#D8D8D8"));
        mCircle_color = mOff_color;
        typedArray.recycle();
        init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        if (mSw_circle_width == -1) {
            mSw_circle_width = mMeasuredWidth / 2;
        }
        mMoveDistance = mMeasuredWidth - mSw_circle_width;
    }

    private void init() {

    }

    /**
     * 转到on
     */
    public void turnOn() {
        mCircle_left = mMoveDistance;
        moveRight = false;
        mCircle_color = mOn_color;
        if (l != null)
            l.turnOn();
        invalidate();

    }

    /**
     * 转到off
     */
    public void turnOff() {
        mCircle_left = 0;
        moveRight = true;
        mCircle_color = mOff_color;
        if (l != null)
            l.turnOff();
        invalidate();

    }
    /**
     * 转到on 带动画
     */
    public void turnOnWithAnimation() {
        moveRight = false;
        turnWithAnimation();
    }
    /**
     * 转到off 带动画
     */
    public void turnOffWithAnimation() {
        moveRight = true;
        turnWithAnimation();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(mBg_color);
        canvas.drawRoundRect(0, 0, mMeasuredWidth, mMeasuredHeight, mMeasuredHeight / 2, mMeasuredHeight / 2, paint);
        Paint paint1 = new Paint();
        paint1.setColor(mCircle_color);
        paint1.setAntiAlias(true);
        canvas.drawRoundRect(mCircle_left + mSw_circle_margin, mSw_circle_margin, mSw_circle_width + mCircle_left - mSw_circle_margin
                , mMeasuredHeight - mSw_circle_margin, mMeasuredHeight / 2, mMeasuredHeight / 2, paint1);
    }

    public interface OnTurnListener {

        void turnOff();

        void turnOn();
    }

    private OnTurnListener l;

    public void setOnTurnListener(OnTurnListener l) {
        this.l = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                isClick = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getX();
                mMoveY = event.getY();
                if (Math.abs(mDownX - mMoveX) > 10 || Math.abs(mDownY - mMoveY) > 10) {
                    isClick = false;
                } else {
                    isClick = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isPlaying) {
                    return true;
                }
                if (isClick) {
                    turnWithAnimation();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void turnWithAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mMoveDistance);
        animator.setDuration(mAnimation_duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircle_left = (int) animation.getAnimatedValue();
                if (!moveRight) {
                    mCircle_left = mMoveDistance - mCircle_left;
                }
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isPlaying = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isPlaying = false;
                if (mCircle_left == 0) {
                    moveRight = true;
                    mCircle_color = mOff_color;
                    if (l != null)
                        l.turnOff();
                } else {
                    moveRight = false;
                    mCircle_color = mOn_color;
                    if (l != null)
                        l.turnOn();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isPlaying = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
