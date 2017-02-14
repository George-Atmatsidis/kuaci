package com.rubahapi.kuaci;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubahapi.kuaci.pojo.Product;

import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PERSON;

public class ProductActivity extends AppCompatActivity {

    RecyclerView productRecyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, CreateProductActivity.class);
                ProductActivity.this.startActivity(intent);
            }
        });

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(TABLE_REF_PERSON);

        productRecyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        final FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.item_product,
                ProductViewHolder.class,
                databaseReference.getRef()) {

            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, final Product model, final int position) {
                viewHolder.nameTextView.setText(model.getName());
                viewHolder.barcodeTextView.setText(model.getBarcode());

                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ProductActivity.this, "You Click on " + model.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        productRecyclerView.setAdapter(adapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView barcodeTextView;
        View view;

        public ProductViewHolder(View v) {
            super(v);
            view = v;
            nameTextView = (TextView) v.findViewById(R.id.name_textView);
            barcodeTextView = (TextView) v.findViewById(R.id.barcode_textView);
        }
    }
}
