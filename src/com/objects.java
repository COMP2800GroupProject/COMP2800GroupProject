package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Vector3f;

import java.io.FileNotFoundException;

public class objects {

    static SharedGroup soundObject() {
        SharedGroup sg = new SharedGroup();
        Transform3D translate = new Transform3D();
        translate.setTranslation(new Vector3f(new Vector3f(7.35f, 2f, 9.85f)));
        TransformGroup objectTG = new TransformGroup(translate);
        objectTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objectTG.addChild(loadShape(0.10f));
        sg.addChild(objectTG);
        return sg;
    }

    private static Appearance setApp(Color3f clr) {
        Appearance app = new Appearance();
        app.setMaterial(setMaterial(clr));
        ColoringAttributes colorAtt = new ColoringAttributes();
        colorAtt.setColor(clr);
        app.setColoringAttributes(colorAtt);
        return app;
    }

    /**
     * Given function by the professor
     * returns material properties
     *
     * @param color Ambient color without lighting
     * @return material containing set properties
     */
    static Material setMaterial(Color3f color) {
        int SH = 128;
        Material ma = new Material();
        ma.setAmbientColor(color);
        ma.setEmissiveColor(new Color3f(0f, 0f, 0f));
        ma.setDiffuseColor(new Color3f(0.6f, 0.6f, 0.6f));
        ma.setSpecularColor(new Color3f(1, 1, 1));
        ma.setShininess(SH);
        ma.setLightingEnable(true);
        return ma;
    }

    /**
     * Function take from professor
     *
     * @param scale    scale of tg
     * @return TransformGroup with the code object
     */
    private static TransformGroup loadShape(final float scale) {

        int flags = ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY;

        ObjectFile f = new ObjectFile(flags, (float) (60 * Math.PI / 180.0f));

        Scene s = null;

        try {
            s = f.load("images/door_knob_smooth (1).obj");
        } catch (FileNotFoundException | ParsingErrorException | IncorrectFormatException e) {
            System.err.println("testing" + e);
            System.exit(1);
        }

        BranchGroup objBG = s.getSceneGroup();
        Shape3D table_cow_shape = (Shape3D) objBG.getChild(0);
        TransformGroup comp_TG = Room.getScaledTransformGroup(new TransformGroup(), scale);
        comp_TG.addChild(objBG);
        table_cow_shape.setAppearance(setApp(Commons.Black));
        return comp_TG;

    }


}
