package com.rubahapi.kuaci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rubahapi.kuaci.pojo.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PERSON;
import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PRODUCT;

public class DetailProduct extends AppCompatActivity {

    @BindView(R.id.name_edit)
    TextView nameEdit;

    @BindView(R.id.barcode_edit)
    TextView barcodeEdit;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        intent = getIntent();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(TABLE_REF_PRODUCT);

        Toast.makeText(this, intent.getStringExtra("key"),Toast.LENGTH_SHORT).show();
//        databaseReference.child(intent.getStringExtra("key")).setValue(product);

        databaseReference.child(intent.getStringExtra("key")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
//                TODO: Still Error Here
//                nameEdit.setText(product.getName());
//                barcodeEdit.setText(product.getBarcode());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
