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
 * This is a basic animation that will wipe an LED panel from top to bottom with a given color
 * 
 * @author Emily Tran
 * 
 */
public class ET_Pinwheel extends ElementAnimation {

    // The color to fade to
    private Color color1;
    private Color color2;
    private Color bgcolor;

    // The number of rows we've faded
   // private int fadedRows = -1;

    public ET_Pinwheel(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();

        // Set a new default color of white
        color1 = sysColor(255, 20, 147);
        color2 = sysColor(255, 255, 255);
        bgcolor = sysColor(0, 0, 0);
    }

    /**
     * This method is called once when the animation is created. You can use it to get user configurable settings like a
     * Color (as we do in this case)
     */
    @Override
    public void setup(Shell window) {
    }
    
    /* When you take in arguments in setup() (like the color for a wipe animation), you have to save it in a map, and load it in a map
    protected void saveSettings(SectionSettings settings) {
        settings.put("color", Saver.saveColor(color));
    }

    protected void loadSettings(SectionSettings settings) {
        color = Saver.loadColor(settings.get("color"), this);
    }
    */

    /**
     * Since we're doing something over time, we need to implement increment()
     * 
     * @param percentage The percentage of the way through the animation we are. This is between 0 and 1
     */
    
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
                    panel.setColor(r, c, bgcolor);
                }
            }
        }
    }
    
    public void increment(double percentage) {
        int newFadedRows = 0;

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // How many rows should be on based on our percentage?
            int num = (int) (percentage * 250);

            // So if we haven't already done this
            if (num < 250) {
                // The for every row we haven't done
                for (int r = 0; r < panel.getPanelHeight(); r++) {
                    // and every column in that row
                    for (int c = 0; c < panel.getPanelWidth(); c++) {
                        // Set the color to the color we picked when making the animation
                    	int x = r-8;
                    	int y = c-8;
                        if(x*x + y*y <= 8*8 && y > -8)
                        {
                        	if(((x >= 0 && y>=0 || x < 0 && y < 0)&&num%2 == 0) || ((x < 0 && y>0 || x >= 0 && y <= 0)&&num%2 == 1)) 
                        		panel.setColor(Math.max(0, r-1), Math.max(0, c), color1);
                        	else 
                        		panel.setColor(Math.max(0, r-1), Math.max(0, c), color2);
                        }
                    }
                }
            }

            newFadedRows = num;
        }

        //fadedRows = newFadedRows;
    }

    /**
     * This returns the kind of elements we support with this animation. In this case, it's simply LED Panels
     */
    public static EnumSet<ElementType> getSupportedTypes() {
        return EnumSet.of(ElementType.LEDS);
    }

}
