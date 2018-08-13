package uk.co.akm.twistertest.timer;

/**
 * Created by Thanos Mavroidis on 13/08/2018.
 */
abstract class AbstractCountDownRunnable implements Runnable {
    final CountdownListener listener;

    AbstractCountDownRunnable(CountdownListener listener) {
        this.listener = listener;
    }

    abstract void start();

    void abort() {}
}
