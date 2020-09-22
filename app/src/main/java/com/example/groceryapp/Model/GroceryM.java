package com.example.groceryapp.Model;

public class GroceryM
{
    String name;
    String quantity;
    String date;
    String id;

    public GroceryM(String name, String quantity, String date, String id)
    {
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.id = id;
    }

    public GroceryM()
    {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
