package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.*;

import java.awt.*;


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
        BranchGroup sceneBG = new BranchGroup(); // create 'objsBG' for content
        TransformGroup staticSceneTG = new TransformGroup();
        TransformGroup sceneTG = new TransformGroup();// create a TransformGroup (TG)

        sceneBG.addChild(sceneTG);// add TG to the scene BranchGroup

        //tg and axis function
        sceneBG.addChild(staticSceneTG);
        createAxis(staticSceneTG);

        sceneBG.addChild(Commons.rotateBehavior(10000, sceneTG));

        //create new lightbulb
        /**
         * creates a new lightbulb - (TransformGroup, Color, Position, Boolean for on/off)
         */
        new Lightbulb(sceneTG, Commons.White, new Vector3f(0, 1, 0), true);

        return sceneBG;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            Commons.setEye(new Point3d(2, 2, 2));
            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
