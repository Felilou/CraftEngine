package net.pizzaboten.craftengine.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public class ScrollableSectionWidget extends AbstractScrollWidget {
    private final int itemHeight = 20; // Höhe jedes Eintrags in der Liste

    public ScrollableSectionWidget(int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // Implementiere diese Methode für die Barrierefreiheit; zurzeit leer
    }

    @Override
    protected int getInnerHeight() {
        // Berechne die Höhe basierend auf der Anzahl der Einträge
        return 25 * itemHeight; // Beispiel mit 25 Einträgen
    }

    @Override
    protected double scrollRate() {
        // Definiert die Scrollgeschwindigkeit (5 Pixel pro Scroll-Schritt)
        return 5;
    }

    @Override
    protected void renderContents(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = guiGraphics.pose();

        // Zugriff auf die Schriftart
        var font = Minecraft.getInstance().font;

        int yPosition = this.getY() + 4; // Startposition im Scrollbereich
        for (int i = 0; i < 25; i++) { // Beispiel mit 25 Dummy-Einträgen
            int entryY = yPosition + i * itemHeight - (int) scrollAmount();

            // Prüfen, ob der Eintrag innerhalb des sichtbaren Bereichs liegt
            if (entryY + itemHeight > this.getY() && entryY < this.getY() + this.height) {
                // Rendern eines einzelnen Eintrags (hier ein einfacher Text)
                Component entryText = Component.literal("Eintrag " + (i + 1));
                guiGraphics.drawString(font, entryText, this.getX() + 5, entryY, 0xFFFFFF);
            }
        }
    }
}
