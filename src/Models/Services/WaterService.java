package Models.Services;

import Models.managerProperties.IDataNodeProperties;

public class WaterService implements IDataNodeProperties {

    private int ID;

    public WaterService(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setId(int id) {

    }
}
