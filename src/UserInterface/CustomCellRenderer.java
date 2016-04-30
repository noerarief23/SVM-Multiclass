/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Administrator
 */
public class CustomCellRenderer extends DefaultTableCellRenderer{
    
        private JTextArea textArea;
        private JScrollPane scrollPane;

        public CustomCellRenderer() {
            textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setFont(new java.awt.Font("Verdana", 0, 12));
            textArea.setEditable(false);
            scrollPane = new JScrollPane(textArea);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            if(null != value)
                textArea.setText(value.toString());

            return scrollPane;
        }
}
