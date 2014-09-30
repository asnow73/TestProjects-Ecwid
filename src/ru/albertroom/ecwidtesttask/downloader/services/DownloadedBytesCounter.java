package ru.albertroom.ecwidtesttask.downloader.services;

//Класс для организации подсчёта скачанных байтов
public class DownloadedBytesCounter implements IDownloadedBytesCounter
{
	private int totalSizeData;
	
	public DownloadedBytesCounter()
	{
		this.totalSizeData = 0;
	}
	
	//Учесть sizeDownloadedBytes скачанных байтов
	public synchronized void onDataDownloaded(int sizeDownloadedBytes)
	{		
		totalSizeData += sizeDownloadedBytes;
	}
	
	//Вернуть общее количество посчитанных байтов
	public int getTotalSizeDownloadedData()
	{
		return totalSizeData;
	}
}
