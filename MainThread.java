package cisc181.bustinbricks;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private boolean running;
    public static Canvas canvas;
    private AnimatedView animatedView;
    public MainThread(SurfaceHolder surfaceHolder, AnimatedView animatedView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.animatedView = animatedView;

    }
    public void setRunning(boolean isRunning) {
        running = isRunning;
    }




    @Override
    public void run() {
        while (running) {
            canvas = this.surfaceHolder.lockCanvas();
            try {

                synchronized(surfaceHolder) {
                    this.animatedView.update();
                    this.animatedView.draw(canvas);
                }
            } catch (Exception e) {} finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
