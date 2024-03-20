import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/** RecursivePrefixtoPostfixConverter converts a provided prefix expression to the corresponding
 * postfix expression.  Prefix expressions are provided via an input file and the result
 * is printed to an output file.  The program checks that the input is in the correct
 * prefix form and prints errors if encountered.  The program also tracks and prints
 * the number of prefix expressions entered.
 * 
 * @version 1.0 / 26 Feb 2024
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
	
	public void processFiles(String inputFile, String outputFile) throws FileNotFoundException {
		
		FileReader fileIn = new FileReader(inputFile);
		Scanner scanner =  new Scanner(fileIn);
		
		while (scanner.hasNextLine()) {
		
			System.out.println(convert(scanner));
		
		}
		
		scanner.close();
		
	} //end processFiles
	
	public static char getInput(Scanner sc) {
		
		char newChar = ' ';
		
		newChar = sc.useDelimiter("").next().charAt(0);
		
		return newChar;
		
	} //end getInput
	
	public static String convert(Scanner sc) {
		
		char input = getInput(sc);
		
		if (!(input == '\n')) {
		
			//System.out.println("input: " + input); //test
			
			String op;
			String op1;
			String op2;
			String result;
			
			if(!isOperator(input))
				return result = "" + input;
			
			else {
				op = "" + input;
				op1 = convert(sc);
				op2 = convert(sc);
				
				return result = op1 + op2 + op;
			}
		}
		
		else
			return "";
		
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

} //end RecursivePrefixToPostfixConverter
