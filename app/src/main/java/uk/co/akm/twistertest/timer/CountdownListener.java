package uk.co.akm.twistertest.timer;

/**
 * Created by Thanos Mavroidis on 13/08/2018.
 */
public interface CountdownListener {

    void onGetReady(int timeUnits);

    void onCountDown(int timeUnitsLeft, int timeUnits);

    void onCountDownFinished();
}
