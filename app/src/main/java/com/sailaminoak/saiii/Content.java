package com.sailaminoak.saiii;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Content#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Content extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int someStateValue;
    private final String SOME_VALUE_KEY = "someValueToSave";

    private Bundle savedState = null;
    SharedPreferences sharedPreferences;
    LinearLayout layout;
    SQLHelper sqlHelper;
    int width=0;
    EditText editText;
    int checkIfSuccess=0;
    Button buttonEnter;
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Content() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Content.
     */
    // TODO: Rename and change types and number of parameters
    public static Content newInstance(String param1, String param2) {
        Content fragment = new Content();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_content, container, false);



        view.post(new Runnable() {
            @Override
            public void run() {
                // for instance
                width = view.getMeasuredWidth();
            }
        });
        sqlHelper=new SQLHelper(getActivity(),"db.sqlite",null,2);
        sharedPreferences =this.getActivity().getSharedPreferences("Saii", Context.MODE_PRIVATE);
        layout=view.findViewById(R.id.layout);
        String zzz=sharedPreferences.getString("ggg",",");
        if(!zzz.contains("Tables")){
            Toast.makeText(getActivity(),"No File To Load",Toast.LENGTH_SHORT).show();
        }
       try{
           String[] filenames=zzz.split(",");
           for(int i=0;i<filenames.length;i++){
               if(filenames[i].length()>0){
                  addButton(filenames[i]);


               }
           }

       }catch (Exception e){
           Toast.makeText(getActivity(),"Sharedpreferences error",Toast.LENGTH_SHORT).show();
       }

        editText=view.findViewById(R.id.editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                if ((keyCode == KeyEvent.KEYCODE_ENTER && keyevent.getAction() == KeyEvent.ACTION_UP) ||(keyCode == KeyEvent.KEYCODE_SEARCH && keyevent.getAction() == KeyEvent.ACTION_UP)||(keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT && keyevent.getAction() == KeyEvent.ACTION_UP)) {

                    if(editText.getText().toString().trim().length()==0){
                        Toast.makeText(getActivity(),"Put Your Ids",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),editText.getText()+"   is sent",Toast.LENGTH_LONG).show();
                        return true;
                    }

                }
                return false;
            }
        });
        buttonEnter=view.findViewById(R.id.enter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().trim().length()==0){
                    Toast.makeText(getActivity(),"Put Your Ids",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(),editText.getText()+"   is sent",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getActivity(),Read.class);
                    intent.putExtra("t",editText.getText().toString().trim());
                    getActivity().finish();
                    startActivity(intent);
                }
            }
        });
        return view;
    }


    public void addButton(final String text){
        boolean specilaBoolean=false;
        TextView button=new TextView(getActivity());
        button.setBackgroundResource(R.drawable.beautify_button);
        button.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.paperlines, 0, 0, 0);
        button.setGravity(Gravity.CENTER);
        button.setPadding(2,2,2,2);
        try{
            Cursor res=sqlHelper.getData("select * from "+text);
            res.moveToNext();
            res.moveToNext();
            String textForView=res.getString(0);
            if(res.getString(0).length()>0){
                String variable="";

                for(int i=0;i<textForView.length() && i<20;i++){
                    variable+=textForView.charAt(i);

                }
                if(textForView.length()<20){
                    textForView=variable;
                }else
                    textForView=variable+"...";
            }
            button.setText(textForView);
            if(button.getText().toString().trim().length()==0){
                button.setText("Image");
            }

        }catch (Exception e){
            specilaBoolean=true;
            button.setText("002 : Error Code");
            sqlHelper.queryData("drop table "+text);
        }


        button.setBackgroundResource(R.drawable.beautify_button);
        button.setWidth(width-60);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 0, 30, 30);
        button.setLayoutParams(layoutParams);
        if(!specilaBoolean)layout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getActivity(),text+" will send",Toast.LENGTH_LONG).show();
                getActivity().finish();
                Intent intent=new Intent(getActivity(),LoadFromTables.class);
                intent.putExtra("t",text);
                startActivity(intent);
            }
        });





    }

}
