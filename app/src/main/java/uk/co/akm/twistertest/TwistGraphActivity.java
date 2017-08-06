package uk.co.akm.twistertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;

/**
 * Created by thanosmavroidis on 13/03/2016.
 */
@Deprecated
public class TwistGraphActivity extends AppCompatActivity {


    private static final class SensorEventValue {
        public final float value;
        public final long timeNanos;

        private SensorEventValue(long timeNanos, float value) {
            this.timeNanos = timeNanos;
            this.value = value;
        }
    }

    private static final class SensorHistoryView extends View {
        private static final int MAX_WIDTH = 4096;

        private final Paint textPaint = new Paint();
        private final Paint graphPaint = new Paint();

        //TODO Use this as a buffer and process the events in bulk
        private final SensorEventValue[] values = new SensorEventValue[MAX_WIDTH];

        private float minValue;
        private float maxValue;
        private long minTimeNanos;
        private long maxTimeNanos;
        private int valuesCount;

        private float[] binSums;
        private int[] binCounts;

        //TODO Rename this to minThresholdValue
        private float rateThresholdRadians;

        //TODO Rename this to setMinThresholdValue and make it abstract.
        private void setValueThreshold(float degrees) {
            rateThresholdRadians = (float)Math.PI*degrees/180;
        }

        public SensorHistoryView(Context context) {
            super(context);

            textPaint.setColor(0xff000000);
            textPaint.setTextSize(40);

            graphPaint.setColor(0xff000000);
        }

        void addValue(long timeNanos, float value) {
            //TODO change this to fill the buffer and then process the events in bulk.
            if (value > rateThresholdRadians && valuesCount < MAX_WIDTH - 1) {
                final SensorEventValue sensorEventValue = new SensorEventValue(timeNanos, value);
                values[valuesCount++] = sensorEventValue;

                if (minTimeNanos == 0) {
                    minTimeNanos = sensorEventValue.timeNanos;
                }
                else {
                    maxTimeNanos = sensorEventValue.timeNanos;
                }

                if (minValue == 0f) {
                    minValue = sensorEventValue.value;
                    maxValue = sensorEventValue.value;
                }
                else if (sensorEventValue.value < minValue) {
                    minValue = sensorEventValue.value;
                }
                else if (sensorEventValue.value > maxValue) {
                    maxValue = sensorEventValue.value;
                }
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (valuesCount > 10) {
                drawSensorValues(canvas);
            }
            else {
                canvas.drawText("No data", getWidth()/2, getHeight()/2, textPaint);
            }
        }

        private void drawSensorValues(Canvas canvas) {
            if (binSums == null) {
                binSums = new float[getWidth()];
                binCounts = new int[getWidth()];
            }

            Arrays.fill(binSums, 0f);
            Arrays.fill(binCounts, 0);

            final long nanosWidth = maxTimeNanos - minTimeNanos;
            final double nanosPerPixel = getWidth()/(double)nanosWidth;
            for (int i=0 ; i<values.length ; i++) {
                final int index = (int)(values[i].timeNanos/nanosPerPixel);
                if (index < values.length) {
                    binSums[index] += values[i].value;
                    binCounts[index]++;
                }
            }

            final float valuesWidth = maxValue - minValue;
            for (int i=0 ; i<binSums.length-1 ; i++) {
                final float startX = i;
                final float startValue = binSums[i]/binCounts[i];
                final float startY = (startValue - minValue)*getWidth()/valuesWidth;

                final float stopX = i + 1;
                final float stopValue = binSums[i+1]/binCounts[i+1];
                final float stopY = (stopValue - minValue)*getWidth()/valuesWidth;

                canvas.drawLine(startX, startY, stopX, stopY, graphPaint);
            }
        }
    }
}
