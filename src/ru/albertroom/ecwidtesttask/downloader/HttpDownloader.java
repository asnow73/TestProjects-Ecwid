package ru.albertroom.ecwidtesttask.downloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

//Класс для скачивания файла по URL ссылке
public class HttpDownloader implements IDownloader
{
	private Downloader downloader;
	private URL source;
	private BufferedInputStream inStream;
	
	private void init(String url) throws IOException
	{
		source = new URL(url);
		inStream = new BufferedInputStream(source.openStream());
	}
	
	public HttpDownloader(String url) throws Exception
	{
		init(url);
		this.downloader = new Downloader(inStream);			
	}
	
	public HttpDownloader(String url, IDownloadedBytesEvent bytesCounter, ISpeedController speedControll, IDownloadedBytesEvent progressVisualisator) throws Exception
	{
		init(url);
		this.downloader = new Downloader(inStream, bytesCounter, speedControll, progressVisualisator);
	}
	
	@Override
	public byte[] download() throws Exception
	{	
		byte[] result = downloader.download();
		inStream.close();
		return result;
	}
}
