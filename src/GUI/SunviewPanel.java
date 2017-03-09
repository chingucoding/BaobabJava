package GUI;

import Data.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Marcel on 06.03.2017.
 */
public class SunviewPanel {
    private JPanel rootPanel;

    private void createUIComponents() {
        rootPanel=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                int size;
                if(rootPanel.getWidth()==rootPanel.getHeight()){
                    size=rootPanel.getWidth();
                }else if(rootPanel.getWidth()>rootPanel.getHeight()){
                    size=rootPanel.getHeight();
                }else{
                    size=rootPanel.getWidth();
                }
                g.drawImage(scale(buffer,size,size/buffer.getHeight()),0,0,null);
            }
        };
    }


    BufferedImage buffer=new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB);

    private final static double degreeOffset=3.5;
    private final static double degreeSpacer=2.6;

    private final static double ringFactor=1.35;

    private final static double layerThickness=50*ringFactor;
    private final static double layerOffset=30*ringFactor;


    public void drawNode(Node node){
        System.out.print("Started drawing!");
        int size;
        if(rootPanel.getWidth()==rootPanel.getHeight()){
            size=rootPanel.getWidth();
        }else if(rootPanel.getWidth()>rootPanel.getHeight()){
            size=rootPanel.getHeight();
        }else{
            size=rootPanel.getWidth();
        }
        buffer=new BufferedImage(size,size,BufferedImage.TYPE_INT_ARGB);
        rootPanel.repaint();
        double offset=0;
        double radius=0;
        for(Node n:node.getSubNodes()){
            radius=360*((double)n.getSize())/((double)node.getSize())-degreeOffset;
            if(radius>degreeSpacer) {
                drawArc(radius, offset, 0);
                drawNode(1, n, offset, ((double) n.getSize()) / ((double) node.getSize()));
                offset += radius + degreeOffset;
            }
        }
    }

    private void drawNode(int layer,Node node,double offset_,double percentage){
        if(layer<5) {
            double offset = offset_;
            double radius = 0;
            for (Node n : node.getSubNodes()) {
                radius = 360 * percentage*((double) n.getSize()) / ((double) node.getSize()) - degreeOffset;
                if(radius>degreeSpacer) {
                    drawArc(radius, offset, layer);
                    drawNode(layer + 1, n, offset, (((double) n.getSize()) / ((double) node.getSize())) * percentage);
                    offset += radius + degreeOffset;
                }
            }
        }
    }

    public void drawArc(double degree,double degreeOffset,int layer){
        //Calculating where the arc would end in
        double totalAngle=degree+degreeOffset;

        //Calculating the distances for the given layer the data is in
        double minDistance=getLayerStart(layer);
        double maxDistance=getLayerEnd(layer);

        //Center of image
        int xCenter=buffer.getHeight()/2;
        int yCenter=buffer.getHeight()/2;

        //Bounds indicating the most upper point,most right point,lowest point and most righter point
        int northbound;
        int eastbound;
        int southbound;
        int westbound;

        //Explanation for the quadrants
        // Third quadrant | Fourth Quadrant
        //         3      |       4
        //----------------+---------------
        // Second Quadrant| First Quadrant
        //         2      |       1

        //First and second quadrant
        if(totalAngle<=180){
            if(Math.abs(Math.sin(Math.toRadians(totalAngle))*minDistance)<
                    Math.abs(Math.sin(Math.toRadians(degreeOffset))*minDistance)){
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*minDistance);
            }else{
                northbound=(int)(Math.sin(Math.toRadians(degreeOffset))*minDistance);

            }

            // First quadrant
            if(totalAngle<=90){
                eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(totalAngle))*minDistance);
            }
            //Lower half
            else{
                //Starts in first quadrant
                if(degreeOffset>90){
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
                    southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                }
                //Starts in second quadrant
                else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                    southbound=(int)(Math.sin(Math.toRadians(90))*maxDistance);
                }
                westbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
            }
        //Ends in third quadrant
        }else if(totalAngle<=270){
            //Starts in first quadrant
            if(degreeOffset<90){
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(90))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in second quadrant
            else if(degreeOffset<180){
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                if(Math.abs(Math.cos(Math.toRadians(totalAngle))*minDistance)<
                        Math.abs(Math.cos(Math.toRadians(degreeOffset))*minDistance)){
                    eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*minDistance);
                }else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
                }
                southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in second quadrant
            else{
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                if(Math.abs(Math.cos(Math.toRadians(totalAngle))*minDistance)<
                        Math.abs(Math.cos(Math.toRadians(degreeOffset))*minDistance)){
                    eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*minDistance);
                }else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
                }
                southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*minDistance);
                westbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
            }
        }
        //Ends in fourth quadrant
        else if(totalAngle<=360){
            //Starts in first quadrant
            if(degreeOffset<90){
                northbound=(int)(Math.sin(Math.toRadians(270))*maxDistance);
                if(Math.abs(Math.cos(Math.toRadians(totalAngle))*maxDistance)>
                        Math.abs(Math.cos(Math.toRadians(degreeOffset))*maxDistance)){
                    eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                }else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                }
                southbound=(int)(Math.sin(Math.toRadians(90))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in second quadrant
            else if(degreeOffset<180){
                northbound=(int)(Math.sin(Math.toRadians(270))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in third quadrant
            else if(degreeOffset<270){
                northbound=(int)(Math.sin(Math.toRadians(270))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                if(Math.abs(Math.sin(Math.toRadians(totalAngle))*minDistance)<Math.abs(Math.sin(Math.toRadians(degreeOffset))*minDistance)){
                    southbound=(int)(Math.sin(Math.toRadians(totalAngle))*minDistance);
                }else{
                    southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*minDistance);
                }
                westbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
            }
            //Starts in fourth quadrant
            else{
                northbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(totalAngle))*minDistance);
                westbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
            }
        }
        //Ends in any quadrant but would override content; is dismissed
        else{
            northbound=0;
            eastbound=0;
            southbound=0;
            westbound=0;
        }


        //System.out.println("Angle: "+totalAngle+" offset "+degreeOffset+
        //        " \nNorth: "+ northbound+" East: "+eastbound+" South: "+southbound+" West: "+westbound+
        //        " \n Maxdistance: "+maxDistance+" Mindistance: "+minDistance);

        Graphics2D g2=(Graphics2D)buffer.getGraphics();
        Graphics2D g2Panel=(Graphics2D)rootPanel.getGraphics();

        //Shift the values to the center of image
        northbound+=yCenter;
        eastbound+=xCenter;
        southbound+=yCenter;
        //Correction of errors while calculating the westbound (arcs are cut off on the left side without it)
        westbound+=xCenter-5;

        //g2.setStroke(new BasicStroke(2));
        //g2.drawLine(0,northbound,xCenter*2,northbound);
        //g2.drawLine(eastbound,0,eastbound,yCenter*2);
        //g2.drawLine(0,southbound,xCenter*2,southbound);
        //g2.drawLine(westbound,0,westbound,yCenter*2);



        g2.setColor(Color.red);
        g2Panel.setColor(Color.darkGray);
        for(int i=westbound-1;i<=eastbound;i++){
            for(int j=northbound-1;j<=southbound;j++){
                double distance=Math.sqrt(Math.pow(i-xCenter,2)+Math.pow(j-yCenter,2));
                double angle=Math.asin(Math.abs((double)j-yCenter)/distance);
                //1 & 2 quadrant
                if(j>yCenter) {
                    //2 quadrant
                    if (i < xCenter) {
                        angle=Math.toRadians(90)-angle;
                        angle += Math.toRadians(90);
                    }
                }
                // 3 & 4 quadrant
                else {
                    //3 quadrant
                    if (i > xCenter) {
                        angle=Math.toRadians(90)-angle;
                        angle += Math.toRadians(90);
                    }
                    angle+=Math.toRadians(180);
                }
                if(angle<=Math.toRadians(totalAngle)&&angle>=Math.toRadians(degreeOffset)){
                    if(distance>minDistance&&distance<maxDistance) {
                        g2.drawOval(i, j, 1, 1);
                        g2Panel.drawOval(i, j, 1, 1);
                    }
                }
            }
        }
    }

    private static double getLayerStart(int layer){
        if(layer==0){
            return layerOffset;
        }else{
            return layer*layerThickness+layerOffset+5;
        }
    }

    private static double getLayerEnd(int layer){
        if(layer==0){
            return layerOffset+layerThickness;
        }else{
            return layer*layerThickness+layerThickness+layerOffset;
        }
    }
    /**
     * scale image
     *
     * @param source image to scale
     * @param sizeDestination width of destination image
     * @param fSize x-factor for transformation / scaling
     * @return scaled image
     */
    public static BufferedImage scale(BufferedImage source, int sizeDestination, double fSize) {
        BufferedImage dbi = null;
        if(source != null) {
            dbi = new BufferedImage(sizeDestination, sizeDestination, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fSize, fSize);
            AffineTransformOp atp=new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
            dbi=atp.filter(source,dbi);
        }
        return dbi;
    }

}
