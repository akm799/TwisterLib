package uk.co.akm.twistertest.timer;

import android.os.Handler;

/**
 * Created by Thanos Mavroidis on 13/08/2018.
 */
final class StartCountDownRunnable extends AbstractCountDownRunnable {
    private final Handler handler;
    private final int timeUnits;
    private final long prepareTimeMillis;
    private final AbstractCountDownRunnable countDownRunnable;

    StartCountDownRunnable(Handler handler, int timeUnits, long prepareTimeMillis, AbstractCountDownRunnable countDownRunnable, CountdownListener listener) {
        super(listener);

        this.handler = handler;
        this.timeUnits = timeUnits;
        this.prepareTimeMillis = prepareTimeMillis;
        this.countDownRunnable = countDownRunnable;
    }

    @Override
    void start() {
        handler.post(this);
    }

    @Override
    public void run() {
        listener.onGetReady(timeUnits);
        postStartCountDown();
    }

    private void postStartCountDown() {
        final Runnable startCountDown = new Runnable() {
            @Override
            public void run() {
                countDownRunnable.start();
            }
        };

        handler.postDelayed(startCountDown, prepareTimeMillis);
    }
}
