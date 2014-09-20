package ru.albertroom.ecwidtesttask.downloader.services;

public interface IDownloadedBytesCounter
{
	void onDataDownloaded(int sizeDownloadedBytes);
}
