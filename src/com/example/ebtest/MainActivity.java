package com.example.ebtest;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.entity.util.FPSLogger;
//import org.andengine.examples.ParticleSystemSimpleExample;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import android.graphics.Typeface;
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
	// Constantsfa
	// ===========================================================

	private static  int CAMERA_WIDTH = 480;
	private static int CAMERA_HEIGHT = 320;
	private static long RANDOM_SEED = 1234567890; 
	private int mApplesCount = 10;
	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private AnimatedSprite face;
	//private BitmapTextureAtlas mBitmapTextureAtlas;
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private ITiledTextureRegion mFaceTextureRegion, mApplesTextureRegion;

	private BitmapTextureAtlas mOnScreenControlTexture;
	private BuildableBitmapTextureAtlas mHomeTextureAtlas;
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
	 private Rectangle redRectangle;
     private static int health;
     private static Rectangle healthbar;
     private String[] backgroundNames = new String[]{"background.png","greenbackground.png","flowers.png"};
     private String[] homeNames = new String[]{"applehousesmall.png"};
     private int stageCount = 0;
    // boolean stageEndReached = false;
     
     private long mDuration = 0;
		private long mFirstExecTime = 0;
		private long mSecondExecTime;
		private TimerHandler timerHandler;
		private long startTime = 120000; // 2 minutes for example
		private Font mFont;
	// ===========================================================
	// Constructors
	// ===========================================================
		private TickerText mText;
	private ITexture mAppleHouseTexture, mApplesTexture;
	private ITextureRegion mAppleHouseTextureRegion; //, mApplesTextureRegion;
	//private int mApplesCount = 5;
	public Sprite mAppleHouse;
	private ITextureRegion mParticleTextureRegion;
	private BitmapTextureAtlas mParticleSystemBitmapTextureAtlas;
	private AppleManager am;
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

		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.NEAREST);
		this.mHomeTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		//actually 256x64
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "worms1200x225.png", 3, 1);
		//this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "worms_2.png", 2, 1);
		try {
			this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.mFont.load();
        //this.mBackgroundTexture = new BitmapTextureAtlas(this.g9etTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
        //this.bgTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		mybackgroundTextureAtlas =  new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		//mybackgroundTextureAtlas 
		mbackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mybackgroundTextureAtlas, this, "background.png",0,0);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
		this.mOnScreenControlTexture.load();
		this.mybackgroundTextureAtlas.load();
		/* Create Apple House Texture */
		try {
			this.mAppleHouseTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/applehousesmall.png");
				}
			});

			this.mAppleHouseTexture.load();
			this.mAppleHouseTextureRegion = TextureRegionFactory.extractFromTexture(this.mAppleHouseTexture);
		} catch (IOException e) {
			Debug.e(e);
		}

		/* Create Apple Texture */
		try {
			this.mApplesTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/apples.png");
				}
			});

			this.mApplesTexture.load();
			this.mApplesTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mApplesTexture,4,1);
		} catch (IOException e) {
			Debug.e(e);
		}
		
		/* Create particle system */
		this.mParticleSystemBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mParticleSystemBitmapTextureAtlas, this, "particle_point.png", 0, 0);

		this.mBitmapTextureAtlas.load();

	}

	private SpriteBackground getSpriteBackground(String imageName)
	{
		mbackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mybackgroundTextureAtlas, this, imageName,0,0);
		
		//create a Sprite object.
		Sprite spritebackground = new Sprite(0,0,mbackgroundRegion,getVertexBufferObjectManager());
		//create a SpriteBackground object.
		SpriteBackground background = new SpriteBackground(0,0,0,spritebackground);
		return background;
	}
	@Override
	public Scene onCreateScene() {
		RANDOM_SEED = System.nanoTime();
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();

		//create a Sprite object.
		Sprite spritebackground = new Sprite(0,0,mbackgroundRegion,getVertexBufferObjectManager());
		//create a SpriteBackground object.
		
		SpriteBackground background = new SpriteBackground(0,0,0,spritebackground);
		//set the background to scene
		//scene.setBackground(background);
		
		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);

			scene.registerUpdateHandler(this.mPhysicsWorld);
		
		float centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		 float centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
		 face = new AnimatedSprite(centerX, centerY, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		//face.setSize(0.75f, 0.75f);
		face.setScale(0.50f);
		face.animate(3);
		final PhysicsHandler physicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(physicsHandler);
		face.stopAnimation();
		scene.registerUpdateHandler(this.mPhysicsWorld);
		scene.attachChild(face);
		
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(0, CAMERA_HEIGHT -50 - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, 200, this.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
				if((pValueX*100 != 0) || (pValueY*100 != 0))
					face.animate(3);
				else
					face.stopAnimation();
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				face.stopAnimation();
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

		initJoints(scene,face);
		
		
		RotatingBody.createRotatingBody(CAMERA_WIDTH/2 - 100,CAMERA_HEIGHT/2-100, getVertexBufferObjectManager(), scene, mPhysicsWorld, face);

		
		/* The actual collision-checking. */
		scene.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				//if(( pSecondsElapsed > 1) && (pSecondsElapsed < 2)){
				if(redRectangle.collidesWith(face)) {
					//face.setRotation(0.5f);
                      updatehealth(health - 5);

					redRectangle.setColor(1, 0, 0);
				} else {
					redRectangle.setColor(0, 0, 0);
				}
				
				if((face.collidesWith(mAppleHouse)) && (am.getGoodAppleSet().size() < 1)){
						face.setPosition(CAMERA_WIDTH-20,CAMERA_HEIGHT/2 + 20);
						mSecondExecTime = System.nanoTime();
						if((mSecondExecTime - mFirstExecTime) > 1000){
							stageCount++;
							health = 100;
							updatehealth(health);
							stageCount = stageCount % (backgroundNames.length);
							scene.setBackground(getSpriteBackground(backgroundNames[stageCount]));
							mFirstExecTime = System.currentTimeMillis();
							
							/* Add new set of images to the scene*/
							am = new AppleManager(scene,getEngine().getCamera(),getEngine().getVertexBufferObjectManager());
					  		List<AnimatedSprite> appleList = am.generateApplesWithCollissionSprite(mApplesCount, mApplesTextureRegion, face);
					  		for(int appleCount = 0; appleCount < appleList.size(); appleCount++)
					  			scene.attachChild(appleList.get(appleCount));
					  
						}
						//setBackground(scene,backgroundNames[stageCount]);
					//}
				}
				
				
				if(!mCamera.isRectangularShapeVisible(face)) {
					face.setPosition(CAMERA_WIDTH-20,CAMERA_HEIGHT/2 + 20);
					redRectangle.setColor(1, 0, 1);
					//face.setPosition(100, 100);
					//stageEndReached = true;
					
				}
			}
			//}
		});

		mText = new TickerText(30, 60, this.mFont, "     ", new TickerTextOptions(HorizontalAlign.CENTER, 10), this.getVertexBufferObjectManager());
//		mText.registerEntityModifier(
//			new SequenceEntityModifier(
//				new ParallelEntityModifier(
//					new AlphaModifier(10, 0.0f, 1.0f),
//					new ScaleModifier(10, 0.5f, 1.0f)
//				),
//				new RotationModifier(5, 0, 360)
//			)
//		);
		mText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		scene.attachChild(mText);
		createTimer();
		/* add health bar */
	      health = 100;
          healthbar = new Rectangle(0,0,health * 2,20,this.getVertexBufferObjectManager());
          healthbar.setColor(0, 1.0f, 0);
          healthbar.setAlpha(0.4f);
          scene.attachChild(healthbar);

          
  		mAppleHouse = new Sprite(10, CAMERA_HEIGHT/2-50, this.mAppleHouseTextureRegion, this.getVertexBufferObjectManager());
  		mAppleHouse.setScale(0.50f);
  		mAppleHouse.setAlpha(0f);
  		mAppleHouse.registerUpdateHandler(new IUpdateHandler(){

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				// bad apples divided by total apples will give between 0 (transparent) and 1 (opaque)
				float alphaValue = (float)am.getBadAppleSet().size()/(float)(am.getGoodAppleSet().size() + am.getBadAppleSet().size());
					mAppleHouse.setAlpha(alphaValue);
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
  			
  		});
  		scene.attachChild(mAppleHouse);
  		//scene.getChildByTag(pTag)


		//RotatingBodyManager rbm = new RotatingBodyManager(scene,getEngine().getCamera(),this.mPhysicsWorld);
		

  		am = new AppleManager(scene,getEngine().getCamera(),this.getVertexBufferObjectManager());
  		List<AnimatedSprite> appleList = am.generateApplesWithCollissionSprite(mApplesCount, mApplesTextureRegion, face);
  		for(int appleCount = 0; appleCount < appleList.size(); appleCount++)
  			scene.attachChild(appleList.get(appleCount));
  		
		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	   private static void updatehealth(int newhealth){
           health = newhealth;
           if (health < 0)
                   health = 0;
           if(health < 20)
        	   healthbar.setColor(1f,0f,0f);
           else if(health < 140)
        	   healthbar.setColor(1f,1f,0f);
           healthbar.setWidth(newhealth * 5);
	   }
	   
	   private void createTimer() {
		    final float period = 1; //one second

		    this.getEngine().registerUpdateHandler(timerHandler = new TimerHandler(period, new ITimerCallback() {                      
		        public void onTimePassed(final TimerHandler pTimerHandler) {
		            timerHandler.reset();

		            startTime = (long) (startTime - (period * 1000));
		            int seconds = (int) ((startTime / 1000) % 60);
		            int minutes = (int) ((startTime / 1000) / 60);
		           mText.setText(String.format("%d:%02d", minutes, seconds));                            
		        }
		    }));
		}
	private void initJoints(final Scene scene, final Sprite face) {
			// revolute engine
		//
		// Create green rectangle
		for(int i = 0; i < 5; i++){
			final Rectangle greenRectangle = new Rectangle(CAMERA_WIDTH/4 + i*40, 10 + i*30, 1, 1, getVertexBufferObjectManager());
			greenRectangle.setColor(Color.TRANSPARENT);
			scene.attachChild(greenRectangle);
	
			// Create red rectangle
			redRectangle = new Rectangle(CAMERA_WIDTH/4 + i*40, 10 + i*30, 40, 1, getVertexBufferObjectManager());
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
			revoluteJointDef.motorSpeed = -1;  // fjrom -1
	 		revoluteJointDef.maxMotorTorque = 10;
	 		
			mPhysicsWorld.createJoint(revoluteJointDef);
			
	//		RotatingBody.createRotatingBody(CAMERA_WIDTH/2 - 200,500, getVertexBufferObjectManager(), scene, mPhysicsWorld, face);
	
	//		RotatingBody.createRotatingBody(CAMERA_WIDTH/4 - 50 ,100, getVertexBufferObjectManager(), scene, mPhysicsWorld);
	
			scene.registerUpdateHandler(mPhysicsWorld);
		}
		

	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}