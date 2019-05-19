package cisc181.bustinbricks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class AnimatedView extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread mainThread;
    private Platform platform;
    private Projectile projectile;
    boolean touchDown = false;
    boolean touchStarted = false;
    float touchX;
    ArrayList<RectF> bricks = new ArrayList<>();


    public AnimatedView(Context context) {
        super(context);
        getHolder().addCallback(this);
       mainThread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        projectile = new Projectile(425, 800);
        platform = new Platform(375, 1050);
        mainThread.setRunning(true);
        mainThread.start();

        //platThread.setRunning(true);
        //platThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
               // platThread.setRunning(false);
               // platThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            projectile.draw(canvas);
            platform.draw(canvas);
            paint.setColor(Color.rgb(0,255,0));
            for(int i = 0; i < projectile.numBricks; i++) {
                if(projectile.bricks[i].getVisibility()) {
                    canvas.drawRect(projectile.bricks[i].getRect(), paint);
                }
            }

        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            touchDown = true;
            touchStarted = true;     // finger JUST went down
            touchX = e.getX();

            platform.x = touchX;


        }

        // finger down and dragging

        else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            touchStarted = false;    // finger has been down for awhile now
            touchX = e.getX();
            platform.x = touchX;
        }

        // finger is up after being down

        else if (e.getAction() == MotionEvent.ACTION_UP) {
            touchDown = false;       // finger up
            touchStarted = false;    // so a touch cannot have just been started
        }

        // unrecognized motion event

        else {
            return false;
        }

        // do NOT force a redraw -- just wait for MyThread's next draw call

        return true;
    }
    public void update() {
        projectile.update();
        platform.update();
    }
}