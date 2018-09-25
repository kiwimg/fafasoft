package com.fafasoft.flow;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.dao.impl.UserRightDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.User;
import com.fafasoft.flow.pojo.UserRight;

import java.util.Properties;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class SysEnv {
	private static SysEnv sysEnv;
	private String skin = "aTunes Dark";
	private String frameMaxpolicy = "false";
	private String version;
	private String loginUserid;
	private String loginUserName;
	private long messageVersion;
	private boolean isSuggest;
	private Properties remotePro;
	static {
		sysEnv = new SysEnv();
	}

	private SysEnv() {
		ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
		Config configSkin = configDAO.getConfig(Constant.SKIN);
		if (configSkin != null) {
			skin = configSkin.getValue();
		}

		Config configMesaageVserion = configDAO
				.getConfig(Constant.MESSAGE_VERSION);
		if (configMesaageVserion != null) {
			this.messageVersion = Long.parseLong(configMesaageVserion
					.getValue());
		}

		Config configIFRAME_MAX = configDAO.getConfig(Constant.MAIN_IFRAME_MAX);

		if (configIFRAME_MAX != null) {
			frameMaxpolicy = configIFRAME_MAX.getValue();
		}

		Config versionconfig = configDAO.getConfig(Constant.VERSION);

		if (versionconfig == null || "".equals(versionconfig.getValue())) {
			version = "1.1";
		} else {
			version = versionconfig.getValue();
		}

		Config suggest = configDAO.getConfig(Constant.VIEW_SET_SUGGEST);

		if (suggest == null || "".equals(suggest.getValue())) {
			isSuggest = true;
		} else {
			isSuggest = Boolean.valueOf(suggest.getValue());
		}
	}

	public boolean isSetInitAdminPass() {
		ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
		Config configAdminPass = configDAO.getConfig(Constant.IS_SETADMIN_PASS);

		return configAdminPass != null;
	}

	public static SysEnv getInstance() {
		return sysEnv;
	}

	public String getSkin() {
		return skin;
	}

	public User getAdminUser() {
		return DAOContentFactriy.getUserDAO().getUserByid("10000");
	}

	public String getLoginUserId() {
		return loginUserid;
	}

	public void setLoginUserId(String loginUserid) {
		this.loginUserid = loginUserid;
	}

	public void setloginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getloginUserName() {
		return loginUserName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getFrameMaxPolicy() {
		return frameMaxpolicy;
	}

	public void setFrameMaxPolicy(String frameMaxpolicy) {
		this.frameMaxpolicy = frameMaxpolicy;
	}

	public long getMessageVersion() {
		return this.messageVersion;
	}

	public void setMessageVersion(long messageVersion) {
		this.messageVersion = messageVersion;
	}

	public boolean isSuggest() {
		return isSuggest;
	}

	public void setSuggest(boolean isSuggest) {
		this.isSuggest = isSuggest;
	}
	
	public Properties getRemoteProperties(){
		return remotePro;
	}
	public void setRemoteProperties(Properties pr){
		this.remotePro =pr;
	}
	public String[] getRight() {
		String longinname = SysEnv.getInstance().getLoginUserId();
		String[] right = null;
		if (Constant.ADMIN.equals(longinname)) {
			right = Constant.RIGHT;
		} else {
			UserRightDAOImpl rightMoudle = DAOContentFactriy.getUserRightDAO();
			UserRight userRight = rightMoudle.get(longinname);

			right = userRight.getRight().split(",");
		}
		return right;
	}
}
