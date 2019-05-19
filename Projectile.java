package cisc181.bustinbricks;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Projectile {
    int x;
    int y;
    int r;
    private int xVelocity = 5;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public RectF ballRect;
    Brick[] bricks = new Brick[50];
    int numBricks = 0;
    int score = 0;
    int lives = 3;
    Projectile proj;

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
        if (y >= 1050 || y <= 0) {
            yVelocity *= -1;
        }

        proj = new Projectile(x, y);
        for(int i = 0; i < numBricks; i++){
            if (bricks[i].getVisibility()){
                if(intersect(bricks[i].getRect(), proj)) {
                    bricks[i].setInvisible();
                    yVelocity *= -1 ;
                    score++;
                }
            }
        }

    }

    public boolean intersect(RectF rect, Projectile projectile){

        if((Math.abs(projectile.x -rect.left)< projectile.r || Math.abs(projectile.x -rect.right)< projectile.r ))
        {
            if (Math.abs(projectile.y-rect.top)<=projectile.r)//Top right or Top left
                return true;
            else if(Math.abs(projectile.y-rect.bottom)<=projectile.r)//Bottom right or bottom left
                return true;
            else if((projectile.x-rect.top)<=0 && (projectile.x-rect.bottom)>=0)//Ball has hit the middle of the brick on the right or on the left
                return true;
            else return false;
        }


        return false;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(250, 0, 0));
        canvas.drawCircle(x, y, r, paint);
    }


}
