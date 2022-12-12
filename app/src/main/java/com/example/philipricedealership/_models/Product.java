package com.example.philipricedealership._models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.philipricedealership._utils.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private int uid, qty = 1;
    private String name, imgUrl, description;
    private double price;

    private boolean added = false;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public Double getTotalCost () { return qty * price; }

    public Product(int uid, int qty) {
        this.uid = uid;
        this.qty = qty;
    }

    public Product(String name, String imgUrl, String description, double price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.description = description;
    }

    public Product(int uid, String name, String imgUrl, String description, double price) {
        this.uid = uid;
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setPrice(double price) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getImgResId(Context context){
        int resid = context.getResources().getIdentifier(String.format("drawable/%s", getImgUrl()), null, context.getPackageName());
        return resid;
    }

    private ContentValues getSelfContentValues(){
        ContentValues vals = new ContentValues();
        vals.put("imgUrl", imgUrl);
        vals.put("name", name);
        vals.put("description", description);
        vals.put("price", price);
        return vals;
    }

    public boolean saveState(Context context, DatabaseHelper dbHelper, boolean isNew){
        if(isNew){
            if(dbHelper.insert(getSelfContentValues(), "product")){
                return true;
            }else{
                Toast.makeText(null, "Failed to create product", Toast.LENGTH_LONG);
                return false;
            }
        }else{
            if( !dbHelper.update(getSelfContentValues(), "uid="+getUid()+"", "product") ){
                Toast.makeText(context, "Failed to save state", Toast.LENGTH_LONG);
                return false;
            }else{
                fetchSelf(dbHelper);
                return true;
            }
        }
    }

    public void fetchSelf(DatabaseHelper dbHelper){
        try{
            Cursor cur = dbHelper.execRawQuery(String.format("SELECT uid, imgUrl, name, description, price FROM product WHERE uid=%d;", uid), null);
            if (cur == null || cur.getCount() == 0 || !cur.moveToNext()) return;
            setUid(cur.getInt(0));
            setImgUrl(cur.getString(1));
            setName(cur.getString(2));
            setDescription(cur.getString(3));
            setPrice(cur.getFloat(4));
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
        Cursor all = dbHelper.execRawQuery("SELECT uid, name, imgUrl, description, price FROM product", null);
        while(all.moveToNext()){
            alls.add(new Product(
                    all.getInt(0),
                    all.getString(1),
                    all.getString(2),
                    all.getString(3),
                    all.getDouble(4)
            ));
        }
        return alls;
    }
    @Override
    public String toString() {
        return "Product{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}