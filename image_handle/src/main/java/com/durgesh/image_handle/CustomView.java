package com.durgesh.image_handle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


/**
 * Created by Durgesh on 5/7/2018.
 */

@SuppressLint("AppCompatCustomView")
public class CustomView extends ImageView {

    int mTextColor,mBorderColor,mPressColor,mFillColor;
    int imagId;
    float mTextSize,mBorderWidth;

    Paint backgroundPaint,textPaint,mBorderPaint,mPressPaint,mPaint;
    String mText="NA";

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    //region Default Variables
    private final static int defaultBorderColor=Color.parseColor("#808080");
    private final static int defaultFillColor=Color.parseColor("#000000");
    private final static int defaultTextColor=Color.parseColor("#ffffff");
    private final static int defaultPressColor=Color.parseColor("#616161");
    private static final String NA = "NA" ;
    private final static int defaultBorderWidth=5;
    private final static int HALF = 2;
    private final static int ONE = 1;
    private final static String SPACE = " ";
    private final static int ZERO = 0;
    private final static int TEN = 10;
    //endregion




    Bitmap mBitmap;
    BitmapShader mBitmapShader;
    /**
     * {@link #init(Context, AttributeSet)}
     * */
    public CustomView(Context context) {
        super(context);
        init(context,null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @SuppressLint("NewApi")
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(@NonNull Context context,@Nullable AttributeSet attrs){
        if(attrs==null)
            return;




        //region fetchValue to Xml
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CustomView,0,0);
        mBorderColor=typedArray.getColor(R.styleable.CustomView_borderColor, defaultBorderColor );
        mPressColor=typedArray.getColor(R.styleable.CustomView_borderColor, defaultPressColor );
        mFillColor=typedArray.getColor(R.styleable.CustomView_fillColor, defaultFillColor );
        mTextColor=typedArray.getColor(R.styleable.CustomView_textColor, defaultTextColor );
        imagId=typedArray.getResourceId(R.styleable.CustomView_fillImage,defaultBorderWidth);
        mText=typedArray.getString(R.styleable.CustomView_text );
        mBorderWidth=typedArray.getDimension(R.styleable.CustomView_borderWidth,defaultBorderWidth);
        //endregion

        //region Text
        if (mText != null) {
           mText= mText.trim().toUpperCase();
            if(mText.length()>HALF){
                if(mText.contains(SPACE))
                {
                    try {
                        String[] arr = mText.split(SPACE);
                        mText = arr[ZERO].substring(ZERO, ONE) + arr[1].substring(ZERO, ONE);
                    }
                    catch (Exception e){
                        mText= mText.substring(ZERO,HALF);
                    }
                }else
               mText= mText.substring(ZERO,HALF);
            }

        }else {
            mText= NA;
        }
        //endregion

        //region initialize Paint
        backgroundPaint=new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);
       // backgroundPaint.setStrokeWidth(5);
        backgroundPaint.setColor(mFillColor);


        mPressPaint=new Paint();
        mPressPaint.setAntiAlias(true);
        mPressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPressPaint.setStrokeWidth(defaultBorderWidth);
        mPressPaint.setColor(mPressColor);

        mBorderPaint=new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setColor(mBorderColor);



         if(imagId!=defaultBorderWidth) {
            mBitmap = BitmapFactory.decodeResource(getResources(), imagId);
            if(mBitmap!=null) {
                mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                mPaint = new Paint();
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setShader(mBitmapShader);
            }
        }


        textPaint=new Paint();
        textPaint.setColor(mTextColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //endregion




        typedArray.recycle();
    }


    private static final int TWENTY_FOUR = 24 ;

    @Override
    protected void onDraw(Canvas canvas) {




        canvas.drawCircle(getMeasuredWidth()/HALF,getMeasuredHeight()/HALF,(getMeasuredWidth()*TEN)/TWENTY_FOUR,mBorderPaint);


        canvas.drawCircle((getMeasuredWidth()/HALF),(getMeasuredHeight()/HALF),(getMeasuredWidth()*TEN)/TWENTY_FOUR-mBorderWidth,backgroundPaint);
        if(mPaint!=null)
        canvas.drawCircle((getMeasuredWidth()/HALF),(getMeasuredHeight()/HALF),(getMeasuredWidth()*TEN)/TWENTY_FOUR-mBorderWidth,mPaint);


        textPaint.setTextSize((getMeasuredWidth()*TEN)/23);


        if(isPressed())
            canvas.drawCircle((getMeasuredWidth() / HALF), (getMeasuredHeight() / HALF), (getMeasuredWidth() * TEN) / TWENTY_FOUR - mBorderWidth, backgroundPaint);


        RoundedBitmapDrawable roundedBitmapDrawable = getDD();
        if(roundedBitmapDrawable!=null)
            roundedBitmapDrawable.draw(canvas);
        else
            canvas.drawText(mText,getMeasuredWidth()/HALF,(getMeasuredHeight()*TEN)/15,textPaint);



        super.onDraw(canvas);


    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        postInvalidate();
    }

    private RoundedBitmapDrawable getDD(){
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), mBitmap);
        if(mBitmap!=null) {
            final float roundPx = (float) mBitmap.getWidth() * 0.06f;
            roundedBitmapDrawable.setCornerRadius(roundPx);
            return roundedBitmapDrawable;
        }
        return null;


    }

    @Override
    public int getMaxWidth() {
        return super.getMaxWidth();
    }



    private static final int COLORDRAWABLE_DIMENSION = 2;


}
