package com.github.statusbarcompat.demo;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.statusbarcompat.StatusBarCompat;


public class CollapsingToolbarrActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbarr);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        imageView = (ImageView) findViewById(R.id.img);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collaps);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Glide.with(this).load("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1490950109&di=01995783e46b189d26021925a03063fe&src=http://s3.lvjs.com.cn/trip/original/20140818131550_1792868513.jpg")
                .into(imageView);
    }

    public void setStatusBar(View view) {
        StatusBarCompat.TranslucentStatusBarForCollapsingToolbar(this, appBarLayout, collapsingToolbarLayout, toolbar,Color.RED);
        StatusBarCompat.setStatusBarLightMode(this,true);
    }

    public void clearStatusBar(View view) {
        StatusBarCompat.clearStatusBarForCollapsingToolbar(this, Color.BLACK);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
