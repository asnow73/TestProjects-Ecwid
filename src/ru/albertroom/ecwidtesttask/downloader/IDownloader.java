/*
 * Project: Ecwid Test Task 
 * Author: Galiullov Albert
*/

package ru.albertroom.ecwidtesttask.downloader;

import java.io.IOException;

public interface IDownloader
{
	byte[] download() throws IOException;
}
