package com.rubahapi.kuaci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubahapi.kuaci.pojo.KasBook;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.rubahapi.kuaci.R.id.action_done;
import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_KAS_BOOK;

public class CreateKasBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kas_book);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        newRef.child(newRef.push().getKey()).setValue(new KasBook(new Date(), "Desc", 999, 1));
        finish();
    }
}
