import java.util.*;

class Animation extends App {
    
    ArrayList<ArrayList<ArrayList<Vector2>>> animation;
    
    // currentFrame is used to track the frame of the animation
    // counter is used to determine the speed of the animation
    int currentFrame;
    int counter;
    
    void setup() {
        System.out.println("Press Q to quit.");
        System.out.println("Press R to rerun setup().");
        
        animation = new ArrayList<>();
        animation.add(new ArrayList<>());
        
        // adds a stroke arraylist to the first frame
        animation.get(currentFrame).add(new ArrayList<>());
        
        currentFrame = 0;
        counter = 0;
    }
    
    void loop() {
        
        boolean playAnimation = false;
        
        // arbitrary frame within animation
        ArrayList<ArrayList<Vector2>> drawing = animation.get(currentFrame);
    
        if (drawing.size() == 0) {
            drawing.add(new ArrayList<>());
        }
        if (mousePressed) { 
            System.out.println("Mouse pressed.");
            drawing.add(new ArrayList<>());
        }
        
        // arbitrary stroke within frame
        ArrayList<Vector2> currentStroke = drawing.get(drawing.size() - 1);
        
        if (mouseHeld) { 
            System.out.println("Mouse held."); 
            currentStroke.add(new Vector2(mousePosition));
        }
        if (mouseReleased) { 
            System.out.println("Mouse released."); 
        }
        if (keyPressed('S')) { 
            System.out.println("Key S pressed."); 
            animation.add(currentFrame + 1, new ArrayList<>());
            currentFrame++;
            drawing = animation.get(currentFrame);
        }
        if (keyToggled('P')) { 
            System.out.println("Key P toggled.");
            counter++;
            
            // determines the speed of the animation
            if (counter > 15) {
                playAnimation = true;
                counter = 0;
            }
        }
        // playAnimation is true if enough time has passed between frames
        if (keyPressed('.') || playAnimation) {
            currentFrame = Math.floorMod(currentFrame + 1, animation.size());
            drawing = animation.get(currentFrame);
        }
        if (keyPressed(',')) {
            currentFrame = Math.floorMod(currentFrame - 1, animation.size());
            drawing = animation.get(currentFrame);
        }
        if (keyPressed('X')) {
            for (int t = 0; t < drawing.size(); t++) {
                ArrayList<Vector2> b = drawing.get(t);
                
                for (int l = 0; l < b.size(); ++l) {
                    Vector2 a = new Vector2(b.get(l).x * (-1), b.get(l).y);
                    b.set(l, a);
                }
            }
        }
        if (keyPressed('Y')) {
            for (int t = 0; t < drawing.size(); t++) {
                ArrayList<Vector2> b = drawing.get(t);
                
                for (int l = 0; l < b.size(); ++l) {
                    Vector2 a = new Vector2(b.get(l).x, b.get(l).y * (-1));
                    b.set(l, a);
                }
            }
        }
        
        // Draws the frame
        for (int x = 0; x < drawing.size(); ++x) {
            ArrayList<Vector2> stroke = drawing.get(x);
            
            for (int i = 0; i < stroke.size() - 1; ++i) {
                drawLine(stroke.get(i), stroke.get(i + 1), Vector3.black);
            }
        }
    }
    
    public static void main(String[] arguments) {
        App app = new Animation();
        app.setWindowBackgroundColor(1.0, 1.0, 1.0);
        app.setWindowSizeInWorldUnits(8.0, 8.0);
        app.setWindowCenterInWorldUnits(0.0, 0.0);
        app.setWindowHeightInPixels(512);
        app.setWindowTopLeftCornerInPixels(64, 64);
        app.run();
    }
    
}