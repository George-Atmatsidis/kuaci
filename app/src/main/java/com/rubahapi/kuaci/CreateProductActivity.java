package com.rubahapi.kuaci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubahapi.kuaci.pojo.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rubahapi.kuaci.R.id.action_done;
import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PRODUCT;

public class CreateProductActivity extends AppCompatActivity {

    @BindView(R.id.name_edit)
    EditText nameEditText;

    @BindView(R.id.barcode_edit)
    EditText barcodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == action_done) {
            action_done_click();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void action_done_click() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newRef = database.getReference(TABLE_REF_PRODUCT);
        newRef.child(newRef.push().getKey()).setValue(new Product(nameEditText.getText().toString(),barcodeEditText.getText().toString()));
        finish();
    }
}
