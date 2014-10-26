package com.example.ebtest;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

import android.opengl.GLES20;

public class GameOverScene {
	private CameraScene mPauseScene;
	private Score mScore;

	public CameraScene getPauseScene() {
		return mPauseScene;
	}

	public GameOverScene(final Camera camera, final ITextureRegion pausedTextureRegion, final VertexBufferObjectManager vbo, final MainActivity mainActivity)
	{
		mPauseScene = new CameraScene(camera);
		mScore = Score.getScoreSingletonInstance();
		/* Make the 'PAUSED'-label centered on the camera. */
		final float centerX = (camera.getWidth() -  pausedTextureRegion.getWidth()) / 2;
		final float centerY = (camera.getHeight() - pausedTextureRegion.getHeight()) / 2;
		final Sprite pausedSprite = new Sprite(centerX, centerY, pausedTextureRegion, vbo);
		this.mPauseScene.attachChild(pausedSprite);
		/* Makes the paused Game look through. */
		this.mPauseScene.setBackgroundEnabled(false);
		

		TickerText mScoreText = new TickerText(camera.getWidth()-200, 20, mainActivity.mFont,"                ", new TickerTextOptions(HorizontalAlign.CENTER, 10), mainActivity.getVertexBufferObjectManager());

		//mText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		mScoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);	
		mScoreText.setText(String.format("High Score - %d",mScore.getScoreCount()));
		mPauseScene.attachChild(mScoreText);

	}
}
