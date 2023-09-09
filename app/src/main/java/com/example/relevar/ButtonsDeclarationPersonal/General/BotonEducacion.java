package com.example.relevar.ButtonsDeclarationPersonal.General;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;

import java.io.Serializable;

public class BotonEducacion implements Serializable {
    PersonClass persona;
    Context context;
    ConstraintLayout layout_educacion;
    TextView avanceeducacion;

    public BotonEducacion(Context originalContext, PersonClass originalPersona, View originalview){
        context=originalContext;
        persona=originalPersona;
        layout_educacion = (ConstraintLayout) originalview.findViewById(R.id.AVANCEEDUCACION);
        avanceeducacion = (TextView) originalview.findViewById(R.id.COMPLETADOEDUCACION);
    }

    public void vista(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        final View view_alert = Inflater.inflate(R.layout.alert_test_hpv, null);
        View view1 = Inflater.inflate(R.layout.alert_educacion, null);
        builder.setView(view1);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final RadioGroup Educacion = view1.findViewById(R.id.GroupEducacion);
        if(persona.Educacion.length()!=0){
            for(int i=0; i<Educacion.getChildCount(); i++){
                RadioButton rb = (RadioButton) Educacion.findViewById(Educacion.getChildAt(i).getId());
                if(rb.getText().toString().split(" ")[0].equals(persona.Educacion.split(" ")[0])){
                    rb.setChecked(true);
                }
            }}

        final RadioButton primaria = view1.findViewById(R.id.PRIMARIO);
        primaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletoIncompletoCursando(primaria.getText().toString());
                dialog.dismiss();
            }
        });
        final RadioButton secundaria = view1.findViewById(R.id.SECUNDARIO);
        secundaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletoIncompletoCursando(secundaria.getText().toString());
                dialog.dismiss();
            }
        });
        final RadioButton terciaria = view1.findViewById(R.id.TERCIARIO);
        terciaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletoIncompletoCursando(terciaria.getText().toString());
                dialog.dismiss();
            }
        });
        final RadioButton universidad = view1.findViewById(R.id.UNIVERSIDAD);
        universidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletoIncompletoCursando(universidad.getText().toString());
                dialog.dismiss();
            }
        });

        final Button guardar = view1.findViewById(R.id.GUARDAREDUCACION);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) Educacion.findViewById(Educacion.getCheckedRadioButtonId());
                if(Educacion.findViewById(Educacion.getCheckedRadioButtonId())!=null){
                    persona.Educacion = rb.getText().toString();}
                dialog.dismiss();
                ColorAvanceEducacion();
            }
        });
    }

    public PersonClass returnPersona(){
        return persona;
    }

    public void ColorAvanceEducacion() {
        if (persona.Educacion.length()!=0){
            layout_educacion.setBackgroundResource(R.drawable.verde);
            avanceeducacion.setText(context.getString(R.string.completado)+" 100%");
        }
    }

    private void CompletoIncompletoCursando(final String nivel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view1 = Inflater.inflate(R.layout.alert_educacion_completitud, null);
        builder.setView(view1);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final RadioGroup completitud = view1.findViewById(R.id.COMPLETITUD);

        final Button guardar = view1.findViewById(R.id.GUARDARCOMPLETITUD);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(completitud.findViewById(completitud.getCheckedRadioButtonId())!=null){
                    RadioButton rb = (RadioButton) completitud.findViewById(completitud.getCheckedRadioButtonId());
                    persona.Educacion = nivel +" "+ rb.getText().toString();}
                ColorAvanceEducacion();
            }
        });
    }
}