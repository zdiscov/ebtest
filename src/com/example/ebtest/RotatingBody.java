package com.example.ebtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class RotatingBody {
public static Map<Rectangle,Rectangle> createRotatingBody(int width, int height, VertexBufferObjectManager vbo, final Scene scene, final PhysicsWorld physicsWorld)
{
	// Create green rectangle
	final Rectangle greenRectangle = new Rectangle(width/2, height, 40, 40, vbo);
	greenRectangle.setColor(Color.TRANSPARENT);
	scene.attachChild(greenRectangle);

	// Create red rectangle
	final Rectangle redRectangle = new Rectangle(width/2, height, 80, 5, vbo);
	redRectangle.setColor(Color.RED);
	scene.attachChild(redRectangle);

	// Create body for green rectangle (Static)
	final Body greenBody = PhysicsFactory.createBoxBody(physicsWorld, greenRectangle, BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0, 0));

	// Create body for red rectangle (Dynamic, for our arm)
	final Body redBody = PhysicsFactory.createBoxBody(physicsWorld, redRectangle, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(5, 0.5f, 0.5f));
	physicsWorld.registerPhysicsConnector(new PhysicsConnector(redRectangle, redBody, true, true));

	// Create revolute joint, connecting those two bodies 
	final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
	revoluteJointDef.initialize(greenBody, redBody, greenBody.getWorldCenter());
	revoluteJointDef.enableMotor = true;
	revoluteJointDef.motorSpeed = 2;  // fjrom -1
		revoluteJointDef.maxMotorTorque = 100;
	physicsWorld.createJoint(revoluteJointDef);
	scene.registerUpdateHandler(physicsWorld);
	HashMap<Rectangle,Rectangle> hMap = new HashMap<Rectangle,Rectangle>();
	hMap.put(greenRectangle, redRectangle);
	return hMap;
	//return scene;
}
}
