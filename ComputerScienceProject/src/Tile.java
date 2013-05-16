import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public class Tile {
	static int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	float x, y;
	float width, length;
	float[] color, color2;
	String name;
	int orientation;
	public Tile(float X, float Y, float Length, float Width, float[] Color, float[] Color2, String Name, int Orientation) {
		x = X;
		y = Y;
		width = Width;
		length = Length;
		color = Color;
		color2 = Color2;
		name = Name;
		orientation = Orientation;
	}
	
	private void drawOutline(int thickness) {
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
	    GL11.glVertex2f(x - thickness,y - thickness);
	    if (orientation == 0 || orientation == 1) {
			GL11.glVertex2f(x + width + thickness,y - thickness);
			GL11.glVertex2f(x + width + thickness,y + length + thickness);
			GL11.glVertex2f(x  - thickness,y + length + thickness);
	    }
	    else {
	    	GL11.glVertex2f(x + length + thickness,y - thickness);
	    	GL11.glVertex2f(x + length + thickness,y + width + thickness);
	    	GL11.glVertex2f(x - thickness,y + width + thickness);
	    }
	    GL11.glEnd();
	}
	
	private void drawBase() {
		GL11.glColor3f(color[0], color[1], color[2]);
		GL11.glBegin(GL11.GL_QUADS);
	    GL11.glVertex2f(x,y);
	    if (orientation == 0 || orientation == 1) {
	    	GL11.glVertex2f(x + width,y);
	    	GL11.glVertex2f(x + width,y + length);
	    	GL11.glVertex2f(x,y + length);
	    }
	    else {
	    	GL11.glVertex2f(x + length,y);
	    	GL11.glVertex2f(x + length,y + width);
	    	GL11.glVertex2f(x,y + width);
	    }
	    GL11.glEnd();
	}
	
	private void drawBar() {
		int barLength = 110;
		GL11.glColor3f(color2[0], color2[1], color2[2]);
		GL11.glBegin(GL11.GL_QUADS);
	    switch (orientation) {
	    	case 0: 
	    		GL11.glVertex2f(x,y);
	    		GL11.glVertex2f(x + width,y);
	    		GL11.glVertex2f(x + width,y + length - barLength);
	    		GL11.glVertex2f(x,y + length - barLength);
	    		break;
	    	case 1:
	    		GL11.glVertex2f(x,y + length);
	    		GL11.glVertex2f(x,y + barLength);
	    		GL11.glVertex2f(x + width,y + barLength);
	    		GL11.glVertex2f(x + width,y + length);
	    		break;
	    	case 2:
	    		GL11.glVertex2f(x,y);
	    		GL11.glVertex2f(x + length - barLength,y);
	    		GL11.glVertex2f(x + length - barLength,y + width);
	    		GL11.glVertex2f(x,y + width);
	    		break;
	    	case 3:
	    		GL11.glVertex2f(x + length,y);
	    		GL11.glVertex2f(x + length,y + width);
	    		GL11.glVertex2f(x + barLength,y + width);
	    		GL11.glVertex2f(x + barLength,y);
	    		break;
	    }
	    GL11.glEnd();
	}
	
	public void draw() {
		drawOutline(5);
		drawBase();
		drawBar();		
	}
	
	public void drawName(UnicodeFont font) {
		//int yOffSet = 0;
		for (String word: name.split(" ")) {
	        font.drawString(x,y, word, Color.blue);
			//yOffSet += 5;
		}
	}
}
