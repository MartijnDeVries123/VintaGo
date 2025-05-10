package org.vfl.vintago.dto;

public class ScheduleRequestDTO {
    private String simulationType;
    private String solver;
    private String days;

    public String getSimulationType() {
        return simulationType;
    }

    public void setSimulationType(String simulationType) {
        this.simulationType = simulationType;
    }

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}