package ru.albertroom.ecwidtesttask;

import java.util.*; 

import ru.albertroom.ecwidtesttask.downloader.HttpDownloader;
import ru.albertroom.ecwidtesttask.downloader.ThreadDownload;
import ru.albertroom.ecwidtesttask.downloader.services.DownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.SpeedController;
import ru.albertroom.ecwidtesttask.time.Timer;


public class ManagerDownloading
{
	private int numberFilesForDownloading;	
	private ArrayList<ThreadDownload> threads;
	private int numberOfThreads;
	private int downloadingSpeed;
	private Stack<String> stackOfDataSource;
	
	public ManagerDownloading(int numberOfThreads, int downloadingSpeed, Stack<String> stackOfDataSource)
	{
		this.numberFilesForDownloading = 0;
		this.threads = new ArrayList<ThreadDownload>(numberOfThreads);
		this.numberOfThreads = numberOfThreads;
		this.downloadingSpeed = downloadingSpeed;
		this.stackOfDataSource = stackOfDataSource;
	}
	
	private boolean isDownloadFinished() 
	{
		return (numberFilesForDownloading == 0);
	}
	
	private boolean canCreateNewThread()
	{
		return ((threads.size() < numberOfThreads) && (!stackOfDataSource.empty()));
	}
	
	private int removeFinishedThreads()
	{
		int result = 0;
		for (int i = 0; i < threads.size(); ++i)
		{
			if (threads.get(i).isAlive() == false)
			{
				threads.remove(i);			
				result++;
			}
		}
		return result;
	}
	
	private String getFileName(String link)
	{
		String result = "";
		int startIndexOfFileNmae = link.lastIndexOf('/')+1;
		
		if (startIndexOfFileNmae < link.length())
		{
			result = link.substring(link.lastIndexOf('/')+1);
		}
		
		return result;
	}
	
	public void startDownloading()
	{
		Timer timer = new Timer();
		DownloadedBytesCounter bytesCounter = new DownloadedBytesCounter();
		SpeedController speedControll = new SpeedController(downloadingSpeed);
		
		int num = 0;
		numberFilesForDownloading = stackOfDataSource.size();
		
		timer.start();
		speedControll.start();

		while (!isDownloadFinished())
		{
			if (canCreateNewThread())
			{
				String linkForDownload = stackOfDataSource.pop();
				HttpDownloader downloader = new HttpDownloader(linkForDownload, getFileName(linkForDownload));
				ThreadDownload thread = new ThreadDownload(downloader, "thread #" + String.valueOf(num));				
				downloader.setDownloadedBytesCounter(bytesCounter);
				downloader.setSpeedController(speedControll);
				
				num++;
				threads.add(thread);
				thread.start();
			}
			
			int numberOfFinishedThreads = removeFinishedThreads();
			numberFilesForDownloading -= numberOfFinishedThreads;
			try
			{
				Thread.sleep(250); //time pause between checking threads
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		timer.finish();
		System.out.println("Working time is " + String.valueOf(timer.getTotalTime()) + " ms" );
		System.out.println("Downloaded " + String.valueOf(bytesCounter.getTotalSizeDownloadedData()) + " bytes" );
	}
}
