package net.qf.tutorialmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.qf.tutorialmod.item.FakeWheat;
import net.qf.tutorialmod.item.TestPolymerItem;

public class TutorialMod implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM,
                new Identifier("test", "test_item"),
                new TestPolymerItem(new Item.Settings().fireproof())
        );
        Registry.register(Registries.ITEM,
                new Identifier("test", "fake_wheat"),
                new FakeWheat(new Item.Settings().maxCount(53).rarity(Rarity.EPIC).fireproof().maxDamage(61))
        );
    }
}
