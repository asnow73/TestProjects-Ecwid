package ru.albertroom.ecwidtesttask.downloader;

//Информация о ссылке для скачивания
public class LinkData
{
	private String urlLink; //URL адрес
	private String[] saveAsNames; //имена под которыми надо сохранить файл
	
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
