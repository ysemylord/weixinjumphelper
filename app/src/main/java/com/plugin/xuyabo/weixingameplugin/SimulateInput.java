package com.plugin.xuyabo.weixingameplugin;


/**
 * Created by xuyabo on 2017/12/31.
 * 模拟点击效果
 */

public class SimulateInput {

    /**
     *
     * root用户使用该方法才生效果
     * @param cmds
     * @throws Exception
     */
    public static void doCmds(String cmds) throws Exception {
        //Process process = Runtime.getRuntime().exec("su");
        Process p = Runtime.getRuntime().exec(cmds);

    }
}
