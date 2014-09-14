/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask.downloader;

//Class for downloading in the individual thread
public class ThreadDownload extends Thread
{
	private IDownloader downloader;
	private IDownloadSave saver;
	
	public ThreadDownload(IDownloader downloader, String threadName, IDownloadSave saver)
	{
		setName(threadName);
		this.downloader = downloader;
		this.saver = saver;
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
			byte[] bytes = downloader.download();
			saver.save(bytes); //saving downloaded bytes
			System.out.println("Download " + getName() + " finished.");
		}
		catch (Exception e)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(getName());
			sb.append(" falled down with exception: ");
			sb.append(e.getMessage());
			System.out.println(sb.toString());
		}
	}
}
