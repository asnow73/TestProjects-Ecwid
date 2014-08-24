/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask;
import org.apache.commons.cli.*;

/*import java.io.*;
import java.net.*;
import java.util.*;*/


public class Main
{
	public static void main(String[] args)
	{
		/*Options options = new Options();
		options.addOption("t", false, "display current time");
		options.addOption("n", false, "display current time");
		options.addOption("l", false, "display current time");
		options.addOption("o", false, "display current time");
		options.addOption("f", false, "display current time");
		
		CommandLineParser parser = new PosixParser();
		try {
			CommandLine cmd = parser.parse( options, args);
			System.out.println(cmd.hasOption('o'));			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}*/
		
		ArgsAnalyzer argsParser = new ArgsAnalyzer();
		argsParser.addArgument(new CommandArgumentDescription("-n", "nnnnn", true));
		argsParser.addArgument(new CommandArgumentDescription("-l", "nnnnn", true));
		argsParser.addArgument(new CommandArgumentDescription("-o", "nnnnn", true));
		argsParser.addArgument(new CommandArgumentDescription("-f", "nnnnn", true));
		argsParser.parse(args);

		
		//разобрать аргументы
		
		//обработать на предмет повтор€ющихс€ ссылок
		
		//ManagerDownloading manager = new ManagerDownloading();
		//manager.startDownloading(/*countThreads, downloadingSpeed, listOfDataSource*/);
		
	}
}
