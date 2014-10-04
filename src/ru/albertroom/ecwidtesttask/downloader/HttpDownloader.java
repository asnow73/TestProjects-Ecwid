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
	
	private void init(String url)
	{
		try
		{
			source = new URL(url);
			inStream = new BufferedInputStream(source.openStream());
		}
		catch (IOException e)
		{
			System.out.println("Error downloading ");
		}	
	}
	
	public HttpDownloader(String url)
	{
		init(url);
		this.downloader = new Downloader(inStream);			
	}
	
	public HttpDownloader(String url, IDownloadedBytesEvent bytesCounter, ISpeedController speedControll, IDownloadedBytesEvent progressVisualisator)
	{
		init(url);
		this.downloader = new Downloader(inStream, bytesCounter, speedControll, progressVisualisator);
	}
	
	@Override
	public byte[] download()
	{	
		byte[] result = downloader.download();
		try
		{
			inStream.close();
		}
		catch (IOException e)
		{
			System.out.println("Error downloading ");
		}
		return result;
	}
}
