package com.fafasoft.flow.service;

import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: noah
 * Date: 11/30/12
 * Time: 9:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class StockImageService {

    private  static StockImageService service;

    public static StockImageService getInstance() {
        if(service==null){
            service=new StockImageService();
        }
        return service;
    }

    public void deleteImageFile(String catno){
        File file = new File("./config/images",catno + ".png");
        if(file.isFile() && file.exists()){
            file.delete();
        }
    }

    public ImageIcon getImageIcon(String catno){
        Image img = Toolkit.getDefaultToolkit().createImage(
                "./config/images/" + catno + ".png");
        if(img != null&& ((ToolkitImage) img).getHeight() >-1 && ((ToolkitImage) img).getWidth()>-1) {
            return new ImageIcon(img);
        }else {
            return null;
        }
    }
}
