import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/** RecursivePrefixtoPostfixConverter converts a provided prefix expression to the corresponding
 * postfix expression.  Prefix expressions are provided via an input file and the result
 * is printed to an output file.  The program checks that the input is in the correct
 * prefix form and prints errors if encountered.  The program also tracks and prints
 * the number of prefix expressions entered.
 * 
 * @version 1.0 / 19 Mar 2024
 * @author David Blossom
 * 
 */

public class RecursivePrefixToPostfixConverter {

	public static void main(String[] args) throws FileNotFoundException {
		
		//validate correct number of arguments was entered
		if (args.length < 2)
			System.out.println("Error: Please enter a valid input and output file.");
		
		//create PrefixToPostfixConverter object
		RecursivePrefixToPostfixConverter converter = new RecursivePrefixToPostfixConverter();
		
		//call processFiles() to process input and create output
		converter.processFiles(args[0], args[1]);

	} //end main
	
	public void processFiles(String inputFileName, String outputFileName) throws FileNotFoundException {
		
		//create File object for input
		File fileIn = new File(inputFileName);
		
		//create File object for output
		File fileOut = new File(outputFileName);
		
		//initialize input and output String variables
		String newInput = null;
		String outputStr = null;
		char test = ' ';
		
		//initialize counter for tracking the number of input expressions
		int counter = 0; 
		
		//verify input file exists; provide error if not found
		if (!fileIn.exists())
			System.out.println("Error: Input file not found.");
		
		else
			try {
		
			BufferedReader reader = new BufferedReader( new FileReader( fileIn ) );
			Scanner scanner =  new Scanner( fileIn );
			PrintWriter writer = new PrintWriter(new FileWriter( fileOut, true ));
			
			
			//print header information to output file
			writer.printf( "%-30s\n", "Input Expressions");
			writer.printf( "%-30s\n\n", "------------------------------");
			
			//get prefix inputs; print to output file
			while ((newInput = reader.readLine()) != null) {
				
				writer.printf( "%-30s\n", newInput );
				counter++;
				
			}
			
			//print divider information to output file
			writer.printf( "\n\n%-30s\n", "Converted Expressions");
			writer.printf( "%-30s\n\n", "------------------------------");
			
			//process conversions from prefix to postfix; print to output file
			while (scanner.hasNextLine()) {				

				outputStr = convert(scanner);
				
				if (scanner.hasNext()) {
										
					test = scanner.next().charAt(0);
								
					if (isOperand(test))
						writer.printf( "%-30s\n", "Invalid prefix input; too many operands" );
					
					//if (isOperand(test) || isOperator(test))
					else if (isOperator(test))
						writer.printf( "%-30s\n", "Invalid prefix input; too many operators" );
					
					else 
						writer.printf( "%-30s\n", outputStr );

				} //end if
												
				//flush remaining characters until newline
				while (test != '\n')
					test = scanner.next().charAt(0);

			} //end while
			
			writer.printf( "%-30s\n", outputStr );
			
			writer.print("\n\n\nExpressions evaluated: " + counter);
			
			writer.close();
			scanner.close();
			reader.close();
		
		  //print error if unable to read or write to files
			} catch (IOException e) {
				System.out.println( "Error: Unable to read or write to file" );
		}
		
	} //end processFiles
	
	//get new character from file; uses recursion
	public char getInput(Scanner sc) {
		
		char newChar = ' ';
		
		newChar = sc.useDelimiter("").next().charAt(0);

		return newChar;
		
	} //end getInput
	
	//convert the prefix input to postfix using recursion
	public String convert(Scanner sc) {
		
		String op;
		String op1;
		String op2;
		String result;
		
		char input = getInput(sc); //read in next character
		
		if (!isOperand(input) && !isOperator(input))
			error = "error";
		
			if(!isOperator(input))
				return result = "" + input;
			
			else {
				op = "" + input;
				op1 = convert(sc);
				op2 = convert(sc);
				
				result = op1 + op2 + op;
			}
			
			if (error != "")
				return error;
			
			else
				return result;
				
	} //end convert
	
	private static boolean isOperator(char c) {
		
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '$' || c == '^')
			return true;
		else
			return false;
	} //end isOperator
	
	private static boolean isOperand(char c) {
		
		if ( (c >= 'a' || c >= 'A') && (c <= 'z' || c<= 'Z') )
			return true;
		else
			return false;
	} //end isOperand

	private String error;
	
} //end RecursivePrefixToPostfixConverter
