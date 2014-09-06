package ru.albertroom.ecwidtesttask;

import java.util.*; 

import ru.albertroom.ecwidtesttask.downloader.FileSaver;
import ru.albertroom.ecwidtesttask.downloader.HttpDownloader;
import ru.albertroom.ecwidtesttask.downloader.ThreadDownload;
import ru.albertroom.ecwidtesttask.downloader.services.DownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.SpeedController;
import ru.albertroom.ecwidtesttask.time.Timer;
import ru.albertroom.ecwidtesttask.downloader.LinkData;

//Class to manage the downloading
public class ManagerDownloading
{
	private int numberFilesForDownloading;	
	private ArrayList<ThreadDownload> threads;
	private int numberOfThreads;
	private int downloadingSpeed;
	Stack<LinkData> linksData; //information about link
	
	public ManagerDownloading(int numberOfThreads, int downloadingSpeed, Stack<LinkData> linksData)
	{
		this.numberFilesForDownloading = 0;
		this.threads = new ArrayList<ThreadDownload>(numberOfThreads);
		this.numberOfThreads = numberOfThreads;
		this.downloadingSpeed = downloadingSpeed;
		this.linksData = linksData;
	}
	
	private boolean isDownloadFinished() 
	{
		return (numberFilesForDownloading == 0);
	}
	
	private boolean canCreateNewThread()
	{
		return ((threads.size() < numberOfThreads) && (!linksData.empty()));
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
	
	public void startDownloading()
	{
		Timer timer = new Timer();
		DownloadedBytesCounter bytesCounter = new DownloadedBytesCounter();  //counting downloaded bytes
		SpeedController speedControll = new SpeedController(downloadingSpeed);
		
		int num = 0;
		numberFilesForDownloading = linksData.size();
		
		timer.start();
		speedControll.start();

		while (!isDownloadFinished())
		{
			if (canCreateNewThread())
			{
				LinkData linkForDownload = linksData.pop();
				HttpDownloader downloader = new HttpDownloader(linkForDownload.getLink());				
				FileSaver saver = new FileSaver(linkForDownload.getSaveAsNames()); //for saving downloaded files
				
				ThreadDownload thread = new ThreadDownload(downloader, "thread #" + String.valueOf(num), saver);				
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
				Thread.yield();
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
