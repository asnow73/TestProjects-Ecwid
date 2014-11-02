package ru.albertroom.ecwidtesttask.time;

import static org.junit.Assert.*;
import org.junit.Test;

public class TimeConverterTest {
	@Test
	public void testConvertNanoSecondToMiliSecond() {
		assertEquals(1, TimeConverter.convertNanoSecondToMiliSecond(1000000));
	}

}
