package com;


import org.jogamp.java3d.*;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;


public class Main {

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

        content_TG.addChild(new Lightbulb(Commons.White, new Vector3f(1.25f, 2.4f, 9), true).getTransformGroup());

        content_TG.addChild(Backgrounds.createBackground());

        content_TG.addChild(new Link(Cab.Togethor()));

        float scale = 10;
        content_TG.addChild(new Link(Room.createFloor(scale)));
        content_TG.addChild(new Link(Room.createCeiling(scale)));
        content_TG.addChild(new Link(Room.createNorthWall(scale)));
        content_TG.addChild(new Link(Room.createEastWall(scale)));
        content_TG.addChild(new Link(Room.createWestWall(scale)));
        content_TG.addChild(new Link(Room.createSouthWall(scale)));
        content_TG.addChild(new Link(Room.createBars(scale)));
        content_TG.addChild(new Link(Room.createWindows(scale)));
        content_TG.addChild(new Link(Room.createDoors(scale)));
        return scene;

    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {


            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
