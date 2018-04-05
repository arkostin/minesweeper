import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Display extends JPanel implements MouseListener{
    //Initializing variables and such
    private int grid_width = 40;
    private int grid_height = 30;
    private int cell_size = 20;
    public Cell[][] cells;
    private Color color_unopened = Color.gray;
    private Color color_grid_lines = Color.white;
    private Color color_opened = Color.lightGray;
    private Color color_flagged = Color.red; 
    private int mines = 240;
    private int flagged = 0;
    private int opened = 0;
    private boolean first_click = true;

    public Display(){
        //Initialize the 'cells' array with instances of the Cell class
        cells = new Cell[grid_width][grid_height];
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                cells[i][j] = new Cell();
                cells[i][j].x = i;
                cells[i][j].y = j;
            }
        }
        //Add the mines
        add_mines(mines);
        //Add the Mouse Listener
        addMouseListener(this);
    }
    //Function that gets called when you either flag or open every single square on the board, correctly
    public void win(){
        System.out.println("Congrats, you've done it!");
    }
    //Function that gets called when you click a bomb/blow your legs off
    public void lose(){
        System.out.println("This happens when you lose);
    }
    //Add a number of mines to the minefield, randomly
    public void add_mines(int x){
        ArrayList<Cell> available_cells = new ArrayList<Cell>();
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                if(!cells[i][j].is_clicked && !cells[i][j].is_bomb && !cells[i][j].is_flagged)
                   available_cells.add(cells[i][j]);
            }
        }
        Random rand = new Random();
        for(int i = 0; i < x; i++){
            int foo = rand.nextInt(available_cells.size());
            Cell bar = available_cells.get(foo);
            int x_coord = bar.x;
            int y_coord = bar.y;
            cells[x_coord][y_coord].is_bomb = true;
            available_cells.remove(foo);
        }
    }
    public void paint(Graphics g){
        //Paint the grid
        for(int i = 0; i < grid_width; i++){
            for(int j = 0; j < grid_height; j++){
                if(cells[i][j].is_clicked)
                    g.setColor(color_opened);
                else if(cells[i][j].is_flagged)
                    g.setColor(color_flagged);
                else
                    g.setColor(color_unopened);

                g.fillRect(i * cell_size, j * cell_size, cell_size, cell_size);
                //Paint the numbers, if applicable to cell
                if(cells[i][j].is_clicked && cells[i][j].neighbour_count > 0){
                    set_num_color(g, cells[i][j].neighbour_count);
                    g.setFont(new Font("Serif", Font.BOLD, 11));
                    g.drawString(Integer.toString(cells[i][j].neighbour_count), i * cell_size + cell_size/3, j * cell_size + cell_size * 3 / 4);
                }
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
        if(first_click)
            first_click(x, y);
        if(cells[x][y].is_bomb){
            //Stuff that happens if you click a bomb
        }
        else if(cells[x][y].is_flagged){
            //Stuff that happens if you click a flag
        }
        else if(cells[x][y].is_clicked && cells[x][y].neighbour_count > 0){
            number_open_neighbours(x, y);
        }
        else{
            System.out.println("Cell " + x + ", " + y + " has been clicked");
            cells[x][y].is_clicked = true;
            opened++;
            this.repaint();
            cells[x][y].neighbour_count = get_neighbours(x,y);
            if(get_neighbours(x, y) == 0){
                for(int i = x - 1; i <= x + 1; i++){
                    for(int j = y - 1; j <= y + 1; j++){
                        //Test if the neighbour exists
                        if(i >= 0 && j >= 0 && i < grid_width && j < grid_height && (i != x || j != y) && !cells[i][j].is_clicked){
                            click(i, j);
                        }
                    }
                }
            }
        }
        if(flagged + opened >= grid_width * grid_height && flagged == mines){
            win();
        }
    }
    public void first_click(int x, int y){
        if(cells[x][y].is_bomb){
            cells[x][y].is_bomb = false;
            mines--;
            add_mines(1);
        }
        first_click = false;
    }
    //Opens the surrounding unflagged squares if the user clicks on a numbered cell that has an equivalent number of surrounding flagged squares
    public void number_open_neighbours(int x, int y){
        int flagged_neighbour_count = 0;
        for(int i = x - 1; i <= x + 1; i++){
            for(int j = y - 1; j <= y + 1; j++){
                //Test if the neighbour exists
                if(i >= 0 && j >= 0 && i < grid_width && j < grid_height && (i != x || j != y) && cells[i][j].is_flagged){
                    flagged_neighbour_count++;
                }
            }
        }
        if(flagged_neighbour_count == cells[x][y].neighbour_count){
            for(int i = x - 1; i <= x + 1; i++){
                for(int j = y - 1; j <= y + 1; j++){
                    //Test if the neighbour exists
                    if(i >= 0 && j >= 0 && i < grid_width && j < grid_height && (i != x || j != y) && !cells[i][j].is_clicked && !cells[i][j].is_flagged){
                        click(i,j);
                    }
                }
            }
        }
    }
    //This gets invoked when you right click a cell, and it flags said cell
    public void flag(int x, int y){
        if(cells[x][y].is_flagged){
           cells[x][y].is_flagged = false;
           flagged--;
        }
        else{
           cells[x][y].is_flagged = true;
           flagged++;
        }
        if(flagged + opened >= grid_width * grid_height && flagged == mines){
            win();
        }
        this.repaint();
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
    //Sets the color before painting a clicked cell's 'neighbours' number based on the amount of neighbours
    public void set_num_color(Graphics g, int neighbours){
        if(neighbours == 1)
            g.setColor(new Color(0, 0, 255));
        else if(neighbours == 2)
            g.setColor(new Color(0, 168, 0));
        else if(neighbours == 3)
            g.setColor(new Color(216, 0, 0));
        else if(neighbours == 4)
            g.setColor(new Color(181, 0, 129));
        else
            g.setColor(new Color(0, 0, 0));
    }

    //Overriding MouseListener methods, as ya do
    public void mouseClicked(MouseEvent e){
        //Invoke the click or the flag method, based on which button was used
        if(e.getButton() == MouseEvent.BUTTON1)
            click(e.getX()/cell_size,e.getY()/cell_size);
        else if(e.getButton() == MouseEvent.BUTTON3)
            flag(e.getX()/cell_size,e.getY()/cell_size);
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
