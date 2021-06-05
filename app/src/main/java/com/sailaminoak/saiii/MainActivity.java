package com.sailaminoak.saiii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    private FrameLayout frameLayout;
    public SQLHelper sqlHelper;
    private int someStateValue;
    private final String SOME_VALUE_KEY = "someValueToSave";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.chipNavigation);
        frameLayout = findViewById(R.id.container);
        sqlHelper=new SQLHelper(this,"db.sqlite",null,2);
        //chipNavigationBar.setItemSelected(R.id.newnote,true);
        chipNavigationBar.setItemSelected(R.id.newnote,true);
        //chipNavigationBar.setSelectedItemId(R.id.newnote);
        String tags="";
        fragment=new NewNote();

        getSupportFragmentManager().beginTransaction().add(R.id.container,fragment,"newnote").commit();
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                switch (i){
                    case R.id.newnote:
                        if(fragmentManager.findFragmentByTag("newnote")!=null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("newnote")).commit();
                        } else{
                            fragmentManager.beginTransaction().add(R.id.container, new NewNote(), "newnote").commit();
                        }

                        if(fragmentManager.findFragmentByTag("content")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("content")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("setting")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("setting")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("database")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("database")).commit();
                        }



                        //fragment=new NewNote();
                        //getSupportFragmentManager().beginTransaction().add(R.id.container,fragment,"newnote").commit();
                        break;
                    case R.id.content:
                        fragmentManager.beginTransaction().add(R.id.container, new Content(), "content").commit();
                        if(fragmentManager.findFragmentByTag("newnote")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("newnote")).commit();
                        } if(fragmentManager.findFragmentByTag("setting")!=null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("setting")).commit();
                    }
                        if(fragmentManager.findFragmentByTag("database")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("database")).commit();
                        }




                        //getSupportFragmentManager().beginTransaction().add(R.id.container,fragment,"content").commit();

                        break;
                    case R.id.database:
                        if(fragmentManager.findFragmentByTag("database")!=null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("database")).commit();
                        }
                        else{
                            fragmentManager.beginTransaction().add(R.id.container, new Database(), "database").commit();
                        }
                        if(fragmentManager.findFragmentByTag("content")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("content")).commit();
                        } if(fragmentManager.findFragmentByTag("setting")!=null){
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("setting")).commit();
                    }
                        if(fragmentManager.findFragmentByTag("newnote")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("newnote")).commit();
                        }

                        // getSupportFragmentManager().beginTransaction().add(R.id.container,fragment,"database").commit();
                        break;
                    case R.id.setting:
                        if(fragmentManager.findFragmentByTag("setting")!=null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("setting")).commit();
                        }else{
                            fragmentManager.beginTransaction().add(R.id.container, new Setting(), "setting").commit();
                        }
                        if(fragmentManager.findFragmentByTag("content")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("content")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("database")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("database")).commit();
                        }
                        if(fragmentManager.findFragmentByTag("newnote")!=null){
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("newnote")).commit();
                        }

                        //getSupportFragmentManager().beginTransaction().add(R.id.container,fragment,"setting").commit();
                        break;
                }
            }
        });



    }
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SOME_VALUE_KEY, someStateValue);
        super.onSaveInstanceState(outState);
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press one more time to exit Saiii", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
