/**
 * Balloontip - Balloon tips for Java Swing applications
 * Copyright 2007-2010 Bernhard Pauler, Tim Molderez
 * 
 * This file is part of Balloontip.
 * 
 * Balloontip is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Balloontip is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Balloontip. If not, see <http://www.gnu.org/licenses/>.
 */

package com.fafasoft.flow.ui.widget.tip.positioners;

import java.awt.Rectangle;

/**
 * This class positions a balloon tip above the component it's attached to, with the tip on the left
 * @author Tim Molderez
 */
public class LeftAbovePositioner extends BasicBalloonTipPositioner {
	public LeftAbovePositioner(int hO, int vO) {
		super(hO, vO);
	}

	public void determineAndSetLocation(Rectangle attached) {
		// First calculate the location, without applying any correction tricks
		int balloonWidth = balloonTip.getPreferredSize().width;
		int balloonHeight = balloonTip.getPreferredSize().height;
		flipX = false;
		flipY = false;
		
		hOffset = preferredHorizontalOffset;
		if (fixedAttachLocation) {
			x = new Float(attached.x + attached.width * attachLocationX).intValue() - hOffset;
			y = new Float(attached.y + attached.height * attachLocationY).intValue() - balloonHeight;
		} else {
			x = attached.x;
			y = attached.y - balloonHeight;
		}
		
		// Apply orientation correction
		if (orientationCorrection) {
			// Check collision with the top of the window
			if (y < 0) {
				flipY = true;
				if (fixedAttachLocation) {
					y += balloonHeight;
				} else {
					y = attached.y + attached.height;
				} 
			}
			
			// Check collision with the left side of the window
			if (x < 0) {
				flipX = true;
				if (fixedAttachLocation) {
					x -= balloonWidth - 2*hOffset;
				} else {
					x = attached.x + attached.width - balloonWidth;
				}
				hOffset = balloonWidth - hOffset;
			}
		}
		
		// Apply offset correction
		if (offsetCorrection) {
			applyOffsetCorrection();
		}
		
		// Finally set the balloon tip's location
		if (flipX) {
			balloonTip.getStyle().setHorizontalOffset(balloonWidth - hOffset);
		} else {
			balloonTip.getStyle().setHorizontalOffset(hOffset);
		}
		
		balloonTip.getStyle().flip(flipX, flipY);
		balloonTip.setBounds(x, y, balloonTip.getPreferredSize().width, balloonTip.getPreferredSize().height);
		
		balloonTip.revalidate(); // Revalidate is needed in case the balloon gets flipped; validate wouldn't do in that case.
		if (hOffset != preferredHorizontalOffset) {
			balloonTip.repaint(); // In certain cases, when the horizontal offset changes, it doesn't get redrawn properly without a repaint...
		}
	}
}