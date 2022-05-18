package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.people.PeopleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class swp extends AppCompatActivity implements View.OnClickListener{
    private arrayAdapter arrayAdapter;
    private int i;
    private Button profil;
    private Button lista;
    ListView listView;
    List<cards> rowItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swp);

        profil= findViewById(R.id.profil);
        profil.setOnClickListener(swp.this);
        lista= findViewById(R.id.lista);
        lista.setOnClickListener(swp.this);
        rowItems = new ArrayList<cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.details, rowItems );
        addEvent();
        SwipeFlingAdapterView flingContainer=(SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(swp.this, "Left!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(swp.this, "Right!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                // al.add("XML ".concat(String.valueOf(i)));
                //arrayAdapter.notifyDataSetChanged();
                //Log.d("LIST", "notified");
                //i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.profil:
                startActivity(new Intent(swp.this, Profil.class));
                break;
            case R.id.lista:
                startActivity(new Intent(swp.this, Lista.class));
        }
    }
    private void addEvent() {

            final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference eventsDb= FirebaseDatabase.getInstance().getReference().child("Users");
            eventsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.exists()){
                        cards Item=new cards(snapshot.getKey(),snapshot.child("Event").child("name").getValue().toString(),snapshot.child("Event").child("pictureUrl").getValue().toString());
                        rowItems.add(Item);
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
    }
    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }
}