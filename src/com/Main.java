package com;


import org.jogamp.java3d.*;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;

import java.util.Objects;


public class Main {


    /* a function to create and return the scene BranchGroup */
    public static BranchGroup createScene() {

        BranchGroup scene = new BranchGroup();
        scene.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TransformGroup content_TG = new TransformGroup();
        scene.addChild(content_TG);


        

        content_TG.addChild(Backgrounds.createBackground());



        Lightbulb light = new Lightbulb(Commons.White, new Vector3f(5, 4.9f, 5), true);
        content_TG.addChild(light.getTransformGroup());
        Lightbulb.PointLight(scene);

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
        content_TG.addChild(new Link(Cab.Togethor()));
        content_TG.addChild(new Link(objects.soundObject()));
        content_TG.addChild(new Link(Room.createCurtains(scale)));
        content_TG.addChild(new Link(Room.createExteriorWalls(scale)));
        //content_TG.addChild(new Link(new TransformGroup(Backgrounds.createBackground())));
        return scene;


    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {


            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
