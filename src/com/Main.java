package com;


import org.jogamp.java3d.*;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;


public class Main {

    /**
     * creates a 3D axis, x being horizontal, y being vertical, and z being depth
     */
    private static void createAxis(TransformGroup sceneTG) {
        LineArray lineArr = new LineArray(6, LineArray.COLOR_3 | LineArray.COORDINATES);

        Point3f origin = new Point3f(0.0f, 0.0f, 0.0f);

        // create x axis
        lineArr.setCoordinate(0, origin);
        lineArr.setCoordinate(1, new Point3f(1, 0.0f, 0.0f));
        lineArr.setColor(0, Commons.Green);
        lineArr.setColor(1, Commons.Green);

        // create y axis
        lineArr.setCoordinate(2, origin);
        lineArr.setCoordinate(3, new Point3f(0.0f, 1, 0.0f));
        lineArr.setColor(2, Commons.Blue);
        lineArr.setColor(3, Commons.Blue);

        // create z axis
        lineArr.setCoordinate(4, origin);
        lineArr.setCoordinate(5, new Point3f(0.0f, 0.0f, 1));
        lineArr.setColor(4, Commons.Red);
        lineArr.setColor(5, Commons.Red);

        sceneTG.addChild(new Shape3D(lineArr));

    }

    /**
     * function to set the material of a shape
     *
     * @return Material
     */
    public static Material setMaterial(Color3f color) {
        int SH = 256;
        Material ma = new Material();

        ma.setAmbientColor(color);
        ma.setEmissiveColor(color);
        ma.setSpecularColor(Commons.White);
        ma.setDiffuseColor(Commons.White);
        ma.setShininess(SH);
        ma.setLightingEnable(true);

        return ma;
    }


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
        content_TG.addChild(new Link(Room.createWindows(scale)));
        return scene;

    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            Commons.setEye(new Point3d(5, 2.5, 1.25));
            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
