package cisc181.bustinbricks;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Projectile {
    int x;
    int y;
    int r;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    Random num = new Random();
    private int xVelocity = 5;
    private int yVelocity = 5;

    Brick[] bricks = new Brick[50];
    int numBricks = 0;
    int score = 0;
    int lives = 3;
    RectF ballRect;

    public Projectile(int x1, int y1) {
        x = x1;
        y = y1;
        r = 20;
        int brickWidth = screenWidth / 6;
        int brickHeight = screenHeight / 20;
        for(int column = 0; column < 6; column ++ ) {
            for(int row = 0; row < 3; row ++ ) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }
    public void update() {
        x += xVelocity;
        y += yVelocity;
        if (x >= screenWidth || x <= 0) {
            xVelocity *= -1;
        }
        if (y <= 0) {
            yVelocity *= -1;
        }

        ballRect = new RectF(x - 20, y + 20, x + 20, y - 20);
        for(int i = 0; i < numBricks; i++){
            if (bricks[i].getVisibility()){
                if(RectF.intersects(bricks[i].getRect(), ballRect)) {
                    if (ballRect.left < bricks[i].getRect().centerX()) {
                        xVelocity *= -1;
                        bricks[i].setInvisible();
                    }
                    if (ballRect.right > bricks[i].getRect().centerX()) {
                        xVelocity *= -1;
                        bricks[i].setInvisible();
                    }
                    else {
                        bricks[i].setInvisible();
                        yVelocity *= -1 ;
                        score++;
                    }

                }
            }
        }

    }



    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(250, 0, 0));
        canvas.drawCircle(x, y, r, paint);
    }


}
