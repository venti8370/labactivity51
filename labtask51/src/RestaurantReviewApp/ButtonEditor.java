/**
 *
 * @author IO
 */

package RestaurantReviewApp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private int row;
    private JTable table;
    
    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;  // Save the reference to the JTable
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                // Get the row and ID
                row = table.getSelectedRow();
                int id = (int) table.getValueAt(row, 0); // Assuming the ID is in the first column

                // Remove the row from the table
                ((DefaultTableModel) table.getModel()).removeRow(row);

                // Remove from the database
                removeRowFromDatabase(id);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        label = (value == null) ? "Remove" : value.toString();
        button.setText(label);
        return button;
    }

    private void removeRowFromDatabase(int id) {
        String sql = "DELETE FROM reviewlist WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

