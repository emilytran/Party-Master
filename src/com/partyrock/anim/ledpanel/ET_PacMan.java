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
 * This is a basic animation of pacman eating 
 * 
 * @author Emily Tran
 * 
 */
public class ET_PacMan extends ElementAnimation {

    private Color color1;
    private Color bgcolor;


    public ET_PacMan(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();

        color1 = sysColor(255, 255, 0);
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

            // Make the percentage of the time into 8 segments
            int num = (int) (percentage * 60);

            // Alternate the colors
            if (num%5==0) {
                for (int r = 1; r < panel.getPanelHeight(); r++) {
                    for (int c = 0; c < panel.getPanelWidth(); c++) {
                    	int x = r-8;
                    	int y = c-8;
                        if(num%3 == 0 && x*x + y*y <= 8*8-1 && ((x-y)<=0 && (x+y) >= 0))
                        	panel.setColor(Math.max(0, r), Math.max(0, c), bgcolor);
                        	//else panel.setColor(Math.max(0, r), Math.max(0, c), color1);
                        else if(x*x + y*y <= 8*8-1)
                        {
                        	if(x == 0 && y > 0) panel.setColor(r, c, bgcolor);
                        	else panel.setColor(Math.max(0, r), Math.max(0, c), color1);
                        
                        }
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
