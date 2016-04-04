package com.example.fragment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.config.Urls;
import com.dialog.ResultDialog;
import com.dialog.UpdateDialog;
import com.entity.JLogin;
import com.example.itingshuo.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.recorder.AudioFileFunc;
import com.recorder.AudioRecordFunc;
import com.recorder.ErrorCode;
import com.recorder.PlayAudioTrack;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecorderFragment extends Fragment {
	// 回听录音上传各个按钮
	private int mState = -1; // -1:没再录制，0：录制wav
	private final static int FLAG_WAV = 0;
	private LinearLayout huiTing_bg;
	private LinearLayout luYin_bg;
	private LinearLayout shangChuan_bg;
	private TextView huiTing_text;
	private TextView luYin_text;
	private TextView shangChuan_text;
	private UIHandler uiHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater
				.inflate(R.layout.recorder_button, container, false);
		uiHandler = new UIHandler();
		initRecorder(view);
		setRecorderListener();
		return view;
	}

	// 回听录音上传控件初始化
	private void initRecorder(View view) {
		huiTing_bg = (LinearLayout) view.findViewById(R.id.huiti_bg);
		huiTing_text = (TextView) view.findViewById(R.id.huiti_text);
		luYin_bg = (LinearLayout) view.findViewById(R.id.luyin_bg);
		luYin_text = (TextView) view.findViewById(R.id.luyin_text);
		shangChuan_bg = (LinearLayout) view.findViewById(R.id.shangchuan_bg);
		shangChuan_text = (TextView) view.findViewById(R.id.shangchuan_text);
	}

	private void setRecorderListener() {
		huiTing_bg.setOnClickListener(new HuiTingListener());
		luYin_bg.setOnClickListener(new LuYinListener());
		shangChuan_bg.setOnClickListener(new ShangChuanListener());
	}

	// 回听监听器
	class HuiTingListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 播放录音
			// Toast.makeText(MovieActivity.this,"huiting",
			// Toast.LENGTH_SHORT).show();
			play();
		}

	}

	// 录音监听器
	class LuYinListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Toast.makeText(MovieActivity.this,"luYin",
			// Toast.LENGTH_SHORT).show();
			if (luYin_text.getText().equals("录音")) {
				record(FLAG_WAV);
			} else {
				stop();
			}

		}

	}

	// 上传监听器
	class ShangChuanListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// Toast.makeText(MovieActivity.this,"shangChuan",
			// Toast.LENGTH_SHORT).show();

			getResult();
		}

	}

	private final static int CMD_RECORDING_TIME = 2000;
	private final static int CMD_RECORDFAIL = 2001;
	private final static int CMD_STOP = 2002;
	private final static int CMD_PLAYFAIL = 2003;

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
			case CMD_RECORDING_TIME:
				luYin_text.setText("完成");
				break;
			case CMD_RECORDFAIL:
				int vErrorCode = b.getInt("msg");
				String vMsg = ErrorCode.getErrorInfo(getActivity(), vErrorCode);
				Toast.makeText(getActivity(), "录音失败", Toast.LENGTH_SHORT)
						.show();
				luYin_text.setText("录音");
				break;
			case CMD_STOP:
				int vFileType = b.getInt("msg");
				switch (vFileType) {
				case FLAG_WAV:
					AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
					luYin_text.setText("录音");
					break;
				}
				break;
			case CMD_PLAYFAIL:
				Toast.makeText(getActivity(), "请先录音！", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 开始录音
	 * 
	 * @param mFlag
	 *            ，0：录制wav格式，1：录音amr格式
	 */
	private void record(int mFlag) {
		if (mState != -1) {
			Log.d("ces", "ggggg");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_RECORDFAIL);
			b.putInt("msg", ErrorCode.E_STATE_RECODING);
			msg.setData(b);

			uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
			return;
		}
		int mResult = -1;
		AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
		mResult = mRecord_1.startRecordAndFile();
		if (mResult == ErrorCode.SUCCESS) {
			Log.d("ces", "ssss");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_RECORDING_TIME);
			msg.setData(b);
			uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
			mState = mFlag;
		} else {
			Log.d("ces", "hhhhh");
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_RECORDFAIL);
			b.putInt("msg", mResult);
			msg.setData(b);
			uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
		}
	}

	/**
	 * 停止录音
	 */
	private void stop() {
		if (mState != -1) {
			AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
			mRecord_1.stopRecordAndFile();
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_STOP);
			b.putInt("msg", mState);
			msg.setData(b);
			uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
			mState = -1;
		}
	}

	/**
	 * 播放录音
	 */
	private void play() {
		if (mState != -1) {

			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_PLAYFAIL);
			b.putInt("msg", mState);
			msg.setData(b);
			uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
			mState = -1;
		} else {
			if (AudioFileFunc.getWavFilePath() != "") {
				try {
					PlayAudioTrack.PlayAudioTrack(AudioFileFunc
							.getWavFilePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Log.d("play", "找不到录音文件！！！");
			}
		}
	}

	// 展示数据
	public void getResult() {
		UpdateDialog.Builder builder = new UpdateDialog.Builder(getActivity());
		final Dialog updateDialog = builder.create();
		final ResultDialog.Builder builder2 = new ResultDialog.Builder(
				getActivity());
		updateDialog.show();
		// 获取上传文件的路径
		String path = AudioFileFunc.getWavFilePath();
		// 判断上传路径是否为空
		if (TextUtils.isEmpty(path.trim())) {
			Toast.makeText(getActivity(), "上次文件路径不能为空", 1).show();
		} else {
			// 异步的客户端对象
			AsyncHttpClient client = new AsyncHttpClient();
			// 指定url路径
			String url = Urls.UPLOAD;
			// 封装文件上传的参数
			RequestParams params = new RequestParams();
			// 根据路径创建文件
			File file = new File(path);
			try {
				// 放入文件
				params.put("file", file);
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("file", "文件不存在----------");
			}
			client.post(url, params, new TextHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					if (statusCode == 200) {
						Log.d("sucess", responseString);
						if(!responseString.equals("0")){
						updateDialog.dismiss();					
						Dialog resultDialog = builder2.create(responseString.trim());
						resultDialog.show();
						}
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub

					Log.d("fail", responseString);
				}
			});
			Log.d("finish", "333333");
		}

	}
}
