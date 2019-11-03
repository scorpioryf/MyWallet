package com.testdemo.holyg.mywallet;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;


public class AnimatorUtil {
    private static AccelerateDecelerateInterpolator LINEAR_INTERRPLATOR =new AccelerateDecelerateInterpolator();
    public static void showFab(View view, ScrollAwareFABBehavior.AnimateListener listener[]){
        if (listener.length!=0){
            view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(600)
                    .setInterpolator(LINEAR_INTERRPLATOR)
                    .setListener(listener[0])
                    .start();
            view.setClickable(true);
        }else {
            view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(600)
                    .setInterpolator(LINEAR_INTERRPLATOR)
                    .start();
            view.setClickable(true);
        }

    }
    public static void hideFab(View view,ScrollAwareFABBehavior.AnimateListener listener){
        view.animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(600)
                .setInterpolator(LINEAR_INTERRPLATOR)
                .setListener(listener)
                .start();
        view.setClickable(false);
    }

}
