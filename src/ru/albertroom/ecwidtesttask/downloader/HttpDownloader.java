package ru.albertroom.ecwidtesttask.downloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadingHandler;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

//Class to download the file from the link
public class HttpDownloader implements IDownloader
{
	private Downloader downloader;
	private URL source;
	private BufferedInputStream inStream;
	
	public HttpDownloader(String url)
	{
		try
		{
			source = new URL(url);
			inStream = new BufferedInputStream(source.openStream());
			this.downloader = new Downloader(inStream);
		}
		catch (IOException e)
		{
			System.out.println("Error downloading ");
		}				
	}
	
	//Set the service to counting downloaded bytes
	public void setDownloadedBytesCounter(IDownloadingHandler bytesCounter)
	{
		downloader.setDownloadedBytesCounter(bytesCounter);
	}
	
	//Set the service to controll the downloading speed
	public void setSpeedController(ISpeedController speedControll)
	{
		downloader.setSpeedController(speedControll);
	}
	
	@Override
	public byte[] download()
	{	
		byte[] result = downloader.download();
		try {
			inStream.close();
		}
		catch (IOException e)
		{
			System.out.println("Error downloading ");
		}
		return result;
	}
}
