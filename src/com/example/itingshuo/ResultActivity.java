package com.example.itingshuo;

import java.util.ArrayList;
import java.util.List;

import com.anim.ListAnim;
import com.result.ResultAdapter;
import com.result.ResutlList;
import com.speak.JuziList;
import com.speak.JuziListAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class ResultActivity extends ActionBarActivity {
	  private ResultAdapter adapter = null;  
      private List<ResutlList> resutlLists;
      private ListView resultListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result_fragment);
		resultListView = (ListView) findViewById(R.id.lv_result);
		 //填充各控件的数据
	    Classinit(); 
       adapter = new ResultAdapter(ResultActivity.this, R.layout.result_item, resutlLists);
       resultListView.setLayoutAnimation(new ListAnim().getListAnim());
       resultListView.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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
	 public void Classinit(){
		  	resutlLists = new ArrayList<ResutlList>();
		  	ResutlList class1 = new ResutlList();
		  	class1.setGrade("99分");
		  	class1.setDate("2016-2-28");
		  	class1.setPingyu("这是评语这是评语这是评语");
		  	resutlLists.add(class1);
		  	ResutlList class2 = new ResutlList();
		  	class2.setGrade("99分");
		  	class2.setDate("2016-2-28");
			class2.setPingyu("这是评语这是评语这是评语");
			resutlLists.add(class2);
			ResutlList class3 = new ResutlList();
		  	class3.setGrade("99分");
		  	class3.setDate("2016-2-28");
			class3.setPingyu("这是评语这是评语这是评语");
			resutlLists.add(class3);
			ResutlList class4 = new ResutlList();
		  	class4.setGrade("99分");
		  	class4.setDate("2016-2-28");
			class4.setPingyu("这是评语这是评语这是评语");
			resutlLists.add(class4);
			ResutlList class5 = new ResutlList();
		  	class5.setGrade("99分");
		  	class5.setDate("2016-2-28");
			class5.setPingyu("这是评语这是评语这是评语");
			resutlLists.add(class5);
			ResutlList class6 = new ResutlList();
		  	class6.setGrade("99分");
		  	class6.setDate("2016-2-28");
			class6.setPingyu("这是评语这是评语这是评语");
			resutlLists.add(class6);
			ResutlList class7 = new ResutlList();
		  	class7.setGrade("99分");
		  	class7.setDate("2016-2-28");
			class7.setPingyu("这是评语这是评语这是评语");
			resutlLists.add(class7);
		  }
}
