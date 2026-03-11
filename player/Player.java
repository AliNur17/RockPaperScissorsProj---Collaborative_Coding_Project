package player;

// Abstract base class for all player types.
// Coding to the Interface: Game interacts with Player through makeMove() and getName()
// without needing to know if it's a Human or Computer underneath.
public abstract class Player {
    protected String name;
    protected boolean hiddenStatus;

    public Player(String name) {
        this.name = name;
        this.hiddenStatus = false;
    }

    public String getName() { return name; }

    // Used when two human players play -- hides choices from each other
    public void updateHidden(boolean val) { hiddenStatus = val; }

    // Each subclass implements its own move strategy (human input vs computer random)
    public abstract String makeMove();
}