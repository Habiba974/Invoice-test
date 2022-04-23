/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursetest.controller;

import coursetest.data.InvTableHeader;
import coursetest.data.InvoiceHeaderTableModel;
import coursetest.data.InvTableLine;
import coursetest.data.InvoiceLineTableModel;
import coursetest.Interface.interFace;
import coursetest.Interface.HeaderDlog;
import coursetest.Interface.LineDlog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Habiba
 */
public class AllActionListener implements ActionListener {

    private interFace view;
    private HeaderDlog headerDialog;
    private LineDlog lineDialog;

    public AllActionListener(interFace view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            

            case "Load Files":
                loadFiles();
                break;

            case "New Invoice":
                createNewInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "New Line":
                createNewLine();
                break;

            case "Delete Line":
                deleteLine();
                break;

            case "newInvoiceOK":
                newInvoiceDialogOK();
                break;

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineOK":
                newLineDialogOK();
        
               break;
       
            case "Save Files":
                saveFiles();
                break;
        
        }
    }

    
    
    private void loadFiles() {
        JFileChooser fileChooser = new JFileChooser();
        try {
            int result = fileChooser.showOpenDialog(view);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                Path hPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(hPath);
                ArrayList<InvTableHeader> invoiceHeaders = new ArrayList<>();
                for (String invheader : headerLines) {
                    String[] arr = invheader.split(",");
                    String str1 = arr[0];
                    String invda = arr[1];
                    String coustname = arr[2];
                    int tranc1 = Integer.parseInt(str1);
                    Date invoiceDate = interFace.dateF.parse(invda);
                    InvTableHeader header = new InvTableHeader(tranc1, coustname, invoiceDate);
                    invoiceHeaders.add(header);
                }
                
                
                view.setInvoicesArray(invoiceHeaders);

                result = fileChooser.showOpenDialog(view);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fileChooser.getSelectedFile();
                    Path lPath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(lPath);
                    ArrayList<InvTableLine> invoiceLines = new ArrayList<>();
                    for (String invline : lineLines) {
                        String[] arr = invline.split(",");
                        String num = arr[0];    
                        String iname = arr[1];    
                        String invprice = arr[2];   
                        String invcount = arr[3];    
                        int trans2 = Integer.parseInt(num);
                        double price = Double.parseDouble(invprice);
                        int count = Integer.parseInt(invcount);
                        InvTableHeader inv = view.getInvObject(trans2);
                        InvTableLine line = new InvTableLine(iname, price, count, inv);
                        inv.getLines().add(line);
                    }
                    
                    
                    System.out.println("**** READING FILES START ****");
    System.out.println("***&& HEADER FILE &&***");
    for (String invheader : headerLines){
        System.out.println(invheader);
        }  
    System.out.println("***&& LINE FILE &&***");  
    for (String invline: lineLines){
        System.out.println(invline);
    }  
    System.out.println("**** READING FILES END ****");
                }
                InvoiceHeaderTableModel headerTableModel = new InvoiceHeaderTableModel(invoiceHeaders);
                view.setHeaderTableModel(headerTableModel);
                view.getInvHTbl().setModel(headerTableModel);
               
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
   

    
    }

    private void createNewInvoice() {
        headerDialog = new HeaderDlog(view);
        headerDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedInvoiceIndex = view.getInvHTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            view.getInvoicesArray().remove(selectedInvoiceIndex);
            view.getHeaderTableModel().fireTableDataChanged();

            view.getInvLTbl().setModel(new InvoiceLineTableModel(null));
            view.setLinesArray(null);
            view.getCustNameLbl().setText("");
            view.getInvNumLbl().setText("");
            view.getInvTotalIbl().setText("");
            view.getInvDateLbl().setText("");
        }
    }

    private void createNewLine() {
        lineDialog = new LineDlog(view);
        lineDialog.setVisible(true);
    }

    private void deleteLine() {
        int selectedLineIndex = view.getInvLTbl().getSelectedRow();
        int selectedInvoiceIndex = view.getInvHTbl().getSelectedRow();
        if (selectedLineIndex != -1) {
            view.getLinesArray().remove(selectedLineIndex);
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) view.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            view.getInvTotalIbl().setText(""+view.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            view.getHeaderTableModel().fireTableDataChanged();
            view.getInvHTbl().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

   

    private void newInvoiceDialogOK() {
        headerDialog.setVisible(false);

        String custName = headerDialog.getCustNameField().getText();
        String str = headerDialog.getInvDateField().getText();
        Date d = new Date();
        try {
            d = interFace.dateF.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(view, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (InvTableHeader inv : view.getInvoicesArray()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        InvTableHeader newInv = new InvTableHeader(invNum, custName, d);
        view.getInvoicesArray().add(newInv);
        view.getHeaderTableModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);
        
        String name = lineDialog.getItemNameField().getText();
        String str1 = lineDialog.getItemCountField().getText();
        String str2 = lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            price = Double.parseDouble(str2);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = view.getInvHTbl().getSelectedRow();
        if (selectedInvHeader != -1) {
            InvTableHeader invHeader = view.getInvoicesArray().get(selectedInvHeader);
            InvTableLine line = new InvTableLine(name, price, count, invHeader);
            //invHeader.getLines().add(line);
            view.getLinesArray().add(line);
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) view.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            view.getHeaderTableModel().fireTableDataChanged();
        }
        view.getInvHTbl().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

    
    
     private void saveFiles() {
    }

    private void newInvoiceDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }
}
