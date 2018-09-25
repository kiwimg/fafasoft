package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.CustomtTypeDAOImpl;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.CustomType;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.User;

import java.util.List;
public class SelectType {
	public static Object[] getOptions(String type) {
		Object[] ob = new Object[] {};
		OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
		List list = optionMoudle.getOption(type);
		if (list != null && list.size() > 0) {
			ob = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Option option = (Option) list.get(i);
				ob[i] = new Options(option.getId(), option.getText());
			}
		}
		return ob;
	}

	public static Object[] getOptionsWithAll(String type) {
		Object[] ob = new Object[] {};
		OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
		List list = optionMoudle.getOption(type);
		if (list != null && list.size() > 0) {
			ob = new Object[list.size() + 1];
			ob[0] = new Options("all", "全部类型");
			for (int i = 0; i < list.size(); i++) {
				Option option = (Option) list.get(i);
				ob[i + 1] = new Options(option.getId(), option.getText());
			}
		}
		return ob;
	}

	public static Object[] getCustomType() {
		Object[] ob = new Object[] {};
		CustomtTypeDAOImpl typeMoudle = DAOContentFactriy.getCustomtTypeDAO();
		List list = typeMoudle.getCustomtTypes();
		if (list != null && list.size() > 0) {
			ob = new Object[list.size()];

			for (int i = 0; i < list.size(); i++) {
				CustomType option = (CustomType) list.get(i);
				ob[i] = new Options(option.getTypename(), option.getTypename());
			}
		}
		return ob;
	}

	public static Object[] getUserList() {
		Object[] ob = new Object[] {};
		List list = DAOContentFactriy.getUserDAO()
				.getUser(Constant.USER_TYPE_M);
		if (!list.isEmpty()) {
			ob = new Object[list.size()];
			for (int i = 0; i < list.size(); i++) {
				User user = (User) list.get(i);
				if (Constant.ADMIN.equals(user.getId())) {
					// user.setUsernmae(Constant.ADMIN_NAME);
					ob[0] = user;
				} else {
					if (i == 0) {
						ob[i + 1] = user;
					} else {
						ob[i] = user;
					}
				}
			}
		}
		return ob;
	}

	public static Object[] getUserLists() {
		Object[] ob = new Object[] {};
		List list = DAOContentFactriy.getUserDAO()
				.getUser(Constant.USER_TYPE_M);
		if (!list.isEmpty()) {
			ob = new Object[list.size() + 1];
			User userall = new User();
			userall.setUsernmae("全部人员");
			userall.setId(null);
			ob[0] = userall;
			for (int i = 0; i < list.size(); i++) {
				User user = (User) list.get(i);
				if (Constant.ADMIN.equals(user.getId())) {
					// user.setUsernmae(Constant.ADMIN_NAME);
					ob[i + 1] = user;
				} else {
					ob[i + 1] = user;
				}
			}
		}
		return ob;
	}

	public static Object[] getStockAlarmTypes() {
		Options option1 = new Options("<=", "库存不足");
		Options option2 = new Options(">=", "库存过多");
		return new Object[] { option1, option2 };
	}

	public static Object[] getStockTypes() {
		Options option1 = new Options(Constant.STCOK_TYPE_JINHUO, "采购进货");
		Options option2 = new Options(Constant.STCOK_TYPE_CAIGOUTH, "采购退货");
		Options option3 = new Options(Constant.STCOK_TYPE_GUKETH, "顾客退货");
		return new Object[] { option1, option2, option3 };
	}

	public static Object[] getSellTypes() {
		// "全部", "销售", "退货"
		Options option1 = new Options(null, "全部");
		Options option2 = new Options(Constant.FLOW_TYPE_SELL, "销售");
		Options option3 = new Options(Constant.FLOW_TYPE_TH, "退货");
		return new Object[] { option1, option2, option3 };
	}

	public static Object[] getCustomersAccountTypes(String name) {
		// new String[] {"应收(客户欠我款)", "预收(我收客户款)", "应付(我欠客户款)", "预付(我付客户款)"})
		Options option1 = new Options(Constant.CUSTOMER_ACCOUNT_YINGS, "应收("
				+ name + "欠我款)");
		Options option2 = new Options(Constant.CUSTOMER_ACCOUNT_YUS, "预收(我收"
				+ name + "款)");
		Options option3 = new Options(Constant.CUSTOMER_ACCOUNT_YINGF, "应付(我欠"
				+ name + "款)");
		Options option4 = new Options(Constant.CUSTOMER_ACCOUNT_YUF, "预付(我付"
				+ name + "款)");
		return new Object[] { null, option1, option2, option3, option4 };
	}

	public static Object[] getStockTypeList() {
		StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
		List list = stockDAO.getStockTypes();
		Object[] object = new Object[] {};
		if (list != null && list.size() > 0) {
			object = new Object[list.size() + 1];
			object[0] = null;
			for (int i = 0; i < list.size(); i++) {
				object[i + 1] = list.get(i);
			}
		}
		return object;
	}


	public static Object[] getPrintMode() {
		Options option1 = new Options(Constant.PRINT_MODE_NO, "销售后不打印");
		Options option2 = new Options(Constant.PRINT_MODE_YESNO, "打印时询问");
		Options option3 = new Options(Constant.PRINT_MODE_AUTO, "自动打印");

		return new Object[] { option1, option2, option3 };
	}
	public static boolean isequals(String text, String type) {
		OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
		List list = optionMoudle.getOption(type);
		boolean is = false;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Option option = (Option) list.get(i);
				if (text.equals(option.getText())) {
					is = true;
					break;
				}
			}
		}
		return is;
	}
}
