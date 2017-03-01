package com.rubahapi.kuaci;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubahapi.kuaci.component.DatePickerFragment;
import com.rubahapi.kuaci.listener.OnDatePickerClickListener;
import com.rubahapi.kuaci.pojo.KasBook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rubahapi.kuaci.R.id.action_done;
import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_KAS_BOOK;

public class CreateKasBookActivity extends AppCompatActivity implements OnDatePickerClickListener{

    @BindView(R.id.date_transaction_edit)
    TextView dateTransactionEdit;

    @BindView(R.id.description_edit)
    TextView descriptionEdit;

    @BindView(R.id.amount_edit)
    TextView amountEdit;

    private int transactionType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kas_book);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_kas_book, menu);
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
        DatabaseReference newRef = database.getReference(TABLE_REF_KAS_BOOK);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            newRef.child(newRef.push().getKey()).setValue(new KasBook(dateFormat.parse(dateTransactionEdit.getText().toString()),
                    descriptionEdit.getText().toString(),
                    Float.parseFloat(amountEdit.getText().toString()),
                    transactionType));

            finish();
        } catch (ParseException e) {
            Toast.makeText(this,"Data Not Saved, Date is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.date_transaction_edit)
    public void OnClickDateTransactionEdit(View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String transactionDate = dateTransactionEdit.getText().toString();

        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        if(!transactionDate.equals("")){
            bundle.putInt("year", Integer.parseInt(splitString(transactionDate,2)));
            bundle.putInt("month",Integer.parseInt(splitString(transactionDate,1)));
            bundle.putInt("day",Integer.parseInt(splitString(transactionDate,0)));
        }else{
            bundle.putInt("year", mYear);
            bundle.putInt("month",mMonth);
            bundle.putInt("day",mDay);
        }

        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String splitString(String value, int retNum){
        String[] result = value.split("-");
        return result[retNum];
    }

    @Override
    public void onDatePickerClickListener(int year, int month, int date) {
        dateTransactionEdit.setText(date + "-" + month + "-" + year);
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
