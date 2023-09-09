package com.example.relevar.ManagementModule.StastisticsManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.relevar.ManagementModule.StorageManagement.BDData;
import com.example.relevar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        TextView txt = findViewById(R.id.TXTSTATISTICS);
        TextView mas = findViewById(R.id.TXTMASCULINO);

        JSONObject piramide = new JSONObject();
        BDData adminBDData = new BDData(getBaseContext(), "BDData", null, 1);
        try {
            piramide = adminBDData.PiramidePoblacional();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        String show = "";
        try {
            Iterator<String> keys = piramide.getJSONObject("F").keys();
            show = "F\n";
            for (Iterator<String> it = keys; it.hasNext(); ) {
                String k = it.next();
                show += k +": "+piramide.getJSONObject("F").get(k)+"\n";
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        txt.setText(show);

        show = "";
        try {
            Iterator<String> keys = piramide.getJSONObject("M").keys();
            show = "M\n";
            for (Iterator<String> it = keys; it.hasNext(); ) {
                String k = it.next();
                show += k +": "+piramide.getJSONObject("M").get(k)+"\n";
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        mas.setText(show);
    }
}