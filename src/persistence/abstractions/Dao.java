package persistence.abstractions;

import domain.abstractions.Entity;

import java.util.List;

public interface Dao<T extends Entity> {
	 T create(T entity);
	 List<T> readAll();
	 T update(T entity);
	 boolean delete(int id);
}
