package uk.co.akm.twistertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import uk.co.akm.twistertest.activity.SensorPlotActivity;
import uk.co.akm.twistertest.activity.SensorRestActivity;
import uk.co.akm.twistertest.activity.base.BaseSingleSensorValueActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRecordRest(View view) {
        startSensorActivity(SensorRestActivity.class);
    }

    public void onPlotTwist(View view) {
        startSensorActivity(SensorPlotActivity.class);
    }

    private void startSensorActivity(Class<? extends BaseSingleSensorValueActivity> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
