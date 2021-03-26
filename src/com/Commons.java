package com;/* *********************************************************
 * For use by students to work on assignments and project.
 * Permission required material. Contact: xyuan@uwindsor.ca 
 **********************************************************/

import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import org.jogamp.java3d.utils.geometry.ColorCube;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.ViewingPlatform;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

import com.jogamp.newt.event.MouseEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;
import java.awt.*;

public class Commons extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	public final static Color3f Red = new Color3f(1.0f, 0.0f, 0.0f);
	public final static Color3f Green = new Color3f(0.0f, 1.0f, 0.0f);
	public final static Color3f Blue = new Color3f(0.0f, 0.0f, 1.0f);
	public final static Color3f Yellow = new Color3f(1.0f, 1.0f, 0.0f);
	public final static Color3f Cyan = new Color3f(0.0f, 1.0f, 1.0f);
	public final static Color3f Orange = new Color3f(1.0f, 0.5f, 0.0f);
	public final static Color3f Magenta = new Color3f(1.0f, 0.0f, 1.0f);
	public final static Color3f White = new Color3f(1.0f, 1.0f, 1.0f);
	public final static Color3f Grey = new Color3f(0.5f, 0.5f, 0.5f);
	public final static Color3f Black = new Color3f(0f, 0f, 0f);
	public final static Color3f[] Clrs = {Blue, Green, Red, Yellow,
			Cyan, Orange, Magenta, Grey};
	public final static int clr_num = 8;


	private static JFrame frame;
	private static Point3d eye = new Point3d(5, 2.5, 1.25);
	private static Canvas3D canvas_3D;
	private static PickTool pickTool;

	/* a function to create a rotation behavior and refer it to 'my_TG' */
	public static RotationInterpolator rotateBehavior(int r_num, TransformGroup my_TG) {

		my_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D yAxis = new Transform3D();
		Alpha rotationAlpha = new Alpha(-1, r_num);
		RotationInterpolator rot_beh = new RotationInterpolator(
				rotationAlpha, my_TG, yAxis, 0.0f, (float) Math.PI * 2f );
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100);
		rot_beh.setSchedulingBounds(bounds);
		return rot_beh;
	}



	/* a function to position viewer to 'eye' location */
//	public static void defineViewer(SimpleUniverse su) {
//	    TransformGroup viewTransform = su.getViewingPlatform().getViewPlatformTransform();
//		Point3d center = new Point3d(0, 2.50, 0);               // define the point where the eye looks at
//		Vector3d up = new Vector3d(0, 1, 0);                 // define camera's up direction
//		Transform3D view_TM = new Transform3D();
//		view_TM.lookAt(eye, center, up);
//		view_TM.invert();
//	    viewTransform.setTransform(view_TM);                 // set the TransformGroup of ViewingPlatform
//	}


	/* a function to build the content branch and attach to 'scene' */
	private static BranchGroup createScene() {
		BranchGroup scene = new BranchGroup();
		
		TransformGroup content_TG = new TransformGroup();    // create a TransformGroup (TG)
		content_TG.addChild(new ColorCube(0.4f));
		scene.addChild(content_TG);	                         // add TG to the scene BranchGroup
		scene.addChild(rotateBehavior(10000, content_TG));   // make TG continuously rotating 
		
		return scene;
	}


//	/* a function to allow key navigation with the ViewingPlateform */
//	private static KeyNavigatorBehavior keyNavigation(SimpleUniverse simple_U) {
//		ViewingPlatform view_platfm = simple_U.getViewingPlatform();
//		TransformGroup view_TG = view_platfm.getViewPlatformTransform();
//
//
//
//		KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(view_TG);
//
//
//		BoundingSphere view_bounds = new BoundingSphere(new Point3d(), 20.0);
//		keyNavBeh.setSchedulingBounds(view_bounds);
//		return keyNavBeh;
//	}


	public static void setEye(Point3d eye_position) {
		eye = eye_position;
	}

	/* a constructor to set up and run the application */
	public Commons(BranchGroup sceneBG) {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas_3D = new Canvas3D(config);
		canvas_3D.addMouseListener(this);

		pickTool = new PickTool(sceneBG);
		pickTool.setMode(PickTool.BOUNDS);
		SimpleUniverse su = new SimpleUniverse(canvas_3D);   // create a SimpleUniverse

		Camera camera = new Camera(); //setup camera
		KeyNavigatorBehavior cameraKeyNav = camera.defineCamera(su); //setup the camera
		sceneBG.addChild(cameraKeyNav); // adding key movement to camera
		System.out.println("Hello");
		System.out.println(su.getCanvas().isFocusable());

        sceneBG.compile(); // add moveTG branch group to SU
		su.addBranchGraph(sceneBG); // attach the scene to SimpleUniverse

		setLayout(new BorderLayout());
		add("Center", canvas_3D);
		frame.setSize(800, 800);                             // set the size of the JFrame
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		frame = new JFrame("Group's Commons");                  // call constructor with 'createScene()'
		frame.getContentPane().add(new Commons(createScene()));
	}
	
	public static class MyGUI extends JFrame {
		private static final long serialVersionUID = 1L;
		public MyGUI(BranchGroup branchGroup, String title) {
			frame = new JFrame(title);
			frame.getContentPane().add(new Commons(branchGroup));
			pack();
		}		
	}
	
	
	public void mouseClicked(java.awt.event.MouseEvent e) { //register mouse clicks for the computer screen
		int x = e.getX(); int y = e.getY();
		Point3d point3d = new Point3d(), center = new Point3d();
		canvas_3D.getPixelLocationInImagePlate(x, y, point3d);
		canvas_3D.getCenterEyeInImagePlate(center);
		
		Transform3D transform3D = new Transform3D();
		canvas_3D.getImagePlateToVworld(transform3D);
		transform3D.transform(point3d);
		transform3D.transform(center);
		
		Vector3d mouseVec = new Vector3d();
		mouseVec.sub(point3d, center);
		mouseVec.normalize();
		pickTool.setShapeRay(point3d, mouseVec);
		
		if(pickTool.pickClosest() != null) {
			
			PickResult pickResult = pickTool.pickClosest();
			Shape3D screen = (Shape3D)pickResult.getNode(PickResult.SHAPE3D);
			
			if((int) screen.getUserData() == 0) {
				screen.setAppearance(Cab.app("texture", "screen"));
				screen.setUserData(1);
			}
			else{
				screen.setAppearance(Cab.app("texture", "login"));
				screen.setUserData(0);
			}
			
		}
		
	}


	//inherited methods ignore for now
	
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




}