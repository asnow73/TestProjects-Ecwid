package ru.albertroom.ecwidtesttask.downloader.services;

import static org.junit.Assert.*;

import org.junit.Test;
import ru.albertroom.ecwidtesttask.time.IChronometer;


class ChronometerMock implements IChronometer
{
	public boolean timePassed = false;
	
	@Override
	public void start()
	{
		timePassed = false;
	}
	
	@Override
	public boolean isTimePassed()
	{
		return timePassed;
	}
	
}

public class SpeedControllerTest {

	@Test
	public void testSpeedController()
	{
		ChronometerMock chronometer = new ChronometerMock();
		SpeedController speedControl = new SpeedController(150, chronometer);
		chronometer.timePassed = false;
		assertEquals(100, speedControl.getAllowBytesToDownload()); //the first 100 bytes allows to downloading
			
		chronometer.timePassed = false;
		assertEquals(50, speedControl.getAllowBytesToDownload()); //the remaining 50 bytes allows to downloading
		
		chronometer.timePassed = false;
		assertEquals(0, speedControl.getAllowBytesToDownload()); //0 bytes allows to downloading, because the limit is exceeded
		
		chronometer.timePassed = true;
		assertEquals(100, speedControl.getAllowBytesToDownload()); //next 100 bytes
	}

	//@Test
	//public void testGetAllowBytesToDownload() {
	//	fail("Not yet implemented");
	//}

}
