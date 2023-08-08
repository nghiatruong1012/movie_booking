package com.example.moviebooking.retrofit;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class APIUtils {

    public static final String Base_url = "https://movieticketproject.000webhostapp.com/";
//    public static final String Base_url = "http://172.16.99.249:8080/cinema/";
//    public static final String Base_url = "http://192.168.0.178:8080/cinema/";
//    public static final String Base_url = "http://127.0.0.1:4040/cinema/";


    public static DataClient getData(){
        return RetrofitClient.getClient(Base_url).create(DataClient.class);
    }
}
