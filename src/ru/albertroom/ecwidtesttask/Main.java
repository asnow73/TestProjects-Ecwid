/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.LinkData;
import ru.albertroom.ecwidtesttask.readlinkslist.FileLinksDataSource;
import ru.albertroom.ecwidtesttask.readlinkslist.ReaderLinksInfo;

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
			String saveFolder = argsParser.getSaveFolder();
			
			System.out.println(countThreads);
			System.out.println(downloadingSpeed);
			System.out.println(pathToLinks);
			System.out.println(saveFolder);
			
			ReaderLinksInfo downloadInfo = new ReaderLinksInfo();
			FileLinksDataSource dataSource = new FileLinksDataSource(pathToLinks);			
			Stack<LinkData> linksData = downloadInfo.read(dataSource);
			dataSource.close();
			
			ManagerDownloading manager = new ManagerDownloading(countThreads, downloadingSpeed, linksData);			
			manager.startDownloading();
		}
		catch (Exception e)
		{
			System.out.print("Error. Programm is finished");
		}
		
	}
}
