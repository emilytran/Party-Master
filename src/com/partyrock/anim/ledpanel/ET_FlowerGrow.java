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
 * This is a basic animation that will create a flower in the end
 * 
 * @author Emily Tran
 * 
 */
public class ET_FlowerGrow extends ElementAnimation {

    private Color black = sysColor(0, 0, 0);
    Color flowerColor = sysColor((int)(Math.random() * 100)+150, 0, 0);
    int currVal = 0; /* 0 through 7*/


    private int doneRad = -1;

    public ET_FlowerGrow(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();
    }
    
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
                    // Set the color to the initial color, black
                    panel.setColor(r, c, black);
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
        int  newDoneRads = 0;

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // Using a radius value of 7
            int radOn = (int)(percentage * 7);

            if (newDoneRads < radOn)
            {
                for (int rad = doneRad + 1; rad <= radOn && rad < 7; rad++) 
                {
                	for(int i = 0; i<16; i++)
                	{
            		  panel.setColor(i, 8-rad, flowerColor);
                	}
                	for(int i = 0; i<16; i++)
                	{
            		  panel.setColor(i, 8+rad, flowerColor);
                	}
                	for(int i = 0; i<16; i++)
                	{
            		  panel.setColor(8-rad, i, flowerColor);
                	}
                	for(int i = 0; i<16; i++)
                	{
            		  panel.setColor(8+rad, i, flowerColor);
                	}
                }
            }           
            newDoneRads = radOn;
        }
        doneRad =  newDoneRads;
        
    }

    /**
     * This returns the kind of elements we support with this animation. In this case, it's simply LED Panels
     */
    public static EnumSet<ElementType> getSupportedTypes() {
        return EnumSet.of(ElementType.LEDS);
    }
   
}
