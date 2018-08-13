package uk.co.akm.twistertest.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import uk.co.akm.twistertest.R;
import uk.co.akm.twistertest.activity.base.BaseSingleSensorValueActivity;
import uk.co.akm.twistertest.timer.CountDown;
import uk.co.akm.twistertest.timer.CountdownListener;

/**
 * Created by Thanos Mavroidis on 17/07/2017.
 */
public class SensorRestActivity extends BaseSingleSensorValueActivity implements CountdownListener {
    private static final int X_AXIS_INDEX = 0;
    private static final int Y_AXIS_INDEX = 1;
    private static final int Z_AXIS_INDEX = 2;

    private static final int PREPARE_SECS = 4;
    private static final int RECORD_SECS = 4;
    private static final int COUNTDOWN_DECREMENT_SECS = 1;
    private static final int TOTAL_SECS = PREPARE_SECS + RECORD_SECS;
    private static final long SECS_TO_MILLIS = 1000;

    private int nUpdates;
    private float sum;
    private float absSum;
    private float minValue;
    private float maxValue;

    private boolean active;

    private final Handler handler = new Handler();

    private TextView avgText;
    private TextView absAvgText;
    private TextView messageText;
    private TextView nSamplesText;
    private TextView minValueText;
    private TextView maxValueText;
    private View startButton;

    private final String recordingStartMessage = ("Hold the device as steady as you can for " + TOTAL_SECS + " seconds.\n");

    private CountDown countDown;

    public SensorRestActivity() {
        super(R.layout.activity_sensor_rest, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        avgText = (TextView)findViewById(R.id.avg);
        absAvgText = (TextView)findViewById(R.id.absAvg);
        messageText = (TextView)findViewById(R.id.message);
        nSamplesText = (TextView)findViewById(R.id.nSamples);
        minValueText = (TextView)findViewById(R.id.minValue);
        maxValueText = (TextView)findViewById(R.id.maxValue);
        startButton = findViewById(R.id.startButton);

        messageText.setText("Press the 'Record' button to record motion levels.");
    }

    @Override
    public void onPause() {
        super.onPause();

        if (countDown != null) {
            countDown.abort();
            countDown = null;
        }
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

            nUpdates++;
            sum += value;
            absSum += Math.abs(value);
        }
    }

    public void onRecord(View view) {
        countDown = new CountDown(handler, RECORD_SECS, COUNTDOWN_DECREMENT_SECS*SECS_TO_MILLIS, PREPARE_SECS*SECS_TO_MILLIS, this);
        countDown.start();
    }

    @Override
    public void onGetReady(int timeUnits) {
        initValuesForRecording();
        setScreenViewsForRecording();
    }

    private void initValuesForRecording() {
        sum = 0;
        absSum = 0;
        nUpdates = 0;
        minValue = Float.MAX_VALUE;
        maxValue = Float.MIN_VALUE;
    }

    private void setScreenViewsForRecording() {
        startButton.setEnabled(false);
        avgText.setText("");
        absAvgText.setText("");
        nSamplesText.setText("");
        minValueText.setText("");
        maxValueText.setText("");
        messageText.setText(recordingStartMessage);
    }

    @Override
    public void onCountDown(int timeUnitsLeft, int timeUnits) {
        if (timeUnitsLeft == timeUnits) {
            active = true;
        }

        messageText.setText(recordingStartMessage + timeUnitsLeft + " seconds left.");
    }

    @Override
    public void onCountDownFinished() {
        active = false;
        final float average = sum/nUpdates;
        final float absAverage = absSum/nUpdates;
        startButton.setEnabled(true);
        avgText.setText("Average: " + average + " rad/s");
        nSamplesText.setText("Number of samples: " + nUpdates);
        minValueText.setText("Minimum: " + minValue + " rad/s");
        maxValueText.setText("Maximum: " + maxValue + " rad/s");
        absAvgText.setText("|Average|: " + absAverage + " rad/s");
        messageText.setText("Press the 'Record' button to record motion levels.");

        countDown = null;
    }
}
