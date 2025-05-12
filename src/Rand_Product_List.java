import java.time.LocalDateTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Rand_Product_List
{
	public Rand_Product_List(int balance)
	{
		cash = balance; 
	}
	protected static String name;						//name of the bought package
	protected static String currency;					//currency of the bought package
	protected static int quantity;						//amount of the bought package category
	protected static LocalDateTime creation_datetime;	//buying time of the package
	protected static int price;							//price of the bought package
	protected static String product_type;				//type of the bought package
	protected static String product_status;				//status of the bought package
	protected static int transaction_id;				//transaction id, which goes between 1 and 999
	protected static List<List<String>> rand_products = new ArrayList<>();			//main store of the scanned data (first edition)
	protected static List<List<String>> rand_products_bad = new ArrayList<>();		//wrong data having store
	protected static List<List<String>> rand_products_final = new ArrayList<>();	//good data having store
	protected static List<String> name_of_packages = new ArrayList<>();				//storing all package names for examination
	protected static List<String> min_price_of_packages = new ArrayList<>();		//storing all min package prices for examination
	protected static List<String> max_price_of_packages = new ArrayList<>();		//storing all max package prices for examination
	protected static List<String> amount_of_packages = new ArrayList<>();			//storing all package amounts for examination
	protected static String[] one_transaction = new String[7];
	protected static List<List<String>> transactions = new ArrayList<>();			//storing all the transactions
	protected static List<String> transaction_ids = new ArrayList<>();				//storing all transaction ids for examination
	protected static int cash_to_give_back;											//cash to refund if refunding occurred
	protected static int package_amount;
	protected static int cash;
	
	//topping-up function behavior, where the user can type in the name of the wished package
	public static String topping_up(List<List<String>> checkit)
	{	
		Scanner scanner = new Scanner(System.in);
		String input;						//stores the user given input
		String proceed;						//additional user input, so that the user can close the shopping or make another purchase
		int exact_price = 0;				//stores the exact price amount given by user (between min and max prices)
		int min_package_price = 0;			//min price value of the package (initial value, that will change later on)
		int max_package_price = 0;			//max price value of the package (initial value, that will change later on)
		int interval_package_price = 0;		//price value between min and max package price
		String message_about_buying = "";	//logging message (initial value, that will change later on)
		String package_type_to_get = "";	//for gathering package type
		try 
		{
			package_amount = 0;				//available quantity amount of the chosen package (initial value, that will change later on)
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
	        		//min price of the package
	        		else if(s.indexOf(i) == 1)
	        		{
		        		min_package_price = Integer.parseInt(i);
		        		min_price_of_packages.add(i);	//filling in the helper mini storage
	        		}
	        		//max price of the package
	        		else if(s.indexOf(i) == 2)
	        		{
		        		max_package_price = Integer.parseInt(i);
		        		max_price_of_packages.add(i);	//filling in the helper mini storage
	        		}
	        		//quantity of the chosen package available
	        		else if(s.indexOf(i) == 4)
	        		{
	        			package_amount = Integer.parseInt(i);
	        			amount_of_packages.add(i);	//filling in the helper mini storage
	        		}
	        		//type of the package
	        		else if(s.indexOf(i) == 6)
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
				min_package_price = Integer.parseInt(min_price_of_packages.get(helper_index)); //example index 1 package name index 1 price
				max_package_price = Integer.parseInt(max_price_of_packages.get(helper_index)); //example index 1 package name index 1 price
				
				package_amount = Integer.parseInt(amount_of_packages.get(helper_index)); //example index 1 package name index 1 amount
				
				//if there is package available, based on quantity
				if(package_amount > 0)
				{
					//check which version does the customer want to buy
					System.out.println("Which edition you would like to buy?");
					System.out.println("Give a price value between min and max price!");
					exact_price = scanner.nextInt();
					interval_package_price = exact_price;	//transformation of the price
					if(interval_package_price < min_package_price)
					{
						System.out.println("Give a higher price value than min price!");
						throw new Exception("Give a higher price value than min price!");
					}
					else if(interval_package_price > max_package_price)
					{
						System.out.println("Give a lower price value than max price!");
						throw new Exception("Give a lower price value than max price!");
					}
					else if(interval_package_price >= min_package_price && interval_package_price <= max_package_price)
					{
						//if the user can't buy it
						if(interval_package_price > cash)
						{
							message_about_buying = "can't pay for the package";
						}
						//if the user can buy the package
						else if(interval_package_price <= cash)
						{	
							//logging messages
							System.out.println("Package to buy: " + input);
							System.out.println("Amount of this package available: " + package_amount);
							System.out.println("Giving one out of the available package");
							package_amount--;	//package amount obviously decreases
							System.out.println("New amount of the available package: " + package_amount);
							message_about_buying = "Purchasing the given package";
							cash -= interval_package_price;	//cash / balance of the customer obviously decreases
							System.out.println("Present cash: " + cash);
							System.out.println("Total price: " + interval_package_price);
		        			Random rn = new Random(); 
		        			transaction_id = rn.nextInt(999) + 1;
		        			System.out.println("Your transaction's id: " + transaction_id);
		        			
		        			//constructing the data for one transaction
							name = input;
							price = interval_package_price;
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
					System.out.println("Present cash: " + cash);
					System.out.println("The refunded package is back in our list!");
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
	        File myObj = new File("rand_product_list.txt");
	        Scanner myReader = new Scanner(myObj);
			
			//each line is an array, where the data are separated with ; character
	        while (myReader.hasNextLine()) 
	        {
	        	rand_products.add(Arrays.asList(myReader.nextLine().split(";")));
	        }
	        
	        String needed_segmented_word_for_min_price = "";			//for gathering the min price of the package name 
			String needed_segmented_word_for_max_price = "";			//for gathering the max price of the package name
			String needed_segmented_word_for_currency = "";				//for gathering the name of the currency out of the package name
	        List<Integer> index_of_bad_data_line = new ArrayList<>(); 	//for identifying the wrong data or missing data having lines
	        
	        //reading all lines
	        for (List<String> s : rand_products) 
	        {
	        	//reading all values in one line
	        	for(String i : s)
	        	{
		      		if(s.indexOf(i) == 0)
		      		{
		      			String experimental = i;	//the entire package name
		      			String[] segmented_words = experimental.split(" ");	//for gathering words out of the package name
		      			  
		      			for(String j : segmented_words)
		      			{
		      				if(j.charAt(0) == '1' || j.charAt(0) == '2' || j.charAt(0) == '3' || j.charAt(0) == '4' || j.charAt(0) == '5' || 
		      				   j.charAt(0) == '6'|| j.charAt(0) == '7' || j.charAt(0) == '8' || j.charAt(0) == '9')
		      				{
		      					needed_segmented_word_for_min_price = j.substring(0, j.indexOf("-"));	//min price
		      					needed_segmented_word_for_max_price = j.substring(j.indexOf("-") + 1);  //max price
		      				}
		      				needed_segmented_word_for_currency = segmented_words[2];
		      			}
		      		}
		      		
		      		//if the min price value is missing or wrong
		      		if(s.indexOf(i) == 1 && (i.length() == 0 || i.charAt(0) == '-' || !(i.equals(needed_segmented_word_for_min_price))))
		      		{
		      			index_of_bad_data_line.add(rand_products.indexOf(s));
		      		}
		      		
		      		//if the max price value is missing or wrong
		      		else if(s.indexOf(i) == 2 && (i.length() == 0 || i.charAt(0) == '-' || !(i.equals(needed_segmented_word_for_max_price))))
		      		{
		      			index_of_bad_data_line.add(rand_products.indexOf(s));
		      		}
		      		
		      		//if the currency value is not equal to the one given in the package name or empty
		      		else if(s.indexOf(i) == 3 && (i.length() == 0 || !(i.equals(needed_segmented_word_for_currency))))
		      		{
		      			index_of_bad_data_line.add(rand_products.indexOf(s));
		      		}
		      		
		      		//if the quantity value is missing or it is a negative value
		      		else if(s.indexOf(i) == 4 && (i.length() == 0 || i.charAt(0) == '-'))
		      		{
		      			index_of_bad_data_line.add(rand_products.indexOf(s));
		      		}
		      		
		      		//if the date value is missing
		      		else if(s.indexOf(i) == 5 && i.length() == 0)
		      		{
		      			index_of_bad_data_line.add(rand_products.indexOf(s));
		      		}
	        	}
	        }
	        myReader.close();
	        
	        //these two for iterations are for finding the wrong data having lines and constructing a data collection of the good data having lines
		    for(int g : index_of_bad_data_line)
		    {
		    	if(rand_products.contains(rand_products.get(g)))
		    	{
		    		rand_products_bad.add(rand_products.get(g));
		    	}
		    }
		    
		    for (List<String> t : rand_products) 
	        {
		    	if(!(rand_products_bad.contains(t)))
		    	{
		    		rand_products_final.add(t);
		    	}
	        }
	    } 
	    catch (FileNotFoundException e) 
	    {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		
		//for presenting all available packages
	    for (List<String> u : rand_products_final) 
        {
      	  	System.out.println(u);
        }
		System.out.println("Give the name of the package that you want to buy!");	//warner message to the user
	}
}
