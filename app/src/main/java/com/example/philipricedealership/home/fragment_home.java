package com.example.philipricedealership.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;


public class fragment_home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        User currentUser = (User) getArguments().getSerializable("currentUser");
        System.out.println("USER "+currentUser.toString());
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}