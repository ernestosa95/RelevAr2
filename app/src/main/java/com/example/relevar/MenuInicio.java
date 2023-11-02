package com.example.relevar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

// Descripcion de la Activity:

//--------------------------------------------------------------------------------------------------
public class MenuInicio extends AppCompatActivity {

    ViewFlipper v_fliper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);


        // Eliminar el action bar
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.hide();

        // Evitar la rotacion
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        }

        int images[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
        v_fliper = findViewById(R.id.v_fliper);

        for (int image : images){
            fliperImages(image);
        }
    }


    public void fliperImages(int image){

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v_fliper.addView(imageView);
        v_fliper.setFlipInterval(5000);
        v_fliper.setAutoStart(true);
        v_fliper.setInAnimation(this, R.anim.nav_default_enter_anim);
        v_fliper.setOutAnimation(this, R.anim.nav_default_enter_anim);


    }
}
