package com.craiglombardo.ocreceipt;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Person {

    private TreeMap<String, Item> itemsList;

    private Double itemsTotal;
    private int itemsCount;

    private String personName;

    private LinearLayout listParent;
    private LinearLayout listRow;

    private Button inlineButton;


    public Person(View view, String name, Button ib) {
        listParent = view.findViewById(R.id.line_item_list);
        listRow = view.findViewById(R.id.line_header);

        TextView fullName = listRow.findViewById(R.id.line_name);
        fullName.setText(name);

        inlineButton = ib;

        itemsCount = 0;
        itemsList = new TreeMap<>();

        personName = name;
        itemsTotal = 0.0;
    }

    public LinearLayout getListParent() {
        return listParent;
    }

    public LinearLayout getListRow() {
        return listRow;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public TreeMap getItemsList() {
        return itemsList;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String s) {
        personName = s;
    }

    public Double getItemsTotal() {
        return itemsTotal;
    }

    public boolean addItem(String itemName, Double itemCost) {

        if (itemsList.containsKey(itemName)) return false;

        Item item = new Item(itemCost);
        itemsList.put(itemName, item);

        itemsCount++;
        itemsTotal += itemCost;
        return true;
    }

    public boolean removeItem(String itemName) {
        Item item = itemsList.get(itemName);
        if (item == null) return false;

        itemsCount -= item.getItemQuantity();
        itemsTotal -= item.getItemQuantity() * item.getItemPrice();
        itemsList.remove(itemName);
        return true;
    }

    public boolean increaseItemCount(String itemName) {
        Item item = itemsList.get(itemName);
        if (item == null) return false;

        item.increaseQuantity();
        itemsCount++;
        itemsTotal += item.getItemPrice();
        return true;
    }

    public boolean decreaseItemCount(String itemName) {
        Item item = itemsList.get(itemName);
        if (item == null) return false;

        if (item.decreaseQuantity()) {
            itemsCount--;
            itemsTotal -= item.getItemPrice();
            return true;
        } else return false;
    }

    public void rotateButton(Boolean up) {
        inlineButton.setBackgroundResource(up ? R.drawable.ic_up : R.drawable.ic_down);
    }

    public static void main(String[] args) {
        Person p1 = new Person(null, "Craig", null);

        p1.addItem("Item 1", 12.00);
        p1.addItem("Item 2", 122.00);
        p1.addItem("Item 3", 16.30);
        p1.addItem("Item 4", 1.70);

        p1.increaseItemCount("Item 1");
        p1.increaseItemCount("Item 1");
        p1.increaseItemCount("Item 1");
        p1.increaseItemCount("Item 2");
        p1.increaseItemCount("Item 78");

        System.out.println("Breakdown for: " + p1.getPersonName() + " total: $" + p1.getItemsTotal());

        Set p1Set = p1.getItemsList().entrySet();
        Iterator p1Iter = p1Set.iterator();
        while (p1Iter.hasNext()) {
            Map.Entry me1 = (Map.Entry) p1Iter.next();
            Item i = (Item) me1.getValue();
            System.out.println(me1.getKey() + " : " + i.getItemQuantity() + " @ " + i.getItemPrice());
        }

        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.decreaseItemCount("Item 1");
        p1.removeItem("Item 2");

        System.out.println("Breakdown for: " + p1.getPersonName() + " total: $" + p1.getItemsTotal());

        p1Set = p1.getItemsList().entrySet();
        p1Iter = p1Set.iterator();
        while (p1Iter.hasNext()) {
            Map.Entry me1 = (Map.Entry) p1Iter.next();
            Item i = (Item) me1.getValue();
            System.out.println(me1.getKey() + " : " + i.getItemQuantity() + " @ " + i.getItemPrice());
        }

    }

}
