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
 * This is a basic animation that will create a filling up and waves animation 
 * 
 * @author Emily Tran
 * 
 */
public class ET_Water extends ElementAnimation {

    private Color color1 = sysColor(0, 0, 128);
    private Color color2 = sysColor(0, 0, 255);

    private int fadedRows = -1;

    public ET_Water(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
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
                    // Set the color to the initial color, a white background
                    panel.setColor(r, c, sysColor(255, 255, 255));
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
        int newFadedRows = 0;

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // How many rows should be on based on our percentage and 150% additional time for wave animation 
            int rowsOn = (int) (percentage * panel.getPanelHeight()*2.5);

            // So if we haven't already done this
            if (fadedRows < rowsOn && rowsOn < 17) {

                // The for every row we haven't done
            	 for (int r = fadedRows + 1; r <= rowsOn && r < panel.getPanelHeight(); r++) {
            		 	for (int c = 0; c < panel.getPanelWidth(); c++) {
                        // Set the color to the alternating blue colors
            		 		double waveAngle = c*8*Math.PI/16;
            		 		//System.out.println("r " + r + "  height: " + (int)(1*Math.sin(waveAngle) +6)+ " ");
         		 			if(r <= (int)(Math.sin(waveAngle) +8)){
		                    	if(r%2 == 0 && c%2 == 1 || r%2 == 1 && c%2 == 0)
		                        panel.setColor((15-r), (15-c), color1);
		                    	else if(r%2 == 1 && c%2 == 1 || r%2 == 0 && c%2 == 0)
		                        panel.setColor((15-r), (15-c), color2);
            		 		}
            		 	}
            	 }
            	 
            }
            // After that, have a wave transformation every 3 time intervals
            if(rowsOn >7 && rowsOn%3 == 0 )
    		 {
            	 for (int r = 6; r < 11; r++) 
    	         {
    	                // and every column in that row
    	                for (int c = 0; c < panel.getPanelWidth(); c++) 
    	                {
    	                    // Set the color to the initial color, black
    	                    panel.setColor(r, c, sysColor(255, 255, 255));
    	                }
    	                for (int r1 = 0; r1 < panel.getPanelHeight(); r1++) {
                		 	for (int c = 0; c < panel.getPanelWidth(); c++) {
                            // Set the color to the color we picked when making the animation
                		 		double waveAngle = c*8*Math.PI/16 + Math.PI/2*(rowsOn);
                		 		//System.out.println("r " + r1 + "  height: " + (int)(1*Math.cos(waveAngle))+ " ");
             		 			if(r1 <= (int)(1*Math.sin(waveAngle + (rowsOn)) +9)){
    		                    	if(r1%2 == 0 && c%2 == 1 || r1%2 == 1 && c%2 == 0)
    		                        panel.setColor((15-r1), (15-c), color1);
    		                    	else if(r1%2 == 1 && c%2 == 1 || r1%2 == 0 && c%2 == 0)
    		                        panel.setColor((15-r1), (15-c), color2);
                		 		}
                        }
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
    public Color getRandomColor() {
        return sysColor((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }
}
