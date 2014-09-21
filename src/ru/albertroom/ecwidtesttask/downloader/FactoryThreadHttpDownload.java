package ru.albertroom.ecwidtesttask.downloader;

import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.services.FileSaver;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

public class FactoryThreadHttpDownload implements IFactoryThreadDownload
{
	private int number;	
	private IDownloadedBytesCounter bytesCounter;
	private ISpeedController speedControll;
	private Stack<LinkData> linksData; //information about link
	
	public FactoryThreadHttpDownload(Stack<LinkData> linksData, IDownloadedBytesCounter bytesCounter, ISpeedController speedControll)
	{
		this.number = 0;
		this.bytesCounter = bytesCounter;
		this.speedControll = speedControll;
		this.linksData = linksData;
	}
	
	public boolean canCreateThread()
	{
		return (!linksData.empty());
	}
	
	public ThreadDownload makeThreadDownload()
	{
		ThreadDownload result = null;
		if (canCreateThread())
		{
			number++;
			LinkData linkForDownload = linksData.pop();
			HttpDownloader downloader = new HttpDownloader(linkForDownload.getLink(), bytesCounter, speedControll);
			FileSaver saver = new FileSaver(linkForDownload.getSaveAsNames()); //for saving downloaded files
			result = new ThreadDownload(downloader, "thread #" + String.valueOf(number), saver);
		}
		return result;
	}
}
