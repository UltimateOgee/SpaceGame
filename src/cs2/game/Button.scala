package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2

class Button(pic:Image, initPos:Vec2) extends Sprite(pic, initPos) {
  
  def position():Vec2 = {
    initPos
  }
 
}