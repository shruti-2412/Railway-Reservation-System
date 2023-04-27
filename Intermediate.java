package BufferLoop;

/*
 * Loop Club Buffer 4.0 Data Structure based Project --> Railway Registeration System
 */

/*
 * Data Structure used : LinkedList, Array, Queue, HashSet
 * Concepts used : OOPs, Data Structure
 */

/*
 * Contributers :
 * UCE2021612 --> Shruti Chandak
 * UCE2021616 --> Simran Desai
 * UCE2021623  --> Anushka Gaikwad
 */




import java.util.*;


// class to represent the train attributes 
class Train1
{
	int trainNo; 
	String trainName;   
	String source,destination;
	String startTime, endTime;
	float price;
	int count;	

	Train1()
	{
		count = 0;		// represents the number of confirmed booked tickets
	}
}


// class to represent the passenger details
class passNode
{
	int Reg_no;
	String fName,lName;
	int age;
	String gender;
	String train_class;
	passNode next;	
	String date;
	int seatNo;
}



// class to represent the seat matrix of each of the 5 trains
class seatMatrix
{
	int[][] seat = new int[4][5];		// 2-D array storing the structure of the seating arrangement

	HashSet<Integer> set = new HashSet<>();		// set represents the available seats (i.e. seats that are not already booked)

	Queue<passNode> waiting = new LinkedList<>();		// queue represents the waiting queue

	seatMatrix()		// constructor to initilize 
	{
		int k = 11;
		for(int i = 0; i < 4 ; i++)		
		{
			for(int j = 0; j < 5; j++)		
			{
				seat[i][j] = k;
				k++;
			}
		}

		for(int i = 11; i < 31; i++)		
		{
			set.add(i);		// all seats are available at the beginning 
		}
	}


	void display() 
	{
		System.out.println("U\tM\tL\tL\tU");
		for(int i = 0; i < 4 ; i++)		
		{
			for(int j = 0; j < 5; j++)
			{
				System.out.print(seat[i][j] + "\t");		
			}
			System.out.println();
		}
	}
}





// class to access the hardcoded values from
class HardCoded
{

	// method to pass the hardcoded values from
	passNode pastPassenger(passNode head, seatMatrix seat2)
	{

		passNode newNode = head;		// declare new node to traverse the linked list

		// add 1st passenger 
		newNode.fName = "Hello";
		newNode.lName = "Hello";
		newNode.age = 23;
		newNode.gender = "F";
		newNode.train_class = "1st AC";
		newNode.date = "12/12/2023";
		newNode.seatNo = 11;
		seat2.set.remove(11);

		for (int i = 0; i < 4; i++) 		
		{
			for (int j = 0; j < 5; j++) 		    
			{
				if (seat2.seat[i][j] == 11) 
				{
					seat2.seat[i][j] = 0;	// update the seat matrix --> replace the booked seats as 0
					break;		// break from search as the occupied seat is found and updated to 0
				}
			}
		}

		newNode = newNode.next;		// move to the next node of the linked list

		// add 2nd passenger
		newNode.fName = "Rhea";
		newNode.lName = "Sharma";
		newNode.age = 21;
		newNode.gender = "F";
		newNode.train_class = "1st AC";
		newNode.date = "12/12/2023";
		newNode.seatNo = 12;
		seat2.set.remove(12);

		for (int i = 0; i < 4; i++) 		
		{
			for (int j = 0; j < 5; j++) 		    
			{
				if (seat2.seat[i][j] == 12) 
				{
					seat2.seat[i][j] = 0;	// update the seat matrix --> replace the booked seats as 0
					break;		// break from search as the occupied seat is found and updated to 0
				}
			}
		}

		newNode.next = null;		// end of the passenger linked list for now
		// rest of the passengers will be entered by the user

		// for the very first passenger in the train
		if(head == null) 	
		{
			head = newNode;
		}


		// for the rest of the passengers
		else  
		{
			passNode ptr = head;

			while(ptr.next != null) 
			{
				ptr = ptr.next;
			}
			ptr.next = newNode;
		}

		return head;
	}



	void displayHardCoded(passNode head)
	{
		passNode temp = head;

		while(temp != null)
		{
			System.out.print("\nFirst Name: " + temp.fName);
			System.out.print("\nLast Name: " + temp.lName);
			System.out.print("\nAge: " + temp.age);
			System.out.print("\nGender: " + temp.gender);
			System.out.print("\nRegNO: " + temp.Reg_no);	
			System.out.print("\nDate of journey: " + temp.date);
			System.out.print("\nClass: " + temp.train_class);
			System.out.print("\nSeat Number:" + temp.seatNo+"\n");

			temp = temp.next;		// move to the next passenger node
		}
	}
}




// class to represent the booking and display of the booked tickets
class operations1  
{
	Scanner sc = new Scanner(System.in);

	// take user input of the passener
	passNode book_ticket(passNode head, seatMatrix getSeat) 	
	{
		passNode newNode = new passNode();		// initilize a new node storing passenger details
		newNode.next = null;


		// validation for the first name to only contain alphabets
		while (true) {
			System.out.print("Enter first name: ");
			newNode.fName = sc.nextLine();

			if (newNode.fName.matches("^[a-zA-Z]*$")) {
				break;
			} else {
				System.out.println("Invalid input. Name should contain only alphabets.");
			}
		}




		// validation for the last name to only contain alphabets
		while (true) {
			System.out.print("Enter first name: ");
			newNode.lName = sc.nextLine();

			if (newNode.lName.matches("^[a-zA-Z]*$")) {
				break;
			} else {
				System.out.println("Invalid input. Name should contain only alphabets.");
			}
		}



		System.out.print("Age: ");
		newNode.age = sc.nextInt();

		// validaion to accept gender in a character/letter
		do {
			System.out.print("Enter gender (F/M) or (f/m): ");
			newNode.gender = sc.nextLine().trim().toUpperCase();
		} while (!(newNode.gender.equals("F") || newNode.gender.equals("M") || newNode.gender.equals("f") || newNode.gender.equals("m")));


		newNode.Reg_no = (int)(Math.random()*9999);		// randomly generate a registeration number for the registered passenger

		// choose the train class
		System.out.println("Select the train class:\n1.Sleeper Class\n2.1st AC\n3.2nd AC\n4.3rd AC");
		int class_choice = sc.nextInt();

		switch(class_choice) 
		{
		case 1:
			newNode.train_class = "Sleeper Class";
			break;

		case 2:
			newNode.train_class = "1st AC";
			break;

		case 3:
			newNode.train_class = "2nd AC";
			break;

		case 4:
			newNode.train_class = "3rd AC";
			break;

		default:
			System.out.println("Invalid choice");
			break;
		}


		// validation for the date to be entered in the format dd/mm/yyyy only
		boolean validInputDate = false;

		while (!validInputDate) {
			System.out.print("Enter the date in the format dd/mm/yyyy: ");
			newNode.date = sc.nextLine();

			if (newNode.date.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
				// Date input is valid
				validInputDate = true;
				System.out.println("Date accepted as it is entered in the correst format that is dd/mm/yyyy");
			} else {
				// Date input is invalid
				System.out.println("Invalid input format. Please enter the date in the format dd/mm/yyyy.");
			}
		}

		// if there are no seats avaliable
		if(getSeat.set.isEmpty()) 
		{
			System.out.println("Your application is in the waiting list, Seat will be allocated once avaliable!!");
			newNode.seatNo = 0;		// seat = 0 indicates that the passenger is in the waiting list
			getSeat.waiting.add(newNode);
		}


		// if there are no seats available display the passengers in the waiting queue
		if(getSeat.set.isEmpty())
		{
			for(passNode wait: getSeat.waiting)
			{
				System.out.println("THE WAITING LIST");
				System.out.print("\nFirst Name: " + wait.fName);
				System.out.print("\nLast Name: " + wait.lName);
				System.out.print("\nAge: " + wait.age);
				System.out.print("\nGender: " + wait.gender);
				System.out.print("\nRegNO: " + wait.Reg_no);	
				System.out.print("\nDate of journey: " + wait.date);
				System.out.print("\nClass: " + wait.train_class);
				System.out.print("\nSeat Number:" + wait.seatNo+"\n");
			}
		}


		// when there are seats avaliable in the train
		else 
		{

			getSeat.display();

			System.out.println("\nNote: 0 indicated that the seat is occupied");
			int flag = 0,seat1 = 0;



			while (flag != 1) 
			{		// loop till the selected seat is within the 11 to 30 range and is not already occupied/booked
				System.out.println("\nEnter the seat number you want to book : ");
				seat1 = sc.nextInt();

				if (!getSeat.set.contains(seat1)) 
				{
					System.out.println("Sorry!! The seat you entered is invalid.");
				} 

				else 
				{
					flag = 1;
				}
			}


			for (int i = 0; i < 4; i++) 		
			{
				for (int j = 0; j < 5; j++) 		    
				{
					if (getSeat.seat[i][j] == seat1) 
					{
						getSeat.seat[i][j] = 0;	// update the seat matrix --> replace the booked seats as 0
						break;		// break from search as the occupied seat is found and updated to 0
					}
				}
			}

			getSeat.set.remove(seat1);	// remove the seat number that is already assigned to one of the passengers
			newNode.seatNo = seat1;
		}


		// display the ticket of the passenger after they have successfully booked a ticket
		System.out.println("\nYour ticket details : ");
		System.out.print("------------------TICKET-----------------------");
		System.out.print("\nFirst Name: " + newNode.fName);
		System.out.print("\nLast Name: " + newNode.lName);
		System.out.print("\nAge: " + newNode.age);
		System.out.print("\nGender: " + newNode.gender);
		System.out.print("\nRegNO: " + newNode.Reg_no);	
		System.out.print("\nDate of journey: " + newNode.date);
		System.out.print("\nClass: " + newNode.train_class);
		System.out.print("\nSeat Number:" + newNode.seatNo + "\n");


		// for the very first passenger in the train
		if(head == null) 	
		{
			head = newNode;
		}


		// for the rest of the passengers
		else  
		{
			passNode ptr = head;

			while(ptr.next != null) 
			{
				ptr = ptr.next;
			}
			ptr.next = newNode;
		}
		return head;
	}



	HardCoded obj1 = new HardCoded();
	// display all the confirmed seat passenger
	void display_passenger(passNode head) 	
	{	

		obj1.displayHardCoded(head);

		if(head == null) 
		{
			System.out.println("No tickets have been booked.");
		}



		else  
		{
			passNode ptr = head;		// temporary node to traverse the linked list
			while(ptr != null) 
			{
				System.out.print("\nFirst Name: " + ptr.fName);
				System.out.print("\nLast Name: " + ptr.lName);
				System.out.print("\nAge: " + ptr.age);
				System.out.print("\nGender: " + ptr.gender);
				System.out.print("\nRegNO: " + ptr.Reg_no);	
				System.out.print("\nDate of journey: " + ptr.date);
				System.out.print("\nClass: " + ptr.train_class);
				System.out.print("\nSeat Number:" + ptr.seatNo+"\n");

				ptr = ptr.next;		// move to the next passenger node
			}
		}
	}	
}



// class to represent the different functions performed
class operation2  
{
	Scanner sc = new Scanner(System.in);

	Train1 T[] = new Train1[5];		// array object of the class train to store the details of the 5 trains 


	// initilizing the nodes for each train
	passNode REhead = null;
	passNode SEhead = null;
	passNode DEhead = null;
	passNode GEhead = null;
	passNode HEhead = null;

	// create 5 different objects of the class seatMatrix to represent the seat matrixes of the trains
	seatMatrix REseat = new seatMatrix();
	seatMatrix SEseat = new seatMatrix();
	seatMatrix DEseat = new seatMatrix();
	seatMatrix GEseat = new seatMatrix();
	seatMatrix HEseat = new seatMatrix();

	void create() 	
	{
		for(int i = 0; i < 5; i++) 	
		{	
			T[i] = new Train1();
		}


		// add details of the trains in the array object of the class train1

		T[0].trainNo = 4256;
		T[0].trainName = "Rajdhani Express";
		T[0].source = "Bhubaneswar";
		T[0].destination = "New Delhi";
		T[0].startTime = "9.05 am   ";
		T[0].endTime = "2.30 am";
		T[0].count = 0; 

		T[1].trainNo = 1940;
		T[1].trainName = "Shatabdi Express";
		T[1].source = "New Delhi";
		T[1].destination = "Bhopal   ";
		T[1].startTime = "6.40 am   ";
		T[1].endTime = "11.00 pm";
		T[1].count = 0;

		T[2].trainNo = 7352;
		T[2].trainName = "Duronto Express   ";
		T[2].source = "Howrah Junction";
		T[2].destination = "Bangalore";
		T[2].startTime = "11.00 am";
		T[2].endTime = "6.05 pm";
		T[2].count = 0;

		T[3].trainNo = 6233;
		T[3].trainName = "Garib-Rath Express";
		T[3].source = "Mumbai Central";
		T[3].destination = "Patna    ";
		T[3].startTime = "2.15 pm   ";
		T[3].endTime = "9.00 am";
		T[3].count = 0;

		T[4].trainNo = 9200;
		T[4].trainName = "Humsafar Express";
		T[4].source = "Anand Vihar";
		T[4].destination = "Agartala";
		T[4].startTime = "10.23 am";
		T[4].endTime = "9.05 pm";
		T[4].count = 0;		
	}


	// display the trains to choose from
	void DisplayTrain() 	
	{
		System.out.println("Following are the available trains : \n");
		System.out.println("Train no\tTrain Name\t\t\tSource\t\t\tDestination\t\tStart Time\t\t\tReach Time\n");

		for (int i = 0; i < 5; i++) 	
		{
			System.out.println(T[i].trainNo + "\t\t" + T[i].trainName + "\t\t" + T[i].source + "\t\t" + T[i].destination + "\t\t" + T[i].startTime + "\t\t\t" + T[i].endTime);
		}	
	}

	operations1 obj = new operations1();		// create an onject of the class operations1 to use the functions belonging to the class operations1 in class operations2
	HardCoded obj1 = new HardCoded();


	void BookTicket() 	
	{

		// keep asking the user to enter the train number till it is valid 
		boolean isValid = false;
		System.out.println("Enter the train number: ");
		int trainNo = 0;

		do 
		{
			trainNo = sc.nextInt();
			if (trainNo == 4256 || trainNo == 1940 || trainNo == 7352 || trainNo == 6233 || trainNo == 9200) {
				isValid = true;
			} else {
				System.out.println("Invalid train number entered. Please try again.");
			}
		} while (!isValid);


		// the number of passenger for a particular train
		System.out.println("Enter the no of the passengers travelling:");
		int passengerNo = sc.nextInt();

		SEhead = obj1.pastPassenger(SEhead, SEseat);		// for the hardcoded values

		for(int i = 0; i < passengerNo; i++) 
		{
			System.out.println("\nEnter details of passenger " + (i + 1));

			switch(trainNo) 
			{


			case 4256 :


				if(T[0].count == 0)  
				{
					REhead = obj.book_ticket(REhead, REseat);
				}

				else 
				{
					obj.book_ticket(REhead,REseat);
				}
				T[0].count++;
				break;


			case 1940 :

				SEhead = obj1.pastPassenger(SEhead, SEseat);		// for the hardcoded values

				if(T[1].count == 0) 
				{
					SEhead = obj.book_ticket(SEhead,SEseat);
				} 

				else   
				{
					obj.book_ticket(SEhead,SEseat);
				}
				T[1].count++;
				break;


			case 7352 :

				DEhead = obj1.pastPassenger(DEhead, DEseat);		// for the hardcoded values

				if(T[2].count == 0) 
				{
					DEhead = obj.book_ticket(DEhead,DEseat);
				} 

				else 
				{
					obj.book_ticket(DEhead,DEseat);
				}
				T[2].count++;
				break;


			case 6233 :

				GEhead = obj1.pastPassenger(GEhead, GEseat);		// for the hardcoded values

				if(T[3].count == 0) 
				{
					GEhead = obj.book_ticket(GEhead,GEseat);
				}

				else 
				{
					obj.book_ticket(GEhead,GEseat);
				}
				T[3].count++;
				break;


			case 9200 :

				HEhead = obj1.pastPassenger(HEhead, HEseat);		// for the hardcoded values

				if(T[4].count == 0) 
				{
					HEhead = obj.book_ticket(HEhead,HEseat);
				}

				else 
				{
					obj.book_ticket(HEhead,HEseat);
				}
				T[4].count++;
				break;


			default:
				System.out.println("This is the wrong train number");
				break;
			}
		}
	}


	void DisplayPassenger() 	
	{
		System.out.println("Enter the Train no to get passenger list : ");
		int trainNo = sc.nextInt();

		switch(trainNo) 
		{
		case 4256:
			obj1.displayHardCoded(REhead);
			obj.display_passenger(REhead);
			break;

		case 1940:
			obj1.displayHardCoded(SEhead);
			obj.display_passenger(SEhead);
			break;

		case 7352:
			obj1.displayHardCoded(DEhead);
			obj.display_passenger(DEhead);
			break;

		case 6233:
			obj1.displayHardCoded(GEhead);
			obj.display_passenger(GEhead);
			break;

		case 9200:
			obj1.displayHardCoded(HEhead);
			obj.display_passenger(HEhead);
			break;

		default:
			System.out.println("Wrong train number");
			break;
		}
	}
}



// main class
public class Railway   
{
	public static void main(String[]args) 
	{
		Scanner sc = new Scanner(System.in);

		int ch = 0;

		operation2 obj2 = new operation2();		// create an object of the class operations2 to access the functions declared in that class

		obj2.create();		// create objects of the trains 

		do 
		{
			System.out.println("\n------------------Select from the options below-----------------------");
			System.out.println("\n0. Exit the application \n1. Book ticket \n2. Display passenger \n3. Display the seat matrix \n4. Delete booking");
			System.out.println();

			System.out.println("Enter your choice : ");
			int choice = sc.nextInt();

			switch(choice) 
			{
			case 0:
				System.out.println("Thank you!!...Visit again....");
				break;

			case 1:
				obj2.DisplayTrain();
				obj2.BookTicket();
				break;	

			case 2:
				obj2.DisplayTrain();
				obj2.DisplayPassenger();
				break;
			case 3:
				break;

			default:
				System.out.println("Invalid choice!");
				break;
			}	
			System.out.println("\nEnter 1 to continue, 0 to terminate : ");
			ch = sc.nextInt();
		}
		while(ch != 0);
	}
}
