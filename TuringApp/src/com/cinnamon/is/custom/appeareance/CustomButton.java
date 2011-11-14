package com.cinnamon.is.custom.appeareance;


import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.view.View;


public class CustomButton extends View{

	private final static int WIDTH_PADDING = 8;
	private final static int HEIGHT_PADDING = 10;
	private final String label;
	private final int imageResId;
	private final Bitmap image;
	

	/**
	 * Constructor.
	 * 
	 * @param context
	 *        Activity context in which the button view is being placed for.
	 * 
	 * @param resImage
	 *        Image to put on the button. This image should have been placed
	 *        in the drawable resources directory.
	 * 
	 * @param label
	 *        The text label to display for the custom button.
	 */
	public CustomButton(Context context, int resImage, String label){
		super(context);
	    this.label = label;
	    this.imageResId = resImage;
	    this.image = BitmapFactory.decodeResource(context.getResources(),
	    			imageResId);
	    setFocusable(true);
	    //setBackgroundColor();
	    
	    setClickable(true);
	}
	
	 /**
	* The method that is called when the focus is changed to or from this
	* view.
	*/
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect){
		if (gainFocus == true){
			this.setBackgroundColor(Color.rgb(255, 165, 0));
		}
		else{
			this.setBackgroundColor(Color.WHITE);
		}
	}
	
	/**
	* Method called on to render the view.
	*/
	protected void onDraw(Canvas canvas){
		Paint textPaint = new Paint();
	    textPaint.setColor(Color.BLACK);
	   
	   canvas.drawBitmap(image, WIDTH_PADDING /2 , HEIGHT_PADDING / 2, null);
	    /*canvas.drawText(label, WIDTH_PADDING / 2, (HEIGHT_PADDING / 2) +
	    		image.getHeight() + 8, textPaint);*/
	}
	
	 @Override
	 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){ 
		 setMeasuredDimension(measureWidth(widthMeasureSpec),
		 measureHeight(heightMeasureSpec));
	 }
		
	 private int measureWidth(int measureSpec){
		 int preferred = image.getWidth() * 2;
		 return getMeasurement(measureSpec, preferred);
	 }
	    
	 private int measureHeight(int measureSpec){
		 int preferred = image.getHeight() * 2;
		 return getMeasurement(measureSpec, preferred);
	 }
	 
	 private int getMeasurement(int measureSpec, int preferred){
		 int specSize = MeasureSpec.getSize(measureSpec);
		 int measurement = 0;
		 
		 switch(MeasureSpec.getMode(measureSpec)){
		 	case MeasureSpec.EXACTLY:
		 		// This means the width of this view has been given.
		 		measurement = specSize;
		 		break;
		 	case MeasureSpec.AT_MOST:
		 		// Take the minimum of the preferred size and what
		 		// we were told to be.
		 		measurement = Math.min(preferred, specSize);
		 		break;
		 	default:
		 		measurement = preferred;
		 		break;
		 }
		 return measurement;
	}
	 

	 
	 /**
	 * Returns the label of the button.
	 */
	 public String getLabel(){
		 return label;
	 }
	 /**
	 * Returns the resource id of the image.
	 */
	 public int getImageResId(){
	 	return imageResId;
	 }
	 
	/* public void setOrientation(Gravity g){
		 super.set
	 }
*/
	 
}
