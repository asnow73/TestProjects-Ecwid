package ru.albertroom.ecwidtesttask;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ru.albertroom.ecwidtesttask.downloader.FactoryThreadDownload;
import ru.albertroom.ecwidtesttask.downloader.IDownloadSave;
import ru.albertroom.ecwidtesttask.downloader.IFactoryConnection;
import ru.albertroom.ecwidtesttask.downloader.LinkData;
import ru.albertroom.ecwidtesttask.downloader.MockInputStream;
import ru.albertroom.ecwidtesttask.downloader.services.DownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.IDownloadedBytesEvent;
import ru.albertroom.ecwidtesttask.downloader.services.IFactorySaver;
import ru.albertroom.ecwidtesttask.downloader.services.ISpeedController;
import ru.albertroom.ecwidtesttask.downloader.services.Output;
import ru.albertroom.ecwidtesttask.downloader.services.SpeedController;
import ru.albertroom.ecwidtesttask.time.Chronometer;


public class ManagerDownloadingTest
{
	private Stack<LinkData> linksData;
	private IFactorySaver factorySaver;
	private IFactoryConnection connectionMakerMock;

	@BeforeClass
    public static void oneTimeSetUp()
	{
		Output.switchOff();
    }
	
	@Before
	public void setUp()
	{
		linksData = new Stack<LinkData>();
		String m[] = {""};
		linksData.add(new LinkData("", m));
		linksData.add(new LinkData("", m));
		linksData.add(new LinkData("", m));
		
		IDownloadSave saver = mock(IDownloadSave.class);
		factorySaver = mock(IFactorySaver.class);
		try {
			doReturn(saver).when(factorySaver).makeSaver((String[]) anyObject());
		}
		catch (Exception e1)
		{
			fail( "FactoryThreeadHttpDownloadTest failed" );
		}
		
		connectionMakerMock = mock(IFactoryConnection.class);
		try {
			doAnswer(new Answer() {
			      public MockInputStream answer(InvocationOnMock invocation)
			      {
			    	  return new MockInputStream();
			      }})
			  .when(connectionMakerMock).makeConnection(anyString());
		}
		catch (Exception e1)
		{
			fail( "FactoryThreeadHttpDownloadTest failed" );
		}
    }
	
	//проверка на то, что программа не качает больше x байт в период времени t	
	@Test
	public void testSpeedDownloading()
	{
		final int COUNT_THREADS = 2;
		final long TIME_PERIOD = 10000000;
		
		DownloadedBytesCounter bytesCounter = new DownloadedBytesCounter();
		//контроллер скорости с малым периодом времени //1 байт в 1/100 с
		SpeedController speedControll = new SpeedController(1, new Chronometer(TIME_PERIOD));
		
		FactoryThreadDownload factory = new FactoryThreadDownload(linksData, connectionMakerMock, factorySaver, bytesCounter, speedControll);		
		ManagerDownloading manager = new ManagerDownloading(COUNT_THREADS, factory);
		
		long startTime = 0;
		startTime = System.nanoTime();
		
		try {
			manager.startDownloading();
		} catch (InterruptedException e) {
			fail( "testStartDownloading failed" );
		}

		long finishTime = 0;
		finishTime = System.nanoTime();
		
		int dowloadedBytes = bytesCounter.getTotalSizeDownloadedData();
		assertEquals(true, (finishTime - startTime)/(dowloadedBytes) > TIME_PERIOD );
	}
	
	//Проверка количесва активных потоков
	@Test
	public void testCountDownloadingThread()
	{
		final int COUNT_THREADS = 2;
		final long TIME_PERIOD = 10000000;
		
		IDownloadedBytesEvent bytesCounter = mock(IDownloadedBytesEvent.class);
		try {
			doAnswer(new Answer() {
			      public Object answer(InvocationOnMock invocation)
			      {
			    	  //Проверка количества потоков
			    	  assertEquals(true, Thread.activeCount() <= COUNT_THREADS);
			    	  return null;
			      }})
			  .when(connectionMakerMock).makeConnection(anyString());
		}
		catch (Exception e1)
		{
			fail( "FactoryThreeadHttpDownloadTest failed" );
		}
		
		//контроллер скорости с малым периодом времени //1 байт в 1/100 с
		SpeedController speedControll = new SpeedController(1, new Chronometer(TIME_PERIOD));
		FactoryThreadDownload factory = new FactoryThreadDownload(linksData, connectionMakerMock, factorySaver, bytesCounter, speedControll);		
		ManagerDownloading manager = new ManagerDownloading(COUNT_THREADS, factory);
				
		try 
		{
			manager.startDownloading();
		} 
		catch (InterruptedException e) 
		{
			fail( "testStartDownloading failed" );
		}
		
	}
}
