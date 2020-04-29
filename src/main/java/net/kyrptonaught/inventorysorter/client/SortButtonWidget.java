package net.kyrptonaught.inventorysorter.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.inventorysorter.InventorySortPacket;
import net.kyrptonaught.inventorysorter.InventorySorterMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class SortButtonWidget extends TexturedButtonWidget {
    private static final Identifier texture = new Identifier(InventorySorterMod.MOD_ID, "textures/gui/button.png");
    private boolean playerInv;

    public SortButtonWidget(int int_1, int int_2, boolean playerInv) {
        super(int_1, int_2, 10, 9, 0, 0, 19, texture, 20, 37, null, LiteralText.EMPTY);
        this.playerInv = playerInv;
    }

    @Override
    public void onPress() {
        if (InventorySorterMod.getConfig().debugMode && GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == 1) {
            System.out.println("Add the line below to config/inventorysorter/blacklist.json5 to blacklist this inventory");
            System.out.println(MinecraftClient.getInstance().currentScreen.getClass().getName());
        } else
            InventorySortPacket.sendSortPacket(playerInv);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int i, int j, float f) {
        RenderSystem.pushMatrix();
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.getTextureManager().bindTexture(texture);
        RenderSystem.scalef(.5f, .5f, 1);
        RenderSystem.translatef(this.x, this.y, 0);
        drawTexture(matrixStack, this.x, this.y, 0, this.isHovered() ? 19 : 0, 20, 18, 20, 37);
        this.renderToolTip(matrixStack, i, j);
        RenderSystem.disableLighting();
        RenderSystem.popMatrix();
    }

    @Override
    public void renderToolTip(MatrixStack matrixStack, int x, int y) {
        if (InventorySorterMod.getConfig().displayTooltip && this.isHovered())
            MinecraftClient.getInstance().currentScreen.renderTooltip(matrixStack, new LiteralText("Sort by: " + StringUtils.capitalize(InventorySorterMod.getConfig().sortType.toString().toLowerCase())), x, y);
    }
}
