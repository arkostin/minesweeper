import javax.swing.JFrame;

class Main{
    //Initializing variables and such
    private static JFrame window;
    private static Display disp;
    private static int HEIGHT = 1000;
    private static int WIDTH = 800;
    private static int mouse_x;
    private static int mouse_y;

    public static void main(String[] args){
        init();
    }
    
    public static void init(){
        //Create instances of Display and JFrame, and add the Display instance to the JFrame window
        disp = new Display();
        window = new JFrame();
        window.setSize(HEIGHT, WIDTH);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setVisible(true);
        window.getContentPane().add(disp);
    }
}
