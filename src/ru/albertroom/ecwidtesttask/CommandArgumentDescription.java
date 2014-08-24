package ru.albertroom.ecwidtesttask;

public class CommandArgumentDescription
{
	private String argumentName;
	private String description;
	private boolean required;
	
	public CommandArgumentDescription(String argumentName, String description, boolean required)
	{
		this.argumentName = argumentName;
		this.description = description;
		this.required = required;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getArgumentName()
	{
		return this.argumentName;
	}
	
	public boolean isRequired()
	{
		return this.required;
	}
}