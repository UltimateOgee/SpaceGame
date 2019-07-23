package cs2.game

import scalafx.Includes._
import cs2.util.Vec2
import scalafx.scene.paint.Color
import scalafx.application.JFXApp
import scalafx.scene.canvas.Canvas
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent
import scala.util.Random
import scalafx.scene.text.Font

/**
 * main object that initiates the execution of the game, including construction
 *  of the window.
 *  Will create the stage, scene, and canvas to draw upon. Will likely contain or
 *  refer to an AnimationTimer to control the flow of the game.
 */
object SpaceGameApp extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "SpaceGame"
    scene = new Scene(600, 800) {
      val canvas = new Canvas(600, 800)
      content = canvas

      //our stack to hold game states (rewind feature):
      var gS = scala.collection.mutable.Stack[GameState]()
      var gQ = scala.collection.mutable.Queue[GameState]()
      var rewind = false

      //this is for scrolling background:
      var vPos = 0
      var vPos1 = 0
      var vPos2 = 0

      var keys: Set[String] = Set()
      var gameOver: Boolean = false
      
      val g = canvas.getGraphicsContext2D
      g.setFill(Color.Black)
      g.fillRect(0, 0, 600, 800)

      //load up images before doing animations
      val playerImg = new Image("file:lib/images/PlayerShip.png")
      val playerImgFast = new Image("file:lib/images/PlayerShip-fast.png")
      val playerImgRight = new Image("file:lib/images/PlayerShip-right.png")
      val playerImgLeft = new Image("file:lib/images/PlayerShip-left.png")
      val bulletImg = new Image("file:lib/images/bullet.png")
      val enemyBulletImg = new Image("file:lib/images/bulletEnemy.png")
      val enemyImgB = new Image("file:lib/images/EnemyShipBlue.png")
      val enemyImgR = new Image("file:lib/images/EnemyShipRed.png")
      val starsImg = new Image("file:lib/images/stars.png")
      val starsImg1 = new Image("file:lib/images/redStars.png")
      val starsImg2 = new Image("file:lib/images/brownStars.png")
      val bossImg = new Image("file:lib/images/boss.png")
      val bossImg1 = new Image("file:lib/images/bossBroken.png")
      val bossImg2 = new Image("file:lib/images/bossBroken1.png")
      
      var pastPlayer = new Player(false, playerImg, new Vec2(0,0), bulletImg)

      var bossExists = false
      var boss = new Boss(bossImg, new Vec2(300, 50), enemyBulletImg)
      var bossLives: Int = 3
      
      var player = new Player(false, playerImg, new Vec2(300, 700), bulletImg)
      var playerLives: Int = 3
      var playerScore: Int = 0

      var bullets = scala.collection.mutable.Buffer[Bullet]()
      var enemyBullets = scala.collection.mutable.Buffer[Bullet]()

      var swarm = new EnemySwarm(6, 8)
      //this doesn't really reset the swarm, but creates it the first time:
      swarm.resetSwarm()
      var right: Boolean = true

      //clone methods for bullets:
      def cloneBullets(b: scala.collection.mutable.Buffer[Bullet]): scala.collection.mutable.Buffer[Bullet] = {
        var newBullets = scala.collection.mutable.Buffer[Bullet]()
        for (bullet <- b) {
          newBullets += bullet.cloneBullet
        }
        newBullets
      }

      def updateSwarmPos() {
        if (gst.swarm.xPosition < 100) right = true
        if (gst.swarm.xPosition > 150) right = false
        if (right) gst.swarm.updatePos(10, 10)
        if (!right) gst.swarm.updatePos(-10, 10)
      }

      def endGameKeys() {
        canvas.onKeyPressed =
          (e: KeyEvent) => {
            if (e.code == KeyCode.Q) System.exit(0)
            if (e.code == KeyCode.Enter) {
              gameInSession = true
              gst.swarm.resetSwarm
              playerLives = 3
              playerScore = 0
              g.setFill(Color.Black)
            }
          }
        canvas.requestFocus
      }

      def scrolling(reset: Boolean) {
        g.setFill(Color.Black)
        //392 by 830 is the size of my scrolling background png
        //600 by 800 is the size of my canvas
        if (!reset) {
          g.drawImage(starsImg, 0, gst.vPos, 600, 830)
          gst.vPos = gst.vPos + 1
        } else {
          gst.vPos = 0
          g.drawImage(starsImg, 0, gst.vPos, 600, 830)
        }
        if (!reset) {
          g.drawImage(starsImg, 0, gst.vPos - 830, 600, 830)
          gst.vPos = gst.vPos + 1
        } else {
          gst.vPos = 0
          g.drawImage(starsImg, 0, gst.vPos - 830, 600, 830)
        }
      }
      def scrolling1(reset: Boolean) {
        g.setFill(Color.Black)
        //392 by 830 is the size of my scrolling background png
        //600 by 800 is the size of my canvas
        if (!reset) {
          g.drawImage(starsImg1, 0, gst.vPos1, 600, 746)
          gst.vPos1 = gst.vPos1 + 2
        } else {
          gst.vPos1 = 0
          g.drawImage(starsImg1, 0, gst.vPos1, 600, 746)
        }
        if (!reset) {
          g.drawImage(starsImg1, 0, gst.vPos1 - 746, 600, 746)
          gst.vPos1 = gst.vPos1 + 2
        } else {
          gst.vPos1 = 0
          g.drawImage(starsImg1, 0, gst.vPos1 - 746, 600, 746)
        }
      }
      def scrolling2(reset: Boolean) {
        g.setFill(Color.Black)
        //392 by 830 is the size of my scrolling background png
        //600 by 800 is the size of my canvas
        if (!reset) {
          g.drawImage(starsImg2, 0, gst.vPos2, 600, 772)
          gst.vPos2 = gst.vPos2 + 3
        } else {
          gst.vPos2 = 0
          g.drawImage(starsImg2, 0, gst.vPos2, 600, 772)
        }
        if (!reset) {
          g.drawImage(starsImg2, 0, gst.vPos2 - 772, 600, 772)
          gst.vPos2 = gst.vPos2 + 3
        } else {
          gst.vPos2 = 0
          g.drawImage(starsImg2, 0, gst.vPos2 - 772, 600, 772)
        }
      }

      var gst = new GameState(player, swarm, vPos, vPos1, vPos2, enemyBullets, bullets, boss)

      def displayAll() {
        var reset = false
        if (gst.vPos > 829) reset = true
        scrolling(reset)
        
        var reset1 = false
        if (gst.vPos1 > 745) reset1 = true
        scrolling1(reset1)
        
        var reset2 = false
        if (gst.vPos2 > 771) reset2 = true
        scrolling2(reset2)

        gst.player.display(g)
        gst.swarm.display(g)
        if(bossExists) gst.boss.display(g)

        //show hearts remaining:
        var buts = scala.collection.mutable.Buffer[Button]()
        var pic = new Image("file:lib/images/heart.png")
        for (x <- 0 until playerLives) {
          buts += new Button(pic, new Vec2(20 + 30 * x, 20))
        }
        
        if(bossLives == 3) boss.img = bossImg
        if(bossLives == 2) boss.img = bossImg1
        if(bossLives == 1) boss.img = bossImg2

        for (heart <- buts) {
          if (heart != null) {
            heart.display(g)
          }
        }

        //show player score:
        g.setFont(new Font("Arial", 20))
        g.setStroke(Color.White)
        g.strokeText("Score: " + playerScore, 20, 780)

        for (bullet <- gst.bullets.toArray) {
          bullet.display(g)
        }

        for (bullet <- gst.enemyBullets.toArray) {
          bullet.display(g)
        }
      }

      def mechanics() {
        //to keep track of when to reset player back to original position later:
        var killed = false

        counter -= 5

        if (keys.contains("right") && gst.player.getPos.x < 570){
          gst.player.moveRight()
          gst.player.img = playerImgRight
          //gst.boss.moveRight()
        }
        if (keys.contains("left") && gst.player.getPos.x > 10){
          gst.player.moveLeft()
          gst.player.img = playerImgLeft
          //gst.boss.moveLeft()
        }
        if (keys.contains("up") && gst.player.getPos.y > 10){
          gst.player.moveUp()
          gst.player.img = playerImgFast
          //gst.boss.moveUp()
        }
        if (keys.contains("down") && gst.player.getPos.y < 770){
          gst.player.moveDown()
          //gst.boss.moveDown()
        }
        if(!(keys.contains("right") || keys.contains("left") || keys.contains("up"))) {
          gst.player.img = playerImg
        }

        var rand = Random.nextInt(35) //everytime the display updates, create a random number between 0 (or 1..?) and 35
        var largeRand = Random.nextInt(150)
        if(largeRand == 1) if(!bossExists) {
          bossExists = true
          bossLives = 3
        }
        if (rand == 5 && gst.swarm.EnemyList.length != 0) gst.enemyBullets += gst.swarm.shoot() //if the random int was 5, add a bullet to enemy swarm
        if (rand == 6) updateSwarmPos //if the random int was 6, move the swarm downwards

        for (enemy <- gst.swarm.EnemyList.toArray) {
          for (bullet <- gst.bullets.toArray) {
            //an enemy intersects with a player's bullet:
            if (enemy.intersection(bullet, 20, 20)) {
              gst.swarm.EnemyList -= enemy
              playerScore += 1
            }
          }
        }
        for(bullet <- gst.bullets.toArray) {
          //boss intersects with a player's bullet:
          if(boss.intersection(bullet, 20, 20)) {
            if(bossLives > -1) {
              bossLives = bossLives - 1
            }
            else {
              if(bossExists) playerScore += 5
              bossExists = false
            }
          }
        }

        for (bullet <- gst.bullets.toArray) {
          if (bullet.timeStep()) gst.bullets -= bullet
        }

        for (bullet <- gst.enemyBullets.toArray) {
          //a player intersects with an enemy's bullet:
          if (gst.player.intersection(bullet, 15, 25)) killed = true
          if (bullet.timeStep()) gst.enemyBullets -= bullet
        }

        for (bullet <- gst.enemyBullets.toArray) {
          for (bullet1 <- gst.bullets.toArray) {
            //a player bullet intersects with an enemy's bullet:
            if (bullet.intersection(bullet1, 10, 20)) {
              gst.enemyBullets -= bullet
              gst.bullets -= bullet1
            }
          }
        }
        for (enemy <- gst.swarm.EnemyList) {
          //a player intersects with an enemy:
          if (gst.player.intersection(enemy, 25, 25)) killed = true
          if (gst.player.intersection(boss, 20, 20) && bossExists) killed = true
        }
        
        if (killed) {
          gst.player.moveTo(new Vec2(300, 700))         
          playerLives = playerLives - 1
        }
        
      }

      def checkForKeys() {
        canvas.onKeyPressed =
          (e: KeyEvent) => {
            if (e.code == KeyCode.D) keys += "right"
            if (e.code == KeyCode.A) keys += "left"
            if (e.code == KeyCode.W) keys += "up"
            if (e.code == KeyCode.S) keys += "down"
            if (e.code == KeyCode.R) keys += "rewind"
            if (e.code == KeyCode.Space && counter <= 5) {
              if(counter <= 10) {
                gst.bullets += gst.player.shoot()
                gst.player.shot = true
              }
              //if(counter <= 5 && bossExists) gst.enemyBullets += gst.boss.shoot()
              counter = 30
            }
          }
        canvas.requestFocus

        canvas.onKeyReleased =
          (e: KeyEvent) => {
            if (e.code == KeyCode.D) keys -= "right"
            if (e.code == KeyCode.A) keys -= "left"
            if (e.code == KeyCode.W) keys -= "up"
            if (e.code == KeyCode.S) keys -= "down"
            if (e.code == KeyCode.R) keys -= "rewind"
            if (e.code == KeyCode.Space) {
              gst.player.shot = false
            }
          }
        canvas.requestFocus
      }

      var gameInSession: Boolean = true

      var counter: Int = 5
      var prevTime: Long = 0
      val timer = AnimationTimer(t => {
        if (t - prevTime > 1e9 / 30) { //30 fps refresh rate here
          prevTime = t
          if (gameInSession) {
            g.fillRect(0, 0, 600, 800) //this will re-draw a black frame to clear out old drawings
            if (keys.contains("rewind")) rewind = true
            else rewind = false

            checkForKeys()
            if (rewind) {
              if (gS.size > 0) {
                gst = gS.pop()
              }
            } else {
              mechanics()
              gS.push(gst.clone(gst.player, gst.swarm, cloneBullets(gst.enemyBullets), cloneBullets(gst.bullets)))
              gQ.enqueue(gst.clone(gst.player, gst.swarm, cloneBullets(gst.enemyBullets), cloneBullets(gst.bullets)))
              if(gQ.size > 24) pastPlayer = gQ.dequeue.player
              boss.moveTo(new Vec2(pastPlayer.getPos.x, boss.getPos.y))
              if(pastPlayer.shot) {
                println("enemyShot")
                if(bossExists) gst.enemyBullets += gst.boss.shoot()
              }
            }

            displayAll()

            if (playerLives < 1) gameInSession = false
          } else {
            //game over screen
            bossExists = false
            g.setFill(Color.White)
            g.fillRect(0, 0, 600, 800)
            var button: Button = new Button(new Image("file:lib/images/gameover.png"), new Vec2(215, 300))
            button.displayWSize(g, 176, 84)
            g.setStroke(Color.Black)
            g.setFont(new Font("Arial", 15))
            g.strokeText("Press 'enter' to restart.", 220, 450)
            g.strokeText("Press 'q' to quit.", 250, 480)
            endGameKeys()
          }
        }
      })
      timer.start

    }
  }

}