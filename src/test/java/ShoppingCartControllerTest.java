import controller.ShoppingCartController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ShoppingCartControllerTest {
    @Test
    void calculateItemTotal() {
        assertEquals(20.0, ShoppingCartController.calculateItemTotal(2.0, 10));
        assertEquals(0.0, ShoppingCartController.calculateItemTotal(0.0,3));
        assertEquals(12.5, ShoppingCartController.calculateItemTotal(2.5, 5));
    }
    @Test
    void calculateCartTotal() {
        List<Double> items = Arrays.asList(20.0, 10.0, 3.5);
        assertEquals(33.5, ShoppingCartController.calculateCartTotal(items));
        assertEquals(0.0, ShoppingCartController.calculateCartTotal(Collections.emptyList()));
    }
}