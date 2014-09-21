package ru.albertroom.ecwidtesttask.downloader;

public interface IFactoryThreadDownload
{
	ThreadDownload makeThreadDownload();
	boolean canCreateThread();
}
