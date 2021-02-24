/*
 * AUTHOR: Corey Blair
 * COURSE: CPT187
 * PURPOSE 		 this program will allow one to log in to, or create accounts for use within the shopping program. Upon logging in, one will be asked 
 * CREATEDATE: 	February 18th, 2021
 */
package edu.cpt187.blair.exercise6;

import java.util.Scanner;
public class MainClass
{
	public static final char MENU_CHARS[] = {'A', 'B', 'Q'};
	public static final String MENU_OPTIONS[] = {"Login", "Create an Account", "Quit"};
	public static final char FILE_MENU_CHARS[] = {'A', 'B', 'R'};
	public static final String FILE_MENU_OPTIONS[] = {"Load Inventory", "Create Order", "Return to Main Menu"};
	public static final char SUB_MENU_CHARS[] = {'A', 'B', 'C', 'D'};
	public static final String INVENTORY_FILE_NAME = "MasterOrderFile.dat";
	public static final String ACCOUNTS_FILE_NAME = "MasterUserFile.dat";
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		Order currentOrder = new Order();
		Inventory currentInventory = new Inventory();
		WriteOrder orders = new WriteOrder(INVENTORY_FILE_NAME);
		UserAccounts currentUser = new UserAccounts(ACCOUNTS_FILE_NAME);
		String username = "";
		char menuSelection = ' ';
		displayWelcomeBanner();

		menuSelection=validateMenuSelection(input);

		while(menuSelection != 'Q')
		{
			currentUser.setUserAccountArrays();
			username = getUserName(input);
			if(menuSelection != 'A')
			{
				currentUser.setSearchedIndex(username);
				if(currentUser.getSearchedIndex()  >= 0)
				{
					displayAccountResults(username);
				}//end of if
				else
				{
					currentUser.setWriteOneRecord(username, getPassword(input));
					displayAccountResults();
				}//end of else

			}//end of if
			else
			{
				currentUser.setSearchedIndex(username, getPassword(input));
				if(currentUser.getSearchedIndex() < 0)
				{
					displayLoginError();


				}//end of if
				else
				{
					menuSelection = validateFileSelection(input);
					while(menuSelection != 'R')
					{
						if(menuSelection == 'A')
						{
							currentInventory.setLoadItems(getFileName(input));
							if(currentInventory.getRecordCount() <= 0)
							{
								displayFileError();
							}//end of if
							else
							{
								displayRecordReport(currentInventory.getRecordCount());

							}//end of else
						}//end of if
						else
						{
							currentInventory.setSearchIndex(validateSearchValue(input));
							if(currentInventory.getItemSearchIndex() < 0)
							{
								displayNotFound();
							}//end of if
							else
							{
								currentOrder.setLastItemSelectedIndex(currentInventory.getItemSearchIndex());
								currentOrder.setItemID(currentInventory.getItemIDs());
								currentOrder.setItemPrice(currentInventory.getItemPrices());
								currentOrder.setItemName(currentInventory.getItemNames());
								currentOrder.setHowMany(validateHowMany(input));
								if(currentOrder.getInStockCount(currentInventory.getInStockCounts()) < currentOrder.getHowMany())
								{
									displayOutOfStock();
								}//end of if
								else
								{
									currentOrder.setDiscountType(validateDiscountMenu(input, currentInventory.getDiscountNames(), currentInventory.getDiscountRates()));
									currentOrder.setDiscountName(currentInventory.getDiscountNames());
									currentOrder.setDiscountRate(currentInventory.getDiscountRates());
									currentOrder.setDecreaseInStock(currentInventory);
									currentOrder.setPrizeName(currentInventory.getPrizeNames(), currentInventory.getRandomNumber());
									orders.setWriteOrder(currentOrder.getItemID(), currentOrder.getItemName(), currentOrder.getItemPrice(), currentOrder.getHowMany(), currentOrder.getTotalCost());
									if(currentOrder.getDiscountRate() > 0.0)
									{
										displayOrderReport(username, currentOrder.getItemName(), currentOrder.getItemPrice(), currentOrder.getHowMany(), currentOrder.getDiscountName(), currentOrder.getDiscountRate(), currentOrder.getDiscountAmt(), currentOrder.getDiscountPrice(), currentOrder.getSubTotal(), currentOrder.getTaxRate(), currentOrder.getTaxAmt(), currentOrder.getTotalCost(), currentOrder.getPrizeName(), currentOrder.getInStockCount(currentInventory.getInStockCounts()));
									}//end of if
									else
									{
										displayOrderReport(username, currentOrder.getItemName(), currentOrder.getItemPrice(), currentOrder.getHowMany(),  currentOrder.getSubTotal(), currentOrder.getTaxRate(), currentOrder.getTaxAmt(), currentOrder.getTotalCost(), currentOrder.getPrizeName(), currentOrder.getInStockCount(currentInventory.getInStockCounts()));
									}//end of else
								}//end of else 

							}//end of else
						}//end of else
						menuSelection = validateFileSelection(input);
					}//end of if

				}//end of while

			}//end of else
			menuSelection = validateMenuSelection(input);
		}//end of while




				
		currentInventory.setLoadItems(orders.getFileName(), orders.getRecordCount());
		if(orders.getRecordCount() > 0)
		{

			displayFinalReport(currentInventory.getItemIDs(), currentInventory.getItemNames(), currentInventory.getItemPrices(), currentInventory.getOrderQuantities(), currentInventory.getOrderTotals(), currentInventory.getRecordCount(), currentInventory.getGrandTotal());
		}
		displayFarewellMessage();
		input.close();
		
	}//end of MAIN method

	//void methods
	public static void displayWelcomeBanner()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Welcome to The Hardware-ish Store");
		System.out.println("This will allow you to log into or create accounts to");
		System.out.println("purchase items from a catalog and then apply discounts.");
		System.out.println("You will then receive an order report, and once all");
		System.out.println("purchases have been made, a final order report.");
	}//endof displayWelcomeBanner

	public static void displayMainMenu()
	{
		int localIndex = 0;
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("MAIN MENU");
		while(localIndex < MENU_CHARS.length)
		{
			System.out.printf("%1c for %1s\n", MENU_CHARS[localIndex], MENU_OPTIONS[localIndex]);
			localIndex++;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Enter your selection here: ");
	}//end of displayMainMenu
	public static void displayFileMenu()
	{
		int localIndex = 0;
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("FILE MENU");
		while(localIndex < FILE_MENU_CHARS.length)
		{
			System.out.printf("%1c for %1s\n", FILE_MENU_CHARS[localIndex], FILE_MENU_OPTIONS[localIndex]);
			localIndex++;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Enter your selection here: ");
	}//end of displayMainMenu
	public static void displayFileNamePrompt()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Enter the file name with extension, i.e. file.txt");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Choose your file here: ");

	}//end of displayFileNamePrompt


	public static void displayDiscountMenu(String[] borrowedDiscountNames, double[] borrowedDiscountRates)
	{
		int localIndex = 0;
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("DISCOUNT MENU");
		while(localIndex < borrowedDiscountNames.length)
		{
			System.out.printf("%c %2s %-20s%5.1f%2s\n", SUB_MENU_CHARS[localIndex], "for", borrowedDiscountNames[localIndex], borrowedDiscountRates[localIndex]*100, "%");
			localIndex++;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Please make your selection here: ");
	} //end of displayDiscountMenu

	public static void displayOrderReport(String borrowedUserName, String borrowedItemName, double borrowedItemPrice, int borrowedHowMany, String borrowedDiscountName, double borrowedDiscountRate, double borrowedDiscountAmt, double borrowedDiscountPrice, double borrowedSubTotal, double borrowedTaxRate, double borrowedTax, double borrowedTotalAmount, String borrowedPrizeName, int borrowedItemStock)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("ORDER REPORT");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%-20s%s","Customer Name:", borrowedUserName);
		System.out.printf("\n%-20s%s","Item Name:", borrowedItemName);
		System.out.printf("\n%-20s%s%10.2f\n","Item Price:", "$", borrowedItemPrice);
		System.out.printf("\n%-20s%s","Discount Name:", borrowedDiscountName);
		System.out.printf("\n%-20s%10.1f%2s","Discount Rate:", borrowedDiscountRate*100, "%");
		System.out.printf("\n%-20s%s%10.2f","Discount Amount:", "$", borrowedDiscountAmt);
		System.out.printf("\n%-20s%s%10.2f\n","Discount Price:","$", borrowedDiscountPrice);
		System.out.printf("\n%-20s%11d\n", "Quantity:", borrowedHowMany);
		System.out.printf("\n%-20s%s%10.2f","Subtotal:", "$", borrowedSubTotal);
		System.out.printf("\n%-20s%10.1f%2s","Tax Rate:", borrowedTaxRate*100, "%");
		System.out.printf("\n%-20s%s%10.2f","Tax Amount:", "$", borrowedTax);
		System.out.printf("\n%-20s%s%10.2f\n", "Order Total:", "$", borrowedTotalAmount);
		System.out.printf("\n%-20s%s\n", "Prize:", borrowedPrizeName);
		System.out.printf("%-13s%d%6s%s%s\n", "Buy now only ", borrowedItemStock, "more ", borrowedItemName, " in stock");
	} // end of displayOrderReport
	public static void displayOrderReport(String borrowedUserName, String borrowedItemName, double borrowedItemPrice, int borrowedHowMany, double borrowedSubTotal, double borrowedTaxRate, double borrowedTax, double borrowedTotalAmount, String borrowedPrizeName, int borrowedItemStock)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("ORDER REPORT");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%-20s%s","Customer Name:", borrowedUserName);
		System.out.printf("\n%-20s%s","Item Name:", borrowedItemName);
		System.out.printf("\n%-20s%s%10.2f\n","Item Price:", "$", borrowedItemPrice);
		System.out.printf("\n%-20s%11d\n", "Quantity:", borrowedHowMany);
		System.out.printf("\n%-20s%s%10.2f","Subtotal:", "$", borrowedSubTotal);
		System.out.printf("\n%-20s%10.1f%2s","Tax Rate:", borrowedTaxRate*100, "%");
		System.out.printf("\n%-20s%s%10.2f","Tax Amount:", "$", borrowedTax);
		System.out.printf("\n%-20s%s%10.2f\n", "Order Total:", "$", borrowedTotalAmount);
		System.out.printf("\n%-20s%s\n", "Prize:", borrowedPrizeName);
		System.out.printf("%-13s%d%6s%s%s\n", "Buy now only ", borrowedItemStock, "more ", borrowedItemName, " in stock");
	} // end of overloaded displayOrderReport
	public static void displayRecordReport(int borrowedRecordCount)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("RECORD REPORT");
		System.out.printf("%-40s%s\n", "Records Processed:", borrowedRecordCount);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	}
	public static void displayFinalReport(int[] borrowedItemIDs, String[] borrowedItemNames, double[] borrowedItemPrices, int[] borrowedItemQuantities, double[] borrowedTotals, int borrowedRecords, double borrowedGrandTotal)
	{
		int localIndex = 0;
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("FINAL REPORT");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%-6s%s%20s%12s%6s\n", "ID", "NAME", "PRICE", "QTY", "TOTAL");
		while(localIndex < borrowedRecords)
		{
			System.out.printf("%-6d%-18s%-2s%.2f%12d%2s%6.2f\n", borrowedItemIDs[localIndex], borrowedItemNames[localIndex],"$", borrowedItemPrices[localIndex], borrowedItemQuantities[localIndex], "$",borrowedTotals[localIndex]);
			localIndex++;
		}
		System.out.printf("\n%s", "GRAND TOTAL");
		System.out.printf("\n%-3s%.2f\n", "$", borrowedGrandTotal);

	} //end of displayFinalReport
	public static void displayFarewellMessage()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Thank you for using this program!");
		System.out.println("Please feel free to use this program again!");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}//end of displayFarewellMessage()
	public static void displayInventoryLoaded()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("INVENTORY LOADED REPORT");
		System.out.println("Inventory in-stock counts, have been processed");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayOutOfStock()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("OUT OF STOCK ERROR");
		System.out.println("The quantity entered is greater than the quantity in-stock");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayFileError()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("FILE ERROR");
		System.out.println("The file named was not found or could not be opened.");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayNotFound()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("NOT FOUND ERROR");
		System.out.println("The search value was not found");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayAccountResults(String borrowedUsername)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("ACCOUNT RESULTS");
		System.out.printf("%s%s%s\n", "Account not created: username, ", borrowedUsername, ", already exists.");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayAccountResults()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("ACCOUNT RESULTS");
		System.out.println("New account created.");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayLoginError()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("LOGIN ERROR");
		System.out.println("Username and/or Password is incorrect");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	//vr methods
	public static String getUserName(Scanner borrowedInput)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Enter your username:");
		String localUserName = "";
		localUserName = borrowedInput.next();
		return localUserName;
	} // end of getUserName();
	public static char validateMenuSelection(Scanner borrowedInput)
	{
		displayMainMenu();
		char localSelection = ' ';
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while(localSelection != 'A' && localSelection != 'B' && localSelection != 'Q')
		{
			System.out.println("Sorry, that's not a valid input. Please try again.");
			displayMainMenu();
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}
		return localSelection;
	}//end of validateMainMenu
	public static char validateFileSelection(Scanner borrowedInput)
	{
		displayFileMenu();
		char localSelection = ' ';
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while(localSelection != 'A' && localSelection != 'B' && localSelection != 'R')
		{
			System.out.println("Sorry, that's not a valid input. Please try again.");
			displayFileMenu();
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}
		return localSelection;
	}//end of validateMainMenu
	public static String getFileName(Scanner borrowedInput)
	{
		displayFileNamePrompt();
		String localSelection = borrowedInput.next();
		return localSelection;
	} //getFileName
	public static char validateDiscountMenu(Scanner borrowedInput, String[] borrowedDiscountNames, double[] borrowedDiscountRates)
	{

		displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
		char localSelection= ' ';
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while(localSelection != 'A' && localSelection != 'B' && localSelection != 'C')
		{
			System.out.println("Sorry, that's not a valid input. Please try again.");
			displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}
		return localSelection;
	}//end of validateDiscountMenu

	public static String validateHowMany(Scanner borrowedInput)
	{
		System.out.print("\nHow many would you like to purchase? Please input here:");
		String localHowMany = " ";
		localHowMany= borrowedInput.next();
		while(Integer.parseInt(localHowMany) <= 0)
		{
			System.out.println("Sorry, that's not a valid amount, please input an amount greater than 0");
			System.out.print("\nHow many would you like to purchase? Please input here:");
			localHowMany = borrowedInput.next();
		}
		return localHowMany;
	}//end of validateHowMany
	public static String validateSearchValue(Scanner borrowedInput)
	{

		String localSearchValue = "";
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Enter the search value here:");
		localSearchValue = borrowedInput.next();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return localSearchValue;
	}//end of validateHowMany
	public static String getPassword(Scanner borrowedInput)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Enter your password: ");
		return borrowedInput.next();
	}//end of getPassword
}	//end of class

