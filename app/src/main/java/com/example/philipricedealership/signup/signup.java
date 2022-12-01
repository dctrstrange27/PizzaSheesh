package com.example.philipricedealership.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.philipricedealership.R;

public class signup extends AppCompatActivity {
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

       signup = findViewById(R.id.signup);





       signup.setOnClickListener(johny -> {
           Dialog verify = new Dialog(signup.this);
           verify.setContentView(R.layout.verification_dialog);

           verify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           verify.getWindow().getAttributes().windowAnimations = R.style.diagAnim;
           verify.show();

       });
    }
}