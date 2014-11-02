package ru.albertroom.ecwidtesttask.downloader;

import ru.albertroom.ecwidtesttask.downloader.services.Output;

//Класс, реализующий поток для скачивания
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
		Output.println("Download " + getName() + " started.");		
		super.start();
	}
	
	@Override
	public void run()
	{
		try
		{
			byte[] bytes = downloader.download();
			saver.save(bytes);
			Output.println("Download " + getName() + " finished.");
		}
		catch (Exception e)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(getName());
			sb.append(" falled down with exception: ");
			sb.append(e.getMessage());
			Output.println(sb.toString());
		}
	}
}
