package com.ldtteam.blockout;

import com.ldtteam.blockout.views.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.glfw.GLFW;

/**
 * Wraps MineCrafts GuiScreen for BlockOut's Window.
 */
public class BOScreen extends Screen
{
    protected static double scale = 0;
    protected Window window;
    protected int x = 0;
    protected int y = 0;
    public static boolean isMouseLeftDown = false;

    /**
     * Create a GuiScreen from a BlockOut window.
     *
     * @param w blockout window.
     */
    public BOScreen(final Window w)
    {
        super(new StringTextComponent("Blockout GUI"));
        window = w;
    }

    public static double getScale()
    {
        return scale;
    }

    private static void setScale(final Minecraft mc)
    {
        // Seems to work without the sides now
        // Failsave
        if (mc != null)
        {
            scale = mc.mainWindow.getGuiScaleFactor();
        }
    }

    @Override
    public void render(final int mx, final int my, final float f)
    {
        if (window.hasLightbox() && super.minecraft != null)
        {
            super.renderBackground();
        }

        setScale(minecraft);

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) x, (float) y, 0);
        window.draw(mx - x, my - y);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean keyPressed(final int key, final int p_keyPressed_2_, final int p_keyPressed_3_)
    {
        if (key == GLFW.GLFW_KEY_BACKSPACE || key == GLFW.GLFW_KEY_ESCAPE || key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_TAB || key == GLFW.GLFW_KEY_BACKSPACE ||
            key == GLFW.GLFW_KEY_INSERT || key == GLFW.GLFW_KEY_DELETE || key == GLFW.GLFW_KEY_RIGHT || key == GLFW.GLFW_KEY_LEFT || key == GLFW.GLFW_KEY_DOWN ||
            key == GLFW.GLFW_KEY_UP || key == GLFW.GLFW_KEY_PAGE_UP || key == GLFW.GLFW_KEY_PAGE_DOWN || key == GLFW.GLFW_KEY_HOME || key == GLFW.GLFW_KEY_END ||
            // key == GLFW.GLFW_KEY_CAPS_LOCK ||
            // key == GLFW.GLFW_KEY_SCROLL_LOCK ||
            // key == GLFW.GLFW_KEY_NUM_LOCK ||
            key == GLFW.GLFW_KEY_PRINT_SCREEN || key == GLFW.GLFW_KEY_PAUSE || key == GLFW.GLFW_KEY_F1 || key == GLFW.GLFW_KEY_F2 || key == GLFW.GLFW_KEY_F3 ||
            key == GLFW.GLFW_KEY_F4 || key == GLFW.GLFW_KEY_F5 || key == GLFW.GLFW_KEY_F6 || key == GLFW.GLFW_KEY_F7 || key == GLFW.GLFW_KEY_F8 ||
            key == GLFW.GLFW_KEY_F9 || key == GLFW.GLFW_KEY_F10 || key == GLFW.GLFW_KEY_F11 || key == GLFW.GLFW_KEY_F12 ||
            // key == GLFW.GLFW_KEY_F13 || key == GLFW.GLFW_KEY_F14 || key == GLFW.GLFW_KEY_F15 || key == GLFW.GLFW_KEY_F16 ||
            // key == GLFW.GLFW_KEY_F17 || key == GLFW.GLFW_KEY_F18 || key == GLFW.GLFW_KEY_F19 || key == GLFW.GLFW_KEY_F20 ||
            // key == GLFW.GLFW_KEY_F21 || key == GLFW.GLFW_KEY_F22 || key == GLFW.GLFW_KEY_F23 || key == GLFW.GLFW_KEY_F24 ||
            // key == GLFW.GLFW_KEY_F25 || key == GLFW.GLFW_KEY_KP_0 || key == GLFW.GLFW_KEY_KP_1 || key == GLFW.GLFW_KEY_KP_2 ||
            // key == GLFW.GLFW_KEY_KP_3 || key == GLFW.GLFW_KEY_KP_4 || key == GLFW.GLFW_KEY_KP_5 || key == GLFW.GLFW_KEY_KP_6 ||
            // key == GLFW.GLFW_KEY_KP_7 || key == GLFW.GLFW_KEY_KP_8 || key == GLFW.GLFW_KEY_KP_9 || key == GLFW.GLFW_KEY_KP_DECIMAL ||
            // key == GLFW.GLFW_KEY_KP_DIVIDE || key == GLFW.GLFW_KEY_KP_MULTIPLY || key == GLFW.GLFW_KEY_KP_SUBTRACT ||
            // key == GLFW.GLFW_KEY_KP_ADD || key == GLFW.GLFW_KEY_KP_ENTER || key == GLFW.GLFW_KEY_KP_EQUAL ||
            // key == GLFW.GLFW_KEY_LEFT_SHIFT ||
            // key == GLFW.GLFW_KEY_LEFT_CONTROL ||
            // key == GLFW.GLFW_KEY_LEFT_ALT ||
            // key == GLFW.GLFW_KEY_LEFT_SUPER ||
            // key == GLFW.GLFW_KEY_RIGHT_SHIFT ||
            // key == GLFW.GLFW_KEY_RIGHT_CONTROL ||
            // key == GLFW.GLFW_KEY_RIGHT_ALT ||
            // key == GLFW.GLFW_KEY_RIGHT_SUPER ||
            key == GLFW.GLFW_KEY_MENU)
        {
            return window.onKeyTyped('\0', key);
        }
        return true;
    }

    @Override
    public boolean charTyped(final char ch, final int key)
    {
        return window.onKeyTyped(ch, key);
    }

    @Override
    public boolean mouseClicked(final double mxIn, final double myIn, final int keyCode)
    {
        final int mx = (int) mxIn - x;
        final int my = (int) myIn - y;
        if (keyCode == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            // Adjust coordinate to origin of window
            window.click(mx, my);
            isMouseLeftDown = true;
        }
        else if (keyCode == GLFW.GLFW_MOUSE_BUTTON_RIGHT)
        {
            window.rightClick(mx, my);
        }
        // TODO: needs propagation
        return true;
    }

    /**
     * @see lwjgl callback {@link net.minecraft.client.MouseHelper#scrollCallback()}
     * @see option taken into account {@link net.minecraft.client.settings.AbstractOption#DISCRETE_MOUSE_SCROLL}
     */
    @Override
    public boolean mouseScrolled(final double mx, final double my, final double scrollDiff)
    {
        if (scrollDiff != 0)
        {
            window.scrollInput(scrollDiff);
        }
        // TODO: needs propagation
        return true;
    }

    @Override
    public void mouseMoved(final double mxIn, final double myIn)
    {
        // final int mx = (int) (super.minecraft.mouseHelper.getMouseX() * super.width / super.minecraft.mainWindow.getWidth()) - x;
        // final int my = (int) (super.height - super.minecraft.mouseHelper.getMouseY() * super.height /
        // super.minecraft.mainWindow.getHeight()) - 1 - y;
        window.handleHover((int) mxIn - x, (int) myIn - y);
    }

    @Override
    public boolean mouseReleased(final double mxIn, final double myIn, final int keyCode)
    {
        if (keyCode == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            // Adjust coordinate to origin of window
            window.onMouseReleased((int) mxIn - x, (int) myIn - y);
            isMouseLeftDown = false;
        }
        // TODO: needs propagation
        return true;
    }

    @Deprecated
    protected void mouseClickMove(final int mx, final int my, final int buttons, final long timeElapsed)
    {
        // Can be overridden
        throw new RuntimeException("deprecated blockout error"); // not ported but available
    }

    @Override
    public void init()
    {
        x = (width - window.getWidth()) / 2;
        y = (height - window.getHeight()) / 2;

        minecraft.keyboardListener.enableRepeatEvents(true);
        window.onOpened();
    }

    @Override
    public void tick()
    {
        if (minecraft != null)
        {
            window.onUpdate();

            if (!minecraft.player.isAlive() || minecraft.player.dead)
            {
                minecraft.player.closeScreen();
            }
        }
    }

    @Override
    public void onClose()
    {
        window.onClosed();
        Window.clearFocus();
        minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean isPauseScreen()
    {
        return window.doesWindowPauseGame();
    }
}