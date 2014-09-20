package ru.albertroom.ecwidtesttask.downloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

class Downloader implements IDownloader
{
	private IDownloadedBytesCounter downloadedBytesCounter; //counting downloaded bytes
	private ISpeedController speedController; //controll the downloading speed
	private ByteArrayOutputStream outStream;
	private InputStream inStream;
	
	public Downloader(InputStream inStream)
	{
		this.inStream = inStream;
		this.outStream = new ByteArrayOutputStream();
		this.downloadedBytesCounter = null;
		this.speedController = null;
	}
	
	public Downloader(InputStream inStream, IDownloadedBytesCounter bytesCounter, ISpeedController speedControll)
	{
		this(inStream);
		this.downloadedBytesCounter = bytesCounter;
		this.speedController = speedControll;
	}
	
	//Download the portion of bytes
	private int downloadBytes(int size) throws IOException
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
			while (result >= 0)
			{
				int allowBytesToDownload = getSizeBuffer();			
				result = downloadBytes(allowBytesToDownload);
			}
			outStream.flush();
			outStream.close();
		}
		catch (IOException e)
		{
			System.out.println("Error downloading ");
		}
		return outStream.toByteArray();
	}
}
