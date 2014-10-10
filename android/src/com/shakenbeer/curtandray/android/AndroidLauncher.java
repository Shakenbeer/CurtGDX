package com.shakenbeer.curtandray.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.shakenbeer.curtandray.CurtAndRay;
import com.google.android.gms.ads.*;

public class AndroidLauncher extends AndroidApplication {
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;

        RelativeLayout layout = new RelativeLayout(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        View gameView = initializeForView(new CurtAndRay(), config);

        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-4740200680513569/8222217731");
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder().build());

        layout.addView(gameView);

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        
        layout.addView(adView, adParams);
        
        setContentView(layout);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setMessage(R.string.quit)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AndroidLauncher.this.finish();
                        }

                    }).setNegativeButton(android.R.string.no, null).show();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
