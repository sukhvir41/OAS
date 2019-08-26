package utility;

import org.apache.commons.lang3.StringUtils;


public class testing1 {


    public static void main(String[] args) {

        var a = "src/main/webapp/WEB-INF/admin/test.jsp";
        var b = "/WEB-INF";
        var c = StringUtils.indexOf(a, b);
        System.out.println(a.substring(c));


    }


}


