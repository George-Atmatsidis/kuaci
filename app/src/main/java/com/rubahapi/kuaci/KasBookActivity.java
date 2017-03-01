package com.rubahapi.kuaci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubahapi.kuaci.pojo.KasBook;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_KAS_BOOK;

public class KasBookActivity extends AppCompatActivity {

    @BindView(R.id.kas_book_recycler_view)
    RecyclerView kasBookRecyclerView;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kas_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(TABLE_REF_KAS_BOOK);

        kasBookRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        final FirebaseRecyclerAdapter<KasBook, KasBookViewHolder> adapter = new FirebaseRecyclerAdapter<KasBook, KasBookViewHolder>(
                KasBook.class,
                R.layout.item_kas_book,
                KasBookViewHolder.class,
                databaseReference.getRef()
        ){

            @Override
            public DatabaseReference getRef(int position) {
                return super.getRef(position);
            }

            @Override
            protected void populateViewHolder(KasBookViewHolder viewHolder, KasBook model, int position) {
                if(null != model){
                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

                    viewHolder.descriptionTextView.setText(model.getDescription());
                    viewHolder.amountTextView.setText(formatter.format(model.getAmount()));
                    viewHolder.dateTextView.setText(dateFormatter.format(model.getDateTransaction()));
                    if(0 != model.getTransactionType()){
                        switch (model.getTransactionType()){
                            case 1 :
                                viewHolder.transactionTypeTextView.setText("D");
                                break;
                            case 2 :
                                viewHolder.transactionTypeTextView.setText("K");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }


        };

        kasBookRecyclerView.setAdapter(adapter);

    }

    public static class KasBookViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionTextView;
        TextView dateTextView;
        TextView transactionTypeTextView;
        TextView amountTextView;
        View view;

        public KasBookViewHolder(View v) {
            super(v);
            view = v;
            descriptionTextView = (TextView) v.findViewById(R.id.description_textView);
            dateTextView = (TextView) v.findViewById(R.id.date_text_view);
            transactionTypeTextView = (TextView) v.findViewById(R.id.transaction_type_text_view);
            amountTextView = (TextView) v.findViewById(R.id.amount_text_view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kas_book, menu);
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
        Intent intent = new Intent(KasBookActivity.this, CreateKasBookActivity.class);
        KasBookActivity.this.startActivity(intent);
    }
}
