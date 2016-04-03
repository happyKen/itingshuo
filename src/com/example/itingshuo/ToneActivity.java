package com.example.itingshuo;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import volley.VolleyManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.config.Urls;
import com.dialog.ChangeDialog;
import com.dialog.ResultDialog;
import com.dialog.UpdateDialog;
import com.entity.JCourse;
import com.entity.JLogin;
import com.entity.JShowMovie;
import com.entity.JShowTone;
import com.example.itingshuo.MovieActivity.PlayAsyncTask;
import com.recorder.AudioFileFunc;
import com.recorder.AudioRecordFunc;
import com.recorder.ErrorCode;
import com.recorder.PlayAudioTrack;

import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToneActivity extends ActionBarActivity {
	// 文章控件
	private TextView tv_content;
	// 回听录音上传各个按钮
	private int mState = -1; // -1:没再录制，0：录制wav
	private final static int FLAG_WAV = 0;
	private LinearLayout huiTing_bg;
	private LinearLayout luYin_bg;
	private LinearLayout shangChuan_bg;
	private TextView huiTing_text;
	private TextView luYin_text;
	private TextView shangChuan_text;
	// intent
	private String typeid;
	private String toneid;
	public static final String TAG = "ToneActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tone);
		tv_content = (TextView) findViewById(R.id.content);
		mGetIntent();
		requestDataFromServer();
	}

	//
	// 获取intent传来的值
	public void mGetIntent() {
		Bundle bundle1 = getIntent().getExtras();
		typeid = bundle1.getString("typeid");
		toneid = bundle1.getString("toneid");
		Log.d("bundle", "typeid: " + typeid);
		Log.d("bundle", "toneid: " + toneid);

	}

	/*
	 * 向服务器请求数据
	 */
	private void requestDataFromServer() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("typeid", typeid);
		map.put("toneid", toneid);
		VolleyManager.newInstance().GsonPostRequest(TAG, map, Urls.SHOWTONE,
				JShowTone.class, new Response.Listener<JShowTone>() {
					@Override
					public void onResponse(JShowTone jtone) {
						// Log.d("111111111111111111111", "ok" +
						// jmovie.getData().getMovie().get(0).getMovie_name());
						// Log.d("111111111111111111111", "ok" +
						// jmovie.getData().getMovie().get(0).getCover_addr());
						int length = 0;
						if (jtone.getData().getStatus() != 0
								&& jtone.getData().getText() != null) {
							// tv_title.setText(jtone.getData().getText().get(0).getText_name());
							String taiciString = jtone.getData().getText()
									.get(0).getContent();
							String taiciString2 = taiciString.replaceAll("##",
									"\n");
							tv_content.setText(taiciString2);
							// Log.d("success", "ok" +
							// Urls.ROOT+jmovie.getData().getMovie().get(0).getSegment_addr());
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
}
