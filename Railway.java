package BufferLoop;

/*
 * Loop Club Buffer Data Structure Based Project --> Railway Registration System
 */

/*
 * Data Structure used : LinkedList, Array, Queue, HashSet, HashMap
 */

/*
 * Contributers :
 * SY Computer Dept C Division
 * UCE2021612 --> Shruti Chandak
 * UCE2021616 --> Simran Desai
 * UCE2021623  --> Anushka Gaikwad
 */



import java.util.*;


// class to represent the attributes associated with a train
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
	HashMap<String, Integer> map = new HashMap<>();		// to store the indexes of the seats

	Queue<passNode> waiting = new LinkedList<>();		// queue represents the waiting queue

	seatMatrix()		// constructor to initilize 
	{

		// the matrix will display the seat number where seat is avaliable and 0 where the seat is booked
		int k = 11;
		for(int i = 0; i < 4 ; i++)		
		{
			for(int j = 0; j < 5; j++)		
			{
				seat[i][j] = k;
				k++;
			}
		}

		// hashset represents the seats that are avaliable(not booked yet)
		for(int i = 11; i < 31; i++)		
		{
			set.add(i);		// all seats are available at the beginning 
		}


		// hashmap stores the index-value pair of the seat matrix 
		// this will be required in mapping 
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				String index = i + "," + j;
				int value = seat[i][j];
				map.put(index, value);
			}
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


// class to represent the booking, display and cancelation of the booked tickets
class operations1  
{
	Scanner sc = new Scanner(System.in);

	// take user input of the passener
	passNode book_ticket(passNode head, seatMatrix getSeat) 	
	{
		passNode newNode = new passNode();		// initilize a new node storing passenger details
		newNode.next = null;

		// validation for the first name to only contain alphabets
		while (true) 
		{
			System.out.print("Enter first name: ");
			newNode.fName = sc.next();

			if (newNode.fName.matches("^[a-zA-Z]*$")) 
			{
				break;
			} else 
			{
				System.out.println("Invalid input. Name should contain only alphabets.");
			}
		}

		// validation for the last name to only contain alphabets
		while (true) 
		{
			System.out.print("Enter last name: ");
			newNode.lName = sc.next();

			if (newNode.lName.matches("^[a-zA-Z]*$")) 
			{
				break;
			} else 
			{
				System.out.println("Invalid input. Name should contain only alphabets.");
			}
		}

		System.out.print("Age: ");
		newNode.age = sc.nextInt();

		// validaion to accept gender in a character/letter
		do 
		{
			System.out.print("Enter gender (F/M) or (f/m): ");
			newNode.gender = sc.next();
		} while (!(newNode.gender.equals("F") || newNode.gender.equals("M") || newNode.gender.equals("f") || newNode.gender.equals("m")));


		newNode.Reg_no = (int)(Math.random()*9999);		// randomly generate a registeration number for the same 

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
			newNode.date = sc.next();

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


	// display all the confirmed seat passenger
	void display_passenger(passNode head) 	
	{

		// base case : no tickets are booked
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


	// cancel booking of a passenger
	void cancelBooking(passNode head1, seatMatrix q)
	{

		// base case : no tickets have been booked
		if(head1 == null) 
		{
			System.out.println("No tickets have been booked.");
		}
		else  
		{
			System.out.println("Enter the seat number that you want to cancel : ");
			int seatNum = sc.nextInt();

			passNode temp = head1, prev = null;
			// check if the head node is the one to be deleted
			if (temp != null && temp.seatNo == seatNum) 
			{
				head1 = temp.next;
				System.out.println("Booking successfully deleted");
				return ;
			}
			// search for the node to be deleted
			while (temp != null && temp.seatNo != seatNum) 
			{
				prev = temp;
				temp = temp.next;
			}
			// if the node to be deleted is not found
			if(temp == null) {
				System.out.println("Booking not found with seat number "+seatNum);
				return ;
			}
			// delete the node
			prev.next = temp.next;
			temp.next = null;

			System.out.println("Booking successfully deleted");
			waitingToConfirm(seatNum, q);
		}
	}

	// assigning the cancelled booking(s) to the waiting passenger(s)
	void waitingToConfirm(int seatNumber, seatMatrix q)
	{
		// if the waiting queue is empty
		if(q.waiting.isEmpty())
		{
			// the seat number that is cancelled is changed from 0 to the seat number 
			// to indicate that it is now avaliable for booking 
			for (Map.Entry<String, Integer> entry : q.map.entrySet()) 
			{
				if (entry.getValue() == seatNumber) 
				{

					// get the row and column number of the value using the key (index)
					String index = entry.getKey();
					int delimiterIndex = index.indexOf(",");
					int row = Integer.parseInt(index.substring(0, delimiterIndex));
					int col = Integer.parseInt(index.substring(delimiterIndex+1));

					// update the value in the matrix
					q.seat[row][col] = seatNumber;

					// update the value in the hashmap
					q.map.put(index, seatNumber);
				}
			}


			// adding the cancelled registration seat number back into the hash set, hashset represents the avaliable seats 
			q.set.add(seatNumber);
		}


		// if the waiting queue is not empty, assign the deleted seat number to the first passenger in the waiting queue
		else if(!q.waiting.isEmpty())
		{
			passNode wait = q.waiting.poll();
			wait.seatNo = seatNumber;
			System.out.println("The deleted passenger has been replaced with the first waiting passenger in the list");
		}
	}
}



// class to represent the different functions performed
class operation2  
{
	Scanner sc = new Scanner(System.in);

	Train1 T[] = new Train1[5];		// array object of the class train to store the details of the 5 trains 

	operations1 obj = new operations1();		// create an onject of the class operations1 to use the functions belonging to the class operations1 in class operations2

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


		System.out.println("Enter the no of the passengers travelling:");
		int passengerNo = sc.nextInt();

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
				System.out.println("Wrong train number");
				break;
			}
		}
	}

	// display the confirmed passenger details by mapping them to their approriate trains
	void DisplayPassenger() 	
	{
		System.out.println("Enter the Train no to get passenger list : ");
		int trainNo = sc.nextInt();

		switch(trainNo) 
		{
		case 4256:
			obj.display_passenger(REhead);
			break;

		case 1940:
			obj.display_passenger(SEhead);
			break;

		case 7352:
			obj.display_passenger(DEhead);
			break;

		case 6233:
			obj.display_passenger(GEhead);
			break;

		case 9200:
			obj.display_passenger(HEhead);
			break;

		default:
			System.out.println("Wrong train number");
			break;
		}
	}


	// identify which train does the deletion needs to be done from
	void cancelPassenger()
	{

		System.out.println("Enter the Train no from which you want to delete your bookings : ");
		int trainNo = sc.nextInt();

		switch(trainNo) 
		{
		case 4256:
			obj.cancelBooking(REhead, REseat);
			break;

		case 1940:
			obj.cancelBooking(SEhead, SEseat);
			break;

		case 7352:
			obj.cancelBooking(DEhead, DEseat);
			break;

		case 6233:
			obj.cancelBooking(GEhead, GEseat);
			break;

		case 9200:
			obj.cancelBooking(HEhead, HEseat);
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


		System.out.println("\n\t\t*****RAILWAY REGISTRATION SYSTEM*****\n");

		do 
		{
			System.out.println("\n------------------Select From The Options Below-----------------------");
			System.out.println("\n0. Exit \n1. Book A Ticket \n2. Display Passenger Details \n3. Delete Booking");
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
				obj2.DisplayTrain();
				obj2.cancelPassenger();
				break;

			default:
				System.out.println("Invalid choice!");
				break;
			}

			System.out.println("\nEnter 1 to continue, 0 to terminate : ");
			ch = sc.nextInt();

			if(ch == 0)
			{
				System.out.println("~Thank you for visiting!!");		// bye message
			}
		}
		while(ch != 0);
	}
}
