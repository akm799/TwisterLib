package uk.co.akm.twistertest.plot.function.impl;

import uk.co.akm.twistertest.plot.function.FunctionData;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
final class FunctionDataXY implements FunctionData {
    private static final int X_START_OFFSET = 0;
    private static final int Y_START_OFFSET = 1;

    private final float[] xyValues;
    private final MinMaxFinder.MinMax xMinMax;
    private final MinMaxFinder.MinMax yMinMax;

    FunctionDataXY(float[] xyValues) {
        this.xyValues = xyValues;
        xMinMax = MinMaxFinder.findMinAndMax(selectValues(xyValues, X_START_OFFSET));
        yMinMax = MinMaxFinder.findMinAndMax(selectValues(xyValues, Y_START_OFFSET));
    }

    private MinMaxFinder.FloatIterable selectValues(final float[] xyValues, final int startOffset) {
        return new MinMaxFinder.FloatIterable() {
            int i = startOffset;

            @Override
            public boolean hasNext() {
                return (i < xyValues.length);
            }

            @Override
            public float next() {
                final float v = xyValues[i];
                i += 2;

                return v;
            }
        };
    }

    @Override
    public float xMin() {
        return xMinMax.min;
    }

    @Override
    public float xMax() {
        return xMinMax.max;
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
        return false;
    }

    @Override
    public float[] values() {
        return xyValues;
    }
}
