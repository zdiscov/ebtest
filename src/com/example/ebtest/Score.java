package com.example.ebtest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

public class Score {
	private Integer m_scoreCount = 0;

    String ERASE_FILENAME = "local_score_file";
    
	public Integer getScoreCount() {
		return m_scoreCount;
	}

	/* Singleton Class */
	private static Score singletonInstance;
	private Score() {
		
	}
	//private static Score singletonInstance = null;

	public static Score getScoreSingletonInstance() {	
		if (null == singletonInstance) {		
			singletonInstance = new Score();
		}		
		
		return singletonInstance;		
	}	
	/* Normal Methods */

	public void updateScore(int hit)
	{
		m_scoreCount += hit;
	}
	
	public void subtractScore(int hit)
	{
		m_scoreCount -= hit;
	}
	
	public void resetScore()
	{
		m_scoreCount = 0;
	}
	

    public void setLocalHighScoreToFile(Context ctx, Integer value)
    {
        //String zero = "0";
        FileOutputStream fos = null;
        try {
            fos =  ctx.openFileOutput(ERASE_FILENAME, Context.MODE_PRIVATE);
            fos.write(String.valueOf(value).getBytes());
            fos.close();

        } catch (Exception e) {
            Log.i("Singleton Local High Score error",e.getLocalizedMessage());
        }

    }
    
	public String getLocalHighScoreFromFile(Context ctx) throws IOException, FileNotFoundException 
	{
		//char[] ch = new char[256];			
		//ch[textToCompare.charAt(0)]++;
		
		FileInputStream fis;
		fis = ctx.openFileInput(ERASE_FILENAME);
		StringBuffer fileContent = new StringBuffer("");
		byte[] buffer = new byte[1024];
		
		while (fis.read(buffer) != -1) {
		    fileContent.append(new String(buffer));
		}
		String temp = fileContent.toString();
		return temp;
		//return fileContent.toString().equalsIgnoreCase(textToCompare);
	}


}
