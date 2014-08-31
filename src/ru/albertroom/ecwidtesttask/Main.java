/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import ru.albertroom.ecwidtesttask.readlinkslist.ReaderLinks;

//TODO нарисовать схему связей пакетов, кто кого включает
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
			
			ReaderLinks readerLinks = new ReaderLinks(pathToLinks);
			readerLinks.read();
			
			ManagerDownloading manager = new ManagerDownloading(countThreads, downloadingSpeed, readerLinks);			
			manager.startDownloading();
		}
		catch (Exception e)
		{
			System.out.print("Error. Programm is finished");
		}
		
	}
}
