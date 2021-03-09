package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;

import static com.Main.setMaterial;

public class Lightbulb {

    Sphere sphere;
    Light light;


    /**
     *  default constructor
     */
    public Lightbulb(){

    }

    /**
     * create a spotlight
     */
    public void createLight(){

        BoundingSphere myBounds = new BoundingSphere(new Point3d(), 1000);

        //create a spotlight facing downwards
        SpotLight spotLight = new SpotLight();
        spotLight.setColor(new Color3f(1f, 1f, 1f));
        spotLight.setPosition(new Point3f(0, 0f, 0));
        spotLight.setDirection(0,-1,0);                 //direction of light
        spotLight.setAttenuation(1,0,0);

        //light on by default
        spotLight.setEnable(true);

        //set bounds of the light
        spotLight.setInfluencingBounds(myBounds);

        this.light = spotLight;

    }

    /**
     * creates a sphere of the given color to represent a lightbulb
     * @param color of the lightbulb
     */
    public void createLightBulb(Color3f color){

        Appearance app = new Appearance();

        //setup appearance attributes
        ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.SHADE_GOURAUD);
        PolygonAttributes pa = new PolygonAttributes();
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.80f);

        //apply appearance attributes
        app.setColoringAttributes(ca);
        app.setPolygonAttributes(pa);
        app.setTransparencyAttributes(ta);

        app.setMaterial(setMaterial(color));

        this.sphere = new Sphere(.1f, Sphere.GENERATE_NORMALS, 120, app);

    }

    //getter for sphere
    public Sphere getLightbulb(){
        return sphere;
    }

    //getter for light
    public Light getLight(){
        return light;
    }

}
