package ru.albertroom.ecwidtesttask.downloader.services;

public interface IDownloadedBytesEvent
{
	void onDataDownloaded(int sizeDownloadedBytes);
}
