package com.example.ebtest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
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
	public Set<AnimatedSprite> getGoodAppleSet() {
		return goodAppleSet;
	}

	public Set<AnimatedSprite> getBadAppleSet() {
		return badAppleSet;
	}

	public AppleManager(final Scene scene,  final Camera camera, final VertexBufferObjectManager vbo){
		mScene = scene;
		mRandomSeed = System.nanoTime();
		CAMERA_HEIGHT = (int)camera.getHeight();
		CAMERA_WIDTH = (int)camera.getWidth();
		mVbo = vbo;
		appleList = new ArrayList<AnimatedSprite>();
		mCamera = camera;
		goodAppleSet = new HashSet<AnimatedSprite>();
		badAppleSet = new HashSet<AnimatedSprite>();
	}
	
	public List<AnimatedSprite> generateApplesWithCollissionSprite(int appleCount, ITiledTextureRegion pApplesTextureRegion, final AnimatedSprite face)
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
						goodAppleSet.remove(apples);
						badAppleSet.add(apples);
						apples.setScale(3f);
						apples.setVisible(false);
					}
//					else if(face.collidesWith(apples)){
//						apples.setVisible(false);
//					}
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
