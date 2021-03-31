package com;

import java.io.FileNotFoundException;

import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.Group;
import org.jogamp.java3d.ImageComponent;
import org.jogamp.java3d.RotationInterpolator;
import org.jogamp.java3d.SharedGroup;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Vector3f;

public class Animations {

    static TransformGroup createFan(Vector3f position) {
        Transform3D posSet = new Transform3D();
        posSet.rotY(Math.PI / 2);
		TransformGroup sceneTG = new TransformGroup(); //Creates Transform Group
        sceneTG.setCapability(Group.ALLOW_CHILDREN_WRITE);
        posSet.setTranslation(position);
		TransformGroup fanHead = new TransformGroup(); //Creates TransfromGroup to be animated
		fanHead.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha fanHeadAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0, 10000, 0, 400, 10000, 0, 400); //alpha 0-1 20 seconds
		Transform3D trans3D = new Transform3D();
		TransformGroup fanBody = new TransformGroup(); //Creates stationery transformgroup
		ObjectFile obj = new ObjectFile(ObjectFile.TRIANGULATE); //add .obj files to respective transform groups
		ObjectFile obj2 = new ObjectFile(ObjectFile.TRIANGULATE);
		try {
            fanHead.addChild(obj.load("images/fan_head.obj").getSceneGroup());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IncorrectFormatException e) {
            e.printStackTrace();
        } catch (ParsingErrorException e) {
            e.printStackTrace();
        }
		try {
            fanBody.addChild(obj2.load("images/fan_body.obj").getSceneGroup());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IncorrectFormatException e) {
            e.printStackTrace();
        } catch (ParsingErrorException e) {
            e.printStackTrace();
        }
		RotationInterpolator fanHeadRot = new RotationInterpolator(fanHeadAlpha, fanHead, trans3D, (float)(- Math.PI/3), (float)( Math.PI/3));
        BoundingSphere zone = new BoundingSphere();
        fanHeadRot.setSchedulingBounds(zone);
        sceneTG.setTransform(posSet);
        sceneTG.addChild(fanHead);
		sceneTG.addChild(fanBody);
        fanHead.addChild(fanHeadRot);
		return sceneTG;
	}

    static TransformGroup createGlobe(Vector3f position) {
        Transform3D posSet = new Transform3D();
        posSet.setTranslation(position);
        TransformGroup sceneTG = new TransformGroup();
        TransformGroup globeGroup = new TransformGroup();
        globeGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        sceneTG.setCapability(Group.ALLOW_CHILDREN_WRITE);
        globeGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        posSet.setTranslation(position);
        sceneTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Alpha earthRotAlpha = new Alpha(-1, 60000);
        globeGroup.addChild(earth());
        RotationInterpolator earthRotator = new RotationInterpolator(earthRotAlpha, globeGroup);
        BoundingSphere zone = new BoundingSphere();
        earthRotator.setSchedulingBounds(zone);
        sceneTG.setTransform(posSet);
        sceneTG.addChild(globeGroup);
        globeGroup.addChild(earthRotator);
        return sceneTG;
    }

    static Sphere earth() {
        String file = "images/earth.jpg";
        TextureLoader loader = new TextureLoader(file, null);
        ImageComponent image = loader.getImage();
        if (image == null)
            System.out.println("Cannot load file: " + file);

        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        Appearance app = new Appearance();
        app.setTexture(texture);
        Sphere earthSphere = new Sphere(0.3f, Primitive.GENERATE_TEXTURE_COORDS, 90, app);
        return earthSphere;
    }
}

    

