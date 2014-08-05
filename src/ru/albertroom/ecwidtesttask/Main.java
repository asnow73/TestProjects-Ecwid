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
		//downloader.download("http://htmlbook.ru/files/images/blog/triangle-2.png");	
		Downloader downloader1 = new Downloader("data.png");
		ThreadDownload thread1 = new ThreadDownload(downloader1, "http://htmlbook.ru/files/images/blog/triangle-2.png", "thread #1");
		
		Downloader downloader2 = new Downloader("kran.jpg");
		ThreadDownload thread2 = new ThreadDownload(downloader2, "http://aquasantop.ru/img/catphotos/68/2(218).jpg", "thread #2");
		
		Downloader downloader3 = new Downloader("kran.jpg");
		ThreadDownload thread3 = new ThreadDownload(downloader3, "http://images.kika.com/db/1/a/8/17471898_2_z.jpg", "thread #3");
	}
}
