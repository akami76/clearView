package com.akami.simple;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class CookieTest {
	
	public static void main(String args[]) throws Exception {
        String urlString = "http://localhost:8080/clearview/helloMysql";
        CookieManager ckman = new CookieManager();
        CookieHandler.setDefault(ckman);
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.getContent();
        CookieStore ckStore = ckman.getCookieStore();
        List<HttpCookie> cks = ckStore.getCookies();
        //iterate HttpCookie object
        for (HttpCookie ck : cks) {
            System.out.println("--- Cookie Information ---");		
            //gets domain set for the cookie
            System.out.println(ck.getDomain());
            //gets max age of the cookie
            System.out.println(ck.getMaxAge());
            // gets name cookie
            System.out.println(ck.getName());
            //gets path of the server
            System.out.println(ck.getPath());
            //gets boolean if cookie is being sent with secure protocol
            System.out.println(ck.getSecure());
            //gets the value of the cookie
            System.out.println(ck.getValue());
            //gets the version of the protocol with which the given cookie is related.
            System.out.println(ck.getVersion());
        }
    }
}
