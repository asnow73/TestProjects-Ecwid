package ru.albertroom.ecwidtesttask.readlinkslist;


import ru.albertroom.ecwidtesttask.downloader.LinkData;

public interface ILinkDataSource
{
	LinkData pop();
	int size();
	boolean empty();
}
