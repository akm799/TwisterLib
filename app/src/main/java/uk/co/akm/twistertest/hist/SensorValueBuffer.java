package uk.co.akm.twistertest.hist;

/**
 * Simple buffer of a single value from the sensor. It holds values in two arrays: one float array
 * for the sensor value and another, of type long, for the time where the value was recorded.
 *
 * Created by Thanos Mavroidis on 09/07/2017.
 */
public final class SensorValueBuffer {
    public final long[] times;
    public final float[] values;

    private int size;

    private long minTime = Long.MAX_VALUE;
    private long maxTime = Long.MIN_VALUE;

    private float minValue = Float.MAX_VALUE;
    private float maxValue = Float.MIN_VALUE;

    public SensorValueBuffer(int maxSize) {
        times = new long[maxSize];
        values = new float[maxSize];
    }

    public void add(long timestamp, float value) {
        times[size] = timestamp;
        values[size++] = value;
    }

    public void clear() {
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public long getMinTime() {
        if (minTime == Long.MAX_VALUE) {
            findLimits();
        }

        return minTime;
    }

    public long getMaxTime() {
        if (maxTime == Long.MIN_VALUE) {
            findLimits();
        }

        return maxTime;
    }

    public long getTimeWidth() {
        return (getMaxTime() - getMinTime());
    }

    public float getMinValue() {
        if (minValue == Float.MAX_VALUE) {
            findLimits();
        }

        return minValue;
    }

    public float getMaxValue() {
        if (maxValue == Float.MIN_VALUE) {
            findLimits();
        }

        return maxValue;
    }

    public float getValueWidth() {
        return (getMaxValue() - getMinValue());
    }

    private void findLimits() {
        for (int i=0 ; i<size ; i++) {
            if (times[i] < minTime) {
                minTime = times[i];
            }

            if (times[i] > maxTime) {
                maxTime = times[i];
            }

            if (values[i] < minValue) {
                minValue = values[i];
            }

            if (values[i] > maxValue) {
                maxValue = values[i];
            }
        }
    }
}
