package com.pg;



import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.KeyEvent;


public class Player implements SensorEventListener {
	//���ǵ�Ѫ����Ѫ��λͼ
	//Ĭ��3Ѫ
	private int playerHp = 3;
	private Bitmap bmpPlayerHp;
	//���ǵ������Լ�λͼ
	public int x, y;
	private Bitmap bmpPlayer;
	//��ʱ��
	private int noCollisionCount = 0;
	//�޵�ʱ��
	private int noCollisionTime = 60;
	//�Ƿ���ײ�ı�ʶλ
	private boolean isCollision;

    private SensorManager mSensorMgr = null;    
    Sensor mSensor = null;  
    
    private float GX = 0;
    private float GY = 0;
	//���ǵĹ��캯��
	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MySurfaceView.screenH - bmpPlayer.getHeight();
		mSensorMgr = (SensorManager)MainActivity.instance.getSystemService(Service.SENSOR_SERVICE);   
        mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);   
        mSensorMgr.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);  
	}

	//���ǵĻ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//��������
		//�������޵�ʱ��ʱ����������˸
		if (isCollision) {
			//ÿ2����Ϸѭ��������һ������
			if (noCollisionCount % 2 == 0) {
				canvas.drawBitmap(bmpPlayer, x, y, paint);
			}
		} else {
			canvas.drawBitmap(bmpPlayer, x, y, paint);
		}
		//��������Ѫ��
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(), MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);
		}
	}

	 public void onAccuracyChanged(Sensor arg0, int arg1) {
         // TODO Auto-generated method stub
         
     }

     @Override
     public void onSensorChanged(SensorEvent e) {     
          GX = e.values[SensorManager.DATA_X];        
          GY = e.values[SensorManager.DATA_Y];        
      }     

	//���ǵ��߼�
	public void logic() {
		//���������ƶ�
		x -= GX*1.7;
		if (GY<0) {
		y -= 1.2*GY;
	}
		y+=GY*2.0;
		//�ж���ĻX�߽�
		if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
			x = MySurfaceView.screenW - bmpPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}
		//�ж���ĻY�߽�
		if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
			y = MySurfaceView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}
		//�����޵�״̬
		if (isCollision) {
			//��ʱ����ʼ��ʱ
			noCollisionCount++;
			if (noCollisionCount >= noCollisionTime) {
				//�޵�ʱ����󣬽Ӵ��޵�״̬����ʼ��������
				isCollision = false;
				noCollisionCount = 0;
			}
		}
	}

	//��������Ѫ��
	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}

	//��ȡ����Ѫ��
	public int getPlayerHp() {
		return playerHp;
	}

	//�ж���ײ(������л�)
	public boolean isCollsionWith(Enemy en) {
		//�Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = en.x;
			int y2 = en.y;
			int w2 = en.frameW;
			int h2 = en.frameH;
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			//��ײ�������޵�״̬
			isCollision = true;
			return true;
			//�����޵�״̬��������ײ
		} else {
			return false;
		}
	}
	//�ж���ײ(������л��ӵ�)
	public boolean isCollsionWith(Bullet bullet) {
		//�Ƿ����޵�ʱ��
		if (isCollision == false) {
			int x2 = bullet.bulletX;
			int y2 = bullet.bulletY;
			int w2 = bullet.bmpBullet.getWidth();
			int h2 = bullet.bmpBullet.getHeight();
			if (x >= x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			//��ײ�������޵�״̬
			isCollision = true;
			return true;
			//�����޵�״̬��������ײ
		} else {
			return false;
		}
	}
}
