package cisc181.bustinbricks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.MainThread;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AnimatedView extends SurfaceView implements SurfaceHolder.Callback{
    private ProjectileThread projThread;
    private PlatformThread platThread;

    public AnimatedView(Context context) {
        super(context);
        getHolder().addCallback(this);
        projThread = new ProjectileThread(getHolder(), this);
        platThread = new PlatformThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        projThread.setRunning(true);
        projThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                projThread.setRunning(false);
                projThread.join();
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
            paint.setColor(Color.rgb(250, 0, 0));
            canvas.drawCircle(projThread.x, projThread.y, 20, paint);
            paint.setColor(Color.rgb(0,0,250));
            canvas.drawRect(platThread.rect, paint);
        }
    }
    public boolean onTouchEvent(MotionEvent e) {


        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
                float x = e.getX();
                platThread.x = x;
                break;
        }
        return true;
    }
    public void update() {
        projThread.update();

    }
}
