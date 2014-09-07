package ru.albertroom.ecwidtesttask.readlinkslist;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.LinkData;

public class ReaderLinksInfoTest {

	@Test
	public void testRead_withException()
	{
		ReaderLinksInfo downloadInfo = new ReaderLinksInfo();
		String[] lines = {"http://htmlbook.ru/files/images/blog/triangle-2.png"};
		ArrayStringLinksDataSource dataSource = new ArrayStringLinksDataSource(lines);
		
		try
		{
			downloadInfo.read(dataSource);
			fail( "Method 'read' threw exception, but he did not should do this" );
		} 
		catch (UncorrectLinkException e)
		{
		}
		catch (IOException e)
		{
		}
	}

	@Test
	public void testRead()
	{
		ReaderLinksInfo downloadInfo = new ReaderLinksInfo();
		String[] lines = new String[4];
		
		lines[0] = "http://htmlbook.ru/files/images/blog/triangle-2.png 2.png";
		lines[1] = "http://htmlbook.ru/files/images/blog/triangle-2.png 2.png";
		lines[2] = "http://htmlbook.ru/files/images/blog/triangle-2.png 5.png";
		lines[3] = "http://aquasantop.ru/img/catphotos/68/2(218).jpg 3.jpg";
		
		ArrayStringLinksDataSource dataSource = new ArrayStringLinksDataSource(lines);
		
		try
		{
			Stack<LinkData> linksData = downloadInfo.read(dataSource);
			assertEquals(2, linksData.size());
			assertEquals("http://htmlbook.ru/files/images/blog/triangle-2.png", linksData.elementAt(0).getLink() );
			assertEquals(2, linksData.elementAt(0).getSaveAsNames().length );
			Arrays.sort(linksData.elementAt(0).getSaveAsNames());
			assertEquals("2.png", linksData.elementAt(0).getSaveAsNames()[0] );
			assertEquals("5.png", linksData.elementAt(0).getSaveAsNames()[1] );
			
			assertEquals("http://aquasantop.ru/img/catphotos/68/2(218).jpg", linksData.elementAt(1).getLink() );
			assertEquals(1, linksData.elementAt(1).getSaveAsNames().length );
			assertEquals("3.jpg", linksData.elementAt(1).getSaveAsNames()[0] );
		} 
		catch (UncorrectLinkException e)
		{
		}
		catch (IOException e)
		{
		}
	}
}
