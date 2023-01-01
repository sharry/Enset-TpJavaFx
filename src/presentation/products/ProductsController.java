package presentation.products;

import domain.products.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import services.products.ProductsService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {
	 private final ProductsService service = new ProductsService();
	 public TextField txtName;
	 public TextField txtPrice;
	 public TextField txtQuantity;
	 public TextField txtSearch;
	 public Button btnAdd;
	 public Button btnEdit;
	 public Button btnDelete;
	 public Button btnClearSearchBar;
	 public TableView<Product> tblProducts;
	 public TableColumn<Product, Integer> colId;
	 public TableColumn<Product, String> colName;
	 public TableColumn<Product, Integer> colQuantity;
	 public TableColumn<Product, Double> colPrice;
	 private final ObservableList<Product> products =
				FXCollections.observableArrayList(service.getProducts());

	 @Override
	 public void initialize(URL url, ResourceBundle resourceBundle) {
		  btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, this::addProduct);
		  btnEdit.addEventHandler(MouseEvent.MOUSE_CLICKED, this::editProduct);
		  btnDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, this::deleteProduct);
		  btnClearSearchBar.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clearSearchBar);
		  tblProducts.setItems(products);
		  colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		  colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		  colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		  colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		  loadProducts();
		  txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.isEmpty()) {
					 loadProducts();
				} else {
					products.clear();
					 products.setAll(service.getByKeyword(newValue));
				}
		  });
		  tblProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> editProductFromTable(newValue));
		  tblProducts.setEditable(true);
		  colName.setCellFactory(TextFieldTableCell.forTableColumn());
		  colPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		  colQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	 }

	 private void editProductFromTable(Product product) {
		  if (product != null) {
				txtName.setText(product.getName());
				txtPrice.setText(String.valueOf(product.getPrice()));
				txtQuantity.setText(String.valueOf(product.getQuantity()));
		  }
	 }

	 private void loadProducts() {
		  products.clear();
		  products.addAll(service.getProducts());
	 }

	 private void clearSearchBar(MouseEvent event) {
		  txtSearch.clear();
	 }

	 private void addProduct(MouseEvent event) {
		  if (fieldsEmpty()) {
				return;
		  }
		  var name = txtName.getText();
		  var price = Double.parseDouble(txtPrice.getText());
		  var quantity = Integer.parseInt(txtQuantity.getText());
		  var product = new Product();
		  product.setName(name);
		  product.setPrice(price);
		  product.setQuantity(quantity);
		  service.addProduct(product);
		  loadProducts();
	 }

	 private void editProduct(MouseEvent event) {
		  if (fieldsEmpty()) {
				return;
		  }
		  var product = tblProducts.getSelectionModel().getSelectedItem();
		  product.setName(txtName.getText());
		  product.setPrice(Double.parseDouble(txtPrice.getText()));
		  product.setQuantity(Integer.parseInt(txtQuantity.getText()));
		  service.updateProduct(product);
		  loadProducts();
	 }

	 private void deleteProduct(MouseEvent event) {
		  var product = tblProducts.getSelectionModel().getSelectedItem();
		  service.deleteProduct(product);
		  loadProducts();

	 }

	 private boolean fieldsEmpty() {
		  return txtName.getText().isEmpty()
					 || txtPrice.getText().isEmpty()
					 || txtQuantity.getText().isEmpty();
	 }

	 public void updateNameFromTable(TableColumn.CellEditEvent<Product, String> productStringCellEditEvent) {
		  var product = tblProducts.getSelectionModel().getSelectedItem();
		  product.setName(productStringCellEditEvent.getNewValue());
		  service.updateProduct(product);
		  loadProducts();
	 }

	 public void updatePriceFromTable(TableColumn.CellEditEvent<Product, Double> productDoubleCellEditEvent) {
		  var product = tblProducts.getSelectionModel().getSelectedItem();
		  product.setPrice(productDoubleCellEditEvent.getNewValue());
		  service.updateProduct(product);
		  loadProducts();
	 }

	 public void updateQuantityFromTable(TableColumn.CellEditEvent<Product, Integer> productIntegerCellEditEvent) {
		  var product = tblProducts.getSelectionModel().getSelectedItem();
		  product.setQuantity(productIntegerCellEditEvent.getNewValue());
		  service.updateProduct(product);
		  loadProducts();
	 }
}
