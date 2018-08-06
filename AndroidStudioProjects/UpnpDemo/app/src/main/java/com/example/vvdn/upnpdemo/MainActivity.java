//package com.example.vvdn.upnpdemo;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import com.example.vvdn.upnpdemo.adapter.DeviceSearchAdapter;
//
//public class MainActivity extends AppCompatActivity {
//    private RecyclerView recyclerList;
//    private DeviceSearchAdapter deviceSearchAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
//
//
//        // set up the RecyclerView
//        recyclerList.setLayoutManager(new LinearLayoutManager(this));
//        deviceSearchAdapter = new DeviceSearchAdapter(this, animalNames);
////        adapter.setClickListener(this);
//        recyclerList.setAdapter(adapter);
//    }
//}
