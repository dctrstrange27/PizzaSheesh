package com.example.philipricedealership._models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.philipricedealership._utils.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private int uid;
    private String name, imgUrl;
    private float price;

    public Product(String name, String imgUrl, float price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public Product(int uid, String name, String imgUrl, float price) {
        this.uid = uid;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private ContentValues getSelfContentValues(){
        ContentValues vals = new ContentValues();
        vals.put("imgUrl", imgUrl);
        vals.put("name", name);
        vals.put("price", price);
        return vals;
    }

    public boolean saveState(Context context, DatabaseHelper dbHelper, boolean isNew){
        if(isNew){
            if(dbHelper.insert(getSelfContentValues(), "product")){
                System.out.println("Product : New movie Saved Self");
                return true;
            }else{
                Toast.makeText(null, "Failed to create product", Toast.LENGTH_LONG);
                return false;
            }
        }else{
            if( !dbHelper.update(getSelfContentValues(), "uid="+getUid()+"", "product") ){
                Toast.makeText(context, "Failed to save state", Toast.LENGTH_LONG);
                System.out.println("Product : Updated Self");
                return false;
            }else{
                fetchSelf(dbHelper);
                return true;
            }
        }
    }

    public void fetchSelf(DatabaseHelper dbHelper){
        try{
            Cursor cur = dbHelper.execRawQuery(String.format("SELECT * FROM product WHERE uid=%d;", uid), null);
            if (cur == null || cur.getCount() == 0 || !cur.moveToNext()) return;
            setUid(cur.getInt(0));
            setImgUrl(cur.getString(1));
            setName(cur.getString(2));
            setPrice(cur.getFloat(3));
        }catch(Exception e){
            System.out.println("ERR ON FETCH " + e);
        }
    }

    /**Retrieve ang mga product desu - jervx
     *
     * @param dbHelper need ko ng instance of DatabaseHelper desu
     * @return ArrayList of <Product>
     */
    public static ArrayList <Product> getAllProduct(DatabaseHelper dbHelper){
        ArrayList <Product> alls = new ArrayList<>();
        Cursor all = dbHelper.execRawQuery("SELECT * FROM movie", null);

        while(all.moveToNext()){
            alls.add(new Product(
                    all.getInt(0),
                    all.getString(1),
                    all.getString(2),
                    all.getFloat(3)
            ));
        }

        return alls;
    }

}