package com.rubahapi.kuaci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PRODUCT;

public class DetailProduct extends AppCompatActivity {

    @BindView(R.id.name_edit)
    TextView nameEdit;

    @BindView(R.id.barcode_edit)
    TextView barcodeEdit;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    Intent intent;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        intent = getIntent();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(TABLE_REF_PRODUCT);

        databaseReference.child(intent.getStringExtra("key")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product = dataSnapshot.getValue(Product.class);
                nameEdit.setText(product.getName());
                barcodeEdit.setText(product.getBarcode());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        if (id == R.id.action_done) {
            action_done_click();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void action_done_click() {
        Toast.makeText(this, product.getName(), Toast.LENGTH_SHORT).show();
//        TODO: Update Still Wrong
        databaseReference.child(intent.getStringExtra("key")).setValue(new Product(product.getName(), product.getBarcode()));
        finish();
    }
}
