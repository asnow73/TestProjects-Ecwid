package ru.albertroom.ecwidtesttask.time;

public class Chronometer implements IChronometer
{
	private long startTime;
	private long currentTime;
	private long periodTime;
	
	public Chronometer(long periodTimeNanoSecond)
	{
		this.startTime = 0;		
		this.currentTime = 0;
		this.periodTime = periodTimeNanoSecond;
	}
	
	@Override
	public void start()
	{
		startTime = System.nanoTime();
		currentTime = startTime;
	}
	
	@Override
	public boolean isTimePassed()
	{
		boolean result = false;
		if (currentTime != 0)
		{
			currentTime = System.nanoTime();
		}
		
		if ((currentTime - startTime) >= periodTime) 
		{
			result = true;
		}
		return result;
	}
}