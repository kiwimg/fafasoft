package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.service.StockImageService;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.skin.image.ImageFilter;
import com.fafasoft.flow.ui.skin.image.ImagePreview;
import com.fafasoft.flow.util.DateHelper;
import com.fafasoft.flow.util.GuiUtils;

import net.coobird.thumbnailator.Thumbnails;

import org.jdesktop.swingx.JXDatePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class KuCunDialog extends JDialog {

    private StockImageService stockImageService=StockImageService.getInstance();

    private JXDatePicker datePicker;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JComboBox comboBox;
    private JLabel label_8;
    private JLabel label_9;
    private JTable tablex;
    private Stock stockx;
    private CallBack callBackx;
    private JTextField textField_7;
    private JLabel lblNewLabelIco;
    public KuCunDialog(Component owner, String tilte, Stock stock, JTable table, CallBack callBack) {
        setResizable(false);
        setTitle(tilte);
        this.stockx = stock;
        this.tablex = table;
        this.callBackx = callBack;
        setSize(new Dimension(499, 381));
        setLocationRelativeTo(owner);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u8D27\u7269\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel label = new JLabel("货号：");
        label.setBounds(57, 74, 36, 15);
        panel.add(label);

        textField = new JTextField();
//        if (this.stockx != null) {
//            textField.setEditable(false);
//        }

        textField.setBounds(97, 71, 125, 21);
        textField.setColumns(10);
        panel.add(textField);


        JLabel label_1 = new JLabel("名称：");
        label_1.setBounds(57, 102, 36, 15);
        panel.add(label_1);

        textField_1 = new JTextField();
        textField_1.setBounds(97, 99, 190, 21);
        textField_1.setColumns(10);
        panel.add(textField_1);


        JLabel label_2 = new JLabel("类别：");
        label_2.setBounds(57, 131, 36, 15);
        panel.add(label_2);

        comboBox = new JComboBox();
        comboBox.setEditable(true);
        comboBox.setModel(new DefaultComboBoxModel(SelectType.getOptions(Constant.TYPE_HW)));
        comboBox.setBounds(97, 128, 190, 21);
        panel.add(comboBox);

        JLabel label_3 = new JLabel("颜色：");
        label_3.setBounds(57, 159, 36, 15);
        panel.add(label_3);

        textField_2 = new JTextField();
        textField_2.setBounds(97, 156, 125, 21);
        textField_2.setColumns(10);
        panel.add(textField_2);


        JLabel label_4 = new JLabel("规格：");
        label_4.setBounds(57, 190, 36, 15);
        panel.add(label_4);

        textField_3 = new JTextField();
        textField_3.setBounds(97, 187, 125, 21);
        textField_3.setColumns(10);
        panel.add(textField_3);


        JLabel label_5 = new JLabel("进货价：");
        label_5.setBounds(45, 221, 48, 15);
        panel.add(label_5);

        JLabel label_6 = new JLabel("销售价：");
        label_6.setBounds(190, 221, 48, 15);
        panel.add(label_6);

        JLabel label_7 = new JLabel("进货数量：");
        label_7.setBounds(33, 253, 60, 15);
        panel.add(label_7);

        textField_4 = new JTextField();
        textField_4.setBounds(97, 218, 66, 21);
        String dubString = "1234567890.";
        textField_4.setDocument(new LimitedDocument(20, dubString));
        textField_4.setColumns(10);
        panel.add(textField_4);


        textField_5 = new JTextField();
        textField_5.setBounds(240, 218, 66, 21);
        textField_5.setDocument(new LimitedDocument(20, dubString));
        textField_5.setColumns(10);
        panel.add(textField_5);


        textField_6 = new JTextField();
        textField_6.setBounds(97, 250, 66, 21);
        String intString = "1234567890";
        textField_6.setDocument(new LimitedDocument(20, intString));
        textField_6.setColumns(10);
        panel.add(textField_6);

        JButton button = new JButton("保存");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((JButton) e.getSource()).setEnabled(false);
                submmit();
                ((JButton) e.getSource()).setEnabled(true);

            }
        });
        button.setBounds(356, 297, 93, 30);
        panel.add(button);

        JButton button_1 = new JButton("取消");
        button_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        button_1.setBounds(240, 297, 93, 30);
        panel.add(button_1);

        label_8 = new JLabel("");
        label_8.setForeground(Color.RED);
        label_8.setBounds(57, 20, 335, 15);
        panel.add(label_8);

        label_9 = new JLabel("");
        label_9.setBounds(232, 74, 131, 15);
        panel.add(label_9);

        JLabel label_10 = new JLabel("时间：");
        label_10.setBounds(57, 45, 36, 15);
        panel.add(label_10);

        datePicker = new JXDatePicker();
        datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
        datePicker.getEditor().setEditable(false);
        datePicker.setFormats("yyyy-MM-dd");
        datePicker.setDate(DateHelper.currentDate());
        datePicker.setBounds(97, 41, 104, 23);
        panel.add(datePicker);

        JLabel label_11 = new JLabel("剩余数量：");
        label_11.setBounds(178, 253, 60, 15);
        panel.add(label_11);

        textField_7 = new JTextField();
        textField_7.setBounds(240, 250, 66, 21);
        panel.add(textField_7);
        textField_7.setColumns(10);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setIcon(new ImageIcon(KuCunDialog.class.getResource("/com/fafasoft/flow/resource/image/addpic.png")));
        btnNewButton.setBounds(425, 155, 18, 18);
        panel.add(btnNewButton);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("选择图片文件");
				chooser.setAcceptAllFileFilterUsed(false);

				// Add the preview pane.
				chooser.setAccessory(new ImagePreview(chooser));
				chooser.setFileFilter(new ImageFilter());
				int returnVal = chooser.showOpenDialog(MainWindows
						.getInstance());
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					try {
						File outfile = new File("./config/images/");

						if (outfile != null && !outfile.exists()) {
							outfile.mkdirs();
						}

						BufferedImage originalImage = ImageIO.read(chooser
								.getSelectedFile());

						BufferedImage thumbnail = Thumbnails.of(originalImage)
								.size(110, 113).asBufferedImage();
						Image img = Toolkit.getDefaultToolkit().createImage(
								thumbnail.getSource());
						lblNewLabelIco.setIcon(new ImageIcon(img));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
        	}
        });
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setIcon(new ImageIcon(KuCunDialog.class.getResource("/com/fafasoft/flow/resource/image/delpic.png")));
        btnNewButton_1.setBounds(405, 155, 18, 18);
        panel.add(btnNewButton_1);
            	btnNewButton_1.addMouseListener(new MouseAdapter() {
        			public void mouseClicked(MouseEvent e) {
        				lblNewLabelIco.setIcon(null);
        			}
        		});
        
        lblNewLabelIco = new JLabel("");
        lblNewLabelIco.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabelIco.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabelIco.setBorder(new TitledBorder(null, "\u8D27\u7269\u56FE\u7247", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        lblNewLabelIco.setBounds(327, 41, 122, 136);
     	Image img = Toolkit.getDefaultToolkit().createImage(
				"./config/images/" +  this.stockx.getCatno() + ".png");
		lblNewLabelIco.setIcon(new ImageIcon(img));
        panel.add(lblNewLabelIco);
        if (this.stockx != null) {
            setValues();
        }
    }

    private void submmit() {
        Stock stock = getFormdata();
        if (stock != null) {
            setFormdata(stock);
            label_8.setText("进货数据保存成功!");
            clear();
            dispose();
        }
    }

    private void setValues() {
        textField.setText(stockx.getCatno());
        textField_1.setText(stockx.getStockname());
        textField_2.setText(stockx.getColor());
        textField_3.setText(stockx.getSpecif());
        textField_4.setText(stockx.getCostprice().toString());
        textField_5.setText(stockx.getSellprice().toString());
        textField_6.setText(String.valueOf(stockx.getAmount()));
        textField_7.setText(String.valueOf(stockx.getSyamount()));

        String type = stockx.getType();
        ComboBoxModel comboBoxModel = comboBox.getModel();
        int n = comboBoxModel.getSize();
        for (int i = 0; i < n; i++) {
            Object ob = comboBoxModel.getElementAt(i);
            if (String.valueOf(ob).equals(type)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }

    }

    private void clear() {
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
        textField_4.setText("");
        textField_6.setText("");
        textField_5.setText("");
        label_9.setText("");
    }

    private Stock getFormdata() {
        String catno = textField.getText();
        String name = textField_1.getText();
        String type = comboBox.getEditor().getItem().toString();
        String color = textField_2.getText();
        String specif = textField_3.getText();
        String costprice = textField_4.getText();
        String amount = textField_6.getText();
        String syamount = textField_7.getText();
        String sellprice = textField_5.getText();
        String date = datePicker.getEditor().getText();

        String nows = DateHelper.getNowDateTime();
        if (date == null || date.trim().length() == 0) {
            date = DateHelper.getNowDateTime();
        }
        long now = Long.parseLong(nows.replaceAll("-", ""));
        long e = Long.parseLong(date.replaceAll("-", ""));
        if (e > now) {
            label_8.setText("输入时间错误！只能录入今天以前的进货数据");
            return null;
        }
        if (catno == null || catno.trim().length() == 0) {
            label_8.setText("请输入货物号码");
            return null;
        }
        if (name == null || name.trim().length() == 0) {
            label_8.setText("请输入货物名称");
            return null;
        }

        if (type.trim().length() == 0 && comboBox.getItemCount() == 0) {
            label_8.setText("请设置货物类型!");
            return null;
        } else {
            if (type == null || type.trim().length() == 0) {
                label_8.setText("请设置货物类型!");
                return null;
            } else {

                boolean is = SelectType.isequals(type, Constant.TYPE_HW);
                if (!is) {  
                OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
                    Option option = new Option();
                    option.setId(String.valueOf(System.currentTimeMillis()));
                    option.setText(type);
                    option.setType(Constant.TYPE_HW);
                    boolean isd = optionMoudle.addOption(option);
                    if (isd) {
                        DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) comboBox.getModel();
                        comboBoxModel.insertElementAt(type, 0);
                    }
                }
            }
        }
        if (costprice == null || costprice.trim().length() == 0) {
            label_8.setText("请输入进货成本价格");
            return null;
        }

        if (sellprice == null || sellprice.trim().length() == 0) {
            label_8.setText("请输入货物销售价格");
            return null;
        }
        if (amount == null || amount.trim().length() == 0) {
            label_8.setText("请输入数量");
            return null;
        }
        if (syamount == null || syamount.trim().length() == 0) {
            label_8.setText("请输入剩余数量");
            return null;
        }
        BigDecimal bigDecostprice = new BigDecimal(costprice);
        BigDecimal bigDesellprice = new BigDecimal(sellprice);

        if (bigDesellprice.compareTo(bigDecostprice) == -1) {
            label_8.setText("输入货物销售价格小于成本价,请重新输入!");
            textField_5.selectAll();
            textField_5.requestFocus();
            return null;
        }
        if (label_8.getText() != null && label_8.getText().trim().length() > 0) {
            label_8.setText("");
        }

        double amounts = Double.parseDouble(amount);

        if (amounts == 0) {
            label_8.setText("输入数量应该大于零！");
            textField_6.selectAll();
            textField_6.requestFocus();
            return null;
        }
        double syamounts =  Double.parseDouble(syamount);
        if (syamounts < 0) {
            label_8.setText("输入剩余数量应该大于等于零！");
            textField_7.selectAll();
            textField_7.requestFocus();
            return null;
        }
        if (syamounts > amounts) {
            label_8.setText("输入剩余数量应该小于等于进货数量！");
            textField_7.selectAll();
            textField_7.requestFocus();
            return null;
        }

        BigDecimal costps = bigDecostprice.multiply(BigDecimal.valueOf(amounts));
        Stock stock = new Stock();
        stock.setId(stockx.getId());
        stock.setCatno(catno);
        stock.setAmount(amounts);
        stock.setSyamount(syamounts);
        stock.setCostprice(bigDecostprice);
        stock.setSellprice(bigDesellprice);
        stock.setTotal(costps);
        stock.setType(type);
        stock.setDate(date);
        if (specif.trim().length() > 0) {
            stock.setSpecif(specif);
        }
        if (color.trim().length() > 0) {
            stock.setColor(color);
        }
        stock.setStockname(name);
        stock.setStockFlag(Constant.STCOK_TYPE_JINHUO);
        stock.setRecorddate(DateHelper.getNowDateTime());
        return stock;
    }

    public void setFormdata(Stock stock) {

        if (this.stockx != null) {
            Object[] rowData = new Object[]{stockx.getId(), stock.getCatno(),
                    stock.getSyamount(), stock.getAmount(), stock.getType(), stock.getSellprice(), stock.getCostprice(),
                    stock.getStockname(), stock.getSpecif(), stock.getColor(), stock.getDate(), stock.getRemarks(),100d*stock.getSyamount()/stock.getAmount(),"删除"};
            StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
            stockMoudle.updateStock(stock);
            if( !stockx.getCatno().equals(stock.getCatno())) {
            	FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
            	flowLogMoudle.updateCatno(stock.getCatno(),stockx.getCatno(),stock.getType(),stockx.getType());
            }
            int row = tablex.getSelectedRow();
            DefaultTableModel tableModel = (DefaultTableModel) this.tablex.getModel();
            tableModel.removeRow(row);
            tableModel.insertRow(row, rowData);

    		if (lblNewLabelIco.getIcon() != null&& lblNewLabelIco.getIcon().getIconWidth()>-1 &&
    				lblNewLabelIco.getIcon().getIconHeight() >-1) {
    			ImageIcon imageIcon = (ImageIcon) lblNewLabelIco.getIcon();
    			BufferedImage originalImage = GuiUtils.getBufferedImageFromImage(imageIcon
    					.getImage(),imageIcon.getIconWidth(),imageIcon.getIconHeight());

    			File outFile = new File("./config/images",
    					String.valueOf(stock.getCatno()) + ".png");
    			try {
    				Thumbnails
    						.of(originalImage)
    						.size(imageIcon.getIconWidth(),
    								imageIcon.getIconHeight()).toFile(outFile);
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    		}else if(lblNewLabelIco.getIcon()==null){
                stockImageService.deleteImageFile(stock.getCatno());
            }
        }
        if (this.callBackx != null) {
            this.callBackx.updateView();
        }
        lblNewLabelIco.setIcon(null);
    }
    public interface CallBack {
        public void updateView();
    }
}
