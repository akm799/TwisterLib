package uk.co.akm.twistertest.plot.function.impl;

import uk.co.akm.twistertest.hist.SensorValueBuffer;
import uk.co.akm.twistertest.plot.function.FunctionData;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
public class FunctionDataFactory {

    public static FunctionData xy(SensorValueBuffer buffer) {
        final long tMin = buffer.getMinTime();
        final float[] xyValues = new float[2*buffer.getSize()];
        for (int i=0 ; i<buffer.getSize() ; i++) {
            xyValues[2*i] = buffer.times[i] - tMin;
            xyValues[2*i + 1] = buffer.values[i];
        }

        return xy(xyValues);
    }

    public static FunctionData xy(float[] xyValues) {
        return new FunctionDataXY(xyValues);
    }

    public static FunctionData yOnly(float xMin, float xMax, float[] yValues) {
        return new FunctionDataY(xMin, xMax, yValues);
    }

    private FunctionDataFactory() {}
}
