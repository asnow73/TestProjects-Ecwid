package ru.albertroom.ecwidtesttask.downloader.services;

import ru.albertroom.ecwidtesttask.time.Chronometer;

public class ProgressVisualisator implements IDownloadedBytesEvent
{
	final long ONE_SECOND = 1000000000;
	private Chronometer period; //���������
	
	public ProgressVisualisator()
	{
		period = new Chronometer(ONE_SECOND);
		period.start();
	}
	
	@Override
	public void onDataDownloaded(int sizeDownloadedBytes)
	{
		if (period.isTimePassed()) //�������� ������ �������� ����������
		{
			Output.print('.'); //��������� ����, ��� ������ ������, ������ �� �������			
			//����� �������� ����������� ������ �������
			period.start(); 
		}
	}
}
