package com.rubahapi.kuaci;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rubahapi.kuaci.pojo.KasBook;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rubahapi.kuaci.R.id.action_delete;
import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_KAS_BOOK;

public class DetailKasBookActivity extends AppCompatActivity {

    @BindView(R.id.date_transaction_edit)
    TextView dateTransactionEdit;

    @BindView(R.id.description_edit)
    TextView descriptionEdit;

    @BindView(R.id.amount_edit)
    TextView amountEdit;

    @BindView(R.id.radio_pemasukan)
    RadioButton radioPemasukan;

    @BindView(R.id.radio_pengeluaran)
    RadioButton radioPengeluaran;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    Intent intent;
    KasBook kasBook;

    private int transactionType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kas_book);

        ButterKnife.bind(this);

        intent = getIntent();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(TABLE_REF_KAS_BOOK);

        databaseReference.child(intent.getStringExtra("key")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kasBook = dataSnapshot.getValue(KasBook.class);
                if(null != kasBook){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    descriptionEdit.setText(kasBook.getDescription());
                    amountEdit.setText(kasBook.getAmount().toString());
                    dateTransactionEdit.setText(dateFormat.format(kasBook.getDateTransaction()));
                    transactionType = kasBook.getTransactionType();

                    if(0 != kasBook.getTransactionType()){
                        switch (kasBook.getTransactionType()){
                            case 1 :
                                radioPemasukan.toggle();
                                break;
                            case 2 :
                                radioPengeluaran.toggle();
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_kas_book, menu);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        kasBook.setDescription(descriptionEdit.getText().toString());
        kasBook.setAmount(Float.parseFloat(amountEdit.getText().toString()));
        try {
            kasBook.setDateTransaction(dateFormat.parse(dateTransactionEdit.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        kasBook.setTransactionType(transactionType);
        databaseReference.child(intent.getStringExtra("key")).setValue(kasBook);
        finish();
    }

    @OnClick({R.id.radio_pemasukan, R.id.radio_pengeluaran})
    public void onClickRadioTransactionType(View view){

        boolean checked= ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radio_pemasukan:
                if(checked)
                    transactionType = 1;
                break;
            case R.id.radio_pengeluaran:
                if (checked)
                    transactionType = 2;
                break;
        }
    }
}
