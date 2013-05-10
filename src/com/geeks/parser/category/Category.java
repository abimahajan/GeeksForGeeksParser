package com.geeks.parser.category;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.geeks.parser.utils.DownloadFile;
import com.geeks.parser.utils.PropertiesFileConfig;

public class Category 
{
	
	public static Map<String,String> questionsMap = new HashMap<String,String>();
	
	public static void main(String[] args)  
	{
		//read from properties file
		PropertiesFileConfig.setPropertyVarialbles();
		
		if(Boolean.parseBoolean(PropertiesFileConfig.isProxyRequired))
		{
			System.setProperty("http.proxyHost",PropertiesFileConfig.proxyHost);
			System.setProperty("http.proxyPort",PropertiesFileConfig.proxyPort);
		}
		
		DownloadFile download=new DownloadFile();
		
		Document doc;
		for (String linkToParse : PropertiesFileConfig.linksList) 
		{
			try 
			{
				doc = Jsoup.connect(linkToParse).get();
				
				//get number of pages in that category
				String pages=doc.select(PropertiesFileConfig.categoryDivForPages).select(PropertiesFileConfig.categorySpanForPages).text();
				if(pages.contains("of"))
				{
					pages=pages.split("of")[1].trim();
				}
				
				for (int j = 1; j <=Integer.parseInt(pages); j++)
				{
					if(j!=1)
						doc = Jsoup.connect(linkToParse+"page/"+j+"/").get();
					Elements links=doc.select(PropertiesFileConfig.categoryDivForLinks);
					
					for(Element link:links)
					{
						String title = link.text();
			        	String href = link.attr("href");
			        	//store each title with its URL in map
			        	questionsMap.put(title, href);
					}
				}
				//download html for each category
				download.downloadFromMap(questionsMap,StringUtils.substringAfterLast(StringUtils.substringBeforeLast(linkToParse, "/"),"/"));
				questionsMap.clear();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
    }

	

}
