package com.example.itingshuo;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import com.config.Urls;
import com.entity.JLogin;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import volley.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
public class LoginActivity extends Activity {
	private Button btn_login;
	private EditText edt_username;
	private EditText edt_password;
	private TextView tv_error;
	private UIHandler uiHandler;
	private UIThread uiThread; 
	private String username;
	private String password;
	private Toast toast;
	public static final String TAG = "LoginActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		findViewByIds();
		setListeners();
		init();
	}
	private void findViewByIds(){
		btn_login=(Button) findViewById(R.id.btn_login);
		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_password = (EditText) findViewById(R.id.edt_password);
		tv_error = (TextView) findViewById(R.id.tv_error);
	}
	private void setListeners(){
		//登陆监听器
        btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//登陆中界面，待写
				logining();
				edt_username.setFocusable(false);
				edt_password.setFocusable(false);
				
				//登陆成功跳转
				if(checkLogin()==1){
					mySendIntent();
				}else{
					loginError();
				}
			

			}
		});
        edt_username.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edt_username.setFocusable(true);
				edt_username.setFocusableInTouchMode(true);
				edt_username.requestFocus();
				edt_username.requestFocusFromTouch();
			}
		});
        edt_password.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edt_password.setFocusable(true);
				edt_password.setFocusableInTouchMode(true);
				edt_password.requestFocus();
				edt_password.requestFocusFromTouch();
			}
		});
        //输入username
        edt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean a) {
				if(a){
				// TODO Auto-generated method stub
				Message msg = new Message();
				   Bundle b = new Bundle();// 存放数据
		           b.putInt("cmd",CMD_INPUT);
		           msg.setData(b); 
		           uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
				}
			}
		});
        
        //输入password
        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean c) {
				if(c){
				// TODO Auto-generated method stub
				Message msg = new Message();
				   Bundle b = new Bundle();// 存放数据
		           b.putInt("cmd",CMD_INPUT);
		           msg.setData(b); 
		           uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
				}
			}
		});
        
    }
	/*
	 * 显示登陆错误信息
	 */
	private void loginError(){
			Message msg = new Message();
		   Bundle b = new Bundle();// 存放数据
           b.putInt("cmd",CMD_LOGIN_ERROR);
           msg.setData(b); 
           uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
	}
	/*
	 * 登陆中
	 */
	private void logining(){
		Message msg = new Message();
		Bundle b = new Bundle();// 存放数据
        b.putInt("cmd",CMD_LOGINING);
        msg.setData(b); 
        uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
	}
	
	/*
	 * 判断密码是否正确，正确1，错误0
	 */

	private int checkLogin(){
		int checkId= 0;
		if(!edt_username.getText().toString().equals("") )//""不能转型为int,字符串对比要用equals
		{
		//获取用户输入的账号
         username = edt_username.getText().toString().trim();
		}
        //获取用户输入的密码
         password = edt_password.getText().toString();
        //调用登录方法
        if(login(username, password))
        	checkId=1;
        return checkId;
	}
	/*
	 * 与服务器连接
	 */
	private Boolean login(String username,String password){
		if(username!=null&&password!=null){
		 Map<String,String> map = new HashMap<String,String>();
	        map.put("username", username);
	        map.put("password",password);
	       VolleyManager.newInstance().GsonPostRequest(TAG, map, Urls.LOGIN_URL, JLogin.class, new Response.Listener<JLogin>() {
	           @Override
	           public void onResponse(JLogin jLogin) {
	               Log.d("111111111111111111111", "ok" + jLogin.getData());

	           }
	       }, new Response.ErrorListener() {
	           @Override
	           public void onErrorResponse(VolleyError error) {
	               Log.d("111111111111111111111", "okhhhghhh");

	           }
	       });
	        Log.d(TAG, "222222");
		}
	        return true;
		
		
	}
	private void init(){
		uiHandler = new UIHandler();
		toast = new Toast(this);

	}
	 private final static int CMD_LOGIN_ERROR = 2000;
	 private final static int CMD_LOGINING = 2001;
	 private final static int CMD_INPUT = 2002;
	 
	 class UIHandler extends Handler{
	        public UIHandler() {
	        }
	        @Override
	        public void handleMessage(Message msg) {
	            // TODO Auto-generated method stub
	            Log.d("MyHandler", "handleMessage......");
	            super.handleMessage(msg);
	            Bundle b = msg.getData();
	            int vCmd = b.getInt("cmd");
	            switch(vCmd)
	            {
	            case CMD_LOGINING:
	            	//Toast.makeText(LoginActivity.this, "正在登陆中。。。。", Toast.LENGTH_LONG).show();
	            	break;
	            case CMD_INPUT:
	            	tv_error.setVisibility(View.GONE);
	                break;
	            case CMD_LOGIN_ERROR:  
	            	tv_error.setVisibility(View.VISIBLE);
	                break;
	            default:
	                break;
	            }
	        }
	    };
	    class UIThread implements Runnable {        

	        @Override
			public void run() {
	          
	            } 
	 
	        }
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void mySendIntent(){
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //new一个Bundle对象，并将要传递的数据传入
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        //将bundle对象assign给Intent
        intent.putExtras(bundle);
        //开启跳转
        startActivity(intent);
	}
}
