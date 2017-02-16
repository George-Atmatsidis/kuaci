package com.rubahapi.kuaci;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SampleDatabaseActivity extends AppCompatActivity {

    @BindView(R.id.text_description)
    TextView textViewDescription;

    private StorageReference storageReference;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_database);

        ButterKnife.bind(this);

        storageReference = FirebaseStorage.getInstance().getReference();

    }

    @OnClick(R.id.browse_button)
    public void onButtonBrowseClick(View view){
        galleryIntent();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri result = data.getData();
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == SELECT_FILE){
//                Toast.makeText(this, result.getPath(), Toast.LENGTH_SHORT).show();
                StorageReference ref = storageReference.child("images/"+result.getLastPathSegment());
                UploadTask uploadTask = ref.putFile(result);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        textViewDescription.setText(downloadUrl.getPath());
                    }
                });
            }
        }
    }
}
