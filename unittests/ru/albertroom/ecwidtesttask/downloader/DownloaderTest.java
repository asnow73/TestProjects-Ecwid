package ru.albertroom.ecwidtesttask.downloader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.services.DownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.SpeedController;

public class DownloaderTest {
		
	@Test
	public void testDownload()
	{
		MockInputStream mockInStream = new MockInputStream();
		
		Downloader downloader = new Downloader(mockInStream);
		
		byte[] result = new byte[5];
		try {
			result = downloader.download();
		} catch (IOException e) {
			fail("testDownload fail");
		}
		for (int i = 0; i < mockInStream.getBytes().length; ++i)
		{
			assertEquals(mockInStream.getBytes()[i], result[i]);
		}
	}
	
	@Test
	public void testDownloadWithSpeedControllAndBytesCounting()
	{
		MockInputStream mockInStream = new MockInputStream();
		
		SpeedController mockSpeedController = mock(SpeedController.class);
		DownloadedBytesCounter mockBytesCounter = mock(DownloadedBytesCounter.class);
		Downloader downloader = new Downloader(mockInStream, mockBytesCounter, mockSpeedController, null);
		
		when(mockSpeedController.getAllowBytesToDownload()).thenReturn(mockInStream.getBytes().length);
		try {
			downloader.download();
		} catch (IOException e) {
			fail("testDownloadWithSpeedControllAndBytesCounting fail");
		}
		
		verify(mockBytesCounter, times(1)).onDataDownloaded(mockInStream.getBytes().length);
		verify(mockSpeedController, times(2)).getAllowBytesToDownload();
	}

}
