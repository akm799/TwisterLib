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
import uk.co.akm.twistertest.hist.SensorValueBuffer;
import uk.co.akm.twistertest.plot.function.FunctionData;
import uk.co.akm.twistertest.plot.function.FunctionViewPoints;
import uk.co.akm.twistertest.plot.function.impl.FunctionDataFactory;
import uk.co.akm.twistertest.plot.function.impl.FunctionViewPointsFactory;
import uk.co.akm.twistertest.plot.view.FunctionView;
import uk.co.akm.twistertest.timer.CountDown;
import uk.co.akm.twistertest.timer.CountdownListener;

public class SensorPlotActivity extends BaseSingleSensorValueActivity implements CountdownListener {
    private static final int X_AXIS_INDEX = 0;
    private static final int Y_AXIS_INDEX = 1;
    private static final int Z_AXIS_INDEX = 2;

    private static final int RECORD_SECS = 6;
    private static final int PREPARE_SECS = 3;
    private static final int COUNTDOWN_DECREMENT_SECS = 1;
    private static final long SECS_TO_MILLIS = 1000;
    private static final int MAX_UPDATES_PER_SEC = 250;

    private static final SensorValueBuffer buffer = new SensorValueBuffer(RECORD_SECS*MAX_UPDATES_PER_SEC);

    private final Handler handler = new Handler();

    private boolean active;

    private TextView xMax;
    private TextView xMin;
    private TextView yMax;
    private TextView yMin;
    private TextView countDownText;

    private View recordButton;

    private FunctionView functionView;

    private CountDown countDown;

    public SensorPlotActivity() {
        super(R.layout.activity_sensor_plot, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resolveViewReferences();
        setPlotAxesTextViews(0, 0, 0, 0);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (countDown != null) {
            countDown.abort();
            countDown = null;
        }
    }

    private void resolveViewReferences() {
        xMin = (TextView)findViewById(R.id.xMin);
        xMax = (TextView)findViewById(R.id.xMax);
        yMin = (TextView)findViewById(R.id.yMin);
        yMax = (TextView)findViewById(R.id.yMax);
        recordButton = findViewById(R.id.recordButton);
        countDownText = (TextView)findViewById(R.id.countDownText);
        functionView = (FunctionView)findViewById(R.id.plot_view);
    }

    public void onRecord(View view) {
        countDown = new CountDown(handler, RECORD_SECS, COUNTDOWN_DECREMENT_SECS*SECS_TO_MILLIS, PREPARE_SECS*SECS_TO_MILLIS, this);
        countDown.start();
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

    @Override
    public void onGetReady(int timeUnits) {
        buffer.clear();
        functionView.clear();

        recordButton.setEnabled(false);
        countDownText.setText("Prepare to record ...");
        setPlotAxesTextViews(0, 0, 0, 0);
    }

    @Override
    public void onCountDown(int timeUnitsLeft, int timeUnits) {
        if (timeUnitsLeft == timeUnits) {
            active = true;
        }

        countDownText.setText(Integer.toString(timeUnitsLeft) + " seconds left.");
    }

    @Override
    public void onCountDownFinished() {
        active = false;
        countDown = null;

        plotBuffer();
        countDownText.setText("");
        recordButton.setEnabled(true);
    }

    private void plotBuffer() {
        final FunctionData data = FunctionDataFactory.xy(buffer);
        final FunctionViewPoints viewPoints = FunctionViewPointsFactory.buildViewPoints(functionView, data);
        functionView.drawFunction(viewPoints);
        setPlotAxesTextViews(0, RECORD_SECS*SECS_TO_MILLIS, data.yMin(), data.yMax());
    }

    private void setPlotAxesTextViews(float xMn, float xMx, float yMn, float yMx) {
        xMin.setText("xMin=" + xMn + " ms");
        xMax.setText("xMax=" + xMx + " ms");
        yMin.setText("yMin=" + yMn + " rad/s");
        yMax.setText("yMax=" + yMx + " rad/s");
    }
}
