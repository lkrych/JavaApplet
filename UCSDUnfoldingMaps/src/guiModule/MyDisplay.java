package guiModule;

import processing.core.PApplet;
import processing.core.PImage;


public class MyDisplay extends PApplet {
	
	PImage img;
	
	public void setup()
	{
		size(400,400);
		stroke(0);
		img = loadImage("palmTrees.jpg", "jpg");
		img.resize(0, height); //dynamically resize background to applet size
		image(img,0,0); //draw image
		
	}
	
	public void draw()
	{
		int[] color =  sunColorSec(second()); // calls built in method called second which returns the number of seconds that have elapsed since last minute
		fill(color[0],color[1],color[2]); // fill sun with color from array of colors
		ellipse(width/4,height/5,width/5,height/5); //draw sun
		
	}
	
	public int[] sunColorSec(float seconds){
		int[] rgb = new int[3]; //create int array to represent rgb values
		//scale the brightness of sun based on seconds
		float diffFrom30 = Math.abs(30-seconds);
		
		float ratio = diffFrom30/30;
		rgb[0] = (int)(255*ratio);
		rgb[1] = (int)(255*ratio);
		rgb[2] = 0;
		
		
		return rgb;
		
	}
	
	public static void main(String[] args){
		//Add main method for running as application
		PApplet.main(new String[] {"--present", "MyPApplet"});
			}
}
