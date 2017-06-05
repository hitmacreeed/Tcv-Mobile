package com.rtc.user.tcvmobile;

        import android.app.Activity;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.TranslateAnimation;
        import android.widget.TextView;

        import gr.net.maroulis.library.EasySplashScreen;

        public class Intro extends Activity {
        //NAO ESTA EM USO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(Intro.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundResource(R.drawable.back1)
                .withHeaderText("CABO VERDE")
                .withFooterText("TCV MOBILE PRO")
                //.withBeforeLogoText("64 Bit Studios")
                .withLogo(R.drawable.icon2);
               // .withAfterLogoText("Aplicaçao sem fins lucrativos");

        //set your own animations
        myCustomTextViewAnimation(config.getFooterTextView());

        //customize all TextViews
        /*Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        config.getAfterLogoTextView().setTypeface(pacificoFont);*/
        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        //create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);
    }

    private void myCustomTextViewAnimation(TextView tv){
        Animation animation=new TranslateAnimation(0,0,480,0);
        animation.setDuration(1200);
        tv.startAnimation(animation);
    }
}
