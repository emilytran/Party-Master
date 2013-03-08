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
 * This is a basic animation that will draw a red and white target onto the LED panel in three steps
 * 
 * @author Emily Tran
 * 
 */
public class ET_Target extends ElementAnimation {
	//Our colors
    private Color color1 = sysColor(255, 0, 0); //red 
    private Color color2 = sysColor(255, 255, 255); //white
    private Color bgcolor = sysColor(0, 0, 0);;

    public ET_Target(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();
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

            // For every row
            for (int r = 0; r < panel.getPanelHeight(); r++) 
            {
                // and every column in that row
                for (int c = 0; c < panel.getPanelWidth(); c++) 
                {
                    // Set the color to the initial color, bgcolor
                    panel.setColor(r, c, bgcolor);
                }
            }
        }
    }
    
    public void increment(double percentage) {

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // How many time segments are kept in num
            int num = (int) (percentage * 5);

            //Draw outermost of target at num = 1, next innermost at num = 2, and last part in num = 3
            if (num < 5) {
                // Then for every row we haven't done
                for (int r = 0; r < panel.getPanelHeight(); r++) {
                    // and every column in that row
                    for (int c = 0; c < panel.getPanelWidth(); c++) {
                        // Set the color to the appropriate part of the target color using equation of a circle
                    	int x = r-8;
                    	int y = c-8;
                    	int x2y2 = x*x + y*y; 
                        if( ((x2y2 <= 8*8-1 && x2y2 > 6*6 -1 ) && num == 1) || (x2y2 <= 2*2 && num == 3))
                        		panel.setColor(Math.max(0, r-1), Math.max(0, c), color1);
                        else if((x2y2 <= 6*6 -1 && x2y2 > 2*2 -1) && num == 2)
                        		panel.setColor(Math.max(0, r-1), Math.max(0, c), color2);
                     
                    }
                }
            }
        }
    }

    /**
     * This returns the kind of elements we support with this animation. In this case, it's simply LED Panels
     */
    public static EnumSet<ElementType> getSupportedTypes() {
        return EnumSet.of(ElementType.LEDS);
    }

}
