package uk.co.akm.twistertest.plot.function.impl;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */

public class MinMaxFinder {

    static MinMax findMinAndMax(float[] values) {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (float v : values) {
            if (v < min) {
                min = v;
            }

            if (v > max) {
                max = v;
            }
        }

        return new MinMax(min, max);
    }

    static MinMax findMinAndMax(FloatIterable values) {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        while (values.hasNext()) {
            final float v = values.next();

            if (v < min) {
                min = v;
            }

            if (v > max) {
                max = v;
            }
        }

        return new MinMax(min, max);
    }

    private MinMaxFinder() {}

    /**
     * Iterable of primitives to avoid memory waste.
     */
    interface FloatIterable {
        boolean hasNext();

        float next();
    }

    static final class MinMax {
        public final float min;
        public final float max;

        private MinMax(float min, float max) {
            this.min = min;
            this.max = max;
        }
    }
}
