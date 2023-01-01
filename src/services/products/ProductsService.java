package services.products;

import domain.products.Product;
import persistence.abstractions.Dao;
import persistence.products.ProductsDao;
import services.abstractions.Service;

import java.util.List;

public class ProductsService extends Service<Product> {
	 @Override
	 protected Dao<Product> setDao() {
		  return new ProductsDao();
	 }

	 public List<Product> getProducts() {
		  return dao.readAll();
	 }

	 public List<Product> getByKeyword(String keyword) {
		  return ((ProductsDao) dao).readAllByKeyword(keyword);
	 }

	 public void addProduct(Product product) {
		  dao.create(product);
	 }

	 public void updateProduct(Product product) {
		  dao.update(product);
	 }

	 public void deleteProduct(Product product) {
		  dao.delete(product.getId());
	 }
}
