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


/*


 *****RAILWAY REGISTRATION SYSTEM*****


------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
42546
Wrong train number

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
4245
Wrong train number

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
4256
No tickets have been booked.

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
3
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no from which you want to delete your bookings : 
4256
No tickets have been booked.

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
1
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the train number: 
9200
Enter the no of the passengers travelling:
3

Enter details of passenger 1
Enter first name: evan 
Enter last name: smith
Age: 20
Enter gender (F/M) or (f/m): F
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	14	15	
16	17	18	19	20	
21	22	23	24	25	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
15

Your ticket details : 
------------------TICKET-----------------------
First Name: evan
Last Name: smith
Age: 20
Gender: F
RegNO: 8
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:15

Enter details of passenger 2
Enter first name: jana
Enter last name: jester
Age: 21
Enter gender (F/M) or (f/m): F
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	14	0	
16	17	18	19	20	
21	22	23	24	25	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
14

Your ticket details : 
------------------TICKET-----------------------
First Name: jana
Last Name: jester
Age: 21
Gender: F
RegNO: 9658
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

Enter details of passenger 3
Enter first name: 23
Invalid input. Name should contain only alphabets.
Enter first name: SD$#
Invalid input. Name should contain only alphabets.
Enter first name: john
Enter last name: smith
Age: 23
Enter gender (F/M) or (f/m): h
Enter gender (F/M) or (f/m): 45
Enter gender (F/M) or (f/m): M
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12230232
Invalid input format. Please enter the date in the format dd/mm/yyyy.
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	0	
16	17	18	19	20	
21	22	23	24	25	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
20

Your ticket details : 
------------------TICKET-----------------------
First Name: john
Last Name: smith
Age: 23
Gender: M
RegNO: 819
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
9200

First Name: evan
Last Name: smith
Age: 20
Gender: F
RegNO: 8
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:15

First Name: jana
Last Name: jester
Age: 21
Gender: F
RegNO: 9658
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

First Name: john
Last Name: smith
Age: 23
Gender: M
RegNO: 819
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
3
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no from which you want to delete your bookings : 
9200
Enter the seat number that you want to cancel : 
20
Booking successfully deleted

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
9200

First Name: evan
Last Name: smith
Age: 20
Gender: F
RegNO: 8
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:15

First Name: jana
Last Name: jester
Age: 21
Gender: F
RegNO: 9658
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
1
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the train number: 
4256
Enter the no of the passengers travelling:
20

Enter details of passenger 1
Enter first name: hoah 
Enter last name: smith 
Age: 23
Enter gender (F/M) or (f/m): M
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	14	15	
16	17	18	19	20	
21	22	23	24	25	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
14

Your ticket details : 
------------------TICKET-----------------------
First Name: hoah
Last Name: smith
Age: 23
Gender: M
RegNO: 334
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

Enter details of passenger 2
Enter first name: emma
Enter last name: harris
Age: 20
Enter gender (F/M) or (f/m): F
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	15	
16	17	18	19	20	
21	22	23	24	25	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
25

Your ticket details : 
------------------TICKET-----------------------
First Name: emma
Last Name: harris
Age: 20
Gender: F
RegNO: 3763
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:25

Enter details of passenger 3
Enter first name: liam
Enter last name: allen
Age: 27
Enter gender (F/M) or (f/m): M
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	15	
16	17	18	19	20	
21	22	23	24	0	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
16

Your ticket details : 
------------------TICKET-----------------------
First Name: liam
Last Name: allen
Age: 27
Gender: M
RegNO: 1544
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:16

Enter details of passenger 4
Enter first name: olivia
Enter last name: evans
Age: 20
Enter gender (F/M) or (f/m): F
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	15	
0	17	18	19	20	
21	22	23	24	0	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
22

Your ticket details : 
------------------TICKET-----------------------
First Name: olivia
Last Name: evans
Age: 20
Gender: F
RegNO: 3661
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:22

Enter details of passenger 5
Enter first name: ava
Enter last name: smith
Age: 20
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
4
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	15	
0	17	18	19	20	
21	0	23	24	0	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
200
Sorry!! The seat you entered is invalid.

Enter the seat number you want to book : 
15

Your ticket details : 
------------------TICKET-----------------------
First Name: ava
Last Name: smith
Age: 20
Gender: f
RegNO: 6404
Date of journey: 12/12/2023
Class: 3rd AC
Seat Number:15

Enter details of passenger 6
Enter first name: micheal 
Enter last name: amith
Age: 28
Enter gender (F/M) or (f/m): M
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 121/23/
Invalid input format. Please enter the date in the format dd/mm/yyyy.
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	0	
0	17	18	19	20	
21	0	23	24	0	
26	27	28	29	30	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
30

Your ticket details : 
------------------TICKET-----------------------
First Name: micheal
Last Name: amith
Age: 28
Gender: M
RegNO: 4432
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:30

Enter details of passenger 7
Enter first name: william
Enter last name: smith
Age: 29
Enter gender (F/M) or (f/m): M
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	0	
0	17	18	19	20	
21	0	23	24	0	
26	27	28	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
28

Your ticket details : 
------------------TICKET-----------------------
First Name: william
Last Name: smith
Age: 29
Gender: M
RegNO: 6901
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:28

Enter details of passenger 8
Enter first name: divia
Enter last name: harris
Age: 23
Enter gender (F/M) or (f/m): F
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	0	
0	17	18	19	20	
21	0	23	24	0	
26	27	0	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
20

Your ticket details : 
------------------TICKET-----------------------
First Name: divia
Last Name: harris
Age: 23
Gender: F
RegNO: 9739
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

Enter details of passenger 9
Enter first name: james
Enter last name: carter
Age: 22
Enter gender (F/M) or (f/m): M
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	0	
0	17	18	19	0	
21	0	23	24	0	
26	27	0	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
18

Your ticket details : 
------------------TICKET-----------------------
First Name: james
Last Name: carter
Age: 22
Gender: M
RegNO: 4109
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:18

Enter details of passenger 10
Enter first name: lily
Enter last name: harris
Age: 20
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	13	0	0	
0	17	0	19	0	
21	0	23	24	0	
26	27	0	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
13

Your ticket details : 
------------------TICKET-----------------------
First Name: lily
Last Name: harris
Age: 20
Gender: f
RegNO: 5607
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:13

Enter details of passenger 11
Enter first name: taylor 
Enter last name: swift
Age: 29
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	0	0	0	
0	17	0	19	0	
21	0	23	24	0	
26	27	0	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
24

Your ticket details : 
------------------TICKET-----------------------
First Name: taylor
Last Name: swift
Age: 29
Gender: f
RegNO: 9934
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:24

Enter details of passenger 12
Enter first name: sofia
Enter last name: carter
Age: 19
Enter gender (F/M) or (f/m): F
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	0	0	0	
0	17	0	19	0	
21	0	23	0	0	
26	27	0	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
23

Your ticket details : 
------------------TICKET-----------------------
First Name: sofia
Last Name: carter
Age: 19
Gender: F
RegNO: 2044
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:23

Enter details of passenger 13
Enter first name: zoey 
Enter last name: harris
Age: 12
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	0	0	0	
0	17	0	19	0	
21	0	0	0	0	
26	27	0	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
18
Sorry!! The seat you entered is invalid.

Enter the seat number you want to book : 
19

Your ticket details : 
------------------TICKET-----------------------
First Name: zoey
Last Name: harris
Age: 12
Gender: f
RegNO: 1390
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:19

Enter details of passenger 14
Enter first name: jimin
Enter last name: cartere
Age: 23
Enter gender (F/M) or (f/m): M
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	0	0	0	
0	17	0	0	0	
21	0	0	0	0	
26	27	0	29	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
29

Your ticket details : 
------------------TICKET-----------------------
First Name: jimin
Last Name: cartere
Age: 23
Gender: M
RegNO: 3584
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:29

Enter details of passenger 15
Enter first name: ella
Enter last name: james
Age: 20
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
2
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	0	0	0	
0	17	0	0	0	
21	0	0	0	0	
26	27	0	0	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
27

Your ticket details : 
------------------TICKET-----------------------
First Name: ella
Last Name: james
Age: 20
Gender: f
RegNO: 38
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:27

Enter details of passenger 16
Enter first name: leah
Enter last name: carter
Age: 33
Enter gender (F/M) or (f/m): m
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	0	0	0	
0	17	0	0	0	
21	0	0	0	0	
26	0	0	0	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
17

Your ticket details : 
------------------TICKET-----------------------
First Name: leah
Last Name: carter
Age: 33
Gender: m
RegNO: 3409
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:17

Enter details of passenger 17
Enter first name: mia
Enter last name: lee
Age: 19
Enter gender (F/M) or (f/m): F
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	12	0	0	0	
0	0	0	0	0	
21	0	0	0	0	
26	0	0	0	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
12

Your ticket details : 
------------------TICKET-----------------------
First Name: mia
Last Name: lee
Age: 19
Gender: F
RegNO: 3241
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:12

Enter details of passenger 18
Enter first name: jane
Enter last name: smith
Age: 12
Enter gender (F/M) or (f/m): d
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	0	0	0	0	
0	0	0	0	0	
21	0	0	0	0	
26	0	0	0	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
21

Your ticket details : 
------------------TICKET-----------------------
First Name: jane
Last Name: smith
Age: 12
Gender: f
RegNO: 9475
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:21

Enter details of passenger 19
Enter first name: john s
Enter last name: Age: 12
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
11	0	0	0	0	
0	0	0	0	0	
0	0	0	0	0	
26	0	0	0	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
11

Your ticket details : 
------------------TICKET-----------------------
First Name: john
Last Name: s
Age: 12
Gender: f
RegNO: 1100
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:11

Enter details of passenger 20
Enter first name: ava
Enter last name: grace
Age: 20
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
U	M	L	L	U
0	0	0	0	0	
0	0	0	0	0	
0	0	0	0	0	
26	0	0	0	0	

Note: 0 indicated that the seat is occupied

Enter the seat number you want to book : 
26

Your ticket details : 
------------------TICKET-----------------------
First Name: ava
Last Name: grace
Age: 20
Gender: f
RegNO: 3846
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:26

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
4256

First Name: hoah
Last Name: smith
Age: 23
Gender: M
RegNO: 334
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

First Name: emma
Last Name: harris
Age: 20
Gender: F
RegNO: 3763
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:25

First Name: liam
Last Name: allen
Age: 27
Gender: M
RegNO: 1544
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:16

First Name: olivia
Last Name: evans
Age: 20
Gender: F
RegNO: 3661
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:22

First Name: ava
Last Name: smith
Age: 20
Gender: f
RegNO: 6404
Date of journey: 12/12/2023
Class: 3rd AC
Seat Number:15

First Name: micheal
Last Name: amith
Age: 28
Gender: M
RegNO: 4432
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:30

First Name: william
Last Name: smith
Age: 29
Gender: M
RegNO: 6901
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:28

First Name: divia
Last Name: harris
Age: 23
Gender: F
RegNO: 9739
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

First Name: james
Last Name: carter
Age: 22
Gender: M
RegNO: 4109
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:18

First Name: lily
Last Name: harris
Age: 20
Gender: f
RegNO: 5607
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:13

First Name: taylor
Last Name: swift
Age: 29
Gender: f
RegNO: 9934
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:24

First Name: sofia
Last Name: carter
Age: 19
Gender: F
RegNO: 2044
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:23

First Name: zoey
Last Name: harris
Age: 12
Gender: f
RegNO: 1390
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:19

First Name: jimin
Last Name: cartere
Age: 23
Gender: M
RegNO: 3584
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:29

First Name: ella
Last Name: james
Age: 20
Gender: f
RegNO: 38
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:27

First Name: leah
Last Name: carter
Age: 33
Gender: m
RegNO: 3409
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:17

First Name: mia
Last Name: lee
Age: 19
Gender: F
RegNO: 3241
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:12

First Name: jane
Last Name: smith
Age: 12
Gender: f
RegNO: 9475
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:21

First Name: john
Last Name: s
Age: 12
Gender: f
RegNO: 1100
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:11

First Name: ava
Last Name: grace
Age: 20
Gender: f
RegNO: 3846
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:26

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
1
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the train number: 
4256
Enter the no of the passengers travelling:
3

Enter details of passenger 1
Enter first name: jack
Enter last name: allen
Age: 20
Enter gender (F/M) or (f/m): m
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
Your application is in the waiting list, Seat will be allocated once avaliable!!
THE WAITING LIST

First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Your ticket details : 
------------------TICKET-----------------------
First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Enter details of passenger 2
Enter first name: amelia
Enter last name: cater
Age: 20
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
Your application is in the waiting list, Seat will be allocated once avaliable!!
THE WAITING LIST

First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0
THE WAITING LIST

First Name: amelia
Last Name: cater
Age: 20
Gender: f
RegNO: 2999
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Your ticket details : 
------------------TICKET-----------------------
First Name: amelia
Last Name: cater
Age: 20
Gender: f
RegNO: 2999
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Enter details of passenger 3
Enter first name: grace
Enter last name: carrter
Age: 20
Enter gender (F/M) or (f/m): f
Select the train class:
1.Sleeper Class
2.1st AC
3.2nd AC
4.3rd AC
1
Enter the date in the format dd/mm/yyyy: 12/12/2023
Date accepted as it is entered in the correst format that is dd/mm/yyyy
Your application is in the waiting list, Seat will be allocated once avaliable!!
THE WAITING LIST

First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0
THE WAITING LIST

First Name: amelia
Last Name: cater
Age: 20
Gender: f
RegNO: 2999
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0
THE WAITING LIST

First Name: grace
Last Name: carrter
Age: 20
Gender: f
RegNO: 1589
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Your ticket details : 
------------------TICKET-----------------------
First Name: grace
Last Name: carrter
Age: 20
Gender: f
RegNO: 1589
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
4256

First Name: hoah
Last Name: smith
Age: 23
Gender: M
RegNO: 334
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

First Name: emma
Last Name: harris
Age: 20
Gender: F
RegNO: 3763
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:25

First Name: liam
Last Name: allen
Age: 27
Gender: M
RegNO: 1544
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:16

First Name: olivia
Last Name: evans
Age: 20
Gender: F
RegNO: 3661
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:22

First Name: ava
Last Name: smith
Age: 20
Gender: f
RegNO: 6404
Date of journey: 12/12/2023
Class: 3rd AC
Seat Number:15

First Name: micheal
Last Name: amith
Age: 28
Gender: M
RegNO: 4432
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:30

First Name: william
Last Name: smith
Age: 29
Gender: M
RegNO: 6901
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:28

First Name: divia
Last Name: harris
Age: 23
Gender: F
RegNO: 9739
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

First Name: james
Last Name: carter
Age: 22
Gender: M
RegNO: 4109
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:18

First Name: lily
Last Name: harris
Age: 20
Gender: f
RegNO: 5607
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:13

First Name: taylor
Last Name: swift
Age: 29
Gender: f
RegNO: 9934
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:24

First Name: sofia
Last Name: carter
Age: 19
Gender: F
RegNO: 2044
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:23

First Name: zoey
Last Name: harris
Age: 12
Gender: f
RegNO: 1390
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:19

First Name: jimin
Last Name: cartere
Age: 23
Gender: M
RegNO: 3584
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:29

First Name: ella
Last Name: james
Age: 20
Gender: f
RegNO: 38
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:27

First Name: leah
Last Name: carter
Age: 33
Gender: m
RegNO: 3409
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:17

First Name: mia
Last Name: lee
Age: 19
Gender: F
RegNO: 3241
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:12

First Name: jane
Last Name: smith
Age: 12
Gender: f
RegNO: 9475
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:21

First Name: john
Last Name: s
Age: 12
Gender: f
RegNO: 1100
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:11

First Name: ava
Last Name: grace
Age: 20
Gender: f
RegNO: 3846
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:26

First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

First Name: amelia
Last Name: cater
Age: 20
Gender: f
RegNO: 2999
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

First Name: grace
Last Name: carrter
Age: 20
Gender: f
RegNO: 1589
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
3
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no from which you want to delete your bookings : 
4256
Enter the seat number that you want to cancel : 
12
Booking successfully deleted
The deleted passenger has been replaced with the first waiting passenger in the list

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
4256

First Name: hoah
Last Name: smith
Age: 23
Gender: M
RegNO: 334
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

First Name: emma
Last Name: harris
Age: 20
Gender: F
RegNO: 3763
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:25

First Name: liam
Last Name: allen
Age: 27
Gender: M
RegNO: 1544
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:16

First Name: olivia
Last Name: evans
Age: 20
Gender: F
RegNO: 3661
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:22

First Name: ava
Last Name: smith
Age: 20
Gender: f
RegNO: 6404
Date of journey: 12/12/2023
Class: 3rd AC
Seat Number:15

First Name: micheal
Last Name: amith
Age: 28
Gender: M
RegNO: 4432
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:30

First Name: william
Last Name: smith
Age: 29
Gender: M
RegNO: 6901
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:28

First Name: divia
Last Name: harris
Age: 23
Gender: F
RegNO: 9739
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

First Name: james
Last Name: carter
Age: 22
Gender: M
RegNO: 4109
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:18

First Name: lily
Last Name: harris
Age: 20
Gender: f
RegNO: 5607
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:13

First Name: taylor
Last Name: swift
Age: 29
Gender: f
RegNO: 9934
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:24

First Name: sofia
Last Name: carter
Age: 19
Gender: F
RegNO: 2044
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:23

First Name: zoey
Last Name: harris
Age: 12
Gender: f
RegNO: 1390
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:19

First Name: jimin
Last Name: cartere
Age: 23
Gender: M
RegNO: 3584
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:29

First Name: ella
Last Name: james
Age: 20
Gender: f
RegNO: 38
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:27

First Name: leah
Last Name: carter
Age: 33
Gender: m
RegNO: 3409
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:17

First Name: jane
Last Name: smith
Age: 12
Gender: f
RegNO: 9475
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:21

First Name: john
Last Name: s
Age: 12
Gender: f
RegNO: 1100
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:11

First Name: ava
Last Name: grace
Age: 20
Gender: f
RegNO: 3846
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:26

First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:12

First Name: amelia
Last Name: cater
Age: 20
Gender: f
RegNO: 2999
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

First Name: grace
Last Name: carrter
Age: 20
Gender: f
RegNO: 1589
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
3
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no from which you want to delete your bookings : 
4256
Enter the seat number that you want to cancel : 
16
Booking successfully deleted
The deleted passenger has been replaced with the first waiting passenger in the list

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
4256

First Name: hoah
Last Name: smith
Age: 23
Gender: M
RegNO: 334
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

First Name: emma
Last Name: harris
Age: 20
Gender: F
RegNO: 3763
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:25

First Name: olivia
Last Name: evans
Age: 20
Gender: F
RegNO: 3661
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:22

First Name: ava
Last Name: smith
Age: 20
Gender: f
RegNO: 6404
Date of journey: 12/12/2023
Class: 3rd AC
Seat Number:15

First Name: micheal
Last Name: amith
Age: 28
Gender: M
RegNO: 4432
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:30

First Name: william
Last Name: smith
Age: 29
Gender: M
RegNO: 6901
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:28

First Name: divia
Last Name: harris
Age: 23
Gender: F
RegNO: 9739
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

First Name: james
Last Name: carter
Age: 22
Gender: M
RegNO: 4109
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:18

First Name: lily
Last Name: harris
Age: 20
Gender: f
RegNO: 5607
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:13

First Name: taylor
Last Name: swift
Age: 29
Gender: f
RegNO: 9934
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:24

First Name: sofia
Last Name: carter
Age: 19
Gender: F
RegNO: 2044
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:23

First Name: zoey
Last Name: harris
Age: 12
Gender: f
RegNO: 1390
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:19

First Name: jimin
Last Name: cartere
Age: 23
Gender: M
RegNO: 3584
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:29

First Name: ella
Last Name: james
Age: 20
Gender: f
RegNO: 38
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:27

First Name: leah
Last Name: carter
Age: 33
Gender: m
RegNO: 3409
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:17

First Name: jane
Last Name: smith
Age: 12
Gender: f
RegNO: 9475
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:21

First Name: john
Last Name: s
Age: 12
Gender: f
RegNO: 1100
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:11

First Name: ava
Last Name: grace
Age: 20
Gender: f
RegNO: 3846
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:26

First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:12

First Name: amelia
Last Name: cater
Age: 20
Gender: f
RegNO: 2999
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:16

First Name: grace
Last Name: carrter
Age: 20
Gender: f
RegNO: 1589
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:0

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
3
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no from which you want to delete your bookings : 
4256
Enter the seat number that you want to cancel : 
24
Booking successfully deleted
The deleted passenger has been replaced with the first waiting passenger in the list

Enter 1 to continue, 0 to terminate : 
1

------------------Select From The Options Below-----------------------

0. Exit 
1. Book A Ticket 
2. Display Passenger Details 
3. Delete Booking

Enter your choice : 
2
Following are the available trains : 

Train no	Train Name			Source			Destination		Start Time			Reach Time

4256		Rajdhani Express		Bhubaneswar		New Delhi		9.05 am   			2.30 am
1940		Shatabdi Express		New Delhi		Bhopal   		6.40 am   			11.00 pm
7352		Duronto Express   		Howrah Junction		Bangalore		11.00 am			6.05 pm
6233		Garib-Rath Express		Mumbai Central		Patna    		2.15 pm   			9.00 am
9200		Humsafar Express		Anand Vihar		Agartala		10.23 am			9.05 pm
Enter the Train no to get passenger list : 
4256

First Name: hoah
Last Name: smith
Age: 23
Gender: M
RegNO: 334
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:14

First Name: emma
Last Name: harris
Age: 20
Gender: F
RegNO: 3763
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:25

First Name: olivia
Last Name: evans
Age: 20
Gender: F
RegNO: 3661
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:22

First Name: ava
Last Name: smith
Age: 20
Gender: f
RegNO: 6404
Date of journey: 12/12/2023
Class: 3rd AC
Seat Number:15

First Name: micheal
Last Name: amith
Age: 28
Gender: M
RegNO: 4432
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:30

First Name: william
Last Name: smith
Age: 29
Gender: M
RegNO: 6901
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:28

First Name: divia
Last Name: harris
Age: 23
Gender: F
RegNO: 9739
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:20

First Name: james
Last Name: carter
Age: 22
Gender: M
RegNO: 4109
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:18

First Name: lily
Last Name: harris
Age: 20
Gender: f
RegNO: 5607
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:13

First Name: sofia
Last Name: carter
Age: 19
Gender: F
RegNO: 2044
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:23

First Name: zoey
Last Name: harris
Age: 12
Gender: f
RegNO: 1390
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:19

First Name: jimin
Last Name: cartere
Age: 23
Gender: M
RegNO: 3584
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:29

First Name: ella
Last Name: james
Age: 20
Gender: f
RegNO: 38
Date of journey: 12/12/2023
Class: 1st AC
Seat Number:27

First Name: leah
Last Name: carter
Age: 33
Gender: m
RegNO: 3409
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:17

First Name: jane
Last Name: smith
Age: 12
Gender: f
RegNO: 9475
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:21

First Name: john
Last Name: s
Age: 12
Gender: f
RegNO: 1100
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:11

First Name: ava
Last Name: grace
Age: 20
Gender: f
RegNO: 3846
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:26

First Name: jack
Last Name: allen
Age: 20
Gender: m
RegNO: 3003
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:12

First Name: amelia
Last Name: cater
Age: 20
Gender: f
RegNO: 2999
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:16

First Name: grace
Last Name: carrter
Age: 20
Gender: f
RegNO: 1589
Date of journey: 12/12/2023
Class: Sleeper Class
Seat Number:24

Enter 1 to continue, 0 to terminate : 
0
~Thank you for visiting!!

 */
