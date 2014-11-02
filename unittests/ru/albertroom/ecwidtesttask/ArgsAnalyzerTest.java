package ru.albertroom.ecwidtesttask;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import ru.albertroom.ecwidtesttask.downloader.services.Output;

public class ArgsAnalyzerTest {
	
	private String[] args = {"-n", "2", "-l", "2000", "-o", "output_folder", "-f", "d:\\MyProjects\\EcwidTestTask\\links.txt"};
	ArgsAnalyzer parser;
	
	public ArgsAnalyzerTest()
	{
		parser = new ArgsAnalyzer();
		Output.switchOff(); // запрет на вывод в консоль
	}
	
	
	
	@Test
	public void testParse() 
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add("-n");
		params.add("2");
		params.add("-l");
		params.add("2000");
		params.add("-o");
		params.add("output_folder");
		params.add("-f");
		params.add("d:\\MyProjects\\EcwidTestTask\\links.txt");
		
		try {
			parser.parse(params.toArray(new String[params.size()]));
		} catch (ParseException e) {
			fail( "Method 'parse' threw exception, but he did not should do this" );
		}
		
		while (params.size() != 0)
		{
			params.remove(0);
			try {
				parser.parse(params.toArray(new String[params.size()]));
				fail("Method 'parse' did not throw exception, but he did should do this");
			} catch (ParseException e) {
				//do nothing
			}
		}
	}
	
	@Test
	public void testParseResult() 
	{
		boolean haveArgs = false;
		try {
			haveArgs = parser.parse(args);
			assertEquals(haveArgs, true);
			
			haveArgs = parser.parse(new String[]{"-help"});
			assertEquals(haveArgs, false);
		}
		catch (ParseException e)
		{
			//e.printStackTrace();
			fail("testParseResult fail");
		}		
	}
	
	@Test
	public void testGetNumberOfThreads() 
	{
		try {
			parser.parse(args);
			assertEquals(2, parser.getNumberOfThreads());
		} catch (ParseException e) {
			fail("ParseException on running getNumberOfThreads");
		}
	}

	@Test
	public void testGetDownloadingSpeedLimit() {
		try {
			parser.parse(args);
			assertEquals(2000, parser.getDownloadingSpeedLimit());
			
			String[] args_kb = {"-n", "2", "-l", "2000k", "-o", "output_folder", "-f", "d:\\MyProjects\\EcwidTestTask\\links.txt"};
			parser.parse(args_kb);
			assertEquals(2000*1024, parser.getDownloadingSpeedLimit());
			
			String[] args_mb = {"-n", "2", "-l", "2000m", "-o", "output_folder", "-f", "d:\\MyProjects\\EcwidTestTask\\links.txt"};
			parser.parse(args_mb);
			assertEquals(2000*1024*1024, parser.getDownloadingSpeedLimit());
		} catch (ParseException e) {
			fail("ParseException on running getDownloadingSpeedLimit");
		}
	}


	@Test
	public void testGetSaveFolder() {
		try {
			parser.parse(args);
			assertEquals("output_folder", parser.getSaveFolder());
		} catch (ParseException e) {
			fail("ParseException on running getSaveFolder");
		}
	}

	@Test
	public void testGetPathToLinksList() {
		try {
			parser.parse(args);
			assertEquals("d:\\MyProjects\\EcwidTestTask\\links.txt", parser.getPathToLinksList());
		} catch (ParseException e) {
			fail("ParseException on running getPathToLinksList");
		}
	}
}
