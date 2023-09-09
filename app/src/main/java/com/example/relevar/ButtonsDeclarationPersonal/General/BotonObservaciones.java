package com.example.relevar.ButtonsDeclarationPersonal.General;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;

import java.io.Serializable;

public class BotonObservaciones implements Serializable {
    EditText obs;
    PersonClass persona;
    Context context;
    ConstraintLayout observaciones;
    TextView avanceobservaciones;

    public BotonObservaciones(Context originalContext, PersonClass originalPersona, View originalview){
        context=originalContext;
        persona=originalPersona;
        observaciones = (ConstraintLayout) originalview.findViewById(R.id.AVANCEOBSERVACIONES);
        avanceobservaciones = (TextView) originalview.findViewById(R.id.COMPLETADOOBSERVACIONES);
    }

    public void vista(){
        // Defino los contenedores
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MiEstiloAlert);
        TextView textView = new TextView(context);
        textView.setText("OBSERVACIONES");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(22F);
        textView.setBackgroundColor(Color.parseColor("#4588BC"));
        textView.setTextColor(Color.WHITE);
        builder.setCustomTitle(textView);

        // Defino el Layaout que va a contener a los Check
        LinearLayout mainLayout       = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        // Entrega de productos de limpieza
        /*LinearLayout layoutlimp       = new LinearLayout(this);
        layoutlimp.setOrientation(LinearLayout.HORIZONTAL);
        layoutlimp.setVerticalGravity(Gravity.CENTER_VERTICAL);
        layoutlimp.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        limp = new TextView(getApplicationContext());
        limp.setText("ENTREGA DE PROD. DE LIMPIEZA");
        limp.setGravity(Gravity.CENTER_HORIZONTAL);
        limp.setBackgroundColor(Color.parseColor("#396F98"));
        limp.setInputType(TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        limp.setHintTextColor(Color.WHITE);
        limp.setTextSize(20);
        limp.setTextColor(Color.WHITE);
        limp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layoutlimp.addView(limp);
        mainLayout.addView(layoutlimp);*/

        // Defino el Layaout que va a contener los radiobutton
        //LinearLayout mainLayout1       = new LinearLayout(this);
        /*RadioGroup mainLayout1 = new RadioGroup(this);
        mainLayout1.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout1.setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        // Button si
        rb1 = new RadioButton(getApplicationContext());
        rb1.setText("SI");
        rb1.setLayoutParams(new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.MATCH_PARENT));
        rb1.setTextColor(Color.WHITE);
        rb1.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
        rb1.setTextSize(25);

        // Button no
        rb2 = new RadioButton(getApplicationContext());
        rb2.setText("NO");
        rb2.setTextColor(Color.WHITE);
        rb2.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
        rb2.setTextSize(25);

        mainLayout1.addView(rb1);
        mainLayout1.addView(rb2);
        mainLayout.addView(mainLayout1);*/

        // Defino los parametros
        int TamañoLetra =20;

        // OBSERVACIONES
        LinearLayout layout0 = new LinearLayout(context);
        layout0.setOrientation(LinearLayout.HORIZONTAL);
        layout0.setVerticalGravity(Gravity.CENTER_VERTICAL);
        obs = new EditText(context);
        //sabin.setText(Texto);
        obs.setHint("OBSERVACIONES");
        obs.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        obs.setHintTextColor(Color.WHITE);
        obs.setTextSize(TamañoLetra);
        obs.setTextColor(Color.WHITE);
        obs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout0.setMinimumHeight(400);
        layout0.addView(obs);


        mainLayout.addView(layout0);

        // Add OK and Cancel buttons
        builder.setPositiveButton("LISTO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The user clicked OK
                persona.Observaciones = obs.getText().toString();
                //if(rb1.isChecked()==true){Persona.Limpieza="SI";}
                //if(rb2.isChecked()==true){Persona.Limpieza="NO";}

                // Cambio los colores de avance
                ColorAvanceObservaciones();

            }
        });
        builder.setNegativeButton("CANCELAR", null);
        builder.setView(mainLayout);
        // Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        if(persona.Observaciones!=null || persona.Limpieza!=null){
            obs.setText(persona.Observaciones);
            //if(Persona.Limpieza.equals("SI")){rb1.setChecked(true);}
            //if(Persona.Limpieza.equals("NO")){rb2.setChecked(true);}
        }
    }

    public PersonClass returnPersona(){
        return persona;
    }

    public void ColorAvanceObservaciones() {
        // Cambio los colores de avance
        int avance = 0;
        if (persona.Observaciones.length()!=0){
            avance+=1;
        }
        /*if (Persona.Limpieza.length()!=0){
            avance+=1;
        }*/

        if(avance==1){
            observaciones.setBackgroundResource(R.drawable.verde);
            avanceobservaciones.setText(context.getString(R.string.completado)+" 100%");
            //Observacion.setBackgroundColor(Color.parseColor("#FFA07A"));
            //String aux = Integer.toString(avance)+"/2";
            //AvObs.setText(aux);
            //AvObs.setBackgroundColor(Color.parseColor("#FFA07A"));
        }
        if(avance==2){
            observaciones.setBackgroundResource(R.drawable.verde);
            avanceobservaciones.setText(context.getString(R.string.completado)+" 100%");
            //Observacion.setBackgroundColor(Color.parseColor("#8BC34A"));
            //AvObs.setText("2/2");
            //AvObs.setBackgroundColor(Color.parseColor("#8BC34A"));
        }
    }
}