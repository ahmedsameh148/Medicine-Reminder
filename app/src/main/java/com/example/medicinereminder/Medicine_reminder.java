package com.example.medicinereminder;

public class Medicine_reminder
{

    private int id;
    private String name;
    private int img;
    private int dose;
    private int Quantity_per_Box;
    private int Current_Quantity;
    private String reminder;
    private String time,date;
    private String activation;
    public Medicine_reminder(int id, String name, int img, int dose, int quantity_per_Box, int current_Quantity, String reminder, String time, String date) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.dose = dose;
        Quantity_per_Box = quantity_per_Box;
        Current_Quantity = current_Quantity;
        this.reminder = reminder;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public int getDose() {
        return dose;
    }

    public int getQuantity_per_Box() {
        return Quantity_per_Box;
    }

    public int getCurrent_Quantity() {
        return Current_Quantity;
    }

    public String getReminder() {
        return reminder;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getActivation() {
        return activation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public void setQuantity_per_Box(int quantity_per_Box) {
        Quantity_per_Box = quantity_per_Box;
    }

    public void setCurrent_Quantity(int current_Quantity) {
        Current_Quantity = current_Quantity;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

}
