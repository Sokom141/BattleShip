package com.battleship.utils;

import java.util.regex.Pattern;

public class AddressChecker {

    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 65535;

    public static boolean isValidIPv4(String ip) {
        return IPV4_PATTERN.matcher(ip).matches();
    }

    public static boolean isValidPort(int port) {
        return MIN_PORT <= port && port <= MAX_PORT;
    }
}
