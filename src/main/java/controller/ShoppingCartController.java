package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

public class ShoppingCartController {
    @FXML private ComboBox<String> languageBox;
    @FXML private TextField numItemsField;
    @FXML private VBox itemBox;
    @FXML private Label totalLabel;
    @FXML private Button calcButton;
    private Locale currentLocale = new Locale("en", "GB");
    private ResourceBundle bundle;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void initialize() {
        languageBox.setItems(FXCollections.observableArrayList("English", "Suomi", "Svenska", "日本語"));
        languageBox.setValue("English");
        bundle = ResourceBundle.getBundle("MessageBundle", currentLocale);
        updateTexts();
    }

    @FXML
    void onLanguageChange(ActionEvent event) {
        switch (languageBox.getValue()) {
            case "Suomi" -> currentLocale = new Locale("fi", "FI");
            case "Svenska" -> currentLocale = new Locale("sv", "SE");
            case "日本語" -> currentLocale = new Locale("ja", "JP");
            default -> currentLocale = new Locale("en", "GB");
        }
        bundle = ResourceBundle.getBundle("MessageBundle", currentLocale);
        updateTexts();
    }

    @FXML
    void onCalculate(ActionEvent event) {
        itemBox.getChildren().clear();
        int numItems;
        try {
            numItems = Integer.parseInt(numItemsField.getText());
        } catch (NumberFormatException e) {
            numItems = 0;
        }
        double total = 0;
        List<Double> itemTotals = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            TextInputDialog priceDialog = new TextInputDialog();
            priceDialog.setHeaderText(bundle.getString("enter.price") + " #" + (i + 1));
            double price = Double.parseDouble(priceDialog.showAndWait().orElse("0"));
            TextInputDialog quantityDialog = new TextInputDialog();
            quantityDialog.setHeaderText(bundle.getString("enter.quantity") + " #" + (i + 1));
            int quantity = Integer.parseInt(quantityDialog.showAndWait().orElse("0"));

            double itemTotal = calculateItemTotal(price, quantity);
            itemTotals.add(itemTotal);
            Label itemLabel = new Label(bundle.getString("item.total") + " " + (i + 1) + ": " + itemTotal);
            itemBox.getChildren().add(itemLabel);
        }
        total = calculateCartTotal(itemTotals);
        totalLabel.setText(bundle.getString("total.cost") + " " + String.format("%.2f", total));
    }


    private void updateTexts() {
        numItemsField.setPromptText(bundle.getString("enter.items"));
        calcButton.setText(bundle.getString("calculate"));
        totalLabel.setText(bundle.getString("total.cost"));
        if (stage != null) {
            stage.setTitle(bundle.getString("app.title"));
        }
    }
    public static double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }
    public static double calculateCartTotal(List<Double> totals) {
        return totals.stream().mapToDouble(Double::doubleValue).sum();
    }
}
