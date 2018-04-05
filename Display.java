import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Display extends JPanel implements MouseListener{
    //Initializing variables and such
    private int grid_width = 40;
    private int grid_height = 30;
    private int cell_size = 20;
    public Cell[][] cells;
    private Color color_unopened = Color.gray;
    private Color color_grid_lines = Color.black;
    private Color color_opened = Color.lightGray;

    public Display(){
        //Initialize the 'cells' array with instances of the Cell class
        cells = new Cell[grid_width][grid_height];
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell();
            }
        }
        cells[10][10].is_bomb = true;
        //Add the Mouse Listener
        addMouseListener(this);
   }

    public void paint(Graphics g){
        //Paint the grid
        for(int i = 0; i < grid_width; i++){
            for(int j = 0; j < grid_height; j++){
                if(cells[i][j].is_clicked)
                    g.setColor(color_opened);
                else
                    g.setColor(color_unopened);

                g.fillRect(i * cell_size, j * cell_size, cell_size, cell_size);
            }
        }
        //Paint the grid lines
        g.setColor(color_grid_lines);
        for(int i = 0; i <= grid_width; i++){
            g.drawLine(i * cell_size, 0, i * cell_size, grid_height * cell_size);
        }
        for(int j = 0; j <= grid_height; j++){
            g.drawLine(0 ,j * cell_size, grid_width * cell_size , j * cell_size);
        }
    }
    
    //The method that gets invoked when you click stuff
    public void click(int x, int y){
        if(cells[x][y].is_bomb){
            //Stuff that happens if you click a bomb
        }
        else{
            System.out.println("Cell " + x + ", " + y + " has been clicked");
            cells[x][y].is_clicked = true;
            this.repaint();
            if(get_neighbours(x, y) == 0){
                for(int i = x - 1; i <= x + 1; i++){
                    for(int j = y - 1; j <= y + 1; j++){
                        //Test if the neighbour exists
                        if(i >= 0 && j >= 0 && i < grid_width && j < grid_height && (i != x || j != y) && !cells[i][j].is_clicked && get_neighbours(i,j) == 0){
                            click(i, j);
                        }
                    }
                }
            }
        }
    }

    //Get the number of bomb-containing neighbours
    public int get_neighbours(int x, int y){
        int neighbours = 0;
        for(int i = x - 1; i <= x + 1; i++){
            for(int j = y - 1; j <= y + 1; j++){
                //Test if the neighbour exists
                if(i >= 0 && j >= 0 && i < grid_width && j < grid_height && (i != x || j != y)){
                    if(cells[i][j].is_bomb)
                        neighbours++;
                }
            }
        }
        return neighbours;
    }

    //Overriding MouseListener methods, as ya do
    public void mouseClicked(MouseEvent e){
        //Invoke the 'click' method on the place in the JPanel that the pointed was clicked at
        click(e.getX()/cell_size,e.getY()/cell_size);
    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mousePressed(MouseEvent e){

    }
}
