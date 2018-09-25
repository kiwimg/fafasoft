package com.fafasoft.flow;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class Constant {
    public static final String VERSION = "VERSION";
    
    public static final String MACHINE_MAC = "MACHINEMAC";
    public static final String LAST_SYN_TIME = "LASTSYNTIME";
    public static final String SHOP_ID = "SHOPID";
    public static final String USERID = "USERID";
    public static final String Authentication_CODE = "authenticationCODE";
    
    
    /**
     * domain_name="com.wateray"
     */
    public static final String DOMAIN_NAME = "com.fafasoft";

    /**
     * project_name="ipassbook"
     */
    public static final String PROJECT_NAME = "flow";

    /**
     * package_root="com.wateray.ipassbook"
     */
    public static final String PACKAGE_ROOT = "com.fafasoft.flow";

    /**
     * protocol_file="file:"
     */
    public static final String PROTOCOL_FILE = "file:";

    /**
     * package_resourse="com.fafa.soft.flow.resource"
     */
    public static final String PACKAGE_RESOURSE = "com.fafasoft.flow.resource";

    /**
     * package_image="com.fafa.soft.flow.resource.image"
     */
    public final static String PACKAGE_IMAGE = "com/fafasoft/flow/resource/image/";


    // database.properties

    public final static String DATA_PATH = "config/data/yygl";
    public final static String CONFIG_PATH = "config/data/";
    public final static String DB_PATH = "jdbc:h2:file:config/data/yygl";
    public final static String SYNDB_PATH = "jdbc:h2:file:config/data/syn;TRACE_LEVEL_FILE=0";
    public static final  String ICON_DIR = "com/fafasoft/flow/resource/image/";
    public static final String APP_ICON = ICON_DIR + "yygl.png";
    public final static String SKIN = "skin";
    public final static String IS_SETADMIN_PASS = "ISSETADMINPASSWORD";
    public final static String SELLPOLICY = "policy";
    public final static String MYDZJ = "mydzj";//每月店租金
    public final static String MYDF = "mydf";//每月电费
    public final static String MYSJ = "gdzc_mysj";//每月税金
    public final static String MYGLF = "gdzc_myglf";//每月管理费
    public final static String MYSFF = "gdzc_mysf";//每月水费
    public final static String MYGZ = "gdzc_mygz";//每月工资
    public final static String MYQT = "gdzc_myqt";//每月其他支出
    public final static String MYGDZCZH = "gdzc_ZH";//每月固定支出总和
    public final static String TYPE_HW = "HWLX";//货物类型
    public final static String TYPE_ZC = "ZCLX";//支出类型
    public final static String TYPE_SR = "SRLX";//支出类型


    public final static String FLOW_TYPE_SELL = "sell";//流水销售
    public final static String FLOW_TYPE_PLSELL = "plsell";//流水销售
    public final static String FLOW_TYPE_TH = "tuihuo";//退货流水

    public final static String STCOK_TYPE_JINHUO = "JINHUO";//流水销售
    public final static String STCOK_TYPE_CAIGOUTH = "caigouth";//退货流水
    public final static String STCOK_TYPE_GUKETH = "guketuihuo";//退货流水
    public final static String USER_TYPE_M = "Manage";//用户类型管理
    public final static String USER_TYPE_F = "Forbid";//用户禁止，未删除
    public final static String ADMIN = "10000";// 管理员ID
    public final static String ADMIN_NAMEX = "老板(管理员)";//管理员名程
    public final static String INCOME = "income";//日常收入
    public final static String EXPENSES = "expenses";//日常支出
    public final static String MESSAGE_V = "http://www.88sys.com/update/ffflow/v.pr";
    public final static String UPDATE_URL = "http://www.88sys.com/update/ffflow/update.exe";
    public final static String MESSAGE_VERSION = "MESSAGEVERSION";
    //suggestCheckBox
    public final static String VIEW_SET_SUGGEST = "VIEW_SET_SUGGEST";
    public final static String VIEW_SHOP_NAME = "VIEW_SHOP_NAME";
    public final static String FLOW_INPUT_WAY = "FLOW_INPUT_WAY";//每天流水录入方式
    public final static String FLOW_INPUT_WAY_JINQUE = "FLOW_INPUT_JINQUe";//每天流水录入方式
    public final static String FLOW_INPUT_WAY_PL = "FLOW_INPUT_PiLiang";//每天流水录入方式
    public final static String MAIN_IFRAME_MAX = "MAIN_IFRAME_MAX";
    public final static String VIEW_FLOWINFO_SELHELPER = "VIEW_FLOWINFO_SELHELPER";
    //new String[] {"应收(客户欠我款)", "预收(我收客户款)", "应付(我欠客户款)", "预付(我付客户款)"})

    public final static String CUSTOMER_ACCOUNT_YINGS = "YINGSHOU";
    public final static String CUSTOMER_ACCOUNT_YUS = "YUSHOU";
    public final static String CUSTOMER_ACCOUNT_YINGF = "YINGFU";
    public final static String CUSTOMER_ACCOUNT_YUF = "YUFU";
    public final static String CUSTOMER_ACCOUNT= "CUSTOMER";
    public final static String JIFEN_BILV= "jifenbilv";
    public final static String SUPPLIERS_ACCOUNT= "Suppliers";
    public final static String ACCOUNT_STATUS_UN= "Untreated";
    public final static String ACCOUNT_STATUS_PR= "Processed";
 
    public final static String PRINT_MODE= "PRINTMODE";
    public final static String PRINT_MODE_AUTO= "PRINTMODEAUTO";
    public final static String PRINT_MODE_YESNO= "PRINTMODEYESNO";
    public final static String PRINT_MODE_NO= "PRINTMODENO";
    
    public final static String PRINT_NAME= "PRINTNAME";
    public final static String PRINT_TITLE= "PRINTTITLE";
    public final static String PRINT_SHEET= "PRINTSHEET";
    public final static String PRINT_ADDRESS= "address";
    public final static String PRINT_phone= "phone";
    
    public final static String SUPPLIERS= "供应商";
    public final static String CUSTOMERS= "客户";
    
    public final static String[] RIGHT = new String[]{
    		"true", "true", "true", "true", "true",
            "true", "true", "true", "true", "true",
            "true", "true", "true", "true", "true",
            "true", "true", "true", "true"};

    public static String getSlash() {
        return "/";
    }
}
   