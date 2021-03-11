package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;

import java.awt.*;

import static com.Main.setMaterial;

public class Lightbulb {

    TransformGroup sceneTG;

    Color3f color;
    Vector3f position;
    Sphere sphere;
    Light light;
    Boolean on;


    /**
     * default constructor
     */
    public Lightbulb(TransformGroup sceneTG, Color3f color, Vector3f position, Boolean on) {

        this.sceneTG = sceneTG;
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

        BoundingSphere myBounds = new BoundingSphere(new Point3d(), 1000);

        //create a spotlight facing downwards
        SpotLight spotLight = new SpotLight();
        spotLight.setColor(new Color3f(1f, 1f, 1f));
        spotLight.setPosition(new Point3f(0, 0f, 0));
        spotLight.setDirection(0, -1, 0);                 //direction of light
        spotLight.setAttenuation(1, 0, 0);

        //light on by default
        spotLight.setEnable(on);

        //set bounds of the light
        spotLight.setInfluencingBounds(myBounds);

        return spotLight;

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

        sceneTG.addChild(lightTG);

    }

}
