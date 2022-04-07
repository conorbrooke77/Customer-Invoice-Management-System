package com.GUI;

import java.sql.SQLException;
import java.util.EventObject;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;

import com.Database.DatabaseConnector;

public class ManageMenu extends JPanel {

    private JTable table;
    private JLabel tableName;
    private String[][] data;
    private String header;
    private DefaultTableModel model;

    public ManageMenu(String headerName) {

        header = headerName;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(80, 60, 50, 60));
        setBackground(Color.WHITE);
        addHeader();
        addTable();
    }

    public void addHeader() {

        tableName = new JLabel("Manage " + header + "s");
        tableName.setFont(new Font("Arial", Font.BOLD, 32));
        tableName.setBorder(new EmptyBorder(0, 0, 80, 0));
        tableName.setForeground(new Color(82, 82, 82));

        add(tableName, BorderLayout.NORTH);
    }

    public void addTable() {
        String[] productColumns = {"prod_Id","prod_Name","prod_Price","prod_Description","prod_Brand","prod_Quantity","prod_Rating","prod_Weight", "Delete", "Update"};
        String[] invoiceColumns = {"invoiceId","customer_Name","noOfProducts","total","Delete", "Update"};
        String[] customerColumns = {"customerID","firstName","lastName","email","password","phoneNumber", "Delete", "Update"};
        String[] systemUsersColumns = {"idAdmin","firstName","lastName","email","phoneNumber","password", "Delete", "Update"};

        try {
            DatabaseConnector.createConnection();
            if (header=="Product") {
                data = DatabaseConnector.retrieveProduct();
                model = new DefaultTableModel(data, productColumns);
            } else if (header=="Customer") {
                data = DatabaseConnector.retrieveCustomers();
                model = new DefaultTableModel(data, customerColumns);
            } else if (header=="System User") {
                data = DatabaseConnector.retrieveAdmins();
                model = new DefaultTableModel(data, systemUsersColumns);
            } else  {
                data = DatabaseConnector.retrieveInvoice();
                model = new DefaultTableModel(data, invoiceColumns);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        table = new JTable();
        table.setModel(model);
        table.getColumn("Delete").setCellRenderer(new MyRendererAndEditor(table, "Delete", header, new Color(245, 22, 37)));
        table.getColumn("Delete").setCellEditor(new MyRendererAndEditor(table, "Delete", header, new Color(245, 22, 37)));
        table.getColumn("Update").setCellRenderer(new MyRendererAndEditor(table, "Update", header, new Color(18, 255, 73)));
        table.getColumn("Update").setCellEditor(new MyRendererAndEditor(table, "Update", header, new Color(18, 255, 73)));
        table.setRowHeight(40);

        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        JScrollPane scrollPane = new JScrollPane(table);

        
        add(scrollPane, BorderLayout.CENTER);
    
    }
}

class MyRendererAndEditor implements TableCellRenderer, TableCellEditor 
{
  private JButton btn;
  private int row;
  public MyRendererAndEditor(JTable table, String type, String header, Color color) {
    btn = new JButton(type);
    btn.setBackground(color);
    btn.setFont(new Font("Arial", Font.BOLD, 16));
    btn.setForeground(Color.white);

    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
        int input = JOptionPane.showConfirmDialog(null, "Do you want to " + type +" this entry?");

        if (input == JOptionPane.OK_OPTION) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int id = Integer.parseInt((String) model.getValueAt(row, 0));
            if (type == "Delete") {
                model.removeRow(row);
                table.revalidate();
                try {
                    DatabaseConnector.createConnection();
                    if (header=="Product") {
                        DatabaseConnector.deleteProduct(id);
                    } else if (header=="Customer") {
                        DatabaseConnector.deleteCustomer(id);
                    } else if (header=="System User") {
                        DatabaseConnector.deleteAdmin(id);
                    } else if (header=="Invoice") {
                        DatabaseConnector.updateInvoice(id);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                String[] product = {(String) model.getValueAt(row, 0), (String) model.getValueAt(row, 1), (String) model.getValueAt(row, 2), (String) model.getValueAt(row, 3), (String) model.getValueAt(row, 4), (String) model.getValueAt(row, 5), (String) model.getValueAt(row, 6), (String) model.getValueAt(row, 7)};
                String[] dataFields = {(String) model.getValueAt(row, 0), (String) model.getValueAt(row, 1), (String) model.getValueAt(row, 2), (String) model.getValueAt(row, 3), (String) model.getValueAt(row, 4), (String) model.getValueAt(row, 5), (String) model.getValueAt(row, 6)};

                try {
                    DatabaseConnector.createConnection();
                    if (header == "Product") {
                        DatabaseConnector.updateProduct(product);
                    } else if (header == "Customer") {
                        DatabaseConnector.updateCustomer(dataFields);
                    } else if (header == "System User") {
                        DatabaseConnector.updateAdmin(dataFields);
                    }
                    
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        }
      }
    });
  }
  @Override
  public Component getTableCellRendererComponent(JTable table, Object 
  value, boolean isSelected, boolean hasFocus, int row, int column) 
  {
    return btn;
  }
  @Override
  public Component getTableCellEditorComponent(JTable table, Object 
  value, boolean isSelected, int row, int column) 
  {
    this.row = row;
    return btn;
  }
  @Override
  public Object getCellEditorValue() { return true; }
  @Override
  public boolean isCellEditable(EventObject anEvent) { return true; }
  @Override
  public boolean shouldSelectCell(EventObject anEvent) { return true; }
  @Override
  public boolean stopCellEditing() { return true; }
  @Override
  public void cancelCellEditing() {}
  @Override
  public void addCellEditorListener(CellEditorListener l) {}
  @Override
  public void removeCellEditorListener(CellEditorListener l) {}
}

