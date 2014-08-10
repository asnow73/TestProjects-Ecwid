/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

public class ThreadDownload extends Thread
{
	private IDownloader downloader;
	
	public ThreadDownload(IDownloader downloader, String threadName)
	{
		setName(threadName);
		this.downloader = downloader;
	}
	
	@Override
	public void start()
	{
		System.out.println("Download " + getName() + " started.");
		super.start();
	}
	
	@Override
	public void run()
	{
		try
		{
			downloader.download();
		}
		catch (Exception e)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(getName());
			sb.append(" falled down with exception: ");
			sb.append(e.getMessage());
			System.out.println(sb.toString());
		}
		System.out.println("Download " + getName() + " finished.");
	}
}
