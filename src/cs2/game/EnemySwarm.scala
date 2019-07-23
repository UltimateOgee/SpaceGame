package cs2.game

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image
import cs2.util.Vec2
import scala.util.Random

/**
 * contains the control and logic to present a coordinated set of Enemy objects.
 *  For now, this class generates a "grid" of enemy objects centered near the
 *  top of the screen.
 *
 *  @param nRows - number of rows of enemy objects
 *  @param nCols - number of columns of enemy objects
 */
class EnemySwarm(private val nRows: Int, private val nCols: Int) extends ShootsBullets {

  var EnemyList = scala.collection.mutable.Buffer[Enemy]()

  val enemyImg = new Image("file:lib/images/EnemyShipGreen.png")
  val bulletImg = new Image("file:lib/images/bulletEnemy.png")

  def cloneSwarm():EnemySwarm = {
    var newSwarm = new EnemySwarm(nRows, nCols)
    for(e <- EnemyList) {
      newSwarm.EnemyList += e.cloneEnemy
    }
    newSwarm
  }
  
  def resetSwarm() {
    EnemyList = scala.collection.mutable.Buffer[Enemy]()
    for (y <- 0 until nRows) {
      for (x <- 0 until nCols) {
        var e: Enemy = new Enemy(enemyImg, new Vec2(100 + x * 50, 100 + y * 50), bulletImg)
        EnemyList += e
      }
    }
  }
  
  /**
   * method to display all Enemy objects contained within this EnemySwarm
   *
   *  @param g - the GraphicsContext to draw into
   *  @return none/Unit
   */

  def display(g: GraphicsContext) {
    for (enemy <- EnemyList) {
      enemy.display(g)
    }
  }

  def updatePos(xM: Int, yM: Int) {
    for (enemy <- EnemyList) {
      enemy.move(new Vec2(xM, yM))
    }
  }

  def xPosition(): Int = {
    if (EnemyList.length != 0) {
      EnemyList(0).position.x.toInt
    }
    else {
      //if we reach here, that means we need to reset the swarm and advance
      //the player to the next level:
      resetSwarm()
      0
    }
  }

  /**
   * overridden method of ShootsBullets. Creates a single, new bullet instance
   *  originating from a random enemy in the swarm. (Not a bullet from every
   *  object, just a single from a random enemy)
   *
   *  @return Bullet - the newly created Bullet object fired from the swarm
   */
  def shoot(): Bullet = {
    var x: Int = EnemyList.size
    EnemyList(Random.nextInt(x)).shoot()
  }

}