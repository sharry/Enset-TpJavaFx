package domain.products;

import domain.abstractions.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class Product extends Entity {

	 @Getter @Setter private String name;
	 @Getter @Setter private double price;
	 @Getter @Setter private int quantity;

	 public static Product fromResultSet(ResultSet resultSet) throws SQLException {
		  var product = new Product();
		  product.setId(resultSet.getInt("id"));
		  product.setName(resultSet.getString("name"));
		  product.setPrice(resultSet.getDouble("price"));
		  product.setQuantity(resultSet.getInt("quantity"));
		  return product;
	 }

}
