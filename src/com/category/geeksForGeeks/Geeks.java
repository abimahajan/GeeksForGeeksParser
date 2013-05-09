package com.category.geeksForGeeks;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Geeks 
{
	
	public static String[] linksList;
	public static String isProxyRequired;
	public static String proxyHost;
	public static String proxyPort;
	public static String divForPages;
	public static String spanForPages;
	public static String baseFolder;
	public static String[] stopCharacterFolder;
	public static Map<String,String> questionsMap = new HashMap<String,String>();
	
	
	public static void main(String[] args)  
	{
		Geeks geek=new Geeks();
		//read from properties file
		geek.setPropertyVarialbles();

		if(Boolean.parseBoolean(isProxyRequired))
		{
			System.setProperty("http.proxyHost",proxyHost);
			System.setProperty("http.proxyPort",proxyPort);
		}
		
		DownloadFile download=new DownloadFile();
		
		Document doc;
		for (String linkToParse : linksList) 
		{
			try 
			{
				doc = Jsoup.connect(linkToParse).get();
				
				//get number of pages in that category
				System.out.println(doc.html());
				String pages=doc.select(divForPages).select(spanForPages).toString();
				pages=StringUtils.substringBetween(pages, "<span class=\"pages\">", "</span>");
				if(pages.contains("of"))
				{
					pages=pages.split("of")[1].trim();
				}
				
				for (int j = 1; j <=Integer.parseInt(pages); j++)
				{
					if(j!=1)
						doc = Jsoup.connect(linkToParse+"page/"+j+"/").get();
					Elements links=doc.select("#content h2 a");
					
					for(Element link:links)
					{
						String title = link.text();
			        	String href = link.attr("href");
			        	//store each title with its URL in map
			        	questionsMap.put(title, href);
					}
				}
				download.downloadFromMap(questionsMap,StringUtils.substringAfterLast(StringUtils.substringBeforeLast(linkToParse, "/"),"/"));
				questionsMap.clear();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
    }

	private void setPropertyVarialbles()
	{
		Properties prop = new Properties();
		 
    	try
    	{
            //load a properties file
    		prop.load(new FileInputStream("./src/geeks.properties"));
 
            //get the different property values
    		if(prop.containsKey("LINKS_LIST"))
    			linksList=prop.getProperty("LINKS_LIST").split("@");
    		
    		if(prop.containsKey("IS_PROXY_NEEDED"))
    			isProxyRequired=prop.getProperty("IS_PROXY_NEEDED");
    		
    		if(prop.containsKey("PROXY_HOST"))
    			proxyHost=prop.getProperty("PROXY_HOST");
    		
    		if(prop.containsKey("PROXY_PORT"))
    			proxyPort=prop.getProperty("PROXY_PORT");
    		
    		if(prop.containsKey("DIV_FOR_PAGES"))
    			divForPages=prop.getProperty("DIV_FOR_PAGES");
    		
    		if(prop.containsKey("SPAN_FOR_PAGES"))
    			spanForPages=prop.getProperty("SPAN_FOR_PAGES");
    		
    		if(prop.containsKey("BASE_FOLDER"))
    			baseFolder=prop.getProperty("BASE_FOLDER");
    		
    		if(prop.containsKey("STOP_CHARACTER_FOLDER"))
    			stopCharacterFolder=prop.getProperty("STOP_CHARACTER_FOLDER").split("@");
    		
           
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
 
	}

}
