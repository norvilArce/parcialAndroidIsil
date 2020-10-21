package com.isil.parcialandroidisil.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.isil.parcialandroidisil.PedidosActivity;
import com.isil.parcialandroidisil.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAvengers, btnJustice;
    RelativeLayout llContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setLogo(R.mipmap.ic_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(" Superhéroes");

        llContenedor = findViewById(R.id.rlContenedor);
        btnJustice = findViewById(R.id.btnDc);
        btnAvengers = findViewById(R.id.btnMarvel);

        btnAvengers.setOnClickListener(this);
        btnJustice.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDc:
                cargarJusticeLeague();
                break;
            case R.id.btnMarvel:
                cargarAvengers();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_pedidos:
                irAlPedidosActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void irAlPedidosActivity() {
        Intent intent = new Intent(this, PedidosActivity.class);
        intent.putExtra("titulo", "Pedidos");
        startActivity(intent);
    }

    private void cargarAvengers() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rlContenedor, new AvengersFragment())
                .commit();
    }

    private void cargarJusticeLeague() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rlContenedor, new JusticeLeagueFragment())
                .commit();
    }
}