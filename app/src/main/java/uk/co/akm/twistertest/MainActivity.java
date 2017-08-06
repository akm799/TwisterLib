package uk.co.akm.twistertest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int Z_AXIS_INDEX = 2;

    private Sensor gyroscope;
    private SensorManager sensorManager;

    private TwistEvent twistEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float zRate = event.values[Z_AXIS_INDEX];
//        Log.d(TAG, "zRate=" + zRate);

        if (twistEvent == null) {
            twistEvent = TwistEvent.isTwistEventStart(event.timestamp, zRate);
        }
        else if (twistEvent.updateRate(event.timestamp, zRate)) {
            Log.d(TAG, "Twist event finished: " + twistEvent);
            twistEvent = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, sensor.getName() + " sensor accuracy changed to " + accuracy);
    }

    private static final class TwistEvent {
        private static final float NS2MS = 1.0f/1000000.0f;
        private static final float RATE_THRESHOLD_DEGREES = 7.5f;
        private static final float RATE_THRESHOLD = (float)Math.PI*RATE_THRESHOLD_DEGREES/180;

        static TwistEvent isTwistEventStart(long timestamp, float rate) {
            if (Math.abs(rate) > RATE_THRESHOLD) {
                return new TwistEvent(timestamp, rate);
            }
            else {
                return null;
            }
        }

        private final float sign;
        private final long startTimestamp;

        private float maxRate;
        private long endTimestamp;

        private TwistEvent(long startTimestamp, float initRate) {
            this.sign = Math.signum(initRate);
            this.startTimestamp = startTimestamp;
            this.maxRate = Math.abs(initRate);
        }

        boolean updateRate(long timestamp, float rate) {
            final float signRate = Math.signum(rate);
            final float absRate = Math.abs(rate);
            if (signRate == sign && absRate > maxRate) {
                maxRate = absRate;
                return false;
            }

            final boolean twistFinished = (signRate != sign || absRate <= RATE_THRESHOLD);
            if (twistFinished) {
                endTimestamp = timestamp;
            }

            return twistFinished;
        }

        float getMaxRate() {
            return maxRate;
        }

        long getTimeWidth() {
            return (endTimestamp - startTimestamp);
        }

        @Override
        public String toString() {
            return ("(" + (sign > 0 ? "LEFT" : "RIGHT") + ", " + (180*maxRate/Math.PI) + ", " + NS2MS*getTimeWidth() + ")");
        }
    }
}
