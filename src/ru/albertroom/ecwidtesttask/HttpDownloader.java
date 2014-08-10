
/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class HttpDownloader implements IDownloader
{
	private File fileForSave;
	private String urlForDownload;
	//private int downloadedBytes;
	private ArrayList<IDownloadingHandler> handlers;
	
	public HttpDownloader(String url, String pathToSave)
	{
		this.fileForSave = new File(pathToSave);
		this.urlForDownload = url;
		//this.downloadedBytes = 0;
		handlers = new ArrayList<IDownloadingHandler>();
	}
	
	public void addHandler(IDownloadingHandler handler)
	{
		handlers.add(handler);
	}
	
	/*public int getSizeOfDownloadedBytes()
	{
		return downloadedBytes;
	}*/
	
	@Override
	public void download()
	{
		try
		{
			URL source = new URL(urlForDownload);
			BufferedInputStream inStream = new BufferedInputStream(source.openStream());
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(fileForSave));			
			final int BUFFER_SIZE = 3000;
			final int START_BUFFER_OFFSET = 0;
			byte[] data = new byte[BUFFER_SIZE];
			int result = 0;
			
			while (result >= 0)
			{
				result = inStream.read(data);
				if (result >= 0)
				{
					if (result < BUFFER_SIZE)
					{
						outStream.write(data, START_BUFFER_OFFSET, result);
					}
					else
					{
						outStream.write(data);
					}
					//downloadedBytes += result;
					if (!handlers.isEmpty())
					{
						//observerDataCounter.notify(downloadedBytes);
						//observerDataCounter.onDataDownloaded(result);
						for (IDownloadingHandler handler : handlers)
						{
							handler.onDataDownloaded(result);
						}
					}
				}
			}
			outStream.flush();
			
			inStream.close();
			outStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
