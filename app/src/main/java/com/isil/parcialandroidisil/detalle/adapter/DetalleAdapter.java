package com.isil.parcialandroidisil.detalle.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.isil.parcialandroidisil.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class DetalleAdapter extends ArrayAdapter {
    Activity activity;
    ArrayList<HashMap<String,String>> maps;

    public DetalleAdapter(Activity activity, ArrayList<HashMap<String, String>> maps) {
        super(activity, maps.size());
        this.activity = activity;
        this.maps = maps;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = layoutInflater.inflate(R.layout.item_detalle,null);

        TextView mtvIdproducto = rootView.findViewById(R.id.tvIdProducto);
        TextView mtvNombre = rootView.findViewById(R.id.tvNombre);
        TextView mtvDetalle = rootView.findViewById(R.id.tvDetalle);
        TextView mtvPrecio = rootView.findViewById(R.id.tvPrecio);
        TextView mtvCantidad = rootView.findViewById(R.id.tvCantidad);
        ImageView mivImagenchica = rootView.findViewById(R.id.ivImagenChica);
        //LinearLayout mllItemproducto = rootView.findViewById(R.id.llItemProducto);

        HashMap<String,String> map = maps.get(position);

        mtvIdproducto.setText(map.get("idproducto"));
        mtvNombre.setText(map.get("nombre"));
        mtvDetalle.setText(map.get("detalle"));
        mtvPrecio.setText(map.get("precio"));
        mtvCantidad.setText(map.get("cantidad"));

        String rutaImagen = "https://servicioweb.pix.pe/" + map.get("imagenchica");
        Picasso.get().load(rutaImagen).fit().into(mivImagenchica);

        return rootView;
    }

    @Override
    public int getCount() {
        return maps.size();
    }
}
