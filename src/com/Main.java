package com;

import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.vecmath.Point3d;

public class Main {


    /* a function to create and return the scene BranchGroup */
    public static BranchGroup createScene() {
        BranchGroup sceneBG = new BranchGroup(); // create 'objsBG' for content
        TransformGroup sceneTG = new TransformGroup();       // create a TransformGroup (TG)
        sceneBG.addChild(sceneTG);                             // add TG to the scene BranchGroup


        return sceneBG;
    }

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            Commons.setEye(new Point3d(1.35, 0.35, 2.0));
            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
