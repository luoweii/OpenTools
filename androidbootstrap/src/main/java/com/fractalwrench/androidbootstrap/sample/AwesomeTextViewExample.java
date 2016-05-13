package com.fractalwrench.androidbootstrap.sample;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.luowei.androidbootstrap.R;

import butterknife.OnClick;

import static com.beardedhen.androidbootstrap.font.FontAwesome.FA_ANCHOR;
import static com.beardedhen.androidbootstrap.font.FontAwesome.FA_ANDROID;
import static com.beardedhen.androidbootstrap.font.FontAwesome.FA_APPLE;
import static com.beardedhen.androidbootstrap.font.FontAwesome.FA_HEART;
import static com.beardedhen.androidbootstrap.font.FontAwesome.FA_TWITTER;
import static com.beardedhen.androidbootstrap.font.Typicon.TY_CODE;

public class AwesomeTextViewExample extends BaseActivity {

    @Override protected int getContentLayoutId() {
        return R.layout.example_awesome_text_view;
    }

    AwesomeTextView exampleChange;
    AwesomeTextView exampleFlash;
    AwesomeTextView exampleRotate;
    AwesomeTextView exampleMultiChange;
    AwesomeTextView exampleBuilder;
    AwesomeTextView mixAndMatch;

    private boolean android = true;
    private boolean wikipedia = true;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exampleChange = (AwesomeTextView) findViewById(R.id.example_fa_text_change);
        exampleFlash = (AwesomeTextView) findViewById(R.id.example_fa_text_flash);
        exampleRotate = (AwesomeTextView) findViewById(R.id.example_fa_text_rotate);
        exampleMultiChange = (AwesomeTextView) findViewById(R.id.example_fa_text_multi_change);
        exampleBuilder = (AwesomeTextView) findViewById(R.id.example_fa_text_builder);
        mixAndMatch = (AwesomeTextView) findViewById(R.id.example_mix_and_match);
        exampleChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android = !android;
                exampleChange.setFontAwesomeIcon(android ? FA_ANDROID : FA_APPLE);
            }
        });
        exampleMultiChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wikipedia = !wikipedia;
                String text = wikipedia ? "{fa_image} is in the {fa_cloud}" : "{fa_bank} are on {fa_globe}";
                exampleMultiChange.setMarkdownText(text);
            }
        });
        setupFontAwesomeText();
    }

    private void setupFontAwesomeText() {
        exampleFlash.startFlashing(true, AwesomeTextView.AnimationSpeed.FAST);
        exampleRotate.startRotate(true, AwesomeTextView.AnimationSpeed.SLOW);

        BootstrapText text = new BootstrapText.Builder(this)
                .addText("I ")
                .addFontAwesomeIcon(FA_HEART)
                .addText(" going on ")
                .addFontAwesomeIcon(FA_TWITTER)
                .build();

        exampleBuilder.setBootstrapText(text);

        mixAndMatch.setBootstrapText(new BootstrapText.Builder(this)
                .addFontAwesomeIcon(FA_ANCHOR)
                .addTypicon(TY_CODE)
                .build());
    }

}
