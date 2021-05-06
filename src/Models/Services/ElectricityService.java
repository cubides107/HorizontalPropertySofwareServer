package Models.Services;

import Models.managerProperties.IDataNodeProperties;

public class ElectricityService implements IDataNodeProperties {
   private int ID;

    public ElectricityService(int ID) {
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
