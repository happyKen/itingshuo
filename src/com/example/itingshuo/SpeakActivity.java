package com.example.itingshuo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import volley.VolleyManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.config.Urls;
import com.dialog.ResultDialog;
import com.dialog.UpdateDialog;
import com.entity.JSentenceList;
import com.entity.JShowMovie;
import com.entity.JShowSentence;
import com.example.itingshuo.SpeakActivity.UIHandler;
import com.player.Player;
import com.recorder.AudioFileFunc;
import com.recorder.AudioRecordFunc;
import com.recorder.ErrorCode;
import com.recorder.PlayAudioTrack;
import com.speak.JuziList;

import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SpeakActivity extends ActionBarActivity {
	// 音频播放控件
	private String playpath = "http://abv.cn/music/光辉岁月.mp3";// 音频地址
	private Button playBtn;
	private Player player;
	private SeekBar musicProgress;
	private int playing = -1;
	private UIHandler uiHandler;
	private Timer mTimer; // 计时器
	private TimerTask timerTask;
	private TextView tv_currentTime;
	private TextView tv_allTime;
	// 服务器与intent
	public static final String TAG = "SpeakActivity";
	private String username;
	private String password;
	private String courseid;
	private String sentenceid;
	private TextView contentTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_speak);
		getmIntent();
		init();
		requestDataFromServer();
		setListener();
		uiHandler = new UIHandler();
		// 计时器
		timerTask = new TimerTask() {

			@Override
			public void run() {
				if (playing != -1) {
					Message msg = new Message();
					Bundle b = new Bundle();// 存放数据
					b.putInt("cmd", CMD_PLAYER_TIME);
					b.putString("currentTime", player.getCurrentTime());
					b.putString("allTime", player.getAllTime());
					msg.setData(b);
					uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
				}
			}
		};
		mTimer = new Timer(); // 计时器
		mTimer.schedule(timerTask, 0, 100);

	}

	public void init() {
		playBtn = (Button) findViewById(R.id.btn_online_play);
		musicProgress = (SeekBar) findViewById(R.id.music_progress);
		tv_allTime = (TextView) findViewById(R.id.all_time);
		tv_currentTime = (TextView) findViewById(R.id.current_time);
		player = new Player(musicProgress);
		musicProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		contentTextView = (TextView) findViewById(R.id.content);
	}

	public void setListener() {

		playBtn.setOnClickListener(new PlayListener());
	}

	// 播放监听器
	class PlayListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 播放录音
			// Toast.makeText(MovieActivity.this,"huiting",
			// Toast.LENGTH_SHORT).show();
			if (playing == -1) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						Message msg = new Message();
						Bundle b = new Bundle();// 存放数据
						b.putInt("cmd", CMD_PLAYER_PLAY);
						msg.setData(b);
						uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
						player.playUrl(playpath);
						playing = 1;
					}
				}).start();
			} else if (playing == 1) {
				player.pause();
				Message msg = new Message();
				Bundle b = new Bundle();// 存放数据
				b.putInt("cmd", CMD_PLAYER_STOP);
				msg.setData(b);
				uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
				playing = 0;
			} else {
				player.play();
				Message msg = new Message();
				Bundle b = new Bundle();// 存放数据
				b.putInt("cmd", CMD_PLAYER_PLAY);
				msg.setData(b);
				uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
				playing = 1;
			}
		}

	}

	class SeekBarChangeEvent implements OnSeekBarChangeListener {
		int progress;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
			this.progress = progress * player.mediaPlayer.getDuration()
					/ seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
			player.mediaPlayer.seekTo(progress);
		}

	}

	private final static int CMD_PLAYER_PLAY = 2004;
	private final static int CMD_PLAYER_STOP = 2005;
	private final static int CMD_PLAYER_TIME = 2006;
	private final static int CMD_SERVER = 2008;

	class UIHandler extends Handler {
		public UIHandler() {
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// Log.d("MyHandler", "handleMessage......");
			super.handleMessage(msg);
			Bundle b = msg.getData();
			int vCmd = b.getInt("cmd");
			switch (vCmd) {
			case CMD_PLAYER_PLAY:
				SpeakActivity.this.playBtn
						.setBackgroundResource(R.drawable.stop);
				break;
			case CMD_PLAYER_STOP:
				SpeakActivity.this.playBtn
						.setBackgroundResource(R.drawable.play);
				break;
			case CMD_PLAYER_TIME:
				String currentTimeString = b.getString("currentTime");
				String allTime = b.getString("allTime");
				tv_allTime.setText(allTime);
				tv_currentTime.setText(currentTimeString);
				break;
			case CMD_SERVER:
				// 接收服务器信息
				String content = b.getString("content");
				String sentenceSrc = b.getString("sentenceSrc");
				contentTextView.setText(content);
				playpath = sentenceSrc;
				break;
			default:
				break;
			}
		}
	};

	// get intent
	public void getmIntent() {
		Bundle bundle1 = getIntent().getExtras();
		courseid = bundle1.getString("courseid");
		sentenceid = bundle1.getString("sentenceid");
		Log.d("bundle", "courseid: " + courseid);
		Log.d("bundle", "sentenceid: " + sentenceid);
	}

	/*
	 * 向服务器请求数据
	 */
	private void requestDataFromServer() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("courseid", courseid);
		map.put("sentenceid", sentenceid);
		VolleyManager.newInstance().GsonPostRequest(TAG, map,
				Urls.SHOWSENTENCE, JShowSentence.class,
				new Response.Listener<JShowSentence>() {

					@Override
					public void onResponse(JShowSentence sentence) {
						int length = 0;
						if (sentence.getData().getStatus() != 0
								&& sentence.getData().getSentence() != null) {
							Message msg = new Message();
							Bundle b = new Bundle();// 存放数据
							b.putInt("cmd", CMD_SERVER);
							b.putString("content", sentence.getData()
									.getSentence().get(0).getContent());
							b.putString("sentenceSrc", Urls.ROOT
									+ sentence.getData().getSentence().get(0)
											.getSen_addr());
							msg.setData(b);
							uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI

							Log.d("success", "ok"
									+ sentence.getData().getSentence().get(0)
											.getContent());
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("fail", "connect fail");

					}
				});
		Log.d(TAG, "finish");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (player != null) {
			player.stop();
		}
		player = null;
		mTimer.cancel();
	}
}
