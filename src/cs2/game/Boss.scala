package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.canvas.GraphicsContext

/** The Boss representation for a simple game based on sprites. Handles all 
 *  information regarding the Boss's positions, movements, and abilities.
 *  
 *  @param avatar the image representing the Boss
 *  @param initPos the initial position of the '''center''' of the Boss
 *  @param bulletPic the image of the bullets fired by this Boss
 */
class Boss(avatar:Image, initPos:Vec2, private val bulletPic:Image) 
                extends Sprite(avatar, initPos) with ShootsBullets {

  def cloneBoss():Boss = {
    new Boss(avatar, pos.clone, bulletPic)
  }
  
  /** moves the Boss sprite one "step" to the left.  The amount of this 
   *  movement will likely need to be tweaked in order for the movement to feel 
   *  natural.
   *  
   *  @return none/Unit
   */
  def moveLeft() {
    move(new Vec2(-5, 0))
  }
  
  /** moves the Boss sprite one "step" to the right (see note above)
   * 
   *  @return none/Unit
   */
  def moveRight() {
    move(new Vec2(5, 0))
  }
  
  def moveUp() {
    move(new Vec2(0, 5))
  }
  
  def moveDown() {
    move(new Vec2(0, -5))
  }
  
  /** creates a new Bullet instance beginning from the Boss, with an 
   *  appropriate velocity
   * 
   *  @return Bullet - the newly created Bullet object that was fired
   */
  def shoot():Bullet = {
    var b = new Vec2(new Vec2(getPos.x, getPos.y))
    new Bullet(bulletPic, b, new Vec2(0, 10))
  }
  
}