package uk.co.akm.twistertest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Thanos Mavroidis on 30/10/2016.
 */
@Deprecated
public abstract class BaseSensorActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = BaseSensorActivity.class.getSimpleName();

    private final int sensorType;
    private final int samplingPeriodUs;

    private Sensor sensor;
    private SensorManager sensorManager;

    protected BaseSensorActivity(int sensorType, int samplingPeriodUs) {
        this.sensorType = sensorType;
        this.samplingPeriodUs = samplingPeriodUs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
    }

    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, samplingPeriodUs);
    }

    @Override
    public void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public abstract void onSensorChanged(SensorEvent event);

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, sensor.getName() + " sensor accuracy changed to " + accuracy);
    }
}
