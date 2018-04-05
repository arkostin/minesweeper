

class Cell{
    //Declare variables
    public boolean is_clicked;
    public boolean is_bomb;
    public boolean is_flagged;
    public int neighbour_count;

    public Cell(){
        is_clicked = false;
        is_bomb = false;
        is_flagged = false;
    }
}
