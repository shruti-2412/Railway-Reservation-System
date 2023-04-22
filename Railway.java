import java.util.*;

class Train1{
	String trainName;//train name
	int trainNo;//train no
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
}

class operations1  {
	
	Scanner sc=new Scanner(System.in);
	
	passNode book_ticket(passNode head) {
		passNode newNode=new passNode();
		newNode.next=null;
		System.out.print("First Name: ");
		newNode.fName=sc.next();
		System.out.print("Last Name: ");
		newNode.lName=sc.next();
		System.out.print("Age: ");
		newNode.age=sc.nextInt();
		System.out.print("Gender: ");
		newNode.gender=sc.next();
		newNode.Reg_no=(int)( Math.random()*9999);
		System.out.println("Select the train class:\n1.Sleeper Class\n2.1st AC\n3.2nd AC\n4.3rd AC");
		int class_choice=sc.nextInt();
		
		switch(class_choice) {
		
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
		
		if(head==null) {
			head=newNode;
		}
		else {
			passNode ptr=head;
			while(ptr.next!=null) {
				ptr=ptr.next;
			}
			ptr.next=newNode;
		}
		return head;
	}
	
	void display_passenger(passNode head) {
		if(head==null) {
			System.out.println("No tickets booked..");
		}
		else {
			passNode ptr=head;
			while(ptr!=null) {
				System.out.print("\nFirst Name: "+ptr.fName);
				System.out.print("\nLast Name: "+ptr.lName);
				System.out.print("\nAge: "+ptr.age);
				System.out.print("\nGender: "+ptr.gender);
				System.out.print("\nRegNO: "+ptr.Reg_no);	
				System.out.print("\nClass: "+ptr.train_class);
				System.out.println("\nDate of journey: "+ptr.date);
				ptr=ptr.next;
			}
		}
	}	
}

class operation2{
	Scanner sc2=new Scanner(System.in);
	Train1 T[]= new Train1[5];
	passNode REhead=null;
	passNode SEhead=null;
	passNode DEhead=null;
	passNode GEhead=null;
	passNode HEhead=null;
	
	void create() {
		for(int i=0;i<5;i++) {
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
	
	void DisplayTrain() {
		System.out.println("........Welcome to the Railway Reservation......\n");
		System.out.println("Following are the available trains:\n");
		System.out.println("Train no\tTrain Name\t\t\tSource\t\t\tDestination\t\tStart Time\t\t\tReach Time\n");
		for (int i=0;i<5;i++) {
System.out.println(T[i].trainNo+"\t\t"+T[i].trainName+"\t\t"+T[i].source+"\t\t"+T[i].destination+"\t\t"+T[i].startTime+"\t\t\t"+T[i].endTime);
		}	
	}
	operations1 obj=new operations1();
	
	
	void BookTicket() {
		System.out.println("Enter the No of Train to travel: ");
		int trainNo=sc2.nextInt();
		System.out.println("Enter the no of the passangers traveling:");
		int passengerNo=sc2.nextInt();
			for(int i=0;i<passengerNo;i++) {
				System.out.println("Enter Data of passanger:"+(i+1));
				switch(trainNo) {
				case 4256:{
					if(T[0].count==0) {
						REhead=obj.book_ticket(REhead);
					}
					else {
						obj.book_ticket(REhead);
					}
					T[0].count++;
					break;
				}
				case 1940:{
					if(T[1].count==0) {
						SEhead=obj.book_ticket(SEhead);
					}
					else {
						obj.book_ticket(SEhead);
					}
					T[1].count++;
					break;
					
				}
				case 7352:{
					if(T[2].count==0) {
						DEhead=obj.book_ticket(DEhead);
					}
					else {
						obj.book_ticket(DEhead);
					}
					T[2].count++;
					break;
					
				}
				case 6233:{
					if(T[3].count==0) {
						GEhead=obj.book_ticket(GEhead);
					}
					else {
						obj.book_ticket(GEhead);
					}
					T[3].count++;
					break;
					
				}
				case 9200:{
					if(T[0].count==0) {
						HEhead=obj.book_ticket(HEhead);
					}
					else {
						obj.book_ticket(HEhead);
					}
					T[0].count++;
					break;
					
				}
				default:
					System.out.println("You have entered wrong train no. Try again...");
				}
			}
		
	}
	
	void DisplayPassenger() {
		System.out.println("Enter the No of Train to get Passenger list: ");
		int trainNo=sc2.nextInt();
		
			switch(trainNo) {
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

public class Railway {
public static void main(String[]args) {
	Scanner sc1=new Scanner(System.in);
	
	int choice=0;
	operation2 obj2=new operation2();
	obj2.create();
	
	do {
	System.out.println("______MENU______");
	System.out.println("\n1.Book Ticket.\n2.Display Passenger");
	System.out.println();
	System.out.println("Enter your choice: ");
	choice=sc1.nextInt();
	switch(choice) {
	case 1:{
		obj2.DisplayTrain();
		obj2.BookTicket();
		break;	
	}
	case 2:{
		obj2.DisplayTrain();
		obj2.DisplayPassenger();
		break;
	}	
	}	
	}while(choice!=0);
}
}
