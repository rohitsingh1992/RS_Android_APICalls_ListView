package com.signup.rohitsingh.apicallsinlistview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohitsingh on 19/08/15.
 */
public class AdapterList extends BaseAdapter  {

    ArrayList<FlowerDataJsonModel> mFlowers;
    Context mContext;
    LayoutInflater mInflator;
    public String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";

    LruCache <String,Bitmap> imageCache;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public AdapterList (Context c, ArrayList<FlowerDataJsonModel> listOfFlowers){

        mFlowers = listOfFlowers;
        mContext = c;
        mInflator = (LayoutInflater) c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int memoryAvailable = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int momoryAllocatedToCache = memoryAvailable / 5;
        imageCache = new LruCache<String, Bitmap>(momoryAllocatedToCache);

    }

    @Override
    public int getCount() {
        return mFlowers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            convertView = mInflator.inflate(R.layout.list_item,parent,false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.list_item_textview);
        FlowerDataJsonModel obj = mFlowers.get(position);
        textView.setText(obj.getPhoto());


        Bitmap bitmap = imageCache.get(obj.getCategory());

        if (bitmap != null){
            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_imageView);
            imageView.setImageBitmap(obj.getBitmap());
        } else {

            FloweModelAndView floweModelAndView = new FloweModelAndView();
            floweModelAndView.flowerDataJsonModel = obj;
            floweModelAndView.view = convertView;

            ImageLoader loader = new ImageLoader();
            loader.execute(floweModelAndView);
        }



        return convertView;
    }


    class FloweModelAndView {
        FlowerDataJsonModel flowerDataJsonModel;
        View view;
        Bitmap bitmap;

    }

    private class ImageLoader extends AsyncTask<FloweModelAndView,String ,FloweModelAndView> {


        @Override
        protected FloweModelAndView doInBackground(FloweModelAndView... params) {

            FloweModelAndView container = params[0];

            FlowerDataJsonModel flowerDataJsonModel = container.flowerDataJsonModel;

            String imageUrl = PHOTOS_BASE_URL + flowerDataJsonModel.getPhoto();
            try {
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                flowerDataJsonModel.setBitmap(bitmap);
                container.bitmap = bitmap;

                in.close();

                return container;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        @Override
        protected void onPostExecute(FloweModelAndView floweModelAndView) {

            ImageView imageView = (ImageView) floweModelAndView.view.findViewById(R.id.list_item_imageView);

            imageView.setImageBitmap(floweModelAndView.bitmap);
            imageCache.put(floweModelAndView.flowerDataJsonModel.getCategory(),floweModelAndView.bitmap);
           // floweModelAndView.flowerDataJsonModel.setBitmap(floweModelAndView.bitmap);

        }
    }

}
