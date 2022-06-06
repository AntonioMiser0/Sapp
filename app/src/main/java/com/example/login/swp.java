package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class swp extends AppCompatActivity implements View.OnClickListener {
    private arrayAdapter arrayAdapter;
    ImageButton profil;
    ImageButton lista;
    ImageButton kat;
    List<Event> rowItems;
    FirebaseAuth mAuth;
    String currentUser;
    String filter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swp);
        Bundle extras = getIntent().getExtras();
        filter="";
        if (extras != null) {
             filter= extras.getString("fil");
            //The key argument here must match that used in the other activity
        }
        profil= findViewById(R.id.profil);
        profil.setOnClickListener(this);
        lista= findViewById(R.id.lista);
        lista.setOnClickListener(this);
        kat= findViewById(R.id.kategorije);
        kat.setOnClickListener(this);

        rowItems = new ArrayList<Event>();
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();
        arrayAdapter = new arrayAdapter(swp.this, R.layout.details, rowItems );
        addEvent();
        SwipeFlingAdapterView flingContainer= findViewById(R.id.frame);
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
                Toast.makeText(swp.this, "Removed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(swp.this, "Added",Toast.LENGTH_SHORT).show();
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
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.profil:
                startActivity(new Intent(swp.this, Profil.class));
                break;
            case R.id.lista:
                startActivity(new Intent(swp.this, Lista.class));
                break;
           case R.id.kategorije:
              startActivity(new Intent(swp.this, Filter.class));
               break;


        }
    }
    private void addEvent() {

            final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference eventsDb= FirebaseDatabase.getInstance().getReference().child("Users");
            eventsDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    boolean lazes=(snapshot.getKey()).equals(currentUser);
                    if(snapshot.child("dogadaj").getValue().equals(true)&&(!lazes)){
                        if(filter==""){
                        Event Item = new Event(snapshot.getKey(), snapshot.child("Event").child("description").getValue().toString(),
                                snapshot.child("Event").child("location").getValue().toString(),
                                snapshot.child("Event").child("category").getValue().toString()
                                , snapshot.child("Event").child("date").getValue().toString()
                                , snapshot.child("Event").child("pictureUrl").getValue().toString()
                                , snapshot.child("fullName").getValue().toString(), snapshot.child("age").getValue().toString());
                        rowItems.add(Item);
                        arrayAdapter.notifyDataSetChanged();
                        }
                        else if(snapshot.child(user.getUid()).child("Event").child("category").getValue().equals(filter)){
                            Event Item = new Event(snapshot.getKey(), snapshot.child("Event").child("description").getValue().toString(),
                                    snapshot.child("Event").child("location").getValue().toString(),
                                    snapshot.child("Event").child("category").getValue().toString()
                                    , snapshot.child("Event").child("date").getValue().toString()
                                    , snapshot.child("Event").child("pictureUrl").getValue().toString()
                                    , snapshot.child("fullName").getValue().toString(), snapshot.child("age").getValue().toString());
                            rowItems.add(Item);
                            arrayAdapter.notifyDataSetChanged();

                        }
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
  //  static void makeToast(Context ctx, String s){
 //       Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
//    }
}