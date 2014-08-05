
/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import java.io.*;
import java.net.*;

public class Downloader implements IDownloader
{
	private File fileForSave;
	
	public Downloader(String pathToSave)
	{
		fileForSave = new File(pathToSave);
	}
	
	public void download(String url)
	{
		try
		{
			URL source = new URL(url);
			InputStream inStream = source.openStream();
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(fileForSave));
			while(true)
			{
				int bytesAvailable = inStream.available();
				if (bytesAvailable >= 0)
				{
					byte[] data = new byte[bytesAvailable];
					int result = inStream.read(data);
					if (result >= 0)
					{
						outStream.write(data);
					}
					else
					{
						outStream.flush();
						break;
					}
				}
			}
			inStream.close();
			outStream.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
