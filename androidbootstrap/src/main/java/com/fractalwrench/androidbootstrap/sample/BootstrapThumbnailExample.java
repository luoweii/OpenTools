package com.fractalwrench.androidbootstrap.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.beardedhen.androidbootstrap.utils.DrawableUtils;
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

public class BootstrapThumbnailExample extends BaseActivity {

    private int resId = R.drawable.ladybird;
    private DefaultBootstrapSize size = DefaultBootstrapSize.MD;

    @Override
    protected int getContentLayoutId() {
        return R.layout.example_bootstrap_thumbnail;
    }

    BootstrapThumbnail imageChange;
    BootstrapThumbnail themeChange;
    BootstrapThumbnail borderChange;
    BootstrapThumbnail roundedChange;
    BootstrapThumbnail sizeChange;
    BootstrapThumbnail setBitmapExample;
    BootstrapThumbnail setDrawableExample;
    BootstrapThumbnail setResourceExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageChange = (BootstrapThumbnail) findViewById(R.id.bthumb_image_change_example);
        themeChange = (BootstrapThumbnail) findViewById(R.id.bthumb_theme_change_example);
        borderChange = (BootstrapThumbnail) findViewById(R.id.bthumb_border_change_example);
        roundedChange = (BootstrapThumbnail) findViewById(R.id.bthumb_rounded_change_example);
        sizeChange = (BootstrapThumbnail) findViewById(R.id.bthumb_size_change_example);
        setBitmapExample = (BootstrapThumbnail) findViewById(R.id.bthumb_set_image_bitmap_example);
        setDrawableExample = (BootstrapThumbnail) findViewById(R.id.bthumb_set_image_drawable_example);
        setResourceExample = (BootstrapThumbnail) findViewById(R.id.bthumb_set_image_resource_example);
        themeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((DefaultBootstrapBrand) themeChange.getBootstrapBrand()) {
                    case PRIMARY:
                        themeChange.setBootstrapBrand(SUCCESS);
                        break;
                    case SUCCESS:
                        themeChange.setBootstrapBrand(INFO);
                        break;
                    case INFO:
                        themeChange.setBootstrapBrand(WARNING);
                        break;
                    case WARNING:
                        themeChange.setBootstrapBrand(DANGER);
                        break;
                    case DANGER:
                        themeChange.setBootstrapBrand(SECONDARY);
                        break;
                    case SECONDARY:
                        themeChange.setBootstrapBrand(REGULAR);
                        break;
                    case REGULAR:
                        themeChange.setBootstrapBrand(PRIMARY);
                        break;
                }
            }
        });
        imageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resId == R.drawable.ladybird) {
                    resId = R.drawable.caterpillar;

                } else if (resId == R.drawable.caterpillar) {
                    resId = 0;

                } else if (resId == 0) {
                    resId = R.drawable.ladybird;

                }
                imageChange.setImageResource(resId);
            }
        });
        roundedChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundedChange.setRounded(!roundedChange.isRounded());
            }
        });
        borderChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borderChange.setBorderDisplayed(!borderChange.isBorderDisplayed());
            }
        });
        sizeChange.setOnClickListener(new View.OnClickListener() {
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
                sizeChange.setLayoutParams(getLayoutParams(size.scaleFactor()));
            }
        });

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.small_daffodils);
        setBitmapExample.setImageBitmap(bm);

        setDrawableExample.setImageDrawable(DrawableUtils.resolveDrawable(R.drawable.ladybird,
                this));
        setResourceExample.setImageResource(R.drawable.caterpillar);
        sizeChange.setLayoutParams(getLayoutParams(size.scaleFactor()));
    }

    private LinearLayout.LayoutParams getLayoutParams(float factor) {
        float baselineSize = 300;
        float size = baselineSize * factor;
        return new LinearLayout.LayoutParams((int) size, (int) size);
    }

}
