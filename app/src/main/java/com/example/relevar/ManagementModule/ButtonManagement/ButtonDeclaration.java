package com.example.relevar.ManagementModule.ButtonManagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.relevar.ButtonsDeclarationFamiliarUnity.BasicServicesButton22;
import com.example.relevar.ButtonsDeclarationFamiliarUnity.DwellingButton;
import com.example.relevar.ButtonsDeclarationFamiliarUnity.ExternalInspectionButton22;
import com.example.relevar.ButtonsDeclarationPersonal.EstadoFisico.BotonConstantesVitales22;
import com.example.relevar.ButtonsDeclarationPersonal.EstadoFisico.BotonDiscapacidad22;
import com.example.relevar.ButtonsDeclarationPersonal.EstadoFisico.BotonEmbarazo22;
import com.example.relevar.ButtonsDeclarationPersonal.EstadoFisico.BotonEnfermedadesCronicas22;
import com.example.relevar.ButtonsDeclarationPersonal.General.BotonColon22;
import com.example.relevar.ButtonsDeclarationPersonal.General.BotonHpv22;
import com.example.relevar.ButtonsDeclarationPersonal.General.BotonScreningCancer22;
import com.example.relevar.ButtonsDeclarationPersonal.General.BotonColon;
import com.example.relevar.ButtonsDeclarationPersonal.PsicoSocial.BotonAcompañamiento22;
import com.example.relevar.ButtonsDeclarationPersonal.PsicoSocial.BotonOcio22;
import com.example.relevar.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ButtonDeclaration {
    HashMap<String, Object> buttons = new HashMap<>();
    // DEFINICION DE LAS VARIABLES
    // Agregar el nombre del boton donde corresponda
    public List<String> FamilyButtons = new ArrayList<String>(Arrays.asList());
    public List<String> PersonButtonsGeneral = new ArrayList<String>(Arrays.asList());
    public List<String> PersonButtonsEstadoFisico = new ArrayList<String>(Arrays.asList());
    public List<String> PersonButtonsPsico = new ArrayList<String>(Arrays.asList());
    public List<String> PersonButtons = new ArrayList<String>(Arrays.asList());

    private Context context;

    public HashMap<String, View> ButtonsViews = new HashMap<>();

    public ButtonDeclaration(Context oldContext){
        context = oldContext;
        //------------------------------------------------------------------------------------------
        // FAMILIA - GENERAL

        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        // DEFINICION DEL BOTON VIVIENDA
        // Agregar al vector que corresponda el nombre del boton
        String title = "VIVIENDA";
        FamilyButtons.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.vivienda, title));
        // Se le da el comportamiento al boton
        buttons.put(title, new DwellingButton(context, ButtonsViews.get("VIVIENDA"), title));
        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        // DEFINICION DEL BOTON SERVICIOS BASICOS
        // Agregar al vector que corresponda el nombre del boton
        title = "SERVICIOS BASICOS";
        FamilyButtons.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.canilla, title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BasicServicesButton22(context, ButtonsViews.get(title),title));
        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        // DEFINICION INSPECCION EXTERIOR
        // Agregar al vector que corresponda el nombre del boton
        title = "INSPECCION EXTERIOR";
        FamilyButtons.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.arboles, title));
        // Se le da el comportamiento al boton
        buttons.put(title, new ExternalInspectionButton22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        // PERSONAS - GENERAL

        //------------------------------------------------------------------------------------------
        // DEFINICION DEL BOTON SCRENING
        // Agregar al vector que corresponda el nombre del boton
        title = "SCREENING CANCER";
        PersonButtonsGeneral.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.screning,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonScreningCancer22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        // DEFINICION DEL BOTON SCRENING
        // Agregar al vector que corresponda el nombre del boton
        title = "HPV";
        PersonButtonsGeneral.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.hpv,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonHpv22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        // DEFINICION DEL BOTON SCRENING
        // Agregar al vector que corresponda el nombre del boton
        title = "CANCER COLON";
        PersonButtonsGeneral.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.cancer_colon,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonColon22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        // PERSONAS - ESTADO FISICO

        //------------------------------------------------------------------------------------------
        // DEFINO EL BOTON DE REGISTRO DE CONSTANTES VITALES
        // Agregar al vector que corresponda el nombre del boton
        title = "CONSTANTES VITALES";
        PersonButtonsEstadoFisico.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.constantes_vitales,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonConstantesVitales22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        // DEFINO EL BOTON DE ENFERMEDADES CRONICAS
        // Agregar al vector que corresponda el nombre del boton
        title = "ENFERMEDADES CRONICAS";
        PersonButtonsEstadoFisico.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.enfermedad_cronica,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonEnfermedadesCronicas22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        // DEFINO EL BOTON DE EMBARAZO
        // Agregar al vector que corresponda el nombre del boton
        title = "EMBARAZO";
        PersonButtonsEstadoFisico.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.embarazada,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonEmbarazo22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        // DEFINO EL BOTON DE DISCAPACIDAD
        // Agregar al vector que corresponda el nombre del boton
        title = "DISCAPACIDAD";
        PersonButtonsEstadoFisico.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.discapacitado,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonDiscapacidad22(context, ButtonsViews.get(title), title));
        //------------------------------------------------------------------------------------------
        // PERSONAS - PSICO

        //------------------------------------------------------------------------------------------
        // DEFINO EL BOTON DE REGISTRO DE CONSTANTES VITALES
        // Agregar al vector que corresponda el nombre del boton
        title = "OCIO";
        PersonButtonsPsico.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.parque,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonOcio22(context, ButtonsViews.get(title), title));

        // DEFINO EL BOTON DE REGISTRO DE CONSTANTES VITALES
        // Agregar al vector que corresponda el nombre del boton
        title = "ACOMPAÑAMIENTO";
        PersonButtonsPsico.add(title);
        // Agrego el boton al menu de opciones
        ButtonsViews.put(title, generateBtnMenu(R.drawable.asistenciasocial,title));
        // Se le da el comportamiento al boton
        buttons.put(title, new BotonAcompañamiento22(context, ButtonsViews.get(title), title));

        PersonButtons.addAll(PersonButtonsGeneral);
        PersonButtons.addAll(PersonButtonsEstadoFisico);
        PersonButtons.addAll(PersonButtonsPsico);

    }

    // NO EDITAR
    private View generateBtnMenu(int idIcon, String strTitle){
        LayoutInflater Inflater = LayoutInflater.from(context);
        final View view = Inflater.inflate(R.layout.button_general_view, null);

        ImageView icon = view.findViewById(R.id.ICONGNRL);
        icon.setBackgroundResource(idIcon);
        TextView title = view.findViewById(R.id.GNRL);
        title.setText(strTitle);

        return view;
    }

    public void executeButtons(String button){
        Method[] aux = buttons.get(button).getClass().getMethods();
        for (int i=0; i< aux.length; i++){
            if (aux[i].getName().equals("vista")) {
                try {
                    aux[i].invoke(buttons.get(button), null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
