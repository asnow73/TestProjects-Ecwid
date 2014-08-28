package ru.albertroom.ecwidtesttask.downloader.services;


public class DownloadedBytesCounter implements IDownloadingHandler
{
	private int totalSizeData;
	
	public DownloadedBytesCounter()
	{
		this.totalSizeData = 0;
	}
	
	public synchronized void onDataDownloaded(int sizeDownloadedBytes)
	{		
		totalSizeData += sizeDownloadedBytes;
	}
	
	public int getTotalSizeDownloadedData()
	{
		return totalSizeData;
	}
}
