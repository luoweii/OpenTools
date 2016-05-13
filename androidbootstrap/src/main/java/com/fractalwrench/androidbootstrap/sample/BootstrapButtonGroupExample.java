package com.fractalwrench.androidbootstrap.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapButtonGroup;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.luowei.androidbootstrap.R;

public class BootstrapButtonGroupExample extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.example_bootstrap_button_group;
    }

    private DefaultBootstrapSize size = DefaultBootstrapSize.MD;

    BootstrapButtonGroup orientationChange;
    BootstrapButtonGroup sizeChange;
    BootstrapButtonGroup outlineChange;
    BootstrapButtonGroup roundedChange;
    BootstrapButtonGroup brandChange;
    BootstrapButtonGroup childChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientationChange = (BootstrapButtonGroup) findViewById(R.id.bbutton_group_orientation_change);
        sizeChange = (BootstrapButtonGroup) findViewById(R.id.bbutton_group_size_change);
        outlineChange = (BootstrapButtonGroup) findViewById(R.id.bbutton_group_outline_change);
        roundedChange = (BootstrapButtonGroup) findViewById(R.id.bbutton_group_rounded_change);
        brandChange = (BootstrapButtonGroup) findViewById(R.id.bbutton_group_brand_change);
        childChange = (BootstrapButtonGroup) findViewById(R.id.bbutton_group_child_change);
        findViewById(R.id.bbutton_group_orientation_change_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHorizontal = orientationChange.getOrientation() == LinearLayout.HORIZONTAL;
                int newOrientation = isHorizontal ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL;
                orientationChange.setOrientation(newOrientation);
            }
        });
        findViewById(R.id.bbutton_group_outline_change_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outlineChange.setShowOutline(!outlineChange.isShowOutline());
            }
        });
        findViewById(R.id.bbutton_group_rounded_change_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundedChange.setRounded(!roundedChange.isRounded());
            }
        });
        findViewById(R.id.bbutton_group_child_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = childChange.getChildCount();

                BootstrapButton button = new BootstrapButton(BootstrapButtonGroupExample.this);
                button.setText(String.format("%d", count + 1));

                childChange.addView(button);
            }
        });
        findViewById(R.id.bbutton_group_child_remove_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = childChange.getChildCount();

                if (count > 0) {
                    childChange.removeViewAt(count - 1);
                }
            }
        });
        findViewById(R.id.bbutton_group_brand_change_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((DefaultBootstrapBrand) brandChange.getBootstrapBrand()) {

                    case PRIMARY:
                        brandChange.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                        break;
                    case SUCCESS:
                        brandChange.setBootstrapBrand(DefaultBootstrapBrand.INFO);
                        break;
                    case INFO:
                        brandChange.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                        break;
                    case WARNING:
                        brandChange.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                        break;
                    case DANGER:
                        brandChange.setBootstrapBrand(DefaultBootstrapBrand.SECONDARY);
                        break;
                    case SECONDARY:
                        brandChange.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                        break;
                    case REGULAR:
                        brandChange.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                        break;
                }
            }
        });
        findViewById(R.id.bbutton_group_size_change_btn).setOnClickListener(new View.OnClickListener() {
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
                sizeChange.setBootstrapSize(size);

            }
        });
    }
}
