package uk.co.akm.twistertest.plot.function;

/**
 * The function data to be plotted. The function points sampled must be greater than the pixel width of the plot.
 *
 * Created by Thanos Mavroidis on 01/08/2017.
 */
public final class FunctionData {
    /**
     * The minimum x-value of the function.
     */
    public final float xMin;

    /**
     * The maximum x-value of the function.
     */
    public final float xMax;

    /**
     * The minimum y-value of the function.
     */
    public final float yMin;

    /**
     * The maximum y-value of the function.
     */
    public final float yMax;

    /**
     * The y-values of the function.
     */
    public final float[] values;

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
     * @param values the y-values of the function
     */
    public FunctionData(float xMin, float xMax, float[] values) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.values = values;
        this.yMin = findMin(values);
        this.yMax = findMax(values);
    }

    private float findMin(float[] values) {
        float min = Float.MAX_VALUE;
        for (float v : values) {
            if (v < min) {
                min = v;
            }
        }

        return min;
    }

    private float findMax(float[] values) {
        float max = Float.MIN_VALUE;
        for (float v : values) {
            if (v > max) {
                max = v;
            }
        }

        return max;
    }
}
