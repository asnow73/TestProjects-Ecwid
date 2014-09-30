package ru.albertroom.ecwidtesttask.readlinkslist;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

import ru.albertroom.ecwidtesttask.downloader.LinkData;

//Класс для формирования стека с описаниями ссылок для скачивания
public class ReaderLinksInfo
{
	private Hashtable<String, HashSet<String>> links;
	
	public ReaderLinksInfo()
	{
		this.links = new Hashtable<String, HashSet<String>>();
	}
	
	//Формирование стека с описанием ссылок для скачивания
	private Stack<LinkData> getLinkDataStack()
	{
		Stack<LinkData> stack = new Stack<LinkData>();
		while (links.keys().hasMoreElements())
		{
			String key = links.keys().nextElement();
			HashSet<String> names = links.get(key);
			String[] saveAsNames = names.toArray(new String[names.size()]);
			stack.add(new LinkData(key, saveAsNames));
			links.remove(key);
		}
		return stack;
	}
	
	//Проверка корректности ссылки
	private boolean isLinkCorrect(String[] strs)
	{
		boolean result = true;
		if (strs.length != 2)
		{
			result = false;			
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
	
	//Прочиьтать из источника ссылки, установленного формата. Возвращается стек с описанием ссылок для скачивания
	//Пример ссылки: http://example.com/archive.zip my_archive.zip
	public Stack<LinkData> read(IDataLinksSource source) throws UncorrectLinkException, IOException
	{
		String linkInf;
		while ((linkInf = source.readLine()) != null)
		{
			String[] strs = linkInf.split(" ");
			if (isLinkCorrect(strs))
			{
				addLink(strs[0], strs[1]);
			}
			else
			{
				throw new UncorrectLinkException("Link is not correct");
			}
		}
		return getLinkDataStack();
	}
}
