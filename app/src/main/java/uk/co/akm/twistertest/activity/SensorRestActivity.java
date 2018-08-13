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

/**
 * Created by Thanos Mavroidis on 17/07/2017.
 */
public class SensorRestActivity extends BaseSingleSensorValueActivity {
    private static final int X_AXIS_INDEX = 0;
    private static final int Y_AXIS_INDEX = 1;
    private static final int Z_AXIS_INDEX = 2;

    private static final int PREPARE_SECS = 4;
    private static final int RECORD_SECS = 4;
    private static final int TOTAL_SECS = PREPARE_SECS + RECORD_SECS;
    private static final long SECS_TO_MILLIS = 1000;

    private int nUpdates;
    private float sum;
    private float absSum;
    private float minValue;
    private float maxValue;

    private boolean active;
    private int secondsLeft;

    private final Handler handler = new Handler();

    private TextView avgText;
    private TextView absAvgText;
    private TextView messageText;
    private TextView nSamplesText;
    private TextView minValueText;
    private TextView maxValueText;
    private View startButton;

    private final String recordingStartMessage = ("Hold the device as steady as you can for " + TOTAL_SECS + " seconds.\n");

    private final Runnable startRecording = new Runnable() {
        @Override
        public void run() {
            active = true;
        }
    };

    private final Runnable recordingCountdown = new Runnable() {
        @Override
        public void run() {
            secondsLeft -= 1;
            if (secondsLeft > 0) {
                messageText.setText(recordingStartMessage + secondsLeft + " seconds left.");
                handler.postDelayed(recordingCountdown, SECS_TO_MILLIS);
            }
        }
    };

    private final Runnable stopRecording = new Runnable() {
        @Override
        public void run() {
            active = false;
            secondsLeft = 0;
            final float average = sum/nUpdates;
            final float absAverage = absSum/nUpdates;
            startButton.setEnabled(true);
            avgText.setText("Average: " + average + " rad/s");
            nSamplesText.setText("Number of samples: " + nUpdates);
            minValueText.setText("Minimum: " + minValue + " rad/s");
            maxValueText.setText("Maximum: " + maxValue + " rad/s");
            absAvgText.setText("|Average|: " + absAverage + " rad/s");
            messageText.setText("Press the 'Record' button to record motion levels.");
        }
    };

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
        initValuesForRecording();
        setScreenViewsForRecording();
        postStartCountdownAndStopRecordingRunnables();
    }

    private void initValuesForRecording() {
        sum = 0;
        absSum = 0;
        nUpdates = 0;
        minValue = Float.MAX_VALUE;
        maxValue = Float.MIN_VALUE;
        secondsLeft = TOTAL_SECS;
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

    private void postStartCountdownAndStopRecordingRunnables() {
        handler.postDelayed(startRecording, PREPARE_SECS*SECS_TO_MILLIS);
        handler.postDelayed(recordingCountdown, SECS_TO_MILLIS);
        handler.postDelayed(stopRecording,  TOTAL_SECS*SECS_TO_MILLIS);
    }
}
