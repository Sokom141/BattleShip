package com.battleship.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IpChecker {

    /**
     * Get the public IP for the server to share
     *
     * @return a String containing the public IP
     * @throws Exception the connection has failed or the site is unreachable
     */
    public static String getIp() throws Exception {
        URL checkIpURL = new URL("https://checkip.amazonaws.com/");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(checkIpURL.openStream()));
            return in.readLine();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
