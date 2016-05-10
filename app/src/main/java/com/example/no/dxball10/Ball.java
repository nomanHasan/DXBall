package com.example.no.dxball10;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by no on 4/13/2016.
 */

public class Ball extends Drawable{
    public float x;
    public float y;
    public float radius;

    Paint paint;

    public float XVELOCITY;
    public float YVELOCITY;

    public float VELOCITY = 0;

    public float angle;

    public float legitX = 0;
    public float legitY = 0;

    public boolean DEAD = false;


    public Ball(float x, float y, float r, Paint p) {

        XVELOCITY = VELOCITY;
        YVELOCITY = VELOCITY;


        this.x = x;
        this.y = y;
        this.radius = r;
        this.paint = p;
    }

    public void Draw(Canvas canvas, Paint paint) {

        if (canvas == null || paint == null) return;
        canvas.drawCircle(x, y, radius, paint);
    }


    public void update(int x, int y){

        this.x+=this.XVELOCITY;
        this.y+=this.YVELOCITY;

        if(this.x >=x - radius || this.x <radius){
            this.XVELOCITY = -this.XVELOCITY;
        }

        if(this.y>=y - radius){
            this.YVELOCITY = 0;
            this.XVELOCITY = 0;
            this.DEAD = true;
        }else if(this.y <radius){
            this.YVELOCITY = - this.YVELOCITY;
        }

    }

    public boolean DetectCollision(Bar bar, Canvas canvas) {

        float closestX = Misc.limit(this.x, bar.x - bar.width / 2, bar.x + bar.width / 2);
        float closestY = Misc.limit(this.y, bar.y - bar.height / 2, bar.y + bar.height / 2);

        float distanceX = this.x - closestX;
        float distanceY = this.y - closestY;

        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        if (distanceSquared < (this.radius * this.radius)) {
            if (distanceX < distanceY) {
                this.XVELOCITY = -this.XVELOCITY;

            } else if (distanceX > distanceY) {
                this.YVELOCITY = -this.YVELOCITY;

            } else {
                this.XVELOCITY = -this.XVELOCITY;
                this.YVELOCITY = -this.YVELOCITY;

            }
        }

        canvas.drawLine(this.x, this.y, closestX, closestY, paint);

        return true;
    }

    public boolean DetectCollision(Box bar, Canvas canvas) {

        float closestX = Misc.limit(this.x, bar.x - bar.width / 2, bar.x + bar.width / 2);
        float closestY = Misc.limit(this.y, bar.y - bar.height / 2, bar.y + bar.height / 2);

        float distanceX = this.x - closestX;
        float distanceY = this.y - closestY;

        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

        if (distanceSquared < (this.radius * this.radius)) {
            Log.d("COLLIDED ","TEST");
            if (distanceX < distanceY) {
                resetPosition();
                this.XVELOCITY = -this.XVELOCITY;
                bar.COLLIDED = true;
            } else if (distanceX > distanceY) {
                resetPosition();
                this.YVELOCITY = -this.YVELOCITY;
                bar.COLLIDED = true;
            } else {
                resetPosition();
                this.XVELOCITY = -this.XVELOCITY;
                this.YVELOCITY = -this.YVELOCITY;
                bar.COLLIDED = true;
             }
        }else{
            legitX = this.x;
            legitY = this.y;
            bar.COLLIDED=false;
        }

//        if(Misc.distance(this.x,this.y,closestX,closestY) <= radius){
//            this.XVELOCITY = -this.XVELOCITY;
//            this.YVELOCITY = -this.YVELOCITY;
//            bar.COLLIDED = true;


//            if(closestY == bar.y-bar.height/2 || closestY == bar.y+bar.height/2){
//                this.XVELOCITY = -this.XVELOCITY;
//            }else if(closestX == bar.x-bar.width/2 || closestX == bar.x+bar.width/2){
//                this.YVELOCITY =-this.YVELOCITY;
//            }else{
//
//            }
//        }

        canvas.drawLine(this.x, this.y, closestX, closestY, paint);

        return bar.COLLIDED;
    }

    public boolean DetectCollision(Box box) {

        boolean col = false;

        if (this.top() > box.bottom() && this.bottom() > box.top() && this.left() < box.right() && this.right() > box.left()) {
            XVELOCITY = -XVELOCITY;
            col = true;
        } else if (this.top() < box.bottom() && this.bottom() > box.top() && this.left() < box.right() && this.right() > box.left()) {
            XVELOCITY = -XVELOCITY;
            col = true;
        }else if(this.right() > box.left() && this.left() < box.right() && this.top() < box.bottom() && this.bottom() > box.top()){
            YVELOCITY =-YVELOCITY;
            col = true;
        }else if(this.right() > box.left() && this.left() > box.right() && this.top() < box.bottom() && this.bottom() > box.top()){
            YVELOCITY =-YVELOCITY;
            col = true;
        }

        if(col){
            box.COLLIDED = true;
        }

        return col;
    }


    public float left(){
        return this.x - radius;
    }

    public float right(){
        return this.x + radius;
    }

    public float top(){
        return this.y - radius;
    }


    public float bottom(){
        return this.y + radius;
    }

    public void resetPosition(){
        this.x = legitX;
        this.y = legitY;
    }
}
