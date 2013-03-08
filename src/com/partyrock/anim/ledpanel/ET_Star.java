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
 * This is a basic animation that will wipe an LED panel from left to right with alternating LED pixel colors
 * 
 * @author Emily Tran
 * 
 */
public class ET_Star extends ElementAnimation {
	
    private Color yellow = sysColor(255, 255, 51);

	boolean[][] starPoints = new boolean[16][16]; 
	

    public ET_Star(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();
    }
    
    public void trigger() 
    {
    	starPoints[0][8] = true;
    	starPoints[1][7] = true;
    	starPoints[2][7] = true;
    	starPoints[3][6] = true;
    	starPoints[4][6] = true;
    	
    	starPoints[5][5] = true;
    	starPoints[5][4] = true;
    	starPoints[5][3] = true;
    	starPoints[5][2] = true;
    	starPoints[5][1] = true;
    	
    	starPoints[6][1] = true;
    	
    	starPoints[7][2] = true;
    	starPoints[8][3] = true;
    	starPoints[9][4] = true;
    	
    	starPoints[10][3] = true;
    	starPoints[11][3] = true;
    	starPoints[12][2] = true;
    	starPoints[12][8] = true;
    	
    	starPoints[13][2] = true;
    	starPoints[13][7] = true;
    	starPoints[13][6] = true;
    	
    	starPoints[14][1] = true;
    	starPoints[14][4] = true;
    	starPoints[14][5] = true;
    	
    	starPoints[15][1] = true;
    	starPoints[15][2] = true;
    	starPoints[15][3] = true;
    	
    	//eyes
    	starPoints[6][7] = true;
    	starPoints[7][7] = true;
    	starPoints[8][7] = true;
    	
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
                    if(starPoints[r][c]) 
                    	{
                    		panel.setColor(r, c, yellow);
                    		panel.setColor(r, 16-c, yellow);
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

            int columnsOn = (int) (percentage*25);

            // So if we haven't already done this column 
            if (columnsOn%4 == 0) {

            	panel.setColor(6, 7, yellow);
            	panel.setColor(7, 7, yellow);
            	panel.setColor(8, 7, yellow);
            	panel.setColor(6, 9, yellow);
            	panel.setColor(7, 9, yellow);
            	panel.setColor(8, 9, yellow);
            	
            }
            else
            {
                	panel.setColor(6, 7, sysColor(0, 0, 0));
                	panel.setColor(7, 7, sysColor(0, 0, 0));
                	panel.setColor(8, 7, sysColor(0, 0, 0));
                	panel.setColor(6, 9, sysColor(0, 0, 0));
                	panel.setColor(7, 9, sysColor(0, 0, 0));
                	panel.setColor(8, 9, sysColor(0, 0, 0));
                
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
