package ru.albertroom.ecwidtesttask.readlinkslist;

import java.io.IOException;

public interface IDataLinksSource
{
	String readLine() throws IOException;
}
