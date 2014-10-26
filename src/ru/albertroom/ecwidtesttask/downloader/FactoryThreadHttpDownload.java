package ru.albertroom.ecwidtesttask.downloader;

import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.services.FileSaver;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;
import ru.albertroom.ecwidtesttask.downloader.services.ProgressVisualisator;

public class FactoryThreadHttpDownload implements IFactoryThreadDownload
{
	private int number;	
	private IDownloadedBytesEvent bytesCounter;
	private ISpeedController speedControll;
	private Stack<LinkData> linksData; //стек ссылок для скачивания
	private String saveFolder; //директория, в которой сохраняются скачанные файлы
	
	private ProgressVisualisator progressVisualizator;
	
	public FactoryThreadHttpDownload(Stack<LinkData> linksData, String saveFolder, IDownloadedBytesEvent bytesCounter, ISpeedController speedControll)
	{
		this.number = 0;
		this.bytesCounter = bytesCounter;
		this.speedControll = speedControll;
		this.linksData = linksData;
		this.saveFolder = saveFolder;
		this.progressVisualizator = new ProgressVisualisator();
	}
	
	//Возможно ли создание потока для скачивания
	public boolean canCreateThread()
	{
		return (!linksData.empty());
	}
	
	public Thread makeThreadDownload() throws Exception
	{
		ThreadDownload result = null;
		if (canCreateThread())
		{
			number++;
			LinkData linkForDownload = linksData.pop();
			HttpDownloader downloader = new HttpDownloader(linkForDownload.getLink(), bytesCounter, speedControll, progressVisualizator);			
			FileSaver saver = new FileSaver(linkForDownload.getSaveAsNames(), saveFolder);
			result = new ThreadDownload(downloader, "thread #" + String.valueOf(number), saver);
		}
		return result;
	}
}
