package com.geeks.parser.company;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.geeks.parser.utils.DownloadFile;
import com.geeks.parser.utils.PropertiesFileConfig;

public class CompanySpecific {

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
		
		for (String companyName : PropertiesFileConfig.companyNameList) 
		{
			try {
				
				String pages="1";
				
				if(companyName.contains(" "))
					companyName=companyName.replace(" ", "-");
				
				for (String exceptionCompany : PropertiesFileConfig.exceptionCompanyList) 
				{
					if(companyName.equalsIgnoreCase(exceptionCompany))
						companyName=companyName.concat("-2");
					
				}
				
				doc = Jsoup.connect(PropertiesFileConfig.companyBaseUrl+companyName.toLowerCase()+"/").get();

				if(doc.select(PropertiesFileConfig.companyClassForPages).size()>0)
					pages=doc.select(PropertiesFileConfig.companyClassForPages).get(1).text();
				
				for (int i = 1; i <= Integer.parseInt(pages); i++) 
				{
					int j=0;
					if(i!=1)
						doc = Jsoup.connect(PropertiesFileConfig.companyBaseUrl+companyName.toLowerCase()+"/"+"page/"+i+"/").get();
					
					Elements links=doc.select(PropertiesFileConfig.companyClassForLinks);
					
					for(Element link:links)
					{
						String title = link.text();
			        	String href = link.attr("href");
			        	
			        	if(questionsMap.containsKey(title))
			        		title=title+"_"+j++;

			        	//store each title with its URL in map			        		
			        	questionsMap.put(title, href);
					}
					
					download.downloadFromMap(questionsMap, companyName);
				}
				
				
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
	}

}
