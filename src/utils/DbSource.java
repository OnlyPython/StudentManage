package utils;

import java.sql.Connection;

public interface DbSource {
	Connection getConnection();
}
