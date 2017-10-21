/*
 * This is the source code of DMAudioStreaming for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry(dibakar.ece@gmail.com), 2017.
 */
package dm.audiostreamerdemo.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class LineProgress extends View {

    public int progress;
    public Paint paint = new Paint();

    public LineProgress(Context context) {
        super(context);
    }

    public LineProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, this.progress, this.getHeight(), paint);
    }

    public void setLineProgress(int pg) {
        int wdt = this.getWidth();
        this.progress = pg * (wdt / 100);
        invalidate();
    }
}


