package com.akami.simple;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class CookieManagerTest {
static CookieManager ckman = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    
    public static void main(String args[]) throws Exception {
        CookieHandler.setDefault(ckman);
        
        testCookieManager();
        testCookieManager();
    }

    private static void testCookieManager() throws MalformedURLException, IOException {
        String urlString = "http://www.kakao.com";
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.getContent();
        
        CookieStore ckStore = ckman.getCookieStore();

        List<URI> uriList = ckStore.getURIs();
        for (URI uri : uriList) {
            System.out.println(uri.getHost());
        }
        List<HttpCookie> cks = ckStore.getCookies();
        for (HttpCookie ck : cks) {
            System.out.println(ck);
        }
    }

}
