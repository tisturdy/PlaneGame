package com.pg;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Handler;

import com.pg.MainActivity;
import com.pg.R;

public class GameSoundPool {
	private MainActivity mainActivity;
	private SoundPool soundPool;
	private Handler hd;
	private HashMap<Integer,Integer> map;

	public GameSoundPool(MainActivity mainActivity,final Handler hd){
		this.hd=hd;
		this.mainActivity = mainActivity;
		map = new HashMap<Integer,Integer>();
		soundPool = new SoundPool(8,AudioManager.STREAM_MUSIC,0);
		
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				// TODO Auto-generated method stub
				System.out.println("准备就绪...");
				hd.sendEmptyMessage(0);
				
			}
		});
	}
	public void initGameSound(){
		map.put(1, soundPool.load(mainActivity, R.raw.shoot, 1));
		map.put(2, soundPool.load(mainActivity, R.raw.explosion, 1));
		map.put(3, soundPool.load(mainActivity, R.raw.explosion2, 1));
		map.put(4, soundPool.load(mainActivity, R.raw.bigexplosion, 1));
		map.put(5, soundPool.load(mainActivity, R.raw.gamelost, 1));
	}
	//播放音效
	public void playSound(int sound,int loop){
		System.out.println("play");
		AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
		float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
		System.out.println("map.get(sound):"+map.get(sound)+"  volume="+volume+" loop= "+loop);
		
			soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);	
		
		
	}
}
