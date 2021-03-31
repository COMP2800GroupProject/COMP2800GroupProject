package com;

import org.jdesktop.j3d.examples.collision.Box;
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.ViewingPlatform;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;

import javax.xml.crypto.dsig.Transform;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

public class Camera implements KeyListener {
    private Point3d defaultPos = new Point3d(9.2, 2.5, 9.2);
    BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), Double.MAX_VALUE);
    KeyNavigatorBehavior keyNavBeh;

    public Camera(ViewPlatform vp, TransformGroup vpTG, BranchGroup bg){
        defineCamera(vpTG);
        vpTG.addChild(vp);
        bg.addChild(vpTG);
    }

//    public TransformGroup getTG(){
//        return this.cameraTG;
//    }

    void defineCamera(TransformGroup vpTG){

        addInvisSphere(vpTG);

        Point3d center = new Point3d(4, 2.50, 5);               // define the point where the defaultPos looks at
        Vector3d up = new Vector3d(0, 1, 0);                 // define camera's up direction
        Transform3D view_TM = new Transform3D();
        view_TM.lookAt(defaultPos, center, up);
        view_TM.invert();
        vpTG.setTransform(view_TM);// set the TransformGroup of ViewingPlatform

        this.keyNavBeh = new KeyNavigatorBehavior(vpTG);
        BoundingSphere view_bounds = new BoundingSphere(new Point3d(), Double.MAX_VALUE);
        keyNavBeh.setSchedulingBounds(view_bounds);
    }

    KeyNavigatorBehavior getKeyNavBeh(){
        return keyNavBeh;
    }

    /**
     * creates an invisible sphere for the camera. used for collision
     */
    void addInvisSphere(TransformGroup vpTG){
        //create invisible ball for the camera. used for collision
        Appearance app = new Appearance();
        ColoringAttributes ca = new ColoringAttributes(Commons.White, ColoringAttributes.FASTEST);

        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);

        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.FASTEST, 1f);

        app.setColoringAttributes(ca);
        app.setTransparencyAttributes(ta);
        app.setPolygonAttributes(pa);

        //sphere for the camera, used for collision
        Shape3D cameraBox = new Box(2f, 2f, 2f);

        cameraBox.setAppearance(app);

        cameraBox.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        cameraBox.setCapability(Shape3D.ALLOW_APPEARANCE_READ);

        CollisionDetectCamera cd = new CollisionDetectCamera(cameraBox);

        vpTG.addChild(cameraBox);
        vpTG.addChild(cd);
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
