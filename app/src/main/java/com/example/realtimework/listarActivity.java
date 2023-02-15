package com.example.realtimework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.realtimework.Adapter.
        myAdapter;
import com.example.realtimework.Modelo.Productos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listarActivity extends AppCompatActivity {
private RecyclerView recyclerView;
    private Toolbar customToolbar;
ArrayList<Productos> list;
DatabaseReference mDatabase;
myAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        recyclerView=findViewById(R.id.rvListaProductos);
        customToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(customToolbar);
        mDatabase= FirebaseDatabase.getInstance().getReference("Productos");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new myAdapter(this,list);
        recyclerView.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
              for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                  Productos producto=dataSnapshot.getValue(Productos.class);
                  list.add(producto);
              }
              adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.itVentas){

            Toast.makeText(this,"Ventas",Toast.LENGTH_LONG).show();
            Intent i= new Intent(getApplicationContext(),ventasActivity.class);
            i.putExtra("accion","Ventas");
                    startActivity(i);
        }else if (id==R.id.itCompras){

            Toast.makeText(this,"Compras",Toast.LENGTH_LONG).show();
            Intent i= new Intent(getApplicationContext(),ventasActivity.class);
            i.putExtra("accion","Compras");
            startActivity(i);
        }else if(id==R.id.itCerrarSesion){
            SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
            //preferences.edit().clear().commit();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }else if(id==R.id.itCompartir){
            Toast.makeText(this,"Compartir",Toast.LENGTH_LONG).show();
        }else if(id==R.id.itBuscar){
            Toast.makeText(this, "Buscar", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}