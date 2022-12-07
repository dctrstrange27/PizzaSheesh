package com.example.philipricedealership._models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;
import com.example.philipricedealership._utils.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.ParameterList;

public class User implements Serializable {
    private int uid, state;
    private String image, email, username, password, address, cart; // <~> + ex : id+qty

    public ArrayList<Product> getCartItems(DatabaseHelper dbHelper){
        ArrayList<Product> items = new ArrayList<>();
        if(cart.length() == 0) return items;
        String [] porducts = cart.split("<~>");
        for(int x = 0; x < porducts.length; x++){
            String [] sparse = porducts[x].split("\\+");
            if(sparse[0].length() == 0) continue;
            Product pr = new Product(Integer.parseInt(sparse[0]), Integer.parseInt(sparse[1]));
            pr.fetchSelf(dbHelper);
            items.add(pr);
        }
        return items;
    }

    public void addToCart(Product p, Context context, DatabaseHelper dbHelper){
        ArrayList<Product> mycart = getCartItems(dbHelper);
        mycart.add(p);
        Toast.makeText(context, "Added To Cart 🛒", Toast.LENGTH_SHORT).show();
        setCart(cartStringifyer(mycart));
        saveState(context, dbHelper, false);
    }

    public String productCartSplitter(Product p){
        return String.format("%d+%d",p.getUid(),p.getQty());
    }

    public String cartStringifyer(ArrayList<Product> p){
        String res = "";
        if(p.size() == 0) return res;
        String arrs[] = new String[p.size()];
        for(int x = 0; x < p.size(); x++){
            String stringified = productCartSplitter(p.get(x));
            arrs[x] = stringified;
        }
        res = String.join("<~>", arrs);
        return res;
    }

    public void placeOrder(Context context, DatabaseHelper dbHelper){
        ArrayList<Product> prd = getCartItems(dbHelper);
        double total = 0;
        for(Product p : prd) total += p.getTotalCost();
        Order ord = new Order(getUid(), total, getCart(), new Date().toLocaleString());
        ord.saveState(context, dbHelper, true);
    }


    public User(int uid, int state, String image, String email, String username, String password, String address, String cart) {
        this.uid = uid;
        this.state = state;
        this.image = image;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.cart = cart;
    }
    public User(String image, String email, String username, String password, String address, String cart) {
        this.image = image;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.cart = cart;
    }
    public User(String email, String username, String password, String address, String cart) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.cart = cart;
    }

    public User(String email) {
        this.email = email;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public boolean checkIfAlreadyExist(DatabaseHelper dbHelper){
        Cursor curs = dbHelper.execRawQuery("SELECT * FROM user WHERE email='"+this.email+"'", null);
        if(curs != null && curs.getCount() > 0) return true;
        return false;
    }

    private ContentValues getSelfContentValues(){
        ContentValues vals = new ContentValues();
        vals.put("state", this.state);
        vals.put("image", this.image);
        vals.put("email", this.email);
        vals.put("username", this.username);
        vals.put("password", this.password);
        vals.put("address", this.address);
        vals.put("cart", this.cart);
        return vals;
    }
    /* Saves current object state to user table
    *
     * @args DatabaseHelper an instance of DatabaseHelper
     * @args isNew indicastes if this user is a new user, if user already exist this will return false
     * */
    public boolean saveState(Context context, DatabaseHelper dbHelper, boolean isNew){
        if(isNew){
            if(checkIfAlreadyExist(dbHelper)){
                System.out.println("USER : Failed New User, Duplics Desu");
                Toast.makeText(context, "User already exist", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(dbHelper.insert(getSelfContentValues(), "user")){
                System.out.println("USER : New User Saved Self");
                return true;
            }else{
                Toast.makeText(context, "Failed to create", Toast.LENGTH_LONG);
                return false;
            }
        }else{
            if( !dbHelper.update(getSelfContentValues(), "email='"+this.email+"'", "user") ){
                Toast.makeText(context, "Failed to save state", Toast.LENGTH_LONG);
                System.out.println("USER : Updated Self");
                return false;
            }else{
                fetchSelf(dbHelper);
                return true;
            }
        }
    }
    public void fetchSelf(DatabaseHelper dbHelper){
        try{
            Cursor findUser = dbHelper.execRawQuery(String.format("SELECT * FROM user WHERE email='%s';", email), null);
            if (findUser == null || findUser.getCount() == 0 || !findUser.moveToNext()) return;
            this.setUid(findUser.getInt(0));
            this.setState(findUser.getInt(1));
            this.setImage(findUser.getString(2));
            this.setEmail(findUser.getString(3));
            this.setUsername(findUser.getString(4));
            this.setPassword(findUser.getString(5));
            this.setAddress(findUser.getString(6));
            this.setCart(findUser.getString(7));
            System.out.println("INITSED ");
        }catch(Exception e){
            System.out.println("ERR ON FETCH " + e);
        }
    }
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", state=" + state +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", cart='" + cart + '\'' +
                '}';
    }
}
