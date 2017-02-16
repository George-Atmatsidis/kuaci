package com.rubahapi.kuaci;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rubahapi.kuaci.pojo.Product;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rubahapi.kuaci.R.id.action_delete;
import static com.rubahapi.kuaci.R.id.image_take_photo;
import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PRODUCT;

public class DetailProduct extends AppCompatActivity {

    @BindView(R.id.name_edit)
    TextView nameEdit;

    @BindView(R.id.barcode_edit)
    TextView barcodeEdit;

    @BindView(R.id.image_take_photo)
    ImageView imageTakePhoto;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    Intent intent;
    Product product;

    ProgressDialog progressDialog;

    private int REQUEST_CAMERA = 0;

    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Retreieve Data From Server");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        progressDialog.show();
    }

    private void hideProgressDialog(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.hide();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        intent = getIntent();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(TABLE_REF_PRODUCT);
        storageReference = FirebaseStorage.getInstance().getReference();

        showProgressDialog();

        databaseReference.child(intent.getStringExtra("key")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product = dataSnapshot.getValue(Product.class);
                if(null != product){
                    nameEdit.setText(product.getName());
                    barcodeEdit.setText(product.getBarcode());
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideProgressDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_done:
                action_done_click();
                return true;
            case action_delete:
                action_delete_click();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void action_delete_click() {

        new AlertDialog.Builder(this).setTitle("Confirmation")
                .setMessage("Do you want delete this record ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(intent.getStringExtra("key")).removeValue();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no,null).show();
    }

    private void action_done_click() {
        product.setName(nameEdit.getText().toString());
        product.setBarcode(barcodeEdit.getText().toString());
        databaseReference.child(intent.getStringExtra("key")).setValue(product);
        finish();
    }

    @OnClick(R.id.image_take_photo)
    public void onClickTakeImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
//        Uri result = data.getData();
//        StorageReference ref = storageReference.child("images/"+result.getLastPathSegment());
//        UploadTask uploadTask = ref.putFile(result);

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        imageTakePhoto.setImageBitmap(thumbnail);

        byte[] dataSent = bytes.toByteArray();

        Uri result = data.getData();
        StorageReference ref = storageReference.child("images/"+result.getLastPathSegment());
        UploadTask uploadTask = ref.putBytes(dataSent);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }
}
