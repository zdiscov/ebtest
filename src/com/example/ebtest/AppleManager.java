package com.example.ebtest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AppleManager {

	Scene mScene;
	long mRandomSeed = 1234567890;
	int CAMERA_WIDTH; // = pCAMERA_WIDTH;
	int CAMERA_HEIGHT; // = pCAMERA_HEIGHT;
	AnimatedSprite mWormSprite;
	ITiledTextureRegion mApplesTextureRegion;
	VertexBufferObjectManager mVbo;
	List<AnimatedSprite> appleList;
	Camera mCamera;
	Set<AnimatedSprite> goodAppleSet, badAppleSet;
	Score mScore;
	private MainActivity mMainActivity;
	//MainActivity mMainActivity;
	public Set<AnimatedSprite> getGoodAppleSet() {
		return goodAppleSet;
	}

	public Set<AnimatedSprite> getBadAppleSet() {
		return badAppleSet;
	}

	public AppleManager(final Scene scene,  final Camera camera, final MainActivity mainActivity, final VertexBufferObjectManager vbo){
			//, final MainActivity mainActivity){
		mMainActivity = mainActivity;
		mScene = scene;
		mRandomSeed = System.nanoTime();
		CAMERA_HEIGHT = (int)camera.getHeight();
		CAMERA_WIDTH = (int)camera.getWidth();
		mVbo = vbo;
		appleList = new ArrayList<AnimatedSprite>();
		mCamera = camera;
		goodAppleSet = new HashSet<AnimatedSprite>();
		badAppleSet = new HashSet<AnimatedSprite>();
		mScore = Score.getScoreSingletonInstance();
		
		
	}
	
	public List<AnimatedSprite> generateApplesWithCollissionSprite(int appleCount, ITiledTextureRegion pApplesTextureRegion, final AnimatedSprite face, final ITextureRegion particleTextureRegion)
	{
  		final Random random = new Random(mRandomSeed);
  		for(int appleIdx = 0; appleIdx < appleCount; appleIdx++){  	
  			
	  		final AnimatedSprite apples = new AnimatedSprite(random.nextFloat() * (CAMERA_WIDTH - 32), random.nextFloat() * (CAMERA_HEIGHT - 32), pApplesTextureRegion, mVbo);
	  		apples.setScale(0.25f);
	  		//apples.animate(2);
	  		apples.setUserData(Integer.valueOf(appleIdx));
	  		apples.registerUpdateHandler(new IUpdateHandler(){

	  			/* Code where worm eats the apple 
	  			 * Good Apples are apples not eaten by worm
	  			 * Bad Apples are apples eaten by worm
	  			 * 
	  			 * */
				
	  			@Override
				public void onUpdate(float pSecondsElapsed) {
					// TODO Auto-generated method stub
					if(apples.collidesWith(face)){
						if(apples.isVisible()){
							mScore.updateScore(1);
							/* Adding fancy shmancy when an apple gets eaten by the worm */
							
							FireWorks fworks = new FireWorks();
							//fworks.startFireworksExplosion(apples.getX(), apples.getY(), iParticleCount, dInitVel, dSpreading, iDecelerationPercent, startRed, endRed, startGreen, endGreen, startBlue, endBlue, engine, particleTextureRegion, vertexBufferObjectManager);
							//fworks.bubbleBurst(explosionSound, rand, afterBurstBackground, scene, text);
							fworks.startFireworksExplosion(apples.getX(),apples.getY(),40,4,0.5f,90, 1.0f, 0.5f, 1.0f,0f,0f,1f,mMainActivity.getEngine(),particleTextureRegion,mMainActivity.getVertexBufferObjectManager());						
							Sounds crunchSound = new Sounds();
					  		crunchSound.playSound(mMainActivity, mScene,"ricecrunch.mp3",false,false);
					  		//crunchSound.playSound(mMainActivity, mScene,"ricecrunch.mp3",false,false);
					  		
						}
//						try{
//							MainActivity.mScoreText.setText(String.format("Apples - %d", mScore.getScoreCount()));                            
//						}catch(Exception ex){
//							Debug.e(ex.toString());
//						}
						goodAppleSet.remove(apples);
						badAppleSet.add(apples);
						apples.setScale(3f);
						apples.setVisible(false);
						//apples.detachSelf();
					}
				}

				@Override
				public void reset() {
					// TODO Auto-generated method stub
					
				}
	  			
	  		});
	  		/* Create visible sprite apples only */
	  		if(mCamera.isRectangularShapeVisible(apples)){
	  			appleList.add(apples);
	  			goodAppleSet.add(apples);
	  		}else{
	  			appleIdx--;
	  			continue;
	  		}
  		}
		return appleList;
	}
}
