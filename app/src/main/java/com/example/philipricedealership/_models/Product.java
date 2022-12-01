package com.example.philipricedealership._models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.philipricedealership._utils.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {


    private ContentValues getSelfContentValues(){
        ContentValues vals = new ContentValues();
//        vals.put("moviecover", moviecover);
        return vals;
    }

//    public boolean saveState(Context context, DatabaseHelper dbHelper, boolean isNew){
//        if(isNew){
//
//            if(dbHelper.insert(getSelfContentValues(), "movie")){
//                System.out.println("Product : New movie Saved Self");
//                return true;
//            }else{
//                Toast.makeText(null, "Failed to create product", Toast.LENGTH_LONG);
//                return false;
//            }
//        }else{
//            if( !dbHelper.update(getSelfContentValues(), "uid="+this.uid+"", "movie") ){
//                Toast.makeText(context, "Failed to save state", Toast.LENGTH_LONG);
//                System.out.println("Product : Updated Self");
//                return false;
//            }else{
//                fetchSelf(dbHelper);
//                return true;
//            }
//        }
//    }

    public void fetchSelf(DatabaseHelper dbHelper){
        try{
//            Cursor cur = dbHelper.execRawQuery(String.format("SELECT * FROM movie WHERE uid=%d;", uid), null);
//            if (cur == null || cur.getCount() == 0 || !cur.moveToNext()) return;
//            setUid(cur.getInt(0));
        }catch(Exception e){
            System.out.println("ERR ON FETCH " + e);
        }
    }

    /**Retrieve ang mga product desu - jervx
     *
     * @param dbHelper need ko ng instance of DatabaseHelper desu
     * @return ArrayList of <Product>
     */
    public static ArrayList <Product> getAllMovies(DatabaseHelper dbHelper){
        ArrayList <Product> alls = new ArrayList<>();
        Cursor all = dbHelper.execRawQuery("SELECT * FROM movie", null);

        while(all.moveToNext()){
            alls.add(new Product(
//                    all.getInt(0)
            ));
        }

        return alls;
    }

}