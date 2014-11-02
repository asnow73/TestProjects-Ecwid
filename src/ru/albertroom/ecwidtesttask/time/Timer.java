package ru.albertroom.ecwidtesttask.time;

// ласс дл€ замера времени
public class Timer
{
	private long startTime;
	private long finishTime;
	
	public Timer()
	{
		startTime = 0;
		finishTime = 0;
	}	
	
	//начать замер
	public void start()
	{
		startTime = System.nanoTime();
	}
	
	//закончить замер
	public void finish()
	{
		finishTime = System.nanoTime();
	}
	
	//посчитать пршедшее врем€ в милиссекундах
	public long getTotalTime()
	{
		return TimeConverter.convertNanoSecondToMiliSecond(finishTime - startTime);
	}
}
