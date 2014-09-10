package ru.albertroom.ecwidtesttask.time;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChronometerTest {

	@Test
	public void testReset()
	{
		Chronometer chronometer = new Chronometer(1000000);
		assertEquals(false, chronometer.isTimePassed());
		chronometer.start();
		assertEquals(false, chronometer.isTimePassed());
		
		Chronometer chronometer2 = new Chronometer(1);
		chronometer2.start();
		assertEquals(true, chronometer2.isTimePassed());
	}
}
