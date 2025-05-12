import java.util.*;

public class Package_shop_imitator 
{
	public static void main(String[] args) 
	{	
		while (true) 
		{
			printOptions();
			int input = readInput();
			switch (input) 
			{
				case 1:
					Fix_Product_List customer = new Fix_Product_List(10000);	//customer object, the balance is related to the constructor
					customer.Storage_Constructor();
					customer.topping_up(customer.fixed_products_final);
					break;
				case 2:
					Rand_Product_List customer2 = new Rand_Product_List(10000);	//customer object, the balance is related to the constructor
					customer2.Storage_Constructor();
					customer2.topping_up(customer2.rand_products_final);
					break;
				case 3:
					System.out.println("What type of product did you buy that you wish to refund?");	//for helping purposes
					System.out.println("(FIX) FIX");
					System.out.println("(RAND) RAND");
					Scanner scanner_for_type = new Scanner(System.in);
					String type_asked = scanner_for_type.nextLine();
					switch(type_asked)
					{
						case "RAND":
							Rand_Product_List customer3 = new Rand_Product_List(10000);	//customer object, the balance is related to the constructor
							customer3.refunding();
							break;
						case "FIX":
							Fix_Product_List customer4 = new Fix_Product_List(10000);	//customer object, the balance is related to the constructor
							customer4.refunding();
							break;
						default:
							System.out.println("Invalid input!\n");
							break;
					}
					break;
				case 4:
					System.out.println("Closing website window.\n");
					return;
				default:
					System.out.println("Invalid input!\n");
					break;
			}
		}
	}
	
	private static void printOptions() 
	{
		System.out.println("(1) Buy a fix priced product.");
		System.out.println("(2) Buy a variable priced product.");
		System.out.println("(3) Refund an order.");
		System.out.println("(4) Close the webshop window.");
		System.out.println("\nWhat do you want to do?");
	}
	
	private static int readInput() 
	{
		Scanner scanner = new Scanner(System.in);
		int input;
		try 
		{
			input = scanner.nextInt();
		} 
		catch (Exception e) 
		{
			input = 0;
		}
		
		return input;
	}
}
