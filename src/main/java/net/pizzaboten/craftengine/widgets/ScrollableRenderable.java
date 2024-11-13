package net.pizzaboten.craftengine.widgets;

import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import static net.pizzaboten.craftengine.CraftEngine.LOGGER;

import java.util.List;

public class ScrollableRenderable extends ScrollPanel {

    private final List<AbstractWidget> renderables;

    public ScrollableRenderable(Minecraft client, int width, int height, int top, int left, List<AbstractWidget> renderables) {
        super(client, width, height, top, left);
        this.renderables = renderables;
    }

    @Override
    protected int getContentHeight() {
        return renderables.size() * 25;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        int offsetY = 5 + top;
            for(AbstractWidget widget : renderables){
                widget.setPosition(widget.getX(), offsetY + relativeY - 14);
                widget.render(guiGraphics, mouseX, mouseY, 0);
                offsetY+=(widget.getHeight() + 5);
            }
            offsetY = 5 + top;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(AbstractWidget widget : renderables) {
            if (widget.isHovered()){
                widget.onClick(mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
