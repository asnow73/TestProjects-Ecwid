package ru.albertroom.ecwidtesttask.downloader.services;

import ru.albertroom.ecwidtesttask.time.Chronometer;

public class ProgressVisualisator implements IDownloadedBytesEvent
{
	final long ONE_SECOND = 1000000000;
	private Chronometer period; //хронометр
	
	public ProgressVisualisator()
	{
		period = new Chronometer(ONE_SECOND);
		period.start();
	}
	
	@Override
	public void onDataDownloaded(int sizeDownloadedBytes)
	{
		if (period.isTimePassed()) //заданный период времеени закончился
		{
			Output.print('.'); //индикация того, что работа ведётся, ничего не повисло			
			//снова начинаем отслеживать период времени
			period.start(); 
		}
	}
}
