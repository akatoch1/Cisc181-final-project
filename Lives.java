package cisc181.bustinbricks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Lives {
    private Bitmap image;
    public int posx;
    public int posy;

    public Lives(Bitmap bmi, int x) {
        image = bmi;
        posx = x;
    }

    public void draw(Canvas canvas) {
        image = Bitmap.createScaledBitmap(image, canvas.getHeight()/17, canvas.getHeight()/17, false);
        canvas.drawBitmap(image, canvas.getWidth()/15 + posx, canvas.getHeight()/80, null);
       // Log.e("heyey", Integer.toString(canvas.getWidth()));
       // Log.e("yess", Integer.toString(canvas.getHeight()));
    }
}

