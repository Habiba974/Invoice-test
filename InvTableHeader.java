/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursetest.data;

import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Habiba
 */
public class InvTableHeader {
    private int num;
    private String customer;
    private Date invDate;
    private ArrayList<InvTableLine> lines;

    public InvTableHeader() {
    }

    public InvTableHeader(int num, String customer, Date invDate) {
        this.num = num;
        this.customer = customer;
        this.invDate = invDate;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<InvTableLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<InvTableLine> lines) {
        this.lines = lines;
    }
    
    public double getInvoiceTotal() {
        double total = 0.0;
        
        for (int x = 0; x < getLines().size(); x++) {
            total += getLines().get(x).getLineTotal();
        }
        
        return total;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num=" + num + ", customer=" + customer + ", invDate=" + invDate + '}';
    }
    
}
