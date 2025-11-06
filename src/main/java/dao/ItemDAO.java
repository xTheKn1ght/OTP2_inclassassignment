package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private final Connection conn;
    public ItemDAO(Connection conn) {
        this.conn = conn;
    }
    public void addItem(double price, int quantity, String lang, String name) throws SQLException {
        String insertItem = "INSERT INTO items (price, quantity) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertItem, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, price);
            stmt.setInt(2, quantity);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int itemId = rs.getInt(1);
                    String insertTranslation = "INSERT INTO item_translations (item_id, lang, name) VALUES (?, ?, ?)";
                    try (PreparedStatement transStmt = conn.prepareStatement(insertTranslation)) {
                        transStmt.setInt(1, itemId);
                        transStmt.setString(2, lang);
                        transStmt.setString(3, name);
                        transStmt.executeUpdate();
                    }
                }
            }
        }
    }
    public List<String> getItemsByLanguage(String lang) throws SQLException {
        List<String> items = new ArrayList<>();
        String query = "SELECT name FROM item_translations WHERE lang = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lang);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(rs.getString("name"));
                }
            }
        }
        return items;
    }
}
