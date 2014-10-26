package com.example.ebtest;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
//import org.andengine.examples.MusicExample;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

public class Sounds {
private MainActivity mActivity;
private Music mMusic;
//private ITextureRegion mSoundNotesTextureRegion;
	public  Sounds(MainActivity mainActivity,  Scene scene)
	{
		mActivity = mainActivity;
		//mScene = scene;
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(mActivity.getEngine().getMusicManager(), mActivity.getApplicationContext(), "smb_over.mid");
			//mMusic.play();
			this.mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}


		final Sprite notes = new Sprite(50, 50,  mainActivity.mNotesTextureRegion, mainActivity.getVertexBufferObjectManager());
		//notes.setAlpha(0.75f);
		notes.setColor(Color.RED);
		//notes.setScale(0.4f);
		scene.attachChild(notes);
		scene.registerTouchArea(notes);
		scene.setOnAreaTouchListener(new IOnAreaTouchListener() {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final ITouchArea pTouchArea, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) {
					if(mMusic.isPlaying()) {
						mMusic.pause();
					} else {
						mMusic.play();
					}
				}

				return true;
			}
		});

		//return mScene;
        //this.mActivity.getEngine().getScene().attachChild(notes);
	}
//    public Scene myLoadPlayer(MainActivity mainActivity) 
//    {
//        // FOR EXAMPLE THIS DISPLAYS A SPRITE USING TEXTURES FROM THE ACTIVITY
//                //final Sprite mySprite = new Sprite(0, 0, this.mActivity.mySoundIconTextureRegion,
//                                                //this.mActivity.getVertexBufferObjectManager());
//                
//    		return mScene;
//        		
//
//
//        }
}
