package ru.albertroom.ecwidtesttask.downloader;

import java.io.IOException;
import java.io.InputStream;

public class MockInputStream extends InputStream
{
	private boolean finished;
	private byte[] source;
	
	public MockInputStream()
	{
		source = new byte[] {0,1,2,3,4};
		finished = false;
	}
	
	public int read(byte[] b)
	{
		if (!finished)
		{
			finished = true;
			for (int i = 0; i < source.length; ++i)
			{
				b[i] = source[i];
			}
			return source.length;
		}
		else
		{
			return -1;
		}
	}
	
	public byte[] getBytes()
	{
		return source;
	}
	
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
