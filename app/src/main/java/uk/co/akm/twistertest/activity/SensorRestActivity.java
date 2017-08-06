package uk.co.akm.twistertest.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.akm.twistertest.R;
import uk.co.akm.twistertest.activity.base.BaseSingleSensorValueActivity;

/**
 * Created by Thanos Mavroidis on 17/07/2017.
 */
public class SensorRestActivity extends BaseSingleSensorValueActivity {
    private static final int X_AXIS_INDEX = 0;
    private static final int Y_AXIS_INDEX = 1;
    private static final int Z_AXIS_INDEX = 2;

    private static final long WAIT_MILLIS = 3*1000;
    private static final long RECORD_MILLIS = 5*1000;

    private float minValue;
    private float maxValue;

    private boolean active;

    private Handler handler = new Handler();

    private TextView messageText;
    private TextView minValueText;
    private TextView maxValueText;
    private View startButton;

    public SensorRestActivity() {
        super(R.layout.activity_sensor_rest, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageText = (TextView)findViewById(R.id.message);
        minValueText = (TextView)findViewById(R.id.minValue);
        maxValueText = (TextView)findViewById(R.id.maxValue);
        startButton = findViewById(R.id.startButton);

        messageText.setText("Press the 'Record' button to record motion levels.");
    }

    @Override
    protected float getSingleValue(SensorEvent event) {
        return event.values[Z_AXIS_INDEX];
    }

    @Override
    protected void onSensorSingleValueChanged(float value, long timestamp) {
        if (active) {
            if (value < minValue) {
                minValue = value;
            }

            if (value > maxValue) {
                maxValue = value;
            }
        }
    }

    public void onRecord(View view) {
        final Runnable startRecording = new Runnable() {
            @Override
            public void run() {
                active = true;
            }
        };

        final Runnable stopRecording = new Runnable() {
            @Override
            public void run() {
                active = false;
                startButton.setEnabled(true);
                minValueText.setText("Minimum: " + minValue);
                maxValueText.setText("Maximum: " + maxValue);
                messageText.setText("Press the 'Record' button to record motion levels.");
            }
        };

        minValue = Float.MAX_VALUE;
        maxValue = Float.MIN_VALUE;

        startButton.setEnabled(false);
        minValueText.setText("");
        maxValueText.setText("");
        messageText.setText("Hold the device as steady as you can.");

        handler.postDelayed(startRecording, WAIT_MILLIS);
        handler.postDelayed(stopRecording,  WAIT_MILLIS + RECORD_MILLIS);
    }
}
