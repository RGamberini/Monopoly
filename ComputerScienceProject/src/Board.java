import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Board {
	static int NAME = 0, COLOR = 1, PRICE = 2, ISDOUBLE = 3;
	String[][][] bob = new String[11][4][4];
	Tile firstTile;
	UnicodeFont font = null;
	//String BoardinfoPath = Board.class.getClassLoader().getResource("Boardinfo").getPath();
	//String BoardLayInfoPath = Board.class.getClassLoader().getResource("BoardLayInfo").getPath();
	//String BoardinfoPath = "C:\\Users\\rudy.gamberini\\Documents\\Adobe\\Boardinfo";
	//String BoardLayInfoPath = "C:\\Users\\rudy.gamberini\\Documents\\Adobe\\BoardLayInfo";
	String BoardinfoPath = "Boardinfo";
	String BoardLayInfoPath = "BoardLayInfo";
	float[] BGcolor, tileColor;
	float tileLength, tileWidth;
	List<Tile> tileList = new ArrayList<Tile>();
	int border = 5;
	String[] images;
	Texture[] textures;
	
	public Board(float[] BGColor, float[] TileColor, float TileLength, float TileWidth) {
		BGcolor = BGColor;
		tileColor = TileColor;
		tileLength = TileLength;
		tileWidth = TileWidth;
	}
	private void setupFonts() {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
		try {
			font = new UnicodeFont(awtFont);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());  // Create a default white color effect
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	private void createTile(String Name, float[] Color, float X, float Y, int Oreintation, int Price, int isDouble) {
		tileList.add(new Tile(X, Y, tileLength, tileWidth, tileColor, Color,  Name, Price, Oreintation, isDouble));
	}
	private void setupArrayBoard() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(BoardinfoPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text;
		String[] neo = new String[6];
		int a,b;
		try {
			while (in.ready()) { 
				text = in.readLine();
				neo = text.split(",");
				a = neo[0].charAt(0) - 97;
				b = neo[1].charAt(0) - 97;
				bob[a][b][0] = neo[2];
				bob[a][b][1] = neo[3];
				bob[a][b][2] = neo[4];
				bob[a][b][3] = neo[5];
			}
			in.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int countLines(String path) throws IOException {
		int lines = 0;
		BufferedReader br = new BufferedReader(new FileReader(path));
		while (br.readLine() != null) {
		    lines++;
		}
		br.close();
		return lines;
	}
	
	private void setupBoard() {
		BufferedReader in = null;
		String tileName;
		String[] strTileColor;
		String strTilePrice;
		float[] tileColor;
		try {
			in = new BufferedReader(new FileReader(BoardLayInfoPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text, twoChar;
		int num1, num2;
		int lineCount = 0, maxLines;
		int oreintation;
		int isDouble;
		float x = 0, y = 0;
		try {
			maxLines = countLines(BoardLayInfoPath);
			while (in.ready()) {
				text = in.readLine();
				for (int i = 0; i < text.length(); i += 2) {
					twoChar = "" + text.charAt(i) + text.charAt(i + 1);
					if (!twoChar.equals("--")) {
						num1 = twoChar.charAt(0) - 97;
						num2 = twoChar.charAt(1) - 97;
						if (lineCount == 0)
							oreintation = Tile.DOWN;
						else if (lineCount == maxLines - 1)
							oreintation = Tile.UP;
						else if(i == 0)
							oreintation = Tile.RIGHT;
						else
							oreintation = Tile.LEFT;
						switch(oreintation) {
							case Tile.UP: {
								x = (i / 2) * (tileWidth + border);
								y = lineCount * (tileWidth + border) + tileLength;
								break;
							}
							case Tile.DOWN: {
								x = (i / 2) * (tileWidth + border);
								y = lineCount * (tileWidth + border) + tileWidth;
								break;
							}
							case Tile.RIGHT: {
								x = (i / 2) * tileWidth;
								y = lineCount * (tileWidth + border) + tileLength;
								break;
							}
							
							case Tile.LEFT: {
								x = (i / 2) * tileWidth + border;
								y = lineCount * (tileWidth + border) + tileLength;
								break;
							}
						}
						tileName = bob[num1][num2][NAME];
						tileColor = new float[3];
						strTileColor = bob[num1][num2][COLOR].split(" ");
						strTilePrice = bob[num1][num2][PRICE];
						isDouble = Integer.parseInt(bob[num1][num2][ISDOUBLE]);
						for (int j = 0; j < strTileColor.length; j++)
							tileColor[j] = Float.parseFloat(strTileColor[j]);
						createTile(tileName, tileColor, x + 200, y + 50, oreintation, Integer.parseInt(strTilePrice), isDouble);
					}
				}
				lineCount++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Piece firstPiece;
	public void init() {
		setupFonts();
		setupArrayBoard();
		setupBoard();
		//firstTile = new Tile(100, 100, 150f, 100f, tileColor, new float[] {.54f, .27f, .75f}, "fuck", 3);
		firstPiece = new Piece(12);
		firstPiece.init();
	}
	
	public void draw() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glDisable(GL11.GL_BLEND);
		//firstTile.draw();
		for (Tile tile: tileList)
			tile.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for (Tile tile: tileList){
			tile.drawTexture(font);
			firstPiece.draw();
		}
		//firstTile.drawName(font);
	}
}