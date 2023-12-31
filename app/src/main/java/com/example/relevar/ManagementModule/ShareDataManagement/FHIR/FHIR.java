package com.example.relevar.ManagementModule.ShareDataManagement.FHIR;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.relevar.ManagementModule.StorageManagement.BDData;
import com.example.relevar.ManagementModule.StorageManagement.BDUbicationManager;
import com.example.relevar.ManagementModule.StorageManagement.SQLitePpal;
import com.example.relevar.ManagementModule.ShareDataManagement.Archivos;
import com.example.relevar.BasicObjets.PollsterClass;
import com.example.relevar.BasicObjets.FamiliarUnityClass;
import com.example.relevar.BasicObjets.PersonClass;
import com.example.relevar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FHIR {
    private String code_hpv = "35904009", code_colon = "275978004";
    private Context context;
    private PersonClass persona;
    private FamiliarUnityClass familia;
    private PollsterClass encuestador;
    BDData adminBDData;
    SQLitePpal adminPpal;

    private static String privateKey = "A?D(G+KbPeShVmYq";
    private String password, user, id;
    ArrayList<String> values = new ArrayList<>();
    private int index = 0;
    private ProgressDialog progress;
    FHIRresources fhiRresources = new FHIRresources();
    public FHIR(Context newContext, PersonClass new_persona, FamiliarUnityClass new_familia){
        context = newContext;
        persona = new_persona;
        familia = new_familia;
        encuestador = new PollsterClass(context);
        adminBDData = new BDData(context, "BDData", null, 1);
        adminPpal = new SQLitePpal(context, "DATA_PRINCIPAL", null, 1);
    }

    public void send(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.111:5000/json";
        //String url = "http://192.168.0.108:5000/json";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("data","value1");
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void sendJson (String str_url, String userText, String passText, ArrayList<String> dates){
        progress = new ProgressDialog(context);
        progress.setMessage("Enviando datos a IPC");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setProgress(0);
        progress.show();

        password = passText;
        user = userText;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //ArrayList<ClasePersona> personsdni = adminBDData.SearchAllPersonsWithDNI(context);
        ArrayList<PersonClass> personsdni = new ArrayList<>();
        for(int i=0; i<dates.size();i++) {
            Log.e("fecha", dates.get(i));
            personsdni.addAll(adminBDData.SearchPersonsDate(context, dates.get(i)));
        }
        int persons = personsdni.size();
        Log.e("cant pers", Integer.toString(persons));
        //String url = "http://192.168.0.108:5000/json";
        //String url = "http://192.168.1.111:5000/json";

        //------------------------------------------------------------------------------------------
        // Ingresar datos a GNUHealth
        /*StringRequest stringRequestGNU = new StringRequest(Request.Method.POST, str_url+"_gnu", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("msj0", "posta 0");
                Toast.makeText(context, context.getString(R.string.confirm_send), Toast.LENGTH_SHORT).show();
                progress.setProgress(100);
                progress.dismiss();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("mj1", error.toString());
                Toast.makeText(context, context.getString(R.string.error_send) +" onresponse", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                return params;
            }
        };
        stringRequestGNU.setRetryPolicy(new DefaultRetryPolicy(
                persons*2000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));*/
        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        // Envio de los datos
        StringRequest stringRequestData = new StringRequest(Request.Method.POST, str_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //requestQueue.add(stringRequestGNU);
                progress.setProgress(100);
                progress.dismiss();
                Toast.makeText(context, context.getString(R.string.confirm_send), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(context, context.getString(R.string.error_send) + "stringReq", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }){@Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<>();
            //String data2send = data_send().toString();
            try {
                //params.put("password", encText("admin userxxx", "areyouokareyouok"));
                String key = generateStrKey(password, id);
                //params.put("data", encText(data2send, key));
                ArrayList<String> data = Bundles2Send2(personsdni, dates);
                data.addAll(Bundles2SendUbication());
                params.put("size", Integer.toString(data.size()));
                //Log.e("SIZE", params.get("size"));
                params.put("key", encText(passText, privateKey));
                params.put("username", userText);
                //Log.e("sixe", Integer.toString(data.size()));
                //Log.e("key", key);
                for(int i=0; i<data.size(); i++) {
                    params.put("data"+Integer.toString(i), encText(data.get(i), key));
                    Log.e("data", params.get("data0"));
                }
            }catch (Exception e){
                Log.e("error", e.toString() + "getParams");
            }
            return params;
        }
        };
        stringRequestData.setRetryPolicy(new DefaultRetryPolicy(
                persons*1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
        // Validacion del usuario
        StringRequest stringRequestUser = new StringRequest(Request.Method.POST, str_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                if(!response.equals("error_user")){
                    id=response;
                    progress.setProgress(20);
                    requestQueue.add(stringRequestData);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("volleyerror", error.toString());
                Toast.makeText(context, context.getString(R.string.error_send) +"volleyerror", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("user", userText);
                try {
                    params.put("password", encText(passText, privateKey));
                }catch (Exception e){}
                return params;
            }
        };
        requestQueue.add(stringRequestUser);
    }

//--------------------------------------------------------------------------------------------------
    public static final byte[] encBytes(byte[] srcBytes, byte[] key, byte[] newIv) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    IvParameterSpec iv = new IvParameterSpec(newIv);
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    byte[] encrypted = cipher.doFinal(srcBytes);
    return encrypted;
}

    public static final String encText(String sSrc, String strKey) throws Exception {
        //String strKey = "areyouokareyouok";
        //byte[] key = {'a','r','e','y','o','u','o','k','a','r','e','y','o','u','o','k'};
        byte[] ivk = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        byte[] srcBytes = sSrc.getBytes("utf-8");
        byte[] encrypted = encBytes(srcBytes, strKey.getBytes(StandardCharsets.UTF_8), ivk);
        return Base64.encodeToString(encrypted,Base64.DEFAULT);
    }

    private String generateStrKey(String pass, String id){
        String value = pass + id + "RelevAr" + "2020" + "UNER";
        return value.substring(0, 16);
    }

//--------------------------------------------------------------------------------------------------
    /*public JSONObject data_send() {

        JSONObject data = new JSONObject();

        ArrayList<JSONObject> entries = new ArrayList<>();
        familia.LoadDataHashToParameters();
        persona.DNI = persona.DNI.replace(" ","");
        entries.add(fhiRresources.patientResourceFHIR(
                                            persona.DNI,
                                            persona.Nombre.split(" "),
                                            persona.Apellido,
                                            persona.Sexo,
                                            persona.Nacimiento,
                                            familia.calle,
                                            "",
                                            "",
                                            "AR",
                                            encuestador.Provincia,
                                            Double.parseDouble(familia.Latitud),
                                            Double.parseDouble(familia.Longitud),
                                            familia.numero,
                                            familia.TelefonoFamiliar
        ));

        String code_du = familia.Latitud + ' ' + familia.Longitud;
        ArrayList<String> data2send = new ArrayList<>();
        data2send.add("TIPO DE VIVIENDA");
        data2send.add("ORIGEN AGUA");
        data2send.add("AGUA");
        data2send.add("BAÑO");
        data2send.add("EL BAÑO TIENE");
        data2send.add("EXCRETAS");
        data2send.add("MATERIAL PREDOMINANTE EN LAS PAREDES EXTERIORES");
        data2send.add("MATERIAL PREDOMINANTE EN LA CUBIERTA EXTERIOR DEL TECHO");
        data2send.add("MATERIAL DE LOS PISOS");
        data2send.add("MENORES");
        //data2send.add("USA PARA COCINAR");
        data2send.add("CANTIDAD DE PIEZAS");

        Date date = new Date();
        String fecha = "";
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy/mm/dd");
        try {
             date = sdf.parse(persona.Fecha);
             fecha = newFormat.format(date);
             //String[] aux = fecha.split("/");
             //fecha = aux[0]+"/"+Integer.toString(Integer.parseInt(aux[1])+1)+"/"+aux[2];
        }catch(ParseException e){
            e.printStackTrace();
        }

        // Enviar datos relacionados con terreno de sangre en materia fecal
        // Datos a enviar
        ArrayList<String> categories_colon = new ArrayList<>();
        categories_colon.add(context.getString(R.string.antecedentes_cancer_colon));
        categories_colon.add(context.getString(R.string.sintomas_cancer_colon));
        categories_colon.add(context.getString(R.string.prueba_cancer_colon));
        categories_colon.add(context.getString(R.string.razon_no_prueba_colon));

        for (int i=0; i<categories_colon.size(); i++){
            if (persona.Data.containsKey(categories_colon.get(i))) {
                if (persona.Data.get(categories_colon.get(i)) != null && persona.Data.get(categories_colon.get(i)).length() != 0) {
                    entries.add(fhiRresources.observationIpcResourceFHIR(
                            persona.DNI,
                            persona.Data.get(context.getString(R.string.efector)),
                            categories_colon.get(i),
                            categories_colon.get(i),
                            persona.Fecha,
                            encuestador.Nombre+" "+encuestador.Apellido,
                            encuestador.DNI,
                            persona.Data.get(categories_colon.get(i)),
                            persona.Data.get(categories_colon.get(i))
                    ));
                }
            }
        }

        // datos de hpv
        if (persona.Data.get(context.getString(R.string.realizo_test_hpv)) != null && persona.Data.get(context.getString(R.string.realizo_test_hpv)).length() != 0) {
            entries.add(fhiRresources.observationIpcResourceFHIR(
                    persona.DNI,
                    persona.Data.get(context.getString(R.string.efector)),
                    context.getString(R.string.realizo_test_hpv),
                    context.getString(R.string.realizo_test_hpv),
                    persona.Fecha,
                    encuestador.Nombre + " " + encuestador.Apellido,
                    encuestador.DNI,
                    persona.Data.get(context.getString(R.string.realizo_test_hpv)),
                    persona.Data.get(context.getString(R.string.realizo_test_hpv))));
        }

        data = fhiRresources.Bundle(entries);
        return data;
    }*/

//--------------------------------------------------------------------------------------------------
    private ArrayList<String> Bundles2SendUbication () throws JSONException {
        ArrayList<String> data = new ArrayList<>();

        Calendar calendario = new GregorianCalendar();
        BDUbicationManager bdUbicationMannager = new BDUbicationManager(context, "BDData", null, 1);
        ArrayList<String> dates = bdUbicationMannager.datesUbications();

        int dia =calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int año = calendario.get(Calendar.YEAR);
        String date_today = Integer.toString(dia)+":"+Integer.toString(mes)+":"+Integer.toString(año);

        //Se podrian enviar todos los recorridos identificando las fechas en que hay datos
        ArrayList<JSONObject> entries_ubication = new ArrayList<>();
        for(int i=0; i<dates.size(); i++) {
            Log.e("date", dates.get(i));
            entries_ubication.add(fhiRresources.observationIndecResourceFHIR(
                    encuestador.DNI,
                    "RECORRIDO",
                    "RECORRIDO",
                    date_today,
                    encuestador.Nombre + " " + encuestador.Apellido,
                    encuestador.DNI,
                    "data ubicacion",
                    bdUbicationMannager.readUbications4Date(dates.get(i))));
            Log.e("UBICACION", entries_ubication.get(i).toString());
        }

        data.add(fhiRresources.Bundle(entries_ubication).toString());
        Log.e("bundleubi", data.get(0));
        return data;
    }

    private ArrayList<String> Bundles2Send2 (ArrayList<PersonClass> personsdni, ArrayList<String> fechas_enviar) throws JSONException {
        //Busco las personas y su respectivas familias para enviar
        //ArrayList<ClasePersona> personsdni = adminBDData.SearchAllPersonsWithDNI(context);
        Log.e("msg", "1");
        HashMap<String, FamiliarUnityClass> families = new HashMap<>();
        for (int i=0; i<personsdni.size(); i++){
            personsdni.get(i).LoadDataHashToParamaters();
            try {
                families.put(personsdni.get(i).Latitud, adminBDData.SearchFamilyCoordinate(context, personsdni.get(i).Latitud, personsdni.get(i).Longitud));
                //Log.e("msg", families.get(personsdni.get(i).Latitud).Data.get("BAÑO"));
            }catch (Exception e){
                Log.e("msg", e.toString());
            }
        }

        // envio de datos relacionado con el registro de personas
        for (int i=0; i<personsdni.size(); i++) {
            Log.e("msg", "registros de personas");
            HashMap<String, String> data_encuestador = new HashMap<>();
            try{
                if (persona.getIdEncuestador()!="") {
                    data_encuestador = adminPpal.searchEncuestadorWithDNI(persona.getIdEncuestador());
                }
                else{
                    data_encuestador = adminPpal.searchEncuestadorWithDNI(encuestador.DNI);
                }
                 Log.e("data si", data_encuestador.toString());
            }catch (Exception e){
                data_encuestador = adminPpal.searchEncuestadorWithDNI(encuestador.DNI);
                Log.e("data", data_encuestador.toString());
            }
            ArrayList<JSONObject> entries = new ArrayList<>();

            persona = personsdni.get(i);
            familia = families.get(personsdni.get(i).Latitud);

            ArrayList<String> datos_enviar_evaluacion_du = persona.cabeceraPersona;
            datos_enviar_evaluacion_du.addAll(familia.FamilyValues());
            Archivos archivo = new Archivos(context);
            datos_enviar_evaluacion_du.addAll(archivo.getCodeCabecerasPersonas(context));

            //envio paciente
            Log.e("msg", "envio de paciente");
            String num = "", calle = "";
            if (familia.Data.get(context.getString(R.string.numero))!=null){num=familia.Data.get(context.getString(R.string.numero));}
            if (familia.Data.get(context.getString(R.string.calle))!=null){calle=familia.Data.get(context.getString(R.string.calle));}
            entries.add(fhiRresources.patientResourceFHIR(
                    persona.DNI,
                    persona.Nombre.split(" "),
                    persona.Apellido,
                    persona.Sexo,
                    persona.Nacimiento,
                    calle,
                    "",
                    "",
                    "AR",
                    data_encuestador.get("PROVINCIA"),
                    Double.parseDouble(familia.Data.get(context.getString(R.string.latitud))),
                    Double.parseDouble(familia.Data.get(context.getString(R.string.longitud))),
                    num,
                    familia.Data.get(context.getString(R.string.telefono_familiar))
            ));

            /*Object[] datas = persona.Data.keySet().toArray();
            for (Object c : datas){
                Log.e("cabeceras enviar", c.toString());
                Log.e("valores enviar", persona.Data.get(c.toString()));
            }*/

            //envio general
            for(int j=0; j<datos_enviar_evaluacion_du.size();j++){
                    try{
                        if (persona.Data.containsKey(datos_enviar_evaluacion_du.get(j))) {
                            if (persona.Data.get(datos_enviar_evaluacion_du.get(j)) != null && persona.Data.get(datos_enviar_evaluacion_du.get(j)).length() != 0) {
                                //Log.e("marca 1", persona.Data.get(datos_enviar_evaluacion_du.get(j)));
                                entries.add(fhiRresources.observationIndecResourceFHIR(
                                        persona.DNI,
                                        datos_enviar_evaluacion_du.get(j),
                                        datos_enviar_evaluacion_du.get(j),
                                        persona.Fecha,
                                        data_encuestador.get("NOMBRE") + " " + data_encuestador.get("APELLIDO"),
                                        data_encuestador.get("DNI"),
                                        persona.Data.get(datos_enviar_evaluacion_du.get(j)),
                                        persona.Data.get(datos_enviar_evaluacion_du.get(j))));
                                //Log.e("cabecera:", datos_enviar_evaluacion_du.get(j));
                                //Log.e("data", persona.Data.get(datos_enviar_evaluacion_du.get(j)));
                            }
                        }
                        if (familia.Data.containsKey(datos_enviar_evaluacion_du.get(j))) {
                            if (familia.Data.get(datos_enviar_evaluacion_du.get(j)) != null && familia.Data.get(datos_enviar_evaluacion_du.get(j)).length() != 0) {
                                //Log.e("msg", "data familia");
                                entries.add(fhiRresources.observationIndecResourceFHIR(
                                        persona.DNI,
                                        datos_enviar_evaluacion_du.get(j),
                                        datos_enviar_evaluacion_du.get(j),
                                        persona.Fecha,
                                        data_encuestador.get("NOMBRE") + " " + data_encuestador.get("APELLIDO"),
                                        data_encuestador.get("DNI"),
                                        familia.Data.get(datos_enviar_evaluacion_du.get(j)),
                                        familia.Data.get(datos_enviar_evaluacion_du.get(j))));
                                //Log.e("cabecera familia:", datos_enviar_evaluacion_du.get(j));
                                //Log.e("data familia", familia.Data.get(datos_enviar_evaluacion_du.get(j)));
                            }
                        }
                    }catch (Exception e){
                        Log.e("msg", e.toString());
                    }

            }



            /*if((persona.Data.get(context.getString(R.string.realizo_test_hpv)) != null && persona.Data.get(context.getString(R.string.realizo_test_hpv)).length() != 0)
                    || (persona.Data.get(context.getString(R.string.prueba_cancer_colon)) != null && persona.Data.get(context.getString(R.string.prueba_cancer_colon)).length()!=0)) {
                persona.DNI = persona.DNI.replace(" ", "");

                String code_du = familia.Latitud + ' ' + familia.Longitud;
                ArrayList<String> data2send = new ArrayList<>();
                data2send.add("TIPO DE VIVIENDA");
                data2send.add("ORIGEN AGUA");
                data2send.add("AGUA");
                data2send.add("BAÑO");
                data2send.add("EL BAÑO TIENE");
                data2send.add("EXCRETAS");
                data2send.add("MATERIAL PREDOMINANTE EN LAS PAREDES EXTERIORES");
                data2send.add("MATERIAL PREDOMINANTE EN LA CUBIERTA EXTERIOR DEL TECHO");
                data2send.add("MATERIAL DE LOS PISOS");
                data2send.add("MENORES");
                //data2send.add("USA PARA COCINAR");
                data2send.add("CANTIDAD DE PIEZAS");

                Date date = new Date();
                String fecha = "";
                SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    date = sdf.parse(persona.Fecha);
                    fecha = newFormat.format(date);
                    //String[] aux = fecha.split("/");
                    //fecha = aux[0]+"/"+Integer.toString(Integer.parseInt(aux[1])+1)+"/"+aux[2];
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                // Enviar datos relacionados con terreno de sangre en materia fecal
                // Datos a enviar
                ArrayList<String> categories_colon = new ArrayList<>();
                categories_colon.add(context.getString(R.string.antecedentes_cancer_colon));
                categories_colon.add(context.getString(R.string.sintomas_cancer_colon));
                categories_colon.add(context.getString(R.string.prueba_cancer_colon));
                categories_colon.add(context.getString(R.string.razon_no_prueba_colon));
                categories_colon.add(context.getString(R.string.code_hpv));

                for (int j = 0; j < categories_colon.size(); j++) {
                    if (persona.Data.containsKey(categories_colon.get(j))) {
                        if (persona.Data.get(categories_colon.get(j)) != null && persona.Data.get(categories_colon.get(j)).length() != 0) {
                            entries.add(fhiRresources.observationIpcResourceFHIR(
                                    persona.DNI,
                                    persona.Data.get(context.getString(R.string.efector)),
                                    categories_colon.get(j),
                                    categories_colon.get(j),
                                    persona.Fecha,
                                    data_encuestador.get("NOMBRE") + " " + data_encuestador.get("APELLIDO"),
                                    data_encuestador.get("DNI"),
                                    persona.Data.get(categories_colon.get(j)),
                                    persona.Data.get(categories_colon.get(j))
                            ));
                        }
                    }
                }

                // datos de hpv
                if (persona.Data.get(context.getString(R.string.realizo_test_hpv)) != null && persona.Data.get(context.getString(R.string.realizo_test_hpv)).length() != 0) {
                    entries.add(fhiRresources.observationIpcResourceFHIR(
                            persona.DNI,
                            persona.Data.get(context.getString(R.string.efector)),
                            context.getString(R.string.realizo_test_hpv),
                            context.getString(R.string.realizo_test_hpv),
                            persona.Fecha,
                            data_encuestador.get("NOMBRE") + " " + data_encuestador.get("APELLIDO"),
                            data_encuestador.get("DNI"),
                            persona.Data.get(context.getString(R.string.realizo_test_hpv)),
                            persona.Data.get(context.getString(R.string.realizo_test_hpv))));
                }

            }*/
            values.add(fhiRresources.Bundle(entries).toString());
            Log.e("datos finales", values.get(values.size() - 1));
        }

        // envio de datos relacionado con las viviendas renuentes y deshabitadas
        ArrayList<FamiliarUnityClass> renuentesdeshabitadas = new ArrayList<>();
        for (int i=0; i<fechas_enviar.size(); i++) {
            renuentesdeshabitadas.addAll(adminBDData.SearchFamilyRenuenteDeshabitadaDate(context, fechas_enviar.get(i)));
        }
        //Log.e("longitud", Integer.toString(renuentesdeshabitadas.size()));
        ArrayList<String> data_send_renuente_deshabitada = new ArrayList<String>();
        data_send_renuente_deshabitada.add(context.getString(R.string.situacion_habitacional));
        data_send_renuente_deshabitada.add(context.getString(R.string.latitud));
        data_send_renuente_deshabitada.add(context.getString(R.string.longitud));
        data_send_renuente_deshabitada.add(context.getString(R.string.encuestador));
        data_send_renuente_deshabitada.add(context.getString(R.string.codigo_color));
        ArrayList<JSONObject> entries_renuentes_deshabitadas = new ArrayList<>();
        for (int i=0; i<renuentesdeshabitadas.size();i++){
            HashMap<String, String> data_encuestador = new HashMap<>();
            try{
                if (renuentesdeshabitadas.get(i).data_enuestador!="") {
                    data_encuestador = adminPpal.searchEncuestadorWithDNI(renuentesdeshabitadas.get(i).data_enuestador);
                }
                else{
                    data_encuestador = adminPpal.searchEncuestadorWithDNI(encuestador.DNI);
                }
            }catch (Exception e){
                data_encuestador = adminPpal.searchEncuestadorWithDNI(encuestador.DNI);
            }

            for (int j=0; j<data_send_renuente_deshabitada.size(); j++){
                entries_renuentes_deshabitadas.add(fhiRresources.observationIndecResourceFHIR(
                        "",
                        data_send_renuente_deshabitada.get(j),
                        data_send_renuente_deshabitada.get(j),
                        renuentesdeshabitadas.get(i).Fecha,
                        data_encuestador.get("NOMBRE") + " " + data_encuestador.get("APELLIDO"),
                        data_encuestador.get("DNI"),
                        renuentesdeshabitadas.get(i).Data.get(data_send_renuente_deshabitada.get(j)),
                        renuentesdeshabitadas.get(i).Data.get(data_send_renuente_deshabitada.get(j))));
                Log.e("renuente", renuentesdeshabitadas.get(i).Data.get(data_send_renuente_deshabitada.get(j)));
            }
            values.add(fhiRresources.Bundle(entries_renuentes_deshabitadas).toString());
        }

        return values;
    }
}
