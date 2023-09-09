package com.example.relevar.ButtonsDeclarationPersonal.PsicoSocial;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;

import java.io.Serializable;

public class BotonAcompañamiento implements Serializable {
    PersonClass persona;
    Context context;
    ConstraintLayout layout_acompañamiento;
    TextView avanceacompañamiento;

    public BotonAcompañamiento(Context originalContext, PersonClass originalPersona, View originalview){
        context=originalContext;
        persona=originalPersona;
        layout_acompañamiento = (ConstraintLayout) originalview.findViewById(R.id.AVANCEACOMPAÑAMIENTO);
        avanceacompañamiento = (TextView) originalview.findViewById(R.id.COMPLETADOACOMPAÑAMIENTO);
    }

    public void vista(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view1 = Inflater.inflate(R.layout.alert_acompanamiento, null);
        builder.setView(view1);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final CheckBox psicologico = view1.findViewById(R.id.PSICOLOGICO);
        final CheckBox medico = view1.findViewById(R.id.MEDICO);
        final CheckBox institucion = view1.findViewById(R.id.INSTITUCIOSOCIAL);

        String[] vac = persona.Acompañamiento.split(",");
        for (int x = 0; x < vac.length; x++) {
            if (vac[x].equals(psicologico.getText().toString())){
                psicologico.setChecked(true);
            }
            if (vac[x].equals(medico.getText().toString())){
                medico.setChecked(true);
            }
            if (vac[x].equals(institucion.getText().toString())){
                institucion.setChecked(true);
            }
        }

        final Button guardar = view1.findViewById(R.id.GUARDARACOMPAÑAMIENTO);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acompañamiento = null;
                if (psicologico.isChecked()){
                    if (acompañamiento==null){
                        acompañamiento=psicologico.getText().toString();
                    } else {
                        acompañamiento+=","+psicologico.getText().toString();}}
                if (medico.isChecked()){
                    if (acompañamiento==null){
                        acompañamiento=medico.getText().toString();
                    } else {
                        acompañamiento+=","+medico.getText().toString();}}
                if (institucion.isChecked()){
                    if (acompañamiento==null){
                        acompañamiento=institucion.getText().toString();
                    } else {
                        acompañamiento+=","+institucion.getText().toString();}}

                if(acompañamiento!=null){persona.Acompañamiento = acompañamiento;}
                else{persona.Acompañamiento="";}

                ColorAvanceAcompañamiento();
                dialog.dismiss();
            }
        });

        final Button cancelar = view1.findViewById(R.id.CANCELARACOMPAÑAMIENTO);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public PersonClass returnPersona(){
        return persona;
    }

    public void ColorAvanceAcompañamiento() {
        // Cambio los colores de avance
        float avance = 0;
        if (persona.Acompañamiento.length()!=0){
            avance+=1;
        }

        if(avance==0){
            layout_acompañamiento.setBackgroundResource(R.drawable.rojo);
            avanceacompañamiento.setText(context.getString(R.string.completado)+" 00%");
        }

        /*if(avance>0 && avance<2){
            layout_acompañamiento.setBackgroundResource(R.drawable.amarillo);
            double porcentaje = Math.round((avance/2)*100);
            String aux = getString(R.string.completado)+" "+ Double.toString(porcentaje)+"%";
            avancediscapacidad.setText(aux);
        }*/

        if(avance==1){
            layout_acompañamiento.setBackgroundResource(R.drawable.verde);
            avanceacompañamiento.setText(context.getString(R.string.completado)+" 100%");
        }
    }
}