package ru.albertroom.ecwidtesttask.downloader;

import java.io.BufferedInputStream;
import java.net.URL;

public class FactoryHttpConnection implements IFactoryConnection
{	
	public BufferedInputStream makeConnection(String url) throws Exception
	{
		URL source = new URL(url);
		return new BufferedInputStream(source.openStream());
	}
}
