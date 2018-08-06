package com.example.vvdn.upnpdemo.utility;

import org.apache.http.conn.util.InetAddressUtils;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class Utility {
    public static String getMyIPAddress() {//P.S there might be better way to get your IP address (NetworkInfo) could do it.
        String myIP = null;
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (isIPv4)
                            myIP = sAddr;
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return myIP;
    }
}
