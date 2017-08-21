package uk.co.akm.twistertest.plot.function;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
public interface FunctionData {

    /**
     * @return The minimum x-value of the function
     */
    float xMin();

    /**
     * @return The maximum x-value of the function
     */
    float xMax();

    /**
     * @return The minimum y-value of the function
     */
    float yMin();

    /**
     * @return The maximum y-value of the function
     */
    float yMax();

    /**
     * @return true of the {@link #values()} method returns only the y-values of the function
     * (i.e. [y0, y1, ...]) or false if it returns a set of points in the format [x0, y0, x1, y1, ...]
     */
    boolean yOnly();

    /**
     * @return The points of the function interpreted according to the result of the {@link #yOnly()} call
     */
    float[] values();
}
