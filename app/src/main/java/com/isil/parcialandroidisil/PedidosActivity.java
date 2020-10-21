package com.isil.parcialandroidisil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isil.parcialandroidisil.detalle.DetalleActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class PedidosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayList pedidos = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        listView = findViewById(R.id.lvPedidos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pedidos");

        leerDatos();
    }

    private void leerDatos() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://servicioweb.pix.pe/pedidoslista.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
        });

        queue.add(stringRequest);
    }

    private void llenarLista(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                String idPedido = jsonArray.getJSONObject(i).getString("idpedido");
                String nombres = jsonArray.getJSONObject(i).getString("nombres");
                String total = jsonArray.getJSONObject(i).getString("total");
                String fechapedido = jsonArray.getJSONObject(i).getString("fechapedido");

                HashMap<String, String> map = new HashMap<>();
                map.put("idpedido", idPedido);
                map.put("nombres", nombres);
                map.put("total", total);
                map.put("fechapedido", fechapedido);

                pedidos.add(map);
            }

            String[] datos = {"idpedido", "nombres", "total", "fechapedido"};
            int[] ids = {R.id.tvIdPedido, R.id.tvNombres, R.id.tvTotal, R.id.tvFechaPedido};

            ListAdapter listAdapter = new SimpleAdapter(this, pedidos, R.layout.item_pedidos, datos, ids);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("POSICION", String.valueOf(i));

        HashMap<String, String> map = (HashMap<String, String>) pedidos.get(i);
        String idPedido = map.get("idpedido").toString();
        String nombres = map.get("nombres").toString();
        String total = map.get("total").toString();
        String fechaPedido = map.get("fechapedido").toString();
        Toast.makeText(this, "Monto total del pedido: "+total, Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString("idpedido", idPedido);
        bundle.putString("nombres", nombres);
        bundle.putString("total", total);
        bundle.putString("fechapedido", fechaPedido);

        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}