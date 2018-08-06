package com.example.vvdn.upnpdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vvdn.upnpdemo.model.SearchDevice;

public class DetaildialogActivity extends AppCompatActivity {
    private TextView deviceName, deviceUId, urlText, friendName, manufacture, manufactureUrl, modelName, modelDes, modelNo, modelUrl, serialNo, presentaionUrl;
    private SearchDevice searchDevice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail);


        deviceName = (TextView) findViewById(R.id.deviceName);
        deviceUId = (TextView) findViewById(R.id.deviceUId);
        urlText = (TextView) findViewById(R.id.urlText);
        friendName = (TextView) findViewById(R.id.friendName);
        manufacture = (TextView) findViewById(R.id.manufacture);
        manufactureUrl = (TextView) findViewById(R.id.manufactureUrl);
        modelName = (TextView) findViewById(R.id.modelName);
        modelDes = (TextView) findViewById(R.id.modelDes);
        modelNo = (TextView) findViewById(R.id.modelNo);
        modelUrl = (TextView) findViewById(R.id.modelUrl);
        serialNo = (TextView) findViewById(R.id.serialNo);
        presentaionUrl = (TextView) findViewById(R.id.presentaionUrl);
        System.out.println("DetaildialogActivity="+getIntent());

        if (getIntent() != null) {

            searchDevice = (SearchDevice) getIntent().getSerializableExtra("data");
            System.out.println("DetaildialogActivity.onCreate=="+searchDevice);
            if (searchDevice != null) {
                deviceName.setText(searchDevice.deviceName);
                deviceUId.setText(searchDevice.deviceUUID);
                urlText.setText(searchDevice.url);
                friendName.setText(searchDevice.friendName);
                manufacture.setText(searchDevice.manufacture);
                manufactureUrl.setText(searchDevice.manufacture_uri);
                modelName.setText(searchDevice.modelName);
                modelDes.setText(searchDevice.modelDes);
                modelNo.setText(searchDevice.modelNo);
                modelUrl.setText(searchDevice.modelUrl);
                serialNo.setText(searchDevice.serialNo);
                presentaionUrl.setText(searchDevice.presentationUri);
            }

        }
    }
}