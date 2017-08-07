package com.mycompany.myapplication;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.androidtown.sensor.R;

import java.util.List;

public class AxisActivity extends AppCompatActivity implements SensorEventListener {

    public static final String TAG = "AxisActivity";
    SensorManager manager = null;
    List<Sensor> sensors = null;
    public static final String AccelerationSensorIndex = "AccelSensorIndex";
    public static final String MagneticSensorIndex = "MagneticSensorIndex";
    int AccelIndex = 0;
    int MagneticIndex = 0;
    String sensorName1 = null;
    String sensorName2 = null;

    TextView txtSensorName1 = null;
    TextView txtSensorName2 = null;

    float[] rotation = new float[9];

    float[] result_data = new float[3];

    float[] mag_data = new float[3];

    float[] acc_data =new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axis);

        txtSensorName1 = (TextView) findViewById(R.id.txtSensorName1);
        txtSensorName2 = (TextView) findViewById(R.id.txtSensorName2);

        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensors = manager.getSensorList(Sensor.TYPE_ALL);

        Intent passedIntent = getIntent();
        if (passedIntent != null) {
            AccelIndex = getIntent().getIntExtra(AccelerationSensorIndex, 0);
            MagneticIndex = getIntent().getIntExtra(MagneticSensorIndex, 0);
            sensorName1 = sensors.get(AccelIndex).getName();
            sensorName2 = sensors.get(MagneticIndex).getName();
            txtSensorName1.setText(sensorName1);
            txtSensorName2.setText(sensorName2);

            //mag_sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); //마그네틱 필드 센서 생성

        }
    }

    protected void onResume() {
        super.onResume();

        manager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_UI);
        manager.registerListener(this, sensors.get(4), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)   //센서가 읽어들인 값이 마그네틱필드일때

            mag_data = event.values.clone();    //데이터를 모두 mag_data 배열에 저장

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) // 가속도센서값일때

            acc_data = event.values.clone();  //마찬가지

        if (mag_data != null && acc_data != null) { //널체크

            SensorManager.getRotationMatrix(rotation, null, acc_data, mag_data); //회전메트릭스 연산

            SensorManager.getOrientation(rotation, result_data); //연산값으로 방향값 산출

            result_data[0] = (float)Math.toDegrees(result_data[0]); // 방향값을 각도로 변환

            if(result_data[0] < 0) result_data[0] += 360; //0보다 작을경우 360을더해줌

            Log.i(TAG,"result[0]"+String.valueOf(result_data[0]));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
