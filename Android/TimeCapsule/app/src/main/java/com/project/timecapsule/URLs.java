package com.project.timecapsule;

public class URLs {
    private static final String ROOT_URL = "192.168.43.17/PhpAPI/";
    private static final String API_KEY = "b3df7b0a616d66fe088ab75b4b3d72a5";

    public static final String LOGIN = ROOT_URL + "login.php";
    public static final String REGISTER= ROOT_URL + "reg.php";
    public static final String CREATE = ROOT_URL + "create.php";
    public static final String DISPLAY= ROOT_URL + "display.php";
    public static final String UPDATE = ROOT_URL + "update.php";

    public static String getApiKey(){
        return API_KEY;
    }

}
