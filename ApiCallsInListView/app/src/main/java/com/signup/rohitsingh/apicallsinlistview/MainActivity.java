package com.signup.rohitsingh.apicallsinlistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;


public class MainActivity extends Activity {

    ListView mListView;
    ProgressBar mProgressbar;
    Button mBtnCallApi;
    public String HTTP_URL = "http://services.hanselandpetal.com/feeds/flowers.json";
    public String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";

    // List - For keeping all the background tasks, to supoort parallel processing
    List<MyTask> tasks;
    ArrayList<FlowerDataJsonModel> flowers = new ArrayList<FlowerDataJsonModel>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnCallApi = (Button) findViewById(R.id.main_button);
        mListView = (ListView) findViewById(R.id.main_list);
        mProgressbar = (ProgressBar) findViewById(R.id.main_progressbar);
        mProgressbar.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<MyTask>();

        mBtnCallApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()){

                    callWebService();
                } else {
                    Toast.makeText(MainActivity.this,"Internet isnt connected.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    *  public void updateTextViewWithArrayList (List <FlowerDataJsonModel> flowerList) {
        for (int i = 0; i < flowerList.size(); i ++){
            FlowerDataJsonModel obj = flowerList.get(i);

            mTextView.append(obj.getCategory() + "\n");
            mTextView.append(String.valueOf(obj.getPrice()) + "\n");
            mTextView.append(String.valueOf(obj.getProductId()) + "\n");
            mTextView.append(obj.getPhoto() + "\n");
            mTextView.append(obj.getName() + "\n");
        }

    }
    * */


    // for checking the internet connection before making any API call
    protected boolean isConnected () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()){
            return true;

        }
        return false;
    }

    protected void callWebService () {

        // Creating the object of the AsyncTask class and exucuting all teh tasks
        // in the Thread_Pool_Executer to support parrallel processing
        MyTask task = new MyTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,HTTP_URL);

    }

    private class MyTask extends AsyncTask<String,String ,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (tasks.size() == 0){
                mProgressbar.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected String doInBackground(String... params) {
            String response = HttpManager.getData(params[0]);

            flowers = FlowerJsonParser.getFlowerJson(response);

//            for (int i = 0; i < 5; i ++) {
//
//                FlowerDataJsonModel flower = flowers.get(i);
//                String photoUrl = PHOTOS_BASE_URL + flower.getPhoto();
//                try {
//                    // Downloading images
//                    InputStream in = (InputStream) new URL(photoUrl).getContent();
//                    Bitmap bitmap = BitmapFactory.decodeStream(in);
//                    flower.setBitmap(bitmap);
//                    in.close();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }



            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          //  Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
         //   flowers = FlowerJsonParser.getFlowerJson(s);

            AdapterList adapterList = null;
//            adapterList = new AdapterList(MainActivity.this,flowers);
//            mListView.setAdapter(adapterList);
            if (adapterList == null){
                adapterList = new AdapterList(MainActivity.this,flowers);
                mListView.setAdapter(adapterList);


            } else {
                adapterList.notifyDataSetChanged();
            }


            tasks.remove(this);
            if (tasks.size() == 0) {
                mProgressbar.setVisibility(View.INVISIBLE);
            }

        }
    }

}
