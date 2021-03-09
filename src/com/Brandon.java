package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.TexCoord2f;

public class Brandon {
    /**
     * @return is a SharedGroup containing the floor object, already textured.
     */
    public static SharedGroup createCeiling(float scale){
        Point3f[] vertices = {
                new Point3f(0, 0.5f, 0),
                new Point3f(1, 0.5f, 0),
                new Point3f(1, 0.5f, 1),
                new Point3f(0, 0.5f, 1)
        };

        Shape3D ceiling = new Shape3D();
        ceiling.setGeometry(getQuadArray(vertices));
        ceiling.setAppearance(getAppearance("ceilingtile.jpg"));

        /** Scaling */
        SharedGroup sg = new SharedGroup();
        TransformGroup tg = new TransformGroup();
        tg.addChild(ceiling);
        sg.addChild(getScaledTransformGroup(tg, scale));
        return sg;
    }

    /**
     * @return is a SharedGroup containing the floor object, already textured.
     */
    public static SharedGroup createFloor(float scale){
        Point3f[] vertices = {
                new Point3f(0, 0, 0),
                new Point3f(1, 0, 0),
                new Point3f(1, 0, 1),
                new Point3f(0, 0, 1)
        };

        Shape3D floor = new Shape3D();
        floor.setGeometry(getQuadArray(vertices));
        floor.setAppearance(getAppearance("carpet.jpg"));

        /** Scaling */
        SharedGroup sg = new SharedGroup();
        TransformGroup tg = new TransformGroup();
        tg.addChild(floor);
        sg.addChild(getScaledTransformGroup(tg, scale));
        return sg;
    }

    /**
     * @return is a SharedGroup containing the NorthWall object, textured.
     */
    public static SharedGroup createNorthWall(float scale){
        Shape3D[] wallPieces = new Shape3D[4];

        Point3f[][] vertices = {
                {//nWall0
                        new Point3f(0, 0.5f, 0),
                        new Point3f(1, 0.5f, 0),
                        new Point3f(1, 0.42f, 0),
                        new Point3f(0, 0.42f, 0)
                },
                {//nWall1
                        new Point3f(0, 0.14f, 0),
                        new Point3f(1, 0.14f, 0),
                        new Point3f(1, 0, 0),
                        new Point3f(0, 0, 0)
                },
                {//nWall2
                        new Point3f(0, 0.42f, 0),
                        new Point3f(0.41f, 0.42f, 0),
                        new Point3f(0.41f, 0.14f, 0),
                        new Point3f(0, 0.01f, 0)
                },
                {//nWall3
                        new Point3f(0.59f, 0.42f, 0),
                        new Point3f(1, 0.42f, 0),
                        new Point3f(1, 0.14f, 0),
                        new Point3f(0.59f, 0.14f, 0)
                },

        };

        TransformGroup tg = new TransformGroup();
        for(int i = 0; i < wallPieces.length; i++){
            wallPieces[i] = new Shape3D();
            wallPieces[i].setGeometry(getQuadArray(vertices[i]));
            wallPieces[i].setAppearance(getAppearance("wall.jpg"));
            tg.addChild(wallPieces[i]);
        }

        /** Scaling */
        SharedGroup sg = new SharedGroup();
        sg.addChild(getScaledTransformGroup(tg, scale));
        return sg;
    }

    /**
     * @return is a SharedGroup containing the EastWall, textured.
     */
    public static SharedGroup createEastWall(float scale){
        Point3f[] vertices = {
                new Point3f(1, 0, 0),
                new Point3f(1, 0, 1),
                new Point3f(1, 0.5f, 1),
                new Point3f(1, 0.5f, 0)
        };

        Shape3D wall = new Shape3D();
        wall.setGeometry(getQuadArray(vertices));
        wall.setAppearance(getAppearance("wall.jpg"));

        /** Scaling */
        SharedGroup sg = new SharedGroup();
        TransformGroup tg = new TransformGroup();
        tg.addChild(wall);
        sg.addChild(getScaledTransformGroup(tg, scale));
        return sg;
    }

    /**
     * Returns scaled TransformGroup containing the passed Shape3D object after scale
     * @param tg is a TransformGroup, which can already have other properties
     * @param scale is a float determining the size of the scale
     * @return is a TransformGroup containing the passed Shape3D object after scale
     */
    private static TransformGroup getScaledTransformGroup(TransformGroup tg, float scale){
        Transform3D scaler = new Transform3D();
        scaler.setScale(scale);
        tg.setTransform(scaler);
        return tg;
    }

    /**
     * Returns a QuadArray with Coordinates and TextureCoordinates already set up.
     * @param vertices are the four vertices of the shape.
     * @return is a QuadArray with Coordinates and TextureCoordinates already set up.
     */
    private static QuadArray getQuadArray(Point3f[] vertices){
        QuadArray quadArray = new QuadArray(4, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);

        quadArray.setCoordinates(0, vertices);
        TexCoord2f texCoord = new TexCoord2f(0,1);
        quadArray.setTextureCoordinate(0, 0, texCoord);
        texCoord.set(0, 0);  quadArray.setTextureCoordinate(0, 1, texCoord);
        texCoord.set(1, 0);  quadArray.setTextureCoordinate(0, 2, texCoord);
        texCoord.set(1, 1);  quadArray.setTextureCoordinate(0, 3, texCoord);

        return quadArray;
    }

    private static Appearance getAppearance(String filename){
        Appearance appearance = new Appearance();
        TextureUnitState[] tus = new TextureUnitState[1];
        TexCoordGeneration tcg = new TexCoordGeneration();
        tcg.setEnable(false);

        TextureAttributes ta = new TextureAttributes();
        ta.setTextureMode(TextureAttributes.MODULATE);

        tus[0] = texState(filename, ta, tcg);
        PolygonAttributes pa = new PolygonAttributes();
        //todo cull bottom of floor for efficiency
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        appearance.setPolygonAttributes(pa);

        appearance.setTextureUnitState(tus);
        return appearance;
    }

    /**
     * Used to apply textures to shape. See createFloor function for use example. J3 - Slide 25
     * @param filename is the name of the texture image including extension
     * @param ta TextureAttribute
     * @param tcg TexCoordGeneration
     * @return TexturedUnitState with applied texture, attributes, and coordinates
     */
    private static TextureUnitState texState(String filename, TextureAttributes ta, TexCoordGeneration tcg){
        filename = "images/" + filename;
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();

        if(image == null) System.err.println("Failed to load texture " + filename);

        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);

        TextureUnitState state = new TextureUnitState(texture, ta, tcg);
        state.setCapability(TextureUnitState.ALLOW_STATE_WRITE);
        return state;
    }

}
