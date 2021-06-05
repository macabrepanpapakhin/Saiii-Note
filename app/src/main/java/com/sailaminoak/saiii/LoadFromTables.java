package com.sailaminoak.saiii;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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


public class LoadFromTables extends AppCompatActivity {
    SQLHelper sqlHelper;
    int specialId=40000;
    boolean noWordWatcher=true;
    int h = 90;
    public String editCollectorId = "";
    LinearLayout layoutForWrite;
    private static final int IMAGE_PICKER_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    String nameOfTable;
    int helper=890;
    SharedPreferences sharedPreferences;
    boolean no=true;
    int edit=0;
    Notes notes;
    int checkIfSuccess=0;
    boolean exit=false;
    boolean first=true;
    boolean checkExit=false;
    RelativeLayout relativeLayout;
    int textColor=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_from_tables);
        String newString = "";
        layoutForWrite = findViewById(R.id.layoutForWrite);
        sharedPreferences =getSharedPreferences("Saii",MODE_PRIVATE);
        int num=sharedPreferences.getInt("BackgroundColor",0);
        RelativeLayout backgroundImage=findViewById(R.id.relativeLayout);
        /*try{
            switch (num){
                case 0:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ironman));

                    break;
                case 1:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.latkhalal));

                    break;
                case 2:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.batman));

                    break;
                case 3:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.statute));

                    break;
                case 4:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.technologybackground));

                    break;
                case 5:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.robort));

                    break;
                case 6:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.depressionkaungsoelay));

                    break;
                case 7:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.girl));

                    break;

                default:
                    backgroundImage.setBackground(ContextCompat.getDrawable(this, R.drawable.oldpaper));
                    Toast.makeText(this,"Not Understanding Background"+num,Toast.LENGTH_SHORT).show();

                    break;
            }

        }catch ( Exception e){
            Toast.makeText(this,"background error and stick with oldpaper"+num,Toast.LENGTH_SHORT).show();
        }*/
       // backgroundImage.setBackground(ContextCompat.getDrawable(this,R.drawable.paperhelper));



        sqlHelper=new SQLHelper(this,"db.sqlite",null,2);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        nameOfTable="Tables"+currentTime;
        sharedPreferences =getSharedPreferences("Saii",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String collle=nameOfTable+","+sharedPreferences.getString("ggg",",");
        editor.putString("ggg",collle);
        editor.apply();
        try{
            boolean a=sqlHelper.queryData("CREATE TABLE " + nameOfTable + "( name TEXT ,image BLOB, ids INTEGER )");
        }catch (Exception e){
            Toast.makeText(this,"Error Occurs When Trying To Create Tables",Toast.LENGTH_SHORT).show();
            sqlHelper.queryData("drop table "+nameOfTable);
            String collle12=sharedPreferences.getString("ggg",",");
            String[] g2=collle12.split(",");
            String putBack2="";
            for(String ui:g2){
                if(ui.equals(newString)){

                }else{
                    if(edit==0)putBack2=putBack2+","+ui;
                    else putBack2=ui+","+putBack2;
                }
            }
            editor.putString("ggg",putBack2);
            editor.apply();
        }

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
        try{
            Cursor res=sqlHelper.getData("select * from "+newString);

            for( res.moveToFirst(); !res.isAfterLast(); res.moveToNext() ){
                Bitmap bitmap = null;
                ImageView imageView = new ImageView(this);
                byte[] a1 = res.getBlob(1);
                if (a1.length != 0) {
                    bitmap = BitmapFactory.decodeByteArray(a1, 0, a1.length);
                    imageView.setImageBitmap(bitmap);
                    addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                } else {
                    addEditText(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, res.getString(0));

                }
            }

            no=false;
           sqlHelper.queryData("drop table "+newString);
            SharedPreferences.Editor editor1=sharedPreferences.edit();
            String collle1=sharedPreferences.getString("ggg",",");
            String[] g=collle1.split(",");
            String putBack="";
           for(String ui:g){
                if(ui.equals(newString)){
                }else{
                    if(edit==0)putBack=putBack+","+ui;
                    else putBack=ui+","+putBack;
                }
            }
           putBack+=nameOfTable;
            editor.putString("ggg",putBack);
            editor.apply();



        }catch (Exception e){
            Toast.makeText(LoadFromTables.this, "error from your page", Toast.LENGTH_SHORT).show();
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_add:
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference(nameOfTable);

                        myRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()) {
                                                                       exit=true;
                                                            }else{

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                        if(exit){
                            myRef.removeValue();
                        }

                            Cursor res=sqlHelper.getData("select * from "+nameOfTable);
                        final ACProgressFlower dialog = new ACProgressFlower.Builder(LoadFromTables.this)
                                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                .themeColor(Color.GREEN)
                                .text("Uploading")
                                .fadeColor(Color.DKGRAY).build();
                        dialog.show();

                        for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext() ){
                            Bitmap bitmap = null;
                            byte[] a1 = res.getBlob(1);
                            if (a1.length != 0) {
                                notes=new Notes();
                                notes.setText("image gonna be");
                                myRef.push().setValue(notes);
                            } else {
                                notes=new Notes();
                                notes.setText(res.getString(0));
                                myRef.push().setValue(notes);
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                               dialog.dismiss();
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                String temp=sharedPreferences.getString("ids",",");
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                                String formattedDate = df.format(c.getTime());
                                temp= nameOfTable+"        "+formattedDate+","+temp;
                                editor.putString("ids",temp);
                                editor.apply();
                                Toast.makeText(LoadFromTables.this, "Successfully Uploaded : Assume:3 ", Toast.LENGTH_SHORT).show();
                            }
                        }, 2000);

                        break;
                    case R.id.navigation_image:
                        AddImage();
                        break;
                    case R.id.navigation_done:
                        doSomethingForSave();
                        display("Updated");
                        break;
                }
                return true;
            }
        });

    }

    public void AddImage() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);

            } else {
                pickImageFromGallery();
            }
        } else {
            pickImageFromGallery();
        }
    }

    public void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_CODE);
        edit=10;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                    edit=10;

                } else {
                    Toast.makeText(this, "Permission Need To Be Allowed To Use This Feature", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICKER_CODE) {
            ImageView imageV = new ImageView(LoadFromTables.this);
            imageV.setImageURI(data.getData());
            edit=10;
            addView(imageV, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(!no){
                addEditText(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,"");
                edit=10;
            }
        }
    }
    public void addEditText(int width, int height,String text){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(0,10,0,10);
        final EditText a=new EditText(LoadFromTables.this);
        a.setBackgroundResource(android.R.color.transparent);
        a.setId(h);
        a.setAutoLinkMask(Linkify.WEB_URLS);
        a.setMovementMethod(LinkMovementMethod.getInstance());
        a.setLinksClickable(false);
        Linkify.addLinks(a, Linkify.WEB_URLS);
        a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                Linkify.addLinks(s, Linkify.WEB_URLS);

            }
        });
        int color=sharedPreferences.getInt("textColor",R.color.yellowBackground);
        a.setTextColor(color);
        byte[] b = new byte[0];
        String yourString = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString();
        a.setText(yourString);
        sqlHelper.insertData(a.getText().toString().trim(),b,h,nameOfTable);
        editCollectorId=editCollectorId+","+h;
        ++h;
        a.setLayoutParams(layoutParams);
        try{
        if(a.getText().toString().trim().contains("asdfjkl;yokelaminoak")){
            String[] importantData=a.getText().toString().trim().split(",,,");
            textColor=Integer.parseInt(importantData[4]);

        }
        a.setTextColor(textColor);
        }catch (Exception e){
         // display("textColor retrieve error");
        }
        try{
            a.setTextColor(textColor);
        }catch (Exception e){
           display("Text Color Error");
        }
        EditText et=new EditText(this);
        et.setText("");
        if(a.getText().toString().trim().contains("asdfjkl;yokelaminoak")){
            layoutForWrite.addView(et);
        }
        else layoutForWrite.addView(a);



    }
    public void addView(ImageView imageV, int width, int height){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(30,0,30,0);
        imageV.setLayoutParams(layoutParams);
        imageV.setAdjustViewBounds(true);
        imageV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        layoutForWrite.addView(imageV);
        sqlHelper.insertData("",imageToByte(imageV),++helper,nameOfTable);
        ++helper;
    }
    public byte[] imageToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }
    public void doSomethingForSave(){
        boolean test=false;
        String[] ids=editCollectorId.split(",");
        for(int i=0;i<ids.length;i++){
            if(ids[i].length()>0) {
              try{
                  int z= Integer.valueOf(ids[i]);

                EditText a=findViewById(z);
                String newName=a.getText().toString().trim();
                test=  sqlHelper.update(newName,z,nameOfTable);}catch (Exception e){
                  //salsfalsjflasef
              }
            }

        }

        edit=10;
    }
    public void onBackPressed() {
        super.onBackPressed();
        save();
        Intent intent=new Intent(LoadFromTables.this,MainActivity.class);
        startActivity(intent);
        finish();
        display("automatically updated");
        return;
    }

    public void save(){
        editCollectorId=specialId+","+editCollectorId;
        String[] ids=editCollectorId.split(",");
        for(int i=0;i<ids.length;i++){
            if(ids[i].length()>0) {
                int z= Integer.valueOf(ids[i]);
                try{
                    EditText a=findViewById(z);
                    String newName=a.getText().toString().trim();
                    if(newName.length()!=0)noWordWatcher=false;
                    sqlHelper.update(newName,z,nameOfTable);
                }catch (Exception e){
                    //no error actually , just catch for the remove view or view doesn't exists.
                }
            }
        }
        boolean success = false;
        if(noWordWatcher){
            success=true;
        }
        if(!success)
        {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            String a=sharedPreferences.getString("ggg",",");
            if(a.contains(nameOfTable)){

            }else{
                String collle=nameOfTable+","+sharedPreferences.getString("ggg",",");
                editor.putString("ggg",collle);
                editor.apply();
            }
        }
        else
        {
            Toast.makeText(LoadFromTables.this,"Empty blank will save",Toast.LENGTH_SHORT).show();
        }

    }
    public void display(String str)
    {
        Toast.makeText(LoadFromTables.this,str,Toast.LENGTH_SHORT).show();
    }

}
