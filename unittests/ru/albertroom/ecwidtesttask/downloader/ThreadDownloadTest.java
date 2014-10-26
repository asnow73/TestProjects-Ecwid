package ru.albertroom.ecwidtesttask.downloader;

import org.junit.BeforeClass;
import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.services.FileSaver;
import ru.albertroom.ecwidtesttask.downloader.services.Output;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ThreadDownloadTest
{
	@BeforeClass
    public static void oneTimeSetUp()
	{
    	Output.switchOff();
    }
	
	@Test
	public void testThreadDownloadingRun()
	{
		FileSaver mockedSave = mock(FileSaver.class);
		HttpDownloader mockedDownloader = mock(HttpDownloader.class);
		ThreadDownload thread = new ThreadDownload(mockedDownloader, "threadName", mockedSave);
		thread.start();
		try {
			thread.join();
		}
		catch (InterruptedException e)
		{
			fail("Thread " + thread.getName() + "was interrupted");
		}
		byte[] bytes;
		try {
			bytes = verify(mockedDownloader).download();
			verify(mockedDownloader, times(1)).download();
			verify(mockedSave).save(bytes);
			verify(mockedSave, times(1)).save(bytes);
		}
		catch (Exception e)
		{
			fail("testThreadDownloadingRun fail");
		}
		
	}

}
