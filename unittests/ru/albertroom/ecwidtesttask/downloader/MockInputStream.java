package ru.albertroom.ecwidtesttask.downloader;

import java.io.IOException;
import java.io.InputStream;

public class MockInputStream extends InputStream
{
	private byte[] source;
	private int position;
	
	public MockInputStream()
	{
		source = new byte[] {0,1,2,3,4};
		position = 0;
	}
	
	public int read(byte[] b)
	{
		
		int count = 0;
		if (position != source.length)
		{
			for (int i = 0; i < b.length; ++i)
			{
				if (position == source.length)
				{
					break;
				}
				else
				{
				  b[i] = source[position];
				  position++;
				  count++;
				}
			}
			return count;
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
