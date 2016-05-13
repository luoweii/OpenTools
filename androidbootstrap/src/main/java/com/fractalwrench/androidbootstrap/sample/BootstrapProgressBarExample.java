package com.fractalwrench.androidbootstrap.sample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.luowei.androidbootstrap.R;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

public class BootstrapProgressBarExample extends BaseActivity {

    enum ChangeState {
        FIRST(false, false),
        SECOND(false, true),
        THIRD(true, false),
        FOURTH(true, true);

        private final boolean animated;
        private final boolean striped;

        ChangeState(boolean animated, boolean striped) {
            this.animated = animated;
            this.striped = striped;
        }

        public ChangeState next() {
            switch (this) {
                case FIRST:
                    return SECOND;
                case SECOND:
                    return THIRD;
                case THIRD:
                    return FOURTH;
                case FOURTH:
                    return FIRST;
                default:
                    return FIRST;
            }
        }
    }

    private Random random;
    private ChangeState changeState = ChangeState.FIRST;
    private DefaultBootstrapSize size = DefaultBootstrapSize.MD;

    @Override protected int getContentLayoutId() {
        return R.layout.example_bootstrap_progress_bar;
    }

   BootstrapProgressBar defaultExample;
   BootstrapProgressBar animatedExample;
   BootstrapProgressBar stripedExample;
   BootstrapProgressBar stripedAnimExample;
   BootstrapProgressBar changeExample;
   BootstrapProgressBar sizeExample;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        defaultExample = (BootstrapProgressBar) findViewById(R.id.example_progress_default);
        animatedExample = (BootstrapProgressBar) findViewById(R.id.example_progress_animated);
        stripedExample = (BootstrapProgressBar) findViewById(R.id.example_progress_striped);
        stripedAnimExample = (BootstrapProgressBar) findViewById(R.id.example_progress_striped_animated);
        changeExample = (BootstrapProgressBar) findViewById(R.id.example_progress_change);
        sizeExample = (BootstrapProgressBar) findViewById(R.id.example_size_change);

        findViewById(R.id.example_progress_default_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        defaultExample.setProgress(randomProgress(defaultExample.getProgress()));

            }
        });
        findViewById(R.id.example_progress_animated_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        animatedExample.setProgress(randomProgress(animatedExample.getProgress()));
            }
        });
        findViewById(R.id.example_progress_striped_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        stripedExample.setProgress(randomProgress(stripedExample.getProgress()));
            }
        });
        findViewById(R.id.example_progress_striped_animated_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        stripedAnimExample.setProgress(randomProgress(stripedAnimExample.getProgress()));
            }
        });
        findViewById(R.id.example_progress_change_type_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        changeState = changeState.next();
        changeExample.setStriped(changeState.striped);
        changeExample.setAnimated(changeState.animated);

            }
        });
        findViewById(R.id.example_progress_change_rounded_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        changeExample.setRounded(!changeExample.isRounded());
            }
        });
        findViewById(R.id.example_progress_change_color_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        switch ((DefaultBootstrapBrand) changeExample.getBootstrapBrand()) {
            case PRIMARY:
                changeExample.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                break;
            case SUCCESS:
                changeExample.setBootstrapBrand(DefaultBootstrapBrand.INFO);
                break;
            case INFO:
                changeExample.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                break;
            case WARNING:
                changeExample.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                break;
            case DANGER:
                changeExample.setBootstrapBrand(DefaultBootstrapBrand.SECONDARY);
                break;
            case SECONDARY:
                changeExample.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
            case REGULAR:
                changeExample.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                break;
        }
            }
        });
        findViewById(R.id.example_size_change_btn).setOnClickListener(new View.OnClickListener() {
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
    private int randomProgress(int currentProgress) {
        if (random == null) {
            random = new Random();
        }

        int prog = currentProgress + random.nextInt(20);

        if (prog > 100) {
            prog -= 100;
        }

        return prog;
    }
}
