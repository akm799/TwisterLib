package uk.co.akm.twistertest.plot.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import uk.co.akm.twistertest.R;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;

/**
 * Function view that plots a series of function points. Each point corresponds exactly to one screen pixel.
 * Consequently, this view is expected to plot functions with a sampling size which yields many more points
 * than the number of pixels reserved for the x-axis. The function can then be plotted by splitting the x-axis
 * in a number of bins which is equal to the number of pixels reserved for the horizontal axis. The value of
 * each bin will, then, represent exactly one pixel. This bin value will be the average of all sampling values
 * that make up the bin.
 *
 * Created by Thanos Mavroidis on 31/07/2017.
 */
public class FunctionView extends View {
    private static final String TAG = FunctionView.class.getSimpleName();

    private Paint plotPaint;
    private int plotColor = Color.BLACK;
    private float plotStrokeWidth = 2;

    private Paint borderPaint;
    private int borderColor = Color.BLACK;
    private float borderStrokeWidth = 1;

    private FunctionViewPoints viewPoints;

    public FunctionView(Context context) {
        super(context);
        init(null, 0);
    }

    public FunctionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FunctionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FunctionView, defStyle, 0);

        try {
            init(a);
        } finally {
            a.recycle();
        }
    }

    private void init(TypedArray a) {
        plotColor = a.getColor(R.styleable.FunctionView_plotColor, plotColor);
        plotStrokeWidth = a.getDimension(R.styleable.FunctionView_plotStrokeWidth, plotStrokeWidth);
        plotPaint = buildPaint(plotColor, plotStrokeWidth);

        borderColor = a.getColor(R.styleable.FunctionView_borderColor, borderColor);
        borderStrokeWidth = a.getDimension(R.styleable.FunctionView_borderStrokeWidth, borderStrokeWidth);
        borderPaint = buildPaint(borderColor, borderStrokeWidth);
    }

    private Paint buildPaint(int colour, float strokeWidth) {
        final Paint paint = new Paint();
        paint.setColor(colour);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);

        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (hasNoDimensions()) {
            return; // If the view has not been laid out yet, there is no point in proceeding.
        }

        drawBorder(canvas); // Always draw the square graph border, even if there is no graph to draw inside it.
        if (viewPoints != null) {
            if (viewPoints.yOnly()) {
                canvas.drawPoints(viewPoints.points(), plotPaint);
            } else {
                canvas.drawLines(viewPoints.points(), plotPaint);
            }
        }
    }

    private boolean hasNoDimensions() {
        return (getWidth() == 0 || getHeight() == 0);
    }

    private void drawBorder(Canvas canvas) {
        final int left = getPaddingLeft();
        final int top = getPaddingTop();
        final int right = getWidth() - getPaddingRight() - 1;
        final int bottom = getHeight() - getPaddingBottom() - 1;
        canvas.drawLine(left,  top,    right, top, borderPaint);
        canvas.drawLine(right, top,    right, bottom, borderPaint);
        canvas.drawLine(right, bottom, left,  bottom, borderPaint);
        canvas.drawLine(left,  bottom, left,  top, borderPaint);
    }

    /**
     * This method should be called after the view has already been laid out, in order to draw the
     * desirable function. Before this method call, only the square graph border is visible.
     *
     * @param viewPoints the function points to be drawn
     */
    public final void drawFunction(FunctionViewPoints viewPoints) {
        if (functionHasData(viewPoints) && hasDimensions()) {
            this.viewPoints = viewPoints;
            invalidate();
        }
    }

    private boolean functionHasData(FunctionViewPoints viewPoints) {
        return (viewPoints != null && viewPoints.points() != null && viewPoints.points().length > 0);
    }

    /**
     * Returns true if the view has already been laid out with non-zero width and height or false otherwise.
     */
    public final boolean hasDimensions() {
        return (getWidth() > 0 && getHeight() > 0);
    }

    /**
     * Returns, in pixels, the width of the space reserved for drawing the graph.
     */
    public final int getContentWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * Returns, in pixels, the height of the space reserved for drawing the graph.
     */
    public final int getContentHeight() {
        return getWidth() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * Clears the view by removing the previously drawn function, if any.
     */
    public final void clear() {
        viewPoints = null;
        invalidate();
    }
}
