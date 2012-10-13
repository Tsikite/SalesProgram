/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.ui;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tsikon
 */
public class UnEditableTableModel extends DefaultTableModel {

    public UnEditableTableModel() {
        super();
    }
     
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
}
