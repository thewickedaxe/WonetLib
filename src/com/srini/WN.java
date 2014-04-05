
package com.srini;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example program to list links from a URL.
 */
public class WN 
{
	
	
	public ArrayList<Definition> getter(String word) throws IOException
	{
		ArrayList<Definition> data=new ArrayList<Definition>();
		String url="http://wordnetweb.princeton.edu/perl/webwn?s="+word;
		Document doc = Jsoup.connect(url).get();
		Elements ele=doc.select("li");
		for(Element e:ele)
		{
			Definition d=new Definition();
			String t=e.text()+"";
			if(t.contains("(n)"))
			{
				d.type="noun";
			}
			else if(t.contains("(v)"))
			{
				d.type="verb";
			}
			else if(t.contains("(adj)"))
			{
				d.type="adjective";
			}
			if(t.contains("(adv)"))
			{
				d.type="adverb";
			}
			int j=0;
			 Pattern pattern1 = Pattern.compile("\\)([^)]+)\\(");
			    Matcher matcher1 = pattern1.matcher(t);
			    while (matcher1.find()) {
			    	if(j==0)
			        {
			    		d.synonyms=matcher1.group(1).toString();
			    		//System.out.println(matcher1.group(1));
			        }
			    	j++;
			    }
			    int i=0;
				 Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
				    Matcher matcher = pattern.matcher(t);
				    while (matcher.find()) {
				    	if(i==1)
				        {
				    		d.meaning=matcher.group(1).toString();
				    		//System.out.println(matcher.group(1));
				        }
				    	i++;
				    }
				    data.add(d);
			//System.out.println(e.text()+"");
		}
		
		return data;
	}
	public static void main(String[] args) throws IOException 
	{
       	//url contains the predefined wordnet search url string without the search word itself
    //	String url="http://wordnetweb.princeton.edu/perl/webwn?s=";
    ArrayList<Definition> stuff;
	WN w=new WN();   
    stuff=w.getter("car");
    for(Definition d:stuff)
    {
    	System.out.println("car");
    	System.out.println("part of speech: "+d.type);
    	System.out.println("definition: "+d.meaning);
    	System.out.println("synonyms: "+d.synonyms);
    	System.out.println("");
    }
    }
}