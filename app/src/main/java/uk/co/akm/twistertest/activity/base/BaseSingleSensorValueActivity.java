package uk.co.akm.twistertest.activity.base;

import android.hardware.SensorEvent;
import android.support.annotation.LayoutRes;


/**
 * Created by Thanos Mavroidis on 17/07/2016.
 */
public abstract class BaseSingleSensorValueActivity extends BaseSensorActivity {

    public BaseSingleSensorValueActivity(@LayoutRes int layoutResId, int sensorType, int samplingPeriodUs) {
        super(layoutResId, sensorType, samplingPeriodUs);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        onSensorSingleValueChanged(getSingleValue(event), event.timestamp);
    }

    protected abstract float getSingleValue(SensorEvent event);

    protected abstract void onSensorSingleValueChanged(float value, long timestamp);
}
