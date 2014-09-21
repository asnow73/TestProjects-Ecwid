package ru.albertroom.ecwidtesttask.readlinkslist;

import java.io.IOException;

public class ArrayStringLinksDataSource implements IDataLinksSource
{
	private String[] lines;
	private int currentLine;
	
	public ArrayStringLinksDataSource(String[] lines)
	{
		this.lines = lines;
		currentLine = 0;
	}

	@Override
	public String readLine() throws IOException
	{
		String result = null;
		if (currentLine < lines.length)
		{
			result = lines[currentLine];
			currentLine++;
		}
		return result;
	}
}
