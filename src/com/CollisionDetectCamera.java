package com;

import org.jogamp.java3d.*;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3f;

import java.util.Iterator;

public class CollisionDetectCamera extends Behavior {

    private Point3d defaultPos = new Point3d(5, 2.5, 1.25);

    private boolean inCollision;
    private Shape3D shape;
    private TransformGroup cameraTG;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;

    public void initialize() { // USE_GEOMETRY USE_BOUNDS
        wEnter = new WakeupOnCollisionEntry(shape, WakeupOnCollisionEntry.USE_GEOMETRY);
        wExit = new WakeupOnCollisionExit(shape, WakeupOnCollisionExit.USE_GEOMETRY);
        wakeupOn(wEnter); // initialize the behavior
    }

    public void processStimulus(Iterator<WakeupCriterion> criteria) {
        Point3d defaultPos = new Point3d(5, 2.5, 1.25);
        inCollision = !inCollision; // collision has taken place

        //when collision happens set the camera to its default position
        if (inCollision) {
            Transform3D lightT3D = new Transform3D();
            lightT3D.setTranslation(new Vector3f((float)defaultPos.x, (float)defaultPos.y,(float)defaultPos.z));
            cameraTG.setTransform(lightT3D);
            wakeupOn(wExit); // keep the color until no collision
        }
    }

    public CollisionDetectCamera(Shape3D camera){

        shape = camera;

        cameraTG = (TransformGroup)shape.getParent();

        inCollision = false;

    }

}
