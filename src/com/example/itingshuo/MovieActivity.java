package com.example.itingshuo;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnSeekCompleteListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import io.vov.vitamio.widget.MediaController.PlayControl;
import io.vov.vitamio.widget.MediaController.onPauseListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import volley.VolleyManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.config.Urls;
import com.dialog.ChangeDialog;
import com.dialog.ResultDialog;
import com.dialog.UpdateDialog;
import com.entity.JShowMovie;
import com.example.fragment.MovieSegmentFragment;
import com.example.fragment.MovieTaiciFragment;
import com.example.fragment.MovieXuanJuFragment;
import com.movie.Utils;
import com.recorder.AudioFileFunc;
import com.recorder.AudioRecordFunc;
import com.recorder.ErrorCode;
import com.recorder.PlayAudioTrack;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MovieActivity extends FragmentActivity implements OnClickListener,
		OnCompletionListener, OnInfoListener, OnPreparedListener,
		OnErrorListener, OnBufferingUpdateListener, OnSeekCompleteListener {
	private MediaController mMediaController;
	private View mLoadingView;
	// private String mPath
	// ="http://192.168.2.1/hhboxwebdav/movie/movie1000233.mp4";
	private String mPath = "http://ocs.maiziedu.com/android_app_sde_1.mp4";
	private VideoView mVideoView;
	private int mLayout = VideoView.VIDEO_LAYOUT_STRETCH;
	private TextView mTv_NoPlay;
	private long mLastPosition;

	private View mVolumeBrightnessLayout;
	private ImageView mOperationBg;
	private ImageView mOperationPercent;
	private AudioManager mAudioManager;
	/** 最大声音 */
	private int mMaxVolume;
	/** 当前声音 */
	private int mVolume = -1;
	/** 当前亮度 */
	private float mBrightness = -1f;
	/** 当前缩放模式 */
	private GestureDetector mGestureDetector;
	private float mFast_forward;
	private View mFl_Progress;
	private TextView mTv_progress;
	private ImageView mIv_Progress_bg;
	private boolean isFast_Forword;
	private boolean isUp_downScroll;
	// 台词和选句切换
	private ArrayList<Fragment> fragments;
	private ViewPager viewPager;
	private LinearLayout tab_taiCi_bg;
	private LinearLayout tab_xuanJu_bg;
	private TextView tab_taiCi_text;
	private TextView tab_xuanJu_text;
	// 获取到的intent字段
	private String emotionid;
	private String movieid;
	PlayAsyncTask playAsyncTask;
	// 两个fragment需要获取的信息
	String taici = "台词";
	private List<JShowMovie.DataEntity.MovieEntity> movieEntity;
	public static final String TAG = "MovieActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object... params) {

				Vitamio.initialize(getApplicationContext());
				if (Vitamio.isInitialized(getApplicationContext()))
					return null;

				// 反射解压
				try {
					Class c = Class.forName("io.vov.vitamio.Vitamio");
					Method extractLibs = c.getDeclaredMethod("extractLibs",
							new Class[] { android.content.Context.class,
									int.class });
					extractLibs.setAccessible(true);
					extractLibs.invoke(c, new Object[] {
							getApplicationContext(), R.raw.libarm });

					// Field vitamioLibraryPath =
					// c.getDeclaredField("vitamioLibraryPath");
					//
					// AndroidContextUtils.getDataDir(ctx) + "libs/"

				} catch (NoSuchMethodException e) {
					Log.e("extractLibs", e.toString());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}
		}.execute();
		setContentView(R.layout.activity_movie);
		initTaiCi();// 台词切换控件初始化
		changeState(0); // 初始化动画
		movieEntity = new ArrayList<JShowMovie.DataEntity.MovieEntity>();
		mGetIntent();
		requestDataFromServer();

	}

	private RelativeLayout mRl_PlayView;

	// 台词切换控件初始化
	private void initTaiCi() {
		tab_taiCi_bg = (LinearLayout) findViewById(R.id.taici_bg);
		tab_taiCi_text = (TextView) findViewById(R.id.taici_text);
		tab_xuanJu_bg = (LinearLayout) findViewById(R.id.xuanju_bg);
		tab_xuanJu_text = (TextView) findViewById(R.id.xuanju_text);
	}

	// 视频控件初始化
	private void initVideoView() {
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		mLoadingView = findViewById(R.id.video_loading);
		mTv_NoPlay = (TextView) findViewById(R.id.tv_noPlay);
		mRl_PlayView = (RelativeLayout) findViewById(R.id.rl_playView);

		mVolumeBrightnessLayout = findViewById(R.id.operation_volume_brightness);
		mOperationBg = (ImageView) findViewById(R.id.operation_bg);
		mOperationPercent = (ImageView) findViewById(R.id.operation_percent);
		mTv_progress = (TextView) findViewById(R.id.tv_progress);
		mFl_Progress = (FrameLayout) findViewById(R.id.fl_set_progress);
		mIv_Progress_bg = (ImageView) findViewById(R.id.iv_progress_bg);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mMaxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnInfoListener(this);
		mVideoView.setOnPreparedListener(this);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnBufferingUpdateListener(this);
		mVideoView.setOnSeekCompleteListener(this);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		int mCurrentOrientation = getResources().getConfiguration().orientation;
		if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
			Utils.full(false, MovieActivity.this);
			mRl_PlayView.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 400));
			if (mVideoView != null)
				mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
			mMediaController = new MediaController(this, mVideoView);
			mMediaController.setOnPauseListener(mPauseListener);
			mVideoView.setMediaController(mMediaController);
		} else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
			Utils.full(true, MovieActivity.this);
			mRl_PlayView.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			if (mVideoView != null)
				mVideoView.setVideoLayout(mLayout, 0);
			mMediaController = new MediaController(this, mVideoView);
			mMediaController.setOnPauseListener(mPauseListener);
			mVideoView.setMediaController(mMediaController);
		}

		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event))
			return true;

		// 处理手势结束
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:
			endGesture();
			break;
		}

		return super.onTouchEvent(event);
	}

	/** 手势结束 */
	private void endGesture() {
		mVolume = -1;
		mBrightness = -1f;
		if (isFast_Forword) {
			onSeekProgress(mFast_forward);
		}
		// 隐藏
		mDismissHandler.removeMessages(0);
		mDismissHandler.sendEmptyMessageDelayed(0, 800);
	}

	class PlayAsyncTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// PLAY
			initVideoView();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (mPath.startsWith("http:"))
				mVideoView.setVideoURI(Uri.parse(mPath));
			else
				mVideoView.setVideoPath(mPath);
			// 设置显示名称
			mMediaController = new MediaController(MovieActivity.this,
					mVideoView);
			mMediaController.setmPlayControl(mPlayControll);
			mMediaController.setOnPauseListener(mPauseListener);
			mVideoView.setMediaController(mMediaController);
			mMediaController.setFileName("哈哈哈");

			int mCurrentOrientation = getResources().getConfiguration().orientation;
			if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
				Utils.full(false, MovieActivity.this);
				mRl_PlayView.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, 600));
				if (mVideoView != null) {
					// mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH,
					// 0);
				}
			} else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
				Utils.full(true, MovieActivity.this);
				mRl_PlayView.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				if (mVideoView != null)
					mVideoView.setVideoLayout(mLayout, 0);
			}
			mVideoView.requestFocus();
			mGestureDetector = new GestureDetector(new MyGestureListener());
		}

	}

	private class MyGestureListener extends SimpleOnGestureListener {

		/** 双击 */
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mLayout == VideoView.VIDEO_LAYOUT_ZOOM)
				mLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
			else
				mLayout++;
			if (mVideoView != null)
				mVideoView.setVideoLayout(mLayout, 0);
			return true;
		}

		/** 滑动 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			mMediaController.hide();
			float mOldX = e1.getX(), mOldY = e1.getY();
			int y = (int) e2.getRawY();
			int x = (int) e2.getRawX();
			Display disp = getWindowManager().getDefaultDisplay();
			int windowWidth = disp.getWidth();
			int windowHeight = disp.getHeight();

			if (Math.abs(x - mOldX) > 20 && !isUp_downScroll) { // 执行快进快退
				isFast_Forword = true;
				mFast_forward = x - mOldX;
				fast_ForWord(mFast_forward);
			} else if (mOldX > windowWidth * 1.0 / 2 && Math.abs(mOldY - y) > 3
					&& !isFast_Forword)// 右边滑动
				onVolumeSlide((mOldY - y) / windowHeight);
			else if (mOldX < windowWidth / 2.0 && Math.abs(mOldY - y) > 3
					&& !isFast_Forword)// 左边滑动
				onBrightnessSlide((mOldY - y) / windowHeight);
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}

	/** 定时隐藏 */
	private Handler mDismissHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			isFast_Forword = false;
			isUp_downScroll = false;
			mVolumeBrightnessLayout.setVisibility(View.GONE);
			mFl_Progress.setVisibility(View.GONE);
		}
	};

	private void onSeekProgress(float dis) {
		Log.e("position ==", mVideoView.getCurrentPosition() + 500 * (long) dis
				+ "/" + mVideoView.getDuration());
		mVideoView.seekTo(mVideoView.getCurrentPosition() + 500 * (long) dis);
	}

	private void fast_ForWord(float dis) {
		long currentProgress;
		long duration = mVideoView.getDuration();
		if (mVideoView.getCurrentPosition() + 500 * (long) dis < 0)
			currentProgress = 0;
		else
			currentProgress = mVideoView.getCurrentPosition() + 500
					* (long) dis;
		mTv_progress.setText(Utils.generateTime(currentProgress) + "/"
				+ Utils.generateTime(duration));
		if (dis > 0)
			mIv_Progress_bg.setImageResource(R.drawable.btn_fast_forword);
		else
			mIv_Progress_bg.setImageResource(R.drawable.btn_back_forword);
		mFl_Progress.setVisibility(View.VISIBLE);
	}

	/**
	 * 滑动改变声音大小
	 * 
	 * @param percent
	 */
	private void onVolumeSlide(float percent) {
		isUp_downScroll = true;
		if (mVolume == -1) {
			mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			if (mVolume < 0)
				mVolume = 0;

			// 显示
			mOperationBg.setImageResource(R.drawable.video_volumn_bg);
			mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
		}

		int index = (int) (percent * mMaxVolume) + mVolume;
		if (index > mMaxVolume)
			index = mMaxVolume;
		else if (index < 0)
			index = 0;

		// 变更声音
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

		// 变更进度条
		ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
		lp.width = findViewById(R.id.operation_full).getLayoutParams().width
				* index / mMaxVolume;
		mOperationPercent.setLayoutParams(lp);
	}

	/**
	 * 滑动改变亮度
	 * 
	 * @param percent
	 */
	private void onBrightnessSlide(float percent) {
		isUp_downScroll = true;
		if (mBrightness < 0) {
			mBrightness = getWindow().getAttributes().screenBrightness;
			if (mBrightness <= 0.00f)
				mBrightness = 0.50f;
			if (mBrightness < 0.01f)
				mBrightness = 0.01f;

			// 显示
			mOperationBg.setImageResource(R.drawable.video_brightness_bg);
			mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
		}
		WindowManager.LayoutParams lpa = getWindow().getAttributes();
		lpa.screenBrightness = mBrightness + percent;
		if (lpa.screenBrightness > 1.0f)
			lpa.screenBrightness = 1.0f;
		else if (lpa.screenBrightness < 0.01f)
			lpa.screenBrightness = 0.01f;
		getWindow().setAttributes(lpa);

		ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
		lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
		mOperationPercent.setLayoutParams(lp);
	}

	private void stopPlayer() {
		if (mVideoView != null)
			mVideoView.pause();
	}

	private void startPlayer() {
		if (mVideoView != null)
			mVideoView.start();
	}

	private boolean isPlaying() {
		return mVideoView != null && mVideoView.isPlaying();
	}

	/** 是否�?��自动恢复播放，用于自动暂停，恢复播放 */
	private boolean needResume;

	@Override
	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		switch (arg1) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			// �?��缓存，暂停播�?
			if (isPlaying()) {
				stopPlayer();
				needResume = true;
			}
			mLoadingView.setVisibility(View.VISIBLE);
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			// 缓存完成，继续播�?
			if (needResume) {
				startPlayer();
			}
			mLoadingView.setVisibility(View.GONE);
			break;
		case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			// 显示 下载速度
			break;
		}
		return true;
	}

	/**
	 * 播放完成
	 */
	@Override
	public void onCompletion(MediaPlayer arg0) {
	}

	/**
	 * //在视频预处理完成后调用。在视频预处理完成后被调用。此时视频的宽度、高度、宽高比信息已经获取到，此时可调用seekTo让视频从指定位置开始播放。
	 */
	@Override
	public void onPrepared(MediaPlayer arg0) {
	}

	/**
	 * 在异步操作调用过程中发生错误时调用。例如视频打开失败。
	 */
	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		mLoadingView.setVisibility(View.GONE);
		mTv_NoPlay.setVisibility(View.VISIBLE);
		return false;
	}

	/**
	 * 在网络视频流缓冲变化时调用。
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
		mTv_NoPlay.setVisibility(View.GONE);
		mLoadingView.setVisibility(View.VISIBLE);
	}

	/**
	 * 在seek操作完成后调用。
	 */
	@Override
	public void onSeekComplete(MediaPlayer arg0) {
	}

	private MediaController.PlayControl mPlayControll = new PlayControl() {

		@Override
		public void downLoad() {

		}

		@Override
		public void collect() {
		}

	};

	private onPauseListener mPauseListener = new onPauseListener() {

		@Override
		public void onPause() {
			Log.d("pause", "pause");
		}

		@Override
		public void onPlay() {
			Log.e("onPlay", "play");
		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	public void fragmentInit() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new MovieTaiciFragment());// 台词
		fragments.add(new MovieSegmentFragment());// 选句
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
			tab_taiCi_bg.setBackground(getResources().getDrawable(
					R.drawable.select_blue_bg));
			tab_xuanJu_bg.setBackground(getResources().getDrawable(
					R.drawable.select_white_bg));
			tab_taiCi_text.setTextColor(getResources().getColor(
					R.color.selected));
			tab_xuanJu_text.setTextColor(getResources().getColor(
					R.color.no_selected));
		} else if (arg0 == 1) {
			tab_xuanJu_bg.setBackground(getResources().getDrawable(
					R.drawable.select_blue_bg));
			tab_taiCi_bg.setBackground(getResources().getDrawable(
					R.drawable.select_white_bg));
			tab_xuanJu_text.setTextColor(getResources().getColor(
					R.color.selected));
			tab_taiCi_text.setTextColor(getResources().getColor(
					R.color.no_selected));

		}
	}

	// 设置切换片段弹框
	public void showAlertDialog() {

		ChangeDialog.Builder builder = new ChangeDialog.Builder(this);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}

	// 设置上传弹框
	public void showUpdateDialog() {

		UpdateDialog.Builder builder = new UpdateDialog.Builder(this);
		builder.create().show();

	}

	// 设置结果弹框
	public void showResultDialog() {

		ResultDialog.Builder builder = new ResultDialog.Builder(this);
		builder.create().show();

	}

	// 与服务器连接获得结果
	public void getResult() {
		UpdateDialog.Builder builder = new UpdateDialog.Builder(this);
		final Dialog updateDialog = builder.create();
		final ResultDialog.Builder builder2 = new ResultDialog.Builder(this);
		updateDialog.show();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				// execute the task
				updateDialog.dismiss();
				Dialog resultDialog = builder2.create();
				resultDialog.show();
			}
		}, 3000);

	}

	// 获取intent传来的值
	public void mGetIntent() {
		Bundle bundle1 = getIntent().getExtras();
		emotionid = bundle1.getString("emotionid");
		movieid = bundle1.getString("movieid");
		Log.d("bundle", "emotionid: " + emotionid);
		Log.d("bundle", "movieid: " + movieid);
		if (bundle1.containsKey("taici")) {
			setTaici(bundle1.getString("taici"));
		}
		if (bundle1.containsKey("movieSrc")) {
			mPath = bundle1.getString("movieSrc");
		}

	}

	/*
	 * 模拟向服务器请求数据
	 */
	private void requestDataFromServer() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("emotionid", emotionid);
		map.put("movieid", movieid);
		VolleyManager.newInstance().GsonPostRequest(TAG, map,
				Urls.SHOWMOVIE_URL, JShowMovie.class,
				new Response.Listener<JShowMovie>() {
					@Override
					public void onResponse(JShowMovie jmovie) {
						// Log.d("111111111111111111111", "ok" +
						// jmovie.getData().getMovie().get(0).getMovie_name());
						// Log.d("111111111111111111111", "ok" +
						// jmovie.getData().getMovie().get(0).getCover_addr());
						int length = 0;
						if (jmovie.getData().getStatus() != 0
								&& jmovie.getData().getMovie() != null) {
							if (taici.equals("台词"))
								setTaici(jmovie.getData().getMovie().get(0)
										.getContent());
							if (mPath
									.equals("http://ocs.maiziedu.com/android_app_sde_1.mp4"))
								mPath = Urls.ROOT
										+ jmovie.getData().getMovie().get(0)
												.getSegment_addr();

							setmovieEntity(jmovie.getData().getMovie());
							Log.d("success", "ok"
									+ Urls.ROOT
									+ jmovie.getData().getMovie().get(0)
											.getSegment_addr());
						}
						playAsyncTask = new PlayAsyncTask();
						playAsyncTask.execute("");
						fragmentInit();

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("fail", "connect fail");

					}
				});
		Log.d(TAG, "finish");
	}

	// fragment获取activity信息
	public void setTaici(String taici) {
		this.taici = taici;
	}

	public void setmovieEntity(
			List<JShowMovie.DataEntity.MovieEntity> movieEntity) {
		this.movieEntity = movieEntity;
	}

	public String getTaici() {
		return taici;
	}

	public List<JShowMovie.DataEntity.MovieEntity> getmovieEntity() {
		return movieEntity;
	}

	// 更新activity
	public void refresh(String movieid, String emotionid, String movieSrc,
			String taici) {
		finish();
		Intent intent = new Intent(MovieActivity.this, MovieActivity.class);
		// //new一个Bundle对象，并将要传递的数据传入
		Bundle bundle = new Bundle();
		bundle.putString("movieid", movieid);
		bundle.putString("emotionid", emotionid);
		bundle.putString("movieSrc", movieSrc);
		bundle.putString("taici", taici);

		// 将bundle对象assign给Intent
		intent.putExtras(bundle);
		// //开启跳转
		Log.d("sendbundle", movieid + emotionid + movieSrc + taici);
		startActivity(intent);
	}

}
