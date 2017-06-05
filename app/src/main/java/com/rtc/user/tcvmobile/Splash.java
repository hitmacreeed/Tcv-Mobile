package com.rtc.user.tcvmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import me.wangyuwei.particleview.ParticleView;

public class Splash extends Activity {
    ParticleView mPvGithub;

    @Override

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPvGithub = (ParticleView) findViewById(R.id.pv_tcv);

        //verifica se é primeira vez que entra na aplicação
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isfirstrun", true);
        if (isFirstRun) {
            // se for a primeira vez vai para a pagina de explicacao
            Intent intent = new Intent(Splash.this, ExplanationActivity.class);
            startActivity(intent);
            //Toast.makeText(this, "FirstTime = YES", Toast.LENGTH_LONG).show();
        } else {

            mPvGithub.startAnim();
            mPvGithub.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {

                @Override
                public void onAnimationEnd() {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(intent);
                    finish();
                }
            });
        }

    }
}
