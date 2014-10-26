/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.FactoryThreadHttpDownload;
import ru.albertroom.ecwidtesttask.downloader.LinkData;
import ru.albertroom.ecwidtesttask.downloader.services.DownloadedBytesCounter;
import ru.albertroom.ecwidtesttask.downloader.services.Output;
import ru.albertroom.ecwidtesttask.downloader.services.SpeedController;
import ru.albertroom.ecwidtesttask.readlinkslist.FileLinksDataSource;
import ru.albertroom.ecwidtesttask.readlinkslist.ReaderLinksInfo;
import ru.albertroom.ecwidtesttask.time.Chronometer;
import ru.albertroom.ecwidtesttask.time.Timer;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			ArgsAnalyzer argsParser = new ArgsAnalyzer();
			boolean haveArgsForDownload = argsParser.parse(args);
			
			if (haveArgsForDownload)
			{
				int countThreads = argsParser.getNumberOfThreads();
				int downloadingSpeed = argsParser.getDownloadingSpeedLimit();
				String pathToLinks = argsParser.getPathToLinksList();
				String saveFolder = argsParser.getSaveFolder();
				
				FileLinksDataSource dataSource = new FileLinksDataSource(pathToLinks);
				ReaderLinksInfo downloadInfo = new ReaderLinksInfo();
				Stack<LinkData> linksData = downloadInfo.read(dataSource);
				dataSource.close();
				
				final long ONE_SECOND = 1000000000;
				Timer timer = new Timer();
				DownloadedBytesCounter bytesCounter = new DownloadedBytesCounter();  //подсчёт скачанных байтов
				SpeedController speedControll = new SpeedController(downloadingSpeed, new Chronometer(ONE_SECOND)); //контроль скорости скачавания
				FactoryThreadHttpDownload factoryThreads = new FactoryThreadHttpDownload(linksData, saveFolder, bytesCounter, speedControll); //фабрика потоков для скачивания
				timer.start();
				
				ManagerDownloading manager = new ManagerDownloading(countThreads, factoryThreads); //управление процессом скачивания
				manager.startDownloading();
				
				timer.finish();
				Output.println("Working time is " + String.valueOf(timer.getTotalTime()) + " ms" );
				Output.println("Downloaded " + String.valueOf(bytesCounter.getTotalSizeDownloadedData()) + " bytes" );
			}

		}
		catch (Exception e)
		{
			Output.println("Error. " + e.getMessage() +" Programm is finished");
		}		
	}
}
