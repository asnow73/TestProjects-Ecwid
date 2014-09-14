package ru.albertroom.ecwidtesttask.downloader;

import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.services.FileSaver;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ThreadDownloadTest
{
	@Test
	public void testRun()
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
		byte[] bytes = verify(mockedDownloader).download();
		verify(mockedDownloader, times(1)).download();
		verify(mockedSave).save(bytes);
		verify(mockedSave, times(1)).save(bytes);
	}

}
