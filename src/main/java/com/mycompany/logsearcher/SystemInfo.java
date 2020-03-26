package com.mycompany.logsearcher;

public class SystemInfo {

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

    public static String appVersion() {
        return "v0.3";
    }
}