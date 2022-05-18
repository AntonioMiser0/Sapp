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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

public class EventsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText description, date,location;
    private TextView Description,Date,Location,Picture,Category,Naslov;
    private ImageView slika;
    private Spinner category;
    private Button addEvent;
    public DatabaseReference EventsDb;
    private Uri resultUri;
    private String desc,loc;
    private java.sql.Date datum;
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

        description=(EditText) (findViewById(R.id.description));
        date=(EditText) (findViewById(R.id.date));
        location=(EditText) (findViewById(R.id.location));
        category=(Spinner) (findViewById(R.id.Category));
        slika=(ImageView)(findViewById(R.id.picture));
        addEvent=(Button)(findViewById(R.id.add_event));
        Naslov=(TextView) (findViewById(R.id.banner_event));
        Date=(TextView) (findViewById(R.id.Date));
        Location=(TextView) (findViewById(R.id.Location));
        Picture =(TextView) (findViewById(R.id.Picture));
        mAuth = FirebaseAuth.getInstance();
        EventsDb= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid().toString()).child("Events");
        Map eventInfo =new HashMap<>();

Picture.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startForResult.launch(intent);
    }
});
addEvent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getEventInfo();
    }
});
    }

    private void getEventInfo() {
        desc=description.getText().toString();
        loc=location.getText().toString();
        datum=(Date)date.getText();

        Map eventInfo=new HashMap();
        eventInfo.put("description",desc);
        eventInfo.put("location",loc);
        eventInfo.put("date",datum);
        EventsDb.updateChildren(eventInfo);
        EventsDb.child("description").setValue(desc);
        EventsDb.child("location").setValue(loc);
        EventsDb.child("date").setValue(datum);

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
            uploadTask.addOnFailureListener(new OnFailureListener() {@Override
            public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    Map eventInfo=new HashMap();
                    eventInfo.put("imageUrl",downloadUrl);
                    EventsDb.updateChildren(eventInfo);
                    EventsDb.child("pictureUrl").setValue(downloadUrl);

                    finish();

                    return;
                }
            });

        }else {
        finish();
        }
        }
    }