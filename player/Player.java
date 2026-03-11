package player;

public abstract class Player {
    protected String name;
    protected boolean hiddenStatus;

    public Player(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void updateHidden(boolean val)
    {
        hiddenStatus = val;
    }

    public abstract String makeMove();
}