package uk.co.akm.twistertest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by thanosmavroidis on 30/10/2016.
 */

public class GyroPlotActivity extends BaseSingleSensorValueActivity {
    private static final int Y_AXIS_INDEX = 1;
    private static final int Z_AXIS_INDEX = 2;

    public GyroPlotActivity() {
        super(Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_UI);
        setContentView(R.layout.activity_main);

        maxValueText = (TextView)findViewById(R.id.text_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected float getSingleValue(SensorEvent event) {
        return event.values[Z_AXIS_INDEX];
    }

private float maxValue = 0;
private TextView maxValueText;
    @Override
    protected void onSensorSingleValueChanged(float value, long timestamp) {
        if (value > maxValue) {
            maxValue = value;
            maxValueText.setText(Float.toString(maxValue));
        }
    }
}
