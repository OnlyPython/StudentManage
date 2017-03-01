package entity;

public class User {
	private Integer id;
	private String userName;
	private String realName;
	private String password;
	
	public User(Integer id, String userName, String realName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.realName = realName;
		this.password = password;
	}
	
	public User() {
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
