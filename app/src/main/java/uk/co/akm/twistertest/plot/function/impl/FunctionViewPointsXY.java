package uk.co.akm.twistertest.plot.function.impl;

import android.view.View;

import uk.co.akm.twistertest.plot.function.FunctionData;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;
import uk.co.akm.twistertest.plot.view.FunctionView;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
final class FunctionViewPointsXY implements FunctionViewPoints {
    private final float[] points;

    FunctionViewPointsXY(FunctionView view, FunctionData data) {
        if (data == null) {
            this.points = null;
            return;
        }

        if (data.yOnly()) {
            throw new IllegalArgumentException("Error: FunctionData argument is null or y-type (i.e. yOnly()=true). This is not allowed in this x-y implementation.");
        }

        if (view != null && view.hasDimensions() && data.values() != null && data.values().length < view.getContentWidth()) {
            this.points = buildPoints(view, data);
        } else {
            this.points = null;
        }
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
    public float[] points() {
        return points;
    }
}
