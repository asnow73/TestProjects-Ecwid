package ru.albertroom.ecwidtesttask.readlinkslist;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Stack;

import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.LinkData;

class ArrayStringLinksDataSource implements IDataLinksSource
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

public class ReaderLinksInfoTest {

	@Test
	public void testRead()
	{
		ReaderLinksInfo downloadInfo = new ReaderLinksInfo();
		String[] lines = {"aaa", "bbb"};
		ArrayStringLinksDataSource dataSource = new ArrayStringLinksDataSource(lines);
		
		try {
			Stack<LinkData> linksData = downloadInfo.read(dataSource);
		} catch (UncorrectLinkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
