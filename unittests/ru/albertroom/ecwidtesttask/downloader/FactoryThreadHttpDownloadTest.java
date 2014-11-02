package ru.albertroom.ecwidtesttask.downloader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.IFactorySaver;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

public class FactoryThreadHttpDownloadTest
{
	private IDownloadedBytesEvent mockByteCounter;
	private ISpeedController mockSpeedControll;
	private IFactoryConnection connectionMakerMock;
	private IFactorySaver saverMock;
	
	@Before
	public void setUp()
	{
		mockByteCounter = mock(IDownloadedBytesEvent.class);
		mockSpeedControll = mock(ISpeedController.class);
		saverMock = mock(IFactorySaver.class);
		connectionMakerMock = mock(IFactoryConnection.class);
		try {
			MockInputStream mm = new MockInputStream();
			doReturn(mm).when(connectionMakerMock).makeConnection(anyString());
		}
		catch (Exception e1)
		{
			fail( "FactoryThreeadHttpDownloadTest failed" );
		}
    }
	
	@Test
	public void testCanCreateThread()
	{
		Stack<LinkData> linksData = new Stack<LinkData>();		
		FactoryThreadDownload factory = new FactoryThreadDownload(linksData, connectionMakerMock, saverMock, mockByteCounter, mockSpeedControll);

		assertEquals(false, factory.canCreateThread());
		
		linksData.add(mock(LinkData.class));
		assertEquals(true, factory.canCreateThread());
	}
	
	
	@Test
	public void testMakeThreadDownload()
	{
		Stack<LinkData> linksData = new Stack<LinkData>();		
		FactoryThreadDownload factory = new FactoryThreadDownload(linksData, connectionMakerMock, saverMock, mockByteCounter, mockSpeedControll);

		try {
			assertEquals(null, factory.makeThreadDownload());
		} catch (Exception e) {
			fail( "testMakeThreadDownload failed" );
		}
		
		linksData.add(mock(LinkData.class));		
		try {
			assertNotNull(factory.makeThreadDownload());
		} catch (Exception e) {
			fail( "testMakeThreadDownload failed" );
		}
		
	}
	
}
