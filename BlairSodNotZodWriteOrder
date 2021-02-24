/*
 * AUTHOR: Corey Blair
 * COURSE: CPT187
 * PURPOSE 		 This will write the orders down to a list so that it can be read back to the customers at a later point. The list includes the item's ID, Name, Price
 * 					how many were purchaesd, and the cost for the order. 
 * CREATEDATE: 	February 18th, 2021
 */
package edu.cpt187.blair.exercise6;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteOrder 
{
	private boolean fileFoundFlag = false;
	private String masterFileName = "";
	private int recordCount = 0;

	//NON DEFAULT CONSTRUCTOR
	public WriteOrder(String borrowedFileName)
	{
		masterFileName = borrowedFileName;
	}//END OF NON-DEFAULT CONSTRUCTOR
	
	public void setWriteOrder(int borrowedItemID, String borrowedItemName, double borrowedItemPrice, int borrowedQuantity, double borrowedOrderCost)
	{
		try
		{
			PrintWriter filePW = new PrintWriter(new FileWriter(masterFileName, fileFoundFlag));
			filePW.printf("%n%d\t%s\t%.2f\t%d\t%.2f", borrowedItemID, borrowedItemName, borrowedItemPrice, borrowedQuantity, borrowedOrderCost);
			fileFoundFlag = true;
			recordCount++;
			filePW.close();
		}//END OF TRY
		
		catch(IOException ex)
		{
			fileFoundFlag = false;
		}//END OF CATCH
	}//END OF setWriteOrder
	
	public boolean getFileFoundFlag()
	{
		return fileFoundFlag;
	}//END OF getFileFoundFlag
	public String getFileName()
	{
		return masterFileName;
	}//END OF getFileName
	public int getRecordCount()
	{
		return recordCount;
	}//END OF getRecordCount
	
}//END OF CLASS
