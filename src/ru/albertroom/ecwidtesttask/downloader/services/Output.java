package ru.albertroom.ecwidtesttask.downloader.services;


import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public final class Output
{
	private static boolean permissionToOut = true;
	
	private Output()
	{
	}
	
	public static void println(String str)
	{
		if (permissionToOut == true)
		{
			System.out.println(str);
		}
	}
	
	public static void print(char ch)
	{
		if (permissionToOut == true)
		{
			System.out.print(ch);
		}
	}
	
	public static void printHelpFormatter(HelpFormatter formatter, Options options)
	{
		if (permissionToOut == true)
		{
			formatter.printHelp( "utility", options );
		}
	}
	
	public static void switchOn()
	{
		permissionToOut = true;
	}
	
	public static void switchOff()
	{
		permissionToOut = false;
	}
}
