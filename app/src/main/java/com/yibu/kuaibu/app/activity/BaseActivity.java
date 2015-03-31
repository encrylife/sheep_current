package com.yibu.kuaibu.app.activity;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shanksYao on 3/18/15.
 */
public class BaseActivity extends Activity {

protected TextView textView(int id){
    return (TextView) findViewById(id);
}
protected void finish(int id){
    findViewById(id).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
}
    protected void invisible(int id){
        findViewById(id).setVisibility(View.INVISIBLE);
    }
}
