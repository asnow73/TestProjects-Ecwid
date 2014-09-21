package ru.albertroom.ecwidtesttask.downloader.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import ru.albertroom.ecwidtesttask.time.Chronometer;

public class SpeedControllerTest {

	@Test
	public void testSpeedController()
	{
		Chronometer mockChronometer = mock(Chronometer.class);
		SpeedController speedControl = new SpeedController(150, mockChronometer);
		when(mockChronometer.isTimePassed()).thenReturn(false);
		assertEquals(100, speedControl.getAllowBytesToDownload()); //the first 100 bytes allows to downloading
		assertEquals(50, speedControl.getAllowBytesToDownload()); //the remaining 50 bytes allows to downloading
		assertEquals(0, speedControl.getAllowBytesToDownload()); //0 bytes allows to downloading, because the limit is exceeded
		
		when(mockChronometer.isTimePassed()).thenReturn(true);
		assertEquals(100, speedControl.getAllowBytesToDownload()); //next 100 bytes*/
	}
}
