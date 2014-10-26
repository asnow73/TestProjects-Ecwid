package ru.albertroom.ecwidtesttask.downloader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;
import ru.albertroom.ecwidtesttask.downloader.services.Output;

public class FactoryThreadHttpDownloadTest {

	//@BeforeClass
    //public static void oneTimeSetUp()
	//{
    //	Output.switchOff();
    //}
	
	@Test
	public void testCanCreateThread()
	{
		Stack<LinkData> linksData = new Stack<LinkData>();		
		IDownloadedBytesEvent mockByteCounter = mock(IDownloadedBytesEvent.class);
		ISpeedController mockSpeedControll = mock(ISpeedController.class);
		
		FactoryThreadHttpDownload factory = new FactoryThreadHttpDownload(linksData, "saveFolder", mockByteCounter, mockSpeedControll);
		assertEquals(false, factory.canCreateThread());
		
		linksData.add(mock(LinkData.class));
		assertEquals(true, factory.canCreateThread());
	}
	
	/*
	@Test
	public void testMakeThreadDownload()
	{
		Stack<LinkData> linksData = new Stack<LinkData>();		
		IDownloadedBytesEvent mockByteCounter = mock(IDownloadedBytesEvent.class);
		ISpeedController mockSpeedControll = mock(ISpeedController.class);
		
		FactoryThreadHttpDownload factory = new FactoryThreadHttpDownload(linksData, "saveFolder", mockByteCounter, mockSpeedControll);
		//assertEquals(null, factory.makeThreadDownload());
		
		linksData.add(mock(LinkData.class));		
		//assertNotNull(factory.makeThreadDownload());
	}
	*/
}
