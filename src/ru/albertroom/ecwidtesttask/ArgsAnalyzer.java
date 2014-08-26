package ru.albertroom.ecwidtesttask;

import org.apache.commons.cli.*;

public class ArgsAnalyzer
{	
	private Options options = new Options();	
	private CommandLine cmd;
	private String[] arguments;
	
	public ArgsAnalyzer(String[] args)
	{
		arguments = args;
		cmd = null;
		
		Option oThreadsNumber = new Option("n", true, "number of threads");
		oThreadsNumber.setArgs(1);
		oThreadsNumber.setRequired(true);
		oThreadsNumber.setArgName("threadsNumber");
		options.addOption(oThreadsNumber);
		
		Option oSpeedLimit = new Option("l", true, "speed limit of downloading. Byte per second (suffix k=1024, m=1024*1024)");
		oSpeedLimit.setArgs(1);
		oSpeedLimit.setRequired(true);
		oSpeedLimit.setArgName("speedLimit");
		options.addOption(oSpeedLimit);
		
		Option oSaveFolder = new Option("o", true, "folder for save information");
		oSaveFolder.setArgs(1);
		oSaveFolder.setRequired(true);
		oSaveFolder.setArgName("saveFolder");
		options.addOption(oSaveFolder);
		
		Option oLinksList = new Option("f", true, "path to the file wich contains list of links");
		oLinksList.setArgs(1);
		oLinksList.setRequired(true);
		oLinksList.setArgName("linksList");
		options.addOption(oLinksList);
	}
	
	private void parse()
	{
		CommandLineParser parser = new PosixParser();
		HelpFormatter formatter = new HelpFormatter();
		try
		{
			cmd = parser.parse( options, arguments);
		}		
		catch (ParseException e) 
		{
			System.err.println( "Parsing failed. Reason: " + e.getMessage() );
			formatter.printHelp( "utility", options );
		}
	}
	
	public int getNumberOfThreads()
	{
		int threadsNumber = 0;
		
		if (cmd == null)
		{
			parse();
		}
		
		try
		{
			threadsNumber = Integer.parseInt(cmd.getOptionValue("n"));
		}
		catch (NumberFormatException e)
		{
			System.out.println("Error. Incorrect value for number of threads");
			throw e;
        }
		return threadsNumber;
	}
	
	public int getDownloadingSpeedLimit()
	{
		final int KBYTES = 1024;
		final int MBYTES = 1024*1024;
		int speedLimit = 0;
		
		if (cmd == null)
		{
			parse();
		}
		
		try
		{
			String slimit = cmd.getOptionValue("l");			
			StringBuilder sb = new StringBuilder(slimit);
			char suffix = sb.charAt(slimit.length() - 1);
			slimit = sb.deleteCharAt(slimit.length() - 1).toString();
			switch (suffix)
			{
				case 'k':
					speedLimit = Integer.parseInt(slimit) * KBYTES;
					break;
				case 'm':
					speedLimit = Integer.parseInt(slimit) * MBYTES;
					break;
			}
		}
		catch (NumberFormatException e)
		{
			System.out.println("Error. Incorrect value for speed limit of downloading");
			throw e;
        }
		return speedLimit;
	}
	
	public String getSvaeFolder()
	{
		if (cmd == null)
		{
			parse();
		}
		return cmd.getOptionValue("o");
	}
	
	public String getPathToLinksList()
	{
		if (cmd == null)
		{
			parse();
		}
		return cmd.getOptionValue("f");
	}
}
