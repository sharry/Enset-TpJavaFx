package persistence.abstractions;

import domain.abstractions.Entity;
import persistence.SingletonDatabaseConnection;

import java.sql.Connection;

public abstract class ConnectedDao<T extends Entity> implements Dao<T> {
	 protected final Connection connection = SingletonDatabaseConnection.getConnection();
}
