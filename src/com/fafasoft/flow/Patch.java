package com.fafasoft.flow;

import com.fafasoft.flow.dao.DAOContentFactriy;


public class Patch {
    public static  void pachExexcute(float  oldversion,float newversion){
    	DAOContentFactriy.getPatchDAO().patch(oldversion,newversion);
    }
}
