package com.example.ebtest;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
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
	private Rectangle mGreenRectangle, mRedRectangle;
	private Body mRedBody, mGreenBody;
	private PhysicsWorld mPhysicsWorld;
	private Camera mCamera;
	public RotatingBodyManager(final Scene scene, final Camera camera, final PhysicsWorld physicsWorld)
	{
		mPhysicsWorld = physicsWorld;
		mScene = scene;
		mCamera = camera;
	}
	/*
	 * Always handle collissions in the rotating body. That way behavior can be specifically adjusted.
	 */
	public Rectangle createRotatingBody(final Scene scene, final Camera camera, final PhysicsWorld physicsWorld, float x, float y, float thickness, float height, final Color cuttingColor, final Color rotatingColor, final AnimatedSprite pOtherShape, final VertexBufferObjectManager vbo)
	{
		// Create green rectangle
		//width 40 
		
		mGreenRectangle = new Rectangle(x, y, 1, 1, vbo);
		mGreenRectangle.setColor(Color.TRANSPARENT);
		mScene.attachChild(mGreenRectangle);

		// Create red rectangle 80, 5
		mRedRectangle = new Rectangle(x, y, height, thickness, vbo);
		mRedRectangle.setColor(rotatingColor);

		//mScene.attachChild(mRedRectangle);

		// Create body for green rectangle (Static)
		mGreenBody = PhysicsFactory.createBoxBody(mPhysicsWorld, mGreenRectangle, BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		// Create body for red rectangle (Dynamic, for our arm)
		mRedBody = PhysicsFactory.createBoxBody(mPhysicsWorld, mRedRectangle, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(5, 0.5f, 0.5f));
		
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mRedRectangle, mRedBody, true, true));

		// Create revolute joint, connecting those two bodies 
		final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(mGreenBody, mRedBody, mGreenBody.getWorldCenter());
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.motorSpeed = 2;  // fjrom -1
 		revoluteJointDef.maxMotorTorque = 100;
		mPhysicsWorld.createJoint(revoluteJointDef);
		
		 
		
		mScene.registerUpdateHandler(mPhysicsWorld);
		return mRedRectangle;
	}
}
