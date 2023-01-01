package services.abstractions;

import domain.abstractions.Entity;
import persistence.abstractions.Dao;

public abstract class Service<T extends Entity> {
	 protected final Dao<T> dao = setDao();

	 protected abstract Dao<T> setDao();
}
