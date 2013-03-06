package com.partyrock.anim.ledpanel;

import java.util.ArrayList;
import java.util.EnumSet;

import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.RGB;
//import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;

import com.partyrock.LightMaster;
import com.partyrock.anim.ElementAnimation;
import com.partyrock.element.ElementController;
import com.partyrock.element.ElementType;
import com.partyrock.element.led.LEDPanelController;

/**
 * This is a basic animation that will swap the places of two random colors 
 * 
 * @author Emily Tran
 * 
 */
public class ET_RandomColorSwap extends ElementAnimation {

    //Background color and two colors to swap 
    private Color initialColor;
    private Color closeColor1;
    private Color closeColor2;

    // The number of rows we've colored in one direction 
    private int fadedRows = -1;

    public ET_RandomColorSwap (LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();

        // Clears the board to black
        initialColor = sysColor(0, 0, 0);
        closeColor1 = getRandomColor();
        closeColor2 = getRandomColor();
    }
    
    public void trigger() 
    {
        // For every element we're given
        for (ElementController controller : getElements())
        {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // The for every row we haven't done
            for (int r = 0; r < panel.getPanelHeight(); r++) 
            {
                // and every column in that row
                for (int c = 0; c < panel.getPanelWidth(); c++) 
                {
                    // Set the color to the initial color, black
                    panel.setColor(r, c, initialColor);
                }
            }
        }
    }

    /**
     * This method is called once when the animation is created. 
     * You can use it to get user configurable settings like a
     * Color (as we do in this case)
     */
    @Override
    public void setup(Shell window) {
        //No initial set up required
    }

    /**
     * Since we're doing something over time, we need to implement increment()
     * 
     * @param percentage The percentage of the way through the animation we are. This is between 0 and 1
     */
    public void increment(double percentage) {
        int newFadedRows = 0;

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // How many rows should be on based on our percentage
            int rowsOn = (int)(percentage * panel.getPanelHeight());

            // So if we haven't already done this
            if (fadedRows < rowsOn)
            {
                // The for every row we haven't done
                for (int r = fadedRows + 1; r <= rowsOn && r < panel.getPanelHeight(); r++) 
                {
                    // and every column in that row
                    for (int c = 0; c < panel.getPanelWidth(); c++) 
                    {
                        // Set the colors of the color moving up and the color moving down 
                        panel.setColor(r, c, closeColor1);
                        panel.setColor(panel.getPanelWidth()-r-1, panel.getPanelHeight()-c-1, closeColor2);
                    }
                }
            }           
            newFadedRows = rowsOn;
        }
        fadedRows = newFadedRows;
    }

    /**
     * This returns the kind of elements we support with this animation. In this case, it's simply LED Panels
     */
    public static EnumSet<ElementType> getSupportedTypes() {
        return EnumSet.of(ElementType.LEDS);
    }
    /**
     * Returns a completely randomized color
     */
    public Color getRandomColor() {
        return sysColor((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

}
