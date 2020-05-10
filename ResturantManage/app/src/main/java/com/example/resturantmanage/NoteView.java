package com.example.resturantmanage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;


//Custom view class allows user to scribble quick notes in
public class NoteView extends View {

    private Paint paint= new Paint();
    private Path path= new Path();

    public NoteView(Context context) {
        super(context);
        init(null,0);
    }

    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public NoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs,int defStyle){
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    //capture touch gestures
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float touchX = motionEvent.getX();
        float touchY= motionEvent.getY();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        invalidate();
        return true;

    }
}
