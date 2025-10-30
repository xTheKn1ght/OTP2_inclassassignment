import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShoppingCartApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Locale locale = new Locale("en", "GB");
        ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/shoppingcart.fxml"), bundle);
        Scene scene = new Scene(loader.load(), 500, 400); // starting size, you can adjust
        stage.setScene(scene);
        controller.ShoppingCartController controller = loader.getController();
        controller.setStage(stage);
        stage.setTitle(bundle.getString("app.title"));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
