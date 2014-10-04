package ru.albertroom.ecwidtesttask;

import org.apache.commons.cli.*;

// ласс дл€ парсинга командной строки с параметрами
public class ArgsAnalyzer
{
	private CommandLine cmd;
	
	private final String THREADS_PARAM = "n";
	private final String SPEED_PARAM = "l"; 
	private final String FOLDER_SAVE_PARAM = "o";
	private final String FILE_LINKS_PARAM = "f";
	private final String HELP_PARAM = "help";
	
	public ArgsAnalyzer()
	{
		cmd = null;
	}
	
	private Options getBaseParamsOptions()
	{
		Options options = new Options();
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
		return options;
	}
	
	private Options getHelpOptions()
	{
		Options options = new Options();
		Option oHelp = new Option(HELP_PARAM, "usage information");
		oHelp.setRequired(false);
		oHelp.setArgName("help");
		options.addOption(oHelp);
		return options;
	}
	
	public boolean parse(String[] args) throws ParseException
	{
		boolean result = true;
		
		CommandLineParser parser = new PosixParser();
		HelpFormatter formatter = new HelpFormatter();
		Options options = getBaseParamsOptions();
		try
		{			
			
			cmd = parser.parse( options, args);
		}		
		catch (ParseException e) 
		{
			Options optionsHelp = getHelpOptions();			
			cmd = parser.parse( optionsHelp, args);
			if (cmd.hasOption("help"))
			{
				formatter.printHelp( "utility", options );
				result = false;
			}
			else
			{
			System.out.println( "Parsing failed. Reason: " + e.getMessage() );
			formatter.printHelp( "utility", options );
			throw e;
			}
		}
		
		return result;
	}
	
	//ѕолучить количество одновременно качающих потоков
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
	
	//ѕолучить значение общего ограничени€ на скорость скачивани€
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
	
	//ѕолучить им€ папки, куда складывать скачанные файлы
	public String getSaveFolder() throws ParseException
	{
		return cmd.getOptionValue(FOLDER_SAVE_PARAM);
	}
	
	//путь к файлу со списком ссылок
	public String getPathToLinksList() throws ParseException
	{
		return cmd.getOptionValue(FILE_LINKS_PARAM);
	}
}
