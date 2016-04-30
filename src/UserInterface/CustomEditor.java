/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Administrator
 */
public class CustomEditor implements TableCellEditor {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public CustomEditor() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if(null != value)
            textArea.setText(value.toString());

        return scrollPane;
    }

    @Override
    public void addCellEditorListener(CellEditorListener arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void cancelCellEditing() {
        // TODO Auto-generated method stub

    }
    @Override
    public Object getCellEditorValue() {
        // TODO Auto-generated method stub
        return textArea.getText();
    }
    @Override
    public boolean isCellEditable(EventObject arg0) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public void removeCellEditorListener(CellEditorListener arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public boolean shouldSelectCell(EventObject arg0) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean stopCellEditing() {
        // TODO Auto-generated method stub
        return true;
    }
}
