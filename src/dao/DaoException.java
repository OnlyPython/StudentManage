package dao;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 7812049835776066064L;

	public DaoException(String message, Throwable cause){
		super(message, cause);
	}
	
	public DaoException(String message){
		super(message);
	}
	
	public DaoException(Throwable cause){
		super(cause);
	}
}
