import java.util.Scanner;

class Stack {
	private Stack underneath;
	private double data;
	public Stack(double data, Stack underneath) {
		this.setData(data);
		this.setUnderneath(underneath);
	}
	public Stack getUnderneath() {
		return underneath;
	}
	public void setUnderneath(Stack underneath) {
		this.underneath = underneath;
	}
	public double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}	
}

class RPN {
	private String command;	
	private Stack top;
	public void push(double num) {
		Stack stack = new Stack(num, top);
		top = stack;
	}
	
	public double pop( ) {
		double topData = top.getData();
		top = top.getUnderneath();
		return topData;
	}
	
	public RPN(String command) {
		top = null;
		this.command = command;
	}
	
	public double get( ) {
		int j;		
		for(int i = 0; i < command.length( ); i++) {			
			// if it's a digit
			if(Character.isDigit(command.charAt(i))) {				
				double number;				
				// get a string of the number
				String temp = "";				
				for(j = 0; (j < 100) && (Character.isDigit(command.charAt(i)) || (command.charAt(i) == '.')); j++, i++) {					
					temp += String.valueOf(command.charAt(i));
				}				
				// convert to double and add to the stack
				number = Double.parseDouble(temp);
				push(number);				
			} else if(command.charAt(i) == '+') {
				push(pop()+pop());
			} else if(command.charAt(i) == '-') {
				push(-pop()-pop());
			} else if(command.charAt(i) == '*') {
				push(pop()*pop());
			} else if(command.charAt(i) == '/') {
				double divisor = pop();
				push(pop()/divisor);
			} else if(command.charAt(i) == '^') {
				double exponent = pop();
				push(Math.pow(pop(), exponent));
			} else if(command.charAt(i) != ' ') {
				throw new IllegalArgumentException( );
			}
		}		
		double val = pop( );		
		if(top != null) {
			throw new IllegalArgumentException( );
		}		
		return val;
	}	
	/* main method */
	public static void main(String args[]) {
		while(true) {
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			System.out.println("Enter RPN expression or \"quit\".");
			String line = in.nextLine( );
			if(line.equals("quit")) {
				break;
			} else {
			RPN calcObj = new RPN(line);
			System.out.printf("Answer is %f%n", calcObj.get( ));
			}
		} 
	}
}