package com.islam.basepropject.project_base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.android.material.button.MaterialButton;
import com.islam.basepropject.R;

public class MyButton extends MaterialButton implements OnViewStatusChange {

    CircularProgressDrawable mProgressDrawable;
    SpannableString spannableString;
    String mButtonText = "";
    String mLoadingText = "";
    OnClickListener mOnClickListener;

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpCustomAttr(attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setUpCustomAttr(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MyButton,
                0, 0);

        try {
            mLoadingText = a.getString(R.styleable.MyButton_loadingText);
            if (mLoadingText == null)
                mLoadingText = getContext().getResources().getString(R.string.loading);
            mLoadingText += " ";
        } finally {
            a.recycle();
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        super.setOnClickListener(mOnClickListener);
    }

    private DynamicDrawableSpan initDrawableSpan() {
        DynamicDrawableSpan dynamicDrawableSpan = new DynamicDrawableSpan() {
            @Override
            public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fontMetricsInt) {
                Rect rect = mProgressDrawable.getBounds();

                Paint.FontMetrics fontMetrics = paint.getFontMetrics();

                // get a font height
                float lineHeight = fontMetrics.bottom - fontMetrics.top;

                //make sure our drawable has height >= font height
                float drHeight = Math.max(lineHeight, rect.bottom - rect.top);

                float centerY = fontMetrics.top + lineHeight / 2;

                //adjust font metrics to fit our drawable size
                if (fontMetricsInt != null) {
                    fontMetricsInt.ascent = (int) (centerY - drHeight / 2);
                    fontMetricsInt.descent = (int) (centerY + drHeight / 2);
                    fontMetricsInt.top = fontMetricsInt.ascent;
                    fontMetricsInt.bottom = fontMetricsInt.descent;
                }

                //return drawable width which is in our case = drawable width + margin from text
                return rect.width() + 16;
            }

            @Override
            public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                             @NonNull Paint paint) {
                canvas.save();
                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                // get font height. in our case now it's drawable height
                float lineHeight = fontMetrics.bottom - fontMetrics.top;

                // adjust canvas vertically to draw drawable on text vertical center
                float centerY = y + fontMetrics.bottom - lineHeight / 2;
                float transY = centerY - mProgressDrawable.getBounds().height() / 2;

                // adjust canvas horizontally to draw drawable with defined margin from text
                canvas.translate(x + 16, transY);

                mProgressDrawable.draw(canvas);

                canvas.restore();
            }

            @Override
            public Drawable getDrawable() {
                return mProgressDrawable;
            }
        };

        return dynamicDrawableSpan;
    }

    private void initProgressBar() {
        if (mProgressDrawable != null) return;
        mProgressDrawable = new CircularProgressDrawable(getContext());
        mProgressDrawable.setStyle(CircularProgressDrawable.DEFAULT);
        mProgressDrawable.setColorSchemeColors(Color.WHITE);
        int size = (int) ((mProgressDrawable.getCenterRadius() + mProgressDrawable.getStrokeWidth()) * 2);
        mProgressDrawable.setBounds(0, 0, size, size);

        mProgressDrawable.setCallback(new Drawable.Callback() {
            @Override
            public void invalidateDrawable(@NonNull Drawable who) {
                MyButton.this.invalidate();
            }

            @Override
            public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

            }

            @Override
            public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

            }
        });


        DynamicDrawableSpan dynamicDrawableSpan = initDrawableSpan();

        spannableString = new SpannableString(mLoadingText);
        //spannableString = new SpannableString("Loading");
        spannableString.setSpan(dynamicDrawableSpan, spannableString.length() - 1,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    private void startLoading() {
        initProgressBar();
        mButtonText = getText().toString();
        setText(spannableString);
        mProgressDrawable.start();
        super.setOnClickListener(null);

    }

    private void stopLoading() {
        mProgressDrawable.stop();
        setText(mButtonText);
        super.setOnClickListener(mOnClickListener);
    }

    @Override
    public void showLoading(boolean b) {
        if (b) {
            startLoading();
        } else {
            stopLoading();
        }

    }


}
