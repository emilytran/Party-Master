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
//import java.awt.Point;
/**
 * This is a basic animation hard-coded but blinks
 * 
 * @author Emily Tran
 * 
 */
public class ET_BlinkDesign extends ElementAnimation {
	
    private Color blink = sysColor(255, 100, 100);
    private Color blink1 = sysColor(255, 100, 100);
    private Color blink2 = sysColor(100, 100, 100);

	boolean[][] alternatePoints = new boolean[16][16]; 
	

    public ET_BlinkDesign(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();
    }
    
    public void trigger() 
    {
    	for(int i = 0; i<14;i+=1)
    	{
    		for(int j = 0; j<14; j+=4)
    		{
    			alternatePoints[i][j] = true;
    			alternatePoints[i+1][j+1] = true;
    		}
    	}
    	
        // For every element we're given
        for (ElementController controller : getElements())
        {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            for (int r = 0; r < panel.getPanelHeight(); r++) 
            {
                // and every column in that row
                for (int c = 0; c < panel.getPanelWidth(); c++) 
                {
                    // Set the color to the initial color, bgcolor
                    panel.setColor(r, c, sysColor(0, 0, 0));
                }
            }
            // For every row
            for (int r = 0; r < panel.getPanelHeight(); r++) 
            {
                // For every column in that row
                for (int c = 0; c < panel.getPanelWidth(); c++) 
                {
                    // Set the star border to yellow
                    if(alternatePoints[r][c]) 
                    	{
                    		panel.setColor(r, c, blink);
                    		//panel.setColor(r, 15-c, blink);
                    	}
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

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            int columnsOn = (int) (percentage*35);
            
            // So if we haven't already done this column 
            if (columnsOn%5 == 0)
            {
        	
            	if(blink == blink1)
            		blink = blink2;
            	else blink = blink1;
	            // For every row
	            for (int r = 0; r < panel.getPanelHeight(); r++) 
	            {
	                // For every column in that row
	                for (int c = 0; c < panel.getPanelWidth(); c++) 
	                {
	                    // Set the star border to yellow
	                    if(alternatePoints[r][c]) 
	                    	{
	                    		panel.setColor(r, c, blink);
	                    		//panel.setColor(r, 16-c, yellow);
	                    	}
	                }
	            }
            }
          //  newFadedColumns = columnsOn;
        }

        //fadedRows = newFadedColumns;
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
