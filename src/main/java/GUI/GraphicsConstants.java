package main.java.GUI;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * This class holds all values and constants for the graphics, such as fonts or label sizes
 * Created by Marcel on 06.04.2017.
 */
class GraphicsConstants {

    static Font standardFont=new Font("Arial",Font.PLAIN,15);

    static Font standardFontLarger=new Font("Arial",Font.PLAIN,16);

    static Font labelFont=new Font("",Font.PLAIN,16);

    static Font labelFontItalic=new Font("",Font.PLAIN,16);

    static Font titleFont=new Font("",Font.PLAIN,16);

    static Font titleFontLarge=new Font("",Font.PLAIN,16);

    static int nameLabelX=200;
    static int nameLabelY=40;

    private static final int WidthFULLHD=1920;
    private static final int HeightFULLHD=1080;

    private static final int nLabelXFHD=150;
    private static final int nLabelYFHD=40;

    private static final int fontSizeFULLHD=11;
    private static final int fontSizeLabelFULLHD=11;

    static int sunviewPrefferedFULLHDX=1200;
    static int sunviewPrefferedFULLHDY=1080;

    static int treeviewPrefferedFULLHDX=600;
    static int treeviewPrefferedFULLHDY=1080;
    static int treeRowHeight=22;

    static int nodeViewerFULLHDX=350;
    static int nodeViewerFULLHDY=460;

    static int settingsPanelFULLHDX=400;
    static int settingsPanelFULLHDY=1080;



    private static void update(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        double scaleFactor;
        if(width/(double)WidthFULLHD>height/(double)HeightFULLHD){
            scaleFactor=height/HeightFULLHD;
        }else{
            scaleFactor=(width/(double)WidthFULLHD);
        }

        System.out.println(scaleFactor);
        nameLabelX=(int)(scaleFactor*nLabelXFHD);
        nameLabelY=(int)(scaleFactor*nLabelYFHD);

        //nodeViewerFULLHDX=(int)(350*scaleFactor);
        //nodeViewerFULLHDY=(int)(460*scaleFactor);
        treeRowHeight=(int)(22*scaleFactor);

        settingsPanelFULLHDX=(int)(300*scaleFactor);
        settingsPanelFULLHDY=(int)(200*scaleFactor);

        standardFont=new Font("Arial",Font.PLAIN,1+(int)Math.ceil(fontSizeFULLHD*scaleFactor));
        standardFontLarger=new Font("Arial",Font.PLAIN,2+(int)Math.ceil(fontSizeFULLHD*scaleFactor));
        labelFont=new Font("Arial",Font.PLAIN,1+(int)Math.ceil(fontSizeLabelFULLHD*scaleFactor));

        titleFont=new Font("Arial",Font.ITALIC,3+(int)Math.ceil(fontSizeFULLHD*scaleFactor));
        Map attributes = titleFont.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titleFont=titleFont.deriveFont(attributes)     ;
        titleFontLarge=new Font("Arial",Font.ITALIC,7+(int)Math.ceil((fontSizeFULLHD)*scaleFactor));
        attributes = titleFontLarge.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        titleFontLarge=titleFontLarge.deriveFont(attributes);

        labelFontItalic =new Font("Arial",Font.ITALIC,2+(int)Math.ceil(fontSizeFULLHD*scaleFactor));

    }

    public static double getScaleFactor(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        double scaleFactor;
        if(width/(double)WidthFULLHD>height/(double)HeightFULLHD){
            scaleFactor=height/HeightFULLHD;
        }else{
            scaleFactor=(width/(double)WidthFULLHD);
        }
        return scaleFactor;
    }


    static {
        update();
    }

}