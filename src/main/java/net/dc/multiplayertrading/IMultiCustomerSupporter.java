package net.dc.multiplayertrading;

import net.minecraft.entity.player.PlayerEntity;

public interface IMultiCustomerSupporter {
    public PlayerEntity[] getCustomers();
    public PlayerEntity getCustomerAt(int index);
    public void setCustomerAt(int index, PlayerEntity customer);
    public void insertCustomer(int index, PlayerEntity customer);
    public default void addCustomer(PlayerEntity customer) { insertCustomer(-1, customer); }
    public PlayerEntity removeCustomer(int index);
    public boolean removeCustomer(PlayerEntity customer);
    public default PlayerEntity popCustomer() { return removeCustomer(-1); }
    public boolean containsCustomer(PlayerEntity customer);
    public void replaceCustomer(PlayerEntity oldCustomer, PlayerEntity newCustomer);
}
