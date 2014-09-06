/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import java.util.Stack;
import ru.albertroom.ecwidtesttask.downloader.LinkData;
import ru.albertroom.ecwidtesttask.readlinkslist.FileReaderLinksInfo;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			ArgsAnalyzer argsParser = new ArgsAnalyzer(args);
			
			int countThreads = argsParser.getNumberOfThreads();
			int downloadingSpeed = argsParser.getDownloadingSpeedLimit();
			String pathToLinks = argsParser.getPathToLinksList();
			String saveFolder = argsParser.getSvaeFolder();
			
			System.out.println(countThreads);
			System.out.println(downloadingSpeed);
			System.out.println(pathToLinks);
			System.out.println(saveFolder);
			
			FileReaderLinksInfo downloadInfo = new FileReaderLinksInfo();
			Stack<LinkData> linksData = downloadInfo.read(pathToLinks);
			
			ManagerDownloading manager = new ManagerDownloading(countThreads, downloadingSpeed, linksData);			
			manager.startDownloading();
		}
		catch (Exception e)
		{
			System.out.print("Error. Programm is finished");
		}
		
	}
}
