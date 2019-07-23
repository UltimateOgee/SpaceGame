package cs2.game

class GameState(var player:Player, var swarm:EnemySwarm, var vPos:Int, var vPos1:Int, var vPos2:Int, var enemyBullets:scala.collection.mutable.Buffer[Bullet], var bullets:scala.collection.mutable.Buffer[Bullet], var boss:Boss) {  
  def clone(player:Player, swarm:EnemySwarm, enemyBullets:scala.collection.mutable.Buffer[Bullet], bullets:scala.collection.mutable.Buffer[Bullet]):GameState = {
    //enemy bullets
      //bullet
        //image
        //pos
        //vel
    //enemy swarm
      //enemies
        //image
        //pos
        //bulletpic
    //player bullets
      //bullet
        //image
        //pos
        //vel
    //player
      //avatar
      //pos
      //bulletpic
    new GameState(player.clonePlayer, swarm.cloneSwarm, vPos, vPos1, vPos2, enemyBullets, bullets, boss.cloneBoss())
  }
}