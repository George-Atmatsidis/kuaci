package com.rubahapi.kuaci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rubahapi.kuaci.pojo.Person;

import static com.rubahapi.kuaci.config.ServerPath.TABLE_REF_PERSON;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
        DatabaseReference newRef = database.getReference(TABLE_REF_PERSON);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.person_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        final FirebaseRecyclerAdapter<Person, PersonViewHolder> adapter = new FirebaseRecyclerAdapter<Person, PersonViewHolder>(
                Person.class,
                R.layout.item_person,
                PersonViewHolder.class,
                newRef.getRef()) {
            @Override
            protected void populateViewHolder(PersonViewHolder viewHolder, Person model, int position) {
                viewHolder.textView.setText(model.getName());
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public PersonViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.name_textView);
        }
    }
}
