package com.sailaminoak.saiii;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rtugeek.android.colorseekbar.ColorSeekBar;

import worker8.com.github.radiogroupplus.RadioGroupPlus;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RadioGroupPlus radioGroupPlus;
    Button button;
    SharedPreferences sharedPreferences;
    ColorSeekBar colorSeekBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view= inflater.inflate(R.layout.fragment_setting, container, false);
       sharedPreferences =this.getActivity().getSharedPreferences("Saii", Context.MODE_PRIVATE);
       radioGroupPlus=view.findViewById(R.id.radio_group);
       button=view.findViewById(R.id.apply);
       colorSeekBar=view.findViewById(R.id.color_seek_bar);

       colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
           @Override
           public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
               SharedPreferences.Editor editor=sharedPreferences.edit();
               editor.putInt("textColor",color);
               editor.apply();
               button.setTextColor(color);

           }
       });

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int number=0;
               try{
                   number=radioGroupPlus.getCheckedRadioButtonId();
                   switch (number){
                       case R.id.ironman:
                           SharedPreferences.Editor editor=sharedPreferences.edit();
                           editor.putInt("BackgroundColor",0);
                           editor.apply();
                          // Toast.makeText(getActivity(),"iron man",Toast.LENGTH_SHORT).show();
                           jump();
                           break;
                       case R.id.batman:
                           SharedPreferences.Editor editor1=sharedPreferences.edit();
                           editor1.putInt("BackgroundColor",2);
                           editor1.apply();
                           //Toast.makeText(getActivity(),"batman",Toast.LENGTH_SHORT).show();
                           jump();
                           break;
                       case R.id.statue:
                           SharedPreferences.Editor editor2=sharedPreferences.edit();
                           editor2.putInt("BackgroundColor",3);
                           editor2.apply();
                           //Toast.makeText(getActivity(),"Statue",Toast.LENGTH_SHORT).show();
                           jump();
                           break;
                       case R.id.technology:
                           SharedPreferences.Editor editor3=sharedPreferences.edit();
                           editor3.putInt("BackgroundColor",4);
                           editor3.apply();
                          // Toast.makeText(getActivity()," 0 1 ",Toast.LENGTH_SHORT).show();
                           jump();
                           break;
                       case R.id.latkhalal:
                           SharedPreferences.Editor editor4=sharedPreferences.edit();
                           editor4.putInt("BackgroundColor",1);
                           editor4.apply();
                           jump();
                           //Toast.makeText(getActivity(),"Fuck You abcdefg ",Toast.LENGTH_SHORT).show();
                           break;
                       case R.id.robert:
                           SharedPreferences.Editor editor5=sharedPreferences.edit();
                           editor5.putInt("BackgroundColor",5);
                           editor5.apply();
                           jump();
                          // Toast.makeText(getActivity(),"Robert",Toast.LENGTH_SHORT).show();
                           break;
                       case R.id.depressionkaungsoelay:
                           SharedPreferences.Editor editor6=sharedPreferences.edit();
                           editor6.putInt("BackgroundColor",6);
                           editor6.apply();
                           jump();
                          // Toast.makeText(getActivity(),"Depression Kaung Soe Lay",Toast.LENGTH_SHORT).show();
                           break;
                       case R.id.girl:
                           SharedPreferences.Editor editor7=sharedPreferences.edit();
                           editor7.putInt("BackgroundColor",7);
                           editor7.apply();
                           jump();
                           break;

                       default:Display("Color will change when new note page created");
                           jump();
                           break;
                   }
               }catch (Exception e){
                   Toast.makeText(getActivity(),"not checking yet",Toast.LENGTH_SHORT).show();
               }
           }
       });


       return view;
    }
    public void jump(){
        Intent intent=new Intent(getActivity(),MainActivity.class);
        getActivity().finish();
        startActivity(intent);
        Display("Applied Successful");


    }
    public void Display(String str){
        Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
    }
}
