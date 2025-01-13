package net.pizzaboten.craftengine.widgets;

import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class scrollableWidget extends ScrollPanel {

    private final List<AbstractWidget> abstractWidgets;

    public final int PADDING = 2;
    public final int GAP = 2;
    public final int BAR_WIDTH = 6;
    public String filter = "";

    public scrollableWidget(Minecraft client, int width, int height, int top, int left, List<AbstractWidget> abstractWidgets) {
        super(client, width, height, top, left);
        this.abstractWidgets = abstractWidgets;
    }

    @Override
    protected int getContentHeight() {
        int ret = PADDING;
        for(AbstractWidget widget : abstractWidgets){
            ret+=widget.getHeight() + GAP;
        }
        ret +=  PADDING - GAP;
        return ret;
    }

    public int contentHeight(){
        return getContentHeight();
    }

    public int getHeight(){
        return this.height;
    }

    public int getTop(){
        return this.top;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        int offsetY = 2;
            for(AbstractWidget widget : abstractWidgets){

                if(!(filter!=null&&filter.isBlank())){
                    if(!widget.getMessage().getString().toLowerCase().contains(filter.toLowerCase())) {
                        continue;
                    }
                }

                widget.setWidth(this.width - BAR_WIDTH - PADDING * 2);
                if(getContentHeight() < height){
                    widget.setWidth(this.width - PADDING * 2);
                    relativeY=top+PADDING;
                }

                widget.setPosition(this.left + PADDING, relativeY + offsetY);
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

}
