package uk.co.akm.twistertest.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import uk.co.akm.twistertest.R;
import uk.co.akm.twistertest.activity.base.BaseSingleSensorValueActivity;
import uk.co.akm.twistertest.hist.SensorValueBuffer;
import uk.co.akm.twistertest.plot.function.FunctionData;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;
import uk.co.akm.twistertest.plot.function.impl.FunctionDataFactory;
import uk.co.akm.twistertest.plot.function.impl.FunctionViewPointsFactory;
import uk.co.akm.twistertest.plot.view.FunctionView;

public class SensorPlotActivity extends BaseSingleSensorValueActivity {
    private static final int X_AXIS_INDEX = 0;
    private static final int Y_AXIS_INDEX = 1;
    private static final int Z_AXIS_INDEX = 2;

    private static final int RECORD_SECS = 3;
    private static final long SECS_TO_MILLIS = 1000;
    private static final int MAX_UPDATES_PER_SEC = 250;

    private static final SensorValueBuffer buffer = new SensorValueBuffer(RECORD_SECS*MAX_UPDATES_PER_SEC);

    private final Handler handler = new Handler();

    private boolean active;

    private FunctionView functionView;

    private final Runnable startRecording = new Runnable() {
        @Override
        public void run() {
            active = true;
        }
    };

    private final Runnable stopRecording = new Runnable() {
        @Override
        public void run() {
            active = false;
            plotBuffer();
        }
    };

    public SensorPlotActivity() {
        super(R.layout.activity_sensor_plot, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        functionView = (FunctionView)findViewById(R.id.plot_view);
    }

    public void onRecord(View view) {
        handler.post(startRecording);
        handler.postDelayed(stopRecording, RECORD_SECS*SECS_TO_MILLIS);
    }

    @Override
    protected float getSingleValue(SensorEvent event) {
        return event.values[Z_AXIS_INDEX];
    }

    @Override
    protected void onSensorSingleValueChanged(float value, long timestamp) {
        if (active) {
            buffer.add(timestamp, value);
        }
    }

    private void plotBuffer() {
        final FunctionData data = FunctionDataFactory.xy(buffer);
        final FunctionViewPoints viewPoints = FunctionViewPointsFactory.buildViewPoints(functionView, data);
        functionView.drawFunction(viewPoints);
    }
}
