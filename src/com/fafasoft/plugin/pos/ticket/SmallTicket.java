package com.fafasoft.plugin.pos.ticket;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.Custom;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.util.DateHelper;

import javax.print.PrintService;
import javax.swing.*;
import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Vector;

public class SmallTicket implements Printable {
	private Vector printData;
	private String yf;
	private String shishou;
	private String zhaoling;
	private Custom custom;
	public SmallTicket(Vector vector, String yinfu, String shishou,
			String zhaoling, Custom custom) {
		this.printData = vector;
		this.yf = yinfu;
		this.shishou = shishou;
		this.zhaoling = zhaoling;
		this.custom = custom;
	}

	private double getHeight() {
		double height = 40.0D;
		height += this.printData.size() * 20;
		height = height + 240D;
		return height;
	}

	private double getWidth() {
		return 164.0D;
	}

	public void print() {
		Book book = new Book();

		PageFormat pf = new PageFormat();
        pf.setOrientation(1);// LANDSCAPE表示竖打;PORTRAIT表示横

		Paper p = new Paper();
		p.setSize(getWidth(), getHeight());// 纸张大小
		p.setImageableArea(7.0D, 30.0D, 260.0D, 343.0D);
		pf.setPaper(p);
		book.append(this, pf);

		ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
		String printerName = "";
		Config configName = configDAO.getConfig("PRINTNAME");
		if (configName != null) {
			printerName = configName.getValue();
		}

		PrinterJob job = PrinterJob.getPrinterJob();

		job.setPageable(book);
		try {
			PrintService[] pServices = PrinterJob.lookupPrintServices();
			if (pServices.length == 0) {
				JOptionPane.showMessageDialog(MainWindows.getInstance(),
						"无法找到打印机:" + printerName, "打印通知", 2);
			} else {
				PrintService pri = null;
				for (int i = 0; i < pServices.length; i++) {
					PrintService nameob = pServices[i];
					if (printerName.equals(nameob.getName())) {
						pri = nameob;
						break;
					}
				}
				if (pri != null) {
					job.setPrintService(pri);
					job.print();
				} else {
					JOptionPane.showMessageDialog(MainWindows.getInstance(),
							"请在系统设置里设置小票打印机:" + printerName, "打印通知", 2);
				}
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public int print(Graphics gra, PageFormat pf, int pageIndex)
			throws PrinterException {
		String title = "购物小票";
		ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
		Config configTile = configDAO.getConfig("PRINTTITLE");
		if (configTile != null) {
			title = configTile.getValue();
		}
		String sheet = "谢谢惠顾，请保管好您的小票";
		Config configsheet = configDAO.getConfig("PRINTSHEET");

		if (configsheet != null) {
			sheet = configsheet.getValue();

		}

		Config configaddr = configDAO.getConfig(Constant.PRINT_ADDRESS);

		Config configphone = configDAO.getConfig(Constant.PRINT_phone);

		Graphics2D g2 = (Graphics2D) gra;

		g2.setColor(Color.BLACK);
		// 打印起点坐标
		double x = pf.getImageableX();
		double y = pf.getImageableY();

		switch (pageIndex) {
		case 0:
			Font font = new Font("宋体", Font.PLAIN, 10);
			g2.setFont(font);

			float[] dash1 = { 2.0F };

			g2.setStroke(new BasicStroke(0.5F, 0, 0, 2.0F, dash1, 0.0F));

			float heigth = font.getSize2D();
			int strWidth = g2.getFontMetrics().stringWidth(title);
			g2.drawString(title, (float) (x + strWidth / 2), (float) y + 1.0F
					* heigth);

			String custoname = custom == null ? "普通客户" : custom.getName();
			g2.drawString("客户:" + custoname, (float) x, (float) y + 1.0F
					* heigth + 10.0F);
			String date = "日期:" + DateHelper.getNowTime();
			g2.drawString(date, (float) x, (float) y + 1.0F * heigth + 20.0F);

			g2.drawString("货号", (float) x, (float) y + 1.0F * heigth + 30.0F);
			g2.drawString("数量*单价", (float) x + 40.0F, (float) y + 1.0F * heigth
					+ 30.0F);
			g2.drawString("金额", (float) x + 100.0F, (float) y + 1.0F * heigth
					+ 30.0F);
			g2.drawLine((int) x, (int) (y + 1.0F * heigth + 35.0D),
					(int) (getWidth() - x), (int) (y + 1.0F * heigth + 35.0D));
			float rowh = (float) y + 1.0F * heigth + 48F;
			double jians = 0.0D;

			if (this.printData.size() > 0) {
				for (int i = 0; i < this.printData.size(); i++) {
					Vector rowdata = (Vector) this.printData.get(i);
					g2.drawString(String.valueOf(rowdata.get(2)), (float) x,
							rowh);
					rowh += 10.0F;

					g2.drawString(String.valueOf(rowdata.get(1)), (float) x,
							rowh);
					jians += ((Double) rowdata.get(3)).doubleValue();
					g2.drawString(
							String.valueOf(((Double) rowdata.get(3))
									.doubleValue())
									+ "*"
									+ String.valueOf(rowdata.get(4)),
							(float) x + 50.0F, rowh);
					g2.drawString(String.valueOf(rowdata.get(6)),
							(float) x + 100.0F, rowh);
					rowh += 12.0F;
				}
			}
			g2.drawLine((int) x, (int) rowh, (int) (getWidth() - x), (int) rowh);
			g2.drawString("购买件数:" + jians, (float) x, rowh + 10.0F);
			g2.drawString("应付金额:" + this.yf, (float) x + 70.0F, rowh + 10.0F);
			g2.drawString("实收金额:" + this.shishou, (float) x, rowh + 20.0F);
			g2.drawString("找零:" + this.zhaoling, (float) x + 70.0F,
					rowh + 20.0F);
			g2.drawString("操作员:" + SysEnv.getInstance().getloginUserName(),
					(float) x, rowh + 30.0F);

			g2.drawLine((int) x, (int) (rowh + 35.0F), (int) (getWidth() - x),
					(int) (rowh + 35.0F));

			FontMetrics fm = g2.getFontMetrics();
			int lineHeight = fm.getHeight();
			rowh = rowh + 35.0F + lineHeight;
			int w = (int) getWidth() - 20;
			if (configaddr != null && configaddr.getValue().trim().length() > 0) {
				int curY = drawString(g2, "地址:" + configaddr.getValue().trim(),
						(int) x, (int) rowh, w);
				rowh = curY + lineHeight;
			}
			if (configphone != null
					&& configphone.getValue().trim().length() > 0) {

				g2.drawString("电话:" + configphone.getValue().trim(), (float) x,
						rowh);
				rowh = rowh + lineHeight;
			}
			try {
				int rowhy = (int) (rowh);

				drawString(g2, sheet, (int) x, rowhy, w);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
		return 1;
	}

	public int drawString(Graphics g, String s, int x, int y, int width) {
		FontMetrics fm = g.getFontMetrics();
		int lineHeight = fm.getHeight();

		int curX = x;
		int curY = y;
		char[] words = s.toCharArray();
		for (char word : words) {
			// Find out thw width of the word.
			int wordWidth = fm.stringWidth(String.valueOf(word));
			// If text exceeds the width, then move to next line.
			if (curX + wordWidth >= x + width) {
				curY += lineHeight;
				curX = x;
			}
			g.drawString(String.valueOf(word), curX, curY);
			// Move over to the right for next word.
			curX += wordWidth;
		}
		return curY;
	}

}