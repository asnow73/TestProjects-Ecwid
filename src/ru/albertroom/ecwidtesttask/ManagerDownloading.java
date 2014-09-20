package ru.albertroom.ecwidtesttask;

import java.util.*; 
import ru.albertroom.ecwidtesttask.downloader.HttpDownloader;
import ru.albertroom.ecwidtesttask.downloader.ThreadDownload;
import ru.albertroom.ecwidtesttask.downloader.services.FileSaver;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;
import ru.albertroom.ecwidtesttask.downloader.LinkData;

//Class to manage the downloading
public class ManagerDownloading
{
	private int numberFilesForDownloading;	
	private ArrayList<ThreadDownload> threads;
	private int numberOfThreads;
	private Stack<LinkData> linksData; //information about link
	
	private IDownloadedBytesCounter bytesCounter;
	private ISpeedController speedControll;
	
	public ManagerDownloading(int numberOfThreads, Stack<LinkData> linksData)
	{
		this.numberFilesForDownloading = 0;
		this.threads = new ArrayList<ThreadDownload>(numberOfThreads);
		this.numberOfThreads = numberOfThreads;
		this.linksData = linksData;
		this.bytesCounter = null;
		this.speedControll = null;
	}
	
	public ManagerDownloading(int numberOfThreads, Stack<LinkData> linksData, IDownloadedBytesCounter bytesCounter, ISpeedController speedControll)
	{
		this(numberOfThreads, linksData);
		this.bytesCounter = bytesCounter;
		this.speedControll = speedControll;
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
		int num = 0;
		numberFilesForDownloading = linksData.size();

		while (!isDownloadFinished())
		{
			if (canCreateNewThread())
			{
				LinkData linkForDownload = linksData.pop();
				
				HttpDownloader downloader = new HttpDownloader(linkForDownload.getLink(), bytesCounter, speedControll);				
				FileSaver saver = new FileSaver(linkForDownload.getSaveAsNames()); //for saving downloaded files				
				ThreadDownload thread = new ThreadDownload(downloader, "thread #" + String.valueOf(num), saver);				

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
	}
}
