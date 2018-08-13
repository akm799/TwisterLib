package uk.co.akm.twistertest.plot.function.impl;

import uk.co.akm.twistertest.plot.function.FunctionData;

/**
 * The function data to be plotted. The function points sampled must be greater than the pixel width of the plot.
 *
 * Created by Thanos Mavroidis on 01/08/2017.
 */
final class FunctionDataY implements FunctionData {
    private final float xMin;
    private final float xMax;
    private final float[] yValues;
    private final MinMaxFinder.MinMax yMinMax;

    /**
     * The input values of the function to be plotted. Please note that for this plot, the number of
     * y-values must be exactly equal to the number of pixels reserved for the width of the plot.
     * This means that a single value represents a single x-y point, which will correspond to a single
     * pixel. Consequently, this data structured will be used for plots where the sampling size along
     * the x-axis yields a number of values which is greater than the pixel width of the graph. Thus,
     * the function is partitioned into a number of bins and each bin value will be the average of the
     * sampled points contained in the bin. The number of bins must be exactly equal to the number of
     * pixels measuring the width of the graph.
     *
     * @param xMin the minimum x-value of the function
     * @param xMax the maximum x-value of the function
     * @param yValues the y-values of the function
     */
    FunctionDataY(float xMin, float xMax, float[] yValues) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yValues = yValues;
        this.yMinMax = MinMaxFinder.findMinAndMax(yValues);
    }

    @Override
    public float xMin() {
        return xMin;
    }

    @Override
    public float xMax() {
        return xMax;
    }

    @Override
    public float yMin() {
        return yMinMax.min;
    }

    @Override
    public float yMax() {
        return yMinMax.max;
    }

    @Override
    public boolean yOnly() {
        return true;
    }

    @Override
    public float[] values() {
        return yValues;
    }
}
