package ru.albertroom.ecwidtesttask;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.Stack;

import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.MockInputStream;
import ru.albertroom.ecwidtesttask.downloader.services.DownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.SpeedController;
import ru.albertroom.ecwidtesttask.time.Chronometer;


public class ManagerDownloadingTest {

	@Test
	public void testStartDownloading()
	{
		//проверка на то, что программа не качает больше x байт в период времени t
		
		
		//создать byteCounter
		final long TIME_PERIOD = 10000000;
		
		DownloadedBytesCounter bytesCounter = new DownloadedBytesCounter();  //counting downloaded bytes
		//контроллер скорости с малым периодом времени
		SpeedController speedControll = new SpeedController(1, new Chronometer(TIME_PERIOD)); //1 байт в 1/100 с
		
		/*IFactoryThreadDownload mockFactory = mock(IFactoryThreadDownload.class);
		FileSaver mockSaver =  mock(FileSaver.class);
		MockInputStream mockInStream = new MockInputStream();
		//Downloader downloader = new Downloader(mockInStream, bytesCounter, speedControll);
		
		when(mockFactory.makeThreadDownload()).thenReturn(new ThreadDownload(new Downloader(mockInStream, bytesCounter, speedControll), "threadname", mockSaver));
		when(mockFactory.canCreateThread()).thenReturn(true);*/
		
		
		Stack<InputStream> inStreams = new Stack<InputStream>();
		inStreams.add(new MockInputStream());
		inStreams.add(new MockInputStream());
		MockFactoryThreadDownload mockFactory = new MockFactoryThreadDownload(inStreams, bytesCounter, speedControll);
		
		//старт скачки, фабрика тредов (2 треда, читать из массива ОЗУ)
		ManagerDownloading manager = new ManagerDownloading(2, mockFactory);
		
		long startTime = 0;
		startTime = System.nanoTime();
		
		manager.startDownloading();

		long finishTime = 0;
		finishTime = System.nanoTime();
		
		//System.out.println("Speed is " + String.valueOf((finishTime - startTime)/(bytesCounter.getTotalSizeDownloadedData()))  );
		//System.out.println("Downloaded " + String.valueOf(bytesCounter.getTotalSizeDownloadedData()) + " bytes" );
		//проверяем количество скачанных байт / время <= скрорости
		
		assertEquals(true, (finishTime - startTime)/(bytesCounter.getTotalSizeDownloadedData()) > TIME_PERIOD );
		
		//Проверка количесва активных потоков
		
		//Создать мок для быйт коунтера и на онДатаРеад проверять Thread.activeCount()
	}

}
