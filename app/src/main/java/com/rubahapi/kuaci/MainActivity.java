package com.rubahapi.kuaci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubahapi.kuaci.listener.OnProductClickListener;
import com.rubahapi.kuaci.pojo.Product;

import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PERSON;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
        DatabaseReference newRef = database.getReference(TABLE_REF_PERSON);


        newRef.child(newRef.push().getKey()).setValue(new Product("Kunci","1212"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        final FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,
                R.layout.item_product,
                ProductViewHolder.class,
                newRef.getRef()) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, final int position) {
                viewHolder.nameTextView.setText(model.getName());
                viewHolder.barcodeTextView.setText(model.getBarcode());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "You Click on " + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView barcodeTextView;
        View mView;

        public ProductViewHolder(View v) {
            super(v);
            mView = v;
            nameTextView = (TextView) v.findViewById(R.id.name_textView);
            barcodeTextView = (TextView) v.findViewById(R.id.barcode_textView);
        }
    }
}
