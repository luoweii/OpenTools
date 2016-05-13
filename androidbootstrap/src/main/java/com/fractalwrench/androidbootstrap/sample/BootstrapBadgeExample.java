package com.fractalwrench.androidbootstrap.sample;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapBadge;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.luowei.androidbootstrap.R;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

public class BootstrapBadgeExample extends BaseActivity {

    BootstrapButton xmlBadgeButton;
    BootstrapButton javaBadgeButton;
    BootstrapBadge lonelyBadge;

    @Override
    protected int getContentLayoutId() {
        return R.layout.example_bootstrap_badge;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xmlBadgeButton = (BootstrapButton) findViewById(R.id.xml_badge_button);
        javaBadgeButton = (BootstrapButton) findViewById(R.id.java_badge_button);
        lonelyBadge = (BootstrapBadge) findViewById(R.id.lonely_badge);
        lonelyBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lonelyBadge.setBadgeText(String.valueOf(new Random().nextInt()));
            }
        });
        xmlBadgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xmlBadgeButton.setBadgeText(String.valueOf(new Random().nextInt()));
            }
        });
        javaBadgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                javaBadgeButton.setBadgeText(String.valueOf(new Random().nextInt()));
            }
        });
        BootstrapBadge badgeThird = new BootstrapBadge(this);
        badgeThird.setBadgeText("Hi!");
        javaBadgeButton.setBadge(badgeThird);
    }

}
