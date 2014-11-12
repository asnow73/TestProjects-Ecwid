package ru.albertroom.ecwidtesttask.downloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FactoryHttpConnection implements IFactoryConnection
{	
	public BufferedInputStream makeConnection(String url) throws MalformedURLException, IOException
	{
		URL source = new URL(url);
		return new BufferedInputStream(source.openStream());
	}
}
