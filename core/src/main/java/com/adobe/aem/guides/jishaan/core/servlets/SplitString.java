package com.adobe.aem.guides.jishaan.core.servlets;

import java.util.Scanner;

public class SplitString {

	public static void main(String args[]) {
//construtor of the Scanner class    
		Scanner scan = new Scanner("/content/jishaan/us/en/pagetwo/jcr:content/root/pagelinking");
//Initialize the string delimiter    
		scan.useDelimiter("/root");
//checks if the tokenized Strings has next token  
		while (scan.hasNext()) {
//prints the next token      
			System.out.println(scan.next());
		}
//closing the scanner  
		scan.close();
		
		
		
		String[] tokens = "/content/jishaan/us/en/pagetwo/jcr:content/root/pagelinking".split("/root");
		 System.out.println("First string-->"+tokens[0]);
		for (String token : tokens)
		{
		    System.out.println("Splitted-->{}"+token);
		}
	}
}