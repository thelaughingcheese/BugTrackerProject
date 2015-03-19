import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CreateReport {
    
    protected JTextField numField;
    protected JTextField typeField;
    protected JTextArea bodyField;
    
    public CreateReport(){
        initNewReportGUI();
    }
    
    private void initNewReportGUI(){
        //component declarations
        JFrame myFrame = new JFrame ("Submit New Bug");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(500,400);
        myFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JPanel numPanel = new JPanel();
        JPanel typePanel = new JPanel();
        JPanel bodyPanel = new JPanel();
        JLabel numLabel = new JLabel("Report #: ");
        JLabel typeLabel = new JLabel ("Type of bug:");
        JLabel bodyLabel = new JLabel ("Summary of bug:");
        //field is for typing
        numField = new JTextField (20);
        typeField = new JTextField (20);
        bodyField = new JTextArea (10,20);
        bodyField.setWrapStyleWord(true);
        bodyField.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(bodyField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JButton submitButton = new JButton ("Submit");
        //just calls the writing method when pressing Submit then exits
        submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                    writeReport();
                    System.exit(0);}
                    }
                );
        numPanel.add(numLabel);//adding all the components to the frame
        numPanel.add(numField);
        typePanel.add(typeLabel);
        typePanel.add(typeField);
        bodyPanel.add(bodyLabel);
        bodyPanel.add(scrollPane);
        c.weightx=0.5;//some formatting for the window
        c.gridx=0;
        c.gridy=0;
        myFrame.add(numPanel,c);
        c.gridy=1;
        myFrame.add(typePanel,c);
        c.gridy=2;
        myFrame.add(bodyPanel,c);
        c.gridy=3;
        myFrame.add(submitButton,c);
        myFrame.pack();
        myFrame.setVisible(true);
        }
    
        private void writeReport(){
            Path path1 = Paths.get("", "sampleDB.txt"); //writes to sampleDB.txt which should exist in the same folder as the .java file, make a blank one if you don't have one already
            Charset dset = Charset.defaultCharset();
            //test to make sure path to the db file is correct
            //String s = path1.toAbsolutePath().toString();
            //System.out.println("Current relative path is: " + s);
            
            try (BufferedWriter writeOut = Files.newBufferedWriter(path1,dset,StandardOpenOption.APPEND)){
                writeOut.write(numField.getText());
                writeOut.newLine();
                writeOut.write(typeField.getText());
                writeOut.newLine();
                writeOut.write(bodyField.getText());
                writeOut.newLine();
                writeOut.write("EOR");//for now appends EOR to end of bug reports so you can tell when one ends and another starts if we have to parse the db text file
                writeOut.newLine();
                writeOut.close();
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }

        }
    
    
   	public static void main(String args[]) throws IOException{
          
            CreateReport tester = new CreateReport();
            tester.initNewReportGUI();
            
        }
}
