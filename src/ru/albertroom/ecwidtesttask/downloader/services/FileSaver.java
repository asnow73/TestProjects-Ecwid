package ru.albertroom.ecwidtesttask.downloader.services;

import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.albertroom.ecwidtesttask.downloader.IDownloadSave;

//����� ��� ���������� ������� ������ � ����(�)
public class FileSaver implements IDownloadSave
{
	private String[] fileNames; //����� ������, ��� �������� ���� ��������� ������ ������
	private String folder; //��� �����, � ������� ��������� �����
	
	public FileSaver(String[] fileNames, String folder)
	{
		this.fileNames = fileNames;
		this.folder = folder;
	}
	
	private String makeDir()
	{
		String result = "";		
		result = System.getProperty("user.dir") + File.separator + folder;
		File directory = new File(result);
		if (!directory.exists())
		{			
			directory.mkdir();
		}
		return result;
	}

	//��������� �����
	@Override
	public void save(byte[] bytes)
	{
		String path = makeDir();
		for (String filename : fileNames)
		{
			FileOutputStream file;
			filename = path + File.separator + filename;
			try
			{
				file = new FileOutputStream(filename);
				file.write(bytes);
				file.flush();
				file.close();
			}
			catch (IOException e)
			{
				Output.println("Can not create file '" + path + "'");
			}

		}
	}
}
