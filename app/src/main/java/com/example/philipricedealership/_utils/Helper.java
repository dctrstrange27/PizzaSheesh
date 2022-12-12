package com.example.philipricedealership._utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
public class Helper {
    final static String EMAIL = "philipricedealer79@gmail.com", PASS = "jyeinuwihktofojg";
    public static String hashPassword(String toHash) {
        String hashed = BCrypt.hashpw(toHash, BCrypt.gensalt());
        return hashed;
    }

    public static boolean comparePassword(String word, String hash){
        if(BCrypt.checkpw(word, hash)) return true;
        return false;
    }

    public static String randomKey(int length) {
        String genCars = "";

        int nums[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        while (length > 0) {
            genCars += nums[randomNum()];
            length--;
        }

        return genCars;
    }

    public static int randomNum() {
        return new Random().nextInt(9);
    }

    public static boolean checkIfExist(String from, String what){
        String strry [] = from.split(",");
        for(String st : strry) if(st.equals(what)) return true;
        return false;
    }

    public static String beutifyDate(Date dt){
        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(new Date());
        return date;
    }

    public static String seatGenerator(int startseat, int endseat){
        String seat = "";
        for(; startseat <= endseat; startseat++)
            seat += startseat + (startseat == endseat ? "" : ",");
        return seat;
    }

    public static String toISODateString(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String ISO = sdf.format(date);
        return ISO;
    }

    public static Date fromIoDateStringToDate(String ISODateString) {
        return Date.from(ZonedDateTime.parse(ISODateString).toInstant());
    }
//
//    public static Bitmap genQr(String content){
//        Bitmap btmp = null;
//
//        MultiFormatWriter mulform = new MultiFormatWriter();
//
//        try{
//            BitMatrix bitma = mulform.encode(content, BarcodeFormat.QR_CODE, 400, 400);
//            BarcodeEncoder baren = new BarcodeEncoder();
//            btmp = baren.createBitmap(bitma);
//        }catch (Exception e){
//
//        }
//        return btmp;
//    }

    public static void saveImage(File file, Bitmap bitmap){
        if (!file.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                System.out.println("Helper write image ");
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getImage(File f){
        Bitmap btm = null;
        if (f.exists()) btm = BitmapFactory.decodeFile(f.getAbsolutePath());
        return btm;
    }

    public static void deleteFile(String filepath){
        File toDelete = new File(filepath);
        toDelete.delete();
    }
}
