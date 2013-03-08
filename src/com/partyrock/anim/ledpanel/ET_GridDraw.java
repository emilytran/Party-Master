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
 * This is a basic animation that will wipe an LED panel from left to right with alternating LED pixel colors
 * 
 * @author Emily Tran
 * 
 */
public class ET_GridDraw extends ElementAnimation {
	
    private Color color1 = getRandomColor();
    //private Color color2 = getRandomColor();

    private int fadedRows = -1;

    public ET_GridDraw(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
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
                // For every column in that row
                for (int c = 0; c < panel.getPanelWidth(); c++) 
                {
                    // Set the color to the initial color, black
                    panel.setColor(r, c, sysColor(0, 0, 0));
                }
            }
        }
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
    public void increment(double percentage) {
        int newFadedColumns = 0;

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // How many rows should be on based on our percentage for this animation 
            int columnsOn = (int) (percentage * panel.getPanelHeight()*2);

            // So if we haven't already done this column 
           
            if(fadedRows<=16)
            {
	            if (fadedRows < columnsOn) {
	
	                // Then for every column
	            	 for (int r = fadedRows + 1; r <= columnsOn && r < panel.getPanelHeight(); r++) {
	            		 	// For every row 
	            		 	for (int c = 0; c < panel.getPanelWidth(); c++) {
	                    	
	                        // Set the alternating colors 
		                    	if(r%2 == 0)
		                        panel.setColor(r, c, color1);
	                    }
	                }
	            }
            }
            else
            {
            	 
	            if (fadedRows < columnsOn) {
	                // Then for every column
	            	 for (int r = fadedRows-17 ; r <= columnsOn-17 && r < panel.getPanelHeight(); r++) {
	            		 	// For every row 
	            		 	for (int c = 1; c < panel.getPanelWidth(); c++) {
	                    	
	                        // Set the alternating colors 
		                    	if(r%2 == 0)
		                        panel.setColor(c, r, color1);
	                    }
	                }
	            }
            }

            newFadedColumns = columnsOn;
        }

        fadedRows = newFadedColumns;
    }

    /**
     * This returns the kind of elements we support with this animation. In this case, it's simply LED Panels
     */
    public static EnumSet<ElementType> getSupportedTypes() {
        return EnumSet.of(ElementType.LEDS);
    }
    public Color getRandomColor() {
        return sysColor((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }
}
