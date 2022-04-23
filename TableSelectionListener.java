/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursetest.controller;

import coursetest.data.InvTableHeader;
import coursetest.data.InvTableLine;
import coursetest.data.InvoiceLineTableModel;
import coursetest.Interface.interFace;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Habiba
 */
public class TableSelectionListener implements ListSelectionListener {

    private interFace view;

    public TableSelectionListener(interFace view) {
        this.view = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = view.getInvHTbl().getSelectedRow();
        System.out.println("Invoice selected: " + selectedInvIndex);
        if (selectedInvIndex != -1) {
            InvTableHeader selectedInv = view.getInvoicesArray().get(selectedInvIndex);
            ArrayList<InvTableLine> lines = selectedInv.getLines();
            InvoiceLineTableModel lineTableModel = new InvoiceLineTableModel(lines);
            view.setLinesArray(lines);
            view.getInvLTbl().setModel(lineTableModel);
            view.getCustNameLbl().setText(selectedInv.getCustomer());
            view.getInvNumLbl().setText("" + selectedInv.getNum());
            view.getInvTotalIbl().setText("" + selectedInv.getInvoiceTotal());
            view.getInvDateLbl().setText(interFace.dateF.format(selectedInv.getInvDate()));
        }
    }

}
