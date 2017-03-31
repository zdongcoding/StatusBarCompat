package com.github.statusbarcompat.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView  imageView= (ImageView) findViewById(R.id.img);
        Glide.with(this).load("http://s1.xinstatic.com/xin/images/brand-100/b_294.png")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.e("zoudong", "onResourceReady====" + "resource = [" + resource + "], model = [" + model + "], target = [" + target + "], isFromMemoryCache = [" + isFromMemoryCache + "], isFirstResource = [" + isFirstResource + "]");
                        return false;
                    }
                })
                .into(imageView);
    }

    public void setStatusBarColor(View view) {
        startActivity(new Intent(this,StatusBarColorActivity.class));
    }

    public void translucentStatusBar(View view) {
        startActivity(new Intent(this,TranslucentStatusBarActivity.class));

    }

    public void setStatusBarColorForCollapsingToolbar(View view) {
        startActivity(new Intent(this,CollapsingToolbarrActivity.class));

    }
}
