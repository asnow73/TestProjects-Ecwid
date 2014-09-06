package ru.albertroom.ecwidtesttask.time;

//Class to measure the time
public class Timer
{
	private long startTime;
	private long finishTime;
	
	public Timer()
	{
		startTime = 0;
		finishTime = 0;
	}	
	
	public void start()
	{
		System.out.println("Timer started");
		startTime = System.nanoTime();
	}
	
	public void finish()
	{
		System.out.println("Timer finished");
		finishTime = System.nanoTime();
	}
		
	public long getTotalTime()
	{
		return TimeConverter.convertNanoSecondToMiliSecond(finishTime - startTime);
	}
}
