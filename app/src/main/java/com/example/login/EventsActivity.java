package com.example.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private EditText description, date,location;
    private TextView Description,Date,Location,Picture,Category,Naslov;
    private ImageView slika;
    private Spinner category;
    private Button addEvent,back;
    public DatabaseReference EventsDb,dogadajDb;
    private Uri resultUri;
    private String desc,loc;
    private String datum,imageUrl;
    public String spin;
    String selectedCategory;
    ActivityResultLauncher<Intent> startForResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result!=null && result.getResultCode()==RESULT_OK) {
                final Uri imageUri=result.getData().getData();
                resultUri=imageUri;
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        description=(findViewById(R.id.description));
        category= (findViewById(R.id.Category));
        slika=(findViewById(R.id.picture));
        addEvent=(findViewById(R.id.add_event));
        back=(findViewById(R.id.back));
        Date= (findViewById(R.id.Date));
        Location= (findViewById(R.id.Location));
        Picture = (findViewById(R.id.Picture));
        mAuth = FirebaseAuth.getInstance();
        String user=mAuth.getCurrentUser().getUid();
        dogadajDb=FirebaseDatabase.getInstance().getReference().child("Users").child(user).child("dogadaj");
        EventsDb= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Event");
        Map eventInfo =new HashMap<>();
        Spinner spinner = (Spinner) findViewById(R.id.Category);
            if(EventsDb.child("description").get()!=null)description.setText(EventsDb.child("description").get().getResult().getValue().toString());
           if(EventsDb.child("location").get()!=null)Location.setText(EventsDb.child("location").get().getResult().getValue().toString());
           if(EventsDb.child("date").get()!=null)Date.setText(EventsDb.child("date").get().getResult().getValue().toString());
       //if(EventsDb.child("pictureUrl").get()!=null)imageUrl=EventsDb.child("pictureUrl").get().getResult().getValue().toString();
//if(EventsDb.child("category").get()!=null){spin=EventsDb.child("category").get().getResult().getValue().toString();
  //  selectSpinnerItemByValue(spinner,spin);}
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
Picture.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startForResult.launch(intent);
    }
});
addEvent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setEventInfo();
        }
    });

back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventsActivity.this,Lista.class));
            }
        });}
    private void setEventInfo() {
        if(resultUri!=null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("images").child(mAuth.getCurrentUser().getUid());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(e -> finish());
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map newImage = new HashMap();
                            newImage.put("pictureUrl", uri.toString());
                            EventsDb.updateChildren(newImage);
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/auth-23952.appspot.com/o/pexels-wendy-wei-1190298.jpg?alt=media&token=fda5cba3-3001-4153-9ede-1ec59881fc1f";
                            return;
                        }
                    });
                }
            });
        }

            desc=description.getText().toString();
            loc=Location.getText().toString();
            datum=Date.getText().toString();
            Map eventInfo=new HashMap();
            eventInfo.put("description",desc);
            eventInfo.put("location",loc);
            eventInfo.put("date",datum);
            eventInfo.put("category",selectedCategory);
            EventsDb.setValue(eventInfo);
            dogadajDb.setValue(true);
            Toast.makeText(EventsActivity.this, "Uspjeh", Toast.LENGTH_SHORT).show();
            finish();


          }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory=(String)adapterView.getItemAtPosition(i);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedCategory="Netflix";
    }
    public static void selectSpinnerItemByValue(Spinner spnr,String spin) {
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position).equals(spin)) {
                spnr.setSelection(position);
                return;
            }
        }
    }

}