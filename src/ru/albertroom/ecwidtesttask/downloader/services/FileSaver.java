package ru.albertroom.ecwidtesttask.downloader.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.albertroom.ecwidtesttask.downloader.IDownloadSave;

//Class to save array of bytes to file(s)
public class FileSaver implements IDownloadSave
{
	private String[] fileNames;
	public FileSaver(String[] fileNames)
	{
		this.fileNames = fileNames;
	}

	@Override
	public void save(byte[] bytes)
	{
		for (String filename : fileNames)
		{
			FileOutputStream file;
			try
			{
				file = new FileOutputStream(filename);
				file.write(bytes);
				file.flush();
				file.close();
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Can not create file '" + filename + "'");
			}
			catch (IOException e)
			{
				System.out.println("Can not create file '" + filename + "'");
			}

		}
	}
}
