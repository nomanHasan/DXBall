package com.example.no.dxball10;

/**
 * Created by no on 4/13/2016.
 */
public class Misc {
    public static float limit(float value, float min, float max){
        if(value > max){
            return max;
        }else if(value < min){
            return min;
        }else{
            return value;
        }
    }

    public static float distance(float x1, float y1, float x2,float y2){
        return (float)Math.sqrt(Math.pow(x2 -x1,2) + Math.pow(y2 - y1,2));
    }
}
