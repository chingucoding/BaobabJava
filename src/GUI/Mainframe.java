package GUI;

import Data.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Marcel on 06.03.2017.
 */
public class Mainframe extends JFrame{
    private JButton chooseDirectoy;
    private JPanel rootPanel;
    private TreeviewPanel treeview;
    private SunviewPanel sunview;
    private JButton analyzeButton;
    private JLabel currentPathLabel;

    private static Node supernode;

    private boolean threadStarted=false;

    public Mainframe(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(this.rootPanel);
        this.setSize(1920,1080);
        this.pack();
        this.setVisible(true);
    }


    private void createUIComponents() {
        rootPanel=new JPanel();
        chooseDirectoy=new JButton();
        chooseDirectoy.addActionListener(e -> {
            JFileChooser fs = new JFileChooser(new File("c:"));
            fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fs.setDialogTitle("save");
            //fs.setFileFilter(new FileNameExtensionFilter("Image", "jpeg","png"));
            int returnVal = fs.showSaveDialog(null);
            switch (returnVal) {
                case JFileChooser.APPROVE_OPTION:
                    File input = fs.getSelectedFile();
                    if (input.exists()) {
                        supernode=new Node(input);
                    } else {
                    }
                    fs.setVisible(false);
                    break;
                case JFileChooser.CANCEL_OPTION:
                    fs.setVisible(false);
                    break;
            }
            currentPathLabel.setText(supernode.getOwnPath().getAbsolutePath());
        });

        analyzeButton=new JButton();
        analyzeButton.addActionListener(e -> {
            if(supernode==null) {
                JFileChooser fs = new JFileChooser(new File("c:\\documents"));
                fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fs.setDialogTitle("save");
                //fs.setFileFilter(new FileNameExtensionFilter("Image", "jpeg","png"));
                int returnVal = fs.showSaveDialog(null);
                switch (returnVal) {
                    case JFileChooser.APPROVE_OPTION:
                        File input = fs.getSelectedFile();
                        if (input.exists()) {
                            supernode = new Node(input);
                        } else {
                        }
                        fs.setVisible(false);
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        fs.setVisible(false);
                        break;
                }
            }
            currentPathLabel.setText(supernode.getOwnPath().getAbsolutePath());
            Thread background=new Thread(()->{
                sunview.displayClaculatingMesssage();
                sunview.setNodeInformation(supernode.getName(),"-calculating-");
                treeview.displayClaculatingMesssage();
                if(supernode!=null){
                    supernode.calculateSubnodes();
                    supernode.calculateSize();
                    supernode.sortNodesSizeReversed();
                    SunviewPanel.setColorsBasedOnAngle(supernode);
                }
                sunview.drawNode(supernode);
                sunview.setNodeInformation(supernode.getName(),supernode.sizeFormated());
                treeview.showNode(supernode);
                threadStarted=false;
            });
            if(!threadStarted){
                threadStarted=true;
                background.start();
            }
        });

        treeview=new TreeviewPanel();

        sunview=new SunviewPanel();

        currentPathLabel=new JLabel();
    }

    public static Node getSupernode(){
        return supernode;
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Mainframe mf=new Mainframe();
        return;
    }

}
