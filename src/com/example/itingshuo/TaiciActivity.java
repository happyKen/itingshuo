package com.example.itingshuo;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.MovieEmotionFragment;
import com.example.fragment.MovieListFragment;
import com.example.fragment.MovieTaiciFragment;
import com.example.fragment.MovieXuanJuFragment;
import com.example.fragment.SpeakClassFragment;
import com.example.fragment.ToneListFragment;
import com.example.itingshuo.R;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class TaiciActivity extends FragmentActivity {

	private ArrayList<Fragment> fragments;
	private ViewPager viewPager;
	private LinearLayout tab_taiCi_bg;
	private LinearLayout tab_xuanJu_bg;
	private TextView tab_taiCi_text;
	private TextView tab_xuanJu_text;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.movie_viewpager);

		tab_taiCi_bg =  (LinearLayout) findViewById(R.id.taici_bg);
		tab_taiCi_text = (TextView) findViewById(R.id.taici_text);	
		tab_xuanJu_bg =  (LinearLayout) findViewById(R.id.xuanju_bg);
		tab_xuanJu_text = (TextView) findViewById(R.id.xuanju_text);	
		changeState(0);
		// 初始化TextView动画
//		ViewPropertyAnimator.animate(tab_movie).scaleX(1.2f).setDuration(0);
//		ViewPropertyAnimator.animate(tab_movie).scaleY(1.2f).setDuration(0);
       
		fragments = new ArrayList<Fragment>();
		fragments.add(new MovieTaiciFragment());//台词
		fragments.add(new MovieXuanJuFragment());//选句	
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setAnimationCacheEnabled(true);
		viewPager.setAdapter(new FragmentStatePagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}
			@Override  
			public int getItemPosition(Object object) {  
			    return POSITION_NONE;  
			}  
			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				changeState(arg0);
			}


			@Override
			public void onPageScrollStateChanged(int arg0) {

			}


			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
		});

		tab_taiCi_bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
				
			}
		});

		tab_xuanJu_bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
			}
		});
	}

	/* 根据传入的值来改变状态 */
	@SuppressLint("NewApi")
	private void changeState(int arg0) {
		if (arg0 == 0) {
			tab_taiCi_bg.setBackground(getResources().getDrawable(R.drawable.select_blue_bg));
			tab_xuanJu_bg.setBackground(getResources().getDrawable(R.drawable.select_white_bg));
			tab_taiCi_text.setTextColor(getResources().getColor(R.color.selected));
			tab_xuanJu_text.setTextColor(getResources().getColor(R.color.no_selected));
		} else if(arg0 == 1){
			tab_xuanJu_bg.setBackground(getResources().getDrawable(R.drawable.select_blue_bg));
			tab_taiCi_bg.setBackground(getResources().getDrawable(R.drawable.select_white_bg));
			tab_xuanJu_text.setTextColor(getResources().getColor(R.color.selected));
			tab_taiCi_text.setTextColor(getResources().getColor(R.color.no_selected));
			
		}
	}

	
}
