import java.time.LocalDateTime;
import java.io.File;  
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Fix_Product_List 
{

	protected static String name;						//name of the bought package
	protected static String currency;					//currency of the bought package
	protected static int quantity;						//amount of the bought package category
	protected static LocalDateTime creation_datetime;	//buying time of the package
	protected static int price;							//price of the bought package
	protected static String product_type;				//type of the bought package
	protected static String product_status;				//status of the bought package
	protected static int transaction_id;				//transaction id, which goes between 1 and 999
	protected static List<List<String>> fixed_products = new ArrayList<>();			//main store of the scanned data (first edition)
	protected static List<List<String>> fixed_products_bad = new ArrayList<>();		//wrong data having store
	protected static List<List<String>> fixed_products_final = new ArrayList<>();	//good data having store
	protected static List<String> name_of_packages = new ArrayList<>();		//storing all package names for examination
	protected static List<String> price_of_packages = new ArrayList<>();	//storing all package prices for examination
	protected static List<String> amount_of_packages = new ArrayList<>();	//storing all package amounts for examination
	protected static String[] one_transaction = new String[7];
	protected static List<List<String>> transactions = new ArrayList<>();	//storing all the transactions
	protected static List<String> transaction_ids = new ArrayList<>();		//storing all transaction ids for examination
	protected static int cash_to_give_back;									//cash to refund if refunding occurred
	protected static int package_amount;
	protected static int cash;
	protected static String input;											//stores the user given input
	
	public Fix_Product_List(int balance)
	{
		cash = balance;
	}
	
	//topping-up function behavior, where the user can type in the name of the wished package
	public static String topping_up(List<List<String>> checkit)
	{		
		Scanner scanner = new Scanner(System.in);
		
		int package_price = 0;				//price value of the package (initial value, that will change later on)
		String message_about_buying = "";	//logging message (initial value, that will change later on)
		String package_type_to_get = "";	//for gathering package type
		try 
		{	
			package_amount = 0;			//available quantity amount of the chosen package (initial value, that will change later on)
			input = scanner.nextLine();
			//reading all lines
	        for (List<String> s : checkit) 
	        {
	        	//reading all values in one line and gathering data out of the package name
	        	for(String i : s)
	        	{
	        		//package name
	        		if(s.indexOf(i) == 0)
	        		{
	        			name_of_packages.add(i);	//filling in the helper mini storage
	        		}
	        		//price of the package
	        		if(s.indexOf(i) == 1)
	        		{
		        		package_price = Integer.parseInt(i);
		        		price_of_packages.add(i);	//filling in the helper mini storage
	        		}
	        		//quantity of the chosen package available
	        		else if(s.indexOf(i) == 3)
	        		{
	        			package_amount = Integer.parseInt(i);
	        			amount_of_packages.add(i);	//filling in the helper mini storage
	        		}
	        		//type of the package
	        		else if(s.indexOf(i) == 5)
	        		{
	        			package_type_to_get = i;
	        		}
	        	}
	        }
	        //if there is package available
			if(name_of_packages.contains(input))
			{
				//helps finding the given package price and amount, because the mini storage elements are the same in amount
				int helper_index = name_of_packages.indexOf(input);	
				package_price = Integer.parseInt(price_of_packages.get(helper_index)); //example index 1 package name index 1 price
				package_amount = Integer.parseInt(amount_of_packages.get(helper_index)); //example index 1 package name index 1 amount
				
				//if there is package available, based on quantity
				if(package_amount > 0)
				{
					//if the user can't buy it
					if(package_price > cash)
					{
						message_about_buying = "can't pay for the package";
					}
					//if the user can buy the package
					else if(package_price <= cash)
					{	
						//logging messages
						System.out.println("Package to buy: " + input);
						System.out.println("Amount of this package available: " + package_amount);
						System.out.println("Giving one out of the available package");
						package_amount--;	//package amount obviously decreases
						for (List<String> s : checkit) 
				        {
				        	//reading all values in one line and gathering data out of the package name
				        	for(String i : s)
				        	{
				        		if(s.indexOf(i) == 3 && checkit.indexOf(s) == helper_index)
				        		{
				        			s.set(3, Integer.toString(package_amount));
				        		}
				        	}
				        }
						System.out.println("New amount of the available package: " + package_amount);
						message_about_buying = "Purchasing the given package";
						cash -= package_price;	//cash / balance of the customer obviously decreases
						System.out.println("New cash: " + cash);
						System.out.println("Total price: " + package_price);
	        			Random rn = new Random(); 
	        			transaction_id = rn.nextInt(999) + 1;
	        			System.out.println("Your transaction's id: " + transaction_id);

	        			//constructing the data for one transaction
						name = input;
						price = package_price;
	        			String[] sections_of_package = input.split(" ");	//for gathering words out of the package name 
	        			for(String j : sections_of_package)
	        			{
	        				currency = sections_of_package[2];
	        			}
	        			product_type = package_type_to_get;
	        			creation_datetime = LocalDateTime.now();
	        			product_status = "PAID";
	        			
	        			one_transaction[0] = Integer.toString(transaction_id);
	        			one_transaction[1] = name;
	        			one_transaction[2] = Integer.toString(price);
	        			one_transaction[3] = currency;
	        			one_transaction[4] = product_type;
	        			one_transaction[5] = creation_datetime.toString();
	        			one_transaction[6] = product_status;
	        			
	        			for (int i = 0; i < one_transaction.length; i++)
	        			{
	        				System.out.print(one_transaction[i] + " ");
	        			}
	        			
	        			transactions.add(Arrays.asList(one_transaction));        			
					}
					System.out.println("\n" + message_about_buying);
				    for (List<String> r : transactions) 
			        {
				    	for(String s : r)
				    	{
				    		if(r.indexOf(s) == 0)
			        		{
				    			transaction_ids.add(s);	//filling in the helper mini storage
			        		}
				    	}				    	
			        }
				}
				else
				{
					System.out.println("Sorry, but presently we have no available given package!");
					throw new Exception("Sorry, but presently we have no available given package!");
				}
			}
			else
			{
				System.out.println("Sorry, but we can't find the product, that you are asking for!");
				throw new Exception("Sorry, but we can't find the product, that you are asking for!");
			}
		} 
		catch (Exception e) 
		{
			input = "Sorry, but we can't find the product, that you are asking for! Error occured!";
		}
		return input;
	}
	
	public static void refunding()
	{
		try
		{
		    if(transactions.isEmpty())
			{
				throw new Exception("Sorry, but we can't find any transaction!");
			}
			else
			{
				System.out.println("Proceeding to refunding process!");
				System.out.println("Give the transaction id!");
				Scanner scanner = new Scanner(System.in);
				String id_to_get = scanner.nextLine();
				if(transaction_ids.contains(id_to_get))
				{
					cash_to_give_back += Integer.parseInt(one_transaction[2]);
					System.out.println("cash to give back: " + cash_to_give_back);
					product_status = "REFUNDED";
					one_transaction[6] = product_status;
					System.out.println("Successfully refunded the given transaction!");
					for(String z : one_transaction)
					{
						System.out.print(z + " ");
					}
					System.out.println();
					int helper_index = name_of_packages.indexOf(input);	
					package_amount = Integer.parseInt(amount_of_packages.get(helper_index)); //example index 1 package name index 1 amount
					for (List<String> s : fixed_products_final) 
			        {
			        	//reading all values in one line and gathering data out of the package name
			        	for(String i : s)
			        	{
			        		if(s.indexOf(i) == 3 && fixed_products_final.indexOf(s) == helper_index)
			        		{
			        			s.set(3, Integer.toString(package_amount));
			        		}
			        	}
			        }
					System.out.println("Present cash: " + cash);
					System.out.println("The refunded package is back in our list!");
					System.out.println("Amount of this package available after the refund: " + package_amount);
				}
				else
				{
					System.out.println("Sorry, but we can't find the transaction!");
					throw new Exception("Sorry, but we can't find the transaction!");
				}	
			}
		}
		catch (Exception e)
		{
			System.out.println("There was an error: " + e);
		}
	}
	
	//the core of the entire class file, where the data from the text file is read into a collection
	//only the lines where the data is not missing or wrong will be used
	public static void Storage_Constructor()
	{		    
		try 
	    {
	        File myObj = new File("fix_product_list.txt");
	        Scanner myReader = new Scanner(myObj);
	        
			String needed_segmented_word_for_fixed_price = "";	//for gathering the fixed price of the package name
			String needed_segmented_word_for_currency = "";		//for gathering the name of the currency out of the package name
			
			//each line is an array, where the data are separated with ; character
	        while (myReader.hasNextLine()) 
	        {
	          fixed_products.add(Arrays.asList(myReader.nextLine().split(";")));
	        }
	        List<Integer> index_of_bad_data_line = new ArrayList<>(); 	//for identifying the wrong data or missing data having lines
	        //reading all lines
	        for (List<String> s : fixed_products) 
	        {
	        	//reading all values in one line
	        	for(String i : s)
	        	{
	        		//gathering data out of the package name
	        		if(s.indexOf(i) == 0)
	        		{
	        			String experimental = i;	//the entire package name
	        			String[] segmented_words = experimental.split(" ");	//for gathering words out of the package name
	        			  
	        			for(String j : segmented_words)
	        			{
	        				needed_segmented_word_for_fixed_price = segmented_words[1];
	        			}
	        			needed_segmented_word_for_currency = segmented_words[2];
	        		}	        		  
	        		  
	        		//if the fixed price value is missing or wrong
	        		else if(s.indexOf(i) == 1 && (i.length() == 0 || i.charAt(0) == '-' || !(i.equals(needed_segmented_word_for_fixed_price))))
	        		{
	        			index_of_bad_data_line.add(fixed_products.indexOf(s));
	        		}
	        		  
		      		//if the currency value is not equal to the one given in the package name or empty
		      		else if(s.indexOf(i) == 2 && (i.length() == 0 || !(i.equals(needed_segmented_word_for_currency))))
		      		{
		      			index_of_bad_data_line.add(fixed_products.indexOf(s));
		      		}
	        		  
	        		//currency setting if there is no missing or wrong data value
	        		else if(s.indexOf(i) == 2)
	        		{
	        			currency = i;
	        		}
	        		  
	        		//if the quantity value is missing or it is a negative value
	        		else if(s.indexOf(i) == 3 && (i.length() == 0 || i.charAt(0) == '-'))
	        		{
	        			index_of_bad_data_line.add(fixed_products.indexOf(s));
	        		}
	        		          		  
	        		//quantity setting if there is no missing or wrong data value
	        		else if(s.indexOf(i) == 3)
	        		{
	        			quantity = Integer.parseInt(i);
	        		}
	        		
	        		//if the date value is missing
	        		else if(s.indexOf(i) == 4 && i.length() == 0)
	        		{
	        			index_of_bad_data_line.add(fixed_products.indexOf(s));
	        		}
	        	}
	        }    
	        myReader.close();
	        
	        //these two for iterations are for finding the wrong data having lines and constructing a data collection of the good data having lines
		    for(int g : index_of_bad_data_line)
		    {
		    	if(fixed_products.contains(fixed_products.get(g)))
		    	{
		    		fixed_products_bad.add(fixed_products.get(g));
		    	}
		    }
		    
		    for (List<String> t : fixed_products) 
	        {
		    	if(!(fixed_products_bad.contains(t)))
		    	{
		    		fixed_products_final.add(t);
		    	}
	        }
	    } 
	    catch (FileNotFoundException e) 
	    {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }

		//for presenting all available packages
	    for (List<String> u : fixed_products_final) 
        {
      	  	System.out.println(u);
        }
		System.out.println("Give the name of the package that you want to buy!");	//warner message to the user
	}
}
