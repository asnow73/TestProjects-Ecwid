package ru.albertroom.ecwidtesttask;

import java.util.HashMap;
import java.util.ArrayList;

public class ArgsAnalyzer
{
	private HashMap<String, ArrayList<String>> argumentsValues;
	private ArrayList<CommandArgumentDescription> argumentsDescription;
	
	public ArgsAnalyzer()
	{
		argumentsValues = new HashMap<String, ArrayList<String>>();
		argumentsDescription = new ArrayList<CommandArgumentDescription>();
	}
	
	public void addArgument(CommandArgumentDescription argDescription)
	{
		argumentsValues.put(argDescription.getArgumentName(), new ArrayList<String>());
		argumentsDescription.add(argDescription);
	}
	
	private boolean isOption(String str)
	{
		boolean result = false;
		if (str.charAt(0) == '-')
		{
			result = true;
		}
		return result;
	}
	
	private void readParams(String[] args)
	{
		ArrayList<String> values = null;
		for (String arg: args)
		{
			if (isOption(arg))
			{
				if (argumentsValues.keySet().contains(arg))
				{
					values = argumentsValues.get(arg);
				}
				else
				{
					//throw UnknownOptionException(); //проверка на неопознаные ключи
				}
			}
			else if (values != null)
			{
				values.add(arg);
			}
			else
			{
				//первый параметр не является ключом
			}
		}
	}
	
	private void checkArguments()
	{
		//проверка все ли требуемые опции указаны
		for (CommandArgumentDescription argumentDesc : argumentsDescription)
		{
			if ( (argumentDesc.isRequired()) && (argumentsValues.keySet().contains(argumentDesc.getArgumentName()) == false) )
			{
				//throw ArgumentMissedException
			}
		}
	}
	
	public void parse(String[] args)
	{
		readParams(args);
		checkArguments();		
	}
}
