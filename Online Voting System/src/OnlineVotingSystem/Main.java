package OnlineVotingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends JFrame implements ActionListener{
    JButton submit,results;
    JRadioButton partyA,partyB,partyC;
    JTextField nameTextField,phoneTextField;
    Main(){
        setLayout(null);
        setTitle("Voting System");

        JLabel heading=new JLabel("ONLINE VOTING SYSTEM");
        add(heading);
        heading.setBounds(150,20,500,70);
        heading.setFont(new Font("Osward",Font.BOLD,38));

        JLabel name=new JLabel("Enter Name");
        add(name);
        name.setBounds(100,100,250,70);
        name.setFont(new Font("Osward",Font.BOLD,20));

        nameTextField=new JTextField();
        add(nameTextField);
        nameTextField.setBounds(250,125,200,25);

        JLabel phone=new JLabel("Phone");
        add(phone);
        phone.setBounds(100,150,250,70);
        phone.setFont(new Font("Osward",Font.BOLD,20));

         phoneTextField=new JTextField();
        add(phoneTextField);
        phoneTextField.setBounds(250,175,200,25);

         partyA=new JRadioButton("Party A");
        partyA.setBounds(100,225,70,30);
        partyA.setBackground(Color.WHITE);
        add(partyA);

         partyB=new JRadioButton("Party B");
        partyB.setBounds(100,270,70,30);
        partyB.setBackground(Color.WHITE);
        add(partyB);

         partyC=new JRadioButton("Party C");
        partyC.setBounds(100,310,70,30);
        partyC.setBackground(Color.WHITE);
        add(partyC);

        ButtonGroup partyGroup=new ButtonGroup();
        partyGroup.add(partyA);
        partyGroup.add(partyB);
        partyGroup.add(partyC);

         submit=new JButton("SUBMIT YOUR VOTE");
        submit.setBounds(400,250,250,30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);

         results=new JButton("CHECK RESULTS");
        results.setBounds(400,300,250,30);
        results.setBackground(Color.BLACK);
        results.setForeground(Color.WHITE);
        results.addActionListener(this);
        add(results);

        setSize(800,480);
        setVisible(true);
        setLocation(300,150);
        getContentPane().setBackground(Color.white);
    }
    public void actionPerformed(ActionEvent ae) {


        String partySelected = null;
        Conn obj = new Conn();
        if (partyA.isSelected()) {
            partySelected = "A";
        }
        if (partyB.isSelected()) {
            partySelected = "B";
        }
        if (partyC.isSelected()) {
            partySelected = "C";
        }
        String voterName = nameTextField.getText();
        String voterPhone = phoneTextField.getText();
        if (ae.getSource() == submit) {
            if (partySelected == null || nameTextField.getText().equals("") || phoneTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill required fields");
            } else {
                String query1 = "insert into Party values('" + voterName + "','" + voterPhone + "','" + partySelected + "')";
                try {
                    obj.s.executeUpdate(query1);
                } catch (Exception e) {
                    System.out.println("Some error occured");
                }
                JOptionPane.showMessageDialog(null, "Thank You");
                setVisible(false);
            }
        }
        if (ae.getSource() == results) {

            if (partySelected == null || nameTextField.getText().equals("") || phoneTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill the required feilds");
            }
            else {
                try {
                    Statement statement1 = obj.c.createStatement();
                    Statement statement2 = obj.c.createStatement();
                    Statement statement3 = obj.c.createStatement();
                   ResultSet aTotal = statement1.executeQuery("select count(partyName) as resultA from Party where partyName='A'");
                   ResultSet bTotal = statement2.executeQuery("select count(partyName) as resultB from Party  where partyName='B'");
                   ResultSet cTotal = statement3.executeQuery("select count(partyName) as resultC from Party  where partyName='C'");
                    int aResult = 0, bResult = 0, cResult = 0;
                    if (aTotal.next()) {
                        aResult = aTotal.getInt("resultA");
                    }
                    if (bTotal.next()) {
                        bResult = bTotal.getInt("resultB");
                    }
                    if (cTotal.next()) {
                        cResult = cTotal.getInt("resultC");
                    }

                    String max = null;
                    if (aResult > bResult && aResult > cResult) {
                        max = "Party A is in Lead";
                    }
                    else if (bResult > aResult && bResult > cResult) {
                        max = "Party B is in Lead";
                    }
                    else if (cResult > bResult && cResult > aResult) {
                        max = "Party C is in Lead";
                    }
                    else if (aResult==bResult && aResult==cResult) {
                        max="all having equal votes";
                    }
                    else if (aResult==bResult) {
                        max="Party A abd B got equal votes";
                    }
                    else if (bResult==cResult) {
                        max="Party B abd C got equal votes";
                    }
                    else {
                        max="Party A and C got equal votes";
                    }

                    JOptionPane.showMessageDialog(null, "Party A " + aResult + "\n Party B " + bResult + "\n Party C " + cResult + "\n \n "+max+"");

                    statement1.close();
                    statement2.close();
                    statement3.close();
                    setVisible(false);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }
    public static void main(String[] args) {

        Main obj=new Main();
    }

}