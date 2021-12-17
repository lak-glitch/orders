package controller;

import alert.FormatCurrency;
import alert.Notice;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import customers.Admin;
import databaseconnection.GetConnection;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditFoodController implements Initializable {
    public TextField productName;
    public JFXButton addButton;
    public TextField priceEach;
    public JFXRadioButton deleteProduct;
    public JFXRadioButton deleteUser;
    public TextField editPriceLabel;
    public TextField deleteProductLabel;
    public JFXButton deleteButton;
    public JFXSnackbar snackbar;
    public Label wrongFormat;
    public Label wrongNumberFormat;
    Connection con = null;
    PreparedStatement stm = null;
    PreparedStatement checkFoodExist = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productName.setText("");
        priceEach.setText("");
        editPriceLabel.setText("");
        deleteProductLabel.setText("");
        deleteProductLabel.setDisable(true);
        deleteButton.setDisable(true);
        addButton.setDisable(true);
        setRadioButton();
        productName.setOnKeyTyped(keyEvent -> {
            try {
                addButton.setDisable(productName.getText().equals(""));
            } catch (InputMismatchException e) {
                System.out.println("fuck");
            }
        });
        editPriceLabel.setDisable(true);
        priceEach.setOnKeyTyped(keyEvent -> {
            try {
                wrongFormat.setVisible(false);
                int price = Integer.parseInt(priceEach.getText());
            } catch (NumberFormatException e) {
                wrongFormat.setVisible(true);
                wrongFormat.setText("Input number!");
            }
        });
        editPriceLabel.setOnKeyTyped(keyEvent -> {
            try {

                wrongNumberFormat.setVisible(false);
                int price = Integer.parseInt(editPriceLabel.getText());
            } catch (NumberFormatException e) {
                wrongNumberFormat.setVisible(true);
                wrongNumberFormat.setText("Input number!");
            }
        });
    }

    public void addProductToDatabase() {
        if (!productName.getText().equals("") || !priceEach.getText().equals("")) {
            Alert alert = Notice.alertConfirmation("Warning", "/icons/checked_32px.png");
            alert.setContentText("Do you want to add this food to product list?");
            Optional<ButtonType> ot = alert.showAndWait();
            if (ot.get() == ButtonType.OK) {
                try {
                    queryDatabase(productName.getText(), priceEach.getText());
                    Admin.refresh();
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setRadioButton() {
        deleteProduct.setOnAction(event -> {
            if (deleteProduct.isSelected()) {
                deleteProductLabel.setDisable(false);
                deleteProductLabel.setText("");
                editPriceLabel.setText("");
                deleteButton.setDisable(false);
                deleteUser.setSelected(false);
                editPriceLabel.setDisable(true);
                deleteButton.setText("Delete");
                try {
                    bindingProductName();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("true");
                deleteButton.setOnAction(event1 -> {
                    try {
                        Alert alert = Notice.alertConfirmation("Warning!", "/icons/warning_shield_24px.png");
                        alert.setContentText("Do you want to delete this food?");
                        Optional<ButtonType> ot = alert.showAndWait();
                        if (ot.get() == ButtonType.OK) {
                            deleteFood();
                            Admin.refresh();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                deleteProductLabel.setDisable(true);
                deleteProductLabel.setText("");
                editPriceLabel.setText("");
                deleteButton.setDisable(true);
            }
        });
        deleteUser.setOnAction(event -> {
            if (deleteUser.isSelected()) {
                deleteProductLabel.setText("");
                editPriceLabel.setText("");
                deleteButton.setDisable(false);
                deleteProductLabel.setDisable(false);
                deleteProduct.setSelected(false);
                editPriceLabel.setDisable(false);
                deleteButton.setText("Edit");
                deleteButton.setDisable(false);
                deleteButton.setOnAction(event12 -> {
                    Alert alert;
                    alert = Notice.alertConfirmation("Notice!", "/icons/warning_shield_24px.png");
                    alert.setContentText("Do you want to edit this food's price?");
                    Optional<ButtonType> ot = alert.showAndWait();
                    if (ot.get() == ButtonType.OK) {
                        if (editPriceLabel.getText().equals("")) {
                            alert = Notice.alertWarning("Warning!", "/icons/warning_shield_24px.png", "Input the price!");
                            alert.showAndWait();
                            return;
                        }
                        if (!Admin.productList.contains(deleteProductLabel.getText())) {
                            alert = Notice.alertWarning("Warning!", "/icons/warning_shield_24px.png", "No food founded!");
                            alert.showAndWait();
                            return;
                        }
                        try {
                            editPrice();
                            Admin.refresh();
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        alert = Notice.alertConfirmation("Successfully", "/icons/checked_32px.png");
                        alert.setContentText("Edit successfully");
                        alert.showAndWait();
                    }
                });
            } else {
                deleteProductLabel.setText("");
                editPriceLabel.setText("");
                editPriceLabel.setDisable(true);
                deleteProductLabel.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
    }

    // check if food can be added to product
    public void queryDatabase(String productName, String price) throws SQLException, ClassNotFoundException, IOException {
        con = GetConnection.getConnection();
        String sql = "SELECT productName FROM ordersmanagement.products WHERE productName = ?";
        checkFoodExist = con.prepareStatement(sql);
        checkFoodExist.setString(1, productName);
        rs = checkFoodExist.executeQuery();
        Alert alert;
        if (rs.isBeforeFirst()) {
            alert = Notice.alertWarning("Warning!", "/icons/warning_shield_24px.png", "This food is already existed!");
        } else {
            String sql1 = "INSERT INTO ordersmanagement.products(productName, productCost) VALUES (?,?)";
            stm = con.prepareStatement(sql1);
            stm.setString(1, productName);
            stm.setString(2, price);
            stm.executeUpdate();
            alert = Notice.alertWarning("Notice!", "/icons/checked_32px.png", "Successfully!");
        }
        alert.showAndWait();
        this.productName.setText("");
        this.priceEach.setText("");
        rs.close();
    }

    public void bindingProductName() throws SQLException, ClassNotFoundException {
        TextFields.bindAutoCompletion(deleteProductLabel, Admin.productList);
        deleteProductLabel.setOnKeyTyped(keyEvent -> {
            deleteButton.setDisable(deleteProductLabel.getText().equals(""));
        });
    }

    // delete Food or Customer
    public void deleteFood() throws SQLException, ClassNotFoundException {

        Alert alert;
        // TODO Check this function
        String sql = "SELECT productName FROM ordersmanagement.products WHERE productName = ?";
        con = GetConnection.getConnection();
        stm = con.prepareStatement(sql);
        stm.setString(1, deleteProductLabel.getText());
        rs = stm.executeQuery();
        if (rs.isBeforeFirst()) {
            sql = "DELETE FROM ordersmanagement.products WHERE productName = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, deleteProductLabel.getText());
            stm.executeUpdate();
            alert = Notice.alertWarning("Successfully", "/icons/checked_32px.png", "Successfully");
        } else {
            alert = Notice.alertWarning("Warning!", "/icons/warning_shield_24px.png", "This food does not exist! Try again.");
        }
        alert.showAndWait();
    }

    public void editPrice() throws SQLException, ClassNotFoundException {
        Alert alert;

        String sql = "UPDATE ordersmanagement.products SET productCost = ? WHERE productName = ?";
        con = GetConnection.getConnection();
        stm = con.prepareStatement(sql);
        stm.setInt(1, Integer.parseInt(editPriceLabel.getText()));
        stm.setString(2, deleteProductLabel.getText());
        stm.executeUpdate();
    }
}
