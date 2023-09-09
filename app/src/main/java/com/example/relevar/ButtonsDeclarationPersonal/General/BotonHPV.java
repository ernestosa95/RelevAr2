package com.example.relevar.ButtonsDeclarationPersonal.General;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.ManagementModule.QRScannerManagement.ScannerQR;
import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;

import java.io.Serializable;

public class BotonHPV implements Serializable {
    PersonClass persona;
    Context context;
    ConstraintLayout layout_hpv;
    TextView avancehpv;

    public BotonHPV(Context originalContext, PersonClass originalPersona, View originalview){
        context=originalContext;
        persona=originalPersona;
        layout_hpv = (ConstraintLayout) originalview.findViewById(R.id.AVANCEHPV);
        avancehpv = (TextView) originalview.findViewById(R.id.COMPLETADOHPV);
    }

    public void vista(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        final View view_alert = Inflater.inflate(R.layout.alert_test_hpv, null);
        builder.setView(view_alert);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        ConstraintLayout pregunta_edad = view_alert.findViewById(R.id.ADVEDAD);
        ConstraintLayout test_hpv = view_alert.findViewById(R.id.CTESTHPV);
        ConstraintLayout code_hpv = view_alert.findViewById(R.id.CODEHPV);
        test_hpv.setVisibility(View.GONE);
        code_hpv.setVisibility(View.GONE);
        RadioButton siEdad = view_alert.findViewById(R.id.SIEDADHPV);
        siEdad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                test_hpv.setVisibility(View.VISIBLE);
                code_hpv.setVisibility(View.GONE);
                pregunta_edad.setVisibility(View.GONE);
            }
        });
        RadioButton noEdad = view_alert.findViewById(R.id.NOEDADHPV);
        noEdad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dialog.dismiss();
                persona.HPV = "NO";
                Toast.makeText(context, "LA PERSONA NO ESTA DENTRO DE LA CAMPAÑA DE AUTOTOMA", Toast.LENGTH_SHORT).show();
            }
        });

        final RadioGroup hpv = view_alert.findViewById(R.id.GROUPHPV);
        final RadioButton siHPV = view_alert.findViewById(R.id.SIHPV);
        final RadioButton noHPV = view_alert.findViewById(R.id.NOHPV);
        if(persona.HPV.equals("SI")){
            siHPV.setChecked(true);
            pregunta_edad.setVisibility(View.GONE);
            test_hpv.setVisibility(View.VISIBLE);
            code_hpv.setVisibility(View.VISIBLE);
        }

        if(persona.HPV.equals("NO")){noHPV.setChecked(true);}
        siHPV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                test_hpv.setVisibility(View.GONE);
                code_hpv.setVisibility(View.VISIBLE);
            }
        });

        final EditText code_number = view_alert.findViewById(R.id.CODENUMBER);
        if (persona.CodeHPV.length() != 0){code_number.setText(persona.CodeHPV);}

        final ImageView number_hpv = view_alert.findViewById(R.id.BUTTONCODEHPV);
        number_hpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanear();
                save(dialog, hpv, code_number);
            }
        });



        final Button guardar = view_alert.findViewById(R.id.GUARDARHPV);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(dialog, hpv, code_number);
            }
        });

        final Button cancelar = view_alert.findViewById(R.id.CANCELARHPV);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void save(Dialog dialog, RadioGroup hpv, EditText code_number){
        RadioButton selec = hpv.findViewById(hpv.getCheckedRadioButtonId());
        if(selec!=null){
            String seleccionado = selec.getText().toString();
            persona.HPV = seleccionado;
            if (code_number.getText().toString().length() != 0){
                persona.CodeHPV = code_number.getText().toString();
            }
        }
        //Toast.makeText(getApplicationContext(), context.getIntent().getStringExtra("LATITUD"), Toast.LENGTH_SHORT).show();
        ColorAvanceTestHPV();
        dialog.dismiss();
    }

    // Leer QR
    private void escanear(){
        Intent Modif= new Intent (context, ScannerQR.class);
        context.startActivity(Modif);
    }

    public PersonClass returnPersona(){
        return persona;
    }

    public void ColorAvanceTestHPV() {
        // Cambio los colores de avance
        float avance = 0;
        if (persona.HPV.length()!=0){
            avance+=1;
        }

        if(avance==0){
            layout_hpv.setBackgroundResource(R.drawable.rojo);
            avancehpv.setText(context.getString(R.string.completado)+" 00%");
        }

        if(avance==1){
            layout_hpv.setBackgroundResource(R.drawable.verde);
            avancehpv.setText(context.getString(R.string.completado)+" 100%");
        }
    }
}