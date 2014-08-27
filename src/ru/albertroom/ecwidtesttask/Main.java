/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

import java.util.Stack;


public class Main
{
	public static void main(String[] args)
	{
		//разобрать аргументы
		ArgsAnalyzer argsParser = new ArgsAnalyzer(args);
		
		int countThreads = argsParser.getNumberOfThreads();
		int downloadingSpeed = argsParser.getDownloadingSpeedLimit();
		String pathToLinks = argsParser.getPathToLinksList();
		String saveFolder = argsParser.getSvaeFolder();
		
		System.out.println(countThreads);
		System.out.println(downloadingSpeed);
		System.out.println(pathToLinks);
		System.out.println(saveFolder);
		
		//обработать на предмет повтор€ющихс€ ссылок
		Stack<String> stackOfDataSource = new Stack<String>();
		stackOfDataSource.push("http://nevseoboi.com.ua/uploads/posts/2011-06/1307722487_62_www.nevseoboi.com.ua.jpg");
		stackOfDataSource.push("http://htmlbook.ru/files/images/blog/triangle-2.png");
		stackOfDataSource.push("http://aquasantop.ru/img/catphotos/68/2(218).jpg");
		stackOfDataSource.push("http://images.kika.com/db/1/a/8/17471898_2_z.jpg");
		
		ManagerDownloading manager = new ManagerDownloading(countThreads, downloadingSpeed, stackOfDataSource);			
		manager.startDownloading();
		
	}
}
