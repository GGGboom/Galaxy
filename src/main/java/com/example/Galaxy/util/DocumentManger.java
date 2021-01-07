package com.example.Galaxy.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

public class DocumentManger {
    static HttpServletRequest request;



    /**
     * 获取当前用户的ip地址
     *
     * @param userAddress
     * @return
     */
    public static String CurUserHostAddress(String userAddress) {
        if (userAddress == null) {
            try {
                userAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception ex) {
                userAddress = "";
                ex.printStackTrace();
            }
        }
        return userAddress.replaceAll("[^0-9a-zA-Z.=]", "_");
    }

    public static String getServerUrl() {
        return request.getScheme() + "://" + CurUserHostAddress(null) + ":" + request.getServerPort() + request.getContextPath();
    }
}
