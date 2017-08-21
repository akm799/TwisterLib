package uk.co.akm.twistertest.plot.function.impl;

import uk.co.akm.twistertest.plot.function.FunctionData;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
public class FunctionDataFactory {

    FunctionData xy(float[] xyValues) {
        return new FunctionDataXY(xyValues);
    }

    FunctionData yOnly(float xMin, float xMax, float[] yValues) {
        return new FunctionDataY(xMin, xMax, yValues);
    }

    private FunctionDataFactory() {}
}
