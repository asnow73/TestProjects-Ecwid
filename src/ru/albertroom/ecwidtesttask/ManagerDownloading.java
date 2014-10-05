package ru.albertroom.ecwidtesttask;

import java.util.*;

import ru.albertroom.ecwidtesttask.downloader.IFactoryThreadDownload;

//Класс для управления потоками закачек
public class ManagerDownloading
{	
	private ArrayList<Thread> threads;
	private int numberOfThreads;
	private IFactoryThreadDownload factoryThreads;
	
	public ManagerDownloading(int numberOfThreads, IFactoryThreadDownload factoryThreads)	
	{
		this.threads = new ArrayList<Thread>(numberOfThreads);
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
				Thread thread = factoryThreads.makeThreadDownload();
				threads.add(thread);
				thread.start();
			}
			removeFinishedThreads();
			//пауза между проверками потоков на завершённость
			Thread.sleep(250);
			Thread.yield();
		}
	}
}
