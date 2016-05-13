package com.fractalwrench.androidbootstrap.sample;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.luowei.androidbootstrap.R;

import butterknife.Bind;
import butterknife.OnClick;

import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand.DANGER;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand.INFO;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand.PRIMARY;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand.REGULAR;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand.SECONDARY;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand.SUCCESS;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand.WARNING;

public class BootstrapEditTextExample extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.example_bootstrap_edit_text_view;
    }

    private DefaultBootstrapSize size = DefaultBootstrapSize.MD;

    BootstrapEditText changeEnabled;
    BootstrapEditText changeRound;
    BootstrapEditText changeTheme;
    BootstrapEditText sizeExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeEnabled = (BootstrapEditText) findViewById(R.id.bedit_text_change_enabled);
        changeRound = (BootstrapEditText) findViewById(R.id.bedit_text_change_round);
        changeTheme = (BootstrapEditText) findViewById(R.id.bedit_text_change_theme);
        sizeExample = (BootstrapEditText) findViewById(R.id.bedit_text_change_size);

        findViewById(R.id.bedit_text_change_enabled_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEnabled.setEnabled(!changeEnabled.isEnabled());
            }
        });

        findViewById(R.id.bedit_text_change_round_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeRound.setRounded(!changeRound.isRounded());
            }
        });
        findViewById(R.id.bedit_text_change_theme_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((DefaultBootstrapBrand) changeTheme.getBootstrapBrand()) {
                    case PRIMARY:
                        changeTheme.setBootstrapBrand(SUCCESS);
                        break;
                    case SUCCESS:
                        changeTheme.setBootstrapBrand(INFO);
                        break;
                    case INFO:
                        changeTheme.setBootstrapBrand(WARNING);
                        break;
                    case WARNING:
                        changeTheme.setBootstrapBrand(DANGER);
                        break;
                    case DANGER:
                        changeTheme.setBootstrapBrand(SECONDARY);
                        break;
                    case SECONDARY:
                        changeTheme.setBootstrapBrand(REGULAR);
                        break;
                    case REGULAR:
                        changeTheme.setBootstrapBrand(PRIMARY);
                        break;
                }
            }
        });
        findViewById(R.id.bedit_text_change_size_btn).setOnClickListener(new View.OnClickListener() {
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
                sizeExample.setBootstrapSize(size);
            }
        });
    }
}
