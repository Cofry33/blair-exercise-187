/*
 * AUTHOR: Corey Blair
 * COURSE: CPT187
 * PURPOSE 		 This will create the orders that the customer by identifying to the rest of the system what exactly the item that the customer is looking for
 * 				 specifically, the item's name, price, the discount the chose and it's rate, and the name of the prize they won by shopping using the program.
 * CREATEDATE: 	Februrary 18th, 2021
 */
package edu.cpt187.blair.exercise6;


public class Order extends Inventory
{
	//declare and initialize class constants
	//these absolutely must be required for object attributes.
	
	private final double TAX_RATE = .075;
	//declare and initialize all non-constant class attributes
	private int discountCounts[];
	private int prizeCounts[];
	private int lastItemSelectedIndex = 0;
	private int discountType = 0;
	private int itemID = 0;
	private String itemName = "";
	private double itemPrice = 0.0;
	private String discountName = "";
	private double discountRate = 0.0;
	private int howMany = 0;
	private String prizeName = "";
	//Order class constructor
	public Order() 
	{
		
	}// END Order constructor.
	//Void methods, all of these set values, do not return any.
	//setter
	//this method will assign a calculated value to lastItemSelectedIndex
	public void setLastItemSelectedIndex(int borrowedMenuSelection)
	{
		lastItemSelectedIndex = borrowedMenuSelection;
	}
	public void setItemID(int[] borrowedItemIDs)
	{
		itemID = borrowedItemIDs[lastItemSelectedIndex];
	}
	//setter
	//This method will assign a new value to itemName as well as initialize itemCounts and increment itemCounts based on lastItemSelectedIndex.
	public void setItemName(String[] borrowedItemNames)
	{
		itemName = borrowedItemNames[lastItemSelectedIndex];
	}//END setItemName

	//setter
	//This method will assign a new value to itemPrice based on lastItemSelectedIndex 
	public void setItemPrice(double[] borrowedItemPrices)
	{
		itemPrice = borrowedItemPrices[lastItemSelectedIndex];
	}
	//setter
	//This method will assign a new value to itemName as well as increment itemCounts based on itemSelection 
	public void setHowMany(String borrowedHowMany)
	{
		howMany = Integer.parseInt(borrowedHowMany);
	}
	//setter
	//This method will assign a new value to itemName as well as increment itemCounts based on itemSelection 
	public void setDiscountType(char borrowedMenuSelection)
	{
		discountType = borrowedMenuSelection - 'A';
		
	}
	//setter
	//This method will assign a new value to itemName as well as increment itemCounts based on itemSelection 
	public void setDiscountName(String[] borrowedDiscountNames)
	{
		if(discountCounts == null)
		{
			 discountCounts = new int[borrowedDiscountNames.length]; 
		}//end of if
		discountName = borrowedDiscountNames[discountType];
		discountCounts[discountType]++;
	}
	public void setDiscountRate(double[] borrowedDiscountRate)
	{
		discountRate = borrowedDiscountRate[discountType];
	}
	public void setPrizeName(String[] borrowedPrizeNames, int borrowedPrizeIndex)
	{
		if(prizeCounts == null)
		{
			prizeCounts = new int[borrowedPrizeNames.length];
		}//end of if
		prizeName = borrowedPrizeNames[borrowedPrizeIndex];
		prizeCounts[borrowedPrizeIndex]++;
	}
	public void setDecreaseInStock(Inventory borrowedInventoryObject)
	{
		borrowedInventoryObject.setReduceStock(howMany);
	}
	
	//VR methods
	//the below methods return values
	public int getItemID()
	{
		return itemID;
	}
	public int getInStockCount(int[] borrowedInStockCounts)
	{
		return borrowedInStockCounts[lastItemSelectedIndex];
	}
	public String getItemName()
	{
		return  itemName;
	}//end of getItemName
	public double getItemPrice()
	{
		return itemPrice;
	} //end of getItemPrice
	public int getHowMany()
	{
		return howMany;
	} //end of getHowMany
	public String getDiscountName()
	{
		return  discountName;
	} //end of getDiscountName
	public double getDiscountRate()
	{
		return  discountRate;
	} //end of getDiscountRate
	public double getDiscountAmt()
	{
		return(itemPrice*discountRate);
	} //end of getDiscountAmt
	public double getDiscountPrice()
	{
		return(itemPrice-getDiscountAmt());
	} //end of getDiscountPrice
	public double getSubTotal()
	{
		return(getDiscountPrice()*getHowMany());
	} //end of getSubTotal
	public double getTaxRate()
	{
		return TAX_RATE;
	} //end of getTaxRate
	public double getTaxAmt()
	{
		return(getSubTotal()*TAX_RATE);
	} //end of getTaxAmt
	public double getTotalCost()
	{
		return(getSubTotal()+getTaxAmt());
	} //end of getTotalCost
	public int[] getDiscountCounts()
	{
		return discountCounts;
	} //end of getDiscountCounts
	public int[] getPrizeCounts()
	{
		return	prizeCounts;
	} // end of getPrizeCounts
	public String getPrizeName()
	{
		return prizeName;
	} //end of getPrizeName


} //end of CLASS





