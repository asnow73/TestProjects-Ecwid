package ru.albertroom.ecwidtesttask;

public class SpeedController implements IDownloadingHandler
{
	private int limitBytesInSecond;
	private int downloadedBytes;
	
	private long startSecond;
	private long currentTime;
	
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
	
	public void finish()
	{
		//this.interrupt();
	}
	
	public void onDataDownloaded(int sizeDownloadedBytes)
	{
		downloadedBytes += sizeDownloadedBytes;
		currentTime = System.nanoTime();
		if (currentTime - startSecond < 1000000000)
		{
			if (downloadedBytes > limitBytesInSecond)
			{
				System.out.println("stop threads");
			}
			startSecond = currentTime;
			downloadedBytes = 0;
		}
	}
}
