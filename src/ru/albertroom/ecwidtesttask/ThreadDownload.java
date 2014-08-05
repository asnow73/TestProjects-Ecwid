/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

public class ThreadDownload implements Runnable
{
	private Thread worker;
	private IDownloader downloader;
	private String url;
	
	public ThreadDownload(IDownloader downloader, String url, String threadName)
	{
		this.worker = new Thread(this, threadName);
		this.downloader = downloader;
		this.url = url;
		System.out.println("Download " + threadName + " started.");
		worker.start();
	}
	
	public void run()
	{
		try
		{
			downloader.download(url);
		}
		catch (Exception e)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(worker.getName());
			sb.append(" falled down with exception: ");
			sb.append(e.getMessage());
			System.out.println(sb.toString());
		}
		System.out.println("Download " + worker.getName() + " finished.");
	}
}
