/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;

/*import java.io.*;
import java.net.*;
import java.util.*;*/


public class Main
{
	public static void main(String[] args)
	{
		//разобрать аргументы
		ArgsAnalyzer argsParser = new ArgsAnalyzer(args);
		System.out.println(argsParser.getNumberOfThreads());
		System.out.println(argsParser.getDownloadingSpeedLimit());
		System.out.println(argsParser.getPathToLinksList());
		System.out.println(argsParser.getSvaeFolder());
		
		//обработать на предмет повтор€ющихс€ ссылок
		
		//ManagerDownloading manager = new ManagerDownloading();
		//manager.startDownloading(/*countThreads, downloadingSpeed, listOfDataSource*/);
		
	}
}
