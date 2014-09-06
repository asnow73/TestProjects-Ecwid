package ru.albertroom.ecwidtesttask.downloader.services;

//Class to controll the downloading speed
public class SpeedController implements ISpeedController
{
	private int limitBytesInSecond;
	private int allowedToDownloadBytes;
	
	private long startSecond;
	private long currentTime;
	
	int countSec = 0;
	
	public SpeedController(int limitBytes)
	{
		limitBytesInSecond = limitBytes;
		allowedToDownloadBytes = 0;
	}
	
	public void start()
	{
		System.out.println("SpeedController started");
		startSecond = System.nanoTime();
	}
	
	//Methos to get size of the allowed bytes to download
	@Override
	public synchronized int getAllowBytesToDownload()
	{
		final long ONE_SECOND = 1000000000;
		final int DEFAULT_SIZE_PART_DATA = 100;
		int allowBytes = 0;
				
		currentTime = System.nanoTime();
		
		if ((currentTime - startSecond) >= ONE_SECOND) //one second passed
		{
			countSec++;
			System.out.println("Second past " + String.valueOf(countSec) + ", downloaded " + String.valueOf(allowedToDownloadBytes));
			allowedToDownloadBytes = 0;
			startSecond = currentTime;
		}
			
		if (allowedToDownloadBytes < limitBytesInSecond) //if the limit is less than allowed to download bytes
		{
			int diff = limitBytesInSecond - allowedToDownloadBytes;
			if (diff >= DEFAULT_SIZE_PART_DATA)
			{
				allowBytes = DEFAULT_SIZE_PART_DATA;
			}
			else
			{
				allowBytes = diff;
			}
			allowedToDownloadBytes += allowBytes;
		}
		
		return allowBytes;
	}

}
