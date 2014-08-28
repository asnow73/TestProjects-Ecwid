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
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*for (int i = 0; i < numberOfThreads; ++i)
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
			}
			
			for (ThreadDownload thread : threads)
			{
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			int numberOfFinishedThreads = removeFinishedThreads();
			numberFilesForDownloading -= numberOfFinishedThreads;
			Thread.yield();*/
			
		}
		timer.finish();
		System.out.println("Working time is " + String.valueOf(timer.getTotalTime()) + " ms" );
		System.out.println("Downloaded " + String.valueOf(bytesCounter.getTotalSizeDownloadedData()) + " bytes" );

		
		/*
		HttpDownloader downloader0 = new HttpDownloader("http://nevseoboi.com.ua/uploads/posts/2011-06/1307722487_62_www.nevseoboi.com.ua.jpg", "city.jpg");
		HttpDownloader downloader1 = new HttpDownloader("http://htmlbook.ru/files/images/blog/triangle-2.png", "data.png");
		HttpDownloader downloader2 = new HttpDownloader("http://aquasantop.ru/img/catphotos/68/2(218).jpg", "kran.jpg");
		HttpDownloader downloader3 = new HttpDownloader("http://images.kika.com/db/1/a/8/17471898_2_z.jpg", "kran2.jpg");
		
		ThreadDownload thread0 = new ThreadDownload(downloader0, "thread #0");
		ThreadDownload thread1 = new ThreadDownload(downloader1, "thread #1");
		ThreadDownload thread2 = new ThreadDownload(downloader2, "thread #2");
		ThreadDownload thread3 = new ThreadDownload(downloader3, "thread #3");
		
		
		
		downloader0.setDownloadedBytesCounter(bytesCounter);
		downloader1.setDownloadedBytesCounter(bytesCounter);
		downloader2.setDownloadedBytesCounter(bytesCounter);
		downloader3.setDownloadedBytesCounter(bytesCounter);
		
		downloader0.setSpeedController(speedControll);
		downloader1.setSpeedController(speedControll);
		downloader2.setSpeedController(speedControll);
		downloader3.setSpeedController(speedControll);
		
		timer.start();
		speedControll.start();
		
		thread0.start();
		thread1.start();
		thread2.start();
		thread3.start();
		
		try {
			thread0.join(); //ждать пока поток не завершит своё выполнение
			thread1.join();
			thread2.join();
			thread3.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		timer.finish();
		
		System.out.println("Working time is " + String.valueOf(timer.getTotalTime()) + " ms" );
		System.out.println("Downloaded " + String.valueOf(bytesCounter.getTotalSizeDownloadedData()) + " bytes" );*/
	}
}
