package com.example.itingshuo;

import java.util.ArrayList;
import java.util.List;

import com.anim.ListAnim;
import com.speak.ClassList;
import com.speak.ClassListAdapter;
import com.speak.JuziList;
import com.speak.JuziListAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class SpeakListActivity extends ActionBarActivity {
	  private JuziListAdapter adapter = null;  
      private List<JuziList> juziList;
      private ListView juziListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.speak_juzi_fragment);  
		juziListView = (ListView) findViewById(R.id.lv_speak_juzi);
		 //填充各控件的数据
	    Classinit(); 
        adapter = new JuziListAdapter(SpeakListActivity.this, R.layout.speak_juzi_item, juziList);
        juziListView.setLayoutAnimation(new ListAnim().getListAnim());
        juziListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.speak_list, menu);
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
	   /*
     * 语调class数据的读入
     * 需要服务器接口
     */

  public void Classinit(){
  	juziList = new ArrayList<JuziList>();
  	JuziList class1 = new JuziList();
  	class1.setTitle("句子1");
  	class1.setContent("句子1内容");
  	class1.setTime("10.12");
  	juziList.add(class1);
  	JuziList class2 = new JuziList();
  	class2.setTitle("句子1");
  	class2.setContent("句子2内容");
	class2.setTime("10.12");
	juziList.add(class2);
  	JuziList class3 = new JuziList();
  	class3.setTitle("句子1");
  	class3.setContent("句子3内容");
	class3.setTime("10.12");
	juziList.add(class3);
  	JuziList class4 = new JuziList();
  	class4.setTitle("句子1");
  	class4.setContent("句子4内容");
	class4.setTime("10.12");
	juziList.add(class4);
  	JuziList class5 = new JuziList();
  	class5.setTitle("句子1");
  	class5.setContent("句子5内容");
	class5.setTime("10.12");
	juziList.add(class5);
	JuziList class6 = new JuziList();
  	class6.setTitle("句子1");
  	class6.setContent("句子6内容");
	class6.setTime("10.12");
	JuziList class7 = new JuziList();
  	class7.setTitle("句子1");
  	class7.setContent("句子7内容");
	class7.setTime("10.12");
	juziList.add(class7);
  }
  
 
}
