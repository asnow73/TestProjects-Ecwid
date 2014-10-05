package ru.albertroom.ecwidtesttask.downloader;

public interface IFactoryThreadDownload
{
	Thread makeThreadDownload();
	boolean canCreateThread();
}
