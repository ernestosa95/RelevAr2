package com.example.relevar.ButtonsDeclarationPersonal.General;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.ManagementModule.StorageManagement.BDData;
import com.example.relevar.R;

import java.io.Serializable;
import java.util.HashMap;

public class BotonColon implements Serializable {
    Context context;
    ConstraintLayout layout;
    TextView avanceTxt;
    BDData adminBData;

    public BotonColon(Context originalContext, View view){
        context=originalContext;
        adminBData = new BDData(context, "BDData", null, 1);
        layout = (ConstraintLayout) view.findViewById(R.id.AVANCEGNRL);
        avanceTxt = (TextView) view.findViewById(R.id.COMPLETEGNRL);
    }

    public void vista(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        final View view_alert = Inflater.inflate(R.layout.alert_colon, null);
        builder.setView(view_alert);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        HashMap<String, String> data = new HashMap<>();
        BDData admin = new BDData(context, "BDData", null, 1);
        data.put(context.getString(R.string.antecedentes_cancer_colon),"");
        data.putAll(admin.SearchDataCache());
        //Toast.makeText(context, data.get(context.getString(R.string.antecedentes_cancer_colon)), Toast.LENGTH_SHORT).show();

        LinearLayout filter = view_alert.findViewById(R.id.FILTERPERSON);
        LinearLayout prueba = view_alert.findViewById(R.id.PRUEBACOLON);
        prueba.setVisibility(View.GONE);

        RadioButton siEdad = view_alert.findViewById(R.id.SIEDAD);
        RadioButton noEdad = view_alert.findViewById(R.id.NOEDAD);

        CheckBox sangrado = view_alert.findViewById(R.id.CHECKSANGRADO);
        CheckBox peso = view_alert.findViewById(R.id.CHECKPESO);
        CheckBox fecal = view_alert.findViewById(R.id.CHECKFECAL);

        RadioButton siAntecedentes = view_alert.findViewById(R.id.SIANTECEDENTES);
        RadioButton noAntecedentes = view_alert.findViewById(R.id.NOANTECEDENTES);

        EditText porque = view_alert.findViewById(R.id.EDITNOCOLON);
        porque.setVisibility(View.GONE);
        RadioButton siPrueba = view_alert.findViewById(R.id.SIPRUEBA);
        RadioButton noPrueba = view_alert.findViewById(R.id.NOPRUEBA);
        noPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                porque.setVisibility(View.VISIBLE);
            }
        });

        // PRELLENADO DE LOS DATOS EN EDITAR
        // en caso de si relizo la prueba
        if (data.containsKey(context.getString(R.string.prueba_cancer_colon)) &&
                data.get(context.getString(R.string.prueba_cancer_colon)) != null) {
            if (data.get(context.getString(R.string.prueba_cancer_colon)).equals("SI")) {
                siEdad.setChecked(true);
                noAntecedentes.setChecked(true);
                siPrueba.setChecked(true);
            }
        }

        if (data.containsKey(context.getString(R.string.antecedentes_cancer_colon))) {
            if(data.get(R.string.antecedentes_cancer_colon) != null) {
                if (data.get(context.getString(R.string.antecedentes_cancer_colon)).equals("SI")) {
                    siAntecedentes.setChecked(true);
                }
                if (data.get(context.getString(R.string.antecedentes_cancer_colon)).equals("NO")) {
                    noAntecedentes.setChecked(true);
                }
            }
        }

        // sintomas
        if (data.containsKey(context.getString(R.string.sintomas_cancer_colon))) {
            if (data.get(context.getString(R.string.sintomas_cancer_colon)) != null) {
                if (data.get(context.getString(R.string.sintomas_cancer_colon)).contains(sangrado.getText().toString())) {
                    sangrado.setChecked(true);
                }
                if (data.get(context.getString(R.string.sintomas_cancer_colon)).contains(peso.getText().toString())) {
                    peso.setChecked(true);
                }
                if (data.get(context.getString(R.string.sintomas_cancer_colon)).contains(fecal.getText().toString())) {
                    fecal.setChecked(true);
                }
            }
        }

        // porque no realizo
        if (data.containsKey(context.getString(R.string.razon_no_prueba_colon)) &&
                data.get(context.getString(R.string.prueba_cancer_colon)) != null) {
            if (data.get(context.getString(R.string.prueba_cancer_colon)).equals("NO") &&
                    data.containsKey(context.getString(R.string.razon_no_prueba_colon))) {
                porque.setText(data.get(context.getString(R.string.razon_no_prueba_colon)));
            }
        }

        final Button siguiente = view_alert.findViewById(R.id.SIGUIENTE);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (siEdad.isChecked() && !sangrado.isChecked() && !peso.isChecked() && !fecal.isChecked() && noAntecedentes.isChecked()) {
                        filter.setVisibility(View.GONE);
                        prueba.setVisibility(View.VISIBLE);
                        siguiente.setVisibility(View.GONE);
                    } else {
                        if (sangrado.isChecked() || peso.isChecked() || fecal.isChecked() || noEdad.isChecked() || siAntecedentes.isChecked()) {
                            String strSintomas = "";
                            if (sangrado.isChecked()) {
                                strSintomas += sangrado.getText().toString();
                            }
                            if (peso.isChecked()) {
                                if (!strSintomas.equals("")){
                                    strSintomas += ",";
                                }
                                strSintomas += peso.getText().toString();
                            }
                            if (fecal.isChecked()) {
                                if (!strSintomas.equals("")){
                                    strSintomas += ",";
                                }
                                strSintomas += fecal.getText().toString();
                            }
                            data.put(context.getString(R.string.sintomas_cancer_colon), strSintomas);

                            if (siAntecedentes.isChecked()) {
                                data.put(context.getString(R.string.antecedentes_cancer_colon), "SI");
                            }
                            data.put(context.getString(R.string.prueba_cancer_colon), "NO");

                            adminBData.insertDataCache(data);
                            dialog.dismiss();
                            ColorAvance(data);

                            if (noEdad.isChecked()){
                                Toast.makeText(context, "NO ESTA ENTRE LA POBLACION OBJETIVO", Toast.LENGTH_SHORT).show();
                            }
                            if (sangrado.isChecked() || peso.isChecked() || fecal.isChecked() || siAntecedentes.isChecked()){
                                Toast.makeText(context, "LA PERSONA NO ESA CONTEMPLADA DENTRO DE ESTA CAMPAÑA", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            }
        });

        final Button guardar = view_alert.findViewById(R.id.GUARDARCOLON);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (siPrueba.isChecked()) {
                        data.put(context.getString(R.string.prueba_cancer_colon), "SI");
                        //persona.save(context.getString(R.string.prueba_cancer_colon), "SI");
                        //Toast.makeText(context, data.get(context.getString(R.string.prueba_cancer_colon)) + "boton", Toast.LENGTH_SHORT).show();
                        adminBData.insertDataCache(data);
                        ColorAvance(data);
                        dialog.dismiss();

                    } else {
                        if (noPrueba.isChecked()) {
                            data.put(context.getString(R.string.prueba_cancer_colon), "NO");
                            //persona.save(context.getString(R.string.prueba_cancer_colon),"NO");
                            if (porque.getText().toString().length() != 0) {
                                data.put(context.getString(R.string.razon_no_prueba_colon), porque.getText().toString());
                                //persona.save(context.getString(R.string.razon_no_prueba_colon),porque.getText().toString())
                            }
                            adminBData.insertDataCache(data);
                            ColorAvance(data);
                            dialog.dismiss();
                        }
                    }
            }
        });

        final Button cancelar = view_alert.findViewById(R.id.CANCELARCOLON);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void ColorAvance(HashMap<String,String> data) {
        if (data!=null) {
            // Cambio los colores de avance
            float avance = 0;

            if (data.get(context.getString(R.string.sintomas_cancer_colon)) != null &&
                    data.get(context.getString(R.string.sintomas_cancer_colon)).length() != 0) {
                avance += 1;
            }
            if (data.get(context.getString(R.string.razon_no_prueba_colon)) != null &&
                    data.get(context.getString(R.string.razon_no_prueba_colon)).length() != 0) {
                avance += 1;
            }
            if (data.get(context.getString(R.string.antecedentes_cancer_colon)) != null &&
                    data.get(context.getString(R.string.antecedentes_cancer_colon)).length() != 0) {
                avance += 1;
                //Toast.makeText(context,"|"+ data.get(context.getString(R.string.antecedentes_cancer_colon))+"|", Toast.LENGTH_SHORT).show();

            }
            if (data.get(context.getString(R.string.prueba_cancer_colon)) != null &&
                    data.get(context.getString(R.string.prueba_cancer_colon)).length() != 0) {
                if (data.get(context.getString(R.string.prueba_cancer_colon)) == "SI") {
                    avance = 4;
                } else {
                    avance += 1;
                }
            }

            if (avance > 0 && avance < 4) {
                layout.setBackgroundResource(R.drawable.amarillo);
                double porcentaje = Math.round((avance / 4) * 100);
                String aux = context.getString(R.string.completado) + " " + Double.toString(porcentaje) + "%";
                avanceTxt.setText(aux);
            }

            if (avance == 0) {
                layout.setBackgroundResource(R.drawable.rojo);
                avanceTxt.setText(context.getString(R.string.completado) + " 00%");
            }

            if (avance == 4) {
                layout.setBackgroundResource(R.drawable.verde);
                avanceTxt.setText(context.getString(R.string.completado) + " 100%");
            }
        }
    }
}