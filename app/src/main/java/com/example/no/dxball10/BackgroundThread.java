package com.example.no.dxball10;

import android.graphics.Canvas;

/**
 * Created by no on 4/18/2016.
 */
public class BackgroundThread extends Thread {
    private GameView view;
    private boolean running = false;

    public BackgroundThread(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while (running) {

        }
    }
}