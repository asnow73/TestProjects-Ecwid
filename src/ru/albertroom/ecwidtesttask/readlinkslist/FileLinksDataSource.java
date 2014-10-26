package ru.albertroom.ecwidtesttask.readlinkslist;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ru.albertroom.ecwidtesttask.downloader.services.Output;

//����� ��� ������ �����, ����������� ������ ��� ���������� �������
public class FileLinksDataSource implements IDataLinksSource
{
	private BufferedReader file;
	
	public FileLinksDataSource(String pathToFile) throws FileNotFoundException
	{
		try
		{
			file = new BufferedReader(new FileReader(pathToFile));
		}
		catch (FileNotFoundException e)
		{
			Output.println("Error. Can't open the file.");
			throw e;
		}
	}

	//��������� ������ �� �����
	@Override
	public String readLine() throws IOException
	{
		String result = null;
		try
		{
			result = file.readLine();
		} 
		catch (IOException e)
		{
			Output.println("Error of reading data");
			throw e;
		}
		return result;
	}
	
	public void close() throws IOException
	{
		file.close();
	}
}
