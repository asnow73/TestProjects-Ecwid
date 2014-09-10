package ru.albertroom.ecwidtesttask.downloader.services;

import ru.albertroom.ecwidtesttask.time.IChronometer;

//Class to control the downloading speed
public class SpeedController implements ISpeedController
{
	private int limitBytesInSecond;
	private int allowedToDownloadBytes;
	private IChronometer period;
	
	int countSec = 0;
	
	public SpeedController(int limitBytes, IChronometer timePeriod)
	{
		this.limitBytesInSecond = limitBytes;
		this.allowedToDownloadBytes = 0;
		this.period = timePeriod;
		timePeriod.start();
	}

	//Method to get size of the allowed bytes to download
	@Override
	public synchronized int getAllowBytesToDownload()
	{
		final int DEFAULT_SIZE_PART_DATA = 100;
		int allowBytes = 0;
				
		if (period.isTimePassed()) //one second passed
		{
			countSec++;
			System.out.println("Second past " + String.valueOf(countSec) + ", downloaded " + String.valueOf(allowedToDownloadBytes));
			allowedToDownloadBytes = 0;
			period.start();
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
