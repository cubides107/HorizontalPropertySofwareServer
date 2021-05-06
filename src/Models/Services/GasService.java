package Models.Services;

import Models.managerProperties.IDataNodeProperties;

public class GasService implements IDataNodeProperties {
    private int ID;

    public GasService(int ID) {
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
