package idk;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Geometry;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.IndexedLineArray;
import org.jogamp.java3d.IndexedQuadArray;
import org.jogamp.java3d.LineArray;
import org.jogamp.java3d.Link;
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
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.TexCoord3f;
import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;

/*
	Creates a function called BuildShape() which takes string argument and returns a 3Dshape associated with string, i.e "cabinet" will build a cabinet
	Use TransformGroup and Transform3D to position and scale correctly i guess 
 */


public class Cab {
	
	
	
	
	public static Texture setTexture(String file)
	{
		
		TextureLoader loader = new TextureLoader(file, null);
		ImageComponent2D image = loader.getImage();
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		
		return texture;
		
	}
	
	
	public static Appearance app() {
		
		Appearance app = new Appearance();
		app.setTexture(setTexture("Textures/wood2.jpg"));
		PolygonAttributes poly = new PolygonAttributes();
		poly.setCullFace(PolygonAttributes.CULL_NONE);
		app.setPolygonAttributes(poly);
		
		TextureAttributes texture = new TextureAttributes();
		texture.setTextureMode(TextureAttributes.REPLACE);
		app.setTextureAttributes(texture);
		
		TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_3);
		app.setTexCoordGeneration(tcg);
		
		Vector3d scale = new Vector3d(2f,2f,2f);
		Transform3D transMap = new Transform3D();
		
		transMap.setScale(scale);
		texture.setTextureTransform(transMap);
		
		return app;
	}
	
	
	public static Shape3D BuildShape(String name){ //builds shape depending on what tag is passed through
		Shape3D shape = new Shape3D();
		if(name == "desk")
			shape.setGeometry(DeskDimensions());
		
		else if(name == "cabinet") {
			shape.setGeometry(CabinetDimensions());		
		}
		
		else if(name == "shelf") {
			shape.setGeometry(ShelfDimensions());
		}
		
		else if(name == "table") {
			shape.setGeometry(TableDimensions());
		}
		else if(name == "chair") {
			shape.setGeometry(ChairDimensions());
		}
		
		
		shape.setAppearance(app());
		return shape;
		
	}
	
	public static Geometry CabinetDimensions() {
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
		
		
		int[] indices =  {0,1,3,2,1,0,2,3,0,1,5,4,1,0,4,5,2,0,8,10,0,2,10,8,3,1,9,11,1,3,11,9,3,2,10,11,2,3,11,10,8,9,11,10,9,8,10,11,16,17,19,18,13,17,19,15,16,12,14,18,
				24,25,27,26,21,25,27,23,24,20,22,26,28,29,31,30,29,33,35,31,33,32,34,35,32,28,30,34,36,37,39,38,37,41,43,39,41,40,42,43,40,36,38,42};

		
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indices);
		
		return side;
	}
	
	
	public static Geometry DeskDimensions() {
		IndexedQuadArray side = new IndexedQuadArray(44, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES | QuadArray.COLOR_3, 180);
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
		
		side.setColors(0, CommonsES.Clrs);
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indices);
		return side;
	}
	
	
	public static Geometry ShelfDimensions() {
		IndexedQuadArray side = new IndexedQuadArray(28, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES | QuadArray.COLOR_3, 108);
		Point3f Coords[] = {new Point3f(-.5f,-1f,.15f), new Point3f(.5f,-1f,.15f), new Point3f(-.5f,-1f,-.15f), new Point3f(.5f,-1f,-.15f), 
				new Point3f(-.5f,-.3f,.15f), new Point3f(.5f,-.3f,.15f), new Point3f(-.5f,-.3f,-.15f), new Point3f(.5f,-.3f,-.15f),
				new Point3f(-.45f,-.95f,.15f), new Point3f(.45f,-.95f,.15f), new Point3f(-.45f,-.35f,.15f), new Point3f(.45f,-.35f,.15f), 
				new Point3f(-.025f,-.95f,.15f), new Point3f(.025f,-.95f,.15f), new Point3f(-.025f,-.35f,.15f), new Point3f(.025f,-.35f,.15f), 
				new Point3f(-.025f,-.95f,-.15f), new Point3f(.025f,-.95f,-.15f), new Point3f(-.025f,-.35f,-.15f), new Point3f(.025f,-.35f,-.15f), 
				new Point3f(-.45f,-.675f,.15f), new Point3f(.45f,-.675f,.15f), new Point3f(-.45f,-.625f,.15f), new Point3f(.45f,-.625f,.15f), 
				new Point3f(-.45f,-.675f,-.15f), new Point3f(.45f,-.675f,-.15f), new Point3f(-.45f,-.625f,-.15f), new Point3f(.45f,-.625f,-.15f)};
		
		int[] indices = {0,1,3,2,1,3,7,5,4,5,7,6,2,0,4,6,12,13,15,14,13,17,19,15,17,16,18,19,16,12,14,18,20,21,25,24,22,23,27,26,20,21,23,22,21,25,27,23,24,20,22,26,0,1,9,8,1,5,11,9,
				5,4,10,11,4,0,8,10,2,3,7,6};
			
		side.setColors(0, CommonsES.Clrs);
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indices);
		return side;
	}
	
	public static Geometry TableDimensions() {
		IndexedQuadArray side = new IndexedQuadArray(60, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES | QuadArray.COLOR_3, 180);
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
		
		
		side.setCoordinates(0, Coords);
		side.setCoordinateIndices(0, indices);
		side.setTextureCoordinateIndices(0,0, indices);
		return side;
	}
	
	public static Geometry ChairDimensions() {
		
		float z = .5f;
		float x = -.5f;
		float y = .5f;
		IndexedQuadArray side = new IndexedQuadArray(136, QuadArray.TEXTURE_COORDINATE_3 | QuadArray.COORDINATES | QuadArray.COLOR_3, 600);
		
		
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
		
		side.setCoordinates(0, Coords1);
		side.setCoordinates(20,Coords2);
		side.setCoordinates(40, Coords3);
		side.setCoordinates(60,Coords4);
		side.setCoordinates(80, Coords5);
		side.setCoordinates(94, Coords6);
		side.setCoordinates(108, Coords7);
		side.setCoordinates(122, Coords8);
		side.setCoordinateIndices(0, indices);
		return side;
	}
	
	public static SharedGroup Togethor() {
		
		SharedGroup boing = new SharedGroup();
		boing.addChild(BuildShape("cabinet"));
		boing.addChild(BuildShape("desk"));
		boing.addChild(BuildShape("shelf"));
		boing.addChild(BuildShape("table"));
		boing.addChild(BuildShape("chair"));
		boing.compile();
		return boing;
	}
	
	public static BranchGroup createScene()//creates the scene
	{
		
		BranchGroup sceneBG = new BranchGroup();
		TransformGroup trans1 = new TransformGroup();
		TransformGroup trans = new TransformGroup();
		Transform3D trans3d = new Transform3D();
		trans3d.setTranslation(new Vector3f(0,0,0));
		trans1.addChild(BuildShape("cabinet"));
		trans.setTransform(trans3d);
		trans.addChild(trans1);
		sceneBG.addChild(trans);
		
		sceneBG.addChild(CommonsES.rotateBehavior(10000, trans1));
		
		return sceneBG;
	}
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				CommonsES.setEye(new Point3d(0.05, 1, 4));
				new CommonsES.MyGUI(createScene(), "Furniture ");
			}
		});
	}

}
