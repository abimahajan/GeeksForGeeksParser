package com.category.geeksForGeeks;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class DownloadFile
{
	public void downloadFromMap(Map<String, String> questionsMap, String categoryName) 
	{
		Document doc;
		//create directory
		File dir=createDirectory(categoryName);
		
		Iterator<Entry<String, String>> entries = questionsMap.entrySet().iterator();
		while (entries.hasNext()) 
		{
		    Entry<String, String> entry = entries.next();
		    try {
		    	
				doc = Jsoup.connect(entry.getValue()).get();
				
				String fileName=entry.getKey()+".html";
				
				//characters not allowed while creating file name
				for(String stop:Geeks.stopCharacterFolder)
				{
					if(fileName.contains(stop))
						fileName=fileName.replaceAll("\\"+stop, "");
				}
				
				createFile(dir,fileName);
				BufferedWriter out = new BufferedWriter(new FileWriter(Geeks.baseFolder+categoryName+"/"+fileName));
			    out.write(doc.html());
			    out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void createFile(File dir, String fileName) 
	{
		File tagFile=new File(dir,fileName);
		if(!tagFile.exists())
		{
			try 
			{
				tagFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static File createDirectory(String categoryName) 
	{
		File dir=new File(Geeks.baseFolder+categoryName);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		return dir;
	}
	

}
