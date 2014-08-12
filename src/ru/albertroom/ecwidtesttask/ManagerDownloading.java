package ru.albertroom.ecwidtesttask;

public class ManagerDownloading
{
	public ManagerDownloading()
	{
	}
	
	public void startDownloading()
	{
		/*HttpDownloader2 downloader0 = new HttpDownloader2("http://nevseoboi.com.ua/uploads/posts/2011-06/1307722487_62_www.nevseoboi.com.ua.jpg", "city.jpg");
		downloader0.download();
		System.out.println("Downloaded");*/
		
		Timer timer = new Timer();
		DownloadedBytesCounter bytesCounter = new DownloadedBytesCounter();
		SpeedController speedControll = new SpeedController(100000);
		
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
			thread0.join();
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
		System.out.println("Downloaded " + String.valueOf(bytesCounter.getTotalSizeDownloadedData()) + " bytes" );
	}
}
