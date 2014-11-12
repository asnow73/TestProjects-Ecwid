package ru.albertroom.ecwidtesttask.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.services.IFactorySaver;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;
import ru.albertroom.ecwidtesttask.downloader.services.ProgressVisualisator;

public class FactoryThreadDownload implements IFactoryThreadDownload
{
	private int number;	
	private IDownloadedBytesEvent bytesCounter; //подсчёт скачанных байтов
	private ISpeedController speedControll; //контроль скорости скачивания
	private Stack<LinkData> linksData; //стек ссылок для скачивания
	private IFactorySaver factorySaver; //фабрика по созданию объектов, сохраняющих скачанные файлы
	private IFactoryConnection connectionMaker; //фабрика по созданию входных потоков
	
	private ProgressVisualisator progressVisualizator;
	
	public FactoryThreadDownload(Stack<LinkData> linksData, IFactoryConnection connectionMaker, IFactorySaver factorySaver, IDownloadedBytesEvent bytesCounter, ISpeedController speedControll)
	{
		this.number = 0;
		this.bytesCounter = bytesCounter;
		this.speedControll = speedControll;
		this.linksData = linksData;
		this.factorySaver = factorySaver;
		this.connectionMaker = connectionMaker;
		this.progressVisualizator = new ProgressVisualisator();
	}
	
	//Возможно ли создание потока для скачивания
	public boolean canCreateThread()
	{
		return (!linksData.empty());
	}
	
	public Thread makeThreadDownload() throws MalformedURLException, IOException
	{
		ThreadDownload thread = null;
		if (canCreateThread())
		{
			number++;
			LinkData linkForDownload = linksData.pop();
			InputStream inStream = connectionMaker.makeConnection(linkForDownload.getLink());			
			Downloader downloader = new Downloader(inStream, bytesCounter, speedControll, progressVisualizator);			
			IDownloadSave saver = factorySaver.makeSaver(linkForDownload.getSaveAsNames());
			thread = new ThreadDownload(downloader, "thread #" + String.valueOf(number), saver);
		}
		return thread;
	}
}
