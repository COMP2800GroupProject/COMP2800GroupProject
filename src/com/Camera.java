package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

import javax.xml.crypto.dsig.Transform;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Camera implements KeyListener {

    private Point3d defaultPos = new Point3d(5, 2.5, 1.25);

    TransformGroup cameraTG;
    KeyNavigatorBehavior keyNavBeh;


    public Camera(SimpleUniverse su){

        defineCamera(su);

        //addInvisSphere();

    }

//    public TransformGroup getTG(){
//        return this.cameraTG;
//    }

    void defineCamera(SimpleUniverse su){
        this.cameraTG = su.getViewingPlatform().getViewPlatformTransform();

//        cameraTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        cameraTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        Point3d center = new Point3d(0, 2.50, 0);               // define the point where the defaultPos looks at
        Vector3d up = new Vector3d(0, 1, 0);                 // define camera's up direction
        Transform3D view_TM = new Transform3D();
        view_TM.lookAt(defaultPos, center, up);
        view_TM.invert();
        cameraTG.setTransform(view_TM);// set the TransformGroup of ViewingPlatform


        this.keyNavBeh = new KeyNavigatorBehavior(cameraTG);
        BoundingSphere view_bounds = new BoundingSphere(new Point3d(), Double.MAX_VALUE);
        keyNavBeh.setSchedulingBounds(view_bounds);


        //for keylistener -- not working atm
        su.getCanvas().addKeyListener(this);
        su.getCanvas().setFocusable(true);

    }

    KeyNavigatorBehavior getKeyNavBeh(){
        return keyNavBeh;
    }

    /**
     * creates an invisible sphere for the camera. used for collision
     */
    void addInvisSphere(){
        //create invisible ball for the camera. used for collision
        Appearance app = new Appearance();
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.FASTEST, 1);
        app.setTransparencyAttributes(ta);



        //sphere for the camera, used for collision
        Sphere cameraSphere = new Sphere(.1f, Sphere.GENERATE_NORMALS, 120, app);
        cameraTG.addChild(cameraSphere);
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     * @param e the event to be processed
     */
    public void keyTyped(java.awt.event.KeyEvent e){


    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     * @param e the event to be processed
     */
    public void keyPressed(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_A){
            System.out.println("a");
        }

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
