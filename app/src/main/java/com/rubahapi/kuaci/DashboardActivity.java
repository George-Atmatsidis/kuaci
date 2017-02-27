package com.rubahapi.kuaci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rubahapi.kuaci.pojo.KasBook;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_products)
    public void onClickProductButton(View view){
        Intent intent = new Intent(DashboardActivity.this, ProductActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.button_kas_book)
    public void onClickKasBook(View view){
        Intent intent = new Intent(DashboardActivity.this, KasBookActivity.class);
        this.startActivity(intent);
    }
}
