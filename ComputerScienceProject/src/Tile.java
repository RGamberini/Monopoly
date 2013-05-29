import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;

public class Tile {
	static final int UP = 1, DOWN = 0, LEFT = 2, RIGHT = 3;
	float x, y;
	float width, length;
	float[] color, color2;
	String name;
	int price;
	int orientation;
	int barLength = 80;
	int border = 5;
	Texture picture;
	int isDouble;
	float expansion;
	
	public Tile(float X, float Y, float Length, float Width, float[] Color, float[] Color2, String Name, int Price, int Orientation, int IsDouble) {
		x = X;
		y = Y;
		width = Width;
		length = Length;
		color = Color;
		color2 = Color2;
		name = Name;
		orientation = Orientation;
		price = Price;
		isDouble = IsDouble;
	    expansion = (isDouble == 1) ?  width + border : 0;
	}
	
	private void drawOutline(int thickness) {
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
	    
	    if (orientation == Tile.UP || orientation == Tile.DOWN || isDouble == 1) {
	    	GL11.glVertex2f(x - thickness - expansion,y - thickness);
			GL11.glVertex2f(x + width + thickness,y - thickness);
			GL11.glVertex2f(x + width + thickness,y + length + thickness);
			GL11.glVertex2f(x  - thickness - expansion,y + length + thickness);
	    }
	    else {
	    	GL11.glVertex2f(x - thickness,y - thickness);
	    	GL11.glVertex2f(x + length + thickness,y - thickness);
	    	GL11.glVertex2f(x + length + thickness,y + width + thickness);
	    	GL11.glVertex2f(x - thickness,y + width + thickness);
	    }
	    GL11.glEnd();
	}
	
	private void drawBase() {
		GL11.glColor3f(color[0], color[1], color[2]);
		GL11.glBegin(GL11.GL_QUADS);
	    if (orientation == Tile.UP || orientation == Tile.DOWN || isDouble == 1) {
	    	GL11.glVertex2f(x - expansion,y);
	    	GL11.glVertex2f(x + width,y);
	    	GL11.glVertex2f(x + width,y + length);
	    	GL11.glVertex2f(x - expansion,y + length);
	    }
	    else {
	    	GL11.glVertex2f(x,y);
	    	GL11.glVertex2f(x + length,y);
	    	GL11.glVertex2f(x + length,y + width);
	    	GL11.glVertex2f(x,y + width);
	    }
	    GL11.glEnd();
	}
	
	private void drawBar() {
		GL11.glColor3f(color2[0], color2[1], color2[2]);
		GL11.glBegin(GL11.GL_QUADS);
		if (isDouble != 1) {
	    switch (orientation) {
	    	case Tile.UP: 
	    		GL11.glVertex2f(x,y);
	    		GL11.glVertex2f(x + width,y);
	    		GL11.glVertex2f(x + width,y + length - barLength);
	    		GL11.glVertex2f(x,y + length - barLength);
	    		break;
	    	case Tile.DOWN:
	    		GL11.glVertex2f(x,y + length);
	    		GL11.glVertex2f(x,y + barLength);
	    		GL11.glVertex2f(x + width,y + barLength);
	    		GL11.glVertex2f(x + width,y + length);
	    		break;
	    	case Tile.LEFT:
	    		GL11.glVertex2f(x,y);
	    		GL11.glVertex2f(x + length - barLength,y);
	    		GL11.glVertex2f(x + length - barLength,y + width);
	    		GL11.glVertex2f(x,y + width);
	    		break;
	    	case Tile.RIGHT:
	    		GL11.glVertex2f(x + length,y);
	    		GL11.glVertex2f(x + length,y + width);
	    		GL11.glVertex2f(x + barLength,y + width);
	    		GL11.glVertex2f(x + barLength,y);
	    		break;
	    }
		}
	    GL11.glEnd();
	}
	
	public void draw() {
		drawOutline(border);
		drawBase();
		drawBar();		
	}
	
	public void drawTexture(UnicodeFont font) {
		drawName(font);
		drawPrice(font);
	}
	
	public void drawPrice(UnicodeFont font) {
		if (price != 0) 
			font.drawString(x, y, "$" + price);
	}
	
	public void drawName(UnicodeFont font) {
		int yOffSet = 0;
		String[] words = name.split(" ");
		for (int i = 0; i < words.length; i++) {
//	        if (orientation !=  Tile.LEFT)
//	        	font.drawString(x,y + yOffSet, word, Color.black);
//	        else
//	        	font.drawString(x + (length - barLength) , y + yOffSet, word, Color.black);
			switch (orientation) {
	    		case Tile.UP: 
	    			font.drawString(x,y + length + border + yOffSet, words[i], Color.black);
	    			break;
	    		case Tile.DOWN:
	    			font.drawString(x,y - border * 4 - 8 * (words.length - i), words[i], Color.black);
	    			break;
	    		case Tile.LEFT:
	    			font.drawString(x + length + border,y - 8 * (words.length - i) + 35, words[i], Color.black);
	    			break;
	    		case Tile.RIGHT:
	    			font.drawString(x - border - 4 * words[i].length() - 25,y - 8 * (words.length - i) + 35, words[i], Color.black);
	    			break;
	    }
			yOffSet += 15;
		}
	}
}
