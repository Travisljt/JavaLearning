import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NameList {
   private List<String> nameList;
   private int lastID = 20;

   public NameList(){
       nameList = new ArrayList<>();
       nameList.add("公公-赖志平");
       nameList.add("阿婆-廖");
       nameList.add("满公-赖");
       nameList.add("满婆-");
       nameList.add("爸爸-赖新文");
       nameList.add("妈妈-黄思静");
       nameList.add("大叔-赖远凌");
       nameList.add("婶婶-黄惠丽");
       nameList.add("二叔-赖思山");
       nameList.add("二婶-叶琼");
       nameList.add("满叔-赖荣寿");
       nameList.add("满婶-");
       nameList.add("我-赖峻霆");
       nameList.add("赖勇超");
       nameList.add("赖政玥");
       nameList.add("赖洁媛");
       nameList.add("赖思宇");
       nameList.add("赖峻燊");
       nameList.add("赖美璐");
       nameList.add("赖伟钒");
   }

   public String getSpecNameByID(int id){
       return nameList.get(id);
   }

   public int getListSize(){
       return nameList.size();
   }

   public void addNameInList(String name){
       nameList.add(lastID,name);
   }

   public void printList(){
       System.out.println("一共有"+nameList.size()+"个名字");
       for(String s : nameList){
           System.out.println(s);
       }
   }

   public void removeNameByName(String name){
       nameList.remove(name);
   }
}
