package ru.albertroom.ecwidtesttask;

import java.util.*;

import ru.albertroom.ecwidtesttask.downloader.IFactoryThreadDownload;
import ru.albertroom.ecwidtesttask.downloader.ThreadDownload;

//Class to manage the downloading
public class ManagerDownloading
{	
	private ArrayList<ThreadDownload> threads;
	private int numberOfThreads;
	private IFactoryThreadDownload factoryThreads;
	
	public ManagerDownloading(int numberOfThreads, IFactoryThreadDownload factoryThreads)	
	{
		this.threads = new ArrayList<ThreadDownload>(numberOfThreads);
		this.numberOfThreads = numberOfThreads;
		this.factoryThreads = factoryThreads;
	}
	
	private boolean isDownloadFinished() 
	{
		return ( (!factoryThreads.canCreateThread()) && (threads.size() == 0) );
	}
	
	private boolean canCreateNewThread()
	{
		return ((threads.size() < numberOfThreads) && (factoryThreads.canCreateThread()));
	}
	
	private void removeFinishedThreads()
	{
		for (int i = 0; i < threads.size(); ++i)
		{
			if (threads.get(i).isAlive() == false)
			{
				threads.remove(i);
			}
		}
	}
	
	public void startDownloading() throws InterruptedException
	{
		while (!isDownloadFinished())
		{
			if (canCreateNewThread())
			{
				ThreadDownload thread = factoryThreads.makeThreadDownload();
				threads.add(thread);
				thread.start();
			}
			removeFinishedThreads();
			Thread.sleep(250); //time pause between checking threads
			Thread.yield();
		}
	}
}
