package cisc181.bustinbricks;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class PlatformThread extends Thread{
    public float x;
    public float y;
    public RectF rect;
    private int length;
    private int width;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    public static Canvas canvas;
    private AnimatedView animatedView;

    public PlatformThread(SurfaceHolder surfaceHolder, AnimatedView animatedView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.animatedView = animatedView;
        x = 375;
        y = 1050;
        length = 110;
        width = 20;
        rect = new RectF(x, y, x + length, y + width);
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
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

    public void update() {

    }
}