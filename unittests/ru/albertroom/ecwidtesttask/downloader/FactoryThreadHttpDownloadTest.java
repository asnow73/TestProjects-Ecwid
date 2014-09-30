package ru.albertroom.ecwidtesttask.downloader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Stack;

import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

public class FactoryThreadHttpDownloadTest {

	@Test
	public void testcanCreateThread()
	{
		Stack<LinkData> linksData = new Stack<LinkData>();		
		IDownloadedBytesCounter mockByteCounter = mock(IDownloadedBytesCounter.class);
		ISpeedController mockSpeedControll = mock(ISpeedController.class);
		
		FactoryThreadHttpDownload factory = new FactoryThreadHttpDownload(linksData, "saveFolder", mockByteCounter, mockSpeedControll);
		assertEquals(false, factory.canCreateThread());
		
		linksData.add(mock(LinkData.class));
		assertEquals(true, factory.canCreateThread());
	}
	
	@Test
	public void testMakeThreadDownload()
	{
		Stack<LinkData> linksData = new Stack<LinkData>();		
		IDownloadedBytesCounter mockByteCounter = mock(IDownloadedBytesCounter.class);
		ISpeedController mockSpeedControll = mock(ISpeedController.class);
		
		FactoryThreadHttpDownload factory = new FactoryThreadHttpDownload(linksData, "saveFolder", mockByteCounter, mockSpeedControll);
		assertEquals(null, factory.makeThreadDownload());
		
		linksData.add(mock(LinkData.class));		
		assertNotNull(factory.makeThreadDownload());
	}
}
