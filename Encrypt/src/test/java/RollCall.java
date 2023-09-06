import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RollCall {
    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println("《=======中心组织学习随机抽取发言人小程序启动=======》");
        System.out.println();
        String[] name = new String[]{
                "游志彬",
                "傅永源",
                "林晓伟",
                "张志扬",
                "唐思远",
                "林森",
                "黄贺",
                "常佳旭",
                "武垠锡",
                "周浩楠",
                "李在甲",
                "教富城",
                "辛凤雪",
                "陈冶",
                "杨松",
                "孙闻钖",
                "李紫阳",
                "孙浩然",
                "王聪",
                "孙汇濛",
                "陈蕊",
                "肖鹏",
                "王亚楠",
                "赵磊锋",
                "张宇",
                "孙婷婷",
                "刘皓",
                "金悦",
                "贾国臣",
                "吴晓明",
                "黄庆强",
                "杜松",
                "连志文",
                "何玉保",
                "吴亚林",
                "齐冠权",
                "步野",
                "张政昊",
                "孙文武",
                "孙嘉恒",
                "吕鹏程",
                "张思杰",
                "康雪",
                "孙可心",
                "李研",
                "李金玲",
                "赵彦婷"
        };
        System.out.println("本次随机共计参加：" + name.length + "人");
        List<Integer> selected = new ArrayList<>();
        int total = 0;
        while (total < 10) {
            Random random = new Random();
            int winner = random.nextInt(name.length);
            if (!selected.contains(winner)) {
                selected.add(winner);
                total = total + 1;
                Thread.sleep(1000);
                System.out.print("有请第 " + total + " 位发言人，第 " + winner + " 号");
                Thread.sleep(3000);
                System.out.println("，小伙伴：" + name[winner]);
            }
        }
        System.out.println();
        System.out.println("《=======中心组织学习随机抽取发言人小程序结束=======》");
        System.out.println();
    }
}
