package com.pg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	public static MainActivity instance;
	private GameSoundPool sounds;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		// 设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 显示自定义的SurfaceView视图
		sounds = new GameSoundPool(this, hd);
		sounds.initGameSound();
	  
	}

	Handler hd = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//sounds.playSound(1, 0);
			setContentView(new MySurfaceView(MainActivity.this, sounds,MainActivity.this));
		
		}

	};
}