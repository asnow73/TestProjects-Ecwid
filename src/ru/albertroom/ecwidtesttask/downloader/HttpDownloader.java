package ru.albertroom.ecwidtesttask.downloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadingHandler;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

public class HttpDownloader implements IDownloader
{
	private File fileForSave;
	private String urlForDownload;
	private IDownloadingHandler downloadedBytesCounter;
	private ISpeedController speedController;
	
	private URL source;
	private BufferedInputStream inStream;
	private BufferedOutputStream outStream;
	
	public HttpDownloader(String url, String pathToSave)
	{
		this.fileForSave = new File(pathToSave);
		this.urlForDownload = url;
		this.downloadedBytesCounter = null;
		this.speedController = null;
	}
	
	public void setDownloadedBytesCounter(IDownloadingHandler bytesCounter)
	{
		downloadedBytesCounter = bytesCounter;
	}
	
	public void setSpeedController(ISpeedController speedControll)
	{
		speedController = speedControll;
	}
	
	private void initStreams() throws IOException
	{
		source = new URL(urlForDownload);
		inStream = new BufferedInputStream(source.openStream());
		outStream = new BufferedOutputStream(new FileOutputStream(fileForSave));
	}
	
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
				downloadedBytesCounter.onDataDownloaded(result);
			}
		}

		return result;
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
	
	private int getSizeBuffer()
	{
		final int DEFAULT_SIZE_BUFFER = 3000;
		int size = DEFAULT_SIZE_BUFFER;
		if (speedController != null)
		{
			size = speedController.getAllowBytesToDownload();
		}
		return size;
	}

	@Override
	public void download()
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
			e.printStackTrace();
		}
	}

}
