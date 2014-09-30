package ru.albertroom.ecwidtesttask.downloader.services;

import ru.albertroom.ecwidtesttask.time.IChronometer;

//Класс для организации контроля за скоростью скачивания
public class SpeedController implements ISpeedController
{
	private int limitBytesInTimePeriod; //максимальное количество байтов, которые можно скачать за period
	private int allowedToDownloadBytes; //количество байтов уже разрешённых для скачивания
	private IChronometer period; //хронометр
	
	public SpeedController(int limitBytes, IChronometer timePeriod)
	{
		this.limitBytesInTimePeriod = limitBytes;
		this.allowedToDownloadBytes = 0;
		this.period = timePeriod;
		timePeriod.start();
	}

	//Получить количество байт, которые можно скачать на данный момент
	@Override
	public synchronized int getAllowBytesToDownload()
	{
		final int DEFAULT_SIZE_PART_DATA = 100;
		int allowBytes = 0;
				
		if (period.isTimePassed()) //заданный период времеени закончился
		{
			System.out.print('.'); //TODO отдельный модуль для индикации прогресса
			allowedToDownloadBytes = 0;
			
			//снова начинаем отслеживать период времени
			period.start(); 
		}
			
		if (allowedToDownloadBytes < limitBytesInTimePeriod) //если уже скачанных байтов меньше чем лимит
		{
			int diff = limitBytesInTimePeriod - allowedToDownloadBytes;
			if (diff >= DEFAULT_SIZE_PART_DATA)
			{
				allowBytes = DEFAULT_SIZE_PART_DATA;
			}
			else
			{
				allowBytes = diff;
			}
			allowedToDownloadBytes += allowBytes;
		}
		
		return allowBytes;
	}
}
