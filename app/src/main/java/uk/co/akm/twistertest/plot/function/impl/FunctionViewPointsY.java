package uk.co.akm.twistertest.plot.function.impl;

import android.view.View;

import uk.co.akm.twistertest.plot.function.FunctionData;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;
import uk.co.akm.twistertest.plot.view.FunctionView;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
final class FunctionViewPointsY implements FunctionViewPoints {
    private final float[] points;

    FunctionViewPointsY(FunctionView view, FunctionData data) {
        if (data == null) {
            this.points = null;
            return;
        }

        if (!data.yOnly()) {
            throw new IllegalArgumentException("Error: FunctionData argument is null or x-y-type (i.e. yOnly()=false). This is not allowed in this y-only implementation.");
        }

        if (view != null && view.hasDimensions() && data.values() != null && data.values().length == view.getContentWidth()) {
            this.points = buildPoints(view, data.values(), data.yMin(), data.yMax());
        } else {
            this.points = null;
        }
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
    public boolean yOnly() {
        return true;
    }

    @Override
    public float[] points() {
        return points;
    }
}
