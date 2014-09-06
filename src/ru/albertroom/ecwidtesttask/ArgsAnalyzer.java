package ru.albertroom.ecwidtesttask;

import org.apache.commons.cli.*;

//Class for parsing command line parameters
public class ArgsAnalyzer
{	
	private Options options = new Options();	
	private CommandLine cmd;
	
	private final String THREADS_PARAM = "n";
	private final String SPEED_PARAM = "l"; 
	private final String FOLDER_SAVE_PARAM = "o";
	private final String FILE_LINKS_PARAM = "f";
	
	public ArgsAnalyzer()
	{
		cmd = null;
		
		Option oThreadsNumber = new Option(THREADS_PARAM, true, "number of threads");
		oThreadsNumber.setArgs(1);
		oThreadsNumber.setRequired(true);
		oThreadsNumber.setArgName("threadsNumber");
		options.addOption(oThreadsNumber);
		
		Option oSpeedLimit = new Option(SPEED_PARAM, true, "speed limit of downloading. Byte per second (suffix k=1024, m=1024*1024)");
		oSpeedLimit.setArgs(1);
		oSpeedLimit.setRequired(true);
		oSpeedLimit.setArgName("speedLimit");
		options.addOption(oSpeedLimit);
		
		Option oSaveFolder = new Option(FOLDER_SAVE_PARAM, true, "folder for save information");
		oSaveFolder.setArgs(1);
		oSaveFolder.setRequired(true);
		oSaveFolder.setArgName("saveFolder");
		options.addOption(oSaveFolder);
		
		Option oLinksList = new Option(FILE_LINKS_PARAM, true, "path to the file wich contains list of links");
		oLinksList.setArgs(1);
		oLinksList.setRequired(true);
		oLinksList.setArgName("linksList");
		options.addOption(oLinksList);
	}
	
	public void parse(String[] args) throws ParseException
	{
		CommandLineParser parser = new PosixParser();
		HelpFormatter formatter = new HelpFormatter();
		try
		{
			cmd = parser.parse( options, args);
		}		
		catch (ParseException e) 
		{
			System.out.println( "Parsing failed. Reason: " + e.getMessage() );
			formatter.printHelp( "utility", options );
			throw e;
		}
	}
	
	//Method to get the number of threads
	public int getNumberOfThreads() throws ParseException
	{
		int threadsNumber = 0;	
		try
		{
			threadsNumber = Integer.parseInt(cmd.getOptionValue(THREADS_PARAM));
		}
		catch (NumberFormatException e)
		{
			System.out.println("Error. Incorrect value for number of threads");
			throw e;
        }
		return threadsNumber;
	}
	
	//Method to get the speed of downloading
	public int getDownloadingSpeedLimit() throws ParseException
	{
		final int KBYTES = 1024;
		final int MBYTES = 1024*1024;
		int speedLimit = 0;
		
		try
		{
			String slimit = cmd.getOptionValue(SPEED_PARAM);			
			StringBuilder sb = new StringBuilder(slimit);
			char suffix = sb.charAt(slimit.length() - 1);
			
			switch (suffix)
			{
				case 'k':
					slimit = sb.deleteCharAt(slimit.length() - 1).toString();
					speedLimit = Integer.parseInt(slimit) * KBYTES;
					break;
				case 'm':
					slimit = sb.deleteCharAt(slimit.length() - 1).toString();
					speedLimit = Integer.parseInt(slimit) * MBYTES;
					break;
				default:
					speedLimit = Integer.parseInt(slimit);
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
	
	//Method to get the folder for saving downloaded files
	public String getSaveFolder() throws ParseException
	{
		return cmd.getOptionValue(FOLDER_SAVE_PARAM);
	}
	
	//Method to get the path to file with links for download
	public String getPathToLinksList() throws ParseException
	{
		return cmd.getOptionValue(FILE_LINKS_PARAM);
	}
}
