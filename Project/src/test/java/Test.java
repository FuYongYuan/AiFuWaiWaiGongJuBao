import page.PageBean;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        PageBean pageBean = new PageBean(1,5,7,3,2,"");
        List<String> list = new ArrayList<>();
        pageBean.setList(list);
    }
}
