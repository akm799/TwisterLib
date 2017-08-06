package uk.co.akm.twistertest.activity.base;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import uk.co.akm.twistertest.R;

/**
 * Created by Thanos Mavroidis on 17/07/2017.
 */
public abstract class BaseSensorActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = BaseSensorActivity.class.getSimpleName();

    @LayoutRes
    private final int layoutResId;

    private final int sensorType;
    private final int samplingPeriodUs;

    private Sensor sensor;
    private SensorManager sensorManager;

    protected BaseSensorActivity(@LayoutRes int layoutResId, int sensorType, int samplingPeriodUs) {
        this.layoutResId = layoutResId;
        this.sensorType = sensorType;
        this.samplingPeriodUs = samplingPeriodUs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);

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
