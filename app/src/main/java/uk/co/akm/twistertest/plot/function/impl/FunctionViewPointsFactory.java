package uk.co.akm.twistertest.plot.function.impl;

import uk.co.akm.twistertest.plot.function.FunctionData;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;
import uk.co.akm.twistertest.plot.view.FunctionView;

/**
 * Created by Thanos Mavroidis on 21/08/2017.
 */
public class FunctionViewPointsFactory {

    public static FunctionViewPoints buildViewPoints(FunctionView view, FunctionData functionData) {
        if (functionData.yOnly()) {
            return new FunctionViewPointsY(view, functionData);
        } else {
            return new FunctionViewPointsXY(view, functionData);
        }
    }

    private FunctionViewPointsFactory() {}
}
