package net.pizzaboten.craftengine.widgets;

import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.NotNull;

import static net.pizzaboten.craftengine.CraftEngine.LOGGER;

import java.util.List;
import java.util.logging.Logger;

public class scrollableWidget extends ScrollPanel implements PercentSpacing {

    private final List<AbstractWidget> abstractWidgets;

    private final int PADDING = 5;
    private final int GAP = 2;
    private int BAR_WIDTH = 6;

    public scrollableWidget(Minecraft client, int width, int height, int top, int left, List<AbstractWidget> abstractWidgets) {
        super(client, width, height, top, left);
        this.abstractWidgets = abstractWidgets;
    }

    public scrollableWidget(Minecraft client, int width, int height, int top, int left, int border, int barWidth, int bgColor, List<AbstractWidget> abstractWidgets) {
        super(client, width, height, top, left, border, barWidth, bgColor, bgColor);
        this.abstractWidgets = abstractWidgets;
        this.BAR_WIDTH = barWidth;
    }

    @Override
    protected int getContentHeight() {
        int ret = 0;
        for(AbstractWidget widget : abstractWidgets){
            ret+=widget.getHeight() + GAP;
        }
        return ret - GAP + 4;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        int offsetY = 2;
            for(AbstractWidget widget : abstractWidgets){
                widget.setWidth(this.width - BAR_WIDTH - 2);
                if(getContentHeight() < height){
                    widget.setWidth(this.width - 4);
                    relativeY=top+2;
                }
                widget.setPosition(this.left + 2, relativeY + offsetY);
                widget.setHeight(widget.getHeight());
                widget.render(guiGraphics, mouseX, mouseY, 0);
                offsetY+=(widget.getHeight() + GAP);
            }
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(AbstractWidget widget : abstractWidgets) {
            if (widget.isHovered()){
                widget.onClick(mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public int getHeightPercent(int percent) {
        double d = this.top + (((double) (this.width - (PADDING * 2) - BAR_WIDTH) / 100) * percent);
        return (int) d;
    }

    @Override
    public int getWidthPercent(int percent) {
        double d = this.left + (((double) (this.width - (PADDING * 2) - BAR_WIDTH) / 100) * percent);
        return (int) d;
    }
}
