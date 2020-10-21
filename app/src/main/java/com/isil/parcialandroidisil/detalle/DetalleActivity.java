package com.isil.parcialandroidisil.detalle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isil.parcialandroidisil.R;
import com.isil.parcialandroidisil.detalle.adapter.DetalleAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleActivity extends AppCompatActivity {

    ListView listView;
    ArrayList productos = new ArrayList<HashMap<String, String>>();
    TextView tvTotal, tvFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        listView = findViewById(R.id.lvDetalle);
        tvFecha = findViewById(R.id.tvFechaDetalle);
        tvTotal = findViewById(R.id.tvTotalDetalle);

        Bundle bundle = getIntent().getExtras();

        String idPedido = bundle.getString("idpedido");
        String nombres = bundle.getString("nombres");
        String total = bundle.getString("total");
        String fechaPedido = bundle.getString("fechapedido");

        tvTotal.setText(total);
        tvFecha.setText(fechaPedido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(idPedido+" "+nombres);

        leerDatos(idPedido);
    }

    private void leerDatos(final String idpedido) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://servicioweb.pix.pe/pedidosdetallebuscar.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA", response);
                        llenarLista(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.getMessage().toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("idpedido", idpedido);
                return map;
            }
        };

        queue.add(stringRequest);
    }

    private void llenarLista(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0; i<jsonArray.length();i++){
                String idproducto = jsonArray.getJSONObject(i).getString("idproducto");
                String nombre = jsonArray.getJSONObject(i).getString("nombre");
                String detalle = jsonArray.getJSONObject(i).getString("detalle");
                String precio = jsonArray.getJSONObject(i).getString("precio");
                String cantidad = jsonArray.getJSONObject(i).getString("cantidad");
                String imagenchica = jsonArray.getJSONObject(i).getString("imagenchica");

                HashMap<String,String> map = new HashMap<>();
                map.put("idproducto",idproducto);
                map.put("nombre",nombre);
                map.put("detalle",detalle);
                map.put("precio",precio);
                map.put("cantidad", cantidad);
                map.put("imagenchica",imagenchica);

                productos.add(map);
            }

            DetalleAdapter detalleAdapter = new DetalleAdapter(this,productos);
            listView.setAdapter(detalleAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}