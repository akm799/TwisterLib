package uk.co.akm.twistertest.plot.function.impl;

import android.util.Log;
import android.view.View;

import uk.co.akm.twistertest.plot.function.FunctionData;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;
import uk.co.akm.twistertest.plot.view.FunctionView;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
final class FunctionViewPointsXY implements FunctionViewPoints {
    private static final String TAG = FunctionViewPointsXY.class.getSimpleName();

    private final float[] points;

    FunctionViewPointsXY(FunctionView view, FunctionData data) {
        if (data == null) {
            this.points = null;
            logPointsCreationError(view, data);
            return;
        }

        if (data.yOnly()) {
            throw new IllegalArgumentException("Error: FunctionData argument is null or y-type (i.e. yOnly()=true). This is not allowed in this x-y implementation.");
        }

        if (canPlotPoints(view, data)) {
            this.points = buildPoints(view, data);
        } else {
            logPointsCreationError(view, data);
            this.points = null;
        }
    }

    private boolean canPlotPoints(FunctionView view, FunctionData data) {
        // The data.values() array contains both the x and the y values, so the number of points is half the length of the array.
        return  (view != null && view.hasDimensions() && data.values() != null && data.values().length/2 < view.getContentWidth());
    }

    private float[] buildPoints(FunctionView view, FunctionData data) {
        final float[] points = new float[data.values().length];
        defineXPoints(view, data.values(), data.xMin(), data.xMax(), view.getContentWidth(),  points);
        defineYPoints(view, data.values(), data.yMin(), data.yMax(), view.getContentHeight(), points);

        return points;
    }

    private void defineXPoints(View functionView, float[] xyValues, float xMin, float xMax, int horizontalRange, float[] points) {
        final float scaleX = horizontalRange/(xMax - xMin);
        final int horizontalOffset = functionView.getPaddingTop();

        int j = 0;
        for (int i=0 ; i<xyValues.length/2 ; i++) {
            points[j] = horizontalOffset + scaleX*(xyValues[j] - xMin);
            j += 2;
        }
    }

    private void defineYPoints(View functionView, float[] xyValues, float yMin, float yMax, int verticalRange, float[] points) {
        final float scaleY = verticalRange/(yMax - yMin);
        final int verticalOffset = functionView.getPaddingTop() + verticalRange;

        int j = 1;
        for (int i=0 ; i<xyValues.length/2 ; i++) {
            points[j] = verticalOffset - scaleY*(xyValues[j] - yMin);
            j += 2;
        }
    }

    @Override
    public boolean yOnly() {
        return false;
    }

    @Override
    public boolean hasPoints() {
        return (points != null);
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

        final int nPoints = data.values().length/2; // The data.values() array contains both the x and the y values.
        if (nPoints >= view.getContentWidth()) {
            Log.e(TAG, "The points data passed contains " + nPoints + " points which is more than the available pixel width " + (view.getContentWidth() - 1) + ". No points will be plotted.");
        }
    }
}
