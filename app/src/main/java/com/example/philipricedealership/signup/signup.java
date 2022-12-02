package com.example.philipricedealership.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipricedealership.R;
import com.example.philipricedealership._models.User;
import com.example.philipricedealership._utils.DatabaseHelper;
import com.example.philipricedealership._utils.Helper;
import com.example.philipricedealership._utils.JavaMailAPI;

public class signup extends AppCompatActivity {

    private EditText email, username, password, confirm_pass;
    private Button button;
    private User newUsr;
    private String key;
    private DatabaseHelper dbHelper;
    private Dialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        dbHelper = new DatabaseHelper(this);

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm_pass = findViewById(R.id.confirm_pass);

        button = findViewById(R.id.button);
        button.setOnClickListener( JohnySinsei -> {
            String _email = email.getText().toString().replaceAll("\\s",""),
                    _username = username.getText().toString(),
                    _password = password.getText().toString().replaceAll("\\s",""),
                    _confirm_pass = confirm_pass.getText().toString().replaceAll("\\s","");
            if (_email.length() == 0 || _username.length() == 0 || _password.length() == 0 || _confirm_pass.length() == 0) {
                Toast.makeText(this, "Please Fill Up All Fields", Toast.LENGTH_LONG).show();
                return;
            }
            if (!_password.equals(_confirm_pass)) {
                Toast.makeText(this, "Confirm Password Doesn't Match", Toast.LENGTH_LONG).show();
                return;
            }
            newUsr = new User(_email, _username, Helper.hashPassword(_password));

            if (newUsr.checkIfAlreadyExist(dbHelper)) {
                Toast.makeText(this, "Email is already taken", Toast.LENGTH_LONG).show();
                return;
            }

            sendCode();
            verify();
        });
    }

    private Button btnlogin, verifyCode;
    private EditText code;
    private TextView signin,emailName,cdown,exp,resend;
    private ImageButton closeVeriDialog;
    boolean sendAgain = false;

    public void sendCode(){
        key = Helper.randomKey(6);
        JavaMailAPI mail = new JavaMailAPI(this, email.getText().toString(), "Your verification code", key+"");
        mail.execute();
    }

    public void loading() {
        Dialog verify = new Dialog(signup.this);
        verify.setContentView(R.layout.verification_dialog);
        verify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        verify.getWindow().getAttributes().windowAnimations = R.style.diagAnim;
        code =  verify.findViewById(R.id.code);
        verifyCode = verify.findViewById(R.id.done);
        emailName = verify.findViewById(R.id.clickEmail);
        emailName.setText(email.getText().toString());
        closeVeriDialog = verify.findViewById(R.id.closeV);
        exp = verify.findViewById(R.id.expired);
        resend = verify.findViewById(R.id.resend);
        cdown =  verify.findViewById(R.id.countdown);
        //close dialog
        closeVeriDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  verify.dismiss(); } });
        //call dialog
        verify.show();
        //start time
        startTime();
    }

    public void startTime(){
        CountDownTimer start = new CountDownTimer(200000, 1000) {
            @Override
            public void onTick(long mili) {
                cdown.setText(""+(mili/1000)+" Seconds..");
                resend.setVisibility(View.GONE);
            }
            @Override
            public void onFinish() {
                sendAgain = true;
                key = "asnrf23n4roansf2urtu9fn2ur5209fdauw0r20ru5basdf";
                exp.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
                resend.setText("Resend");
                cdown.setText("Code Expired! Send Token Again!");
                cdown.setTextColor(Color.rgb(255,57,51));
            }
        };
        start.start();
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call verify again
                if(sendAgain){sendCode(); start.start();verify(); }
                else {
                    Toast.makeText(signup.this, "wait for Time to finish", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verify() {
        load = new Dialog(signup.this);
        load.setContentView(R.layout.loading);
        load.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        load.getWindow().getAttributes().windowAnimations = R.style.diagAnim;
        load.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                load.dismiss(); loading();
                verifyCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(verifyCode.getText().toString().equals("")) Toast.makeText(signup.this, "Missing code!", Toast.LENGTH_LONG).show();
                        if(code.getText().toString().equals(key)) {
                            onSuccess();
                            load.dismiss();
                            Toast.makeText(signup.this, "registration Successful", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(signup.this, "Invalid Code", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }, 2000);
    }

    public void onSuccess() {
        newUsr.setState(1);
        if (newUsr.saveState(this, dbHelper, true)) {
            // TODO YEPES ATTATCH MO HERE YUNG HOME ACTIVITY
//            Intent homeIntent = new Intent(getApplicationContext(), Home.class);
//            homeIntent.putExtra("currentUser", newUsr);
//            startActivity(homeIntent);

            // TODO YEPES EDIT loading.xml confirm_dialog.xml THEME/STYLE
        }
    }
}