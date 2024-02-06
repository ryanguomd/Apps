import java.util.*;

class Game extends App {
    
    static class Player {
        Vector2 position;
        double radius;
        Vector3 color;
    }
    
    static class Turret {
        Vector2 position;
        double radius;
        Vector3 color;
        int framesSinceFired;
        int health;
        boolean alive;
    }
    
    static class Bullet {
        Vector2 position;
        Vector2 velocity;
        double radius;
        Vector3 color;
        boolean alive;
        int age;
        int type;
        
        static final int TYPE_PLAYER = 0;
        static final int TYPE_TURRET = 1;
    }
    
    Player player;
    
    Turret[] turrets;

    Bullet[] bullets;
    
    // fireBullet function
    void fireBullet(Vector2 position, Vector2 velocity, double radius, Vector3 color, int type) {
        for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
            Bullet bullet = bullets[bulletIndex];
            if (!bullet.alive) { 
                bullet.position = position;
                bullet.velocity = velocity;
                bullets[bulletIndex].radius = radius;
                bullet.color = color;
                bullet.alive = true;
                bullet.age = 0;
                bullet.type = type;
                
                break;
            }
        } 
    }
    
    void setup() {
        player = new Player();
        player.position = new Vector2(0.0, -40.0);
        player.radius = 4.0;
        player.color = Vector3.cyan;
        
        turrets = new Turret[2];
        for (int turretIndex = 0; turretIndex < turrets.length; ++turretIndex) {
            // create
            turrets[turretIndex] = new Turret();
            
            // initialize
            Turret turret = turrets[turretIndex];
            turret.radius = 8.0;                  
            turret.color = Vector3.red;           
            turret.health = 5;
            turret.alive = true;
        }
        
        turrets[0].position = new Vector2(-40.0, 0.0);
        turrets[1].position = new Vector2( 40.0, 0.0);
        
        bullets = new Bullet[256];
        for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
            bullets[bulletIndex] = new Bullet();
        }
    }
    
    // checks if the bullet has 'penetrated' the player or turret
    boolean circlecircleIntersection(double bulletRadius, Vector2 bulletPosition, double genRadius, Vector2 genPosition) {
        return bulletRadius + genRadius > Vector2.distanceBetween(bulletPosition, genPosition);
    }
    
    void loop() {
        // player
        if (keyHeld('W')) { 
            player.position.y += 1.0; 
        }
        if (keyHeld('A')) { 
            player.position.x -= 1.0; 
        }
        if (keyHeld('S')) { 
            player.position.y -= 1.0; 
        }
        if (keyHeld('D')) { 
            player.position.x += 1.0; 
        }
        
        drawCircle(player.position, player.radius, player.color);
        
        if (keyPressed('I')) { 
            fireBullet(player.position, Vector2.up, 1.0, player.color, Bullet.TYPE_PLAYER); 
        }
        if (keyPressed('J')) { 
            fireBullet(player.position, Vector2.left, 1.0, player.color, Bullet.TYPE_PLAYER); 
        }
        if (keyPressed('K')) { 
            fireBullet(player.position, Vector2.down, 1.0, player.color, Bullet.TYPE_PLAYER); 
        }
        if (keyPressed('L')) { 
            fireBullet(player.position, Vector2.right, 1.0, player.color, Bullet.TYPE_PLAYER); 
        }
        
        // turrets
        for (int turretIndex = 0; turretIndex < turrets.length; ++turretIndex) {
            Turret turret = turrets[turretIndex];
            
            if (turret.framesSinceFired++ == 32 && turret.alive) {
                turret.framesSinceFired = 0;
                
                fireBullet(turret.position, Vector2.directionVectorFrom(turret.position, player.position), 2.0, turret.color, Bullet.TYPE_TURRET);
            }
            
            if (turret.alive) {
                drawCircle(turret.position, turret.radius, turret.color); 
            }
        }
        
        // bullets
        for (int bulletIndex = 0; bulletIndex < bullets.length; ++bulletIndex) {
            Bullet bullet = bullets[bulletIndex];
            
            if (!bullet.alive) { 
                continue; 
            } // skip dead bullets
            
            if (bullet.type == bullet.TYPE_TURRET) {
                if (circlecircleIntersection(bullet.radius, bullet.position, player.radius, player.position)) {
                    reset();
                }
            } else {
                for (int turretIndex = 0; turretIndex < turrets.length; ++turretIndex) {
                    Turret turret = turrets[turretIndex];
                    
                    if (turret.alive) {
                        if (circlecircleIntersection(bullet.radius, bullet.position, turret.radius, turret.position)) {
                            bullet.alive = false;
                            turret.health -= 1;
                        }
                    }
                    
                    if (turret.health == 0) {
                        turret.alive = false;
                    }
                }
            }
            
            // kill bullets that are too old (they're probably off-screen anyway)
            if (bullet.age++ > 128) {
                bullet.alive = false;
            }
            
            // "physics"
            bullet.position = bullet.position.plus(bullet.velocity);
            
            // draw
            drawCircle(bullet.position, bullet.radius, bullet.color);
        }
    }
    
    public static void main(String[] arguments) {
        App app = new Game();
        app.setWindowBackgroundColor(0.0, 0.0, 0.0);
        app.setWindowSizeInWorldUnits(128.0, 128.0);
        app.setWindowCenterInWorldUnits(0.0, 0.0);
        app.setWindowHeightInPixels(512);
        app.setWindowTopLeftCornerInPixels(64, 64);
        app.run();
    }
}