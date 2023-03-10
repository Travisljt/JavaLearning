public class Main {
    static int count = 0;

    public static void main(String[] args) {
        /*
        抽奖程序：
        主体设置若干奖项，奖项获得数始终小于20
        因此可以假设一等奖两位，二等奖5位，三等奖7位；其余没获奖的人，获得安慰奖。
        ps：这种方式可以有效保证如果有其他人来，可以立刻安排更多的安慰奖红包以防万一

        第一步：先完成一个随机数抽取名字的程序，每抽取一个，便在hashMap中删除一个；
              可通过输入r来进行抽取，并打印名字
        第二步：实现满足调节，print出三等奖，然后实现第一步的程序，当满足7个之后，自动变成二等奖，依次类推
        第三步：允许中间加入其他名字，并进行抽奖
        第四步：窗体程序（需要查阅资料）


        窗体程序内容：一个名字输入文本框，一个名字显示label，一个奖品显示label，
        一个抽奖button（包含"抽奖"二字的label），一个添加名字的button
        执行逻辑：
        1. 运行抽奖button，调用抽奖function，名字label显示名字
        2. 输入text进文本框，点击添加后触发event，调用添加名字function，并在名字label中显示"已经添加名字"
        3. 当抽完三等奖（可能为7位），奖品显示label调整。需要一个cnt计数器记录调用抽奖function的次数
        4. 当所有奖项都抽完，奖品label显示抽奖结束，其他人颁发安慰奖 -> 可以额外加个功能打印剩下列表的人名
         */

//        //测试java swing
//        JFrame window = new JFrame("test");
//        Container container = window.getContentPane();
//        container.setBackground(Color.white);
//        window.setBounds(250, 180, 1000, 600);
//        window.setVisible(true);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyFrame myFrame = new MyFrame("text", 250, 180, 1000, 600);

//        NameList list = new NameList();
//        boolean flag = true;
//        boolean check = true;
//        do {
//            if(count < 7){
//                System.out.println("三等奖");
//            }else if(count >=7 && count < 12){
//                System.out.println("二等奖");
//            }else if(count >=12 && count <15){
//                System.out.println("一等奖");
//            }else{
//                System.out.println("奖品已经颁完");
//                break;
//            }
//
//            System.out.println("开始抽奖：1");
//            System.out.println("退出程序：0");
//            System.out.println("增加名字：2");
//            System.out.println("列出名字：3");
//            Scanner sc = new Scanner(System.in);
//            int key = sc.nextInt();
//            switch (key) {
//                case 3 -> {
//                    list.printList();
//                }
//                case 2 -> {
//                    System.out.println("请输入名字：");
//                    String name = sc.next();
//                    list.addNameInList(name);
//                }
//                case 1 -> {
//                    check = luckDraw(list);
//                    count++;
//                }
//                case 0 -> flag = false;
//            }
//
//        } while (flag && check);
//        System.out.println("程序退出");
    }


}