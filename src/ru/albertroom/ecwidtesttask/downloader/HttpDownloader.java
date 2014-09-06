package ru.albertroom.ecwidtesttask.downloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadingHandler;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

//Class to download the file from the link
public class HttpDownloader implements IDownloader
{
	private String urlForDownload;
	private IDownloadingHandler downloadedBytesCounter; //counting downloaded bytes
	private ISpeedController speedController; //controll the downloading speed
	
	private URL source;
	private BufferedInputStream inStream;
	private ByteArrayOutputStream outStream;
	
	public HttpDownloader(String url)
	{
		this.urlForDownload = url;
		this.downloadedBytesCounter = null;
		this.speedController = null;
	}
	
	//Set the service to counting downloaded bytes
	public void setDownloadedBytesCounter(IDownloadingHandler bytesCounter)
	{
		downloadedBytesCounter = bytesCounter;
	}
	
	//Set the service to controll the downloading speed
	public void setSpeedController(ISpeedController speedControll)
	{
		speedController = speedControll;
	}
	
	private void initStreams() throws IOException
	{
		source = new URL(urlForDownload);
		inStream = new BufferedInputStream(source.openStream());
		outStream = new ByteArrayOutputStream();
	}
	
	private void saveFile() throws IOException
	{
		outStream.flush();
	}
	
	private void closeStreams() throws IOException
	{
		inStream.close();
		outStream.close();
	}
	
	//Download the portion of bytes
	private int getBytes(int size) throws IOException
	{
		final int START_BUFFER_OFFSET = 0;
		int result = 0;		
		byte[] data = new byte[size];
		
		result = inStream.read(data);
		if (result >= 0)
		{
			outStream.write(data, START_BUFFER_OFFSET, result);
			if (downloadedBytesCounter != null)
			{
				downloadedBytesCounter.onDataDownloaded(result); //report about downloaded bytes
			}
		}

		return result;
	}
		
	private int getSizeBuffer()
	{
		final int DEFAULT_SIZE_BUFFER = 3000;
		int size = DEFAULT_SIZE_BUFFER;
		if (speedController != null)
		{
			size = speedController.getAllowBytesToDownload(); //ask how many bytes allow to download
		}
		return size;
	}

	@Override
	public byte[] download()
	{
		int result = 0;
		try
		{
			initStreams();
			while (result >= 0)
			{
				int allowBytesToDownload = getSizeBuffer();			
				result = getBytes(allowBytesToDownload);
			}
			saveFile();
			closeStreams();
		}
		catch (IOException e)
		{
			System.out.println("Error downloading ");
		}
		return outStream.toByteArray();
	}

}
