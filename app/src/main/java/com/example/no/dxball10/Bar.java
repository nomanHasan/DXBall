package com.example.no.dxball10;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by no on 4/13/2016.
 */

public class Bar extends Drawable{
    public float left;
    public float top;
    public float right;
    public float bottom;
    public Paint paint;

    float height;
    float width;
    float x;
    float y;


    public Bar(float x, float y, float height, float width, Paint p){

        this.x =x;
        this.y = y;
        this.width = width;
        this.height = height;

        left = x - width/2;
        top = y - height /2;
        right = x + width/2;
        bottom = y + height/2;
        paint = p;
    }

    public void Draw(Canvas canvas, Paint paint){


        left = x - width/2;
        top = y - height /2;
        right = x + width/2;
        bottom = y + height/2;


        if(canvas==null || paint == null) return;
        canvas.drawRect(left,top,right,bottom,paint);
    }

    public float left(){
        return this.x - width/2;
    }

    public float right(){
        return this.x + width/2;
    }

    public float top(){
        return this.y - height/2;
    }


    public float bottom(){
        return this.y + height/2;
    }



}
