package com.example.vvdn.upnpdemo;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.ModelDetails;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.types.DLNACaps;
import org.fourthline.cling.model.types.DLNADoc;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;


/**
 * Created by karthik on 11/17/14.
 */
public class BrowseRegistryListener extends DefaultRegistryListener {
    public JSONArray jsonArray = new JSONArray();
    public JSONArray jsonArrayOwnCollectData = new JSONArray();

    public JSONObject jsonObject;

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        deviceAdded(device);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        deviceRemoved(device);
    }

    //this call back method provide all local connected devices
    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {
        deviceAdded(device);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {
        deviceRemoved(device);
    }

    public void deviceAdded(Device device) {
        try {
            DeviceDetails deviceDetails = device.getDetails();
            URL url = deviceDetails.getBaseURL();
            String friendName = deviceDetails.getFriendlyName();
            ManufacturerDetails manufacturerDetails = deviceDetails.getManufacturerDetails();
            ModelDetails modelDetails = deviceDetails.getModelDetails();
            String serialNo = deviceDetails.getSerialNumber();
            DLNADoc[] dlnaDocs = deviceDetails.getDlnaDocs();
            URI preUi = deviceDetails.getPresentationURI();
            DLNACaps dlnaCaps = deviceDetails.getSecProductCaps();
            String upc = deviceDetails.getUpc();

            System.out.println("BrowseRegistryListener.deviceAdded url=" + url);
            System.out.println("BrowseRegistryListener.deviceAdded friend name=" + friendName);
            System.out.println("BrowseRegistryListener.deviceAdded manufacture=" + manufacturerDetails.getManufacturer());
            System.out.println("BrowseRegistryListener.deviceAdded manufacture uri=" + manufacturerDetails.getManufacturerURI());
            System.out.println("BrowseRegistryListener.deviceAdded model name=" + modelDetails.getModelName());
            ;
            System.out.println("BrowseRegistryListener.deviceAdded model des=" + modelDetails.getModelDescription());
            System.out.println("BrowseRegistryListener.deviceAdded model no=" + modelDetails.getModelNumber());
            System.out.println("BrowseRegistryListener.deviceAdded model url=" + modelDetails.getModelURI().toString());
            System.out.println("BrowseRegistryListener.deviceAdded serial no=" + serialNo);
            System.out.println("BrowseRegistryListener.deviceAdded dlna=" + dlnaDocs);
            System.out.println("BrowseRegistryListener.deviceAdded presentation uri=" + preUi);
            System.out.println("BrowseRegistryListener.deviceAdded dlna caps=" + dlnaCaps);
            System.out.println("BrowseRegistryListener.deviceAdded upc=" + upc);


            Log.d("App Added", device.getDisplayString());
            jsonObject = new JSONObject();
            String deviceName = device.getDisplayString();
            String deviceUUID = device.getIdentity().getUdn().toString().substring(5);
            jsonObject.put("deviceName", deviceName);
            jsonObject.put("deviceUUID", deviceUUID);
            if (url != null)
                jsonObject.put("url", url.toString());
            else
                jsonObject.put("url", "");
            jsonObject.put("friendName", friendName);
            jsonObject.put("manufacture", manufacturerDetails.getManufacturer());
            jsonObject.put("manufacture_uri", manufacturerDetails.getManufacturerURI().toString());
            jsonObject.put("modelName", modelDetails.getModelName());
            jsonObject.put("modelDes", modelDetails.getModelNumber());
            if (preUi != null)
                jsonObject.put("presentationUri", preUi.toString());
            else
                jsonObject.put("presentationUri", "");
            jsonObject.put("modelNo", modelDetails.getModelNumber());
            if (modelDetails.getModelURI() != null)
                jsonObject.put("modelUrl", modelDetails.getModelURI().toString());
            else
                jsonObject.put("modelUrl", "");

            jsonObject.put("serialNo", serialNo);
            if (upc != null)
                jsonObject.put("upc", upc);
            else
                jsonObject.put("upc", "");
            jsonArray.put(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void deviceRemoved(Device device) {

        String removedDevice = device.getIdentity().getUdn().toString().substring(5);
        Log.d("App", "Rem: " + removedDevice);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currentDevice = jsonObject.getString("deviceUUID");
                if (currentDevice.equals(removedDevice)) {
                    jsonArray.remove(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JSONArray returnDevices() {
        return jsonArray;
    }
}
