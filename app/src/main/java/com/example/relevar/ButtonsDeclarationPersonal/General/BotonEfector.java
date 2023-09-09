package com.example.relevar.ButtonsDeclarationPersonal.General;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;
import com.example.relevar.ManagementModule.StorageManagement.EfectoresSearchAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BotonEfector implements Serializable {
    AutoCompleteTextView autoEfector;
    PersonClass persona;
    Context context;
    ConstraintLayout efector;
    TextView avanceefector;

    public BotonEfector(Context originalContext, PersonClass originalPersona, View originalview){
        context=originalContext;
        persona=originalPersona;
        efector = (ConstraintLayout) originalview.findViewById(R.id.AVANCEEFECTOR);
        avanceefector = (TextView) originalview.findViewById(R.id.COMPLETADOEFECTOR);
    }

    public void vista(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view1 = Inflater.inflate(R.layout.alert_efector, null);
        builder.setView(view1);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // AUTOCOMPLETE TEXTVIEW DE LOS TRABAJOS
        autoEfector = view1.findViewById(R.id.AutoEfector);
        List<String> efectores = new ArrayList<String>();
        EfectoresSearchAdapter searchAdapter = new EfectoresSearchAdapter(context, efectores);
        autoEfector.setThreshold(1);
        autoEfector.setAdapter(searchAdapter);
        if(persona.Efector.length()!=0) autoEfector.setText(persona.Efector);

        final Button guardar = view1.findViewById(R.id.GUARDAREFECTOR);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persona.Efector = autoEfector.getText().toString();
                dialog.dismiss();
                ColorAvanceEfector();
            }
        });

        final Button cancelar = view1.findViewById(R.id.CANCELAREFECTOR);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final TextView AgregarManualmente = view1.findViewById(R.id.AGREGARMANUALMENTEEFECTOR);
        AgregarManualmente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EfectorManual();
            }
        });
    }

    public PersonClass returnPersona(){
        return persona;
    }

    public void ColorAvanceEfector() {
        // Cambio los colores de avance
        float avance = 0;
        if (persona.Efector.length()!=0){
            avance+=1;
        }

        if(avance==1){
            efector.setBackgroundResource(R.drawable.verde);
            avanceefector.setText(context.getString(R.string.completado)+" 100%");
        }
    }

    private void EfectorManual(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        View viewManual = Inflater.inflate(R.layout.alert_nuevo_encuestador, null);
        builder.setView(viewManual);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final TextView encabezado = viewManual.findViewById(R.id.textView4);
        encabezado.setText(R.string.nuevo_efector);

        final TextView NuevoEfector = viewManual.findViewById(R.id.EditNuevoEncuestador);
        NuevoEfector.setHint(R.string.nuevo_efector);

        final Button guardar = viewManual.findViewById(R.id.GUARDAR);
        guardar.setText(R.string.listo);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Persona.Ocupacion = NuevoTrabajo.getText().toString().toUpperCase();
                autoEfector.setText(NuevoEfector.getText().toString().toUpperCase());
                dialog.dismiss();
            }
        });

        final Button cancelar = viewManual.findViewById(R.id.CANCELAR);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}