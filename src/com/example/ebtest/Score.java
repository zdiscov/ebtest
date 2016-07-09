package com.example.ebtest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
	
	public void setLocalHighScoreToFile(String value) throws FileNotFoundException
	{
		File f = new File(ERASE_FILENAME);
		PrintWriter pw = new PrintWriter(f);
		pw.println(value);
		pw.close();
	}

	public String getLocalHighScoreFromFile() throws FileNotFoundException, IOException
	{
		File f = new File(ERASE_FILENAME);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String data = br.readLine();
		br.close();
		return data;
	}
	/*
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
        //m_scoreCount = 0;
    }
    */
	/*
	public String getLocalHighScoreFromFile(Context ctx) throws IOException, FileNotFoundException 
	{
		//char[] ch = new char[256];			
		//ch[textToCompare.charAt(0)]++;
		
		FileInputStream fis;
		
		fis = ctx.openFileInput(ERASE_FILENAME);
		
		//InputStreamReader is = new InputStreamReader(fis);
		File f = new File(ERASE_FILENAME);
		fis = new FileInputStream(f);
		 //BufferedInputStream buf = new BufferedInputStream(new FileInputStream(ERASE_FILENAME));
		 
		BufferedInputStream buf = new BufferedInputStream(fis);
		 
		
		StringBuffer fileContent = new StringBuffer("");
		byte[] buffer = new byte[1024];
		
		while (fis.read(buffer) != -1) {
		    fileContent.append(new String(buffer));
		}
		String temp = fileContent.toString();
		//System.console().writer().write("******" + temp);
		System.out.println("*****" + temp);
		return temp;
		//return fileContent.toString().equalsIgnoreCase(textToCompare);
	}
	*/

}
