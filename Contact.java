class Contact{

	String name;
	String mobno;
	String email;
	String address;
	
	Contact(String name,String phoneno,String email,String address){
		this.name = name;
		this.name = phoneno;
		this.email = email;
		this.address = address;
	}

	public String getName(){
		return name;
	}
	public String getMobno(){
		return mobno;
	}
	public String getEmail(){
		return email;
	}
	
	public String getAddress(){
		return address;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setMobno(String mobno){
		this.mobno = mobno;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setAddress(String address){
		this.address = address;
	}

}
