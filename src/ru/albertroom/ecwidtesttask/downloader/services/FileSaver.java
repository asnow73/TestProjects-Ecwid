package ru.albertroom.ecwidtesttask.downloader.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.albertroom.ecwidtesttask.downloader.IDownloadSave;

//Класс для сохранения массива байтов в файл(ы)
public class FileSaver implements IDownloadSave
{
	private String[] fileNames; //имена файлов, под которыми надо сохранить массив байтов 
	public FileSaver(String[] fileNames)
	{
		this.fileNames = fileNames;
	}

	//Сохранить байты
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
