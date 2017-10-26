package com.web.actions.web.kcode;

import com.web.actions.weixin.common.BaseAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by wh on 2017/7/4.
 */
@Controller
@RequestMapping("/webkcode")
public class KcodeAction extends BaseAction implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(KcodeAction.class);

    @RequestMapping(value = "/k2p")
    public String k2p(HttpServletRequest request) {
        return "web/help/kcodeActivationk2P";
    }

    @RequestMapping(value = "/k2")
    public String k2(HttpServletRequest request) {
        return "web/help/kcodeActivationK2";
    }

    @RequestMapping(value = "/k3")
    public String k3(HttpServletRequest request) {
        return "web/help/kcodeActivationK3";
    }

    @RequestMapping(value = "/c1330")
    public String c1330(HttpServletRequest request) {
        return "web/help/kcodeActivationC1330";
    }

    @RequestMapping(value = "/s7")
    public String s7(HttpServletRequest request) {
        return "web/help/kcodeActivationS7";
    }

    @RequestMapping(value = "/other")
    public String other(HttpServletRequest request) {
        return "web/help/kcodeActivationOther";
    }

}
