import java.util.Random;

public class MyThread extends Thread {
    boolean flag = true;

    public synchronized void changeToTrue() {
        flag = true;

    }

    public synchronized void changToFalse() {
        flag = false;
        notify();
    }

    public void run() {
        do {
            synchronized (this) {
                while (flag) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } while (true);
    }

    public boolean luckDraw(NameList list) {
        if (list.getListSize() < 1) {
            nameLabel.setText("表单已经空了");
            return false;
        } else {
            int key = randomSelect(list);
            String name = list.getSpecNameByID(key);
            nameLabel.setText("中奖者: " + name);
            correctRemove(list, name);
            return true;
        }

    }

    public int randomSelect(NameList list) {
        int num = list.getListSize();
        Random rand = new Random();
        return rand.nextInt(num);
    }

    public void correctRemove(NameList nameList, String target) {
        int size = nameList.getListSize();
        for (int i = size - 1; i >= 0; i--) {
            String item = nameList.getSpecNameByID(i);
            if (target.equals(item)) {
                nameList.removeNameByName(item);
            }
        }
    }
}
