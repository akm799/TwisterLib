package uk.co.akm.twistertest;

import android.hardware.SensorEvent;

/**
 * Created by Thanos Mavroidis on 30/10/2016.
 */
@Deprecated
public abstract class BaseSingleSensorValueActivity extends BaseSensorActivity {

    public BaseSingleSensorValueActivity(int sensorType, int samplingPeriodUs) {
        super(sensorType, samplingPeriodUs);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        onSensorSingleValueChanged(getSingleValue(event), event.timestamp);
    }

    protected abstract float getSingleValue(SensorEvent event);

    protected abstract void onSensorSingleValueChanged(float value, long timestamp);
}
