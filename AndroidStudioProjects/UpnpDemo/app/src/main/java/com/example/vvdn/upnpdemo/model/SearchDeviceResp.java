package com.example.vvdn.upnpdemo.model;

import java.util.ArrayList;

public class SearchDeviceResp {
    public ArrayList<SearchDevice> getSearchDevices() {
        return searchDevices;
    }

    public void setSearchDevices(ArrayList<SearchDevice> searchDevices) {
        this.searchDevices = searchDevices;
    }

    public ArrayList<SearchDevice> searchDevices=new ArrayList<SearchDevice>();
}
