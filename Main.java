import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class Main implements MouseListener{
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

    //Overriding the MouseListener methods, as usual
    @Override
    public void mouseClicked(MouseEvent e){
        mouse_x = e.getX();
        mouse_y = e.getY();
        System.out.println("Cell " + mouse_x + ", " + mouse_y + " has been clicked");

        disp.click(mouse_x, mouse_y);

        window.repaint();
    }
    @Override
    public void mouseExited(MouseEvent e){

    }
    @Override
    public void mouseEntered(MouseEvent e){

    }
    @Override
    public void mouseReleased(MouseEvent e){

    }
    @Override
    public void mousePressed(MouseEvent e){

    }
}
