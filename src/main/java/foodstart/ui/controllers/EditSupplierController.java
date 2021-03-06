package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.SupplierManager;
import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controls the UI for the edit suppliers screen
 */
public class EditSupplierController {
    /**
     * Input field for supplier name
     */
    @FXML
    private TextField nameTextField;
    /**
     * Input field for supplier address
     */
    @FXML
    private TextField addressTextField;
    /**
     * Input field for supplier website
     */
    @FXML
    private TextField websiteTextField;
    /**
     * Input field for supplier email
     */
    @FXML
    private TextField emailTextField;
    /**
     * Input field for supplier phone
     */
    @FXML
    private TextField phoneTextField;
    /**
     * Combo box for supplier phone type
     */
    @FXML
    private ComboBox<PhoneType> phoneTypeComboBox;
    /**
     * Label for if there is an error with the name input
     */
    @FXML
    private Label nameErrorLabel;
    /**
     * Label for if there is an error with the address input
     */
    @FXML
    private Label addressErrorLabel;
    /**
     * Label for if there is an error with the phone input
     */
    @FXML
    private Label phoneErrorLabel;
    /**
     * ID of supplier currently being edited
     */
    private int code;


    /**
     * Called when the controller is initialized
     */
    @FXML
    public void initialize() {
        this.phoneTypeComboBox.setItems(FXCollections.observableArrayList(PhoneType.values()));
        this.phoneTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("[\\d|\\-|+|(|)| ]*")) {
                phoneTextField.setText(newValue.replaceAll("[^\\d|\\-|+|(|)| ]", ""));
            }
        }));
    }

    /**
     * Called to set the supplier so the correct information can be displayed
     * @param supplier the supplier to edit
     */
    public void setSupplier(Supplier supplier) {
    	code = supplier.getId();
        nameTextField.setText(supplier.getSupplierName());
        addressTextField.setText(supplier.getAddress());
        websiteTextField.setText(supplier.getUrl());
        emailTextField.setText(supplier.getEmail());
        phoneTextField.setText(supplier.getPhoneNumber());
        phoneTypeComboBox.getSelectionModel().select(supplier.getPhoneType());
    }

    /**
     * Checks if the name text field is valid
     * @return if the name is valid or not
     */
    private Boolean isValidSupplierName() {
        if (nameTextField.getText().isEmpty()) {
            nameErrorLabel.setText("Name field cannot be empty");
            nameErrorLabel.setVisible(true);
            return false;
        } else {
            nameErrorLabel.setVisible(false);
            return true;
        }
    }

    /**
     * Checks if the address text field is valid
     * @return if the address is valid or not
     */
    private Boolean isValidAddressName() {
        if (addressTextField.getText().isEmpty()) {
            addressErrorLabel.setText("Address field cannot be empty");
            addressErrorLabel.setVisible(true);
            return false;
        } else {
            addressErrorLabel.setVisible(false);
            return true;
        }

    }

    /**
     * Checks if the phone number text field is valid
     * @return if the phone number is valid or not
     */
    private Boolean isValidPhoneNumber() {
        if (phoneTextField.getText().isEmpty()) {
            phoneErrorLabel.setText("Phone number field cannot be empty");
            phoneErrorLabel.setVisible(true);
            return false;
        } else {
            phoneErrorLabel.setVisible(false);
            return true;
        }
    }


    /**
     * Called when the popup is to be closed
     */
    public void onCancel() {
        Stage stage = (Stage) this.nameTextField.getScene().getWindow();
        stage.close();

    }

    /**
     * Called when the submit button is clicked
     * Replaces the existing supplier with a new supplier with the retrieved information
     */
    public void onSubmit() {
        boolean validAddress = isValidAddressName();
        boolean validPhone = isValidPhoneNumber();
        boolean validName = isValidSupplierName();
        if (validAddress && validPhone && validName) {
            SupplierManager supplierManager = Managers.getSupplierManager();
            supplierManager.removeSupplier(code);
            supplierManager.addSupplier(code, nameTextField.getText(), phoneTextField.getText(), phoneTypeComboBox.getValue(),
                    emailTextField.getText(), websiteTextField.getText(), addressTextField.getText());
            this.onCancel();
        }

    }
}
