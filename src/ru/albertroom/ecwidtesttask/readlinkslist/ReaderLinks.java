package ru.albertroom.ecwidtesttask.readlinkslist;

import java.util.HashSet;
import java.util.Hashtable;
import java.io.*;

import ru.albertroom.ecwidtesttask.downloader.LinkData;

public class ReaderLinks implements ILinkDataSource
{
	private Hashtable<String, HashSet<String>> links;
	private String pathToLinksFile;
	
	public ReaderLinks(String pathToLinksFile)
	{
		this.pathToLinksFile = pathToLinksFile;
		this.links = new Hashtable<String, HashSet<String>>();
	}
	
	@Override
	public LinkData pop()
	{	
		String key = links.keys().nextElement();	
		HashSet<String> names = links.get(key);
		String[] saveAsNames = names.toArray(new String[names.size()]);
		links.remove(key);
		
		LinkData linkd = new LinkData(key, saveAsNames);
		return linkd;
	}
	
	@Override
	public int size()
	{
		return links.size();
	}
	
	@Override
	public boolean empty()
	{
		return links.isEmpty();
	}
	
	private boolean isLinkCorrect(String[] strs) throws UncorrectLinkException
	{
		boolean result = true;
		if (strs.length != 2)
		{
			result = false;
			throw new UncorrectLinkException("Link is not correct");
		}
		return result;
	}
	
	private void addLink(String urlLink, String name)
	{
		if (links.containsKey(urlLink))
		{
			links.get(urlLink).add(name);
		}
		else
		{
			links.put(urlLink, new HashSet<String>());
			links.get(urlLink).add(name);
		}
	}
	
	public void read() throws UncorrectLinkException, FileNotFoundException, IOException
	{
		try
		{
			String linkInf;
			BufferedReader in = new BufferedReader(new FileReader(pathToLinksFile));
			while ((linkInf = in.readLine()) != null)
			{
				String[] strs = linkInf.split(" ");
				if (isLinkCorrect(strs))
				{
					addLink(strs[0], strs[1]);
				}
			}
			in.close();
		}
		catch (UncorrectLinkException e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File " + pathToLinksFile + " not founded");
			throw e;
		}
		catch (IOException e)
		{
			System.out.println("File read error");
			throw e;
		}
	}
}
