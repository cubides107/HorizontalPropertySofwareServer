package Models.Services;

import Models.managerProperties.IDataNodeProperties;

import java.time.LocalDate;

public class WrapperService implements IDataNodeProperties {

    private LocalDate date;
    private double value;

    public WrapperService(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }
}
