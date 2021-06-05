package com.sailaminoak.saiii;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Database#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Database extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences sharedPreferences;
    LinearLayout linearLayout;
    private FragmentToActivity mCallback;
    //SharedPreferences sharedPreferences;
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Database() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Database.
     */
    // TODO: Rename and change types and number of parameters
    public static Database newInstance(String param1, String param2) {
        Database fragment = new Database();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_database, container, false);
        sharedPreferences =getActivity().getSharedPreferences("Saii",getContext().MODE_PRIVATE);
        helper();

        return view;
    }
@SuppressLint("ResourceAsColor")
public  void helper(){
    SharedPreferences.Editor editor=sharedPreferences.edit();
    String temp=sharedPreferences.getString("ids",",");
    linearLayout=view.findViewById(R.id.linearLayout);
    String[] idds=temp.split(",");

    int count=0;
    for(final String i: idds){
        if(i.length()>0){
            TextView textView=new TextView(getActivity());
            textView.setText(i);
            textView.setTextColor(R.color.colorSaiNote);
            String id="";
            for(int h=0;h<i.length();h++){
                if(i.charAt(h)==' '){
                    break;
                }else{
                    id+=i.charAt(h);
                }
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(30,30,0,30);
            textView.setLayoutParams(layoutParams);
            final String finalId1 = id;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    Intent intent=new Intent(getActivity(),Read.class);
                    intent.putExtra("t",finalId1);
                    startActivity(intent);
                }
            });
            linearLayout.addView(textView);
            count++;
        }
    }
}

}
