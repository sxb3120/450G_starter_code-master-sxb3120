package edu.louisiana.cacs.csce450GProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Parser{
	static String file;
	static String[] idqueue = new String[15];
	static String[] idstack = new String[30];
	static String[][] action_values = new String[12][6];
	static int[][] goto_values = new int[12][3];
	static int front = 0;
	static int last = -1;
	static int top = -1;
	static String[] parsestack = new String[5];
	static int s_top = -1; 
	static boolean control = true;
	public Parser(String fileName)
	{
		System.out.println("File to parse : "+fileName);
		file = fileName;
	}
	public void printParseTree()
	{
		System.out.println("Parse Tree:");
		char ch;
		int tab = 0;
		for(int i = 0; i < parsestack[s_top].length(); i++)
		{
			boolean flag = false;
			ch = parsestack[s_top].charAt(i);
			if(ch == '[')
			{
				tab++;
				continue;
			}
			else if(ch == ']')
			{
				tab--;
				continue;
			}
			if(ch == 'i')
			{
				tab++;
				i++;
				flag = true;
			}
			for(int j = 0; j < tab; j++)
			{
				System.out.print("\t");
			}
			if(flag)
			{
				System.out.println("id");
				tab--;
			}
			else if(ch == '*' || ch == '+')
			{
				System.out.println("\t" + ch);
			}
			else
			{
				System.out.println(ch);
			}
		}
		System.out.println("\t$");
	}
	public String action(String x, String y)
	{
		int x_int = Integer.parseInt(x);
		int val = -1;
		switch(y)
		{
			case "id" : val = 0; break;
			case "+" : val = 1; break;
			case "*" : val = 2; break;
			case "(" : val = 3; break;
			case ")" : val = 4; break;
			case "$" : val = 5; break;
		}
		return action_values[x_int][val];
	}
	public int gotof(String x, String y)
	{
		int x_int = Integer.parseInt(x);
		int val = -1;
		switch(y)
		{
			case "e" : val = 0; break;
			case "t" : val = 1; break;
			case "f" : val = 2; break;
		}
		
		return goto_values[x_int][val];
	}
	public void parse() throws IOException
	{
		BufferedReader br_doc = null;
		br_doc = new BufferedReader(new FileReader(file));
		Scanner sc = new Scanner(br_doc);
		String line = br_doc.readLine();
		sc = new Scanner(line);
		while(sc.hasNext())
		{
			last++;
			idqueue[last] = sc.next();
		}
		System.out.println("queue items:");
		//assigning values into action and goto matrices
		action_values[0][0] = "s5";
		action_values[0][3] = "s4";
		action_values[1][1] = "s6";
		action_values[1][5] = "accept";
		action_values[2][1] = "r2";
		action_values[2][2] = "s7";
		action_values[2][4] = "r2";
		action_values[2][5] = "r2";
		action_values[3][1] = "r4";
		action_values[3][2] = "r4";
		action_values[3][4] = "r4";
		action_values[3][5] = "r4";
		action_values[4][0] = "s5";
		action_values[4][3] = "s4";
		action_values[5][1] = "r6";
		action_values[5][2] = "r6";
		action_values[5][4] = "r6";
		action_values[5][5] = "r6";
		action_values[6][0] = "s5";
		action_values[6][3] = "s4";
		action_values[7][0] = "s5";
		action_values[7][3] = "s4";
		action_values[8][1] = "s6";
		action_values[8][4] = "s11";
		action_values[9][1] = "r1";
		action_values[9][2] = "s7";
		action_values[9][4] = "r1";
		action_values[9][5] = "r1";
		action_values[10][1] = "r3";
		action_values[10][2] = "r3";
		action_values[10][4] = "r3";
		action_values[10][5] = "r3";
		action_values[11][1] = "r5";
		action_values[11][2] = "r5";
		action_values[11][4] = "r5";
		action_values[11][5] = "r5";
		
		goto_values[0][0] = 1;
		goto_values[0][1] = 2;
		goto_values[0][2] = 3;
		goto_values[4][0] = 8;
		goto_values[4][1] = 2;
		goto_values[4][2] = 3;
		goto_values[6][1] = 9;
		goto_values[6][2] = 3;
		goto_values[7][2] = 10;
		
		top++;
		idstack[top] = "0";
		String temp;
		char[] s_r = new char[3];
		System.out.println("stack\t  \t" + "input\t \t" + "action lookup\t" + "action value\t" + "value of LHS\t" + "length of RHS\t" + "tempstack\t" + "gotolookup\t" + "gotovalue\t" + "stack action\t" + "parse tree stack");
		while(front <= last)
		{
			for(int i = 0; i <= top; i++)
			{
				System.out.print(idstack[i]);
			}
			if(top <= 6)
			{
				System.out.print("\t");
			}
			System.out.print("  \t");
			for(int i = front; i <= last; i++)
			{
				System.out.print(idqueue[i]);
			}
			System.out.print("\t \t");
			System.out.print("[" + idstack[top] + "," + idqueue[front] + "]");
			System.out.print("\t");
			temp = action(idstack[top], idqueue[front]);
			if(temp == null)
			{
				System.out.println("grammatical error");
				control = false;
				break;
			}
			if(temp == "accept")
			{
				System.out.println(temp);
				break;
			}
			System.out.print(temp + "\t");
			s_r[2] = 'x';
			for(int i = 0; i < temp.length(); i++)
			{
				s_r[i] = temp.charAt(i);
			}
			//System.out.println(s_r[0]);
			if(s_r[0] == 's')
			{
				System.out.print("\t \t \t \t \t \t \t \t \t \t \t \t");
				top++;
				idstack[top] = idqueue[front];
				front++;
				if(idstack[top].equals("id"))
				{
					s_top++;
					parsestack[s_top] = "id";
				}
				if(s_r[2] == 'x')  //to check whether it is 2 digited number or not
				{
					top++;
					idstack[top] = Character.toString(s_r[1]);
				}
				else
				{
					String combine;
					combine = Character.toString(s_r[1]);
					combine.concat(Character.toString(s_r[2]));
					top++;
					idstack[top] = combine;
				}
			}
			else if(s_r[0] == 'r')
			{
				String lhs = "";
				int rhs = 0;
				int val;
				String sym;
				switch(s_r[1])
				{
					case '1' : lhs = "e"; rhs = 3; sym = parsestack[s_top-1] + "+" + parsestack[s_top]; parsestack[s_top-1] = sym; s_top--; break;
					case '2' : lhs = "e"; rhs = 1; break;
					case '3' : lhs = "t"; rhs = 3; parsestack[s_top-1] = parsestack[s_top-1] + "*" + parsestack[s_top]; s_top--; break;
					case '4' : lhs = "t"; rhs = 1; break;
					case '5' : lhs = "f"; rhs = 3; break;
					case '6' : lhs = "f"; rhs = 1; break;
				}
				System.out.print("\t \t" + lhs + "\t \t" + rhs + "\t \t");
				top = top - (2 * rhs);
				for(int i = 0; i <= top; i++)
				{
					System.out.print(idstack[i]);
				}
				System.out.print("\t \t [" + idstack[top] + "," + lhs + "] \t \t");
				val = gotof(idstack[top], lhs);
				if(val == 0)
				{
					System.out.print("grammatical error");
					control = false;
					break;
				}
				System.out.print(val + "\t \t");
				top++;
				idstack[top] = lhs;
				top++;
				idstack[top] = Integer.toString(val);
				parsestack[s_top] = "[" + idstack[top-1] + parsestack[s_top] + "]";
			}
			System.out.print("push " + idstack[top-1] + idstack[top] + "\t \t");
			for(int i = s_top; i >= 0; i--)
			{
				System.out.print(parsestack[i]);
			}
			System.out.println();
			if(front > last)
			{
				System.out.println("grammatical error"); 
				control = false;
				break;
			}
		} //end of while
		if(control)
		{
			printParseTree();
		}
	}

}