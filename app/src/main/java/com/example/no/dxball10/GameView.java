package com.example.no.dxball10;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by no on 4/11/2016.
 */
public class GameView extends View {

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



    int defaultSleep=1;


    Stage stage = new Stage(1,0,false);

    public GameView(Context context){
        super(context);
        Log.d("GAMEVIEW Started ","TEST");

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

    public List<Box> GenerateBoxes(int size, Paint paint){
        List<Box> boxes;
        int row=0;
        int col=0;

        boxes = new ArrayList<>();

        int def=0;

        for(int i=0; i<size; i++){
            Random w = new Random();

            int randW = w.nextInt(1000)+20;
            int randH = w.nextInt(1000)+20;

            boxes.add(new Box(randW,randH,boxheight,boxWidth,paint));

            col+=  boxWidth;
            if(col >getWidth()-def){
                row+= 2 * boxheight ;
                def+=20;
                if(row>getHeight()/2){
                    row-=def;
                }
                if(col > getWidth()){
                    col+=def;
                }
            }
        }

        return boxes;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


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

            ball = new Ball(dx,dy+200,radius,paint);

            ball.XVELOCITY = XVELOCITY;
            ball.YVELOCITY = YVELOCITY;

            boxes = new ArrayList<>();

            int row=0;
            int col=0;


            int def=0;

            boxes = GenerateBoxes(300, paint);

            /*for(int i=0; i<200; i++){
                Random w = new Random();

                boxes.add(new Box(col,row,boxheight,boxWidth,paint));

                col+=  boxWidth;
                if(col >getWidth()-def){
                    row+= 2 * boxheight ;
                    col=def;
                    def+=1;
                    if(row>getHeight()/2){
                        row-=def;
                    }
                    if(col > getWidth()){
                        col+=def;
                    }
                }
            }*/

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
            boolean c = ball.DetectCollision(b, canvas);
            if(c)stage.score++;
        }



        if(stage.GAME_OVER){
            boxes = GenerateBoxes(new Random().nextInt(50)+1,paint);
            stage.GAME_OVER = false;
            ball.XVELOCITY = 10;
            ball.YVELOCITY = 10;
            ball.x = getWidth()/2;
            ball.y = getHeight()/2+200;
            defaultSleep = 1;
            stage.score=0;
            stage.stage=1;
        }

        if(ball.DEAD){
            paint.setTextSize(160);
            canvas.drawText("YOURE DEAD!",0,getHeight()/2,paint);
            stage.GAME_OVER = true;
            ball.DEAD = false;
            defaultSleep=2000;

        }
        if(stage.score > 0 && stage.score==boxes.size()){
            stage.stage++;
            ball.XVELOCITY = 10;
            ball.YVELOCITY = 10;
            ball.x = getWidth()/2;
            ball.y = getHeight()/2+200;
            stage.score=0;
            boxes = GenerateBoxes(new Random().nextInt(100)+1,paint);
        }



        ball.DetectCollision(bar, canvas);

        //Updating the Canvas
        ball.update(getWidth(),getHeight());

        paint.setTextSize(48);
        canvas.drawText("SCORE : "+stage.score,20,60,paint);
        canvas.drawText("STAGE : "+stage.stage,600,60,paint);


        try{
            Thread.sleep(defaultSleep);
        }catch (Exception e){
            e.printStackTrace();
        }


        //Redraw
        invalidate();

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






//
//            if(this.x+radius >= bar.x + bar.width && this.x+radius <radius){
//                this.XVELOCITY = -this.XVELOCITY;
//            }
//
//            if(this.y>=y - radius || ball.y <radius){
//                this.YVELOCITY = -this.YVELOCITY;
//            }



}

