package com.fractalwrench.androidbootstrap.sample;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading;
import com.luowei.androidbootstrap.R;

import butterknife.Bind;
import butterknife.OnClick;

import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H1;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H2;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H3;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H4;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H5;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H6;

public class BootstrapLabelExample extends BaseActivity {

    @Override protected int getContentLayoutId() {
        return R.layout.example_bootstrap_label;
    }

    BootstrapLabel lblChangeColor;
    BootstrapLabel lblChangeHeading;
    BootstrapLabel lblChangeRounded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lblChangeColor = (BootstrapLabel) findViewById(R.id.example_blabel_change_color);
        lblChangeHeading = (BootstrapLabel) findViewById(R.id.example_blabel_change_heading);
        lblChangeRounded = (BootstrapLabel) findViewById(R.id.example_blabel_change_rounded);
        lblChangeHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        switch ((DefaultBootstrapHeading) lblChangeHeading.getBootstrapHeading()) {
            case H1:
                lblChangeHeading.setBootstrapHeading(H2);
                break;
            case H2:
                lblChangeHeading.setBootstrapHeading(H3);
                break;
            case H3:
                lblChangeHeading.setBootstrapHeading(H4);
                break;
            case H4:
                lblChangeHeading.setBootstrapHeading(H5);
                break;
            case H5:
                lblChangeHeading.setBootstrapHeading(H6);
                break;
            case H6:
                lblChangeHeading.setBootstrapHeading(H1);
                break;
            default:
                lblChangeHeading.setBootstrapHeading(H1);
                break;
        }
            }
        });
        lblChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        switch ((DefaultBootstrapBrand) lblChangeColor.getBootstrapBrand()) {
            case PRIMARY:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                break;
            case SUCCESS:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.INFO);
                break;
            case INFO:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                break;
            case WARNING:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                break;
            case DANGER:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.SECONDARY);
                break;
            case SECONDARY:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                break;
            case REGULAR:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                break;
            default:
                lblChangeColor.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                break;
        }

            }
        });

        lblChangeRounded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        lblChangeRounded.setRounded(!lblChangeRounded.isRounded());
            }
        });
    }
}
