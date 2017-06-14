package com.term.paimai;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ViewFlipper;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GuideActivity extends FragmentActivity {

    private ViewFlipper viewFlipper = null;
    private boolean is_first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewFlipper = (ViewFlipper)this.findViewById(R.id.viewflipper);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewFlipper.showNext();
                startMainActivity();
            }
        }, 2000);
    }

    public void startMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                GuideActivity.this.startActivity(intent);
                GuideActivity.this.finish();
            }
        }, 1500);
    }
}
