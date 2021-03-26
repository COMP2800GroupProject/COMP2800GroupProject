package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;


public class Lightbulb {

    TransformGroup lightbulbTG = new TransformGroup();

    Color3f color;
    Vector3f position;
    Sphere sphere;
    Light light;
    Boolean on;


    /**
     * default constructor
     */

    public Lightbulb(Color3f color, Vector3f position, Boolean on) {

        this.position = position;
        this.color = color;
        this.on = on;

        this.light = createLight();
        this.sphere = createLightBulb(color);

        addLights();
    }

    /**
     * create a spotlight
     */
    public Light createLight() {


        BoundingSphere bounds = new BoundingSphere(new Point3d(position.x,position.y,position.z), Double.MAX_VALUE);

        AmbientLight ambientLight = new AmbientLight(on, color);

        ambientLight.setInfluencingBounds(bounds);

        return ambientLight;
    }

    /**
     * creates a sphere of the given color to represent a lightbulb
     *
     * @param color of the lightbulb
     */
    public Sphere createLightBulb(Color3f color) {

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

        return new Sphere(.1f, Sphere.GENERATE_NORMALS, 120, app);

    }

    /**
     * adds the lights to the TG
     */
    public void addLights() {

        //transform group for lights - added to sceneTG as child
        TransformGroup lightTG = new TransformGroup();

        //change transparency of lightbulb depending on if the lightbulb is on or off
        TransparencyAttributes lightOnTA = sphere.getAppearance().getTransparencyAttributes();
        if (light.getEnable()) {
            lightOnTA = new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.05f);
        }
        sphere.getAppearance().setTransparencyAttributes(lightOnTA);

        //transform3d to set the position of the light
        Transform3D lightT3D = new Transform3D();
        lightT3D.setTranslation(position);
        lightTG.setTransform(lightT3D);

        //add lightbulb to transform group
        lightTG.addChild(sphere);
        lightTG.addChild(light);


        lightbulbTG.addChild(lightTG);
    }

    /**
     * method to get the TransformGroup from Lightbulb
     * @return TransformGroup
     */
    public TransformGroup getTransformGroup(){
        return lightbulbTG;
    }


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

}
