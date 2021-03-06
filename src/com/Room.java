package com;

import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.GeometryInfo;
import org.jogamp.java3d.utils.geometry.NormalGenerator;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.universe.ViewingPlatform;
import org.jogamp.vecmath.*;

import java.io.FileNotFoundException;

public class Room {

    private final static float BAR_HALF_WIDTH = 0.005f;
    private final static float CURTAIN_HEIGHT = 0.05f;

    static TransformGroup createRoom(float scale){
        float[] distances = {50f * scale, 70f * scale, 120f *scale};
        Switch s = new Switch();
        s.setCapability(Switch.ALLOW_SWITCH_WRITE);
        TransformGroup tg = new TransformGroup();
        tg.addChild(new Link(createFloor(scale)));
        tg.addChild(new Link(createCeiling(scale)));
        tg.addChild(new Link(createNorthWall(scale)));
        tg.addChild(new Link(createEastWall(scale)));
        tg.addChild(new Link(createWestWall(scale)));
        tg.addChild(new Link(createSouthWall(scale)));
        tg.addChild(new Link(createBars(scale)));
        tg.addChild(new Link(createWindows(scale)));
        tg.addChild(new Link(createDoors(scale)));
        tg.addChild(new Link(createCurtains(scale)));
        tg.addChild(new Link(createExteriorWalls(scale)));

        DistanceLOD lod = new DistanceLOD(distances);
        lod.addSwitch(s);
        lod.setSchedulingBounds(new BoundingSphere(new Point3d(10f, 2.5f, 10f), Double.MAX_VALUE));
        tg.addChild(lod);
        tg.addChild(s);
        return tg;
    }

    /**
     * @return is a SharedGroup containing the floor object, already textured.
     */
    static SharedGroup createCeiling(float scale) {
        Point3f[] vertices = {
                new Point3f(-0.5f, 0.5f, 0),
                new Point3f(-0.5f, 0.5f, 3f),
                new Point3f(2.5f, 0.5f, 3f),
                new Point3f(2.5f, 0.5f, 0)
        };

        Shape3D ceiling = new Shape3D();
        ceiling.setGeometry(getTextureQuadArray(vertices));
        ceiling.setAppearance(getAppearance("ceilingtile.jpg", Commons.White));

        /* Scaling */
        SharedGroup sg = new SharedGroup();
        TransformGroup tg = new TransformGroup();
        tg.addChild(ceiling);
        sg.addChild(getScaledTransformGroup(tg, scale));
        return sg;
    }

    static SharedGroup createFloor(float scale) {
        Point3f[] vertices = {
                new Point3f(-0.5f, 0f, 0),
                new Point3f(-0.5f, 0f, 3f),
                new Point3f(2.5f, 0f, 3f),
                new Point3f(2.5f, 0f, 0)
        };

        Shape3D floor = new Shape3D();
        floor.setGeometry(getTextureQuadArray(vertices));
        floor.setAppearance(getAppearance("graycarpet.jpg", Commons.Grey));

        /* Scaling */
        SharedGroup sg = new SharedGroup();
        TransformGroup tg = new TransformGroup();
        tg.addChild(floor);
        sg.addChild(getScaledTransformGroup(tg, scale));
        return sg;
    }

    /**
     * @return is a SharedGroup containing the NorthWall object, textured.
     */
    static SharedGroup createNorthWall(float scale) {
        Shape3D[] wallPieces = new Shape3D[4];
        Point3f[][] vertices = {
                {//Wall0
                        new Point3f(0, 0.5f, 0),
                        new Point3f(1, 0.5f, 0),
                        new Point3f(1, 0.42f, 0),
                        new Point3f(0, 0.42f, 0)
                },
                {//Wall1
                        new Point3f(0, 0.14f, 0),
                        new Point3f(1, 0.14f, 0),
                        new Point3f(1, 0, 0),
                        new Point3f(0, 0, 0)
                },
                {//Wall2
                        new Point3f(0, 0.42f, 0),
                        new Point3f(0.41f, 0.42f, 0),
                        new Point3f(0.41f, 0.14f, 0),
                        new Point3f(0, 0.01f, 0)
                },
                {//Wall3
                        new Point3f(0.59f, 0.42f, 0),
                        new Point3f(1, 0.42f, 0),
                        new Point3f(1, 0.14f, 0),
                        new Point3f(0.59f, 0.14f, 0)
                },

        };

        /* Scaling */
        SharedGroup sg = new SharedGroup();
        sg.addChild(getScaledTransformGroup(createWall(wallPieces, vertices), scale));
        return sg;
    }

    /**
     * @return is a SharedGroup containing the EastWall, textured.
     */
    static SharedGroup createEastWall(float scale) {
        Point3f[] vertices = {
                new Point3f(1, 0, 0),
                new Point3f(1, 0, 1),
                new Point3f(1, 0.5f, 1),
                new Point3f(1, 0.5f, 0)
        };

        Shape3D wall = new Shape3D();
        wall.setGeometry(getTextureQuadArray(vertices));
        wall.setAppearance(getAppearance("wall.jpg", Commons.Red));

        /* Scaling */
        SharedGroup sg = new SharedGroup();
        TransformGroup tg = new TransformGroup();
        tg.addChild(wall);
        sg.addChild(getScaledTransformGroup(tg, scale));
        return sg;
    }

    static SharedGroup createExteriorWalls(float scale){
        Point3f[][] vertices = {

                {//wall1
                        new Point3f(2.5f, 0.5f, 1f),
                        new Point3f(2.5f, 0.5f, 3f),
                        new Point3f(2.5f, 0f, 3f),
                        new Point3f(2.5f, 0f, 1f)
                },
                {//wall2
                        new Point3f(2.5f, 0.5f, 3f),
                        new Point3f(-0.5f, 0.5f, 3f),
                        new Point3f(-0.5f, 0f, 3f),
                        new Point3f(2.5f, 0f, 3f)
                },
                {//wall3
                        new Point3f(-0.5f, 0.5f, 3f),
                        new Point3f(-0.5f, 0.5f, 0f),
                        new Point3f(-0.5f, 0f, 0f),
                        new Point3f(-0.5f, 0f, 3f)
                },
                {//wall4
                        new Point3f(-0.5f, 0.5f, 0f),
                        new Point3f(0f, 0.5f, 0f),
                        new Point3f(0f, 0f, 0f),
                        new Point3f(-0.5f, 0f, 0f)
                }
        };

        SharedGroup sg = new SharedGroup();

        for(Point3f[] verts : vertices){
            sg.addChild(getScaledTransformGroup(createFullWall(verts), scale));
        }
        return sg;
    }

    /**
     * @return is a SharedGroup containing the EastWall, textured.
     */
    static TransformGroup createFullWall(Point3f[] vertices) {
        Shape3D wall = new Shape3D();
        wall.setGeometry(getTextureQuadArray(vertices));
        wall.setAppearance(getAppearance("wall.jpg", Commons.White));

        /* Scaling */
        SharedGroup sg = new SharedGroup();
        TransformGroup tg = new TransformGroup();
        tg.addChild(wall);
        return tg;
    }

    /**
     * @param scale
     * @return
     */
    static SharedGroup createSouthWall(float scale) {
        Shape3D[] wallPieces = new Shape3D[2];
        Point3f[][] vertices = {
                {// thin south wall
                        new Point3f(1f, 0.5f, 1f),
                        new Point3f(0.95f, 0.5f, 1f),
                        new Point3f(0.95f, 0f, 1f),
                        new Point3f(1f, 0f, 1f)
                },
                {// less thin south wall
                        new Point3f(0.2f, 0.5f, 1f),
                        new Point3f(0f, 0.5f, 1f),
                        new Point3f(0f, 0f, 1f),
                        new Point3f(0.2f, 0f, 1f),
                }
        };


        /* Scaling */
        SharedGroup sg = new SharedGroup();
        sg.addChild(getScaledTransformGroup(createWall(wallPieces, vertices), scale));
        return sg;
    }

    /**
     * @param scale
     * @return
     */
    static SharedGroup createWestWall(float scale) {
        Shape3D[] wallPieces = new Shape3D[4];
        Point3f[][] vertices = {
                {//Wall0
                        new Point3f(0f, 0.5f, 1f),
                        new Point3f(0f, 0.5f, 0f),
                        new Point3f(0f, 0.46f, 0f),
                        new Point3f(0f, 0.46f, 1f)
                },
                {//Wall1
                        new Point3f(0f, 0.14f, 1f),
                        new Point3f(0f, 0.14f, 0f),
                        new Point3f(0f, 0f, 0f),
                        new Point3f(0f, 0f, 1f)
                },
                {//Wall2
                        new Point3f(0, 0.46f, 1),
                        new Point3f(0f, 0.46f, 0.96f),
                        new Point3f(0f, 0.14f, 0.96f),
                        new Point3f(0, 0.14f, 1)
                },
                {//Wall3
                        new Point3f(0f, 0.46f, 0.72f),
                        new Point3f(0, 0.46f, 0),
                        new Point3f(0, 0.14f, 0),
                        new Point3f(0, 0.14f, 0.72f)
                },

        };
        /* Scaling */
        SharedGroup sg = new SharedGroup();
        sg.addChild(getScaledTransformGroup(createWall(wallPieces, vertices), scale));
        return sg;
    }

    private static TransformGroup createWall(Shape3D[] wallPieces, Point3f[][] vertices) {
        TransformGroup tg = new TransformGroup();
        for (int i = 0; i < wallPieces.length; i++) {
            wallPieces[i] = new Shape3D();
            wallPieces[i].setGeometry(getTextureQuadArray(vertices[i]));
            wallPieces[i].setAppearance(getAppearance("wall.jpg", Commons.Blue));
            tg.addChild(wallPieces[i]);
        }
        return tg;
    }

    static SharedGroup createBars(float scale) {
        SharedGroup sg = new SharedGroup();

        //South bars (Horizontal)
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.95f, 0.5f, 1), new Point3f(0.2f, 0.5f, 1), true), scale)); //top
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.95f, 0.45f, 1), new Point3f(0.2f, 0.45f, 1), true), scale)); //middle
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.70f, 0f, 1), new Point3f(0.2f, 0f, 1), true), scale)); //bottom

        // South bars (Vertical)
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.95f, 0f, 1), new Point3f(0.95f, 0.5f, 1), false), scale)); //left
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.70f, 0f, 1), new Point3f(0.7f, 0.5f, 1), false), scale)); //middle left
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.45f, 0f, 1), new Point3f(0.45f, 0.5f, 1), false), scale)); //middle right
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.2f, 0f, 1), new Point3f(0.2f, 0.5f, 1), false), scale)); //right

        // North window bars (horizontal)
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.41f, 0.42f - BAR_HALF_WIDTH, 0), new Point3f(0.59f, 0.42f - BAR_HALF_WIDTH, 0), true), scale)); //top
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.41f, 0.14f + BAR_HALF_WIDTH, 0), new Point3f(0.59f, 0.14f + BAR_HALF_WIDTH, 0), true), scale)); //bottom

        // North window bars (vertical)
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.41f + BAR_HALF_WIDTH, 0.42f - 2 * BAR_HALF_WIDTH, 0), new Point3f(0.41f + BAR_HALF_WIDTH, 0.14f + 2 * BAR_HALF_WIDTH, 0), false), scale)); //left
        sg.addChild(getScaledTransformGroup(createBar(new Point3f(0.59f - BAR_HALF_WIDTH, 0.42f - 2 * BAR_HALF_WIDTH, 0), new Point3f(0.59f - BAR_HALF_WIDTH, 0.14f + 2 * BAR_HALF_WIDTH, 0), false), scale)); //right

        // West window bars ()
        TransformGroup tg = new TransformGroup();
        Transform3D t3d = new Transform3D();
        tg.addChild(getScaledTransformGroup(createBar(new Point3f(0.72f, 0.46f - BAR_HALF_WIDTH, 0), new Point3f(0.96f, 0.46f - BAR_HALF_WIDTH, 0), true), scale));//top
        tg.addChild(getScaledTransformGroup(createBar(new Point3f(0.72f, 0.14f + BAR_HALF_WIDTH, 0), new Point3f(0.96f, 0.14f + BAR_HALF_WIDTH, 0), true), scale));//bottom
        // West window bars (vertical)
        tg.addChild(getScaledTransformGroup(createBar(new Point3f(0.72f + BAR_HALF_WIDTH, 0.46f - 2 * BAR_HALF_WIDTH, 0), new Point3f(0.72f + BAR_HALF_WIDTH, 0.14f + 2 * BAR_HALF_WIDTH, 0), false), scale)); //left
        tg.addChild(getScaledTransformGroup(createBar(new Point3f(0.96f - BAR_HALF_WIDTH, 0.46f - 2 * BAR_HALF_WIDTH, 0), new Point3f(0.96f - BAR_HALF_WIDTH, 0.14f + 2 * BAR_HALF_WIDTH, 0), false), scale)); //right
        sg.addChild(tg);
        t3d.setRotation(new AxisAngle4d(0d, 0.5d, 0d, -Math.PI / 2));
        tg.setTransform(t3d);

        return sg;
    }

    private static TransformGroup createBar(Point3f start, Point3f end, boolean horizontal) {
        float half_width = 0.005f;
        TransformGroup tg = new TransformGroup();
        Shape3D sides[] = new Shape3D[6];
        float startX = start.x, startY = start.y, startZ = start.z;
        float endX = end.x, endY = end.y, endZ = end.z;
        Point3f tmp[];
        if (horizontal)
            tmp = new Point3f[]{
                    new Point3f(startX, startY + half_width, startZ - half_width),
                    new Point3f(startX, startY + half_width, startZ + half_width),
                    new Point3f(startX, startY - half_width, startZ + half_width),
                    new Point3f(startX, startY - half_width, startZ - half_width),
                    new Point3f(endX, endY + half_width, endZ + half_width),
                    new Point3f(endX, endY + half_width, endZ - half_width),
                    new Point3f(endX, endY - half_width, endZ - half_width),
                    new Point3f(endX, endY - half_width, endZ + half_width)
            };
        else
            tmp = new Point3f[]{
                    new Point3f(startX - half_width, startY, startZ - half_width),
                    new Point3f(startX + half_width, startY, startZ - half_width),
                    new Point3f(startX + half_width, startY, startZ + half_width),
                    new Point3f(startX - half_width, startY, startZ + half_width),
                    new Point3f(endX + half_width, endY, endZ - half_width),
                    new Point3f(endX - half_width, endY, endZ - half_width),
                    new Point3f(endX - half_width, endY, endZ + half_width),
                    new Point3f(endX + half_width, endY, endZ + half_width)

            };
        sides[0] = new Shape3D();
        sides[0].setGeometry(getTextureQuadArray(new Point3f[]{tmp[0], tmp[1], tmp[2], tmp[3]}));
        sides[1] = new Shape3D();
        sides[1].setGeometry(getTextureQuadArray(new Point3f[]{tmp[4], tmp[5], tmp[6], tmp[7]}));
        sides[2] = new Shape3D();
        sides[2].setGeometry(getTextureQuadArray(new Point3f[]{tmp[1], tmp[4], tmp[7], tmp[2]}));
        sides[3] = new Shape3D();
        sides[3].setGeometry(getTextureQuadArray(new Point3f[]{tmp[5], tmp[0], tmp[3], tmp[6]}));
        sides[4] = new Shape3D();
        sides[4].setGeometry(getTextureQuadArray(new Point3f[]{tmp[0], tmp[5], tmp[4], tmp[1]}));
        sides[5] = new Shape3D();
        sides[5].setGeometry(getTextureQuadArray(new Point3f[]{tmp[2], tmp[7], tmp[6], tmp[3]}));
        for (Shape3D side : sides) {
            side.setAppearance(getAppearance("metal.png", Commons.Black));
            tg.addChild(side);
        }
        return tg;
    }

    static SharedGroup createWindows(float scale) {
        SharedGroup sg = new SharedGroup();
        sg.addChild(getScaledTransformGroup(createWindow(new Point3f[]{new Point3f(0.70f, 0.45f, 1f), new Point3f(0.45f, 0.45f, 1f), new Point3f(0.45f, 0f, 1f), new Point3f(0.70f, 0, 1)}), scale));
        sg.addChild(getScaledTransformGroup(createWindow(new Point3f[]{new Point3f(0.45f, 0.45f, 1f), new Point3f(0.20f, 0.45f, 1f), new Point3f(0.2f, 0f, 1f), new Point3f(0.45f, 0, 1)}), scale));
        sg.addChild(getScaledTransformGroup(createWindow(new Point3f[]{new Point3f(0.95f, 0.5f, 1f), new Point3f(0.7f, 0.5f, 1f), new Point3f(0.7f, 0.45f, 1f), new Point3f(0.95f, 0.45f, 1)}), scale));
        sg.addChild(getScaledTransformGroup(createWindow(new Point3f[]{new Point3f(0.70f, 0.5f, 1f), new Point3f(0.45f, 0.5f, 1f), new Point3f(0.45f, 0.45f, 1f), new Point3f(0.70f, 0.45f, 1)}), scale));
        sg.addChild(getScaledTransformGroup(createWindow(new Point3f[]{new Point3f(0.45f, 0.5f, 1f), new Point3f(0.2f, 0.5f, 1f), new Point3f(0.2f, 0.45f, 1f), new Point3f(0.45f, 0.45f, 1f)}), scale));
        sg.addChild(getScaledTransformGroup(createWindow(new Point3f[]{new Point3f(0.41f, 0.42f, 0f), new Point3f(0.59f, 0.42f, 0f), new Point3f(0.59f, 0.14f, 0f), new Point3f(0.41f, 0.14f, 0f)}), scale));
        sg.addChild(getScaledTransformGroup(createWindow(new Point3f[]{new Point3f(0f, 0.46f, 0.96f), new Point3f(0f, 0.46f, 0.72f), new Point3f(0f, 0.14f, 0.72f), new Point3f(0f, 0.14f, 0.96f)}), scale));
        return sg;
    }


    private static TransformGroup createWindow(Point3f point[]) {

        TransformGroup tg = new TransformGroup();

        Appearance appearance = new Appearance();
        TransparencyAttributes trans = new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.75f);
        appearance.setTransparencyAttributes(trans);
        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        appearance.setPolygonAttributes(pa);

        Shape3D shape = new Shape3D();
        shape.setGeometry(getColorArray(point));
        shape.setAppearance(appearance);
        tg.addChild(shape);
        return tg;
    }


    /**
     * Returns a QuadArray with Coordinates and TextureCoordinates already set up.
     *
     * @param vertices are the four vertices of the shape.
     * @return is a QuadArray with Coordinates and TextureCoordinates already set up.
     */
    private static QuadArray getColorArray(Point3f[] vertices) {
        QuadArray quadArray = new QuadArray(4, GeometryArray.COORDINATES | GeometryArray.COLOR_4);
        quadArray.setCoordinates(0, vertices);
        for (int i = 0; i < 4; i++)
            quadArray.setColor(i, new Color4b((byte) 255, (byte) 255, (byte) 255, (byte) 255));
        return quadArray;
    }


    /**
     * Returns a QuadArray with Coordinates and TextureCoordinates already set up.
     *
     * @param vertices are the four vertices of the shape.
     * @return is a QuadArray with Coordinates and TextureCoordinates already set up.
     */
    private static QuadArray getTextureQuadArray(Point3f[] vertices) {
        QuadArray quadArray = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
        quadArray.setCoordinates(0, vertices);
        TexCoord2f texCoord = new TexCoord2f(0, 1);
        quadArray.setTextureCoordinate(0, 0, texCoord);
        texCoord.set(0, 0);
        quadArray.setTextureCoordinate(0, 1, texCoord);
        texCoord.set(1, 0);
        quadArray.setTextureCoordinate(0, 2, texCoord);
        texCoord.set(1, 1);
        quadArray.setTextureCoordinate(0, 3, texCoord);

        //for lighting
        //generate normals for the textured quadarray
        NormalGenerator ng = new NormalGenerator();
        GeometryInfo gi = new GeometryInfo(quadArray);

        ng.generateNormals(gi);

        quadArray.setNormals(0, gi.getNormals());

        return quadArray;
    }

    static SharedGroup createDoors(float scale) {
        SharedGroup sg = new SharedGroup();
        sg.addChild(getScaledTransformGroup(createDoor(new Point3f[]{new Point3f(0.945f, 0.45f, 1.005f), new Point3f(0.705f, 0.45f, 1.005f), new Point3f(0.705f, 0f, 0.995f), new Point3f(0.945f, 0f, 0.995f)}), scale));
        sg.addChild(getScaledTransformGroup(createDoor(new Point3f[]{new Point3f(0.705f, 0.45f, 0.995f), new Point3f(0.945f, 0.45f, 0.995f), new Point3f(0.945f, 0f, 0.995f), new Point3f(0.705f, 0f, 0.995f)}), scale));
        return sg;
    }


    private static TransformGroup createDoor(Point3f[] vertices) {
        TransformGroup tg = new TransformGroup();
        Shape3D shape = new Shape3D();
        shape.setGeometry(getTextureQuadArray(vertices));
        Appearance app = new Appearance();
        app.setTexture(textureApp("door.jpg"));

        shape.setAppearance(app);
        tg.addChild(shape);
        return tg;
    }

    static SharedGroup createCurtains(float scale){
        Point3f[] positions = {
                new Point3f(6.95f, 5f, 8.8f),
                new Point3f(4.45f, 5f, 8.8f),
                new Point3f(1.12f, 5f, 9.6f - BAR_HALF_WIDTH)
        };
        SharedGroup sg = new SharedGroup();

        TransformGroup[] tgs = new TransformGroup[3];
        Transform3D[] t3ds = new Transform3D[3];

        for(int i = 0; i < positions.length; i++) {
            tgs[i] = new TransformGroup();
            t3ds[i] = new Transform3D();
        }

        tgs[0].addChild(new Link(Room.createCurtain(4.4f, 2.5f - 0.1f)));
        tgs[1].addChild(new Link(Room.createCurtain(4.4f, 2.5f - 0.1f)));
        tgs[2].addChild(new Link(Room.createCurtain(3.1f, 2.4f - 0.1f)));

        t3ds[2].rotY(-Math.PI/2);

        for(int i = 0; i< t3ds.length; i++){
            t3ds[i].setTranslation(new Vector3f(positions[i]));
            tgs[i].setTransform(t3ds[i]);
            sg.addChild(tgs[i]);
        }

        return sg;
    }

    static SharedGroup createCurtain(float height, float width){
        int count = (int)(height / CURTAIN_HEIGHT) + 1;
        Appearance ap = new Appearance();
        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        ap.setPolygonAttributes(pa);

        SharedGroup sg = new SharedGroup();
        TransformGroup tg;
        Transform3D t3d = new Transform3D(), rot = new Transform3D();
        t3d.rotX(Math.PI/8);
        for(int i = 0; i < count; i++) {
            //System.err.println(new Point3f(top.getX(), top.getY() - (CURTAIN_HEIGHT * i) * 2, top.getZ()));
            tg = curtainPiece(new Point3f(-width / 2, CURTAIN_HEIGHT / 2, width / 2), width);
            t3d.setTranslation(new Vector3f(0f, 0f - (CURTAIN_HEIGHT * i), 0f));
            tg.setTransform(t3d);
            sg.addChild(tg);
        }
        return sg;
    }

    public static TransformGroup curtainPiece(Point3f top, float width) {
        QuadArray rect = new QuadArray(4, QuadArray.COLOR_3 | QuadArray.COORDINATES);

        rect.setCoordinate(0, new Point3f(top.getX() - width / 2, top.getY(), top.getZ()));
        rect.setCoordinate(1, new Point3f(top.getX() + width / 2, top.getY(), top.getZ()));
        rect.setCoordinate(2, new Point3f(top.getX() + width / 2, top.getY() - CURTAIN_HEIGHT, top.getZ()));
        rect.setCoordinate(3, new Point3f(top.getX() - width / 2, top.getY() - CURTAIN_HEIGHT, top.getZ()));

        for(int i = 0; i < 4; i++) {
            rect.setColor(i, new Color3f(255, 255, 255));
        }

        Shape3D rectangle = new Shape3D(rect);

        //shows both sides of the rectangle
        Appearance ap = new Appearance();
        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        ap.setPolygonAttributes(pa);
        rectangle.setAppearance(ap);

        TransformGroup tg = new TransformGroup();
        tg.addChild(rectangle);
        return tg;
    }


    /**
     * Returns scaled TransformGroup containing the passed Shape3D object after scale
     *
     * @param tg    is a TransformGroup, which can already have other properties
     * @param scale is a float determining the size of the scale
     * @return is a TransformGroup containing the passed Shape3D object after scale
     */
    static TransformGroup getScaledTransformGroup(TransformGroup tg, float scale) {
        Transform3D scaler = new Transform3D();
        scaler.setScale(scale);
        tg.setTransform(scaler);
        return tg;
    }


    private static Appearance getAppearance(String filename, Color3f color) {

        Texture texture = textureApp(filename);

        TextureAttributes ta = new TextureAttributes();
        ta.setTextureMode(TextureAttributes.MODULATE);

        Appearance app = new Appearance();
        app.setTexture(texture);
        app.setTextureAttributes(ta);


        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        app.setPolygonAttributes(pa);

        Color3f am = new Color3f(0.2f, 0.2f, 0.2f);
        Color3f di = new Color3f(1f, 1f, 1f);
        Color3f sp = new Color3f(1f, 1f, 1f);
        Color3f em = new Color3f(0f, 0f, 0f);

        Material mat = new Material(Commons.White, Commons.Black, sp, color, 64f);
        app.setMaterial(mat);

        return app;
    }

    /**
     * Used to apply textures to shape. See createFloor function for use example. J3 - Slide 25
     *
     * @param filename is the name of the texture image including extension
     * @return TexturedUnitState with applied texture, attributes, and coordinates
     */
    private static Texture textureApp(String filename) {
        filename = "images/" + filename;
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        if (image == null)
            System.out.println("Cannot load file:  " + filename);

        assert image != null;
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);

        return texture;

    }

}
