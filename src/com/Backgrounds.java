package com;

import org.jogamp.java3d.Background;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.ImageComponent2D;


import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Point3d;


public class Backgrounds {

	static Background createBackground() {
		
		String filename = "images/windowView.png";
		TextureLoader loader = new TextureLoader(filename, null);
		ImageComponent2D image = loader.getImage();
		Background backGround = new Background(image);
		BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 10000);
		backGround.setApplicationBounds(sphere);
		backGround.setCapability(Background.ALLOW_IMAGE_SCALE_MODE_WRITE);
		backGround.setImageScaleMode(Background.SCALE_FIT_MAX);
		return backGround;
	}
}

