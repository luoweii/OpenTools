package com.luowei.opentools.module.materialripple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.luowei.opentools.BaseActivity;
import com.luowei.opentools.Constant;
import com.luowei.opentools.R;
import com.luowei.opentools.entity.Tool;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by 骆巍 on 2016/4/25.
 */
public class MaterialRippleActivity extends BaseActivity {
    @Bind(R.id.ripple_layout_2)
    Button btnRipple;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ripple_activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tool t = (Tool) getIntent().getSerializableExtra(Constant.TOOL);
        setTitle(t.name);

        MaterialRippleLayout.on(btnRipple)
                .rippleColor(Color.parseColor("#FF0000"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();
    }

    @OnClick(R.id.ripple_layout_2)
    public void onClickRipple(View v) {
        Toast.makeText(this, "Short click", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.ripple_layout_2)
    public boolean onLongClickRipple(View v) {
        Toast.makeText(this, "Long click and consumed", Toast.LENGTH_SHORT).show();
        return true;
    }

    @OnLongClick(R.id.ripple_layout_1)
    public boolean onLongClickRipple1(View v) {
        Toast.makeText(this, "Long click not consumed", Toast.LENGTH_SHORT).show();
        return false;
    }

    @OnClick(R.id.ripple_layout_3)
    public void onClickRipple3(View v) {
        getSupportFragmentManager().beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
//                .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right, android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.flContainer, RippleListViewFragment.getInstance())
                .addToBackStack(null).commit();
    }

    @OnClick(R.id.ripple_layout_4)
    public void onClickRipple4(View v) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                .replace(R.id.flContainer, RippleRecyclerFragment.getInstance())
                .addToBackStack(null).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
