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

    public WrapperService() {
    }

    public double getValue() {
        return value;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }
}
