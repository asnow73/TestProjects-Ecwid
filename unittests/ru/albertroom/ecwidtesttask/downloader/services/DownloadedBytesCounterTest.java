package ru.albertroom.ecwidtesttask.downloader.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class DownloadedBytesCounterTest {

	@Test
	public void testOnDataDownloaded()
	{
		DownloadedBytesCounter counter = new DownloadedBytesCounter();		
		assertEquals(0, counter.getTotalSizeDownloadedData());
		counter.onDataDownloaded(100);
		assertEquals(100, counter.getTotalSizeDownloadedData());
		counter.onDataDownloaded(200);
		assertEquals(100+200, counter.getTotalSizeDownloadedData());
	}
}
