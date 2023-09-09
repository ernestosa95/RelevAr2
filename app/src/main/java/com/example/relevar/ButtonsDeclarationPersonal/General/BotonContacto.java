package com.example.relevar.ButtonsDeclarationPersonal.General;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;

import java.io.Serializable;

public class BotonContacto implements Serializable {
    PersonClass persona;
    Context context;
    ConstraintLayout contacto;
    TextView avancecontacto;

    public BotonContacto(Context originalContext, PersonClass originalPersona, View originalview){
        context=originalContext;
        persona=originalPersona;
        contacto = (ConstraintLayout) originalview.findViewById(R.id.AVANCECONTACTO);
        avancecontacto = (TextView) originalview.findViewById(R.id.COMPLETADOCONTACTO);
    }

    public void vista(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view1 = Inflater.inflate(R.layout.alert_contacto, null);
        builder.setView(view1);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText celular = view1.findViewById(R.id.CELULAR);
        final EditText fijo = view1.findViewById(R.id.FIJO);
        final EditText mail = view1.findViewById(R.id.MAIL);
        final EditText nombrecontacto = view1.findViewById(R.id.NOMBREAPELLIDOCONTACTO);
        final EditText telcontacto = view1.findViewById(R.id.TELEFONOCONTACTO);
        final EditText parentezco = view1.findViewById(R.id.PARENTESCOCONTACTO);

        // Si ya tengo valores de contactos debo inicializar
        if(persona.Celular!=""){celular.setText(persona.Celular);}
        if(persona.Fijo!=""){fijo.setText(persona.Fijo);}
        if(persona.Mail!=""){mail.setText(persona.Mail);}
        if(persona.NombreContacto!=""){nombrecontacto.setText(persona.NombreContacto);}
        if(persona.TelefonoContacto!=""){telcontacto.setText(persona.TelefonoContacto);}
        if(persona.ParentezcoContacto!=""){parentezco.setText(persona.ParentezcoContacto);}

        Button guardar = view1.findViewById(R.id.GUARDAR2);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persona.Celular = celular.getText().toString();
                persona.Fijo = fijo.getText().toString();
                persona.Mail = mail.getText().toString();
                persona.NombreContacto = nombrecontacto.getText().toString();
                persona.TelefonoContacto = telcontacto.getText().toString();
                persona.ParentezcoContacto = parentezco.getText().toString();
                ColorAvanceContacto();
                dialog.dismiss();
            }
        });

        Button cancelar = view1.findViewById(R.id.CANCELAR2);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public PersonClass returnPersona(){
        return persona;
    }

    public void ColorAvanceContacto() {
        // Cambio los colores de avance
        float avance = 0;
        if (persona.Celular.length()!=0){
            avance+=1;
        }
        if (persona.Fijo.length()!=0){
            avance+=1;
        }
        if (persona.Mail.length()!=0){
            avance+=1;
        }
        if (persona.NombreContacto.length()!=0){
            avance+=1;
        }
        if (persona.TelefonoContacto.length()!=0){
            avance+=1;
        }
        if (persona.ParentezcoContacto.length()!=0){
            avance+=1;
        }
        if(avance==1 || avance==2 || avance==3 || avance==4 || avance==5){
            contacto.setBackgroundResource(R.drawable.amarillo);
            double porcentaje = Math.round((avance/6)*100);
            //Toast.makeText(getApplicationContext(), Double.toString(porcentaje), Toast.LENGTH_SHORT).show();
            String aux = context.getString(R.string.completado)+" "+ Double.toString(porcentaje)+"%";
            avancecontacto.setText(aux);
            //AvContacto.setText(aux);
            //AvContacto.setBackgroundColor(Color.parseColor("#FFA07A"));
        }
        if(avance==6){
            contacto.setBackgroundResource(R.drawable.verde);
            avancecontacto.setText(context.getString(R.string.completado)+" 100%");
            //Contacto.setBackgroundColor(Color.parseColor("#8BC34A"));
            //AvContacto.setText("3/3");
            //AvContacto.setBackgroundColor(Color.parseColor("#8BC34A"));
        }
    }
}