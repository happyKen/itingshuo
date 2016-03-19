package com.movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
public class Utils {

	public static String splitVersionStr(String str, String tag) {

		String[] strs = str.split(tag);

		return strs[1];
	}

	public static String splitVersionStr(String str) {

		String[] strs = str.split("[MB]");

		return strs[0];
	}

	public static String splitAppImageUrl(String str, int postion) {

		String[] strs = str.split("[.]");

		return strs[0] + "_" + postion + "." + strs[1];
	}

	public static String splitTvStr(String str) {

		String[] strs = str.split("[/]");

		return strs[2];
	}

	public static String splitNextUrl(String url) {
		String[] strs = url.split("_");
		return strs[0] + "_" + (Integer.parseInt(strs[1].substring(0, 1)) + 1) + ".mp4";
	}

	public static int splitNextPosition(String url) {
		String[] strs = url.split("_");
		return Integer.parseInt(strs[1].substring(0, 1)) + 1;
	}

	public static int splitCurrentLevel(String url) {
		if (url != null) {
			String[] strs = url.split("[.]");
			return Integer.parseInt(strs[0].substring(strs[0].length()-1, strs[0].length()));
		}
		return 1;
	}

	
	public static String splitNextFilePath(String filePath) {
		if (filePath.equals("") || filePath == null) {
			return "";
		} else {
			String[] strs = filePath.split("[.]");
			int level = Integer.parseInt(strs[0].substring(strs[0].length() - 1, strs[0].length())) + 1;
			return strs[0].substring(0, strs[0].length() - 1) + level + "." + strs[1];
		}

	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "";
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	/**
	 * 安装apk文件
	 * 
	 * @param context
	 * @param file
	 */
	public static void apkAdd(Context context, File file) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public static String getNoEmptyContent(String str) {

		return   str != null && !"".equals(str)? str : "";
	}

	public static int getNoEmptyCode(String str) {

		return !"".equals(str) && str != null ? Integer.parseInt(str) : 0;
	}

	public static String stringToUTF(String str) {
		String tag = "";

		if (!"".equals(str) && str != null) {
			try {
				tag = URLEncoder.encode(str, "gbk");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tag;
		}

		return tag;
	}

	/**
	 * 判断是否是中文
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是乱码的json数据
	 * 
	 * @param strName
	 * @return
	 */
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = 0;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
				chLength++;
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}



	/**
	 * 精确到俩位小数
	 * 
	 * @param f
	 * @return
	 */
	public static float getFloatNumber(float f) {
		BigDecimal bd = new BigDecimal(f);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	/**
	 * 判断文件是否下载完成
	 * 
	 * @param context
	 * @param path
	 * @param AllSize
	 * @return
	 */
	public static boolean isDownLoaded(Context context, String path, String AllSize) {
		String integer = Formatter.formatFileSize(context, new File(path).length());
		float filesize = Float.parseFloat(splitVersionStr(integer).trim());
		float fileAllSize = Float.parseFloat(splitVersionStr(AllSize).trim());
		if ((int) filesize < (int) fileAllSize) {
			return false;
		} else {
			return true;
		}
	}


	/**
	 * 计算下载速度是mb/s 还是kb/s
	 * 
	 * @param speed
	 * @return
	 */
	public static String downLoadSpeed(float speed) {
		String downLoad;
		if (speed > 1024) {
			downLoad = Utils.getFloatNumber((speed / (float) 1024)) + "MB/S";
			return downLoad;
		} else {
			downLoad = speed + "KB/S";
			return downLoad;
		}

	}

	/**
	 * 播放本地视频
	 * 
	 * @param mContext
	 * @param path
	 */
	public static void playFileVideo(Context mContext, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String type = "video/mp4";
//		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, type);
		mContext.startActivity(intent);

	}

	/**
	 * 播放网络视频
	 * 
	 * @param mContext
	 * @param path
	 */
	public static void playNetWorkVideo(Context mContext, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String type = "video/mp4";
//		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.parse(path);
		intent.setDataAndType(uri, type);
		mContext.startActivity(intent);

	}

	/**
	 * 防止因为scrollView 解决listView 等嵌套而引起的滑动到底部
	 * 
	 * @param mScrollView
	 */
	public static void disableAutoScrollToBottom(ScrollView mScrollView) {
		mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		mScrollView.setFocusable(true);
		mScrollView.setFocusableInTouchMode(true);
		mScrollView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.requestFocusFromTouch();
				return false;
			}
		});
	}
	public static void ableAutoScrollToBottom(ScrollView mScrollView) {
		mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		mScrollView.setFocusable(false);
		mScrollView.setFocusableInTouchMode(false);
		mScrollView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.requestFocusFromTouch();
				return true;
			}
		});
	}
	/**
	 * 判断是否又足够的内存可以使用
	 * @param allMemory
	 * @param fileSize
	 * @return
	 */
	public static boolean  isfreeMemory(String allMemory,String fileSize){
		double all  = 0;
		double file = 0 ;
		
		if (allMemory.contains("MB")) {
			 all =Double.parseDouble(allMemory.replaceAll(" ", "").trim().substring(0, allMemory.length()-3));
		}else if (allMemory.contains("K")) {
			all =Double.parseDouble(allMemory.replaceAll(" ", "").trim().substring(0, allMemory.length()-1));
		}else if(allMemory.contains("M")){
			all =Double.parseDouble(allMemory.replaceAll(" ", "").trim().substring(0, allMemory.length()-1));
		}
		if (fileSize.contains("M")) {
			file =Double.parseDouble(fileSize.replaceAll(" ", "").trim().substring(0, fileSize.length()-1));
		}else if (fileSize.contains("MB")) {
			file =Double.parseDouble(fileSize.replaceAll(" ", "").trim().substring(0, fileSize.length()-2));
		}else if (fileSize.contains("K")) {
			file =Double.parseDouble(fileSize.replaceAll(" ", "").trim().substring(0, fileSize.length()-1));
		}
		
		if (all > file) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 插屏广告
	 * @param context
	 * @param spotListener
	 */
//	public static boolean  setSpotAd(Context context,SpotDialogListener spotListener) {
//		// 插播接口调用
//		// 开发者可以到开发者后台设置展示频率，需要到开发者后台设置页面（详细信息->业务信息->无积分广告业务->高级设置）
//		// 自4.03版本增加云控制是否开启防误点功能，需要到开发者后台设置页面（详细信息->业务信息->无积分广告业务->高级设置）
//
//		// 加载插播资源
//		SpotManager.getInstance(context).loadSpotAds();
//		// 插屏出现动画效果，0:ANIM_NONE为无动画，1:ANIM_SIMPLE为简单动画效果，2:ANIM_ADVANCE为高级动画效果
//		SpotManager.getInstance(context).setAnimationType(
//				SpotManager.ANIM_ADVANCE);
//		// 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图。
//		SpotManager.getInstance(context).setSpotOrientation(
//				SpotManager.ORIENTATION_PORTRAIT);
//				// 展示插播广告，可以不调用loadSpot独立使用
//				SpotManager.getInstance(context).showSpotAds(context, spotListener);
//				return true;
//		
//	}
//	
public static boolean objIsNull (Object object){
	
	if (object != null) {
		return false;
	}
	return true;
}	

public static void delectFile (String path){
	
	File file  =new File(path);
	if (file.exists()) {
		file.delete();
	}
}

/**
 * 
 * @param theString
 * @return String
 */
public static String unicodeToUtf8(String theString) {
    char aChar;
    if(theString==null){
        return "";
    }
    int len = theString.length();
    StringBuffer outBuffer = new StringBuffer(len);
    for (int x = 0; x < len;) {
        aChar = theString.charAt(x++);
        if (aChar == '/') {
            aChar = theString.charAt(x++);
            if (aChar == 'u') {
                // Read the xxxx
                int value = 0;
                for (int i = 0; i < 4; i++) {
                    aChar = theString.charAt(x++);
                    switch (aChar) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        value = (value << 4) + aChar - '0';
                        break;
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                        value = (value << 4) + 10 + aChar - 'a';
                        break;
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                        value = (value << 4) + 10 + aChar - 'A';
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Malformed   /uxxxx   encoding.");
                    }
                }
                outBuffer.append((char) value);
            } else {
                if (aChar == 't')
                    aChar = 't';
                else if (aChar == 'r')
                    aChar = 'r';
                else if (aChar == 'n')
                    aChar = 'n';
                else if (aChar == 'f')
                    aChar = 'f';
                outBuffer.append(aChar);
            }
        } else
            outBuffer.append(aChar);
    }
    return outBuffer.toString();
}
 

public static  void full(boolean enable,Activity activity) {
    if (enable) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    } else {
        WindowManager.LayoutParams attr = activity.getWindow().getAttributes();
        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(attr);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}

/**
* 获取指定文件大小
* @param f
* @return
* @throws Exception
*/
public static long getFileSize(File file) throws Exception
{
long size = 0;
 if (file.exists()){
 FileInputStream fis = null;
 fis = new FileInputStream(file);
 size = fis.available();
 }
 else{
 Log.e("获取文件大小","文件不存在!");
 }
 return size;
}
 

/**  
 * 复制单个文件  
 * @param oldPath String  
 * @param newPath String  
 * @return boolean  
 */   
public static boolean copyFile(File oldFile, String newPath) {   
   try {   
       int bytesum = 0;   
       int byteread = 0;   
      
       if (oldFile.exists()) { //文件不存在时   
           InputStream inStream = new FileInputStream(oldFile.getAbsolutePath()); //读入原文件   
           FileOutputStream fs = new FileOutputStream(newPath);   
           byte[] buffer = new byte[1444];   
           int length;   
           while ( (byteread = inStream.read(buffer)) != -1) {   
               bytesum += byteread; //字节数 文件大小   
               System.out.println(bytesum);   
               fs.write(buffer, 0, byteread);   
           }   
           inStream.close();   
           return true;
       }   
       return false;
   }   
   catch (Exception e) {   
       System.out.println("复制单个文件操作出错");   
       e.printStackTrace();   
       return false;
   }

}   

/**  
 * 复制整个文件夹内容  
 * @param oldPath String 
 * @param newPath String
 * @return boolean  
 */   
public static void copyFolder(String oldPath, String newPath) {   

   try {   
       (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹   
       File a=new File(oldPath);   
       String[] file=a.list();   
       File temp=null;   
       for (int i = 0; i < file.length; i++) {   
           if(oldPath.endsWith(File.separator)){   
               temp=new File(oldPath+file[i]);   
           }   
           else{   
               temp=new File(oldPath+File.separator+file[i]);   
           }   

           if(temp.isFile()){   
               FileInputStream input = new FileInputStream(temp);   
               FileOutputStream output = new FileOutputStream(newPath + "/" +   
                       (temp.getName()).toString());   
               byte[] b = new byte[1024 * 5];   
               int len;   
               while ( (len = input.read(b)) != -1) {   
                   output.write(b, 0, len);   
               }   
               output.flush();   
               output.close();   
               input.close();   
           }   
           if(temp.isDirectory()){//如果是子文件夹   
               copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);   
           }   
       }   
   }   
   catch (Exception e) {   
       System.out.println("复制整个文件夹内容操作出错");   
       e.printStackTrace();   

   }   

}  

public static boolean checkPhoneNumber(String phone){
	 Pattern p = Pattern
	            .compile("^((12[0-9])|(11[0-9])|(13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9])|)\\d{11}$");
	    Matcher m = p.matcher(phone);
	    System.out.println(m.matches() + "---");
	    return m.matches();
	}
/**
 * 解决俩个double类型相减
 * @param v1
 * @param v2
 * @return
 */
public static double sub(double v1, double v2) {  
    BigDecimal b1 = new BigDecimal(Double.toString(v1));  
    BigDecimal b2 = new BigDecimal(Double.toString(v2));  
    return b1.subtract(b2).doubleValue();  
	}  
/**
 * 设置textView的图片
 * @param context
 * @param textView
 * @param id
 */
public static void setTextViewDrawable(Context context,TextView textView,int  id){
	
	Drawable drawable = context.getResources().getDrawable(id);
	drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
	textView.setCompoundDrawables(drawable, null, null, null);//画在右边
	}

public static String generateTime(long time) {
	int totalSeconds = (int) (time / 1000);
	int seconds = totalSeconds % 60;
	int minutes = (totalSeconds / 60) % 60;
	int hours = totalSeconds / 3600;

	return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
}
}
