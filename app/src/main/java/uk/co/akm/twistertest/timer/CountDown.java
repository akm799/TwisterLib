package uk.co.akm.twistertest.timer;


import android.os.Handler;

/**
 * Helper class to abstract a simple count-down functionality.
 *
 * Created by Thanos Mavroidis on 13/08/2018.
 */
public final class CountDown {
    private final Handler handler;
    private final int timeUnits;
    private final long timeDecrementMillis;
    private final long prepareTimeMillis;
    private final CountdownListener callback;

    private AbstractCountDownRunnable countDownRunnable;

    /**
     * @param handler the handler to post count-down updates
     * @param timeUnits the number of count-down steps
     * @param timeDecrementMillis the number of milliseconds between two consecutive count-down steps
     * @param prepareTimeMillis the number of milliseconds for which to prepare for the count-down
     * @param callback the callback to use to perform the count-down updates
     */
    public CountDown(Handler handler, int timeUnits, long timeDecrementMillis, long prepareTimeMillis, CountdownListener callback) {
        this.handler = handler;
        this.timeUnits = timeUnits;
        this.timeDecrementMillis = timeDecrementMillis;
        this.prepareTimeMillis = prepareTimeMillis;
        this.callback = callback;
    }

    /**
     * Starts the count-down process, which includes the preparation time.
     */
    public void start() {
        countDownRunnable = new CountDownRunnable(handler, timeUnits, timeDecrementMillis, callback);
        final AbstractCountDownRunnable startCountDownRunnable = new StartCountDownRunnable(handler, timeUnits, prepareTimeMillis, countDownRunnable, callback);
        startCountDownRunnable.start();
    }

    /**
     * Aborts the count-down process.
     */
    public void abort() {
        if (countDownRunnable != null) {
            countDownRunnable.abort();
        }
    }
}
