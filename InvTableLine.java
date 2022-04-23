/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coursetest.data;
/**
 *
 * @author Habiba
 */
public class InvTableLine {
    private String item;
    private double price;
    private int count;
    private InvTableHeader header;

    public InvTableLine() {
    }

    public InvTableLine(String item, double price, int count, InvTableHeader header) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.header = header;
    }

    public InvTableHeader getHeader() {
        return header;
    }

    public void setHeader(InvTableHeader header) {
        this.header = header;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public double getLineTotal() {
        return price * count;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "item=" + item + ", price=" + price + ", count=" + count + ", header=" + header + '}';
    }

    
    
}
