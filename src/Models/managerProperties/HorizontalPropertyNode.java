package Models.managerProperties;

public class HorizontalPropertyNode implements IDataNodeProperties{


    private int ID;

    public HorizontalPropertyNode(int ID) {
        this.ID = ID;
    }

    public HorizontalPropertyNode() {
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setId(int id) {
        this.ID = ID;
    }
}
