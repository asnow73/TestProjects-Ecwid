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
		//Downloader downloader1 = new Downloader("http://htmlbook.ru/files/images/blog/triangle-2.png", "data.png");
		//downloader1.download();
		
		//разобрать аргументы
		
		//обработать на предмет повтор€ющихс€ ссылок
		
		ManagerDownloading manager = new ManagerDownloading();
		manager.startDownloading(/*countThreads, downloadingSpeed, listOfDataSource*/);
		
	}
}
