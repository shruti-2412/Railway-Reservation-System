package BufferLoop;



/*
 * 
 * Data Structure used : LinkedList, Array, Queue, HashSet
 * Concepts used : OOPs, Data Structure
 */

import java.util.*;

class Train1{
	String trainName;  
	int trainNo;  
	String source,destination;
	String startTime, endTime;
	float price;
	int count;	
	
	Train1(){
		count=0;
	}
}

class passNode{
	String fName,lName;
	int Reg_no;
	int age;
	String gender;
	String train_class;
	passNode next;	
	String date;
	int seatNo;
}


//seat matrix
class seatMatrix
{
	int[][] seat = new int[4][5];	// to store the seat matrix for each train
	HashSet<Integer> set = new HashSet<>();	// to store the seat number so that 
	seatMatrix()
	{
		int k = 11;
		for(int i = 0; i < 4 ; i++)		{
			for(int j = 0; j < 5; j++)		{
				seat[i][j] = k;
				k++;
			}
		}
		
		for(int i = 11; i < 31; i++)		{
			set.add(i);		// add all the seat number in each of the hashsets for each 5 trains
		}
	}
	
	void display() {
		System.out.println("U\tM\tL\tL\tU");
		for(int i = 0; i < 4 ; i++)		{
			for(int j = 0; j < 5; j++){
				System.out.print(seat[i][j]+"\t");		// seat matrix for the 
			}
			System.out.println();
		}
	}
}

class operations1  {
	Scanner sc = new Scanner(System.in);
	
	passNode book_ticket(passNode head, seatMatrix getSeat) 	{
		passNode newNode=new passNode();
		newNode.next=null;
		System.out.print("First Name: ");
		newNode.fName=sc.next();
		System.out.print("Last Name: ");
		newNode.lName=sc.next();
		System.out.print("Age: ");
		newNode.age=sc.nextInt();
		System.out.print("Gender(F or M): ");
		newNode.gender=sc.next();
		newNode.Reg_no=(int)(Math.random()*9999);
    	System.out.println("Select the train class:\n1.Sleeper Class\n2.1st AC\n3.2nd AC\n4.3rd AC");
		int class_choice=sc.nextInt();

		switch(class_choice) 
		{
		case 1:
			newNode.train_class="Sleeper Class";
			break;
		case 2:
			newNode.train_class="1st AC";
			break;
		case 3:
			newNode.train_class="2nd AC";
			break;
		case 4:
			newNode.train_class="3rd AC";
			break;
		}
		
		System.out.println("Enter the date of your journey(DD/MM/YYYY): ");
		newNode.date=sc.next();
		
		getSeat.display();
		
		System.out.println("\nNote: 0 indicated that the seat is occupied");
		int flag=0,seat1=0;
		
		while (flag != 1) {		// loop till the selected seat is within the 11 to 30 range and is not already occupied/booked
		    System.out.println("\nEnter the seat number you want to select: ");
		    seat1 = sc.nextInt();
		    if (!getSeat.set.contains(seat1)) {
		        System.out.println("Sorry!! the seat you entered is invalid ");
		    } else {
		        flag = 1;
		    }
		}

		for (int i = 0; i < 4; i++) 		{
		    for (int j = 0; j < 5; j++) 		    {
		        if (getSeat.seat[i][j] == seat1) {
		            getSeat.seat[i][j] = 0;	// update the seat matrix --> replace the booked seats as 0
		            break;		// break from search as the occupied seat is found and updated to 0
		        }
		    }
		}
		getSeat.set.remove(seat1);	// remove the seat number that is already assigned to one of the passengers
		
		newNode.seatNo=seat1;
		if(head==null) 		
			head=newNode;
		
		else  {
			passNode ptr=head;
			while(ptr.next!=null) 
			{
				ptr=ptr.next;
			}
			ptr.next=newNode;
		}
		return head;
		
		
	}

	void display_passenger(passNode head) 	{
		if(head==null) 
			System.out.println("No tickets booked..");
		
		else  {
			passNode ptr=head;
			while(ptr!=null) 
			{
				System.out.print("\nFirst Name: "+ptr.fName);
				System.out.print("\nLast Name: "+ptr.lName);
				System.out.print("\nAge: "+ptr.age);
				System.out.print("\nGender: "+ptr.gender);
				System.out.print("\nRegNO: "+ptr.Reg_no);	
		
				System.out.print("\nDate of journey: "+ptr.date);
				System.out.print("\nClass: "+ptr.train_class);
				System.out.print("\nSeat Number:"+ptr.seatNo+"\n");
				
				ptr=ptr.next;
			}
		}
	}	
}

class operation2  {
	Scanner sc=new Scanner(System.in);
	Train1 T[]= new Train1[5];
	passNode REhead=null;
	passNode SEhead=null;
	passNode DEhead=null;
	passNode GEhead=null;
	passNode HEhead=null;
	seatMatrix REseat = new seatMatrix();
	seatMatrix SEseat = new seatMatrix();
	seatMatrix DEseat = new seatMatrix();
	seatMatrix GEseat = new seatMatrix();
	seatMatrix HEseat = new seatMatrix();
	
	int s[][] = new int[4][5]; // seat arrangement array
	int seat_no[] = new int[30]; // array to store seat numbers of the passengers


	void create() 	{
		for(int i=0;i<5;i++) 	{	
			T[i]=new Train1();
		}

		T[0].trainNo= 4256;
		T[0].trainName="Rajdhani Express";
		T[0].source="Bhubaneswar";
		T[0].destination="New Delhi";
		T[0].startTime="9.05 am   ";
		T[0].endTime="2.30 am";
		T[0].count=0;

		T[1].trainNo= 1940;
		T[1].trainName="Shatabdi Express";
		T[1].source="New Delhi";
		T[1].destination="Bhopal   ";
		T[1].startTime="6.40 am   ";
		T[1].endTime="11.00 pm";
		T[1].count=0;

		T[2].trainNo= 7352;
		T[2].trainName="Duronto Express   ";
		T[2].source="Howrah Junction";
		T[2].destination="Bangalore";
		T[2].startTime="11.00 am";
		T[2].endTime="6.05 pm";
		T[2].count=0;

		T[3].trainNo= 6233;
		T[3].trainName="Garib-Rath Express";
		T[3].source="Mumbai Central";
		T[3].destination="Patna    ";
		T[3].startTime="2.15 pm   ";
		T[3].endTime="9.00 am";
		T[3].count=0;

		T[4].trainNo= 9200;
		T[4].trainName="Humsafar Express";
		T[4].source="Anand Vihar";
		T[4].destination="Agartala";
		T[4].startTime="10.23 am";
		T[4].endTime="9.05 pm";
		T[4].count=0;		
	}
	
	void DisplayTrain() 	{
		System.out.println("Following are the available trains:\n");
		System.out.println("Train no\tTrain Name\t\t\tSource\t\t\tDestination\t\tStart Time\t\t\tReach Time\n");
		for (int i=0;i<5;i++) 	{
			System.out.println(T[i].trainNo+"\t\t"+T[i].trainName+"\t\t"+T[i].source+"\t\t"+T[i].destination+"\t\t"+T[i].startTime+"\t\t\t"+T[i].endTime);
		}	
	}
	operations1 obj=new operations1();


	void BookTicket() 	{
		System.out.println("\nEnter the train no to book tickets: ");
		int trainNo=sc.nextInt();
		System.out.println("Enter the no of the passengers travelling:");
		int passengerNo=sc.nextInt();
		
		for(int i=0;i<passengerNo;i++) {
			System.out.println("\nEnter details of passenger "+(i+1));
			
			switch(trainNo) {
			case 4256:
				if(T[0].count==0) {
					REhead=obj.book_ticket(REhead, REseat);
				}
				else {
					obj.book_ticket(REhead,REseat);
				}
				T[0].count++;
				System.out.println("\nYour ticket details:");
		        System.out.print("------------------TICKET-----------------------");
		        obj.display_passenger(REhead);
				break;
			
			case 1940:
				if(T[1].count==0) {
					SEhead=obj.book_ticket(SEhead,SEseat);
				}
				else {
					obj.book_ticket(SEhead,SEseat);
				}
				T[1].count++;
				System.out.println("\nYour ticket details:");
		        System.out.print("------------------TICKET-----------------------");
		        obj.display_passenger(SEhead);
				break;

			case 7352:
				if(T[2].count==0) {
					DEhead=obj.book_ticket(DEhead,DEseat);
				}
				else {
					obj.book_ticket(DEhead,DEseat);
				}
				T[2].count++;
				System.out.println("\nYour ticket details:");
		        System.out.print("------------------TICKET-----------------------");
		        obj.display_passenger(DEhead);
				break;

			case 6233:
				if(T[3].count==0) {
					GEhead=obj.book_ticket(GEhead,GEseat);
				}
				else {
					obj.book_ticket(GEhead,GEseat);
				}
				T[3].count++;
				System.out.println("\nYour ticket details:");
		        System.out.print("------------------TICKET-----------------------");
		        obj.display_passenger(GEhead);
				break;

			case 9200:
				if(T[0].count==0) {
					HEhead=obj.book_ticket(HEhead,HEseat);
				}
				else {
					obj.book_ticket(HEhead,HEseat);
				}
				T[0].count++;
				System.out.println("\nYour ticket details:");
		        System.out.print("------------------TICKET-----------------------");
		        obj.display_passenger(HEhead);
				break;

			default:
				System.out.println("You have entered wrong train no. Try again...");
			}
			
		}
	}

	void DisplayPassenger() 	{
		System.out.println("Enter the Train no to get passenger list: ");
		int trainNo=sc.nextInt();

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
			System.out.println("You have entered wrong train no. Try again....");
		}
	}
}

public class Railway   {
	public static void main(String[]args) {
		Scanner sc = new Scanner(System.in);

		int choice = 0;
		operation2 obj2 = new operation2();
		obj2.create();
	
		do {
			System.out.println("\n------------------Select from the options below-----------------------");
			System.out.println("0.Exit application\n1.Book Ticket.\n2.Display Passenger\n3.Display the seat matrix\n4.Delete booking");
			System.out.println();
			System.out.println("Enter your choice: ");
			choice = sc.nextInt();

			switch(choice) {
			case 0:
				System.out.println("Thank you for bookings...Visit again....");
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
			
			case 4:
				break;
				
			default:
				System.out.println("Invalid choice!");
				break;
			
			}	
		}while(choice!= 0);
	}
}
