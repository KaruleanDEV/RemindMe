package oop.msu;

//------------------------------------> OOP Assignment 3 <------------------------------------
//REFER TO https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html for documentation. 
/* Author: Zack, Dashan, Ken 
 * A demo application showcasing GUI components using javax.swing library For Mr.Kevin OOP Class @ MSU
 * Java Version: openjdk version "17.0.11" 2024-04-16 Eclipse Adoptium
 */


 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.FocusEvent;
 import java.awt.event.FocusListener;
 
 public class App extends JFrame {
     private JTextField itemTextField;
     private JPanel itemListPanel;
     private JButton addButton;
     private JButton clearButton;
     private JButton clearCheckedButton;
     private JLabel instructionLabel;
     private int itemCount;
     private JTextArea noteField;
 
     /*
     * =================== JFRAME PROPERTIES ===================
     */
 
     public App() {
         //JFrame
         setTitle("Remind me");
         setSize(940, 540);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null); // Center relative to screen size
         //When compiling into Jar, use getResource refer to https://stackoverflow.com/questions/56387179/java-swing-maven-how-to-load-imageicon-in-jar-file
         setIconImage(new ImageIcon(getClass().getResource("/ico.png")).getImage());
 
         // Add Component Here
         itemTextField = new JTextField(20); //Adjust text fieldwidth
         itemListPanel = new JPanel();
         itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
         addButton = new JButton("Add Item");
         clearButton = new JButton("Clear List");
         clearCheckedButton = new JButton("Clear Checked Items");
         instructionLabel = new JLabel("Enter and click 'Add Item'");
         //Apparently you can use HTML for customization but only in JTextPane Feel free to rewrite this
         noteField = new JTextArea(10, 20);
         noteField.setEditable(true);
         noteField.setText("Your Notes Here...");
         
         // item counter ! Someone wanna redo this func to automatically resort on deletion of items? Check below
         itemCount = 0;
         
         // ! DO NOT TOUCH ! 
         JPanel panel = new JPanel();
         panel.setLayout(new FlowLayout());
         
         // Add to Panels
         panel.add(instructionLabel);
         panel.add(itemTextField);
         panel.add(addButton);
         panel.add(clearButton);
         panel.add(clearCheckedButton);
         panel.add(noteField);
         
         //PLACEMENT
         add(panel, BorderLayout.NORTH);
         add(new JScrollPane(itemListPanel), BorderLayout.CENTER);
         add(noteField, BorderLayout.EAST);
         
 
         /*
          * =================== EVENT LISTENER ===================
          */
         // Remove text when Focus on notes section, anyone have a cleaner solution to this?
         noteField.addFocusListener(new FocusListener() {
             @Override
             public void focusGained(FocusEvent e) {
                 JTextArea noteField = (JTextArea) e.getSource();
                 if (noteField.getText().equals("Your Notes Here...")) {
                     noteField.setText("");
                 }
             }
 
             @Override
             public void focusLost(FocusEvent e) {
                 //Focus Listener has two methods that are mandatory even if one not in use
             }
 });
         // ADD ITEM
         addButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String item = itemTextField.getText();
                 if (!item.isEmpty()) {
                     addItemToList(item);
                     itemTextField.setText("");
                 }
             }
         });
         
         // CLEAR BUTTON
         clearButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 itemListPanel.removeAll(); //reset GUI
                 itemListPanel.revalidate();
                 itemListPanel.repaint();
                 itemCount = 0;  // Reset item count
             }
         });
         
         // CLEAR CHECKED ITEM 
         clearCheckedButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 clearCheckedItems();
             }
         });
     }
 
     /* 
      * ! CAN BE MOVED AS A SAPERATE PACKAGE !
      * 
      * Note: Add Call Methods Here for use with event listener
      * Keep name relevent to the event listener for easy readibility.
      * 
      * DOC: USE TEMPLATE
      * private void yourCallMethods() {
      *      x = Do Something Fancy here
      *      //This is magic https://www.oracle.com/java/technologies/painting.html but it basically automagically update the content dynamically.
      *      x.revalidate()
      *      x.repaint()
      * 
      * }
      */
     private void addItemToList(String item) {
         itemCount++;
         JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
         JCheckBox itemCheckBox = new JCheckBox(itemCount + ". " + item);
         itemPanel.add(itemCheckBox);
         itemListPanel.add(itemPanel);
         itemListPanel.revalidate();
         itemListPanel.repaint();
     }
 
     private void clearCheckedItems() {
         Component[] components = itemListPanel.getComponents();
         for (Component component : components) {
             if (component instanceof JPanel) {
                 JPanel itemPanel = (JPanel) component;
                 Component[] itemComponents = itemPanel.getComponents();
                 for (Component itemComponent : itemComponents) {
                     if (itemComponent instanceof JCheckBox) {
                         JCheckBox checkBox = (JCheckBox) itemComponent;
                         if (checkBox.isSelected()) {
                             itemListPanel.remove(itemPanel);
                             break;
                         }
                     }
                 }
             }
         }
         itemListPanel.revalidate();
         itemListPanel.repaint();
     }
 
     public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 new App().setVisible(true);
             }
         });
     }
 }
 
  
 