package ru.albertroom.ecwidtesttask.downloader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
//import static org.mockito.Matchers.*;
//import static org.mockito.Mockito.*;

import java.util.Stack;

//import org.junit.BeforeClass;
import org.junit.Test;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;

import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;

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
		
		/*FactoryHttpConnection connectionMakerMock = mock(FactoryHttpConnection.class);
		//when(connectionMakerMock.makeConnection(anyString())).thenReturn(new MockInputStream());
		try {
			when(connectionMakerMock.makeConnection(anyString())).thenAnswer(new Answer<MockInputStream>(){
			    @Override
			    public MockInputStream answer(InvocationOnMock invocation) throws Throwable {
			      return new MockInputStream();
			    }
			  });
		} catch (Exception e) {
			fail( "testCanCreateThread failed" );
		}*/
		MockFactoryHttpConnection connectionMakerMock = new MockFactoryHttpConnection();
		FactoryThreadDownload factory = new FactoryThreadDownload(linksData, connectionMakerMock, "saveFolder", mockByteCounter, mockSpeedControll);
		assertEquals(false, factory.canCreateThread());
		
		linksData.add(mock(LinkData.class));
		assertEquals(true, factory.canCreateThread());
	}
	
	
	@Test
	public void testMakeThreadDownload()
	{
		Stack<LinkData> linksData = new Stack<LinkData>();		
		IDownloadedBytesEvent mockByteCounter = mock(IDownloadedBytesEvent.class);
		ISpeedController mockSpeedControll = mock(ISpeedController.class);
				
		MockFactoryHttpConnection connectionMakerMock = new MockFactoryHttpConnection();
		FactoryThreadDownload factory = new FactoryThreadDownload(linksData, connectionMakerMock, "saveFolder", mockByteCounter, mockSpeedControll);

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
