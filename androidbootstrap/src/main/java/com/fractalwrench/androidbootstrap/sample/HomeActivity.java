package com.fractalwrench.androidbootstrap.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.luowei.androidbootstrap.R;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        findViewById(R.id.example_bootstrap_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, BootstrapButtonExample.class));
            }
        });
        findViewById(R.id.example_fontawesometext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, AwesomeTextViewExample.class));
            }
        });
        findViewById(R.id.example_bootstrap_label).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, BootstrapLabelExample.class));
            }
        });
        findViewById(R.id.example_bootstrap_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BootstrapProgressBarExample.class));

            }
        });
        findViewById(R.id.example_bootstrap_btn_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, BootstrapButtonGroupExample.class));
            }
        });
        findViewById(R.id.example_bootstrap_cricle_thumbnail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, BootstrapCircleThumbnailExample.class));
            }
        });
        findViewById(R.id.example_bootstrap_edit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, BootstrapEditTextExample.class));
            }
        });
        findViewById(R.id.example_bootstrap_thumbnail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BootstrapThumbnailExample.class));

            }
        });
        findViewById(R.id.example_bootstrap_well).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, BootstrapWellExample.class));
            }
        });
        findViewById(R.id.example_bootstrap_dropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BootstrapDropDownExample.class));

            }
        });
        findViewById(R.id.example_bootstrap_alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, BootstrapAlertExample.class));
            }
        });
        findViewById(R.id.example_bootstrap_badge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BootstrapBadgeExample.class));

            }
        });
    }
}