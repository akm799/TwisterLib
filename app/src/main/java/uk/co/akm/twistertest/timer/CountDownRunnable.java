package uk.co.akm.twistertest.timer;


import android.os.Handler;

/**
 * Created by Thanos Mavroidis on 13/08/2018.
 */
final class CountDownRunnable extends AbstractCountDownRunnable {
    private final Handler handler;
    private final int timeUnits;
    private final long timeDecrementMillis;

    private boolean active;
    private int timeUnitsLeft;

    CountDownRunnable(Handler handler, int timeUnits, long timeDecrementMillis, CountdownListener listener) {
        super(listener);

        this.handler = handler;
        this.timeUnits = timeUnits;
        this.timeDecrementMillis = timeDecrementMillis;

        timeUnitsLeft = timeUnits;
    }

    @Override
    void start() {
        active = true;
        handler.post(this);
    }

    @Override
    void abort() {
        active = false;
    }

    @Override
    public void run() {
        if (active) {
            if (timeUnitsLeft > 0) {
                listener.onCountDown(timeUnitsLeft, timeUnits);
            } else {
                active = false;
                listener.onCountDownFinished();
            }

            if (timeUnitsLeft > 0) {
                timeUnitsLeft--;
                handler.postDelayed(this, timeDecrementMillis);
            }
        }
    }
}