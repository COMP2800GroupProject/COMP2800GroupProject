package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.TexCoord2f;

public class Brandon {
    /**
     * @return is a SharedGroup containing the floor object, already textured.
     */
    public static SharedGroup createFloor(){

        /** Geometry Code Begins **/

        QuadArray floorArray = new QuadArray(4, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);

        Point3f[] vertices = {
                new Point3f(0, 0, 0),
                new Point3f(1, 0, 0),
                new Point3f(1, 0, 1),
                new Point3f(0, 0, 1)
        };

        floorArray.setCoordinates(0, vertices);
        TexCoord2f texCoord = new TexCoord2f(0,1);
        floorArray.setTextureCoordinate(0, 0, texCoord);
        texCoord.set(0, 0);  floorArray.setTextureCoordinate(0, 1, texCoord);
        texCoord.set(1, 0);  floorArray.setTextureCoordinate(0, 2, texCoord);
        texCoord.set(1, 1);  floorArray.setTextureCoordinate(0, 3, texCoord);

        /** Appearance Code Begins **/

        Appearance appearance = new Appearance();
        TextureUnitState[] tus = new TextureUnitState[1];
        TexCoordGeneration tcg = new TexCoordGeneration();
        tcg.setEnable(false);

        TextureAttributes ta = new TextureAttributes();
        ta.setTextureMode(TextureAttributes.MODULATE);

        tus[0] = texState("carpet.jpg", ta, tcg);
        PolygonAttributes pa = new PolygonAttributes();
        //todo cull bottom of floor for efficiency
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        appearance.setPolygonAttributes(pa);

        appearance.setTextureUnitState(tus);

        /** Object Code **/
        Shape3D floor = new Shape3D();
        floor.setGeometry(floorArray);
        floor.setAppearance(appearance);

        SharedGroup sg = new SharedGroup();
        sg.addChild(floor);
        return sg;
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
