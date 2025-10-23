import java.util.*;

public class ShoppingCart {
    public static double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }
    public static double calculateCartTotal(List<Double> itemTotals) {
        return itemTotals.stream().mapToDouble(Double::doubleValue).sum();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Select language / Valitse kieli / Välj språk / 日本語");
        System.out.println("1. English");
        System.out.println("2. Suomi (Finnish)");
        System.out.println("3. Svenska (Swedish)");
        System.out.println("4. 日本語 (Japanese)");
        System.out.print(">");

        int decision = sc.nextInt();
        sc.nextLine();
        Locale locale;
        switch (decision) {
            case 2 -> locale = new Locale("fi", "FI");
            case 3 -> locale = new Locale("sv", "SE");
            case 4 -> locale = new Locale("ja", "JP");
            default -> locale = new Locale("en", "GB");
        }

        ResourceBundle rb = ResourceBundle.getBundle("MessageBundle", locale);
        System.out.print(rb.getString("enter.items"));
        int numItems = sc.nextInt();

        List<Double> itemTotals = new ArrayList<>();
        for (int i = 1; i <= numItems; i++) {
            System.out.print(rb.getString("enter.price") + " ");
            double price = sc.nextDouble();

            System.out.print(rb.getString("enter.quantity") + " ");
            int quantity = sc.nextInt();

            itemTotals.add(calculateItemTotal(price, quantity));
        }
        double totalPrice = calculateCartTotal(itemTotals);
        System.out.println(rb.getString("total.cost") + " " + String.format("%.2f", totalPrice));
        sc.close();
    }
}
