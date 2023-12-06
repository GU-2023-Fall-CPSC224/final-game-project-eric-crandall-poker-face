/* Class Name: PlayerIcons
 * Desc: A simple helper class to get all the player icons from media folder and loads them into an array list of images
 * Notes: *u*
 */
package edu.gonzaga.utils;

 import java.util.ArrayList;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;
 
 import javax.imageio.ImageIO;
 
 public class PlayerIcons {
     private final ArrayList<BufferedImage> icons;
 
     /* Default Value Constructor
      * Intializes an empty arraylist of buffered images
      */
     public PlayerIcons() {
         icons = new ArrayList<>();
         loadIcons();
     }
 
     /* Method Name: loadIcons()
      * Returns: N/A (Void)
      * Desc: Loads icons from media folder into icons arrayList, hardcoding the number of player for now
      */
     public void loadIcons() {
         if (!this.icons.isEmpty()) return;
         int temp;
         for(int i = 0; i < 7; i++) {
             temp = i + 1;
             try {
                 System.out.println("Trying to read new icon");
                 System.out.println("Icon" + temp + ".png");
                 BufferedImage newIcon = ImageIO.read(new File("media/playerIcons/Icon" + temp + ".png"));
                 this.icons.add(newIcon);
                 System.out.println("Succesfully Loaded Icon" + (i+1) +".png");
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
 
     /* Method Name: getIcons()
      * Returns: ArrayList<BufferedImage>
      * Desc: returns this.icons
      */
     public ArrayList<BufferedImage> getIcons() {
         return this.icons;
     }
 }
