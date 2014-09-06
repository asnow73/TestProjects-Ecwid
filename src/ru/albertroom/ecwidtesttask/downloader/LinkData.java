package ru.albertroom.ecwidtesttask.downloader;

//Information about the link and names for saving
public class LinkData
{
	private String urlLink;
	private String[] saveAsNames;
	
	public LinkData(String urlLink, String[] saveAsNames)
	{
		this.urlLink = urlLink;
		this.saveAsNames = saveAsNames;
	}
	
	public String getLink()
	{
		return urlLink;
	}
	
	public String[] getSaveAsNames()
	{
		return (String[]) saveAsNames;
	}

}
