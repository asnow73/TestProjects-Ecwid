/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.FactoryThreadHttpDownload;
import ru.albertroom.ecwidtesttask.downloader.LinkData;
import ru.albertroom.ecwidtesttask.downloader.services.DownloadedBytesCounter;
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
			argsParser.parse(args);
			
			int countThreads = argsParser.getNumberOfThreads();
			int downloadingSpeed = argsParser.getDownloadingSpeedLimit();
			String pathToLinks = argsParser.getPathToLinksList();
			String saveFolder = argsParser.getSaveFolder(); //TODO использовать при сохранении файлов
			
			FileLinksDataSource dataSource = new FileLinksDataSource(pathToLinks);
			ReaderLinksInfo downloadInfo = new ReaderLinksInfo();
			Stack<LinkData> linksData = downloadInfo.read(dataSource);
			dataSource.close();
			
			final long ONE_SECOND = 1000000000;
			Timer timer = new Timer();
			DownloadedBytesCounter bytesCounter = new DownloadedBytesCounter();  //counting downloaded bytes
			SpeedController speedControll = new SpeedController(downloadingSpeed, new Chronometer(ONE_SECOND));
			FactoryThreadHttpDownload factoryThreads = new FactoryThreadHttpDownload(linksData, saveFolder, bytesCounter, speedControll);
			timer.start();
			
			//ManagerDownloading manager = new ManagerDownloading(countThreads, linksData, bytesCounter, speedControll);
			ManagerDownloading manager = new ManagerDownloading(countThreads, factoryThreads);
			manager.startDownloading();
			
			timer.finish();
			System.out.println("Working time is " + String.valueOf(timer.getTotalTime()) + " ms" );
			System.out.println("Downloaded " + String.valueOf(bytesCounter.getTotalSizeDownloadedData()) + " bytes" );

		}
		catch (Exception e)
		{
			System.out.print("Error. " + e.getMessage() +" Programm is finished");
		}		
	}
}
