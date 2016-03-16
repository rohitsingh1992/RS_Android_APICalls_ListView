package com.signup.rohitsingh.apicallsinlistview;

import android.graphics.Bitmap;

/**
 * Created by rohitsingh on 19/08/15.
 */
public class FlowerDataJsonModel {
    /*

    "category": "Container Plants",
        "price": 4.99,
        "instructions": "Compact mounds of colorful dainty flowers, good for window boxes. Fertile well drained soil.",
        "photo": "penny_orange_jumpup.jpg",
        "name": "Viola Penny Orange Jump Up",
        "productId": 13
     */

    String category;
    double price;
    String  photo;
    String  name;
    int  productId;
    Bitmap bitmap;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }




}
