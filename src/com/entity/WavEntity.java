package com.entity;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class WavEntity {
	 //参数的名称,<input file>标签中的name
    private String mName ="uploadfile";
    //文件名
    private String mFileName ;
    //文件的 mime，需要根据文档查询
    private String mMime ;
    //需要上传的图片资源，因为这里测试为了方便起见，直接把 bigmap 传进来，真正在项目中一般不会这般做，而是把图片的路径传过来，在这里对图片进行二进制转换
    private String filepath;
    public void setmFileName(String mFileName){
    	this.mFileName = mFileName;
    	
    }
    public void setFilepath(String filepath){
    	this.filepath = filepath;
    	
    }
//    public FormImage(Bitmap mBitmap) {
//        this.mBitmap = mBitmap;
//    }

    public String getName() {
       return mName;
    }

    public String getFileName() {
        return mFileName;
    }
    //对图片进行二进制转换
    public byte[] getValue() {  	
    	byte[] byt=null;
    	FileInputStream input=null;
		try {
			input= new FileInputStream (filepath) ;
			byt = new byte[input.available()];
			input.read(byt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			try {
				if(input!=null)
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	   return byt;  
    }
    //wav的mine
    public String getMime() {
        return "audio/wav";
    }
}
