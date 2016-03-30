package com.example.itingshuo;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.MovieEmotionFragment;
import com.example.fragment.SpeakClassFragment;
import com.example.fragment.ToneListFragment;
import com.example.fragment.ToneTypeFragment;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class MainActivity extends FragmentActivity {

	private ArrayList<Fragment> fragments;

	private ViewPager viewPager;

	private ImageView tab_speak;

	private ImageView tab_movie;
	
	private ImageView tab_tone;

	private int line_width;

	private View line;
	
	private TextView bar_title;
	//各个fragment
	private MovieEmotionFragment movieEmotionFragment;
	//private MovieListFragment movieListFragment;
	private SpeakClassFragment speakClassFragment;
//	private ToneListFragment toneListFragment;
	private ToneTypeFragment toneTypeFragment;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		myGetIntent();
		tab_speak = (ImageView) findViewById(R.id.tab_speak);
		tab_movie = (ImageView) findViewById(R.id.tab_movie);
		tab_tone = (ImageView) findViewById(R.id.tab_tone);
		bar_title= (TextView) findViewById(R.id.tv_bar_title);
		line = findViewById(R.id.line);
		
		changeState(0);
		// 初始化TextView动画
//		ViewPropertyAnimator.animate(tab_movie).scaleX(1.2f).setDuration(0);
//		ViewPropertyAnimator.animate(tab_movie).scaleY(1.2f).setDuration(0);
       movieEmotionFragment = new MovieEmotionFragment();
       speakClassFragment = new SpeakClassFragment();
     //  toneListFragment = new ToneListFragment();
       toneTypeFragment = new ToneTypeFragment();
		fragments = new ArrayList<Fragment>();
		fragments.add(movieEmotionFragment);//电影情感选择，不是列表
		
		fragments.add(speakClassFragment);//语调选择
		fragments.add(toneTypeFragment);//文章类型选择
		//fragments.add(new MovieListFragment());
		//movieEmotionIntent();
		line_width = getWindowManager().getDefaultDisplay().getWidth()
				/ fragments.size();
		line.getLayoutParams().width = line_width;
		line.requestLayout();
		
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
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				float tagerX = arg0 * line_width + arg2 / fragments.size();
				ViewPropertyAnimator.animate(line).translationX(tagerX)
						.setDuration(0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		tab_speak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
				
			}
		});
		tab_tone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(2);
				
			}
		});

		tab_movie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});
	}

	/* 根据传入的值来改变状态 */
	private void changeState(int arg0) {
		if (arg0 == 0) {
			tab_movie.setImageDrawable(getResources().getDrawable(R.drawable.tab_movie_click));
			tab_speak.setImageDrawable(getResources().getDrawable(R.drawable.tab_speak));
			tab_tone.setImageDrawable(getResources().getDrawable(R.drawable.tab_tone));
			ViewPropertyAnimator.animate(tab_movie).scaleX(1.2f).setDuration(200);
			ViewPropertyAnimator.animate(tab_movie).scaleY(1.2f).setDuration(200);
			ViewPropertyAnimator.animate(tab_speak).scaleX(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_speak).scaleY(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_tone).scaleX(1.0f).setDuration(200);
	        ViewPropertyAnimator.animate(tab_tone).scaleY(1.0f).setDuration(200);
	        bar_title.setText("选择电影情感");

		} else if(arg0 == 1){
			tab_speak.setImageDrawable(getResources().getDrawable(R.drawable.tab_speak_click));
			tab_movie.setImageDrawable(getResources().getDrawable(R.drawable.tab_movie));
			tab_tone.setImageDrawable(getResources().getDrawable(R.drawable.tab_tone));
			ViewPropertyAnimator.animate(tab_tone).scaleX(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_tone).scaleY(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_movie).scaleX(1.0f).setDuration(200);
	        ViewPropertyAnimator.animate(tab_movie).scaleY(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_speak).scaleX(1.2f).setDuration(200);
			ViewPropertyAnimator.animate(tab_speak).scaleY(1.2f).setDuration(200);
			 bar_title.setText("选择课程");
		}else{
			tab_tone.setImageResource(R.drawable.tab_tone_click);
			tab_movie.setImageResource(R.drawable.tab_movie);
			tab_speak.setImageResource(R.drawable.tab_speak);
			ViewPropertyAnimator.animate(tab_movie).scaleX(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_movie).scaleY(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_speak).scaleX(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_speak).scaleY(1.0f).setDuration(200);
			ViewPropertyAnimator.animate(tab_tone).scaleX(1.2f).setDuration(200);
			ViewPropertyAnimator.animate(tab_tone).scaleY(1.2f).setDuration(200);
			bar_title.setText("选择练习篇章");
		}
	}	
	public void myGetIntent(){
		// 获取上一个activity传来的值
				Bundle bundle1 = getIntent().getExtras();
				int username=bundle1.getInt("username");
				String password=bundle1.getString("password");
	}
	//movieEmotionFragment跳转
//	private void movieEmotionIntent(){
//		movieEmotionFragment.setOnHappinessClick(new OnHappinessClick(){
//
//			@Override
//			public void onClick(View view) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MainActivity.this, "ssss", Toast.LENGTH_LONG).show();
////				FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
////				MovieListFragment movieListFragment = new MovieListFragment();
//				//				Bundle bundle = new Bundle();  
////				bundle.putString("key", "这是方法二");  
////				demoFragment.setArguments(bundle); 
////                transaction.replace(transaction.("android:switcher:" + R.id.viewPager + ":" + 0), movieListFragment);
////                transaction.commit();  
//				viewPager.setCurrentItem(3);
//			}
//			
//		});
//	}
}
