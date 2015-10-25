import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
/*
 * Created by JFormDesigner on Tue Sep 15 12:11:12 MSK 2015
 */



/**
 * @author DTSHNICK DTSHNICK
 */
public class test extends JFrame {
    public test() {
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {

        Object[] columnNames = {"Type", "Company", "Shares", "Price", "Boolean"};
        Object[][] data = {
                {"Buy", "IBM", new Integer(1000), new Double(80.50), false},
                {"Sell", "MicroSoft", new Integer(2000), new Double(6.25), true},
                {"Sell", "Apple", new Integer(3000), new Double(7.35), true},
                {"Buy", "Nortel", new Integer(4000), new Double(20.00), false}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames){
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                if (getColumnName(columnIndex).equals("Boolean")) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        table.setModel(model);
        /*table.setModel(new DefaultTableModel(record, new String[]{"id", "area", "location",
                "status1"}) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (getColumnName(columnIndex).equals("status1")) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        });*/

    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - DTSHNICK DTSHNICK
        scrollPane1 = new JScrollPane();
        table = new JTable();
        button1 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table);
        }

        //---- button1 ----
        button1.setText("text");
        button1.addActionListener(e -> button1ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(button1)
                            .addGap(0, 418, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(button1)
                    .addContainerGap(21, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public static void main (String args[])
    {
        //test test =new test();
        //test.setVisible(true);
        Period period=new Period("24.2.1992","31.2.1992");
        System.out.println(period.getDays());
    }



    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - DTSHNICK DTSHNICK
    private JScrollPane scrollPane1;
    private JTable table;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
