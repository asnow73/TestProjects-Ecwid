package ru.albertroom.ecwidtesttask.downloader.services;


public class SpeedController implements ISpeedController
{
	private int limitBytesInSecond;
	private int downloadedBytes;
	
	private long startSecond;
	private long currentTime;
	
	int countSec = 0;
	
	public SpeedController(int limitBytes)
	{
		limitBytesInSecond = limitBytes;
		downloadedBytes = 0;
	}
	
	public void start()
	{
		System.out.println("SpeedController started");
		startSecond = System.nanoTime();
	}
	
	@Override
	public synchronized int getAllowBytesToDownload()
	{
		final long ONE_SECOND = 1000000000;
		final int DEFAULT_SIZE_PART_DATA = 100;
		int allowBytes = 0;
				
		currentTime = System.nanoTime();
		
		if ((currentTime - startSecond) >= ONE_SECOND)
		{
			countSec++;
			System.out.println("Second past " + String.valueOf(countSec) + ", downloaded " + String.valueOf(downloadedBytes));
			downloadedBytes = 0;
			startSecond = currentTime;
		}
			
		if (downloadedBytes < limitBytesInSecond) //если лимит не превышен
		{
			int diff = limitBytesInSecond - downloadedBytes;
			if (diff >= DEFAULT_SIZE_PART_DATA)
			{
				allowBytes = DEFAULT_SIZE_PART_DATA;
			}
			else
			{
				allowBytes = diff;
			}
			downloadedBytes += allowBytes;
		}
		
		return allowBytes;
	}

}
