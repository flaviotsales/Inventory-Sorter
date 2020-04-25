package net.kyrptonaught.inventorysorter.client.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.kyrptonaught.inventorysorter.InventorySorterMod;
import net.kyrptonaught.inventorysorter.SortCases;
import net.kyrptonaught.inventorysorter.client.config.ConfigOptions;
import net.minecraft.client.gui.screen.Screen;

public class InventorySorterConfigScreenFactory implements ConfigScreenFactory<Screen> {
    @Override
    public Screen create(Screen screen) {
        ConfigOptions options = InventorySorterMod.getConfig();
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(screen).setTitle("Inventory Sorting Config");
        builder.setSavingRunnable(() -> InventorySorterMod.configManager.saveAll());
        ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();

        ConfigCategory displayCat = builder.getOrCreateCategory("key.inventorysorter.config.category.display");
        displayCat.addEntry(entryBuilder.startBooleanToggle("key.inventorysorter.config.displaysort", options.displaySort).setSaveConsumer(val -> options.displaySort = val).setDefaultValue(true).build());
        displayCat.addEntry(entryBuilder.startBooleanToggle("key.inventorysorter.config.seperatebtn", options.seperateBtn).setSaveConsumer(val -> options.seperateBtn = val).setDefaultValue(true).build());
        displayCat.addEntry(entryBuilder.startBooleanToggle("key.inventorysorter.config.displaytooltip", options.displayTooltip).setSaveConsumer(val -> options.displayTooltip = val).setDefaultValue(true).build());

        ConfigCategory logicCat = builder.getOrCreateCategory("key.inventorysorter.config.category.logic");
        logicCat.addEntry(entryBuilder.startEnumSelector("key.inventorysorter.config.sorttype", SortCases.SortType.class, options.sortType).setSaveConsumer(val -> options.sortType = val).build());
        logicCat.addEntry(entryBuilder.startBooleanToggle("key.inventorysorter.config.sortplayer", options.sortPlayer).setSaveConsumer(val -> options.sortPlayer = val).setDefaultValue(false).build());
        logicCat.addEntry(entryBuilder.startBooleanToggle("key.inventorysorter.config.middleclick", options.middleClick).setSaveConsumer(val -> options.middleClick = val).setDefaultValue(true).build());

        return builder.build();
    }
}
