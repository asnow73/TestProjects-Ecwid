package ru.albertroom.ecwidtesttask.downloader;

import java.io.InputStream;

public interface IFactoryConnection
{
	InputStream makeConnection(String url) throws Exception;
}
