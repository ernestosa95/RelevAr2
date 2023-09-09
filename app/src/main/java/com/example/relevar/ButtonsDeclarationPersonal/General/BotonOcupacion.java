package com.example.relevar.ButtonsDeclarationPersonal.General;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;
import com.example.relevar.ManagementModule.StorageManagement.TrabajosSearchAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BotonOcupacion implements Serializable {
    private AutoCompleteTextView autocomplete;
    PersonClass persona;
    Context context;
    ConstraintLayout layout_ocupacion;
    TextView avanceocupacion;

    public BotonOcupacion(Context originalContext, PersonClass originalPersona, View originalview){
        context=originalContext;
        persona=originalPersona;
        layout_ocupacion = (ConstraintLayout) originalview.findViewById(R.id.AVANCETRABAJO);
        avanceocupacion = (TextView) originalview.findViewById(R.id.COMPLETADOTRABAJO);
    }

    public void vista(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view1 = Inflater.inflate(R.layout.alert_ocupacion, null);
        builder.setView(view1);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // AUTOCOMPLETE TEXTVIEW DE LOS TRABAJOS
        autocomplete = view1.findViewById(R.id.AutoTrabajos);
        List<String> trabajos = new ArrayList<String>();
        TrabajosSearchAdapter searchAdapter = new TrabajosSearchAdapter(context, trabajos);
        autocomplete.setThreshold(1);
        autocomplete.setAdapter(searchAdapter);

        final RadioButton RbtSITrabajo = view1.findViewById(R.id.SITRABAJO);
        final RadioButton RbtNOTrabajo = view1.findViewById(R.id.NOTRABAJO);


        // Si ya tengo valores de contactos debo inicializar
        if(persona.Ocupacion!=""){
            if(!persona.Ocupacion.equals("DESOCUPADO")){
                autocomplete.setText(persona.Ocupacion);
                RbtSITrabajo.setChecked(true);}
            else{
                RbtNOTrabajo.setChecked(true);
                autocomplete.setText(persona.Ocupacion);
            }}

        final EditText edtPlanSocial = view1.findViewById(R.id.EDTTXTPLANSOCIAL);
        if(persona.PlanSocial.length()!=0){
            edtPlanSocial.setText(persona.PlanSocial);
        }

        Button guardar = view1.findViewById(R.id.GUARDAR3);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!RbtSITrabajo.isChecked() && !RbtNOTrabajo.isChecked()){
                    Toast.makeText(context, "DEBE COMPLETAR SI LA PERSONA TIENE O NO OCUPACION", Toast.LENGTH_SHORT).show();
                }else{
                    if(RbtSITrabajo.isChecked()){
                        if(autocomplete.getText().toString().length()!=0){
                            persona.Ocupacion=autocomplete.getText().toString();
                            persona.PlanSocial = edtPlanSocial.getText().toString();
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(context, "INGRESE UNA OCUPACIÓN", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (RbtNOTrabajo.isChecked()){
                        persona.Ocupacion = "DESOCUPADO";
                        persona.PlanSocial = edtPlanSocial.getText().toString();
                        dialog.dismiss();
                    }}


                ColorAvanceOcupacion();

            }
        });

        Button cancelar = view1.findViewById(R.id.CANCELAR3);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final TextView AgregarManualmente = view1.findViewById(R.id.AGREGARMANUALMENTE);
        AgregarManualmente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrabajoManual();
                //dialog.dismiss();
            }
        });
    }

    public PersonClass returnPersona(){
        return persona;
    }

    public void ColorAvanceOcupacion() {
        // Cambio los colores de avance
        float avance = 0;
        if (persona.Ocupacion.length()!=0){
            avance+=1;
        }

        if(persona.PlanSocial.length()!=0){
            avance+=1;
        }

        if(avance==0){
            layout_ocupacion.setBackgroundResource(R.drawable.rojo);
            avanceocupacion.setText(context.getString(R.string.completado)+" 00%");
        }

        if(avance>0 && avance<2){
            layout_ocupacion.setBackgroundResource(R.drawable.amarillo);
            double porcentaje = Math.round((avance/2)*100);
            String aux = context.getString(R.string.completado)+" "+ Double.toString(porcentaje)+"%";
            avanceocupacion.setText(aux);
        }

        if(avance==2){
            layout_ocupacion.setBackgroundResource(R.drawable.verde);
            avanceocupacion.setText(context.getString(R.string.completado)+" 100%");
        }
    }

    private void TrabajoManual(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        View viewManual = Inflater.inflate(R.layout.alert_nuevo_encuestador, null);
        builder.setView(viewManual);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final TextView encabezado = viewManual.findViewById(R.id.textView4);
        encabezado.setText(R.string.nuevo_trabajo);

        final TextView NuevoTrabajo = viewManual.findViewById(R.id.EditNuevoEncuestador);
        NuevoTrabajo.setHint(R.string.nuevo_trabajo);

        final Button guardar = viewManual.findViewById(R.id.GUARDAR);
        guardar.setText(R.string.listo);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Persona.Ocupacion = NuevoTrabajo.getText().toString().toUpperCase();
                autocomplete.setText(NuevoTrabajo.getText().toString().toUpperCase());
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