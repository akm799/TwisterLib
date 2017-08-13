package uk.co.akm.twistertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import uk.co.akm.twistertest.hist.SensorValueHistogram;


/**
 * TODO: document your custom view class.
 */
@Deprecated
public class HistogramView extends View {
    private static final String TAG = HistogramView.class.getSimpleName();

    private Paint paint;
    private SensorValueHistogram histogram;

    public HistogramView(Context context) {
        super(context);
        init();
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xff000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (hasNoDimensions()) {
            Log.e(TAG, "Error: Histogram view has no dimensions when drawn.");
            return;
        }

        if (histogram == null || histogram.heights == null || histogram.heights.length == 0) {
            Log.e(TAG, "Error: Histogram view has no values when drawn.");
            return;
        }

        final int w = getWidth();
        for (int i=0 ; i<w ; i++) {
            canvas.drawPoint(i, histogram.heights[i], paint);
        }
    }

    public final boolean hasDimensions() {
        return (getWidth() > 0 && getHeight() > 0);
    }

    public final boolean hasNoDimensions() {
        return (getWidth() == 0 || getHeight() == 0);
    }
}
