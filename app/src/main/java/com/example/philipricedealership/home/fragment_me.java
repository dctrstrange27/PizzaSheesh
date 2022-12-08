package com.example.philipricedealership.home;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership._utils.Helper;

import java.io.File;
import java.util.Date;
import com.example.philipricedealership.signup.*;

public class fragment_me extends Fragment {
    private User currentUser;
    private TextView staticemail, staticusername, sw, logout;
    private EditText usernameeditable, passwordeditable, passwordconfirmeditable, address;
    private ImageView cover;

    private boolean hasNewImage = false;
    private Bitmap bitmap;
    private DatabaseHelper dbHelper;
    private Button savebtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_me, container, false);
        dbHelper = new DatabaseHelper(getContext());
        currentUser = (User) getArguments().getSerializable("currentUser");
        staticemail = v.findViewById(R.id.staticemail);
        staticusername = v.findViewById(R.id.staticusername);
        usernameeditable = v.findViewById(R.id.usernameeditable);
        passwordeditable = v.findViewById(R.id.passwordeditable);
        passwordconfirmeditable = v.findViewById(R.id.passwordconfirmeditable);

        sw = v.findViewById(R.id.sw);
        sw.setOnClickListener(JohnySinsei -> {
            currentUser.setState(0);
            currentUser.saveState(getContext(), dbHelper, false);
            Intent signup = new Intent(getContext(), signup.class);
            startActivity(signup);
            getActivity().finish();
        });

        logout = v.findViewById(R.id.logout);

        logout.setOnClickListener(JohnySinsei -> {
            currentUser.setState(0);
            currentUser.saveState(getContext(), dbHelper, false);
            Intent login = new Intent(getContext(), login.class);
            startActivity(login);
            getActivity().finish();
        });

        address = v.findViewById(R.id.address);
        cover = v.findViewById(R.id.cover);
        savebtn = v.findViewById(R.id.savebtn);
        cover.setOnClickListener(JohnySensei -> {
            Intent pickGal = new Intent(Intent.ACTION_PICK);
            pickGal.setType("image/*");
            pickGal.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickGal, 1000);
        });
        savebtn.setOnClickListener(JohnySinsei -> {
            saveChange();
        });

        renderMe();
        return v;
    }

    public void renderMe(){
        staticemail.setText(currentUser.getEmail());
        staticusername.setText(currentUser.getUsername());
        usernameeditable.setText(currentUser.getUsername());
        address.setText(currentUser.getAddress());
        passwordconfirmeditable.setText("");
        passwordeditable.setText("");

        if (currentUser.getImage() != null) {
            try {
                File imgFile = new File(currentUser.getImage());
                if (imgFile.exists()) {
                    cover.setImageBitmap(Helper.getImage(new File(currentUser.getImage())));
                }
            } catch (Exception e) {
                System.out.println("ERR IMAGE CUZ " + e);
            }
        }
    }

    void saveChange() {
        // TODO AFTER settings xml completed
        String _username = usernameeditable.getText().toString(),
                _address = address.getText().toString();

        if (passwordeditable.getText().toString().length() > 0) {
            if(passwordeditable.getText().toString().equals(passwordconfirmeditable.getText().toString()))
                currentUser.setPassword(Helper.hashPassword(passwordeditable.getText().toString()));
            else {
                passwordconfirmeditable.setError("Confirm Password Doesn't Match!");
                return;
            }
        }

        if(_address.length() == 0){
            address.setError("Address is required");
            return;
        }else currentUser.setAddress(_address);

        if(_username.length() == 0){
            usernameeditable.setError("Username is required");
            return;
        }else currentUser.setUsername(_username);

        if (hasNewImage) {
            try {
                ContextWrapper cw = new ContextWrapper(getContext());
                File directory = cw.getDir("profiles", Context.MODE_PRIVATE);
                File file = new File(directory, Helper.toISODateString(new Date()) + "_PROFILE_"
                        + Helper.randomKey(8) + ".jpg");
                Helper.saveImage(file, bitmap);
                String abspath = file.toString();

                if(currentUser.getImage() != null){
                    String prevImg = currentUser.getImage();
                    System.out.println("WORK D");
                    Helper.deleteFile(prevImg);
                }
                currentUser.setImage(abspath);
            } catch (Exception e) {
                System.out.println("SETTED ERR " + e);
            }
        }
        currentUser.saveState(getContext(), dbHelper, false);
        currentUser.fetchSelf(dbHelper);

        renderMe();
        Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Home parent = (Home) getActivity();
        try {
            if (resultCode == parent.RESULT_OK && requestCode == 1000) {
                Uri targetUri = data.getData();
                bitmap = BitmapFactory.decodeStream(parent.getContentResolver().openInputStream(targetUri));
                hasNewImage = true;
                cover.setImageBitmap(bitmap);
//              handleType();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("CANCELLED ");
            }
        } catch (Exception e) {
            System.out.println("Fire ERR " + e);
        }
    }
}