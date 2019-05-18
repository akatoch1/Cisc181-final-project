package cisc181.bustinbricks;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class ProjectileThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private boolean running;
    public static Canvas canvas;
    private AnimatedView animatedView;
    public int x;
    public int y;
    private int xVelocity = 5;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public ProjectileThread(SurfaceHolder surfaceHolder, AnimatedView animatedView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.animatedView = animatedView;
        x = 425;
        y = 800;
    }
    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    public void update() {
        x += xVelocity;
        y += yVelocity;
        if (x >= screenWidth || x <= 0) {
            xVelocity *= -1;
        }
        if (y >= 1050 || y <= 0) {
            yVelocity *= -1;
        }
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
}
