package cisc181.bustinbricks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class AnimatedView extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread mainThread;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Platform platform;
    Brick[] bricks = new Brick[50];
    int numBricks = 0;
    private Projectile projectile;
    boolean touchDown = false;
    boolean touchStarted = false;
    float touchX;
    private Lives life1;
    private Lives life2;
    private Lives life3;

    ArrayList<Lives> lives = new ArrayList<>();

//scaled bitmap
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
        int brickWidth = screenWidth / 6;
        int brickHeight = screenHeight / 20;
        Brick.scoreArea = screenHeight /16;
        for(int column = 0; column < 6; column ++ ) {
            for(int row = 0; row < 3; row ++ ) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
        projectile = new Projectile(screenWidth/2, (screenHeight*2)/3);
        platform = new Platform(GameActivity.xScreen, GameActivity.yScreen);
        life1 = new Lives(BitmapFactory.decodeResource(getResources(),R.drawable.heart), 10);
        life2 = new Lives(BitmapFactory.decodeResource(getResources(),R.drawable.heart), 90);
        life3 = new Lives(BitmapFactory.decodeResource(getResources(),R.drawable.heart), 170);
        lives.add(life1);
        lives.add(life2);
        lives.add(life3);
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

            for(int i = 0; i < numBricks; i++) {
                if(bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }
            for (int i = 0; i < lives.size(); i++) {
                lives.get(i).draw(canvas);
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
        RectF ballRect = new RectF(projectile.x, projectile.y, projectile.x, projectile.y );
        for(int i = 0; i < numBricks; i++){
            if (bricks[i].getVisibility()){
                if(RectF.intersects(bricks[i].getRect(), ballRect)) {
                    if ((ballRect.bottom > bricks[i].getRect().top) && (ballRect.left > bricks[i].getRect().left) && (ballRect.right < bricks[i].getRect().right)) {
                        projectile.yVelocity *= -1;
                        bricks[i].setInvisible();
                        Log.e("hello", "ye");

                    }
                    else if ((ballRect.top < bricks[i].getRect().bottom)  && (ballRect.left > bricks[i].getRect().left) && (ballRect.right < bricks[i].getRect().right)) {
                        projectile.yVelocity *= -1;
                        bricks[i].setInvisible();
                        Log.e("heye", "ye");

                    }
                    else {
                        projectile.xVelocity *= -1 ;
                        bricks[i].setInvisible();
                    }
                    projectile.score++;
                }
            }
        }
        if(RectF.intersects(platform.getRect(), ballRect)){
            projectile.yVelocity*=-1;
        }
        if (projectile.y >= GameActivity.yScreen) {
            lives.remove(lives.size() - 1);
            projectile.x = screenWidth/2;
            projectile.y = (screenHeight*2)/3;
        }
        projectile.update();
        platform.update();
    }
}
