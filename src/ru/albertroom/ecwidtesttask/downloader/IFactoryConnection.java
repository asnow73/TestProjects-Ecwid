package ru.albertroom.ecwidtesttask.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public interface IFactoryConnection
{
	InputStream makeConnection(String url) throws MalformedURLException, IOException;
}
