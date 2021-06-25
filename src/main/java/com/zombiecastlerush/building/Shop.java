package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;

import java.util.stream.Collectors;

public class Shop extends Room {

    public Shop(String name, String description) {
        super(name, description);
    }

    public String sellItemToPlayer(Player player, Item item) {
        StringBuilder buyString = new StringBuilder();
        if (player.getAcctBalance() >= item.getPrice()) {
            player.getInventory().transferItem(this.getInventory(), player.getInventory(), item);
            player.setAcctBalance(player.getAcctBalance() - item.getPrice());
            buyString.append("You've bought yourself a " + item.getName());
        } else
            buyString.append("You do not have enough money to buy the " + item.getName() + ".");
        return buyString.toString();
    }

    public String buyItemFromPlayer(Player player, Item item) {
        StringBuilder sellString = new StringBuilder();
        player.getInventory().transferItem(player.getInventory(), this.getInventory(), item);
        player.setAcctBalance(player.getAcctBalance() + 0.75 * item.getPrice());
        sellString.append("You've sold your " + item.getName() + " for " + 0.75 * item.getPrice() + ".");
        return sellString.toString();
    }

    public String toStringShopInventory() {
        String listString = getInventory().getItems().stream().map(Item -> Item.getName() + ", Price= $" + Item.getPrice())
                .collect(Collectors.joining(";  "));
        return listString;
    }
}