package com.fractalwrench.androidbootstrap.sample;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.luowei.androidbootstrap.R;

import butterknife.Bind;
import butterknife.OnClick;

public class BootstrapButtonExample extends BaseActivity {

    @Override protected int getContentLayoutId() {
        return R.layout.example_bootstrap_button;
    }

    private DefaultBootstrapSize size = DefaultBootstrapSize.LG;

    BootstrapButton exampleCorners;
    BootstrapButton exampleOutline;
    BootstrapButton exampleSize;
    BootstrapButton exampleTheme;
    BootstrapButton exampleCustomStyle;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exampleCorners = (BootstrapButton) findViewById(R.id.bbutton_example_corners);
        exampleOutline = (BootstrapButton) findViewById(R.id.bbutton_example_outline);
        exampleSize = (BootstrapButton) findViewById(R.id.bbutton_example_size);
        exampleTheme = (BootstrapButton) findViewById(R.id.bbutton_example_theme);
        exampleCustomStyle = (BootstrapButton) findViewById(R.id.example_bbutton_custom_style);
        exampleCorners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exampleCorners.setRounded(!exampleCorners.isRounded());
            }
        });
        exampleOutline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exampleOutline.setShowOutline(!exampleOutline.isShowOutline());
            }
        });
        exampleSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (size) {
                    case XS:
                        size = DefaultBootstrapSize.SM;
                        break;
                    case SM:
                        size = DefaultBootstrapSize.MD;
                        break;
                    case MD:
                        size = DefaultBootstrapSize.LG;
                        break;
                    case LG:
                        size = DefaultBootstrapSize.XL;
                        break;
                    case XL:
                        size = DefaultBootstrapSize.XS;
                        break;
                }
                exampleSize.setBootstrapSize(size);
            }
        });
        exampleTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((DefaultBootstrapBrand) exampleTheme.getBootstrapBrand()) {
                    case PRIMARY:
                        exampleTheme.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                        break;
                    case SUCCESS:
                        exampleTheme.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                        break;
                    case WARNING:
                        exampleTheme.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                        break;
                    case DANGER:
                        exampleTheme.setBootstrapBrand(DefaultBootstrapBrand.INFO);
                        break;
                    case INFO:
                        exampleTheme.setBootstrapBrand(DefaultBootstrapBrand.SECONDARY);
                        break;
                    case SECONDARY:
                        exampleTheme.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                        break;
                    case REGULAR:
                        exampleTheme.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                        break;
                }
            }
        });
        setupCustomStyle();
    }

    private void setupCustomStyle() {
        // create a custom bootstrap size
        exampleCustomStyle.setBootstrapSize(3.0f);

        // create a Bootstrap Theme with holo colors
        exampleCustomStyle.setBootstrapBrand(new CustomBootstrapStyle(this));
    }

}