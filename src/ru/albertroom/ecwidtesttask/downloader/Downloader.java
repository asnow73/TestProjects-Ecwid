package ru.albertroom.ecwidtesttask.downloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

// ласс дл€ скачивани€ данных из входного потока
public class Downloader implements IDownloader
{
	private IDownloadedBytesCounter downloadedBytesCounter; //подсчЄт скачанных байтов	
	private ISpeedController speedController; //контроль скорости скачивани€
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
	
	//—качать порцию байтов размером size
	private int downloadPortionBytes(int size) throws IOException
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
				downloadedBytesCounter.onDataDownloaded(result); //сообщить счетчику скачанных байтов о количестве скачанных байтов
			}
		}

		return result;
	}
	
	//ѕолучить зазиер порции байтов, которве можно скачать
	private int getSizeBuffer()
	{
		final int DEFAULT_SIZE_BUFFER = 3000;
		int size = DEFAULT_SIZE_BUFFER;
		if (speedController != null)
		{
			//спросить у контроллера скорости скачивани€ сколько байт можно скачать на данный момент
			size = speedController.getAllowBytesToDownload();
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
				result = downloadPortionBytes(allowBytesToDownload);
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
