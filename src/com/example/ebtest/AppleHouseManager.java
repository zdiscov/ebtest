//package com.example.ebtest;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.andengine.engine.camera.Camera;
//import org.andengine.engine.handler.IUpdateHandler;
//import org.andengine.entity.scene.Scene;
//import org.andengine.entity.sprite.AnimatedSprite;
//import org.andengine.entity.sprite.Sprite;
//import org.andengine.opengl.texture.region.ITextureRegion;
//import org.andengine.opengl.texture.region.ITiledTextureRegion;
//import org.andengine.opengl.vbo.VertexBufferObjectManager;
///*
// * Under Development
// */
//public class AppleHouseManager {
//
//	Scene mScene;
//	long mRandomSeed = 1234567890;
//	int CAMERA_WIDTH; // = pCAMERA_WIDTH;
//	int CAMERA_HEIGHT; // = pCAMERA_HEIGHT;
//	AnimatedSprite mWormSprite;
//	ITiledTextureRegion mApplesTextureRegion;
//	VertexBufferObjectManager mVbo;
//	List<AnimatedSprite> appleList;
//	Camera mCamera;
//	Set<AnimatedSprite> goodAppleSet, badAppleSet;
//	Score mScore;
//	private MainActivity mMainActivity;
//	private Sprite mAppleHouse;
//
//	public AppleHouseManager(final Scene scene,  final Camera camera, final MainActivity mainActivity, final VertexBufferObjectManager vbo){
//		//, final MainActivity mainActivity){
//		mMainActivity = mainActivity;
//		mScene = scene;
//		mRandomSeed = System.nanoTime();
//		CAMERA_HEIGHT = (int)camera.getHeight();
//		CAMERA_WIDTH = (int)camera.getWidth();
//		mVbo = vbo;
//		appleList = new ArrayList<AnimatedSprite>();
//		mCamera = camera;
//		goodAppleSet = new HashSet<AnimatedSprite>();
//		badAppleSet = new HashSet<AnimatedSprite>();
//		mScore = Score.getScoreSingletonInstance();				
//	}
//	public generateAppleHouse()
//	{
//		
//	}
//	
//
//	//public List<AnimatedSprite> generateApplesWithCollissionSprite(int appleCount, ITiledTextureRegion pApplesTextureRegion, final AnimatedSprite face, final ITextureRegion particleTextureRegion)
//	
//	public List<Sprite> generateAppleHouseWithCollissionSprite(int appleHouseCount, ITiledTextureRegion pAppleHouseTextureRegion, final AnimatedSprite face, final ITextureRegion particleTextureRegion)
//	{
//        
//		mAppleHouse = new Sprite(10, CAMERA_HEIGHT/2-50, pAppleHouseTextureRegion, mMainActivity.getVertexBufferObjectManager());
//		mAppleHouse.setScale(0.50f);
//		mAppleHouse.setAlpha(0f);
//		mAppleHouse.registerUpdateHandler(new IUpdateHandler(){
//
//			@Override
//			public void onUpdate(float pSecondsElapsed) {
//				// TODO Auto-generated method stub
//				// bad apples divided by total apples will give between 0 (transparent) and 1 (opaque)
//				float alphaValue = (float)am.getBadAppleSet().size()/(float)(am.getGoodAppleSet().size() + am.getBadAppleSet().size());
//					mAppleHouse.setAlpha(alphaValue);
//			}
//			@Override
//			public void reset() {
//				// TODO Auto-generated method stub				
//			}
//			
//		});
//		
//		
//	}
//}
