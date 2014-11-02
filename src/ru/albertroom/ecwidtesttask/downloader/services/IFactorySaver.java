package ru.albertroom.ecwidtesttask.downloader.services;

import ru.albertroom.ecwidtesttask.downloader.IDownloadSave;

public interface IFactorySaver
{
	IDownloadSave makeSaver(String[] saveAsNames);
}
