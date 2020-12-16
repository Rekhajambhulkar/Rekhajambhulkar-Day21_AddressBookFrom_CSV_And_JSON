package com.telusko.com.bridgelabz.addressbook;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.google.gson.Gson;

public class AddressBookMainClass 
{
	static AddressBook familyAddressBook = new AddressBook();//family Address Book
	static AddressBook friendsAddressBook = new AddressBook();//Friends Address Book
	static Scanner scan = new Scanner(System.in);
	private static final String AllContactsFile = "F:\\AddressBook\\allDetails.csv";
	private static final String AllContactsFileForJSON = "F:\\AddressBook\\allDetails.json";

	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("rawtypes")
		HashMap<String, HashMap> addressBooks = new HashMap<String, HashMap>();//Stores Multiple Address Books
		HashMap<String, Person> oneListContacts = new HashMap<String, Person>();//Stores Single AddressBook
		
		
		System.out.println("\n\tWelcome to Address Book Program");
		
		
	       
	    while (true) {
	        System.out.println("\n1: for family \n" +
	        					 "2: for friend \n" +
	                    		 "3: To Search\n" +
	        					 "4: TO Sort\n" +
	        					 "5: To Read CSV\n" +
	        					 "6: To Terminate");
	        int selectedOption = scan.nextInt();
	            
	        switch (selectedOption) {
	        case 1:
	        	oneListContacts = familyAddressBook.userChoice();
	            addressBooks.put("Family", oneListContacts);
	            addToCsv(addressBooks);
	            break;
	        case 2:
	        	oneListContacts = friendsAddressBook.userChoice();	
	        	addressBooks.put("Friend", oneListContacts);   
	        	addToCsv(addressBooks);
	        	break;
	        case 3:
	        	search();
	        	break;
	        case 4:
	        	sort();
	        	break;
	        case 5:
	        	readCsv();
	        	break;
	        case 6:
	        	System.out.println("\n\tTerminated");
	        	break;
	        } 
	        if(selectedOption == 6)
	        	break;
	    }
	}

	private static void search() {
		
		 System.out.println("\n1.Search by City\n2.Search by State\n3.Count by City\n4.Count by State");
		 int search = scan.nextInt();
		 switch (search) {
		 
		    case 1:
		    	System.out.print("Enter City : ");
		    	String city = scan.next();
		    	System.out.println("Contacts in "+city+" are");
		    	familyAddressBook.searchByCity(city);
		    	friendsAddressBook.searchByCity(city);
		    	break;
		    case 2:
		    	System.out.print("Enter State : ");
		    	String state = scan.next();
		    	System.out.println("Contacts in "+state+" are");
		    	familyAddressBook.searchByState(state);
		    	friendsAddressBook.searchByState(state);
		    	break;
		    case 3:
		    	System.out.print("Enter City : ");
		    	String CityToCountContacts = scan.next();
		    	Long noOfFamilyContactsInCity = familyAddressBook.countByCity(CityToCountContacts);
		    	Long noOfFriendsContactsInCity = friendsAddressBook.countByCity(CityToCountContacts);
		    	System.out.println("\nNo of Contacts in "+CityToCountContacts+" are "+(noOfFamilyContactsInCity+noOfFriendsContactsInCity));
		    	break;
		    case 4:
		    	System.out.print("Enter State : ");
		    	String StateToCountContacts = scan.next();
		    	Long noOfFamilyContactsInState = familyAddressBook.countByState(StateToCountContacts);
		    	Long noOfFriendsContactsIntate = friendsAddressBook.countByState(StateToCountContacts);
		    	System.out.println("\nNo of Contacts in "+StateToCountContacts+" are "+(noOfFamilyContactsInState+noOfFriendsContactsIntate));
		    	break;
		    default:
		    	System.out.println("\nInvalid Entry\n");
		    	break;
		}		
	}
	private static void sort() { //Sorting
		
		System.out.println("\n1: Sort by First Name\n2: Sort by City\n3: Sort by State\n4: Sort by ZIP\n");
		int sortOption = scan.nextInt();
		
		boolean ifNotSorted = false;
		while(!ifNotSorted) {
			switch (sortOption) { 
		
				case 1: // Sorts by First Name
					System.out.println("\nSorted List of Contacts by First Name\n");
					familyAddressBook.sortByName();
					friendsAddressBook.sortByName();
					ifNotSorted = true;
					break;
				case 2: // Sorts by City
					System.out.println("\nSorted List of Contacts by City\n");
					familyAddressBook.sortByCity();
					friendsAddressBook.sortByCity();
					ifNotSorted = true;
					break;
				case 3: // Sorts by State
					System.out.println("\nSorted List of Contacts by State\n");
					familyAddressBook.sortByState();
					friendsAddressBook.sortByState();
					ifNotSorted = true;
					break;
				case 4: // Sorts by ZIP
					System.out.println("\nSorted List of Contacts by ZIP\n");
					familyAddressBook.sortByZip();
					friendsAddressBook.sortByZip();
					ifNotSorted = true;
					break;
				default :
					System.out.println("Enter Correct Option\n");
					sortOption = scan.nextInt();
					break;
			}
		}
	}
	
	private static void addToCsv(HashMap<String, HashMap> addressBooks) throws IOException {
	
		try (
			Writer writer = Files.newBufferedWriter(Paths.get(AllContactsFile));

			CSVWriter csvWriter = new CSVWriter(writer,
					CSVWriter.DEFAULT_SEPARATOR,
					CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);
			) {
			
			 String[] headerRecord = {"AddressBook", "First Name", "Last Name", "Address", "City", "State", "ZIP", "Phone No", "Email"};
	         csvWriter.writeNext(headerRecord);

	        for(java.util.Map.Entry<String, HashMap> eachAddressBook : addressBooks.entrySet()) {
	         
	        	 String addressBookName = eachAddressBook.getKey();
	        	 HashMap<String, Person> contacts = eachAddressBook.getValue();
			
	        	 for(Person details : contacts.values()) {
	        		 csvWriter.writeNext(new String[] {(String) addressBookName, details.getfName(), details.getlName(), 
																		details.getAddress(), details.getCity(), 
																		details.getState(), details.getZip(), 
																		details.getPhoneNumber(), details.getEmailId()});
	        		 
	        	 }
	         }
		}
	}
	
	private static void readCsv() {
		try {
			FileReader fileReader = new FileReader(AllContactsFile);
			CSVReader csvReader = new CSVReader(fileReader);
			String[] nextRecord;
			while((nextRecord = csvReader.readNext()) != null) {
				for(int index = 0; index < nextRecord.length; index++) {
					
						System.out.print(nextRecord[index]+",  ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
