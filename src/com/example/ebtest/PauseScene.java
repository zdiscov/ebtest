package com.example.ebtest;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PauseScene {
	private CameraScene mPauseScene;

	public CameraScene getPauseScene() {
		return mPauseScene;
	}

	public PauseScene(final Scene pauseScene, final Camera camera, final ITextureRegion pausedTextureRegion, final VertexBufferObjectManager vbo)
	{
		mPauseScene = new CameraScene(camera);
		/* Make the 'PAUSED'-label centered on the camera. */
		final float centerX = (camera.getWidth() -  pausedTextureRegion.getWidth()) / 2;
		final float centerY = (camera.getHeight() - pausedTextureRegion.getHeight()) / 2;
		final Sprite pausedSprite = new Sprite(centerX, centerY, pausedTextureRegion, vbo);
		this.mPauseScene.attachChild(pausedSprite);
		/* Makes the paused Game look through. */
		this.mPauseScene.setBackgroundEnabled(false);
	}
}
