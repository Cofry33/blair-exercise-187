/*
 * AUTHOR: Corey Blair
 * COURSE: CPT187
 * PURPOSE 		 THIS WILL ALLOW CUSTOMERS TO LOG INTO ACCOUNTS OR CREATE ACCOUNTS 
 * 				 SO THAT THEY MAY PURCHASE ITEMS FROM A CUSTOMER CHOSEN LIST OF AVAILABLE 
 * 			     ITEMS.
 * CREATEDATE: 	February 18th, 2021
 */
package edu.cpt187.blair.exercise6;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class UserAccounts 
{
	//INITIALIZE CLASS CONSTANTS
	private final int NOT_FOUND = -1;
	private final int RESET_VALUE = 0; 
	private final int MAXIMUM_RECORDS = 50;
	//DECLARE AND INITIALIZE ALL NON-CONSTANT CLASS ATTRIBUTES
	private String[] userNames = new String[MAXIMUM_RECORDS];
	private String[] passwords = new String[MAXIMUM_RECORDS];
	private String masterFileName = "";
	private int recordCount = 0;
	private int searchedIndex = 0; 
	
	
	//NON-DEFAULT CONSTRUCTOR
	public UserAccounts(String borrowedFileName)
	{
		 masterFileName = borrowedFileName;
	} //END OF NON-DEFAULT CONSTRUCTOR
	
	
	//setter 
	//this method will populate the arrays of usernames and passwords with the information from the file named masterFileName 
	//!!!!THIS METHOD WILL NOT DO ANYTHING THE FIRST TIME IT IS INVOKED BECAUSE THERE WILL BE NO INFORMATION FROM WHICH TO PULL!!!!!
	public void setUserAccountArrays()
	{
		try
		{
			Scanner infile = new Scanner(new FileInputStream(masterFileName));
			recordCount = RESET_VALUE;
			while(infile.hasNext() == true)
			{
				userNames[recordCount] = infile.next();
				passwords[recordCount] = infile.next();
				recordCount++;
			}//end of while
			infile.close();
		}//end of try
		catch(IOException ex)
		{
			recordCount = NOT_FOUND;
		}//end of catch
	}//end of setUserAccountArrays
	
	//setter
	//this method will assign getSeqSearch to searchedIndex
	public void setSearchedIndex(String borrowedUserName)
	{
		searchedIndex = getSeqSearch(borrowedUserName);
	}//end of setSearchedIndex
	
	//setter 
	//this overloaded method of setSearchedIndex will test if the passwords match for the username at searchedIndex
	public void setSearchedIndex(String borrowedUserName, String borrowedPassword)
	{
		//searchedIndex = RESET_VALUE; // DOING THIS FIXED AN ERROR in which you could "hack" into the account of someone you know the username of.
		if(searchedIndex >= RESET_VALUE && getPasswordMatch(borrowedPassword) == false)
		{
			searchedIndex = NOT_FOUND;
			
		}//end of if
		else 
		{
			
			searchedIndex =  getSeqSearch(borrowedUserName); 
		}//end of else
	}//end of overloaded setSearchedIndex
	
	//setter
	//this method will write 1(one) record to the master file, creating both a master file, if there is none already, as well as an account (one username and password) 
	public void setWriteOneRecord(String borrowedUserName, String borrowedPassword)
	{
		try
		{
			PrintWriter filePW = new PrintWriter(new FileWriter(masterFileName, true)); //had to set this to true becaues it would just overwrite every time. 
			filePW.printf("%s\t%s%n", borrowedUserName, borrowedPassword);
			recordCount++;
			filePW.close();
		}//end of try
		
		catch(IOException ex)
		{
			//unsure what to put here, I don't have a boolean to alter, so I just removed that line 
		}//end of catch
	}//end of setWriteOneRecord
	
	
	
	//getter
	//this method will search through an array using sequential search in order to acquire the desired value. 
	public int getSeqSearch(String borrowedBorrowedUserName)
	{
		int localIndex = 0; 
		int localFound = NOT_FOUND;
		
		while(localIndex < recordCount)
		{
			if(borrowedBorrowedUserName.equalsIgnoreCase(userNames[localIndex]))
			{
				localFound = localIndex;
				localIndex = recordCount;
			}//end of if
			localIndex++;
		}//end of while
		
		return localFound;
	}//end of getSeqSearch
	
	
	//getter
	//this method will check if the password in the parameters matches (case matters) the password in the array passwords at the index searchIndex, 
	public boolean getPasswordMatch(String borrowedBorrowedPassword)
	{
		return borrowedBorrowedPassword.equals(passwords[searchedIndex]);	// null pointer exception happening because I'm using .equals with the other way around. 
	}//end of getPasswordMatch
	
	
	//getter
	//this method returns masterFileName
	public String getFileName()
	{
		return masterFileName;
	}//end of getFileName
	
	//getter 
	//this method returns MAXIMUM_RECORDS
	public int getMaximumRecords()
	{
		return MAXIMUM_RECORDS;
	}//end of getMaximumREcords
	
	//getter
	//this method returns recordCount
	public int getRecordCount()
	{
		return recordCount;
	}//end of getRecordCount
	
	//getter
	//this method returns searchedIndex
	public int getSearchedIndex()
	{
		return searchedIndex;
	}//end of getSearchedIndex
}//end of class
