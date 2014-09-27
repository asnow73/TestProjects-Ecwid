package ru.albertroom.ecwidtesttask;

import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.Downloader;
import ru.albertroom.ecwidtesttask.downloader.IFactoryThreadDownload;
import ru.albertroom.ecwidtesttask.downloader.ThreadDownload;
import ru.albertroom.ecwidtesttask.downloader.services.FileSaver;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;


public class MockFactoryThreadDownload implements IFactoryThreadDownload
{
	//private int number;	
	private IDownloadedBytesCounter bytesCounter;
	private ISpeedController speedControll;
	private Stack<InputStream> linksData;
	
	public MockFactoryThreadDownload(Stack<InputStream> linksData, IDownloadedBytesCounter bytesCounter, ISpeedController speedControll)
	{
		//this.number = 0;
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
			//number++;
			InputStream mockInStream = linksData.pop();
			FileSaver mockSaver =  mock(FileSaver.class);
			Downloader downloader = new Downloader(mockInStream, bytesCounter, speedControll);
			result = new ThreadDownload(downloader, "threadname", mockSaver);
		}
		return result;
	}
}