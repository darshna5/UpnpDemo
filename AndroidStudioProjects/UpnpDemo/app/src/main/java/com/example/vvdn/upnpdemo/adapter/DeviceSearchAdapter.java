package com.example.vvdn.upnpdemo.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vvdn.upnpdemo.DetaildialogActivity;
import com.example.vvdn.upnpdemo.MyActivity;
import com.example.vvdn.upnpdemo.R;
import com.example.vvdn.upnpdemo.model.SearchDevice;

import java.util.ArrayList;
import java.util.List;

public class DeviceSearchAdapter extends RecyclerView.Adapter<DeviceSearchAdapter.DeviceViewHolder> {
    private Context mContext;
    private ArrayList<SearchDevice> mSearchDeviceArrayList;


    // data is passed into the constructor
     public DeviceSearchAdapter(Context context, ArrayList<SearchDevice> searchDeviceArrayList) {
        this.mContext = context;
        this.mSearchDeviceArrayList = searchDeviceArrayList;
    }



    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_device_list, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        if (mSearchDeviceArrayList != null && mSearchDeviceArrayList.size() > 0) {
            SearchDevice searchDevice = mSearchDeviceArrayList.get(position);
            holder.deviceName.setText(searchDevice.deviceName);
            holder.deviceUUDN.setText(searchDevice.deviceUUID);
        }

    }

    @Override
    public int getItemCount() {
        return mSearchDeviceArrayList.size();
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView deviceUUDN, deviceName;
        private LinearLayout ll_main;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            deviceUUDN = (TextView) itemView.findViewById(R.id.deviceUUDN);
            deviceName = (TextView) itemView.findViewById(R.id.deviceName);
            ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            System.out.println("DeviceViewHolder.onClick==" + position);
            Intent intent=new Intent(mContext, DetaildialogActivity.class);
            intent.putExtra("data",mSearchDeviceArrayList.get(position));
            ((MyActivity)mContext).startActivity(intent);
        }
    }


}
