package ru.albertroom.ecwidtesttask.time;

//Класс для конвертирования времени в различные единицы измерения
public class TimeConverter
{
	public static long convertNanoSecondToMiliSecond(long nanoValue)
	{
		return  (nanoValue / 1000000);
	}
}
