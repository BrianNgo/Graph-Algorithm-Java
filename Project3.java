import java.util.*;

public class Project3 {
	public static void main(String[] args) {
		// Create a Digraph object and read data from the text files
		Digraph myGraph = new Digraph("city.dat", "road.dat");
		
		// Variables for program's operation
		String input = null, firstCode = null, secondCode = null;
		int distanceInput = 0;
		
		// Create a scanner object to read input from user
		Scanner keyboard = new Scanner(System.in);
		
		do {
			System.out.print("Command? ");
			input = keyboard.nextLine();
			
			switch (input) {
			case("Q"): { // Query the city
				System.out.print("City code: ");
				input = keyboard.nextLine();
				// Print the city's information
				myGraph.printCity(input);
				break;
			}		
			case("D"): { // Find the shortest distance
				System.out.print("City codes: ");
				input = keyboard.nextLine();
				Scanner codeScan = new Scanner(input);
				
				// Extract the codes from the user's input
				if (codeScan.hasNext())
					firstCode = codeScan.next();
				else {
					System.out.println("Missing input!!");
					break;
				}
				
				
				if (codeScan.hasNext())
					secondCode = codeScan.next();
				else {
					System.out.println("Missing input!!");
					break;
				}
				
				// Get the shortest path between two cities
				myGraph.printPath(firstCode, secondCode);
				codeScan.close();
				break;
			}	
			case("I"): { // Insert new path
				System.out.print("City codes and distance: ");
				input = keyboard.nextLine();
				Scanner codeScan = new Scanner(input);
				
				// Extract the code from the user's input
				if (codeScan.hasNext())
					firstCode = codeScan.next();
				else {
					System.out.println("Missing input!!");
					break;
				}
				
				if (codeScan.hasNext())
					secondCode = codeScan.next();
				else {
					System.out.println("Missing input!!");
					break;
				}
				
				if (codeScan.hasNextInt())
					distanceInput = codeScan.nextInt();
				else {
					System.out.println("Invalid input!!");
					break;
				}
				
				// Insert the new path
				myGraph.insertPath(firstCode, secondCode, distanceInput);
				codeScan.close();
				break;		
			}		
			case("R"): { // Delete a path
				System.out.print("City codes: ");
				input = keyboard.nextLine();
				Scanner codeScan = new Scanner(input);
				
				// Extract the codes from user's input
				if (codeScan.hasNext())
					firstCode = codeScan.next();
				else {
					System.out.println("Missing input!!");
					break;
				}
				
				if (codeScan.hasNext())
					secondCode = codeScan.next();
				else {
					System.out.println("Missing input!!");
					break;
				}
				
				// Remove the path
				myGraph.removePath(firstCode, secondCode);
				codeScan.close();
				break;
			}
			case("H"): { // Print command menu
				System.out.println("Q\tQuery the city information by entering the city code.");
				System.out.println("D\tFind the minimum distance between two cities.");
				System.out.println("I\tInsert a road by entering two city codes and distance.");
				System.out.println("R\tRemove an existing road by entering two city codes.");
				System.out.println("H\tDisplay this message.");
				System.out.println("E\tExit.");
				break;
			}
			case("E"): { // Exit the program
				System.out.println("Thank you for using");
				break;
			}
			default: {
				System.out.println("Invalid Option!!!");
			}
			}
			
		} while (!input.equals("E")); 
		keyboard.close();
	}

}
