import java.sql.*;
import java.util.*;
class ContactDb{
	
	static PreparedStatement ps;
	static Statement stmt; 
	static ResultSet rs;
	static Connection con;


	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args){
		connect();
		
		while(true){
			display();
			int choice = getChoice();
			
			switch(choice){
				case 1:
					view();
				break;
				
				case 2:
					insert();
				break;
				
				case 3:
					update();
				break;
				
				case 4:
					delete();
				break;
				
				case 5:
					search();
				break;
				
				case 6:
					System.out.println("Exiting....");
					conClose();
					System.exit(0);
					return;
				
				default:
					System.out.println("Invalid Choice Please Enter Number Between 1 to 7");
					
			
			}
		}
		
		
	}
	//
	public static void connect(){
		try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/contactbook","shraddha","");
		}
		catch(Exception e){
			System.out.println("Error"+e);
		}
	}
	//
	
	public static void display(){
		System.out.println("Menu");
			System.out.println("1. View");
			System.out.println("2. Insert");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Search");
			System.out.println("6. Exit");

			System.out.println("Enter your choice");
	}
	public static int getChoice(){
		int choice = -1;
		while(true){
			try{
				choice = input.nextInt();
				input.nextLine();
				break;
			}
			catch(InputMismatchException e){
				System.out.println("Invalid input Enter a number between 1 to 6");
				display();
			}
			
		}
		return choice;
	}
	//View Records
	public static void view(){
		try{
			stmt = con.createStatement();
			String query = "select * from contacts";
			rs = stmt.executeQuery(query);
			System.out.println("retrived data ..");
			System.out.println("sr.no.\t Name \t Phone \t email\t address");
			while(rs.next()){
					System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
			}
		}
		catch(SQLException e){
			System.out.println("Error"+e);
		}
	}
	
	//Insert Records
	public static void insert(){
		try{
			String query = "insert into contacts(name,phone,email,address) values (?,?,?,?)";
			String name;
			//validate name should contain only characters
					while(true){
						System.out.println("Enter name");
						name = input.nextLine().toLowerCase();
						if(name.matches("[a-zA-Z]+")){
							break;
						}
						else{
							System.out.println("Invalid name : Name should contain only alphabets, Please Try Again");
						}
					}
					
					String phone;
					while(true){
						System.out.println("Enter phone no");
						phone = input.next();
						boolean flag = phoneValidate(phone);
						if(flag){
							break;
						}
						
					}
					
					if(phoneExist(phone)){
						System.out.println("Mob No already Exist");
						return;
					}
//email validation
					String email;
					while(true){
					System.out.println("Enter email");
					email = input.next().toLowerCase();
					input.nextLine();
					if(email.contains("@") && email.contains(".")){
						break;
					}
					else{
						System.out.println("Invalid Email: Must Contain @ and .");
					}
					}
					
					String address;
					while(true){
						System.out.println("Enter address");
						address = input.nextLine().toLowerCase();
						if(address.matches("[a-zA-Z ]+")){
							break;
						}
						else{
							System.out.println("Invalid name : Address should contain only alphabets, Please Try Again");
						}
					}
					
					ps = con.prepareStatement(query);
					ps.setString(1,name);
					ps.setString(2,phone);
					ps.setString(3,email);
					ps.setString(4,address);

					int ans = ps.executeUpdate();
					System.out.println(ans+"record inserted");
			}
			catch(SQLException e){
			System.out.println("Error"+e);
		}
			
	}
	//phone number validation
	public static boolean phoneValidate(String phone){
			
			if(phone.length()==10){
					for(int i=0;i<phone.length();i++){
						if(!Character.isDigit(phone.charAt(i))){
							System.out.println("only numbers are allowed");
							return false;
						}		
					}	
					return true;
			}
			System.out.println("Invalid Mob Number: Only contain digits with length 10 Please Try again");
			return false;
		}
	//validate if Phone Number is already Exists
	public static boolean phoneExist(String phone){
		try{
			String query = "select * from contacts where phone = ?";
			ps = con.prepareStatement(query);
			ps.setString(1,phone);
			rs = ps.executeQuery();
		
			if(rs.next() && rs.getInt(1)>0){
				return true;
			}
			return false;
			}
		catch(SQLException e){
			System.out.println("Error"+e);
		}
			return false;
	}
	
	//update
	public static void update(){
		try{
	System.out.println("Enter the column name to update"); 
	System.out.println("1. name\n 2. phone\n3. email\n4. address)");
	
					int choice = input.nextInt();
					input.nextLine();
					String column = "";
					
					switch(choice){
						case 1: column = "name"; break;
						case 2: column = "phone"; break;
						case 3: column = "email"; break;
						case 4: column = "address"; break;
						default:
							System.out.println("Invalid Choice");
							return;
					}
					System.out.println("Enter the value to update");
					String newVal = input.nextLine();

					System.out.println("Enter the ID");
					int id = input.nextInt();
				
					String query = "update contacts set " + column + " = ? where id = ?";
				 	ps = con.prepareStatement(query);

					ps.setString(1,newVal);
					ps.setInt(2,id);
					
					int ans = ps.executeUpdate();
					System.out.println(ans+" record updated");
				}
				catch(SQLException e){
			System.out.println("Error"+e);
		}
	}
	
	//Delete Records
	public static void delete(){
		try{
		System.out.println("Enter the id to delete");
		int id = input.nextInt();
		input.nextLine();
		System.out.println("Are You Sure You want to Delete This Contact(yes/no))"); //recheck if want to delete
		String confirm = input.next().toLowerCase();
		
		if(!confirm.matches("yes")){
			System.out.println("Deletion Cancelled");
			return;
		}
					String query = "delete from contacts where id = ?";
					ps = con.prepareStatement(query);
					ps.setInt(1,id);
					int ans = ps.executeUpdate();
					if(ans>0){
						System.out.println(ans+" record deleted successfully");
					}
					else{
						System.out.println(" Contact Id not found");
					}
				}
				catch(SQLException e){
			System.out.println("Error "+e);
		}
	
	}
	//Search Records
	public static void search(){
		try{
			String query = "select * from contacts where name = ?";
					ps = con.prepareStatement(query);
					System.out.println("Enter name to search");
					String name = input.next().toLowerCase();
					ps.setString(1,name);
					rs = ps.executeQuery();
					System.out.println("sr.no.\t Name \t Phone \t email\t address");
					while(rs.next()){
						
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
					}	
		}
		catch(SQLException e){
			System.out.println("Error "+e);
		}
	}
	
	
	public static void conClose(){
		try{
			if(con!=null) con.close();
			 if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (stmt != null) stmt.close();
		}
		catch(Exception e){
			System.out.println("Error "+e);
		}
	}

	
}
