package com;

import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Camera implements KeyListener {

    private Point3d eye = new Point3d(5, 2.5, 1.25);
    TransformGroup cameraTG;

    public Camera(){

    }

//    public TransformGroup getTG(){
//        return this.cameraTG;
//    }

    KeyNavigatorBehavior defineCamera(SimpleUniverse su){
        TransformGroup viewTransform = su.getViewingPlatform().getViewPlatformTransform();
        Point3d center = new Point3d(0, 2.50, 0);               // define the point where the eye looks at
        Vector3d up = new Vector3d(0, 1, 0);                 // define camera's up direction
        Transform3D view_TM = new Transform3D();
        view_TM.lookAt(eye, center, up);
        view_TM.invert();
        viewTransform.setTransform(view_TM);                 // set the TransformGroup of ViewingPlatform

        KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(viewTransform);
        BoundingSphere view_bounds = new BoundingSphere(new Point3d(), Double.MAX_VALUE);
        keyNavBeh.setSchedulingBounds(view_bounds);

        su.getCanvas().addKeyListener(this);
        su.getCanvas().setFocusable(true);

        System.out.println(su.getCanvas().isFocusable());

        return keyNavBeh;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     * @param e the event to be processed
     */
    public void keyTyped(java.awt.event.KeyEvent e){

        char hit = e.getKeyChar();

        System.out.println("Pressed: " + hit);

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     * @param e the event to be processed
     */
    public void keyPressed(KeyEvent e){



    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     * @param e the event to be processed
     */
    public void keyReleased(KeyEvent e){



    }


}
