package ru.albertroom.ecwidtesttask.downloader;

import java.io.InputStream;
import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.services.FileSaver;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;
import ru.albertroom.ecwidtesttask.downloader.services.ProgressVisualisator;

public class FactoryThreadDownload implements IFactoryThreadDownload
{
	private int number;	
	private IDownloadedBytesEvent bytesCounter;
	private ISpeedController speedControll;
	private Stack<LinkData> linksData; //стек ссылок для скачивания
	private String saveFolder; //директория, в которой сохраняются скачанные файлы
	IFactoryConnection connectionMaker;
	
	private ProgressVisualisator progressVisualizator;
	
	public FactoryThreadDownload(Stack<LinkData> linksData, IFactoryConnection connectionMaker, String saveFolder, IDownloadedBytesEvent bytesCounter, ISpeedController speedControll)
	{
		this.number = 0;
		this.bytesCounter = bytesCounter;
		this.speedControll = speedControll;
		this.linksData = linksData;
		this.saveFolder = saveFolder;
		this.connectionMaker = connectionMaker;
		this.progressVisualizator = new ProgressVisualisator();
	}
	
	//Возможно ли создание потока для скачивания
	public boolean canCreateThread()
	{
		return (!linksData.empty());
	}
	
	public Thread makeThreadDownload() throws Exception
	{
		ThreadDownload thread = null;
		if (canCreateThread())
		{
			number++;
			LinkData linkForDownload = linksData.pop();
			InputStream inStream = connectionMaker.makeConnection(linkForDownload.getLink());			
			Downloader downloader = new Downloader(inStream, bytesCounter, speedControll, progressVisualizator);			
			
			FileSaver saver = new FileSaver(linkForDownload.getSaveAsNames(), saveFolder);
			thread = new ThreadDownload(downloader, "thread #" + String.valueOf(number), saver);
		}
		return thread;
	}
}
