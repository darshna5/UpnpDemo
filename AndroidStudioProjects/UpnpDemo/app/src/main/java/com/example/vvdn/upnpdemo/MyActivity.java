package com.example.vvdn.upnpdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.AndroidSimpleUpnp.AndroidUpnpProvider;

import com.example.vvdn.upnpdemo.adapter.DeviceSearchAdapter;
import com.example.vvdn.upnpdemo.model.SearchDevice;
import com.example.vvdn.upnpdemo.model.SearchDeviceResp;
import com.example.vvdn.upnpdemo.service.UDPListenerService;
import com.example.vvdn.upnpdemo.utility.Utility;
import com.google.gson.Gson;

import org.apache.http.conn.util.InetAddressUtils;
import org.eclipse.jetty.server.Response;
import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.transport.spi.NetworkAddressFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MyActivity extends Activity {

    private String details;
    private JSONArray jsonArray;
    TextView textStatus;
    AndroidUpnpProvider upnpProvider;
    private RecyclerView recyclerList;
    private DeviceSearchAdapter deviceSearchAdapter;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);

        System.out.println("MyActivity.onCreate ip address for device="+ Utility.getMyIPAddress());
        /**
         * Instantiate a AndroidUpnpProvider object
         */

        upnpProvider = new AndroidUpnpProvider();

        /**
         * Bind to the service and initialize search so that you can enumerate devices asynchronously.
         */

        getApplicationContext().bindService(
                new Intent(this, AndroidUpnpServiceImpl.class),
                upnpProvider.serviceConnection,
                Context.BIND_AUTO_CREATE
        );


        /**
         * Enumerate Button
         */

        Button btnEnum = (Button) findViewById(R.id.btnEnum);
        btnEnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<SearchDevice> searchDeviceArrayList = new ArrayList<SearchDevice>();

                /**
                 * Log available devices to console output. Also set it to a Textview on the Screen
                 */
                try {
                    Log.d("Test", upnpProvider.getDevices());
//                    jsonArray = new JSONArray(upnpProvider.getDevices());
                    jsonArray = upnpProvider.getDevices1();

                    System.out.println("Test devices=" + jsonArray.toString());
                    if (jsonArray.toString().equalsIgnoreCase("") ||
                            jsonArray.toString().equalsIgnoreCase(" ")
                            || jsonArray.toString().equalsIgnoreCase("[]")) {
//                        textStatus = (TextView) findViewById(R.id.textViewStatus);
//                    textStatus.setText(jsonArray.toString());

                        Toast.makeText(MyActivity.this, "Data not found!", Toast.LENGTH_LONG).show();
                    } else {
                        textStatus = (TextView) findViewById(R.id.textViewStatus);
//                        Toast.makeText(MyActivity.this, jsonArray.toString(), Toast.LENGTH_LONG).show();
//                        SearchDeviceResp searchDeviceResp = new SearchDeviceResp();

                        for (int x = 0; x < jsonArray.length(); x++) {
                            JSONObject thisSet = jsonArray.getJSONObject(x);
                            SearchDevice searchDevice = new SearchDevice();
                            searchDevice.setDeviceName(thisSet.getString("deviceName"));
                            searchDevice.setDeviceUUID(thisSet.getString("deviceUUID"));
                            searchDevice.url = thisSet.getString("url");
                            searchDevice.friendName = thisSet.getString("friendName");
                            searchDevice.manufacture = thisSet.getString("manufacture");
                            searchDevice.manufacture_uri = thisSet.getString("manufacture_uri");
                            searchDevice.modelName = thisSet.getString("modelName");
                            searchDevice.modelDes = thisSet.getString("modelDes");
                            searchDevice.modelNo = thisSet.getString("modelNo");
                            searchDevice.modelUrl = thisSet.getString("modelUrl");
                            searchDevice.serialNo = thisSet.getString("serialNo");
                            searchDevice.upc = thisSet.getString("upc");
                            searchDevice.presentationUri = thisSet.getString("presentationUri");
                            searchDeviceArrayList.add(searchDevice);

                        }
                        System.out.println("MyActivity.onClick=" + searchDeviceArrayList.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (searchDeviceArrayList != null && searchDeviceArrayList.size() > 0) {
                    setDataToAdapter(searchDeviceArrayList);
                }
            }
        });


        /**
         * Select a Device Button
         */

        Button btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Parse the JSON String devices. Then, fetch the UUID of required device and select it.
                 * Here, it's fetching the UUID of a dummy Plex Media Renderer on my network.
                 */

                try {
                    String UUID = jsonArray.getJSONObject(2).getString("deviceUUID"); //Change index to choose device
                    System.out.println("Test select device=" + UUID);
                    Log.d("Test", UUID);
                    upnpProvider.selectDevice("fb8e67ba-7dba-11e7-be55-10da43d77721");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Test", "Improper JSON Index");
                }
            }
        });

        /**
         * Send Stream button. Sends a URL to the renderer and initiates play.
         */

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upnpProvider.sendStream("http://cloudwalker.bc.cdn.bitgravity.com/Movies/IshqInParis/IshqInParis_200_180p.mp4");
            }
        });

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upnpProvider.play();
            }
        });


        Button btnPause = (Button) findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upnpProvider.pause();
            }
        });

        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upnpProvider.stop();
            }
        });


        Button btnSeek = (Button) findViewById(R.id.btnSeek);
        btnSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                /**
//                 * Get seek time from Textbox and seek.
//                 */
//                EditText seekValue = (EditText) findViewById(R.id.editText);
//                upnpProvider.seek(seekValue.getText().toString());
                startService(new Intent(MyActivity.this, UDPListenerService.class));

            }
        });


        /**
         * Get current status details of the player in a JSON String. Set to TextView and Log
         */

        Button btnDetails = (Button) findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upnpProvider != null) {
                    details = upnpProvider.getDetails();
                    textStatus = (TextView) findViewById(R.id.textViewStatus);
                    textStatus.setText(details);
                    Log.d("Test", details);
                }
            }
        });


        /**
         * Cleanup Operations
         */

        Button btnClean = (Button) findViewById(R.id.btnClean);
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upnpProvider.cleanup();
//                getApplicationContext().unbindService(upnpProvider.serviceConnection);

                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        // Do network action in this function
                        UpnpServiceConfiguration upnpServiceConfiguration= upnpProvider.getAddress();
                        if (upnpServiceConfiguration!=null){
                            NetworkAddressFactory networkAddressFactory= upnpServiceConfiguration.createNetworkAddressFactory();
                            if (networkAddressFactory!=null){
                                int multicastPort= networkAddressFactory.getMulticastPort();
                                Iterator<InetAddress> inetAddressIterator= networkAddressFactory.getBindAddresses();
                                while (inetAddressIterator.hasNext()) {
                                    InetAddress item = inetAddressIterator.next();

                                    System.out.println("MyActivity.onClick multi group1 ="+"\nconical host address1="+item.getCanonicalHostName()
                                            +"\n host address1="+item.getHostAddress()+"\n host name1="+item.getHostName());
                                }
                                System.out.println("MyActivity.onClick multicast port="+multicastPort);
                                InetAddress inetAddress= networkAddressFactory.getMulticastGroup();
                                System.out.println("MyActivity.onClick multi group ="+"\nconical host address="+inetAddress.getCanonicalHostName()
                                        +"\n host address="+inetAddress.getHostAddress()+"\n host name="+inetAddress.getHostName());
                            }
                        }
                    }
                }).start();

            }
        });


    }

    private void setDataToAdapter(ArrayList<SearchDevice> searchDeviceArrayList) {
        // set up the RecyclerView
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        deviceSearchAdapter = new DeviceSearchAdapter(MyActivity.this, searchDeviceArrayList);
//        adapter.setClickListener(this);
        recyclerList.setAdapter(deviceSearchAdapter);
    }


}
