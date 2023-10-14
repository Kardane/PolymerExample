package net.qf.tutorialmod.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.qf.tutorialmod.api.TestAPI;
import org.jetbrains.annotations.Nullable;

public class TestPolymerItem extends Item implements PolymerItem {
    public TestPolymerItem(Settings settings) {
        super(settings);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        if(player ==null) return Items.IRON_SWORD;
        TestAPI temp = (TestAPI) player;
        int exp = temp.getWeaponExp();
        return exp >= 10 ? Items.DIAMOND_SWORD : Items.IRON_SWORD;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var result = super.use(world, user, hand);
        if (world.isClient) {return result;}
        TestAPI temp = (TestAPI) user;
        temp.setWeaponExp(0);
        user.sendMessage(Text.literal("Exp Reset! ("+temp.getWeaponExp()+"/10)").formatted(Formatting.RED));
        PolymerUtils.reloadInventory((ServerPlayerEntity) user);
        return TypedActionResult.success(result.getValue());
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var result = super.postHit(stack, target, attacker);

        if (attacker.world.isClient) return result;
        attacker.sendMessage(Text.literal(target.getName().getString() + " > 윽엑!"));
        if(attacker instanceof ServerPlayerEntity){
            TestAPI temp = (TestAPI) attacker;
            if(temp.getWeaponExp()<10){
                temp.setWeaponExp(temp.getWeaponExp()+1);
                attacker.sendMessage(Text.literal("Exp gained! ("+temp.getWeaponExp()+"/10)").formatted(Formatting.GREEN));
                if(temp.getWeaponExp()==10){
                    temp.setWeaponExp(temp.getWeaponExp()+1);
                    attacker.sendMessage(Text.literal("Max Exp! Upgrade!").formatted(Formatting.AQUA));
                }
            }
            PolymerUtils.reloadInventory((ServerPlayerEntity) attacker);
        }
        return false;
    }
}
