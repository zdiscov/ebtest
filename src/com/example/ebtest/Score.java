package com.example.ebtest;

public class Score {
	private Integer m_scoreCount = 0;
	
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
}
