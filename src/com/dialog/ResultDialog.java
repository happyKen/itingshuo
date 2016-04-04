package com.dialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.itingshuo.R;

public class ResultDialog extends Dialog{

	
	public ResultDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public ResultDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private View contentView;
		private TextView tv_voice;
		private TextView tv_emotion;
		private TextView tv_energy;
		private TextView tv_speed;
		private TextView tv_rhytm;
		private TextView tv_intonation;
		private TextView tv_final;
		private TextView tv_feedback;
		public Builder(Context context) {
			this.context = context;
		}



		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}
		//录入服务器传来的评价
		/*
		 * tv_voice      音准：D;句子发音不够准确,部分内容不够完整。;62.769229849608898;
		 * tv_emotion    情感：C;句子表达略有情感色彩。参考情感为：(悲伤)。;70.461713906311545;
		 * tv_energy     重音：D;重音发音较差；;63.170994800608604;
		 * tv_speed      语速：B;语速比较适中。;88.234578283272924;
		 * tv_rhytm      节奏：A;句子节奏感较好,朗读较为流利。;90.971317812358478;
		 * tv_intonation 语调：D;发音语调较差，需要注意。;66.948286229758168;
		 * tv_final      总分：C;
		 * tv_feedback   总评：句子发音不够准确,部分内容不够完整。句子表达略有情感色彩。参考情感为：(悲伤)。语速比较适中。句子节奏感较好,朗读较为流利。发音语调较差，需要注意。
		 */
         public void setInformation(String info){
        	 String [] information = info.split(";");
        	 for (int i = 0; i < information.length; i++) {
                Log.d(""+i,information[i]);
             }
        	 tv_voice.setText(information[0]);
        	 tv_emotion.setText(information[3]);
        	 tv_energy.setText(information[6]);
        	 tv_speed.setText(information[9]);
        	 tv_rhytm.setText(information[12]);
        	 tv_intonation.setText(information[15]);
        	 tv_final.setText(information[18]);
        	 tv_feedback.setText(information[19]);
         }
		public ResultDialog create(String info) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final ResultDialog dialog = new ResultDialog(context,R.style.Dialog);
			dialog.setCanceledOnTouchOutside(false);
			View layout = inflater.inflate(R.layout.dialog_result_layout, null);
			init(layout);
			setInformation(info);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set back listener
			((ImageView)layout.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.setContentView(layout);
			return dialog;
		}
		/*
		 * 初始化个各个指标控件
		 */
	     public void init(View view){
	    	tv_voice = (TextView) view.findViewById(R.id.voice2);
	 		tv_emotion = (TextView) view.findViewById(R.id.emotion2);
	 		tv_speed = (TextView) view.findViewById(R.id.speed2);
	 		tv_rhytm = (TextView) view.findViewById(R.id.rhythm2);
	 		tv_energy = (TextView) view.findViewById(R.id.energy2);
	 		tv_intonation = (TextView) view.findViewById(R.id.intonation2);
	 		tv_final = (TextView) view.findViewById(R.id.final2);
	 		tv_feedback = (TextView) view.findViewById(R.id.feedback2);
	 		
	 		}
	}

}
