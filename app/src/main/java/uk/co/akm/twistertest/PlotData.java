package uk.co.akm.twistertest;

/**
 * Created by thanosmavroidis on 30/10/2016.
 */

@Deprecated
public class PlotData {
    private static final long UNDEFINED_TIME = -1;

    private final float minValue;
    private final long stopInterval;

    private long t0;
    private long tLast;
    private boolean finished;

    public PlotData(float minValue, long stopInterval) {
        this.minValue = minValue;
        this.stopInterval = stopInterval;

        t0 = UNDEFINED_TIME;
        tLast = UNDEFINED_TIME;
        finished = false;
    }

    public boolean onNewValue(float value, long time) {
        if (finished) {
            return true;
        } else if (processNewValue(value, time)) {
            finished = true;
        }

        return finished;
    }

    private boolean processNewValue(float value, long time) {
        if (value >= minValue) {
            if (t0 == UNDEFINED_TIME) {
                t0 = time;
            }

            addPoint(value, time - t0);
        } else {
            if (t0 != UNDEFINED_TIME) {
                if (tLast == UNDEFINED_TIME) {
                    tLast = time;
                } else {
                    if (time - tLast >= stopInterval) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void addPoint(float value, long t) {
        //TODO
    }
}
