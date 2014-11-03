package com.example.ebtest;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class RotatingBodyManager {

	private Scene mScene;
	//private Rectangle mGreenRectangle, mRedRectangle;
	//private Body mRedBody, mGreenBody;
	private PhysicsWorld mPhysicsWorld;
	private Set<Rectangle> rectangleBodySet;
	private Set<PhysicsConnector> physicsConnectorSet;
	private Set<Joint> jointSet;
	private Set<Body> allBodySet;
	private Camera mCamera;
	public RotatingBodyManager(final Scene scene, final Camera camera, final PhysicsWorld physicsWorld)
	{
		mPhysicsWorld = physicsWorld;
		mScene = scene;
		mCamera = camera;
		rectangleBodySet = new HashSet<Rectangle>();
		physicsConnectorSet = new HashSet<PhysicsConnector>();
		jointSet = new HashSet<Joint>();
		allBodySet = new HashSet<Body>();
		
	}
	
	public void removeAllRectangleBodies(final MainActivity mainActivity)
	{
		// In a new UI thread
		// 1. Unregister Physics Connector
		// 2. Set body active to false
		// 3. destroy boyd
		// 4. detach child shape from scene

		//		for(Rectangle rBody : rectangleBodySet){
//			//mPhysicsWorld.unregisterPhysicsConnector(pPhysicsConnector);
//			//mPhysicsWorld.destroyBody(rBody);
//			mScene.unregisterUpdateHandler(rBody);
//			mScene.detachChild(rBody);
//			//mPhysicsWorld.destroyBody(rBody);
//			rBody.clearUpdateHandlers();
//			rBody.clearEntityModifiers();
//			rBody.detachSelf();
//			//mPhysicsWorld.unregisterPhysicsConnector(pPhysicsConnector);
//		}

//			final PhysicsConnector physicsConnector =
//					mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(shape);
	
/*					mainActivity.getEngine().runOnUpdateThread(new Runnable() 
					{
					    @Override
					    public void run() 
					    {
							for(PhysicsConnector pConn : physicsConnectorSet){
						             mPhysicsWorld.unregisterPhysicsConnector(pConn);
						             Body body = pConn.getBody();
						             body.setActive(false);
						             //body.setActive(false);
						             //mPhysicsWorld.destroyBody(body);
							}
						
					    }
					});
*///			for(Body body : allBodySet){
//				//mPhysicsWorld.destroyBody(body);
//				body.setActive(false);
//				body.
//			}
//		
		
//		for(PhysicsConnector pConn : physicsConnectorSet){
//			mPhysicsWorld.unregisterPhysicsConnector(pConn);
//		}
		
//		for(Joint joint : jointSet){
//			mPhysicsWorld.destroyJoint(joint);
//		}
	}
	public void initJoints(final Scene scene, final Sprite face, long randomSeed, final MainActivity activity, int rotBodyCount, int rotBodyLength) {
		final Random random = new Random(randomSeed);
		
		try{
			
		
			for(int i = 1; i <= rotBodyCount; i++){
			float randFloat = random.nextFloat();
			float randFloatX = random.nextFloat();
			float randFloatY = random.nextFloat();
			
			if(randFloatY > mCamera.getWidth()){
				i--;
				continue;
			}
			if(randFloatX > mCamera.getHeight()){
				i--;
				continue;
			}
			//Object
			final Rectangle mGreenRectangle = new Rectangle(randFloatX * mCamera.getWidth()/4 + i*100, randFloatY + i*50 , 1, 1, activity.getVertexBufferObjectManager() );
			mGreenRectangle.setColor(Color.TRANSPARENT);
			scene.attachChild(mGreenRectangle);
			rectangleBodySet.add(mGreenRectangle);
			//Object
			// Create red rectangle
			final Rectangle mRedRectangle = new Rectangle(randFloatX*mCamera.getWidth()/4 + i*100,  randFloatY + i*50, 1, rotBodyLength, activity.getVertexBufferObjectManager());
			mRedRectangle.setColor(Color.RED);
			scene.attachChild(mRedRectangle);
			rectangleBodySet.add(mRedRectangle);
			//Object
			// Create body for green rectangle (Static)
			Body mGreenBody = PhysicsFactory.createBoxBody(mPhysicsWorld, mGreenRectangle, BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0, 0));
	
			//Object
			// Create body for red rectangle (Dynamic, for our arm)
			Body mRedBody = PhysicsFactory.createBoxBody(mPhysicsWorld, mRedRectangle, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(5, 0.5f, 0.5f));
	//		MassData massData = new MassData();
	//		massData.mass = 1;
	//		mRedBody.setMassData(massData);
			allBodySet.add(mGreenBody);
			allBodySet.add(mRedBody);
			PhysicsConnector pConnector = new PhysicsConnector(mRedRectangle, mRedBody, true, true);
			physicsConnectorSet.add(pConnector);
			mPhysicsWorld.registerPhysicsConnector(pConnector);
			
			//Object
			// Create revolute joint, connecting those two bodies 
			final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
			revoluteJointDef.initialize(mGreenBody, mRedBody, mGreenBody.getWorldCenter());
			revoluteJointDef.enableMotor = true;
			revoluteJointDef.motorSpeed = (i%2 == 0 ? (-0.5f*i*0.5f): (0.5f*i*0.5f));
	 		revoluteJointDef.maxMotorTorque = 100;
	 		
			Joint joint = mPhysicsWorld.createJoint(revoluteJointDef);

	 		jointSet.add(joint);
	
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
							//mRedRectangle.setColor(0, 1, 0);
						} else {
							//mRedRectangle.setColor(1, 1, 0);
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
			//scene.u
	}
	
	}catch(Exception e){
		Log.d("error",e.getLocalizedMessage());
	}
	}
}
	/*
	 * Always handle collissions in the rotating body. That way behavior can be specifically adjusted.
	 */
//	f}
