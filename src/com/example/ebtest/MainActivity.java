package com.example.ebtest;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 00:06:23 - 11.07.2010
 */
public class MainActivity extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;

	//private BitmapTextureAtlas mBitmapTextureAtlas;
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private ITiledTextureRegion mFaceTextureRegion;

	private BitmapTextureAtlas mOnScreenControlTexture;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;
	private ITextureRegion mBgTexture;
	private PhysicsWorld mPhysicsWorld;
	private Body testBody1;
	private Body testBody2;
	//private BitmapTextureAtlas mBackgroundTexture;
	//private BitmapTextureAtlas bgTexture;
	//private ITextureRegion bgTextureRegion;
	private ITextureRegion mbackgroundRegion;
	private BitmapTextureAtlas mybackgroundTextureAtlas;
	 Rectangle redRectangle;
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		Toast.makeText(this, "Also try tapping this AnalogOnScreenControl!", Toast.LENGTH_LONG).show();

		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		//return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), this.mCamera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//mybackgroundTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(),1024,1024,TextureOptions.NEAREST);

		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "worms_2.png", 2, 1);
		try {
			this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}


        //this.mBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
        //this.bgTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		mybackgroundTextureAtlas =  new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		//mybackgroundTextureAtlas 
		mbackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mybackgroundTextureAtlas, this, "background.png",0,0);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
		//this.mBgTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundTexture, this, "background.png", 0, 0);
		this.mOnScreenControlTexture.load();
		this.mybackgroundTextureAtlas.load();
		//this.mBgTexture.load();
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		//scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

		//create a Sprite object.
		Sprite spritebackground = new Sprite(0,0,mbackgroundRegion,getVertexBufferObjectManager());
		//create a SpriteBackground object.
		SpriteBackground background = new SpriteBackground(0,0,0,spritebackground);
		//set the background to scene
		scene.setBackground(background);
		
		//Sprite bgSprite = new Sprite(0,0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTexture,this.getVertexBufferObjectManager());
		//SpriteBackground background=new SpriteBackground(bgSprite);
		//scene.setBackground(background);        
		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);

		final float centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final float centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;

		final AnimatedSprite face = new AnimatedSprite(centerX, centerY, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		face.animate(100);
		final PhysicsHandler physicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(physicsHandler);

		scene.attachChild(face);

		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(0, CAMERA_HEIGHT -50 - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, 200, this.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				face.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 1, 1.5f), new ScaleModifier(0.25f, 1.5f, 1)));
			}
		});
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.25f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		//analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlBase().setScale(0.75f);
		analogOnScreenControl.getControlKnob().setScale(0.75f);
		analogOnScreenControl.getControlKnob().setAlpha(0.25f);
		analogOnScreenControl.refreshControlKnobPosition();

		scene.setChildScene(analogOnScreenControl);

		initJoints(scene);
		
		/* The actual collision-checking. */
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				if(redRectangle.collidesWith(face)) {
					redRectangle.setColor(1, 0, 0);
				} else {
					redRectangle.setColor(0, 1, 0);
				}
				
				if(!mCamera.isRectangularShapeVisible(face)) {
					redRectangle.setColor(1, 0, 1);
				}
			}
		});

		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	private void initJoints(final Scene scene) {
		// revolute engine
		//
		// Create green rectangle
		final Rectangle greenRectangle = new Rectangle(CAMERA_WIDTH/2, 10, 40, 40, getVertexBufferObjectManager());
		greenRectangle.setColor(Color.GREEN);
		scene.attachChild(greenRectangle);

		// Create red rectangle
		redRectangle = new Rectangle(CAMERA_WIDTH/2, 15, 120, 10, getVertexBufferObjectManager());
		redRectangle.setColor(Color.RED);
		scene.attachChild(redRectangle);

		// Create body for green rectangle (Static)
		final Body greenBody = PhysicsFactory.createBoxBody(mPhysicsWorld, greenRectangle, BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		// Create body for red rectangle (Dynamic, for our arm)
		final Body redBody = PhysicsFactory.createBoxBody(mPhysicsWorld, redRectangle, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(5, 0.5f, 0.5f));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(redRectangle, redBody, true, true));

		// Create revolute joint, connecting those two bodies 
		final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(greenBody, redBody, greenBody.getWorldCenter());
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.motorSpeed = 10;  // fjrom -1
 		revoluteJointDef.maxMotorTorque = 100;
		mPhysicsWorld.createJoint(revoluteJointDef);
		scene.registerUpdateHandler(mPhysicsWorld);

	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}