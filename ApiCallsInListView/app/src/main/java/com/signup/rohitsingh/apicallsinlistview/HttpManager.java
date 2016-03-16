package com.signup.rohitsingh.apicallsinlistview;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rohitsingh on 19/08/15.
 */
public class HttpManager {

    static public String getData(String urlString)  {

        BufferedReader reader = null;

        try{

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();


            StringBuilder sb = new StringBuilder();
            String line;

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }

            return sb.toString();


        }catch (Exception e){
            e.printStackTrace();
            return null;

        } finally {
            try {
                reader.close();
            }catch (Exception e){
                e.printStackTrace();;
                return null;

            }

        }

    }

}
