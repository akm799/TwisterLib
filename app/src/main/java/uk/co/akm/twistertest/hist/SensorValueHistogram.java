package uk.co.akm.twistertest.hist;


import android.util.Log;
import android.view.View;

import uk.co.akm.twistertest.view.HistogramView;

/**
 * Created by Thanos Mavroidis on 17/07/2017.
 */
public final class SensorValueHistogram {
    private static final String TAG = SensorValueHistogram.class.getSimpleName();

    private final int maxHeight;
    public final int[] heights;

    public SensorValueHistogram(HistogramView histogramView, SensorValueBuffer buffer) {
        if (histogramView.hasDimensions()) {
            heights = new int[histogramView.getWidth()];
            maxHeight = histogramView.getHeight();
            fill(buffer);
        } else {
            heights = null;
            maxHeight = 0;
            Log.e(TAG, "Error: Input histogram view has no dimensions.");
        }
    }

    private void fill(SensorValueBuffer buffer) {
        final int numberOfBins = heights.length;
        final double binWidth = buffer.getTimeWidth()/numberOfBins;
        final int[] binSizes = new int[numberOfBins];
        final float[] binValues = new float[numberOfBins];

        // Store the sum of values in each bin, temporarily.
        final long minX = buffer.getMinTime();
        for (int i=0 ; i<buffer.getSize() ; i++) {
            final int binIndex = (int)((buffer.times[i] - minX)/binWidth);
            binValues[binIndex] += buffer.values[i];
            binSizes[binIndex]++;
        }

        // Store the real bin values in each bin, by calculating the average value.
        for (int i=0 ; i<binValues.length ; i++) {
            if (binValues[i] > 0) {
                binValues[i] = binValues[i]/binSizes[i];
            }
        }

        // Convert the bin values into screen pixel height
        final float minY = buffer.getMinValue();
        final float deltaY = buffer.getValueWidth();
        for (int i=0 ; i<binValues.length ; i++) {
            if (binValues[i] > 0) {
                final int intY = (int)(maxHeight*(binValues[i] - minY)/deltaY);
                heights[i] = maxHeight - intY; // y-coordinate is reversed in the screen coordinate system.
            }
        }
    }
}
