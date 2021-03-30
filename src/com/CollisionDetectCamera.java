package com;

import org.jogamp.java3d.*;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3f;

import java.util.Iterator;

public class CollisionDetectCamera extends Behavior {

    private Point3d defaultPos = new Point3d(5, 2.5, 1.25);

    private boolean inCollision;
    private Shape3D shape;
    private TransformGroup cameraTG;
    private Appearance shapeAppearance;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;
    private ColoringAttributes defaultColor;

    public void initialize() { // USE_GEOMETRY USE_BOUNDS
        wEnter = new WakeupOnCollisionEntry(shape, WakeupOnCollisionEntry.USE_GEOMETRY);
        wExit = new WakeupOnCollisionExit(shape, WakeupOnCollisionExit.USE_GEOMETRY);
        wakeupOn(wEnter); // initialize the behavior
    }

    public void processStimulus(Iterator<WakeupCriterion> criteria) {
        Color3f hilightClr = Commons.Green;
        ColoringAttributes highlight = new ColoringAttributes(hilightClr, ColoringAttributes.SHADE_GOURAUD);
        inCollision = !inCollision; // collision has taken place

        if (inCollision) { // change color to highlight 'shape'
            shapeAppearance.setColoringAttributes(highlight);
            wakeupOn(wExit); // keep the color until no collision
        } else { // change color back to its original
            shapeAppearance.setColoringAttributes(defaultColor);
            wakeupOn(wEnter); // wait for collision happens
        }
    }


    public CollisionDetectCamera(Shape3D camera){

        shape = camera;

        shapeAppearance = shape.getAppearance();

        defaultColor = camera.getAppearance().getColoringAttributes();

        cameraTG = (TransformGroup)shape.getParent();

        inCollision = false;

    }

}
