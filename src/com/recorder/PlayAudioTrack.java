package com.recorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;

public class PlayAudioTrack {	
	//AudioTrack方法失真，改用mediaplayer
	//会有QCMediaPlayer mediaplayer NOT present错误，但能正常进行
    public static void PlayAudioTrack(String filePath)
                    throws IOException {
    	  MediaPlayer player = new MediaPlayer();
           player.setDataSource(filePath);
           player.prepare();
           player.start();
           //这里资源不知如何释放
    }
}
