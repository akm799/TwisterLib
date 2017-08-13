package uk.co.akm.twistertest.plot.function;


import android.view.View;

import uk.co.akm.twistertest.plot.view.FunctionView;

public final class FunctionPlotValues {
    /**
     * The points that will be drawn to plot the function. These array holding these points follows
     * the usual convention: [x0, y0, x1, y1, x2, y2, ..., xN, yN]. Thus the array must hold 2*N values
     * where N is the number of points. Please note that if N is no equal to #getContentWidth(), then
     * this method will fail silently.
     */
    public final float[] points;

    public FunctionPlotValues(FunctionView view, FunctionData data) {
        if (view.hasDimensions() && data.values != null && data.values.length == view.getContentWidth()) {
            this.points = buildPoints(view, data.values, data.yMin, data.yMax);
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
}
