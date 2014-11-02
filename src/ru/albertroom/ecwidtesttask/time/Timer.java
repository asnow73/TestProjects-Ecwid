package ru.albertroom.ecwidtesttask.time;

//����� ��� ������ �������
public class Timer
{
	private long startTime;
	private long finishTime;
	
	public Timer()
	{
		startTime = 0;
		finishTime = 0;
	}	
	
	//������ �����
	public void start()
	{
		startTime = System.nanoTime();
	}
	
	//��������� �����
	public void finish()
	{
		finishTime = System.nanoTime();
	}
	
	//��������� �������� ����� � �������������
	public long getTotalTime()
	{
		return TimeConverter.convertNanoSecondToMiliSecond(finishTime - startTime);
	}
}
