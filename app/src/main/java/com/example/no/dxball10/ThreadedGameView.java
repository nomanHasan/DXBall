package com.example.no.dxball10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by no on 5/5/2016.
 */
class ThreadedGameView extends SurfaceView implements Runnable{


    Thread gameThread = null;

    SurfaceHolder ourHolder;

    volatile boolean playing;


    Canvas canvas;
    Paint paint;

    long fps;

    private long timeThisFrame;

    Bitmap bitmapBob;

    boolean isMoving = false;

    float walkSpeedPerSecond = 150;

    float bobXPosition = 10;


    int x=0;
    int y=0;


    float dx=0;
    float dy=0;

    boolean firstTime = true;


    float XVELOCITY = 10;
    float YVELOCITY = 10;

    float radius = 50;

    Bar bar = null;
    Ball ball = null;
    List<Box> boxes;

    float boxWidth=50;
    float boxheight=20;



    public ThreadedGameView(Context context){

        super(context);

        ourHolder = getHolder();
        paint = new Paint();


        this.setOnTouchListener(new touchHandler());
    }

    public class touchHandler implements OnTouchListener{


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(bar!=null){
                if(event.getX() < x/2){
                    bar.x  = event.getX();
                }else{
                    bar.x  = event.getX();
                }

            }
            return true;
        }


    }

    @Override
    public void run() {
        while (playing){

            long startFrameTime = System.currentTimeMillis();



            draw();
            update();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;

            if(timeThisFrame > 0){
                fps = 1000/ timeThisFrame;
            }
        }


    }

    public void update(){

        ball.x+=ball.XVELOCITY;
        ball.y+=ball.YVELOCITY;

        if(ball.x >=x - radius || ball.x <radius){
            ball.XVELOCITY = -ball.XVELOCITY;
        }

        if(ball.y>=y - radius || ball.y <radius){
            ball.YVELOCITY = -ball.YVELOCITY;
        }
    }


    public void draw(){

        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();

            Paint paint = new Paint();

            if(firstTime){
                x = getWidth();
                y = getHeight();

                dx = x/2;
                dy=y/2;

                firstTime = false;

                paint.setColor(Color.parseColor("#663300"));

                bar = new Bar(x/2 - 100,y-50,50, 200, paint);

                paint.setColor(Color.parseColor("#0669AC"));

                ball = new Ball(dx,dy,radius,paint);

                ball.XVELOCITY = XVELOCITY;
                ball.YVELOCITY = YVELOCITY;

                boxes = new ArrayList<>();

                int row=0;
                int col=0;


                int def=0;

                for(int i=0; i<200; i++){
                    Random r = new Random();
                    boxes.add(new Box(col,row,boxheight,boxWidth,paint));

                    col+= 2 * boxWidth;
                    if(col >getWidth()-def){
                        row+= 2 * boxheight ;
                        col=def;
                        def+=21;
                        if(row>getHeight()/2){
                            row-=def;
                        }
                        if(col > getWidth()){
                            col+=def;
                        }
                    }
                }

            }

            int i=0;
        /*for(Box b:boxes){
            if(b.COLLIDED) {
                //boxes.remove(b);
                Log.d(i + "BOX DELETED", "TEST");

            }
            i++;
        }*/
            Log.d("DELETETION Complete","TEST");

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#AD5DAC"));

            paint.setColor(Color.GREEN);

            paint.setColor(Color.BLUE);
            ball.Draw(canvas,paint);

            paint.setColor(Color.GREEN);
            bar.Draw(canvas,paint);

            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            for(Box b:boxes){

                if(b.COLLIDED)continue;

                b.Draw(canvas,paint);
                ball.DetectCollision(b, canvas);


            }




            ball.DetectCollision(bar, canvas);

            //Updating the Canvas
            ball.update(getWidth(),getHeight());


            try{
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }


            //Redraw
            invalidate();


            ourHolder.unlockCanvasAndPost(canvas);

        }
    }



    public void pause(){

        playing = false;

        try{
            gameThread.join();
        }catch (Exception e){

        }

    }

    public void resume(){

        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}