package com;

import org.jogamp.java3d.*;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;

import java.awt.*;


public class Main {

    /**
     * creates a 3D axis, x being horizontal, y being vertical, and z being depth
     *
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
     * creates lights using the Lightbulb class
     *
     * @param sceneTG transform group to add the lights to
     */
    public static void addLights(TransformGroup sceneTG, Vector3f position){

        //transform group for lights - added to sceneTG as child
        TransformGroup lightTG = new TransformGroup();

        //default lightbulb
        Lightbulb lightbulb = new Lightbulb();

        //create lightbulb and light
        lightbulb.createLightBulb(Commons.Yellow);
        lightbulb.createLight();

        //change transparency of lightbulb depending on if the lightbulb is on or off
        TransparencyAttributes lightOnTA = lightbulb.getLightbulb().getAppearance().getTransparencyAttributes();
        ColoringAttributes lightOnCA = lightbulb.getLightbulb().getAppearance().getColoringAttributes();
        if(lightbulb.getLight().getEnable()){
            lightOnTA = new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.05f);
            lightOnCA = new ColoringAttributes(Commons.White, ColoringAttributes.SHADE_GOURAUD);
        }
        lightbulb.getLightbulb().getAppearance().setTransparencyAttributes(lightOnTA);
        lightbulb.getLightbulb().getAppearance().setColoringAttributes(lightOnCA);

        //transform3d to set the position of the light
        Transform3D lightT3D = new Transform3D();
        lightT3D.setTranslation(position);
        lightTG.setTransform(lightT3D);

        //add lightbulb to transform group
        lightTG.addChild(lightbulb.getLightbulb());
        lightTG.addChild(lightbulb.getLight());

        sceneTG.addChild(lightTG);

    }


    /**
     * function to set the material of a shape
     *
     * @return Material
     */
    public static Material setMaterial(Color3f color) {
        int SH = 128;
        Material ma = new Material();

        ma.setAmbientColor(color);
        ma.setEmissiveColor(new Color3f(color));
        ma.setDiffuseColor(new Color3f(0.6f, 0.6f, 0.6f));
        ma.setSpecularColor(new Color3f(1, 1, 1));
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

        //function to add lights
        addLights(sceneTG, new Vector3f(0,1,0));

        return sceneBG;
    }

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(() -> {
            Commons.setEye(new Point3d(2, 2, 2));
            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
