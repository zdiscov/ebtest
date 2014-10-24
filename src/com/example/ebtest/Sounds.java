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
import org.andengine.util.debug.Debug;

public class Sounds {
private MainActivity mActivity;
private Scene mScene;
private Music mMusic;
	public Sounds(MainActivity mainActivity)
	{
		mActivity = mainActivity;
		mScene = mActivity.getEngine().getScene();
	}
    public void myLoadPlayer() 
    {
        // FOR EXAMPLE THIS DISPLAYS A SPRITE USING TEXTURES FROM THE ACTIVITY
                //final Sprite mySprite = new Sprite(0, 0, this.mActivity.mySoundIconTextureRegion,
                                                //this.mActivity.getVertexBufferObjectManager());
                
        		final Sprite notes = new Sprite(100, 100, this.mSoundNotesTextureRegion, this.mActivity.getVertexBufferObjectManager());
        		mScene.attachChild(notes);

        		mScene.registerTouchArea(notes);
        		mScene.setOnAreaTouchListener(new IOnAreaTouchListener() {
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
        		
        		try {
        			this.mMusic = MusicFactory.createMusicFromAsset(mActivity.getEngine().getMusicManager(), mActivity.getApplicationContext(), "smb_over.mid");
        			this.mMusic.setLooping(true);
        		} catch (final IOException e) {
        			Debug.e(e);
        		}



                this.mActivity.getEngine().getScene().attachChild(notes);
        }
}
