package com.yl.myimageupdata.golbal;


import com.yl.myimageupdata.utils.PrefUtils;
import com.yl.myimageupdata.utils.Utils;

/**
 * Description:
 * Date       : 2017/1/25 15:47
 */
public class GlobalConstants {
    public  String path = PrefUtils.getString(Utils.getContext(), "path", "");
}