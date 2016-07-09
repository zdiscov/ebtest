package com.example.ebtest;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
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
import org.andengine.entity.text.exception.OutOfCharactersException;
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

import android.content.Context;
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
public class MainActivity extends SimpleBaseGameActivity  {
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
	Score mScore;
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
     //private String[] backgroundNames = new String[]{"blackbackground.png","GreenBubbles.jpg","HandPrints.jpg","background.png","greenbackground.png","flowers.png"};
     private String[] backgroundNames = new String[]{"bluesparkle.png","GreenBubbles.jpg","Stars.jpg","OrangeCircles.jpg","greenbackground.png","flowers.png"};
     private String[] homeNames = new String[]{"applehousesmall.png"};
     private int stageCount = 0;
    // boolean stageEndReached = false;
     
     private long mDuration = 0;
		private long mFirstExecTime = 0;
		private long mSecondExecTime;
		private TimerHandler timerHandler;
		private long startTime = 120000; // 2 minutes for example
		Font mFont;
	// ===========================================================
	// Constructors
	// =====healthba======================================================
		private TickerText mText;
	private ITexture mAppleHouseTexture, mApplesTexture,mNotesTexture, mPauseTexture;
	private ITextureRegion mAppleHouseTextureRegion; //, mApplesTextureRegion;
	ITextureRegion mPauseTextureRegion;
	//private int mApplesCount = 5;
	public Sprite mAppleHouse;
	ITextureRegion mPointTextureRegion;
	public ITextureRegion getmPointTextureRegion() {
		return mPointTextureRegion;
	}

	private BitmapTextureAtlas mParticleSystemBitmapTextureAtlas;
	private AppleManager am;
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	private long mRandomSeed = 1234567890;
	private ITextureRegion mPausedTextureRegion;
	public ITextureRegion mNotesTextureRegion;
	private Sound mThemeSound;
	private BitmapTextureAtlas mSoundTextureAtlas;
	public static TickerText mScoreText, mHighScoreText;
	RotatingBodyManager rbm;
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	private MainActivity getMainActivity()
	{
		return this;
	}
	/*
	private void writeValuesToFile(String textToWrite) throws IOException
	{
		FileOutputStream fos;
        fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        fos.write(textToWrite.getBytes());
		fos.close();
	}
	 */
	@Override
	public EngineOptions onCreateEngineOptions() {
		Toast.makeText(this, "Also try tapping this AnalogOnScreenControl!", Toast.LENGTH_LONG).show();

		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		//EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED,new FillResolutionPolicy(),this.mCamera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//mybackgroundTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(),1024,1024,TextureOptions.NEAREST);

		this.mSoundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.NEAREST);

		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.NEAREST);
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
		
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 128, 128, TextureOptions.BILINEAR, Typeface.create(Typeface.SERIF, Typeface.BOLD), 16);
		this.mFont.load();
        //this.mBackgroundTexture = new BitmapTextureAtlas(this.g9etTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
        //this.bgTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		mybackgroundTextureAtlas =  new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
		//mybackgroundTextureAtlas 
		mbackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mybackgroundTextureAtlas, this, backgroundNames[0],0,0);
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
		this.mParticleSystemBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		this.mPointTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mParticleSystemBitmapTextureAtlas, this, "bf7.png", 0, 0);
		this.mPointTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mParticleSystemBitmapTextureAtlas, this, "particle_point.png", 0, 0);
//        BitmapTextureAtlas mParticleBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);//
//		this.mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mParticleBitmapTextureAtlas, this, "apples.png", 0, 0);

		/* Create Puase Scene resources */
		try {
			this.mPauseTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/gameover.png");
				}
			});

			this.mPauseTexture.load();
			this.mPauseTextureRegion = TextureRegionFactory.extractFromTexture(this.mPauseTexture);
		} catch (IOException e) {
			Debug.e(e);
		}	
		/* Create and load sound */

		//this.mNotesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSoundTextureAtlas, this.getApplicationContext(), "notes.png");
		//this.mBitmapTextureAtlas.load();
		try {
			this.mNotesTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/notes.png");
				}
			});

			this.mNotesTexture.load();
			this.mNotesTextureRegion = TextureRegionFactory.extractFromTexture(this.mNotesTexture);
		} catch (IOException e) {
			Debug.e(e);
		}

		
		/* Create sound resource */
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.mThemeSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "mfx/smb_over.mid");
		} catch (final IOException e) {
			Debug.e(e);
		}

		
		mParticleSystemBitmapTextureAtlas.load();
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
	protected void onPause()
	{
	    super.onPause();
	    if (this.isGameLoaded())
	        this.mThemeSound.pause();
	}

	@Override
	protected synchronized void onResume()
	{
	    super.onResume();
	    System.gc();
	    if (this.isGameLoaded())
	        this.mThemeSound.play(); 
	}
	@Override
	protected void onDestroy()
	{
	    super.onDestroy();
	        
	    if (this.isGameLoaded())
	    {
	        System.exit(0);    
	    }
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
		scene.setBackground(background);
		
		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);

			scene.registerUpdateHandler(this.mPhysicsWorld);
		
		float centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		 float centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
		 //face = new AnimatedSprite(CAMERA_WIDTH/2-10, CAMERA_HEIGHT-10, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		 face = new AnimatedSprite(300, 10, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		 //face.setSize(0.75f, 0.75f);
		face.setScale(0.50f);
		face.animate(3);
		final PhysicsHandler physicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(physicsHandler);
		face.stopAnimation(1);
		scene.registerUpdateHandler(this.mPhysicsWorld);
		scene.attachChild(face);
		
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(0, CAMERA_HEIGHT -50 - this.mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, 200, this.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
				if((pValueX*100 != 0) || (pValueY*100 != 0))
					face.animate(3);
				else
					face.stopAnimation(1);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				face.stopAnimation(1);
				//face.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 1, 1.5f), new ScaleModifier(0.25f, 1.5f, 1)));
			}
			
			
		});
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.50f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		//analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlBase().setScale(0.75f);
		analogOnScreenControl.getControlKnob().setScale(0.75f);
		analogOnScreenControl.getControlKnob().setAlpha(0.50f);
		analogOnScreenControl.refreshControlKnobPosition();

		scene.setChildScene(analogOnScreenControl);
		/* The actual collision-checking. */
		scene.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				if((face.collidesWith(mAppleHouse)) && (am.getGoodAppleSet().size() < 1)){
						face.setPosition(CAMERA_WIDTH-20,CAMERA_HEIGHT/2 + 20);
						mSecondExecTime = System.nanoTime();
						if((mSecondExecTime - mFirstExecTime) > 1000){
							stageCount++;
							health = 100;
							//updatehealth(health);
							stageCount = stageCount % (backgroundNames.length);
							scene.setBackground(getSpriteBackground(backgroundNames[stageCount]));
							
							//rbm.removeAllRectangleBodies();
							//scene.setBackground(new Background());
							//scene.setBackgroundEnabled(false);
							mFirstExecTime = System.currentTimeMillis();
							
							/* Add new set of images to the scene*/
							am = new AppleManager(scene,getEngine().getCamera(),getMainActivity(),getEngine().getVertexBufferObjectManager() );
					  		List<AnimatedSprite> appleList = am.generateApplesWithCollissionSprite(mApplesCount, mApplesTextureRegion, face, getmPointTextureRegion());
					  		for(int appleCount = 0; appleCount < appleList.size(); appleCount++)
					  			scene.attachChild(appleList.get(appleCount));
					  		
							rbm.removeAllRectangleBodies(getMainActivity());
							rbm.initJoints(scene, face, System.nanoTime(), getMainActivity(), 1 + stageCount%2,25 + stageCount*2);
							//Toast.makeText(getApplicationContext(), "LEVEL - " + String.valueOf(stageCount), Toast.LENGTH_SHORT).show();
					  
						}
				}
				
			}
		});

		mText = new TickerText(30, 20, this.mFont, "        ", new TickerTextOptions(HorizontalAlign.CENTER, 8), this.getVertexBufferObjectManager());

		mScoreText = new TickerText(CAMERA_WIDTH-200, 20, this.mFont,"           ", new TickerTextOptions(HorizontalAlign.CENTER, 8), this.getVertexBufferObjectManager());

		mHighScoreText = new TickerText(CAMERA_WIDTH-200, 8, this.mFont,"           ", new TickerTextOptions(HorizontalAlign.CENTER, 6), this.getVertexBufferObjectManager());

		mText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		mScoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		mHighScoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		scene.attachChild(mScoreText);
		scene.attachChild(mText);
		scene.attachChild(mHighScoreText);
		
		createTimer(this);
		/* add health bar */
	      health = 100;
          healthbar = new Rectangle(0,0,CAMERA_HEIGHT,70,this.getVertexBufferObjectManager());
          //healthbar.setColor(0, 1.0f, 0);
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
   		am = new AppleManager(scene,getEngine().getCamera(),getMainActivity(),this.getVertexBufferObjectManager());
  		List<AnimatedSprite> appleList = am.generateApplesWithCollissionSprite(mApplesCount, mApplesTextureRegion, face, this.getmPointTextureRegion());
  		for(int appleCount = 0; appleCount < appleList.size(); appleCount++)
  			scene.attachChild(appleList.get(appleCount)); 		
  		
  		Sounds mainSound = new Sounds();
  		mainSound.playSound(this, scene,"smb_over.mid",true,true);
  		
		rbm = new RotatingBodyManager(scene, mCamera, mPhysicsWorld);
		//rbm.initJoints(scene, face, RANDOM_SEED, this, 1,60);
		//RANDOM_SEED = System.nanoTime();
		//rbm.initJoints(scene, face, RANDOM_SEED, this, 1,30);

		RANDOM_SEED = System.nanoTime();
		rbm.removeAllRectangleBodies(getMainActivity());
		int numberOfRotators = (int)Math.random()%10;
		
		rbm.initJoints(scene, face, RANDOM_SEED, this, numberOfRotators,30);

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
	   
	   private void createTimer(final MainActivity mainActivity) {
		    final float period = 1; //one second
		    mScore = Score.getScoreSingletonInstance();
		   // final int temp = mScore.getScoreCount();

		    mScore.resetScore();
		    this.getEngine().registerUpdateHandler(timerHandler = new TimerHandler(period, new ITimerCallback() {                      
		        public void onTimePassed(final TimerHandler pTimerHandler) {
		            timerHandler.reset();

		            startTime = (long) (startTime - (period * 1000));
		            /* 2 minute expiry */
		            int totSeconds = (int) (startTime / 1000);
		            if(totSeconds < 0){
		            	GameOverScene pScene = new GameOverScene(getEngine().getCamera(), mPauseTextureRegion, getEngine().getVertexBufferObjectManager(),mainActivity);
		            	
		            	
						//mScore.setLocalHighScoreToFile(getApplicationContext(), mScore.getScoreCount());
						//mScore.setLocalHighScoreToFile(String.valueOf(mScore.getScoreCount()));						
		            	try {
							mScore.setLocalHighScoreToFile(String.valueOf(mScore.getScoreCount()));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
		            	
		            	getEngine().getScene().setChildScene(pScene.getPauseScene(), false, true, true);
		            	//getEngine().stop();
		            }
		            int seconds = (int) ((startTime / 1000) % 60);
		            int minutes = (int) ((startTime / 1000) / 60);
		            
		            
		          mText.setText(String.format("%d:%02d", minutes, seconds));   		      	
		          mScoreText.setText(String.format("Score - %d",mScore.getScoreCount()));
		          try {
					mHighScoreText.setText(String.format("High Score - %s",mScore.getLocalHighScoreFromFile()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		          
		          String pText="";
				/*
		          try {
					pText = mScore.getLocalHighScoreFromFile(getApplicationContext());
					try{
						if(Integer.valueOf(pText) > mScore.getScoreCount()){
							mScore.setLocalHighScoreToFile(getApplicationContext(), Integer.valueOf(pText));
						}
					}catch(Exception ex){
						mScore.setLocalHighScoreToFile(getApplicationContext(), 0);
						
						
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					mScore.setLocalHighScoreToFile(getApplicationContext(), 0);
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mScore.setLocalHighScoreToFile(getApplicationContext(), 0);
				}
				*/
		         // mHighScoreText.setText(String.format("High Score - %s",pText));
		        }
		    }));
		}

	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}