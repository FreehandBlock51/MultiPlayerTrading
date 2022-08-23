package net.dc.multiplayertrading.mixins;

import java.util.ArrayList;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.dc.multiplayertrading.IMultiCustomerSupporter;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(MerchantEntity.class)
public class MerchantEntityMixin implements IMultiCustomerSupporter {
    private ArrayList<PlayerEntity> customers;

    @Shadow
    private PlayerEntity customer;

    @Inject(method = "resetCustomer", at = @At("HEAD"))
    public void onResetCustomer(CallbackInfo ci) {
        this.customers.clear();
    }

    @Inject(method = "setCustomer", at = @At("HEAD"))
    public void onSetCustomer(@Nullable PlayerEntity customer, CallbackInfo ci) {
        replaceCustomer(this.customer, customer);
    }

    @Inject(method = "getCustomer", at = @At("HEAD"))
    public void onGetCustomer(CallbackInfo ci) {
        this.customer = getCustomerAt(-1); // the getCustomer method will return the most recent customer
    }

    @Inject(method = "hasCustomer", at = @At("HEAD"))
    public void onHasCustomer(CallbackInfo ci) {
        if (this.customer != null && this.customers.size() == 0) {
            this.customer = null; // this will have the method return false
        }
    }

    // Implementation of IMultiCustomerSupporter methods

    public PlayerEntity[] getCustomers() {
        PlayerEntity[] cArr = new PlayerEntity[this.customers.size()]; // we need a source array to copy the contents of our list to
        return this.customers.toArray(cArr);
    }
    public PlayerEntity getCustomerAt(int index) {
        return this.customers.get(index);
    }
    public void setCustomerAt(int index, PlayerEntity customer) {
        this.customers.set(index, customer);
    }
    public void insertCustomer(int index, PlayerEntity customer) {
        this.customers.add(index, customer);
    }
    @Override
    public void addCustomer(PlayerEntity customer) {
        this.customers.add(customer);
    }
    public PlayerEntity removeCustomer(int index) {
        return this.customers.remove(index);
    }
    public boolean removeCustomer(PlayerEntity customer) {
        return this.customers.remove(customer);
    }
    @Override
    public PlayerEntity popCustomer() {
        return this.customers.remove(this.customers.size() - 1);
    }
    public boolean containsCustomer(PlayerEntity customer) {
        return this.customers.contains(customer);
    }
    public void replaceCustomer(PlayerEntity oldCustomer, PlayerEntity newCustomer) {
        this.customers.replaceAll(old -> old == oldCustomer ? newCustomer : old);
    }
}
