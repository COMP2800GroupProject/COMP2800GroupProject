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

        Lightbulb light = new Lightbulb(Commons.White, new Vector3f(1.25f, 2.4f, 9), true);
        content_TG.addChild(light.getTransformGroup());
        Lightbulb.PointLight(scene);

        float scale = 10;
        content_TG.addChild(Room.createRoom(scale));
        content_TG.addChild(new Link(Cab.Togethor()));
        content_TG.addChild(new Link(com.objects.createObject("door_knob_smooth (1).obj",new Vector3f(7.35f, 2f, 9.85f))));
        content_TG.addChild(new Link(com.objects.createObject("basw57iwvs93.obj", new Vector3f(5, 2, 3))));
        content_TG.addChild(Animations.createFan(new Vector3f(0.5f, 2.85f, 3)));
        content_TG.addChild(Animations.createGlobe(new Vector3f(0.5f, 3.1f, 5)));
        return scene;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Commons.MyGUI(createScene(), "COMP2800 Project");
        });
    }

}
