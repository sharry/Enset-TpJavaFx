package persistence.products;

import domain.products.Product;
import persistence.abstractions.ConnectedDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDao extends ConnectedDao<Product> {

	 @Override
	 public Product create(Product entity) {
		  try {
				var statement = connection.prepareStatement("INSERT INTO Products (name, price, quantity) VALUES (?, ?, ?)");
				statement.setString(1, entity.getName());
				statement.setDouble(2, entity.getPrice());
				statement.setInt(3, entity.getQuantity());
				statement.executeUpdate();
				var resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					 entity.setId(resultSet.getInt(1));
				}
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  }
		  return entity;
	 }

	 @Override
	 public List<Product> readAll() {
		  var products = new ArrayList<Product>();
		  try {
				var statement = connection.prepareStatement("SELECT * FROM Products");
				var resultSet = statement.executeQuery();
				while (resultSet.next()) {
					 products.add(Product.fromResultSet(resultSet));
				}
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  }
		  return products;
	 }

	 @Override
	 public Product update(Product entity) {
		  try {
				var statement = connection.prepareStatement("UPDATE Products SET name = ?, price = ?, quantity = ? WHERE id = ?");
				statement.setString(1, entity.getName());
				statement.setDouble(2, entity.getPrice());
				statement.setInt(3, entity.getQuantity());
				statement.setInt(4, entity.getId());
				statement.executeUpdate();
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  }
		  return entity;
	 }

	 @Override
	 public boolean delete(int id) {
		  try {
				var statement = connection.prepareStatement("DELETE FROM Products WHERE id = ?");
				statement.setInt(1, id);
				statement.executeUpdate();
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  }
		  return true;
	 }

	 public List<Product> readAllByKeyword(String keyword) {
		  var products = new ArrayList<Product>();
		  try {
				var statement =
						  connection.prepareStatement("SELECT * FROM Products WHERE LOWER(name) LIKE '%" + keyword.toLowerCase() + "%'");
				var resultSet = statement.executeQuery();
				while (resultSet.next()) {
					 products.add(Product.fromResultSet(resultSet));
				}
		  } catch (SQLException e) {
				throw new RuntimeException(e);
		  }
		  return products;
	 }

}
