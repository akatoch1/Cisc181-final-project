package cisc181.bustinbricks;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class Brick {
    private RectF rect;
    private boolean isVisible;
    public static int scoreArea;



    public Brick(int row, int col, int width, int height) {
        isVisible = true;
        int padding = 1;
        Log.e("heye", Integer.toString(scoreArea));
        rect = new RectF(col * width + padding,
                row * height + padding + scoreArea,
                col * width + width - padding,
                scoreArea + row * height + height - padding);
    }

    public RectF getRect() {
        return this.rect;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }
}
