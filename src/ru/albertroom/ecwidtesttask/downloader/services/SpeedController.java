package ru.albertroom.ecwidtesttask.downloader.services;

import ru.albertroom.ecwidtesttask.time.IChronometer;

//����� ��� ����������� �������� �� ��������� ����������
public class SpeedController implements ISpeedController
{
	private int limitBytesInTimePeriod; //������������ ���������� ������, ������� ����� ������� �� period
	private int allowedToDownloadBytes; //���������� ������ ��� ����������� ��� ����������
	private IChronometer period; //���������
	
	public SpeedController(int limitBytes, IChronometer timePeriod)
	{
		this.limitBytesInTimePeriod = limitBytes;
		this.allowedToDownloadBytes = 0;
		this.period = timePeriod;
		this.period.start();
	}

	//�������� ���������� ����, ������� ����� ������� �� ������ ������
	@Override
	public synchronized int getAllowBytesToDownload()
	{
		final int DEFAULT_SIZE_PART_DATA = 100;
		int allowBytes = 0;
				
		//�������� ������ �������� ����������
		if (period.isTimePassed())
		{
			allowedToDownloadBytes = 0;			
			//����� �������� ����������� ������ �������
			period.start(); 
		}
			
		if (allowedToDownloadBytes < limitBytesInTimePeriod) //���� ��� ��������� ������ ������ ��� �����
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
