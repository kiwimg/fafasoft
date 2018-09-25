package com.fafasoft.flow.ui.widget.table;

import net.coobird.thumbnailator.Thumbnails;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		setText((String) value);
		try {
			File oufile = new File("config/images/" + value + ".png");
			if (oufile.exists()) {
				BufferedImage thumbnail = Thumbnails.of(oufile).size(25, 25)
						.asBufferedImage();
				Image img = Toolkit.getDefaultToolkit().createImage(
						thumbnail.getSource());
				setIcon(new ImageIcon(img));
				setToolTipText("<html><img src='file:"+oufile.getAbsolutePath()+"'></img></html>");			
			} else {
				setIcon(null);
				setToolTipText(null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
}
