package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.canvas.GraphicsContext

/** A graphical sprite object used for gaming or other visual displays
 *  
 *  @constructor create a new sprite based on the given image and initial location
 *  @param img the image used to display this sprite
 *  @param pos the initial position of the '''center''' of the sprite in 2D space
 */
abstract class Sprite (var img:Image, protected var pos:Vec2) {
  
  def intersection(s:Sprite, xLeniency:Int, yLeniency:Int):Boolean = {
    //this method seems a little jittery. Do further investigation/ask someone.
    if(this.pos.x+xLeniency>s.pos.x && this.pos.x-xLeniency<s.pos.x && this.pos.y+yLeniency>s.pos.y && this.pos.y-yLeniency< s.pos.y) true
    else false
  }
  
  def setImage(image:Image) {
    this.img = image
  }
  
  /** moves the sprite a relative amount based on a specified vector
   *  
   *  @param direction - an offset that the position of the sprite should be moved by
   *  @return none/Unit
   */
  def move (direction:Vec2) {
    pos += direction //built in + def in vec2
  }
  
  def getPos():Vec2 = pos
  
  /** moves the sprite to a specific location specified by a vector (not a relative movement)
   *  
   *  @param location - the new location for the sprite's position
   *  @return none/Unit
   */
  def moveTo (location:Vec2) {
    pos.x = location.x
    pos.y = location.y
  }
  
  /** Method to display the sprite at its current location in the specified Graphics2D context
   *  
   *  @param g - a GraphicsContext object capable of drawing the sprite
   *  @return none/Unit
   */
  def display (g:GraphicsContext) {
    g.drawImage(img, pos.x, pos.y, 25, 25)
  }
  def displayLarge (g:GraphicsContext) {
    g.drawImage(img, pos.x, pos.y, 75, 75)
  }
  def displayWSize (g:GraphicsContext, x:Int, y:Int) {
    g.drawImage(img, pos.x, pos.y, x, y)
  }
  
}