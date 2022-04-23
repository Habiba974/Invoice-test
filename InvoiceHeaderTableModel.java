/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursetest.data;

import coursetest.Interface.interFace;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Habiba
 */
public class InvoiceHeaderTableModel extends AbstractTableModel {

    private ArrayList<InvTableHeader> invoicesArray;
    private String[] columns = {"Invoice Num", "Invoice Date", "Customer Name", "Invoice Total"};
    
    public InvoiceHeaderTableModel(ArrayList<InvTableHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvTableHeader inv = invoicesArray.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getNum();
            case 1: return interFace.dateF.format(inv.getInvDate());
            case 2: return inv.getCustomer();
            case 3: return inv.getInvoiceTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
