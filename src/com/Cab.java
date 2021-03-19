package com;

import java.awt.GraphicsConfiguration;
import java.awt.event.MouseListener;
import java.util.Iterator;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.ColoringAttributes;
import org.jogamp.java3d.Geometry;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.IndexedLineArray;
import org.jogamp.java3d.IndexedQuadArray;
import org.jogamp.java3d.LineArray;
import org.jogamp.java3d.Link;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.PolygonAttributes;
import org.jogamp.java3d.QuadArray;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.SharedGroup;
import org.jogamp.java3d.TexCoordGeneration;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.TextureAttributes;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.TransparencyAttributes;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnCollisionEntry;
import org.jogamp.java3d.WakeupOnCollisionExit;
import org.jogamp.java3d.utils.geometry.ColorCube;
import org.jogamp.java3d.utils.geometry.Primitive;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.TexCoord2f;
import org.jogamp.vecmath.TexCoord3f;
import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;

import com.jogamp.newt.event.MouseEvent;

/*
	Creates a function called BuildShape() which takes string argument and returns a 3Dshape associated with string, i.e "cabinet" will build a cabinet
	Use TransformGroup and Transform3D to position and scale correctly i guess 
 */


public class Cab {
	
	
	
	private static TransformGroup tranny = null; // Global TransformGroup if model has multiple components
	private static TransformGroup tranny1 = null;
	
	
	private static Texture setTexture(String file)
	{
		
		TextureLoader loader = new TextureLoader(file, null);
		ImageComponent2D image = loader.getImage();
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		
		return texture;
		
	}
	
	
	public static Appearance app(String type, String selection) {
		
		Appearance app = new Appearance();
		
		if(type == "texture") {
			
			if(selection == "screen") app.setTexture(setTexture("images/metal.png"));
			
			
			else if(selection == "login") app.setTexture(setTexture("images/" + "MarbleTexture" + ".jpg"));
			
			else if(selection == "off") app.setTexture(setTexture("images/" + "black" + ".png"));
		
			else if(selection == "computer") app.setTexture(setTexture("images/" + "black_t" + ".jpg"));
			
			else if(selection == "chair") app.setTexture(setTexture("images/" + "black_wool" + ".jpg"));
			
			else if(selection == "shelf") app.setTexture(setTexture("images/" + "orange_t" + ".jpg"));
			
			else if(selection == "lamp_bottom") app.setTexture(setTexture("images/" + "wood5" + ".jpg"));
			
			else if(selection == "lamp_top") app.setTexture(setTexture("images/" + "beige" + ".jpg"));
			
			else if(selection == "desk") app.setTexture(setTexture("images/" + "wood5" + ".jpg"));
			
			else app.setTexture(setTexture("images/wood6.jpg"));
			
			TextureAttributes texture = new TextureAttributes();
			texture.setTextureMode(TextureAttributes.REPLACE);
			app.setTextureAttributes(texture);
			
			
			
			Vector3d scale = new Vector3d(4f,1f,1f);
			Transform3D transMap = new Transform3D();
			transMap.setScale(scale);
			texture.setTextureTransform(transMap);
		}
			
		PolygonAttributes poly = new PolygonAttributes();
		poly.setCullFace(PolygonAttributes.CULL_NONE);
		app.setPolygonAttributes(poly);
		
		
		
	return app;
	}
	
	
	private static Shape3D BuildShape(String name){ //builds shape depending on what tag is passed through
		
		Shape3D shape = new Shape3D();
		if(name == "desk") {
			shape.setGeometry(DeskDimensions());
			shape.setAppearance(app("texture", "desk"));
			return shape;
		}
			
		
		else if(name == "cabinet") {
			shape.setGeometry(CabinetDimensions());		
		}
		
		else if(name == "shelf") {
			shape.setGeometry(ShelfDimensions());
			shape.setAppearance(app("texture", "shelf"));
			return shape;
		}
		
		else if(name == "table") {
			shape.setGeometry(TableDimensions());
		}
		
		else if(name == "chair") {
			shape.setGeometry(ChairDimensions());
			shape.setAppearance(app("texture", "chair"));
			return shape;
		}
		else if(name == "lamp") {
			tranny1 = new TransformGroup();
			tranny1.addChild(new Shape3D(LampBottom(), app("texture", "lamp_bottom")));
			tranny1.addChild(new Shape3D(LampTop(), app("texture", "lamp_top")));
			return null;
		}
		else if(name == "computer") {
			float x = .25f, y = .25f, z = .025f;
			tranny = new TransformGroup();
			Shape3D screen = new Shape3D(CompooterScreen(x-.05f, y-.05f, z), app("texture", "off"));
			screen.setName("screen");
			screen.setUserData(0);
			screen.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
			tranny.addChild(new Shape3D(CompooterDimensions(x,y,z), app("texture", "computer")));
			tranny.addChild(screen);
			return null;
		}
		
		
		shape.setAppearance(app("texture", "default"));
		return shape;
		
	}
	
	
	private static Geometry CompooterScreen(float x, float y, float z) {
			IndexedQuadArray side = new IndexedQuadArray(8, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_3, 32);
			
			Point3f Coords[] = {
					new Point3f(x,y,z-.02f), new Point3f(-x,y,z-.02f), new Point3f(-x,-y,z-.02f), new Point3f(x,-y,z-.02f),
					new Point3f(x,y,z+.02f), new Point3f(-x,y,z+.02f), new Point3f(-x,-y,z+.02f), new Point3f(x,-y,z+.02f),
			};
			
			int[] indices = {
					0,1,2,3};
			
			
			side.setCoordinateIndices(0,indices);
			side.setCoordinates(0, Coords);
			return side;
	}
	
	
	
	private static Geometry CompooterDimensions(float x, float y, float z) {
		IndexedQuadArray side = new IndexedQuadArray(48, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES, 180);
		
		Point3f Coords1[] = {
				new Point3f(-x,-y,z), new Point3f(x,-y,z), new Point3f(x,y,z), new Point3f(-x,y,z),
				new Point3f(-x + 0.05f,-y +0.05f,z), new Point3f(x-0.05f,-y+0.05f,z), new Point3f(x-0.05f,y-0.05f,z), new Point3f(-x+0.05f,y-0.05f,z), 
				new Point3f(-x,-y,-z), new Point3f(x,-y,-z), new Point3f(x,y,-z), new Point3f(-x,y,-z), 
				new Point3f(-x+0.05f,-y+0.05f,-z), new Point3f(x-0.05f,-y+0.05f,-z), new Point3f(x-0.05f,y-0.05f,-z), new Point3f(-x+0.05f,y-0.05f,-z),
				new Point3f(-x+0.1f,-y+0.1f,-z-.05f), new Point3f(x-0.1f,-y+0.1f,-z-.05f), new Point3f(x-0.1f,y-0.1f,-z-.05f), new Point3f(-x+0.1f,y-0.1f,-z-.05f),
				
		};
		
		x = .1f;
		y = -.35f;
		z = .02f;
		
		Point3f Coords2[] = {
				new Point3f(x,y,z-0.05f), new Point3f(-x,y,z-0.05f), new Point3f(-x,y+.25f,z-0.05f), new Point3f(x,y+.25f,z-0.05f), 
				new Point3f(x,y,-z-0.05f), new Point3f(-x,y,-z-0.05f), new Point3f(-x,y+.25f,-z-0.05f), new Point3f(x,y+.25f,-z-0.05f), 
		};
		
		x = .2f;
		y = -.375f;
		Point3f Coords3[] = {
				new Point3f(x,y,z), new Point3f(-x,y,z), new Point3f(-x,y+0.025f,z), new Point3f(x,y+0.025f,z), 
				new Point3f(x,y,-z-0.1f), new Point3f(-x,y,-z-0.1f), new Point3f(-x,y+0.025f,-z-0.1f), new Point3f(x,y+0.025f,-z-0.1f), 
		};
		
		int[] indices = {
				0,1,5,4,1,2,6,5,2,3,7,6,3,0,4,7,
				8,9,11,10,9,10,8,11,10,11,9,8,11,8,10,9,
				0,1,9,8,1,2,10,9,2,3,11,10,3,0,8,11,
				4,5,13,12,5,6,14,13,6,7,15,14,7,4,12,15,
				16,17,19,18,17,18,16,19,18,19,17,16,19,16,18,17,
				16,17,12,13,17,18,14,13,18,19,15,14,19,16,12,15,
				20,21,22,23,24,25,26,27,21,22,26,25,22,23,27,26,23,20,24,27,
				28,29,30,31,32,33,34,35,29,30,34,33,28,31,35,32,28,29,33,32,30,31,35,34,
				};
		
		int[] tindices = {
				19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,
				19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,
				19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,
				19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,
				19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,
				19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,
				19,16,12,15,19,16,12,15,19,16,12,15,19,16,12,15,
		};
		
		TexCoord3f tc[] = new TexCoord3f[36];
		
		for(int i = 0; i < 36 ; i++) {
			
			if(i > 19 && i < 28) tc[i] = new TexCoord3f(Coords2[i - 20]);	
			
			else if(i > 27) tc[i] = new TexCoord3f(Coords3[i - 28]);
			
			else tc[i] = new TexCoord3f(Coords1[i]);
			
			side.setTextureCoordinate(0,i, tc[i]);
			
		}
		
		side.setCoordinates(0, Coords1);
		side.setCoordinates(20, Coords2);
		side.setCoordinates(28, Coords3);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, tindices);
		return side;
	}
	
	private static Geometry LampBottom() {
		IndexedQuadArray side = new IndexedQuadArray(48, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES, 180);
		
		float x = .2f;
		float y = .3f;
		float z = .025f;
		
		Point3f Coords1[] = {
				new Point3f(-x,-y,z), new Point3f(x,-y,z), new Point3f(x,y,z), new Point3f(-x,y,z),
				new Point3f(-x + 0.05f,-y + 0.1f,z), new Point3f(x-0.05f,-y+0.1f,z), new Point3f(x-0.05f,y-0.05f,z), new Point3f(-x+0.05f,y-0.05f,z),
				
		};
		
		x = .15f;
		y = .25f;
		
		Point3f Coords2[] = {
			new Point3f(-x + 0.175f,-y+0.05f,z), new Point3f(x-0.175f,-y+0.05f,z), new Point3f(x,-y+.2f,z), new Point3f(-x,-y+.2f,z),
			new Point3f(-x,-y+.4f,z), new Point3f(x,-y+.4f,z), new Point3f(x-0.175f,y+.3f,z), new Point3f(-x+0.175f,y+.3f,z),
			new Point3f(-x,-y+.35f,z), new Point3f(x,-y+.35f,z), new Point3f(-x,-y+.15f,z), new Point3f(x,-y+.15f,z),
		};
		
		
		Point3f Coords3[] = new Point3f[20];
		
		for(int i = 0; i <20; i++) {
			
			if(i <8) Coords3[i] = new Point3f(Coords1[i].x, Coords1[i].y, -Coords1[i].z);
			
				
			else Coords3[i] = new Point3f(Coords2[i-8].x, Coords2[i-8].y, -Coords2[i-8].z);
			
		}
		
		x = .25f;
		z = z + .025f;
		y = -.3f;
		
		Point3f Coords4[] = {
				new Point3f(x,y - .025f,z), new Point3f(-x,y- .025f,z), new Point3f(x,y- .025f,-z), new Point3f(-x,y- .025f,-z), 
				new Point3f(x,y,z), new Point3f(-x,y,z), new Point3f(x,y,-z), new Point3f(-x,y,-z), 
		};
		
		int[] indices = {0,1,5,4,1,2,6,5,2,3,7,6,3,0,4,7,8,9,14,15,10,11,18,19,12,13,17,16,
				20,21,25,24,21,22,26,25,22,23,27,26,23,20,24,27,28,29,34,35,30,31,38,39,32,33,37,36,
				0,1,21,20,1,2,22,21,2,3,23,22,3,0,20,23,4,5,25,24,5,6,26,25,6,7,27,26,7,4,24,27,
				9,14,34,29,8,15,35,28,10,11,31,30,18,19,39,38,12,13,33,32,16,17,37,36,
				40,41,43,42,44,45,47,46,40,41,45,44,41,42,46,45,42,43,45,46,43,40,44,47
				};
		
		int[] indice = {0,1,5,4};
		
		TexCoord3f tc[] = new TexCoord3f[48];

		for(int i = 0; i < 48; i++) {
			
			if(i >= 8 && i < 20) tc[i] = new TexCoord3f(Coords2[i-8]);
			
			else if(i >= 20 && i < 40) tc[i] = new TexCoord3f(Coords3[i-20]);		
			
			else if(i >= 40) tc[i] = new TexCoord3f(Coords4[i-40]);	
			
			else tc[i] = new TexCoord3f(Coords1[i]);	
			
			side.setTextureCoordinate(0,i, tc[i]);
			
		}
		
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indicer(indice, 180));
		side.setCoordinates(0, Coords1);
		side.setCoordinates(8, Coords2);
		side.setCoordinates(20, Coords3);
		side.setCoordinates(40, Coords4);
		
		return side;
	}
	
	private static Geometry LampTop() {
		IndexedQuadArray side = new IndexedQuadArray(44, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES, 180);
		float r = 0.4f, x, y;
		Point3f Coords[] = new Point3f[16];
		
		for (int i = 0; i < 16; i++) { 
			
			x = (float) (Math.cos(Math.PI / 180 * (360/16) * i ) * r);
			y = (float) (Math.sin(Math.PI / 180 * (360/16) * i ) * r);
			
			Coords[i] = new Point3f(x, .3f, y);
			side.setTextureCoordinate(0,i, new TexCoord3f(Coords[i]));
		}
		
		side.setCoordinates(0, Coords);
		r = .15f;
		
		for (int i = 0; i < 16; i++) { 
			
			x = (float) (Math.cos(Math.PI / 180 * (360/16) * i ) * r);
			y = (float) (Math.sin(Math.PI / 180 * (360/16) * i ) * r);
			
			Coords[i] = new Point3f(x, .7f, y);
			side.setTextureCoordinate(0,i + 16, new TexCoord3f(Coords[i]));
		}
		
		int[] indice = {15,0,16,31};
			
		int[] indices = { 0,1,17,16,1,2,18,17,2,3,19,18,3,4,20,19,4,5,21,20,5,6,22,21,6,7,23,22,7,8,24,23,8,9,25,24,9,10,26,25,10,11,27,26,11,12,28,27,12,13,29,28,13,14,30,29,14,15,31,30,15,0,16,31};
		
		
		side.setCoordinates(16, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0, 0, indicer(indice, 180));
		
		return side;
	}
	
	private static Geometry CabinetDimensions() {
		IndexedQuadArray side = new IndexedQuadArray(44, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES, 180);
		float x = .45f;
		float y = 1f;
		float z = .2f;
		
		Point3f Coords[] = {new Point3f(-x, -y, z), new Point3f(x, -y, z), new Point3f(-x, -y, -z), new Point3f(x, -y, -z),
				new Point3f(-x, -.05f, z), new Point3f(x, -.05f, z), new Point3f(-x, -.05f, -z), new Point3f(x, -.05f, -z),
				new Point3f(-x, y, z), new Point3f(x, y, z), new Point3f(-x, y, -z), new Point3f(x, y, -z),
				new Point3f(-x,-.1f, z), new Point3f(x,-.1f, z), new Point3f(-x,-.45f, z), new Point3f(x,-.45f, z), 
				new Point3f(-x,-.1f, z+.01f), new Point3f(x,-.1f, z+.01f), new Point3f(-x,-.45f, z+.01f), new Point3f(x,-.45f, z+.01f),
				new Point3f(-x,-.5f, z), new Point3f(x,-.5f, z), new Point3f(-x,-.85f, z), new Point3f(x,-.85f, z), 
				new Point3f(-x,-.5f, z+.01f), new Point3f(x,-.5f, z+.01f), new Point3f(-x,-.85f, z+.01f), new Point3f(x,-.85f, z+.01f),
				new Point3f(-x,-.05f,z), new Point3f(0,-.05f,z), new Point3f(-x,1f,z), new Point3f(0,1f,z), 
				new Point3f(-x,-.05f,z+.01f), new Point3f(0,-.05f,z+.01f), new Point3f(-x,1f,z+.01f), new Point3f(0,1f,z+.01f),
				new Point3f(x,-.05f,z), new Point3f(0,-.05f,z), new Point3f(x,1f,z), new Point3f(0,1f,z), 
				new Point3f(x,-.05f,z+.01f), new Point3f(0,-.05f,z+.01f), new Point3f(x,1f,z+.01f), new Point3f(0,1f,z+.01f)};
		
		
		int[] indices =  {
				0,1,3,2,1,3,11,9,9,11,10,8,0,2,10,8,2,3,11,10,0,1,5,4,4,5,7,6,16,17,19,18,13,17,19,15,12,16,18,14,14,15,19,18,24,25,27,26,21,25,27,23,20,24,26,22,22,23,27,26,
				28,29,31,30,29,33,35,31,28,32,34,30,34,35,31,30,28,29,33,32,36,37,39,38,37,41,43,39,36,40,42,38,38,39,43,42,36,37,41,40
		};
				//0,1,3,2,1,0,2,3,0,1,5,4,1,0,4,5,2,0,8,10,0,2,10,8,3,1,9,11,1,3,10,11,2,3,11,10,9,8,10,11,16,17,19,18,13,17,19,15,16,12,14,18,
				//24,25,27,26,21,25,27,23,24,20,22,26,28,29,31,30,29,33,35,31,33,32,34,35,32,28,30,34,36,37,39,38,37,41,43,39,41,40,42,43,40,36,38,42};

		
		TexCoord3f tc[] = new TexCoord3f[44];

		for(int i = 0; i < 44; i++) {
			tc[i] = new TexCoord3f(Coords[i]);			
			side.setTextureCoordinate(0,i, tc[i]);
			
		}
		
		int[] indice = {16,17,19,18};
		//16,17,19,18
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indicer(indice, 180));
		
		return side;
	}
	
	
	private static Geometry DeskDimensions() {
		IndexedQuadArray side = new IndexedQuadArray(44, IndexedQuadArray.TEXTURE_COORDINATE_3 | IndexedQuadArray.COORDINATES, 124);
		Point3f Coords[] = {new Point3f(-.5f, -1f, .25f),new Point3f(-.45f,-1f, .25f),new Point3f(-.5f, -1f, .2f),new Point3f(-.45f, -1f, .2f),
				new Point3f(-.5f, -.75f, .25f),new Point3f(-.45f,-.75f, .25f),new Point3f(-.5f, -.75f, .2f),new Point3f(-.45f, -.75f, .2f),
				new Point3f(.45f, -1f, .25f),new Point3f(.5f, -1f, .25f),new Point3f(.45f, -1f, .2f),new Point3f(.5f, -1f, .2f),
				new Point3f(.45f, -.75f, .25f),new Point3f(.5f, -.75f, .25f),new Point3f(.45f, -.75f, .2f),new Point3f(.5f, -.75f, .2f),
				new Point3f(-.5f, -1f, -.2f),new Point3f(-.45f, -1f, -.2f),new Point3f(-.5f, -1f, -.25f),new Point3f(-.45f, -1f, -.25f),
				new Point3f(-.5f, -.75f, -.2f),new Point3f(-.45f, -.75f, -.2f),new Point3f(-.5f, -.75f, -.25f),new Point3f(-.45f, -.75f, -.25f),
				new Point3f(.45f, -1f, -.2f),new Point3f(.5f, -1f, -.2f),new Point3f(.45f, -1f, -.25f),new Point3f(.5f, -1f, -.25f),
				new Point3f(.45f, -.75f, -.2f),new Point3f(.5f, -.75f, -.2f),new Point3f(.45f, -.75f, -.25f),new Point3f(.5f, -.75f, -.25f),
				new Point3f(-.5f, -.5f, -.25f),new Point3f(.5f, -.5f, -.25f),new Point3f(-.5f, -.5f, .25f),new Point3f(.5f, -.5f, .25f),
				new Point3f(-.70f, -.5f, -.35f),new Point3f(.70f, -.5f, -.35f),new Point3f(-.70f, -.5f, .35f),new Point3f(.70f, -.5f, .35f),
				new Point3f(-.70f, -.45f, -.35f),new Point3f(.70f, -.45f, -.35f),new Point3f(-.70f, -.45f, .35f),new Point3f(.70f, -.45f, .35f)};
		
		int[] indices = {0,1,3,2,0,1,5,4,1,3,7,5,3,2,6,7,2,0,4,6,8,9,11,10,8,9,13,12,9,11,15,13,11,10,14,15,10,8,12,14,
				16,17,19,18,16,17,21,20,17,19,23,21,19,18,22,23,18,16,20,22,24,25,26,27,24,25,29,28,25,27,31,29,27,26,
				30,31,26,24,28,30,13,4,22,31,4,13,35,34,13,31,33,35,31,22,32,33,22,4,34,32,
				36,37,39,38,36,37,41,40,37,39,43,41,39,38,42,43,38,36,40,42,40,41,43,42};
			
		TexCoord3f tc[] = new TexCoord3f[44];

		for(int i = 0; i < 44; i++) {
			tc[i] = new TexCoord3f(Coords[i]);			
			side.setTextureCoordinate(0,i, tc[i]);
			
		}
		
		int[] indice = {4,13,35,34};
		
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0, 0, indicer(indice, 124));
		
		return side;
	}
	
	
	private static Geometry ShelfDimensions() {
		IndexedQuadArray side = new IndexedQuadArray(36, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES, 108);
		Point3f Coords[] = {
				new Point3f(-.5f,-1f,.1f), new Point3f(.5f,-1f,.1f), new Point3f(-.5f,-1f,-.1f), new Point3f(.5f,-1f,-.1f), 
				new Point3f(-.5f,-.3f,.1f), new Point3f(.5f,-.3f,.1f), new Point3f(-.5f,-.3f,-.1f), new Point3f(.5f,-.3f,-.1f),
				new Point3f(-.45f,-.95f,.1f), new Point3f(.45f,-.95f,.1f), new Point3f(-.45f,-.35f,.1f), new Point3f(.45f,-.35f,.1f), 
				new Point3f(-.025f,-.95f,.1f), new Point3f(.025f,-.95f,.1f), new Point3f(-.025f,-.35f,.1f), new Point3f(.025f,-.35f,.1f), 
				new Point3f(-.025f,-.95f,-.1f), new Point3f(.025f,-.95f,-.1f), new Point3f(-.025f,-.35f,-.1f), new Point3f(.025f,-.35f,-.1f), 
				new Point3f(-.45f,-.675f,.1f), new Point3f(.45f,-.675f,.1f), new Point3f(-.45f,-.625f,.1f), new Point3f(.45f,-.625f,.1f), 
				new Point3f(-.45f,-.675f,-.1f), new Point3f(.45f,-.675f,-.1f), new Point3f(-.45f,-.625f,-.1f), new Point3f(.45f,-.625f,-.1f),
				new Point3f(-.5f,-.95f,-.1f), new Point3f(.5f,-.95f,-.1f), new Point3f(-.5f,-.95f,.1f), new Point3f(.5f,-.95f,.1f),
				new Point3f(-.5f,-.35f,-.1f), new Point3f(.5f,-.35f,-.1f), new Point3f(-.5f,-.35f,.1f), new Point3f(.5f,-.35f,.1f),
				
		};
		
		int[] indices = {0,1,3,2,1,3,7,5,4,5,7,6,2,0,4,6,12,13,15,14,13,17,19,15,17,16,18,19,16,12,14,18,20,21,25,24,22,23,27,26,20,21,23,22,21,25,27,23,24,20,22,26,0,1,9,8,1,5,11,9,
				5,4,10,11,4,0,8,10,2,3,7,6,28,29,31,30,32,33,35,34};
		
		int[] indice = {17,16,18,19};
			
		//12,13,15,14
		TexCoord3f tc[] = new TexCoord3f[36];

		for(int i = 0; i < 36; i++) {
			tc[i] = new TexCoord3f(Coords[i]);			
			side.setTextureCoordinate(0,i, tc[i]);
			
		}
		
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indicer(indice, 108));
		return side;
	}
	
	private static Geometry TableDimensions() {
		
		IndexedQuadArray side = new IndexedQuadArray(44, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES, 180);
		Point3f Coords[] = {new Point3f(-.3f, -1f, .25f),new Point3f(-.25f,-1f, .25f),new Point3f(-.3f, -1f, .2f),new Point3f(-.25f, -1f, .2f),
				new Point3f(-.3f, -.75f, .25f),new Point3f(-.25f,-.75f, .25f),new Point3f(-.3f, -.75f, .2f),new Point3f(-.25f, -.75f, .2f),
				new Point3f(.25f, -1f, .25f),new Point3f(.3f, -1f, .25f),new Point3f(.25f, -1f, .2f),new Point3f(.3f, -1f, .2f),
				new Point3f(.25f, -.75f, .25f),new Point3f(.3f, -.75f, .25f),new Point3f(.25f, -.75f, .2f),new Point3f(.3f, -.75f, .2f),
				new Point3f(-.3f, -1f, -.2f),new Point3f(-.25f, -1f, -.2f),new Point3f(-.3f, -1f, -.25f),new Point3f(-.25f, -1f, -.25f),
				new Point3f(-.3f, -.75f, -.2f),new Point3f(-.25f, -.75f, -.2f),new Point3f(-.3f, -.75f, -.25f),new Point3f(-.25f, -.75f, -.25f),
				new Point3f(.25f, -1f, -.2f),new Point3f(.3f, -1f, -.2f),new Point3f(.25f, -1f, -.25f),new Point3f(.3f, -1f, -.25f),
				new Point3f(.25f, -.75f, -.2f),new Point3f(.3f, -.75f, -.2f),new Point3f(.25f, -.75f, -.25f),new Point3f(.3f, -.75f, -.25f),
				new Point3f(-.3f, -.7f, -.25f),new Point3f(.3f, -.7f, -.25f),new Point3f(-.3f, -.7f, .25f),new Point3f(.3f, -.7f, .25f),
				new Point3f(-.35f, -.7f, -.35f),new Point3f(.35f, -.7f, -.35f),new Point3f(-.35f, -.7f, .35f),new Point3f(.35f, -.7f, .35f),
				new Point3f(-.35f, -.65f, -.35f),new Point3f(.35f, -.65f, -.35f),new Point3f(-.35f, -.65f, .35f),new Point3f(.35f, -.65f, .35f)};
		
		int[] indices = {0,1,3,2,0,1,5,4,1,3,7,5,3,2,6,7,2,0,4,6,8,9,11,10,8,9,13,12,9,11,15,13,11,10,14,15,10,8,12,14,
				16,17,19,18,16,17,21,20,17,19,23,21,19,18,22,23,18,16,20,22,24,25,26,27,24,25,29,28,25,27,31,29,27,26,
				30,31,26,24,28,30,13,4,22,31,4,13,35,34,13,31,33,35,31,22,32,33,22,4,34,32,
				36,37,39,38,36,37,41,40,37,39,43,41,39,38,42,43,38,36,40,42,40,41,43,42};
		
		int[] indice = {0,1,5,4};
		
		TexCoord3f tc[] = new TexCoord3f[44];

		for(int i = 0; i < 44; i++) {
			tc[i] = new TexCoord3f(Coords[i]);			
			side.setTextureCoordinate(0,i, tc[i]);
			
		}
		
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indicer(indice, 180));
		return side;
	}
	
	private static Geometry ChairDimensions() {
		
		float z = .5f;
		float x = -.5f;
		float y = .5f;
		IndexedQuadArray side = new IndexedQuadArray(136, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES, 528);
		
		
		Point3f Coords1[] = { //outer ring of armrest
				new Point3f(x-.05f,-y,-z), new Point3f(x-.05f,-y-.1f,-z+.2f), new Point3f(x-.05f,-y-.1f,z-.2f), new Point3f(x-.05f,-y,z), new Point3f(x-.05f,y-.2f,z), 
				new Point3f(x-.05f,y-.1f,z-.15f), new Point3f(x-.05f,y,z-.4f), new Point3f(x-.05f,y,-z+.4f), new Point3f(x-.05f,y-.1f,-z+.15f), new Point3f(x-.05f,y-.2f,-z),
				new Point3f(x+.05f,-y,-z), new Point3f(x+.05f,-y-.1f,-z+.2f), new Point3f(x+.05f,-y-.1f,z-.2f), new Point3f(x+.05f,-y,z), new Point3f(x+.05f,y-.2f,z), 
				new Point3f(x+.05f,y-.1f,z-.15f), new Point3f(x+.05f,y,z-.4f), new Point3f(x+.05f,y,-z+.4f), new Point3f(x+.05f,y-.1f,-z+.15f), new Point3f(x+.05f,y-.2f,-z), 
				
		};
		z = .45f;
		y = .45f;
		Point3f Coords2[] = {//inner ring of armrest
				new Point3f(x-.05f,-y,-z), new Point3f(x-.05f,-y-.1f,-z+.2f), new Point3f(x-.05f,-y-.1f,z-.2f), new Point3f(x-.05f,-y,z), new Point3f(x-.05f,y-.2f,z), 
				new Point3f(x-.05f,y-.1f,z-.15f), new Point3f(x-.05f,y,z-.4f), new Point3f(x-.05f,y,-z+.4f), new Point3f(x-.05f,y-.1f,-z+.15f), new Point3f(x-.05f,y-.2f,-z),
				new Point3f(x+.05f,-y,-z), new Point3f(x+.05f,-y-.1f,-z+.2f), new Point3f(x+.05f,-y-.1f,z-.2f), new Point3f(x+.05f,-y,z), new Point3f(x+.05f,y-.2f,z), 
				new Point3f(x+.05f,y-.1f,z-.15f), new Point3f(x+.05f,y,z-.4f), new Point3f(x+.05f,y,-z+.4f), new Point3f(x+.05f,y-.1f,-z+.15f), new Point3f(x+.05f,y-.2f,-z), 
				
		};
		
		x = .5f;
		z = .5f;
		y = .5f;
		
		Point3f Coords3[] = { //outer ring of armrest
				new Point3f(x-.05f,-y,-z), new Point3f(x-.05f,-y-.1f,-z+.2f), new Point3f(x-.05f,-y-.1f,z-.2f), new Point3f(x-.05f,-y,z), new Point3f(x-.05f,y-.2f,z), 
				new Point3f(x-.05f,y-.1f,z-.15f), new Point3f(x-.05f,y,z-.4f), new Point3f(x-.05f,y,-z+.4f), new Point3f(x-.05f,y-.1f,-z+.15f), new Point3f(x-.05f,y-.2f,-z),
				new Point3f(x+.05f,-y,-z), new Point3f(x+.05f,-y-.1f,-z+.2f), new Point3f(x+.05f,-y-.1f,z-.2f), new Point3f(x+.05f,-y,z), new Point3f(x+.05f,y-.2f,z), 
				new Point3f(x+.05f,y-.1f,z-.15f), new Point3f(x+.05f,y,z-.4f), new Point3f(x+.05f,y,-z+.4f), new Point3f(x+.05f,y-.1f,-z+.15f), new Point3f(x+.05f,y-.2f,-z), 
				
		};
		z = .45f;
		y = .45f;
		Point3f Coords4[] = {//inner ring of armrest
				new Point3f(x-.05f,-y,-z), new Point3f(x-.05f,-y-.1f,-z+.2f), new Point3f(x-.05f,-y-.1f,z-.2f), new Point3f(x-.05f,-y,z), new Point3f(x-.05f,y-.2f,z), 
				new Point3f(x-.05f,y-.1f,z-.15f), new Point3f(x-.05f,y,z-.4f), new Point3f(x-.05f,y,-z+.4f), new Point3f(x-.05f,y-.1f,-z+.15f), new Point3f(x-.05f,y-.2f,-z),
				new Point3f(x+.05f,-y,-z), new Point3f(x+.05f,-y-.1f,-z+.2f), new Point3f(x+.05f,-y-.1f,z-.2f), new Point3f(x+.05f,-y,z), new Point3f(x+.05f,y-.2f,z), 
				new Point3f(x+.05f,y-.1f,z-.15f), new Point3f(x+.05f,y,z-.4f), new Point3f(x+.05f,y,-z+.4f), new Point3f(x+.05f,y-.1f,-z+.15f), new Point3f(x+.05f,y-.2f,-z), 
				
		};
		
		//dimensions of cushion; x = .45f, z = .45f, y = -.05f
		
		x = .45f;
		y= -.05f;
		z = .45f;
		
		Point3f Coords5[] = {
				new Point3f(-x, -y -.025f, z+.025f), new Point3f(-x+.1f, -y-.05f, z+.05f), new Point3f(-x+.2f, -y-.075f, z+.075f), new Point3f(x-x, -y-.1f, z+.1f), new Point3f(x-.2f, -y-.075f, z+.075f), new Point3f(x-.1f, -y-.05f, z+.05f), new Point3f(x, -y-.025f, z+.025f), 
				new Point3f(-x, -y-.025f, -(z +.025f)), new Point3f(-x+.1f, -y-.05f, -(z+.05f)), new Point3f(-x+.2f, -y-.075f, -(z+.075f)), new Point3f(x-x, -y-.1f, -(z+.1f)), new Point3f(x-.2f, -y-.075f, -(z+.075f)), new Point3f(x-.1f, -y-.05f, -(z+.05f)), new Point3f(x, -y-.025f, -(z+.025f)),
		};
		
		y = .05f;
		
		Point3f Coords6[] = {
				new Point3f(-x, -y -.025f, z+.025f), new Point3f(-x+.1f, -y-.05f, z+.05f), new Point3f(-x+.2f, -y-.075f, z+.075f), new Point3f(x-x, -y-.1f, z+.1f), new Point3f(x-.2f, -y-.075f, z+.075f), new Point3f(x-.1f, -y-.05f, z+.05f), new Point3f(x, -y-.025f, z+.025f), 
				new Point3f(-x, -y-.025f, -(z +.025f)), new Point3f(-x+.1f, -y-.05f, -(z+.05f)), new Point3f(-x+.2f, -y-.075f, -(z+.075f)), new Point3f(x-x, -y-.1f, -(z+.1f)), new Point3f(x-.2f, -y-.075f, -(z+.075f)), new Point3f(x-.1f, -y-.05f, -(z+.05f)), new Point3f(x, -y-.025f, -(z+.025f)), 
		};
		
		x = .4f;
		y = .45f;
		z = -.6f;
		
		
		Point3f Coords7[] = {
				new Point3f(x+.025f, y - y,-z-.025f), new Point3f(x+.05f,y-.4f,-z -0.05f), new Point3f(x+.075f,y-.3f,-z-0.075f), new Point3f(x+.1f,y,-z-.1f), new Point3f(x+.075f,y+.3f,-z-0.075f), new Point3f(x+.05f,y+.4f,-z-0.05f), new Point3f(x+.025f,y+y,-z-0.025f), 
				new Point3f(-(x+.025f), y - y,-z-.025f), new Point3f(-(x+.05f),y-.4f,-z -0.05f), new Point3f(-(x+.075f),y-.3f,-z-0.075f), new Point3f(-(x+.1f),y,-z-.1f), new Point3f(-(x+.075f),y+.3f,-z-0.075f), new Point3f(-(x+.05f),y+.4f,-z-0.05f), new Point3f(-(x+.025f),y+y,-z-0.025f),    
		};
		
		z = -.5f;
		x = .4f;
		
		Point3f Coords8[] = {
				new Point3f(x+.025f, y - y,-z-.025f), new Point3f(x+.05f,y-.4f,-z -0.05f), new Point3f(x+.075f,y-.3f,-z-0.075f), new Point3f(x+.1f,y,-z-.1f), new Point3f(x+.075f,y+.3f,-z-0.075f), new Point3f(x+.05f,y+.4f,-z-0.05f), new Point3f(x+.025f,y+y,-z-0.025f), 
				new Point3f(-(x+.025f), y - y,-z-.025f), new Point3f(-(x+.05f),y-.4f,-z -0.05f), new Point3f(-(x+.075f),y-.3f,-z-0.075f), new Point3f(-(x+.1f),y,-z-.1f), new Point3f(-(x+.075f),y+.3f,-z-0.075f), new Point3f(-(x+.05f),y+.4f,-z-0.05f), new Point3f(-(x+.025f),y+y,-z-0.025f),        
		};
		
		
		int[] indices = {0,1,11,10,1,2,12,11,2,3,13,12,3,4,14,13,4,5,15,14,5,6,16,15,6,7,17,16,7,8,18,17,8,9,19,18,9,0,10,19,
				20,21,31,30,21,22,32,31,22,23,33,32,23,24,34,33,24,25,35,34,25,26,36,35,26,27,37,36,27,28,38,37,28,29,39,38,29,20,30,39,
				0,1,21,20,1,2,22,21,2,3,23,22,3,4,24,23,4,5,25,24,5,6,26,25,6,7,27,26,7,8,28,27,8,9,29,28,9,0,20,29,
				10,11,31,30,11,12,32,31,12,13,33,32,13,14,34,33,14,15,35,34,15,16,36,35,16,17,37,36,17,18,38,37,18,19,39,38,19,10,30,39,
				40,41,51,50,41,42,52,51,42,43,53,52,43,44,54,53,44,45,55,54,45,46,56,55,46,47,57,56,47,48,58,57,48,49,59,58,49,40,50,59,
				60,61,71,70,61,62,72,71,62,63,73,72,63,64,74,73,64,65,75,74,65,66,76,75,66,67,77,76,67,68,78,77,68,69,79,78,69,60,70,79,
				40,41,61,60,41,42,62,61,42,43,63,62,43,44,64,63,44,45,65,64,45,46,66,65,46,47,67,66,47,48,68,67,48,49,69,68,49,40,60,69,
				50,51,71,70,51,52,72,71,52,53,73,72,53,54,74,73,54,55,75,74,55,56,76,75,56,57,77,76,57,58,78,77,58,59,79,78,59,50,70,79,
				80,81,95,94,81,82,96,95,82,83,97,96,83,84,98,97,84,85,99,98,85,86,100,99,86,93,107,100,87,88,102,101,88,89,103,102,89,90,104,103,90,91,105,104,91,92,106,105,92,93,107,106,80,87,101,94,
				80,87,88,81,81,88,89,82,82,89,90,83,83,90,91,84,84,91,92,85,85,92,93,86,
				94,95,102,101,95,96,103,102,96,97,104,103,97,98,105,104,98,99,106,105,99,100,107,106,
				108,109,123,122,109,110,124,123,110,111,125,124,111,112,126,125,112,113,127,126,113,114,128,127,115,116,130,129,116,117,131,130,117,118,132,131,118,119,133,132,119,120,134,133,120,121,135,134,108,115,129,122,114,121,135,128,
				108,109,116,115,109,110,117,116,110,111,118,117,111,112,119,118,112,113,120,119,113,114,121,120,
				122,123,130,129,123,124,131,130,124,125,132,131,125,126,133,132,126,127,134,133,127,128,135,134,
				
				};
		
		int[] indice = {0,1,11,10};
		
		TexCoord3f tc[] = new TexCoord3f[136];
		
		for(int i = 0; i < 136; i++) {
			
			if(i >= 20 && i < 40) tc[i] = new TexCoord3f(Coords2[i - 20]);	
			
			else if(i >= 40 && i < 60) tc[i] = new TexCoord3f(Coords3[i - 40]);
			
			else if(i >= 60 && i < 80) tc[i] = new TexCoord3f(Coords4[i - 60]);
			
			else if(i >= 80 && i < 94) tc[i] = new TexCoord3f(Coords5[i - 80]);
			
			else if(i >= 94 && i < 108) tc[i] = new TexCoord3f(Coords6[i - 94]);
			
			else if(i >= 108 && i < 122) tc[i] = new TexCoord3f(Coords7[i - 108]);
			
			else if(i >= 122) tc[i] = new TexCoord3f(Coords8[i - 122]); 
			
			else tc[i] = new TexCoord3f(Coords1[i]);
			
			side.setTextureCoordinate(0,i, tc[i]);
			
		}
		
		side.setCoordinates(0, Coords1);
		side.setCoordinates(20,Coords2);
		side.setCoordinates(40, Coords3);
		side.setCoordinates(60,Coords4);
		side.setCoordinates(80, Coords5);
		side.setCoordinates(94, Coords6);
		side.setCoordinates(108, Coords7);
		side.setCoordinates(122, Coords8);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indicer(indice, 528));
		return side;
	}
	
	
	
	private static TransformGroup Position(Shape3D shape, Vector3f vector, double scale, double rot, int i) {
		TransformGroup trans = new TransformGroup();
		TransformGroup scaler = new TransformGroup();
		Transform3D trans3d = new Transform3D();
		trans3d.setTranslation(vector);
		
		if(rot != 0) {
			Transform3D rot1 = new Transform3D();
			Transform3D rot2 = new Transform3D();
			rot1.rotY(rot);
			rot2.mul(trans3d);
			rot2.mul(rot1);
			rot2.setScale(scale);
			scaler.setTransform(rot2);
		}
		
		else {
			trans3d.setScale(scale);
			scaler.setTransform(trans3d);
		}
		
		if(shape == null) 
			
			if(i == 6) scaler.addChild(tranny1);
				
			else scaler.addChild(tranny);
			
		else scaler.addChild(shape);
		
		trans.addChild(scaler);
		
		
		return trans;
	}

	private static int[] indicer(int[] indice, int length) {
		int[] indices = new int[length];
		
		for(int i = 0; i < length; i += 4) {
			indices[i] = indice[0];
			indices[i+1] = indice[1];
			indices[i+2] = indice[2];
			indices[i+3] = indice[3];
		}
		
		return indices;
	}
	
	
	public static SharedGroup Togethor() { //enter all translations rotations and scales in this function. Returns a link to sharedgroup
		SharedGroup shared = new SharedGroup();
		
		Shape3D shapes[] = {
				BuildShape("desk"), BuildShape("computer"), BuildShape("chair"), BuildShape("chair"),
				BuildShape("table"), BuildShape("chair"), BuildShape("lamp"), BuildShape("cabinet"),
				BuildShape("shelf"),
		};
		
		//position shapes properly
		Vector3f vector[] = { 
				new Vector3f(5f,3.5f,4f), new Vector3f(4f,2.825f,4f), new Vector3f(5.5f,.905f,9f), 
				new Vector3f(3.5f,.905f,9f), new Vector3f(1.25f,2.5f,9f), new Vector3f(1.25f,.905f,7f), 
				new Vector3f(1.25f,1.375f,9f), new Vector3f(8.5f,2.5f,0.55f), new Vector3f(.5f,4.05f,3.5f),
		};
		
		//scale shapes properly
		double scale[] = { 
				3.5f, 2.4f, 1.5f, 1.5f, 2.5f, 1.5f, 1.5f,2.5f,4f
		};
		
		double rot[] = {
				0, 180 * Math.PI/180, 0, 0, 0, 270 * Math.PI/180, 270 * Math.PI/180, 0, 90 * Math.PI/180
		};
		
		for(int i = 0; i < 9; i++) shared.addChild(Position(shapes[i], vector[i], scale[i], rot[i], i));
		
		shared.compile();
	
		return shared;
	}
	
	
	

}


