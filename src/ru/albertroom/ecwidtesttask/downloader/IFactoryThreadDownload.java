package ru.albertroom.ecwidtesttask.downloader;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IFactoryThreadDownload
{
	Thread makeThreadDownload() throws MalformedURLException, IOException;
	boolean canCreateThread();
}
