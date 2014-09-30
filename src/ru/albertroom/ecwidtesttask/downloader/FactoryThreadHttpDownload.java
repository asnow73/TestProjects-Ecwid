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
	private Stack<LinkData> linksData; //стек ссылок для скачивания
	private String saveFolder; //директория, в которой
	
	public FactoryThreadHttpDownload(Stack<LinkData> linksData, String saveFolder, IDownloadedBytesCounter bytesCounter, ISpeedController speedControll)
	{
		this.number = 0;
		this.bytesCounter = bytesCounter;
		this.speedControll = speedControll;
		this.linksData = linksData;
		this.saveFolder = saveFolder;
	}
	
	//Возможно ли создание потока для скачивания
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
			FileSaver saver = new FileSaver(linkForDownload.getSaveAsNames(), saveFolder);
			result = new ThreadDownload(downloader, "thread #" + String.valueOf(number), saver);
		}
		return result;
	}
}
