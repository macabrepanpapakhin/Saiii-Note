package com.sailaminoak.saiii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import android.provider.Settings.Secure;
public class Read extends AppCompatActivity {
LinearLayout linearLayout;
SharedPreferences sharedPreferences;
boolean deleteTablesAfter=true;
SQLHelper sqlHelper;
String realFilename="";
    boolean first=true;
    int h=90;
    int helperForTime=88;
    String deviceName="Unknown Device ";
String byWho="Unknown Author";
String dateToDisplay="Unknown Date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        linearLayout=findViewById(R.id.linearLayout);
        final String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("t");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("t");
        }
        sharedPreferences =getSharedPreferences("Saii",MODE_PRIVATE);
        //Toast.makeText(this,newString,Toast.LENGTH_LONG).show();
        final ACProgressFlower dialog = new ACProgressFlower.Builder(Read.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.GREEN)
                .text("Connecting To Server...")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
        try {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            sqlHelper=new SQLHelper(Read.this,"db.sqlite",null,2);
            realFilename="Tables"+currentTime;
            try{
                boolean a=sqlHelper.queryData("CREATE TABLE " + realFilename + "( name TEXT ,image BLOB, ids INTEGER )");
            }catch (Exception e){
                Toast.makeText(Read.this,e+" error ",Toast.LENGTH_SHORT).show();
            }
            byte[] b612 = new byte[0];
             String android_id = Secure.getString(Read.this.getContentResolver(),
                    Secure.ANDROID_ID);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
            String formattedDate = df.format(c.getTime());
            sqlHelper.insertData("asdfjkl;yokelaminoak,,,"+"???"+",,,"+"???"+",,,"+"???",b612,9999,realFilename);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(newString);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            TextView textView = new TextView(Read.this);
                            final String a = dataSnapshot.child("text").getValue().toString();
                            if (a.length() == 0) continue;
                           try{ if(a.contains("asdfjkl;yokelaminoak")){
                                String[] importantData=a.split(",,,");
                                 byWho=importantData[1];
                                 dateToDisplay=importantData[2];
                                 deviceName=importantData[3];
                            }}catch (Exception e){
                               Toast.makeText(Read.this,"Can't reveive full write information",Toast.LENGTH_SHORT).show();
                           }
                           if(a.length()==0)continue;
                           if(a.contentEquals(" "))continue;
                            if (a.contains("asdfjkl;yokelaminoak")) continue;
                            if(a.contentEquals("image gonna be")){
                                ImageView imageView=new ImageView(Read.this);
                                imageView.setImageResource(R.drawable.rose);
                                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(30,15,30,15);
                                imageView.setLayoutParams(layoutParams);
                                imageView.setAdjustViewBounds(true);
                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                linearLayout.addView(imageView);
                                final int idForImage=helperForTime;
                                ++helperForTime;
                                boolean v=sqlHelper.insertData("",imageToByte(imageView),idForImage,realFilename);
                            }
                            else{
                                byte[] b = new byte[0];
                                if(sqlHelper.insertData(a,b,h,realFilename)){

                                }else{
                                    Toast.makeText(Read.this,"Error occur when inserting EDIT TEXT",Toast.LENGTH_LONG).show();
                                }
                                h++;
                                textView.setText(a);
                                textView.setTextSize(18);
                                textView.setTextColor(getResources().getColor(R.color.black));
                                linearLayout.addView(textView);
                            }

                        }
                    } else {
                        TextView textView = new TextView(Read.this);
                        textView.setText("     No Such File Exists......");
                        textView.setTextColor(Color.RED);
                        textView.setTextSize(30);
                        textView.getResources().getDrawable(R.color.tran);
                        linearLayout.addView(textView);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            TextView textView = new TextView(Read.this);
            textView.setText("Cannot connect to server. Try Again!");
            textView.setTextColor(Color.RED);
            textView.setTextSize(30);
            textView.getResources().getDrawable(R.color.tran);
            linearLayout.addView(textView);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() { dialog.dismiss();
            }
        }, 500);
        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_add:
                        Toast.makeText(Read.this,"Stored in Database",Toast.LENGTH_SHORT).show();
                        deleteTablesAfter=false;
                        break;
                    case R.id.navigation_image:
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        String temp=sharedPreferences.getString("ids",",");
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                        String formattedDate = df.format(c.getTime());
                        temp= newString+"        "+formattedDate+","+temp;
                        editor.putString("ids",temp);
                        editor.apply();
                        Toast.makeText(Read.this, "Remembered...Assume:3 ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_done:
                        if(byWho.contentEquals("asdfjkl;yokelaminoak"))
                            byWho="Unknown (May Detected Later)";

                        AlertDialog.Builder builder=new AlertDialog.Builder(Read.this);
                        builder.setTitle("Writer Information");
                        builder.setMessage("Device ID : "+byWho+"\n"+"Date : "+dateToDisplay+"\n"+"Device Name : "+deviceName);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(deleteTablesAfter){
            try{
                sqlHelper.queryData("drop table "+realFilename);
            }catch (Exception e){
                Toast.makeText(Read.this,"dropping table error",Toast.LENGTH_SHORT).show();
            }
        }else{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            String a=sharedPreferences.getString("ggg",",");
            if(a.contains(realFilename)){

            }else{
                String collle=realFilename+","+sharedPreferences.getString("ggg",",");
                editor.putString("ggg",collle);
                editor.apply();
            }
        }
        finish();
        Intent intent=new Intent(Read.this,MainActivity.class);
        startActivity(intent);
    }
    public byte[] imageToByte(ImageView image){
        Bitmap bitmap= ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
        return stream.toByteArray();
    }

}
