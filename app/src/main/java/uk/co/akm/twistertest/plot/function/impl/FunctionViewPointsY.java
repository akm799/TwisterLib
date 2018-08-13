package uk.co.akm.twistertest.plot.function.impl;

import android.util.Log;
import android.view.View;

import uk.co.akm.twistertest.plot.function.FunctionData;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;
import uk.co.akm.twistertest.plot.view.FunctionView;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
final class FunctionViewPointsY implements FunctionViewPoints {
    private static final String TAG = FunctionViewPointsY.class.getSimpleName();

    private final float[] points;

    FunctionViewPointsY(FunctionView view, FunctionData data) {
        if (data == null) {
            logPointsCreationError(view, data);
            this.points = null;
            return;
        }

        if (!data.yOnly()) {
            throw new IllegalArgumentException("Error: FunctionData argument is null or x-y-type (i.e. yOnly()=false). This is not allowed in this y-only implementation.");
        }

        if (canPlotPoints(view, data)) {
            this.points = buildPoints(view, data.values(), data.yMin(), data.yMax());
        } else {
            logPointsCreationError(view, data);
            this.points = null;
        }
    }

    private boolean canPlotPoints(FunctionView view, FunctionData data) {
        return (view != null && view.hasDimensions() && data.values() != null && data.values().length == view.getContentWidth());
    }

    private float[] buildPoints(FunctionView view, float[] values, float yMin, float yMax) {
        final float[] points = new float[2*view.getContentWidth()];
        definePoints(view, values, yMin, yMax, view.getContentHeight(), points);

        return points;
    }

    private void definePoints(View functionView, float[] values, float yMin, float yMax, int verticalRange, float[] points) {
        final float scaleY = verticalRange/(yMax - yMin);
        final int verticalOffset = functionView.getPaddingTop() + verticalRange;
        for (int i=0 ; i<values.length ; i++) {
            final int j = 2*i;
            points[j] = i;
            points[j + 1] = verticalOffset - scaleY*(values[i] - yMin);
        }
    }

    @Override
    public boolean hasPoints() {
        return (points != null);
    }

    @Override
    public boolean yOnly() {
        return true;
    }

    @Override
    public float[] points() {
        return points;
    }

    private void logPointsCreationError(FunctionView view, FunctionData data) {
        if (view == null) {
            Log.e(TAG, "Null view to plot the points passed. No points will be plotted.");
        }

        if (!view.hasDimensions()) {
            Log.e(TAG, "The view passed to plot the points has not been layout out yet. No points will be plotted.");
        }

        if (data == null) {
            Log.e(TAG, "Null points data passed. No points will be plotted.");
        }

        if (data.values() == null) {
            Log.e(TAG, "The points data passed contains null values. No points will be plotted.");
        }

        if (data.values().length >= view.getContentWidth()) {
            Log.e(TAG, "The points data passed contains " + data.values().length + " points which is more than the available pixel width " + (view.getContentWidth() - 1) + ". No points will be plotted.");
        }
    }
}
