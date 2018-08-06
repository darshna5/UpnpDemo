package com.example.vvdn.upnpdemo.model;

import java.io.Serializable;

public class SearchDevice implements Serializable{

    public String deviceName;
    public String deviceUUID;
    public String url;
    public String friendName;
    public String manufacture;
    public String manufacture_uri;
    public String modelName;
    public String modelDes;
    public String modelNo;
    public String modelUrl;
    public String serialNo;
    public String presentationUri;
    public String dlnaCaps;
    public String upc;



    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }


}
