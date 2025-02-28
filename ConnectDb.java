import java.sql.*;
import java.util.*;

public class ConnectDb{
	public static void main(String[] args){
			Scanner sc = new Scanner(System.in);
		try{
			Class.forName("org.postgresql.Driver");
			System.out.println("driver Loaded");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost/contactbook","shraddha","");
			System.out.println("Connection Established");

		while(true){
			System.out.println("Menu");
			System.out.println("1. Select");
			System.out.println("2. Insert");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Search");
			System.out.println("6. Exit");

			System.out.println("Enter your choice");
			int choice = sc.nextInt();

			switch(choice){
//select statement
				case 1:
					Statement stmt = con.createStatement();
					String query = "select * from contacts";
					ResultSet rs = stmt.executeQuery(query);

					System.out.println("retrived data ..");
					System.out.println("sr.no.\t Name \t Phone \t email\t address");
					while(rs.next()){
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
					}
				break;

//insert values
				case 2:
					query = "insert into contacts(name,phone,email,address) values (?,?,?,?)";

					System.out.println("Enter name");
					String name = sc.next().toLowerCase();

					System.out.println("Enter phone no");
					String phone = sc.next();
//number validation
					if(phone.length()!=10){
						System.out.println("Invali Number should contain exactly 10 digits");
						break;
					}
					for(int i=0;i<phone.length();i++){
						if(!Character.isDigit(phone.charAt(i))){
							System.out.println("only numbers are allowed");
							break;
						}		
						
					}
//number duplicate validation
					query = "select * from contacts where phone = ?";
					ps = con.prepareStatement();
					ps.setString(1,phone);
					rs = ps.executeQuery();
					
					if(!rs){
						System.out.println("Number already exist");
					}
					
										
//email validation
					System.out.println("Enter email");
					String email = sc.next().toLowerCase();
					sc.nextLine();

					System.out.println("Enter address");
					String address = sc.nextLine().toLowerCase();

					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1,name);
					ps.setString(2,phone);
					ps.setString(3,email);
					ps.setString(4,address);

					int ans = ps.executeUpdate();
					System.out.println(ans+"record inserted");
				
				break;
			
				case 3:
					System.out.println("Enter the column name to update (name,email,phone,address)");
					String column = sc.next().toLowerCase();
					sc.nextLine();

					if(!column.matches("name|email|address|phone")){
						System.out.println("Invalid Column name");
						break;
					
					}
					System.out.println("Enter the value to update");
					String newVal = sc.nextLine();

					System.out.println("Enter the id ");
					int id = sc.nextInt();
				
					query = "update contacts set " + column + " = ? where id = ?";
				 	ps = con.prepareStatement(query);

					ps.setString(1,newVal);
					ps.setInt(2,id);
					
					ans = ps.executeUpdate();
					System.out.println(ans+"record updated");
				
				break;

				case 4:
					System.out.println("Enter the id to delete");
					id = sc.nextInt();
			
					query = "delete from contacts where id = ?";
					ps = con.prepareStatement(query);
					ps.setInt(1,id);
					ans = ps.executeUpdate();
					System.out.println(ans+"record deleted");
				
				break;
				
				case 5:
					query = "select * from contacts where name = ?";
					ps = con.prepareStatement(query);
					System.out.println("Enter name to search");
					name = sc.next().toLowerCase();
					ps.setString(1,name);
					rs = ps.executeQuery();
					System.out.println("sr.no.\t Name \t Phone \t email\t address");
					while(rs.next()){
						
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
					}				
					

				case 6:
					con.close();	
					System.exit(0);
				break;

				default:
					System.out.println("Invalid Choice");
				}
			
			}
		
			
		}
		
		catch(Exception e){
			System.out.println("Error"+e);
		}
		
		
		

	}
}
