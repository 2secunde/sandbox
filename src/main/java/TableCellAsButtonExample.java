import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TableCellAsButtonExample extends JFrame {

    private JTable table;
    private JButtonCellRenderer buttonRenderer;
    private JButtonCellEditor buttonEditor;

    public TableCellAsButtonExample() {
        setTitle("JTable Cell as Button Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        IconFontSwing.register(FontAwesome.getIconFont());

        Icon icon = IconFontSwing.buildIcon(FontAwesome.SMILE_O, 256, Color.WHITE);

        JButton fontButton = new JButton(icon);

        fontButton.setBackground(Color.BLACK);
        fontButton.addActionListener(e-> System.out.println("Awesome Button clicked!"));

        add(fontButton, BorderLayout.NORTH);

        // Sample data
        Object[][] data = {
                {"Button 1"},
                {"Button 2"},
                {"Button 3"}
        };

        // Column headers
        String[] columns = {"Button"};

        // Create the table with data and headers
        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        // Set up the button renderer
        buttonRenderer = new JButtonCellRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer(buttonRenderer);

        // Set up the button editor
        buttonEditor = new JButtonCellEditor();
        table.getColumnModel().getColumn(0).setCellEditor(buttonEditor);

        // Add the table to a scroll pane and to the frame
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    // Custom cell renderer for rendering JButton
    class JButtonCellRenderer extends JButton implements TableCellRenderer {
        public JButtonCellRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Set button text
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom cell editor for editing JButton
    class JButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;

        public JButtonCellEditor() {
            button = new JButton();
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Handle button click action here
                    System.out.println("Button clicked!");
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // Set button text
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            return button.getText();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TableCellAsButtonExample().setVisible(true);
            }
        });
    }
}
