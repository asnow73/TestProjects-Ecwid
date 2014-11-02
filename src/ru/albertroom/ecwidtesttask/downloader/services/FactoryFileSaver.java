package ru.albertroom.ecwidtesttask.downloader.services;

import ru.albertroom.ecwidtesttask.downloader.IDownloadSave;

public class FactoryFileSaver implements IFactorySaver
{
	private String saveFolder; //����������, � ������� ����������� ��������� �����
	
	public FactoryFileSaver(String folder)
	{
		this.saveFolder = folder;
	}

	@Override
	public IDownloadSave makeSaver(String[] saveAsNames)
	{
		FileSaver saver = new FileSaver(saveAsNames, saveFolder);
		return saver;
	}
}
