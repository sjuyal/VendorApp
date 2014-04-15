package com.trex.app.vendor.screens;

/**
 * Created by sjuyal on 11/4/14.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by devilo on 11/4/14.
 */
public class DataThread extends Observable implements Runnable {

    private static final String USER_AGENT = "TRexIOT/1.0";
    private static final String DATA_URL = "http://en.wikipedia.org/w/api.php?action=query&list=backlinks&bltitle=Main%20Page&bllimit=5&blfilterredir=redirects";
    //private static String DATA_URL = "http://1-dot-utopian-sky-547.appspot.com/resource/getSeatCords/";
    //private  static  String DATA_URL = "http://192.168.1.8:4567/meta";
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    public String sendGet(String url) throws Exception {

        System.out.println("In send get method !!");
        URL obj = new URL(DATA_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        return response.toString();
    }

    @Override
    public void run() {

        System.out.println("Thread stared in DataThread!!");

        try {
            String response = sendGet("1:2");
            setChanged();
            notifyObservers(response);
            System.out.println("Observers notified");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

