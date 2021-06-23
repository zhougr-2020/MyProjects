package com.nanhang.mybatis_plus.socket;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: immortal
 * @CreateDate: 2021/6/16 16:04
 * @Description:
 */
public class Tcp {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostAddress());
        System.out.println(localHost.getHostName());
        System.out.println(localHost);
    }
}
