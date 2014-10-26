package com.example.ebtest;

import java.util.List;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.widget.Toast;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class RotatingBodyManager {

	private Scene mScene;
	//private Rectangle mGreenRectangle, mRedRectangle;
	//private Body mRedBody, mGreenBody;
	private PhysicsWorld mPhysicsWorld;
	private Camera mCamera;
	public RotatingBodyManager(final Scene scene, final Camera camera, final PhysicsWorld physicsWorld)
	{
		mPhysicsWorld = physicsWorld;
		mScene = scene;
		mCamera = camera;
	}
	
	public void initJoints(final Scene scene, final Sprite face, long randomSeed, final MainActivity activity, int rotBodyCount) {
		final Random random = new Random(randomSeed);
		for(int i = 0; i < rotBodyCount; i++){
		float randFloat = random.nextFloat();
		float randFloatX = random.nextFloat();
		float randFloatY = random.nextFloat();
		final Rectangle mGreenRectangle = new Rectangle(randFloatX * mCamera.getWidth()/4 + i*100, randFloatY + i*50 , 1, 1, activity.getVertexBufferObjectManager() );
		mGreenRectangle.setColor(Color.TRANSPARENT);
		scene.attachChild(mGreenRectangle);

		// Create red rectangle
		final Rectangle mRedRectangle = new Rectangle(randFloatX*mCamera.getWidth()/4 + i*100,  randFloatY + i*50, 1, 40, activity.getVertexBufferObjectManager());
		mRedRectangle.setColor(Color.RED);
		scene.attachChild(mRedRectangle);

		// Create body for green rectangle (Static)
		Body mGreenBody = PhysicsFactory.createBoxBody(mPhysicsWorld, mGreenRectangle, BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		// Create body for red rectangle (Dynamic, for our arm)
		Body mRedBody = PhysicsFactory.createBoxBody(mPhysicsWorld, mRedRectangle, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(5, 0.5f, 0.5f));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mRedRectangle, mRedBody, true, true));
		
		// Create revolute joint, connecting those two bodies 
		final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(mGreenBody, mRedBody, mGreenBody.getWorldCenter());
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.motorSpeed = -1f;
 		revoluteJointDef.maxMotorTorque = 100;
 		
		mPhysicsWorld.createJoint(revoluteJointDef);
		

		scene.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {

					if(mRedRectangle.collidesWith(face)) {
						face.setScale(1.1f);
						face.setAlpha(0.5f);
						
						GameOverScene pScene = new GameOverScene(activity.getEngine().getCamera(),activity.mPauseTextureRegion, activity.getEngine().getVertexBufferObjectManager(),activity);
		            	activity.getEngine().getScene().setChildScene(pScene.getPauseScene(), false, true, true);
		            	activity.getEngine().stop();
						mRedRectangle.setColor(0, 1, 0);
					} else {
						mRedRectangle.setColor(1, 1, 0);
					}
				
				if(!mCamera.isRectangularShapeVisible(face)) {
					face.setPosition(mCamera.getWidth()-20,mCamera.getHeight()/2 + 20);
					mRedRectangle.setColor(1, 0, 1);
					//face.setPosition(100, 100);
					//stageEndReached = true;
					
				}
			
			}
			
		});
		scene.registerUpdateHandler(mPhysicsWorld);
	}
	
	}
}
	/*
	 * Always handle collissions in the rotating body. That way behavior can be specifically adjusted.
	 */
//	f}
