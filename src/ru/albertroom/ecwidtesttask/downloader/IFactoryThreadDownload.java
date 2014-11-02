package ru.albertroom.ecwidtesttask.downloader;

public interface IFactoryThreadDownload
{
	Thread makeThreadDownload() throws Exception;
	boolean canCreateThread();
}
