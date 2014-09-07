package ru.albertroom.ecwidtesttask.readlinkslist;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.LinkData;

//Class to read links data from file 
public class ReaderLinksInfo
{
	private Hashtable<String, HashSet<String>> links;
	
	public ReaderLinksInfo()
	{
		this.links = new Hashtable<String, HashSet<String>>();
	}
	
	private Stack<LinkData> getLinkDataStack()
	{
		Stack<LinkData> stack = new Stack<LinkData>();
		while (links.keys().hasMoreElements())
		{
			String key = links.keys().nextElement();
			HashSet<String> names = links.get(key);
			String[] saveAsNames = names.toArray(new String[names.size()]);
			stack.add(new LinkData(key, saveAsNames));
		}
		return stack;
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
	
	//public Stack<LinkData> read(String pathToLinksFile) throws UncorrectLinkException, FileNotFoundException, IOException
	public Stack<LinkData> read(IDataLinksSource source) throws UncorrectLinkException, IOException
	{
		try
		{
			String linkInf;
			//BufferedReader in = new BufferedReader(new FileReader(pathToLinksFile));
			while ((linkInf = source.readLine()) != null)
			{
				String[] strs = linkInf.split(" ");
				if (isLinkCorrect(strs))
				{
					addLink(strs[0], strs[1]);
				}
			}
			//source.close();
			
			return getLinkDataStack();
		}
		catch (UncorrectLinkException e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		//catch (FileNotFoundException e)
		//{
		//	System.out.println("File " + pathToLinksFile + " not founded");
		//	throw e;
		//}
		//catch (IOException e)
		//{
		//	System.out.println("File read error");
		//	throw e;
		//}
	}
}
