package cisc181.bustinbricks;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Projectile {
    int x;
    int y;
    private int xVelocity = 5;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    public Projectile(int x1, int y1) {
        x = x1;
        y = y1;
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

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(250, 0, 0));
        canvas.drawCircle(x, y, 20, paint);
    }


}
