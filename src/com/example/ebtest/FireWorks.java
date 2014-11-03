package com.example.ebtest;

import java.util.List;
import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

public class FireWorks {

	public Scene bubbleBurst(List<Sound> explosionSound, Random rand, Sprite afterBurstBackground,final Scene scene, List<Text> text) {
		//int randInt = rand.nextInt(50000)%4;
        int randInt = 0;
		 SpriteBackground secondBackground = null;
			if(afterBurstBackground == null){ // initializing the pop sound.. wotn be called if the new images appears after a few secs.. this is pop sound on bubble burst impact and not after impact.

		if(randInt==0)
			explosionSound.get(0).play();
		else if(randInt == 1)
			explosionSound.get(1).play();
		else if(randInt == 2)
			explosionSound.get(2).play();
		else
			explosionSound.get(3).play();
		}
		if(afterBurstBackground != null){
		  secondBackground = new SpriteBackground(afterBurstBackground);
		 secondBackground.setColor(Color.WHITE);
		 //this.mScene.attachChild(text);
		}if(text != null){
		 for(Text t : text){
			 scene.attachChild(t);
		 }}
		 //scene.attachChild(text2);
		if(afterBurstBackground != null)
		 scene.setBackground(secondBackground);
		 return scene;
		
	}  public void startFireworksExplosion(float fCoordX, float fCoordY, int iParticleCount,
			 double dInitVel, float dSpreading,
			 int iDecelerationPercent, float startRed, float endRed, float startGreen,
			 float endGreen, float startBlue, float endBlue, final Engine engine,
			 ITextureRegion particleTextureRegion,VertexBufferObjectManager vertexBufferObjectManager) {
			CircleOutlineParticleEmitter circleEmitter;
			//final SpriteParticleSystem particleSystem = new SpriteParticleSystem(particleEmitter, 60, 60, 360, this.particleTextureRegion, this.getVertexBufferObjectManager());
			final SpriteParticleSystem particleSystem;
			final Random generator = new Random();
			
			final float fDecelPercent = iDecelerationPercent / 100.0f;
			final double vel = dInitVel;
			// radii good value is 50.
			int ang = generator.nextInt(359); if(ang < 0 )ang = -ang;
			
			//int radii = ang%3 * 20 + 10;
			float radii = 15f;
			
			circleEmitter = new CircleOutlineParticleEmitter(fCoordX, fCoordY, radii);
			//particleSystem = new SpriteParticleSystem(new PointParticleEmitter(fCoordX, fCoordY), 20, 200, 200, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
			//particleSystem = new SpriteParticleSystem(circleEmitter, 100, 200, 400, particleTextureRegion, this.getVertexBufferObjectManager());
			particleSystem = new SpriteParticleSystem(circleEmitter, 20f, 30f, iParticleCount, particleTextureRegion, vertexBufferObjectManager);
			float fVelocityX = (float) (Math.cos(Math.toRadians(ang)) * vel);
			float fVelocityY = (float) (Math.sin(Math.toRadians(ang)) * vel);
			particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 0.0f,0.0f));
			particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0f));
			particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-2f, 2f, -20f, -10f));
			particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(fVelocityX,fVelocityY));
			particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
			particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2f));
			particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0f, 0.5f, 0.5f,0.25f));
			particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.5f, 1.0f, 0.25f,0f));			
			particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0f, 1f, 1f, 1f, 0f, 1f, 0f, 0f));
			particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1f, 2f, startRed, endRed, startGreen, endGreen, startBlue, endBlue));
			particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0f, 1f, 0f, 0.5f));
			particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(1f, 0f, 1f, 2f));
			engine.getScene().attachChild(particleSystem);
			engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
			final RunnableHandler runnableRemoveHandler = new RunnableHandler();
			//Blob.this.getEngine().getScene().registerUpdateHandler(runnableRemoveHandler);
			engine.getScene().registerUpdateHandler(runnableRemoveHandler);
					  //.registerPreFrameHandler(runnableRemoveHandler);
			engine.getScene().detachChild(particleSystem);
			}
			}));
			
		} 
}
