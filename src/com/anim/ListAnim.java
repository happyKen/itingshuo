package com.anim;

import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
/*
 * list item∑…»ÎÃÿ–ß
 * 
 */
public class ListAnim {
	   public LayoutAnimationController getListAnim() {
		   Log.v("anim", "animMessage......");
	    	AnimationSet set = new AnimationSet(true);
	    	Animation animation = new AlphaAnimation(0.0f, 1.0f);
	    	animation.setDuration(300);
	    	set.addAnimation(animation);

	    	animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
	    	Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
	    	-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
	    	animation.setDuration(500);
	    	set.addAnimation(animation);
	    	LayoutAnimationController controller = new LayoutAnimationController(
	    	set, 0.5f);
	    	return controller;
	    	}

}
