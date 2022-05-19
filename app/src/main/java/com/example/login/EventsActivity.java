package com.example.login;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class EventsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private EditText description, date,location;
    private TextView Description,Date,Location,Picture,Category,Naslov;
    private ImageView slika;
    private Spinner category;
    private Button addEvent;
    public DatabaseReference EventsDb,dogadajDb;
    private Uri resultUri;
    private String desc,loc;
    private String datum,imageUrl;
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
        Naslov= (findViewById(R.id.banner_event));
        Date= (findViewById(R.id.Date));
        Location= (findViewById(R.id.Location));
        Picture = (findViewById(R.id.Picture));
        mAuth = FirebaseAuth.getInstance();
        dogadajDb=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("dogadaj");
        EventsDb= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Event");
        Map eventInfo =new HashMap<>();
        Spinner spinner = (Spinner) findViewById(R.id.Category);
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
    }

    private void setEventInfo() {
        if(resultUri!=null){
            StorageReference filepath= FirebaseStorage.getInstance().getReference().child("images").child(mAuth.getCurrentUser().getUid());
            Bitmap bitmap=null;
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
            byte[] data= baos.toByteArray();
            UploadTask uploadTask=filepath.putBytes(data);
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

                            finish();
                            return;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            imageUrl="https://firebasestorage.googleapis.com/v0/b/auth-23952.appspot.com/o/pexels-wendy-wei-1190298.jpg?alt=media&token=fda5cba3-3001-4153-9ede-1ec59881fc1f";
                            finish();
                            return;
                        }
                    });
                }
            });

        desc=description.getText().toString();
        loc=Location.getText().toString();
        datum=Date.getText().toString();
        Map eventInfo=new HashMap();
        eventInfo.put("description",desc);
        eventInfo.put("location",loc);
        eventInfo.put("date",datum);
        eventInfo.put("category",selectedCategory);
        eventInfo.put("pictureUrl",imageUrl);
        EventsDb.updateChildren(eventInfo);
        dogadajDb.setValue(true);


        }}
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory=(String)adapterView.getItemAtPosition(i);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedCategory="Zabava";
    }


}