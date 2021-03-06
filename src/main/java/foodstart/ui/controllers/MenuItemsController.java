package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import foodstart.ui.Refreshable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls the UI for the manage menu items screen
 */
public class MenuItemsController implements Refreshable {
    /**
     * The flow pane to which the menu items are added
     */
    @FXML
    private FlowPane flowPane;

    /**
     * FXML loader for popup screen
     */
    private FXMLLoader loader;
    /**
     * Stage for the popup screen
     */
    private Stage popupStage;
    /**
     * Scene for the popup stage
     */
    private Scene scene;
    /**
     * Box background for menu items in the grid
     */
    private Background boxBackground;

    /**
     * The edit loader of the popup screen
     */

    private FXMLLoader editLoader;

    /**
     * The FXML for the edit recipe popup screen
     */
    private Parent editFXML;

    /**
     * The stage of the edit popup screen
     */
    private Stage editPopup;

    /**
     * Initialises the MenuItemsController
     */
    public void initialize() {
        try {
            editLoader = new FXMLLoader(getClass().getResource("menuItemEditor.fxml"));
            editFXML = editLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Screen screen = Screen.getPrimary();
        editPopup = new Stage();
        editPopup.setResizable(false);
        editPopup.initModality(Modality.WINDOW_MODAL);
        editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() /4, screen.getVisualBounds().getHeight() / 1.75));
        boxBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        populateMenuItems(flowPane);
    }

    /**
     * Populates the flow pane with menu items
     * @param flowPane the flow pane to add the menu items to
     */
    public void populateMenuItems(FlowPane flowPane) {
        flowPane.getChildren().clear();
        for (MenuItem item : Managers.getMenuItemManager().getMenuItemSet()) {
            flowPane.getChildren().add(createMenuItemBox(item));
        }
    }

    /**
     * Creates a menu item node for the flow pane
     * @param item the menu item to create the node for
     * @return the node that can be added to a flow pane
     */
    private Node createMenuItemBox(MenuItem item) {
        VBox box = new VBox();
        box.setPrefSize(150, 150);
        box.setPadding(new Insets(5));
        box.setBackground(boxBackground);
        box.setAlignment(Pos.CENTER);
        box.setCursor(Cursor.HAND);
        box.setOnMouseClicked((event) -> {
            editMenuItem(item);
        });
        FlowPane.setMargin(box, new Insets(5));
        box.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

        Text menuItemName = new Text(item.getName());
        menuItemName.setTextAlignment(TextAlignment.CENTER);

        String priceString = item.getVariants().size() > 0
                ? String.format("$%.02f", Managers.getMenuItemManager().getApproxPrice(item.getId()))
                : "No Recipes";
        Text itemPrice = new Text(priceString);

        box.getChildren().add(menuItemName);
        box.getChildren().add(itemPrice);

        return box;
    }

    /**
     * Calls add recipe popup
     */
    @FXML void addMenuItem() {
        if (editPopup.getOwner() == null) {
            editPopup.initOwner(this.flowPane.getScene().getWindow());
        }
        ((MenuItemEditorController) editLoader.getController()).clearFields();
        editPopup.showAndWait();
        populateMenuItems(flowPane);
    }

    public void editMenuItem(MenuItem menuItem) {
        if (editPopup.getOwner() == null) {
            editPopup.initOwner(this.flowPane.getScene().getWindow());
        }
        ((MenuItemEditorController) editLoader.getController()).setFields(menuItem);
        editPopup.setHeight(600);
        editPopup.setWidth(800);
        editPopup.showAndWait();
        populateMenuItems(flowPane);

    }

    /**
     * Refreshes the flowpane with menu items
     */
    public void refreshTable() {
        populateMenuItems(flowPane);
    }
}
