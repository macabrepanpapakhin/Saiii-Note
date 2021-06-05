package com.sailaminoak.saiii;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewNote extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int someStateValue;
    private final String SOME_VALUE_KEY = "someValueToSave";
    boolean first=true;
    LinearLayout linearLayout;
    int h=90;
    int helperForTime=88;
    SQLHelper sqlHelper;
    int specialId=40000;
    public String editCollectorId="";
    LinearLayout layoutForWrite;
    EditText editText;
    SharedPreferences sharedPreferences;
    private static  final int IMAGE_PICKER_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    String realFilename="";
    boolean noWordWatcher=true;
    int realWidth=0;
    int realHeight=0;
    int count=0;
    RelativeLayout relativeLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    int textColor;
    String nickiminaj="";


    public NewNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewNote.
     */
    // TODO: Rename and change types and number of parameters
    public static NewNote newInstance(String param1, String param2) {
        NewNote fragment = new NewNote();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SOME_VALUE_KEY, someStateValue);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_content,container,false);
        sharedPreferences =this.getActivity().getSharedPreferences("Saii", Context.MODE_PRIVATE);
        int num=sharedPreferences.getInt("BackgroundColor",0);
        textColor=sharedPreferences.getInt("textColor",R.color.colorAccent);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        sqlHelper=new SQLHelper(getActivity(),"db.sqlite",null,2);
        realFilename="Tables"+currentTime;
        Toast.makeText(getActivity(),"New Note Created",Toast.LENGTH_SHORT).show();
        try{
            boolean a=sqlHelper.queryData("CREATE TABLE " + realFilename + "( name TEXT ,image BLOB, ids INTEGER )");
        }catch (Exception e){
            Toast.makeText(getActivity(),e+" error ",Toast.LENGTH_SHORT).show();
        }
        view = inflater.inflate(R.layout.fragment_new_note, container, false);
        linearLayout = view.findViewById(R.id.linearLayout);
        RelativeLayout backgroundImage =view.findViewById(R.id.backgroundImage);
        try{
            switch (num){
                case 0:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;
                case 1:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;
                case 2:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;
                case 3:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;
                case 4:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;
                case 5:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;
                case 6:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;
                case 7:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));

                    break;

                default:
                    backgroundImage.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.girl));
                    Toast.makeText(getActivity(),"Not Understanding Background"+num,Toast.LENGTH_SHORT).show();

                    break;
            }

        }catch ( Exception e){
            Toast.makeText(getActivity(),"background error and stick with oldpaper"+num,Toast.LENGTH_SHORT).show();
        }


        byte[] b612 = new byte[0];
       String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String formattedDate = df.format(c.getTime());
        nickiminaj="asdfjkl;yokelaminoak,,,"+android_id+",,,"+formattedDate+",,,"+getDeviceName();
        sqlHelper.insertData(nickiminaj,b612,9999,realFilename);
        addEditText(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        first=false;
        BottomNavigationView bottomNavigationView=view.findViewById(R.id.chipNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_add:
                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.replace(R.id.container,new NewNote(),"newnote");
                        tr.commit();
                        break;
                    case R.id.navigation_image:
                        if (ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    2000);
                        } else {
                            startGallery();
                        }
                        break;
                    case R.id.navigation_done:
                        save();
                        break;
                }
                return true;
            }
        });


        return view;

    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageBitmap(bitmapImage);
                addView(imageView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                addEditText(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        }
    }
    public void addView(ImageView imageV, int width, int height){
        first=false;
        try{
            EditText e=getActivity().findViewById(specialId);
            e.setHint("");

        }catch (Exception e){
            Toast.makeText(getActivity(),"unsuccessful deleting hint",Toast.LENGTH_LONG).show();
        }
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(30,0,30,0);
        imageV.setLayoutParams(layoutParams);
        imageV.setAdjustViewBounds(true);
        imageV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        linearLayout.addView(imageV);
        final int idForImage=helperForTime;
        ++helperForTime;
        noWordWatcher=false;
        boolean v=sqlHelper.insertData("",imageToByte(imageV),idForImage,realFilename);
    }
    public byte[] imageToByte(ImageView image){
        Bitmap bitmap= ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
        return stream.toByteArray();
    }

    public void addEditText(int width, int height){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(30,0,30,0);
        final EditText a=new EditText(getActivity());
        a.setLayoutParams(layoutParams);
        a.setBackgroundResource(android.R.color.transparent);
        a.setId(h);
        a.requestFocus();
        a.setTextColor(textColor);
        //setKeyboardFocus(a);
        if(first){
            a.setHint("Start writing your note please hehe...");
            a.setId(specialId);

        }

        a.setAutoLinkMask(Linkify.WEB_URLS);
        a.setMovementMethod(LinkMovementMethod.getInstance());
        a.setLinksClickable(false);
        Linkify.addLinks(a, Linkify.WEB_URLS);
        a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textColor=sharedPreferences.getInt("textColor",R.color.colorAccent);
                a.setTextColor(textColor);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                Linkify.addLinks(s, Linkify.WEB_URLS);
            }
        });
        byte[] b = new byte[0];
        int temp=0;
        if(first){
            temp=specialId;
        }else{
            temp=h;
        }
        if(a.getText().toString().trim().length()!=0)noWordWatcher=false;
        if(sqlHelper.insertData(a.getText().toString().trim(),b,temp,realFilename)){
            //Toast.makeText(getActivity(),"success inserting edittext",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"Error occur when inserting EDIT TEXT",Toast.LENGTH_LONG).show();
        }
        editCollectorId=editCollectorId+","+h;
        ++h;
        a.setLayoutParams(layoutParams);
        linearLayout.addView(a);
    }
    public void save(){
        count++;
        editCollectorId=specialId+","+editCollectorId;
        String[] ids=editCollectorId.split(",");
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String formattedDate = df.format(c.getTime());
        nickiminaj="asdfjkl;yokelaminoak,,,"+android_id+",,,"+formattedDate+",,,"+getDeviceName()+",,,"+textColor;
        sqlHelper.update(nickiminaj,9999,realFilename);
        for(int i=0;i<ids.length;i++){
            if(ids[i].length()>0) {
                int z= Integer.valueOf(ids[i]);
                try{
                    EditText a=view.findViewById(z);
                    String newName=a.getText().toString().trim();
                    if(newName.length()!=0)noWordWatcher=false;
                    sqlHelper.update(newName,z,realFilename);
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
                if(a.contains(realFilename)){

                }else{
                    String collle=realFilename+","+sharedPreferences.getString("ggg",",");
                    editor.putString("ggg",collle);
                    editor.apply();
                }
                Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"Can't save empty blank",Toast.LENGTH_SHORT).show();
        }

    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }

}
