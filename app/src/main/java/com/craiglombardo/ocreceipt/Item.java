package com.craiglombardo.ocreceipt;

/**
 * Created by CraigLombardo on 8/12/18.
 */

public class Item {

    private int itemQuantity;
    private double itemPrice;


    public Item(Double cost) {

        itemQuantity = 1;


        itemPrice = cost;

    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int i) {
        itemQuantity = i;
    }

    public void increaseQuantity() {
        itemQuantity++;
    }

    public boolean decreaseQuantity() {
        if (itemQuantity == 0) return false;

        itemQuantity--;
        return true;

    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double d) {
        itemPrice = d;
    }

}

