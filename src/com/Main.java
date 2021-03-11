package com;

import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Link;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.vecmath.Point3d;


public class Main {


    /* a function to create and return the scene BranchGroup */
    public static BranchGroup createScene() {
        BranchGroup scene = new BranchGroup();
        TransformGroup content_TG = new TransformGroup();
        scene.addChild(content_TG);

        float scale = 10;
        content_TG.addChild(new Link(Room.createFloor(scale)));
        content_TG.addChild(new Link(Room.createCeiling(scale)));
        content_TG.addChild(new Link(Room.createNorthWall(scale)));
        content_TG.addChild(new Link(Room.createEastWall(scale)));
        content_TG.addChild(new Link(Room.createWestWall(scale)));
        content_TG.addChild(new Link(Room.createSouthWall(scale)));
        content_TG.addChild(new Link(Room.createBars(scale)));
        return scene;
    }

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            Commons.setEye(new Point3d(9, 7, 15));
            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
