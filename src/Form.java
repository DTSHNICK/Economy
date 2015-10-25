import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
/*
 * Created by JFormDesigner on Tue Sep 15 10:37:07 MSK 2015
 */



/**
 * @author DTSHNICK DTSHNICK
 */
public class Form extends JFrame {
    public Form() {
        initComponents();
    }

    static {

    }


    public static void main(String[] args)
    {


        Form form=new Form();
        form.setVisible(true);

    }

    private void addActionPerformed(ActionEvent e) {
        DefaultTableModel model= (DefaultTableModel) table1.getModel();
        model.setRowCount(model.getRowCount() + 1);
        table1.setModel(model);

    }

    private void removeActionPerformed(ActionEvent e) {
        try {

            DefaultTableModel model= (DefaultTableModel) table1.getModel();
            model.setRowCount(model.getRowCount()-1);
            table1.setModel(model);

        }
        catch (java.lang.ArrayIndexOutOfBoundsException ex)
        {

        }

    }

    private void calculateActionPerformed(ActionEvent e) {
        if (table1.getColumnCount()==3)
        {

            {
                DefaultTableModel softwareTable=new DefaultTableModel() {
                    @Override
                    public Class<?> getColumnClass(int columnIndex)
                    {
                        if ((columnIndex==2))
                        {
                            return Boolean.class;
                        }
                        return super.getColumnClass(columnIndex);
                    }
                };
                //softwareTable.setRowCount(2);
                softwareTable.addColumn("Product",new Object[]{"Windows 7","Microsoft Visual Studio" });
                softwareTable.addColumn("Cost",new Object[]{670,4345});
                softwareTable.addColumn("Is target software",new Object[]{false,true});
                table5.setModel(softwareTable);
            }





            DefaultTableModel model= (DefaultTableModel) table1.getModel();


            model.addColumn("Days");
            for (int i=0;i<model.getRowCount();i++)
            {
                float Q = Float.parseFloat(model.getValueAt(i, 1).toString());
                String performers= (String) model.getValueAt(i,2);
                String performers_list[] = performers.split("\\s*,\\s*");

                int R=performers_list.length;
                model.setValueAt(Q/R, i, 3);
            }

            //add to actors
            ArrayList<String>Actors=new ArrayList<>();
            for (int i=0;i<model.getRowCount();i++)
            {

                ArrayList<String>Performers_list=new ArrayList<>(Arrays.asList(model.getValueAt(i, 2).toString().split("\\s*,\\s*")));
                Actors.addAll(Performers_list);
            }
            Set<String> set =new HashSet<>(Actors);

            ArrayList<String> jobs=new ArrayList<>();
            jobs.addAll(set);
            for (int i=0;i<jobs.size();i++)
            {
                System.out.println(jobs.get(i));
            }
            //finish
            //crete timetable
            DefaultTableModel timeTableModel=new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex)
                {
                    /*if (!getColumnName(columnIndex).equals("Description of work")) {
                        return Boolean.class;
                    }*/
                    if ((columnIndex!=0) && (columnIndex!=1))
                    {
                        return Boolean.class;
                    }
                    else
                    {
                        if (columnIndex==1)
                        {
                            return Period.class;
                        }
                    }
                    return super.getColumnClass(columnIndex);
                }
            };
            timeTableModel.setRowCount(model.getRowCount());
            timeTableModel.addColumn("Description of work");
            timeTableModel.addColumn("Time");

            //fin
            //add jobs to table
            for (int i=0;i<jobs.size();i++)
            {
                timeTableModel.addColumn(jobs.get(i));
            }
            //finish
            //check performers and job

            for (int i=0;i<model.getRowCount();i++)
            {
                //take performers
                String all_perform=new String(model.getValueAt(i,2).toString());
                //drop perform
                ArrayList<String>Performers_list=new ArrayList<>(Arrays.asList(all_perform.split("\\s*,\\s*")));
                for (int j=0;j<Performers_list.size();j++) {
                    for (int k = 0; k < timeTableModel.getColumnCount(); k++) {
                        if (Performers_list.get(j).equals(timeTableModel.getColumnName(k)))
                        {
                            timeTableModel.setValueAt(true,i,k);
                        }
                    }
                }
            }

            //fin
            //take data from model to timemodel

            for (int i=0;i<model.getRowCount();i++)
            {
                timeTableModel.setValueAt(model.getValueAt(i,0),i,0);
            }
            //fin

            //add period


            ArrayList<Period> periods=new ArrayList<>();
            try
            {ArrayList<Integer> days=new ArrayList<>();
                Period start=new Period(textField1.getText().toString());
                for (int i=0;i<model.getRowCount();i++)
                {
                    days.add(Math.round(Float.parseFloat(model.getValueAt(i, model.getColumnCount() - 1).toString())));

                }
                start.addDay(days.get(0));

                periods.add(start);
                for (int i=1;i<model.getRowCount();i++)
                {
                    Period period=new Period(periods.get(i-1).getEnd());
                    period.addDay(days.get(i));
                    periods.add(period);
                }
                for (int i=0;i<periods.size();i++)
                {
                    timeTableModel.setValueAt(periods.get(i),i,1);
                }
            }
            catch (java.lang.NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Error");
            }


            //fin
            table1.setModel(model);
            table2.setModel(timeTableModel);
            //start calc finaces

            DefaultTableModel finTable=new DefaultTableModel();
            finTable.setRowCount(jobs.size());
            finTable.addColumn("Position");
            finTable.addColumn("Count");
            finTable.addColumn("Emolument");
            finTable.addColumn("Total Days");


            for (int i=0;i<jobs.size();i++)
            {
                finTable.setValueAt(jobs.get(i),i,0);
            }
            for (int i=0;i<jobs.size();i++)
            {
                int time=0;
                for (int j=0;j<timeTableModel.getRowCount();j++)
                {
                    if (timeTableModel.getValueAt(j,2+i)!=null)
                    {
                        int days=periods.get(j).getDays();
                        time+=days;
                    }
                }
                finTable.setValueAt(time,i,3);

            }
            //fin
            //add last day
            double added=0;
            for (int i=0;i<table1.getRowCount();i++)
            {
                double k= Double.parseDouble(table1.getValueAt(i, table1.getColumnCount() - 1).toString());
                added+=k;
            }
            Period start=new Period(textField1.getText());
            start.addDay((int) added);
            label24.setText(start.getFinish());
            //fin
            table3.setModel(finTable);
            button4.setEnabled(true);
        }


/*
        String string=new String("a,b, c,d, f, g");
        String animals_list[] = string.split("\\s*,\\s*");

        for (int i=0;i<animals_list.length;i++)
        {
            System.out.println(animals_list[i]);
        }
        System.out.print(animals_list.length);
*/

    }

    private void button4ActionPerformed(ActionEvent e) {
        try
        {
            DefaultTableModel finModel= (DefaultTableModel) table3.getModel();
            finModel.addColumn("Total Cost");
            float totalEvo=0;
            for (int i=0;finModel.getRowCount()>i;i++)
            {
                int count = Integer.parseInt(table3.getValueAt(i, 1).toString());
                float evo = Float.parseFloat(table3.getValueAt(i, 2).toString());
                float days = Float.parseFloat(table3.getValueAt(i, 3).toString()) / 30f;
                System.out.println(count +" "+ evo+" "+days+" ");
                float res=Math.round(count*evo*days);
                totalEvo+=res;
                finModel.setValueAt(res,i,4);

            }
            label1.setText("Total:" + String.valueOf(totalEvo));
            label2.setText("Extra: "+String.valueOf(new BigDecimal(totalEvo*0.1).setScale(2, RoundingMode.UP).doubleValue()));
            label3.setText("Si :"+String.valueOf(new BigDecimal((totalEvo*0.1+totalEvo)*0.382).setScale(2, RoundingMode.UP).doubleValue()));

            double materials = 0d;
            for (int i=0;i<table4.getRowCount();i++)
            {
                double C= Double.parseDouble(table4.getValueAt(i,1).toString());
                double TP= Double.parseDouble(table4.getValueAt(i,2).toString())*C;
                double K= Double.parseDouble(table4.getValueAt(i,3).toString())+1d;
                double WC= Double.parseDouble(table4.getValueAt(i,4).toString());
                double TWP= Double.parseDouble(table4.getValueAt(i,5).toString())*WC;
                System.out.println("TP:"+TP+" K:"+K+" TWP:"+TWP);


                double res=(TP-TWP)*K;
               materials+=res;
            }
            label4.setText("M:"+materials);
            double A;
            {
                double Fa= Double.parseDouble(textField2.getText().toString());
                double Na = Double.parseDouble(textField3.getText().toString());
                A =Fa*(Na/100);
            }


            label14.setText("A: " + String.valueOf(A));

            double C3;
            {
                double N = Double.parseDouble(textField6.getText().toString());
                double F = Double.parseDouble(textField4.getText().toString());
                double Kt = Double.parseDouble(textField5.getText().toString());
                double Kp = Double.parseDouble(textField7.getText().toString());
                double C = Double.parseDouble(textField8.getText().toString());
                System.out.println(N+" "+F+" "+Kt+" "+Kp+" "+C);
                C3 = N * F * Kt * Kp * C;
            }
            label15.setText("C3: "+new BigDecimal(C3).setScale(2, RoundingMode.UP).doubleValue());
            double PR;
            {
                double FZP = Double.parseDouble(textField12.getText().toString());
                double K = Double.parseDouble(textField10.getText().toString());
                double T = Double.parseDouble(textField11.getText().toString());
                double F = Double.parseDouble(textField9.getText().toString());
                PR=FZP*(1+K)*T/F;
            }
            label16.setText("PR: "+new BigDecimal(PR).setScale(2, RoundingMode.UP).doubleValue());

            double ALL;
            double perTime;
            {
                double Fa= Double.parseDouble(textField2.getText().toString());
                double Nr = Fa*0.03d;
                ALL=Nr+PR+C3+A;
                ALL+=ALL*0.05;
                label21.setText("ALL: "+new BigDecimal(ALL).setScale(2, RoundingMode.UP).doubleValue());
                double fa =Double.parseDouble(textField4.getText().toString());
                perTime=ALL/fa;
                label22.setText("A/t: "+new BigDecimal(perTime).setScale(2, RoundingMode.UP).doubleValue());
            }
            double Cmo;
            {
                String start=textField1.getText();
                String finish=label24.getText();
                Period p=new Period(start,finish);
                int days=p.getDays();
                Cmo=(days*8)*new BigDecimal(perTime).setScale(2, RoundingMode.UP).doubleValue();
                System.out.println();
                System.out.println(days*8);
            }
            label25.setText(String.valueOf("Cmo: "+new BigDecimal(Cmo).setScale(2, RoundingMode.UP).doubleValue()));

            double Ssum=0d;
            {
                for (int i=0;i<table5.getRowCount();i++)
                {
                    if (new Boolean(table5.getValueAt(i, table5.getColumnCount() - 1).toString()))
                    {
                        String start=textField1.getText();
                        String finish=label24.getText();
                        Period p=new Period(start,finish);
                        int work=p.getDays()*8;
                        //TODO TEXTFIELD4 TAKE VALUE
                    }
                    else
                    {

                    }
                }

            }

        }
        catch (java.lang.NullPointerException ex)
        {

        }
        catch (java.lang.NumberFormatException ex)
        {

        }


    }

    private void button5ActionPerformed(ActionEvent e) {
        DefaultTableModel mod =(DefaultTableModel)table4.getModel();
        mod.setRowCount(mod.getRowCount()+1);
    }

    private void button6ActionPerformed(ActionEvent e) {
        DefaultTableModel mod =(DefaultTableModel)table4.getModel();
        if (mod.getRowCount()!=0)
            mod.setRowCount(mod.getRowCount()-1);
    }

    private void button7ActionPerformed(ActionEvent e) {
        DefaultTableModel mod=(DefaultTableModel)table5.getModel();
        mod.setRowCount(mod.getRowCount()+1);
    }

    private void button8ActionPerformed(ActionEvent e) {
        DefaultTableModel mod=(DefaultTableModel)table5.getModel();
        if (mod.getRowCount()!=0)
        {
            mod.setRowCount(mod.getRowCount()-1);
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Danil DANIL
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        textField1 = new JTextField();
        scrollPane3 = new JScrollPane();
        table3 = new JTable();
        button4 = new JButton();
        panel1 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label14 = new JLabel();
        label15 = new JLabel();
        label16 = new JLabel();
        label21 = new JLabel();
        label22 = new JLabel();
        label25 = new JLabel();
        label26 = new JLabel();
        scrollPane4 = new JScrollPane();
        table4 = new JTable();
        textField2 = new JTextField();
        button5 = new JButton();
        button6 = new JButton();
        label5 = new JLabel();
        label6 = new JLabel();
        textField3 = new JTextField();
        label7 = new JLabel();
        panel2 = new JPanel();
        label9 = new JLabel();
        label10 = new JLabel();
        label11 = new JLabel();
        label12 = new JLabel();
        textField4 = new JTextField();
        label13 = new JLabel();
        textField5 = new JTextField();
        textField6 = new JTextField();
        textField7 = new JTextField();
        textField8 = new JTextField();
        label17 = new JLabel();
        label18 = new JLabel();
        label19 = new JLabel();
        textField9 = new JTextField();
        textField10 = new JTextField();
        textField11 = new JTextField();
        label20 = new JLabel();
        textField12 = new JTextField();
        label23 = new JLabel();
        label24 = new JLabel();
        scrollPane5 = new JScrollPane();
        table5 = new JTable();
        button7 = new JButton();
        button8 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                    {"Analysis software", "10", "Programmer, Analyst"},
                    {"Defining requirements for software", "10", "Programmer, Analyst"},
                    {"Design", "20", "Programmer, Analyst"},
                    {"Development scheme of the program ", "10", "Programmer"},
                    {"Creating software code ", "10", "Programmer"},
                    {"Testing", "10", "Programmer, Analyst"},
                    {"Drawing documentation ", "10", "Programmer, Analyst"},
                },
                new String[] {
                    "Description of work", "Complexity", "Performer"
                }
            ));
            scrollPane1.setViewportView(table1);
        }

        //---- button1 ----
        button1.setText("Add");
        button1.addActionListener(e -> addActionPerformed(e));

        //---- button2 ----
        button2.setText("Remove");
        button2.addActionListener(e -> removeActionPerformed(e));

        //---- button3 ----
        button3.setText("Calculate");
        button3.addActionListener(e -> calculateActionPerformed(e));

        //======== scrollPane2 ========
        {

            //---- table2 ----
            table2.setModel(new DefaultTableModel());
            scrollPane2.setViewportView(table2);
        }

        //---- textField1 ----
        textField1.setText("1.1.2015");

        //======== scrollPane3 ========
        {

            //---- table3 ----
            table3.setModel(new DefaultTableModel());
            scrollPane3.setViewportView(table3);
        }

        //---- button4 ----
        button4.setText("Calc Fin");
        button4.setEnabled(false);
        button4.addActionListener(e -> button4ActionPerformed(e));

        //======== panel1 ========
        {

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });


            //---- label1 ----
            label1.setText(" ");

            //---- label2 ----
            label2.setText(" ");

            //---- label3 ----
            label3.setText(" ");

            //---- label4 ----
            label4.setText(" ");

            //---- label14 ----
            label14.setText(" ");

            //---- label15 ----
            label15.setText(" ");

            //---- label16 ----
            label16.setText(" ");

            //---- label21 ----
            label21.setText(" ");

            //---- label22 ----
            label22.setText(" ");

            //---- label25 ----
            label25.setText(" ");

            //---- label26 ----
            label26.setText(" ");

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(label21, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                            .addComponent(label22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(label25, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addComponent(label26, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label1)
                            .addComponent(label4)
                            .addComponent(label16)
                            .addComponent(label25))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label2)
                            .addComponent(label14)
                            .addComponent(label21)
                            .addComponent(label26))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label3)
                            .addComponent(label15)
                            .addComponent(label22))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        //======== scrollPane4 ========
        {

            //---- table4 ----
            table4.setModel(new DefaultTableModel(
                new Object[][] {
                    {"CD", "1", "3", "0.05", "0", "0"},
                    {"Paper", "1", "30", "0.05", "0", "0"},
                    {"Cartridge", "1", "30", "0.05", "0", "0"},
                },
                new String[] {
                    "Title", "Count", "Price", "K", "Waste count", "Waste sale"
                }
            ));
            scrollPane4.setViewportView(table4);
        }

        //---- textField2 ----
        textField2.setText("3000");

        //---- button5 ----
        button5.setText("Add");
        button5.addActionListener(e -> button5ActionPerformed(e));

        //---- button6 ----
        button6.setText("Remove");
        button6.addActionListener(e -> button6ActionPerformed(e));

        //---- label5 ----
        label5.setText("Project Start:");

        //---- label6 ----
        label6.setText("Fa");

        //---- textField3 ----
        textField3.setText("25");

        //---- label7 ----
        label7.setText("Na");

        //======== panel2 ========
        {

            //---- label9 ----
            label9.setText("F");

            //---- label10 ----
            label10.setText("Kt");

            //---- label11 ----
            label11.setText("Kp");

            //---- label12 ----
            label12.setText("Pp");

            //---- textField4 ----
            textField4.setText("1800");

            //---- label13 ----
            label13.setText("P");

            //---- textField5 ----
            textField5.setText("0.9");

            //---- textField6 ----
            textField6.setText("0.2");

            //---- textField7 ----
            textField7.setText("0.6");

            //---- textField8 ----
            textField8.setText("0.435");

            //---- label17 ----
            label17.setText("Fh");

            //---- label18 ----
            label18.setText("Ks");

            //---- label19 ----
            label19.setText("t");

            //---- textField9 ----
            textField9.setText("1750");

            //---- textField10 ----
            textField10.setText("0.382");

            //---- textField11 ----
            textField11.setText("12");

            //---- label20 ----
            label20.setText("Sf");

            //---- textField12 ----
            textField12.setText("12000");

            GroupLayout panel2Layout = new GroupLayout(panel2);
            panel2.setLayout(panel2Layout);
            panel2Layout.setHorizontalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(GroupLayout.Alignment.LEADING, panel2Layout.createSequentialGroup()
                                        .addComponent(label10)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textField5, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(GroupLayout.Alignment.LEADING, panel2Layout.createSequentialGroup()
                                        .addComponent(label9)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(panel2Layout.createParallelGroup()
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(label13)
                                        .addGap(18, 18, 18)
                                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(label11)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textField7, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(panel2Layout.createParallelGroup()
                                    .addComponent(label18)
                                    .addComponent(label17)))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(label12)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textField8, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(label19)))
                        .addGap(18, 18, 18)
                        .addGroup(panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(textField9, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label20)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textField12, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(textField11, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                .addComponent(textField10, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label9)
                            .addComponent(label13)
                            .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label17)
                            .addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label20)
                            .addComponent(textField12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label10)
                            .addComponent(label11)
                            .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label18)
                            .addComponent(textField10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label12)
                            .addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label19)
                            .addComponent(textField11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(28, Short.MAX_VALUE))
            );
        }

        //---- label23 ----
        label23.setText("Project Finish:");

        //---- label24 ----
        label24.setText(" ");

        //======== scrollPane5 ========
        {

            //---- table5 ----
            table5.setModel(new DefaultTableModel());
            scrollPane5.setViewportView(table5);
        }

        //---- button7 ----
        button7.setText("Add");
        button7.addActionListener(e -> button7ActionPerformed(e));

        //---- button8 ----
        button8.setText("Remove");
        button8.addActionListener(e -> button8ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 634, GroupLayout.PREFERRED_SIZE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(button1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label5)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label23)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label24)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(button4)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label6)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label7)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
                                .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 634, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                            .addGroup(contentPaneLayout.createSequentialGroup()
                                                    .addComponent(button5)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(button6))
                                            .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(contentPaneLayout.createSequentialGroup()
                                                    .addComponent(button7)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(button8)))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(scrollPane4))))
                    .addGap(86, 86, 86))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button1)
                        .addComponent(button2)
                        .addComponent(button3)
                        .addComponent(button4)
                        .addComponent(label5)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label6)
                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label7)
                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label23)
                        .addComponent(label24))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button5)
                                .addComponent(button6))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button7)
                                .addComponent(button8))
                            .addGap(0, 27, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Danil DANIL
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JTextField textField1;
    private JScrollPane scrollPane3;
    private JTable table3;
    private JButton button4;
    private JPanel panel1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label14;
    private JLabel label15;
    private JLabel label16;
    private JLabel label21;
    private JLabel label22;
    private JLabel label25;
    private JLabel label26;
    private JScrollPane scrollPane4;
    private JTable table4;
    private JTextField textField2;
    private JButton button5;
    private JButton button6;
    private JLabel label5;
    private JLabel label6;
    private JTextField textField3;
    private JLabel label7;
    private JPanel panel2;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JTextField textField4;
    private JLabel label13;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JLabel label17;
    private JLabel label18;
    private JLabel label19;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JLabel label20;
    private JTextField textField12;
    private JLabel label23;
    private JLabel label24;
    private JScrollPane scrollPane5;
    private JTable table5;
    private JButton button7;
    private JButton button8;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
