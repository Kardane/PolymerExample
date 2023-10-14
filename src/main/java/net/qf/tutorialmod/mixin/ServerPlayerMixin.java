package net.qf.tutorialmod.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.qf.tutorialmod.api.TestAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin implements TestAPI {
    @Unique private int WeaponExp = 0;
    @Unique private final String WEAPON_EXP = "WeaponExp";

    @Override
    @Unique
    public int getWeaponExp(){
        return WeaponExp;
    }

    @Override
    @Unique
    public void setWeaponExp(int num){
        WeaponExp = num;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void testMod$writeCustomData(NbtCompound nbt, CallbackInfo ci){
        nbt.putInt(WEAPON_EXP,WeaponExp);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void testMod$readCustomData(NbtCompound nbt, CallbackInfo ci){
        this.WeaponExp = nbt.getInt(WEAPON_EXP);
    }
}
