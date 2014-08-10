package ru.albertroom.ecwidtesttask;

/*public class Timer
{
	private long startTime;
	private long finishTime;
	
	public Timer()
	{
		startTime = 0;
		finishTime = 0;
	}
	
	private long convertNanoSecondToMiliSecond(long nanoValue)
	{
		return  (nanoValue / 1000000);
	}
	
	public void start()
	{
		startTime = System.nanoTime();
	}
	
	public void finish()
	{
		finishTime = System.nanoTime();
	}
	
	public long getTotalTime()
	{
		return convertNanoSecondToMiliSecond(finishTime - startTime);
	}
}*/

//import java.util.ArrayList;

public class Timer //extends Thread
{
	//private ArrayList<ITimerHandler> handlers;
	
	private long startTime;
	private long finishTime;
	
	//private long startSecond;
	//private long currentTime;
	
	public Timer()
	{
		startTime = 0;
		finishTime = 0;
		//handlers = new ArrayList<ITimerHandler>();
	}
	
	//public void addHandler(ITimerHandler handler)
	//{
	//	handlers.add(handler);
	//}
	
	private long convertNanoSecondToMiliSecond(long nanoValue)
	{
		return  (nanoValue / 1000000);
	}
	
	//@Override
	public void start()
	{
		//System.out.println("Timer started");
		startTime = System.nanoTime();
		//startSecond = startTime;
		//super.start();
	}
	
	public void finish()
	{
		//System.out.println("Timer finished");
		finishTime = System.nanoTime();
		//this.interrupt();
	}
	
	/*public void run()
	{
		while (true)
		{
			if(!Thread.interrupted())	//Проверка прерывания
            {
				currentTime = System.nanoTime();
				if (currentTime - startSecond >= 1000000000)
				{
					for (ITimerHandler handler : handlers)
					{
						handler.onEachSecond();
					}
					startSecond = currentTime;
				}
            }
            else
            {
            	return;
            }
		}
	}*/
	
	public long getTotalTime()
	{
		return convertNanoSecondToMiliSecond(finishTime - startTime);
	}
}
