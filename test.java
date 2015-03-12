// Komron Iranzad 
// CS 3345 
// Project 2
//

import java.util.*;
import java.io.*;


class Node {
	boolean terminal;
	int outDegree;
	Node[] children;
	
	Node (){
		this.terminal = false;
		this.outDegree = 0;
		this.children = new Node[26];
	}
}

class Trie {
	
	Node root;
	
	Trie (){
		root = new Node();
	}
	
	boolean insert(String s){ // returns false if s is already present, true otherwise
		if(insert(root,s) == true)
			return true;
		else
			return false;
	}
	private boolean insert(Node n, String s){ // **Private** returns false if s is already present, true otherwise


		if(s.length() == 0){
			n.terminal = true;
			return true;// returns since end of word was reached without a terminal so word hasn't been placed
		}
		else{
			//I got some pointers on insert from Matt Bachelder and made it a little shorter
			int i = s.charAt(0) - 'a'; // i is the index contain the reference to the next child/ letter to be placed
					
			if(n.children[i] == null){
				n.children[i] = new Node();
				n.outDegree++;
			}
			else if (s.length() == 1 && n.children[i].terminal == true){//if we have gotten this far and the last placement will already be a terminal 
				return false; //this means this word has been placed
			}
			return (insert(n.children[i], s.substring(1, s.length())));	
			//using a substring minus the character that was just analyzed 
			//going into a recursion and either placing a word or finding the word has already been placed
			//or possibly going in another recursion 
		}
	}
	
	boolean isPresent(String s){ // returns true if s is present, false otherwise
		if(isPresent(root,s) == true)
			return true;
		else
			return false;
	}
	
	private boolean isPresent(Node n, String s){ // **private** returns true if s is present, false otherwise
		
		//it seems to me that this would be almost the same as insert 
		//so we are going to go with that and see how it works out 
		if(s.length() == 0){
				return false;//return false if end is reached and no word has been found
		}
		else{
			int i = s.charAt(0) - 'a'; // i is the index contain the reference to the next child/ letter to be placed
			
			if(n.children[i] == null){ 
				return false;//if the next child is null then no character no more character means no more word 
			}
			else if (s.length() == 1 && n.children[i].terminal == true){//if we have gotten this far and the last placement will already be a terminal 
				return true; //this means this word has been placed
			}
			return (isPresent(n.children[i], s.substring(1, s.length())));
			//using a substring minus the character that was just analyzed 
			//going into a recursion finding the word has been placed or not
			//or possibly going in another recursion 

		}
	}
	
	boolean delete(String s){ // returns false if s is not present, true otherwise
		if(isPresent(s) == false)//using isPresent rather then making more code for nothing
			return false;
		else
			return delete(root, s);
	}
	private boolean delete(Node n, String s){
		
		// the reason there is no statement like the first statement in insert is because
		// there has already been a check for whether the word exists or not 
		int i = s.charAt(0) - 'a'; // i is the index contain the reference to the next child/ letter to be placed
		
		if (s.length() == 1 && n.children[i].terminal == true){//if we have gotten this far and the last placement will already be a terminal 
			n.children[i].terminal = false;// these are almost the same as the insert function
			n.outDegree--;// but the have been reversed since we are deleting
			return true; //word deleted 
			// this private part of this function will only ever come back true
		}
		return (delete(n.children[i], s.substring(1, s.length())));
		//using a substring minus the character that was just analyzed 
		//going into a recursion finding the word has been placed or not
		//or possibly going in another recursion 
	}
	
	
	/*************************************
	boolean delete(String s){ // returns false if s is not present, true otherwise
		if(isPresent(s) == false)//using isPresent rather then making more code for nothing
			return false;
		else
			return delete(root, s);
	}
	private boolean delete(Node n, String s){
		
		// the reason there is no statement like the first statement in insert is because
		// there has already been a check for whether the word exists or not 
		int i = s.charAt(0) - 'a'; // i is the index contain the reference to the next child/ letter to be placed
		
		if (s.length() == 1 && n.children[i].terminal == true){//if we have gotten this far and the last placement will already be a terminal 
			n.children[i].terminal = false;// these are almost the same as the insert function
			//*********n.children[i].outDegree--;// but the have been reversed since we are deleting
			if(n.children[i].outDegree > 0){// we are in the middle of the word so we are done
				return true; //word deleted 
			// this private part of this function will only ever come back true
			}
			else 
				return false;
		}
		else{
			boolean bool = delete(n.children[i], s.substring(1, s.length()));
			//using a substring minus the character that was just analyzed 
			//going into a recursion finding the word has been placed or not
			//or possibly going in another recursion 
			if(bool == true)
				return true;
			else {
				n.children[i].outDegree--;
				if (n.children[i].outDegree == 0){
					n.children[i]= null;
					return true; //word has been deleted
				}
				else{
					n.children[i] = null;
					return false;
				}
			}
		}
	}
	*/
	
	
	
	
	int membership(){ // returns the number of words in the data structure
		return membership(root);
	}
	
	private int membership(Node n){// **private**returns the number of words in the data structure
		int count = 0;//lets say a = 0 
		for(int i = 0; i <= 25; i++)// we start at a and go to z 
			if(n.children[i] != null){ //looking for a child that would indicate a letter
				if(n.children[i].terminal == true)// if the child we land on is a terminal 
					count++;//we know this is a word so increase count
				count += membership(n.children[i]);//recurse to check this node's child for letters after it
			}
		return count;
	}
	
	void listAll(){ // list all members of the Trie in alphabetical order
		
		
		for(int i = 0; i <= 25; i++){// we start at a and go to z 
			if(root.children[i] != null){ //looking for a child that would indicate a letter
				String s = "";
				int x = i + 'a';
				char c = (char) x;
				s = s + c;
				if(root.children[i].terminal == true){// if the child we land on is a terminal 
					System.out.println(s);
				}
				listAll(root.children[i], s);//recurse to check this node's child for letters after it
			}
		}
	}
	private void listAll(Node n, String s){ 
		for(int i = 0; i <= 25; i++){// we start at a and go to z 
			if(n.children[i] != null){ //looking for a child that would indicate a letter
				int x = i + 'a';
				char c = (char) x;
				s = s + c;
				if(n.children[i].terminal == true){// if the child we land on is a terminal 
					System.out.println(s);
				}
				listAll(n.children[i], s);//recurse to check this node's child for letters after it
			}
		}
	}
}

public class test {
	public static void main(String args[]) {
		
		//Node root = new Node();
		//root.children[3] = new Node();
		
		Trie word = new Trie();
		
		Scanner sc = new Scanner(System.in); 
		String line = "";
		boolean done = false;
		
		while(done == false) {
			line = sc.nextLine();
			String [] tokens = line.split(" ");
			switch(tokens[0]) {
				case "N": {
					System.out.println("Komron Iranzad");
					break;
					//N // Print your name followed by a newline.
				}
				case "A": {
					if(word.insert(tokens[1]) == true)
						System.out.println("Word inserted");
					else
						System.out.println("Word already exists");
					break;
					//A soap // Insert the word 'soap'
					// Print one of the strings "Word inserted" or "Word already exists"
					// followed by a newline.
				}
				case "D": {
					if(word.delete(tokens[1]) == true)
						System.out.println("Word deleted");
					else
						System.out.println("Word not present");
					break;
					//D sin // Delete the word 'sin'.
					// Print "Word deleted" or "Word not present" followed by a newline.
				}
				case "S": {
					if(word.isPresent(tokens[1]) == true)
						System.out.println("Word found");
					else
						System.out.println("Word not found");
					break;
					//S fortune // Search for the word "fortune".
					// Print "Word found" or "Word not found" followed by a newline.
				}
				case "M": {
					System.out.println("Membership is " + word.membership());
					break;
					//M // Print "Membership is ####" where #### is the number of words in the Trie
				}
				case "C": {
					int numToken = tokens.length;
					for (int i = 1; i < numToken ; i++){
						if(word.isPresent(tokens[i]) != true)
							System.out.println("Spelling mistake " + tokens[i]);
					}
					break;
					//C wa wb wc wd we // Check the space separated sequence of words wa, wb, wc, wd, we, ...
					// up to the end of the line for presence in the Trie and list any that are
					// not present, one per line, each preceded by the phrase "Spelling mistake".
					// The words in the list may each be up to 20 characters long and the line may
					// be up to 200 characters long.
				}
				case "L": {
					word.listAll();
					break;
					//L // Print a list of the elements of the Trie in alphabetical order, one word per line.
				}
				case "E": {
					done = true;
					break;
					//E // The end of the input file
				}
			} // end of switch
		} // end while
		sc.close();	
	}
}
