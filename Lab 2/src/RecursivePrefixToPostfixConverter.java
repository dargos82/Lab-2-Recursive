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
 * @version 1.0 /  Mar 2024
 * @author David Blossom
 * 
 */
public class RecursivePrefixToPostfixConverter {

	/** main() validates the command line input and contains the program driver.   If an
	 * incorrect number of arguments was entered, main() prints an error and prompts the
	 * user for correct input. 
	 * @param args  //input and output file names
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		//validate correct number of arguments was entered
		if (args.length < 2)
			System.out.println("Error: Please enter a valid input and output file.");
		
		//create PrefixToPostfixConverter object
		RecursivePrefixToPostfixConverter converter = new RecursivePrefixToPostfixConverter();
		
		//call processFiles() to process input and create output
		converter.processFiles(args[0], args[1]);

	} //end main
	
	/** processFiles() opens the input file, reads the input, prints the prefix string to the
	 * output file, then opens the input file again to read the input, calls convert() for 
	 * processing and prints the results to the output file.
	 * @param inputFileName
	 * @param outputFileName
	 * @param fileIn
	 * @param fileOut
	 * @param outputStr
	 * @param newInput
	 * @param test
	 * @param counter
	 * @throws FileNotFoundException
	 */
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
				
				error = "";
				outputStr = convert(scanner);
				
				if (scanner.hasNext()) {
										
					test = scanner.next().charAt(0);
								
					if (isOperand(test))
						writer.printf( "%-30s\n", "Invalid prefix input; too many operands" );
					
					else
						writer.printf( "%-30s\n", outputStr );
					
					//flush remaining characters until newline
					while (test != '\n')
						test = scanner.next().charAt(0);
					
				} //end if

			} //end while
			
			writer.print("\n\nExpressions evaluated: " + counter);
			
			writer.close();
			scanner.close();
			reader.close();
		
		  //print error if unable to read or write to files
			} catch (IOException e) {
				System.out.println( "Error: Unable to read or write to file" );
		}
		
	} //end processFiles
	
	/** getInput() accepts a Scanner object as a parameter and gets a single character from the
	 * input file.
	 * @param sc
	 * @return newChar
	 */
	public char getInput(Scanner sc) {
		
		char newChar = ' ';
		
		newChar = sc.useDelimiter("").next().charAt(0);

		return newChar;
		
	} //end getInput
	
	/** convert() accepts a Scanner object as a parameter, calls getInput() to get a new character,
	 * evaluates the character, and returns a String.
	 * @param sc
	 * @param op
	 * @param op1
	 * @param op2
	 * @param input
	 * @return error
	 * @return result
	 */
	public String convert(Scanner sc) {
		
		String op;
		String op1;
		String op2;
		String result;
		
		char input = getInput(sc); //read in next character
		
		//check if character is whitespace
		if (Character.isWhitespace(input))
			error = "Invalid prefix input; too many operators";
		
		//check if character is valid
		else if (!isOperand(input) && !isOperator(input))
			error = "Invalid character in the prefix input";
			
			//begin recursive section
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
	
	
	/** isOperator is a boolean method that accepts a char and returns true or false dependent
	 * on whether or not the char matches a set of defined operators.
	 * @param c
	 * @return true or false
	 */
	private static boolean isOperator(char c) {
		
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '$' || c == '^')
			return true;
		else
			return false;
	} //end isOperator
	
	/** isOperand is a boolean method that accepts a char and returns true or false dependent
	 * on whether or not the char is a lower or upper case member of the Latin alphabet.
	 * @param c
	 * @return true or false
	 */
	private static boolean isOperand(char c) {
		
		if ( (c >= 'a' || c >= 'A') && (c <= 'z' || c<= 'Z') )
			return true;
		else
			return false;
	} //end isOperand

	private String error;
	
} //end RecursivePrefixToPostfixConverter
