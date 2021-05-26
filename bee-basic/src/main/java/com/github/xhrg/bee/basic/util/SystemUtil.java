package com.github.xhrg.bee.basic.util;

import org.apache.commons.lang3.StringUtils;

import java.net.*;

public class SystemUtil {

    private static String ip = "";

    public static void main(String[] args) {
        ip();
    }

    public static String ip() {
        if (StringUtils.isNotEmpty(ip)) {
            return ip;
        }
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("8.8.8.8", 53));
            ip = socket.getLocalAddress().getHostAddress();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                InetAddress ip4 = Inet4Address.getLocalHost();
                ip = ip4.getHostAddress();
            } catch (Exception e1) {
            }
        }
        if (StringUtils.isEmpty(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
