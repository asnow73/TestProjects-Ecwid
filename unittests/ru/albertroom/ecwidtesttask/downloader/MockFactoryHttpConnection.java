package ru.albertroom.ecwidtesttask.downloader;

import java.io.InputStream;

public class MockFactoryHttpConnection implements IFactoryConnection
{
	public InputStream makeConnection(String url) throws Exception
	{
		return new MockInputStream();
	}
}
