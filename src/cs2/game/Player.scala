package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.canvas.GraphicsContext

/** The player representation for a simple game based on sprites. Handles all 
 *  information regarding the player's positions, movements, and abilities.
 *  
 *  @param avatar the image representing the player
 *  @param initPos the initial position of the '''center''' of the player
 *  @param bulletPic the image of the bullets fired by this player
 */
class Player(var shot:Boolean,  avatar:Image, initPos:Vec2, private val bulletPic:Image) 
                extends Sprite(avatar, initPos) with ShootsBullets {
  
  def clonePlayer():Player = {
    new Player(shot, avatar, pos.clone, bulletPic)
  }
  
  /** moves the player sprite one "step" to the left.  The amount of this 
   *  movement will likely need to be tweaked in order for the movement to feel 
   *  natural.
   *  
   *  @return none/Unit
   */
  def moveLeft() {
    move(new Vec2(-10, 0))
  }
  
  /** moves the player sprite one "step" to the right (see note above)
   * 
   *  @return none/Unit
   */
  def moveRight() {
    move(new Vec2(10, 0))
  }
  
  def moveUp() {
    move(new Vec2(0, -10))
  }
  
  def moveDown() {
    move(new Vec2(0, 10))
  }
  
  /** creates a new Bullet instance beginning from the player, with an 
   *  appropriate velocity
   * 
   *  @return Bullet - the newly created Bullet object that was fired
   */
  def shoot():Bullet = {
    var b = new Vec2(initPos)
    new Bullet(bulletPic, b, new Vec2(0, -20))
  }
  
}



