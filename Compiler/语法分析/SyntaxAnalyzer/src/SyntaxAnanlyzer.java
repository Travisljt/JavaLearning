import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class SyntaxAnanlyzer {

	private String temp1;
	private LinkedList<Character> list1 = new LinkedList<Character>();
	private Stack<Character> stack = new Stack<Character>();
	private Stack<Integer> stack2 = new Stack<>();
	private BufferedWriter output;
	private Production production = new Production();
	private String actions = "";
	private Vector<Integer> sequenceOfP=new Vector<Integer>();

	private String[][] table = { { "", "", "S4", "", "S5", "", "1", "2", "3" }, // 0
			{ "S6", "", "", "", "", "accept", "", "", "" }, // 1
			{ "r2", "S7", "", "r2", "", "r2", "", "", "" }, // 2
			{ "r4", "r4", "", "r4", "", "r4", "", "", "" },// 3
			{ "", "", "S4", "", "S5", "", "8", "2", "3" },// 4
			{ "r6", "r6", "", "r6", "", "r6", "", "", "" },// 5
			{ "", "", "S4", "", "S5", "", "", "9", "3" },// 6
			{ "", "", "S4", "", "S5", "", "", "", "10" },// 7
			{ "S6", "", "", "S11", "", "", "", "", "" },// 8
			{ "r1", "S7", "", "r1", "", "r1", "", "", "" },// 9
			{ "r3", "r3", "", "r3", "", "r3", "", "", "" },// 10
			{ "r5", "r5", "", "r5", "", "r5", "", "", "" },// 11
	};

	public SyntaxAnanlyzer() {
		try {
			output = new BufferedWriter(new FileWriter("output.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getText() {
		char a[];
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader("input.txt"));
			String lString;
			while ((lString = bufferedReader.readLine()) != null) {
				stack.clear();
				list1.clear();
				stack2.clear();
				sequenceOfP.clear();
				temp1 = lString.trim();
				temp1.replaceAll("\\s+", "");// ȥ��һ�����ϵĿհ׷�����һ���հ״���
				a = temp1.toCharArray();
				for (char _char : a) {
					list1.offer(_char);
				}
				list1.offerLast('$');
				stack.push('$');
				stack2.push(0);
				output.write("state\t symbol\t input\t action");
				output.newLine();
				boolean b = analysis();
				if (b){
					output.write("�����������趨��SLR(1)�ķ�");
					output.newLine();
					output.write("����ʽ˳�����£�(bottom-up)");
					output.newLine();
					for(int i=0;i<sequenceOfP.size();i++){
						Integer n=sequenceOfP.get(i);
						output.write(production.getProduction(n.intValue()));
						output.newLine();
					}
				}
				else
					output.write("������벻�����趨��SLR(1)�ķ�");
				output.write("-----------------------------------------");
				output.newLine();
			}
		} catch (Exception e) {
		} finally { // �ر���Դ
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (output != null)
				try {
					output.close();
				} catch (Exception e2) {
				}
		}
	}

	public int getOrder(char c) {        //��ȡ���ţ��ս������ս�����ı�ţ����ű��еĺ���˳��
		if (c == '+')
			return 0;
		else if (c == '*')
			return 1;
		else if (c == '(')
			return 2;
		else if (c == ')')
			return 3;
		else if (c == 'i')
			return 4;
		else if (c == '$')
			return 5;
		else if (c == 'E')
			return 6;
		else if (c == 'T')
			return 7;
		else if(c=='F')
			return 8;
		else 
			return -1;
	}

	public void display() {          //��SymbolStack��StateStack��input��������ַ�����ʾ������ļ�
		String symbols = "";
		String states = "";
		String input = "";

		Object[] symbolObjects = stack.toArray();
		for (int i = 0; i < symbolObjects.length; i++) {
			symbols += symbolObjects[i].toString();

		}

		Object[] stateObjects = stack2.toArray();
		for (int i = 0; i < stack2.size(); i++) {
			states += stateObjects[i].toString();
		}

		Object[] inputObjects = list1.toArray();
		for (int i = 0; i < list1.size(); i++) {
			input += inputObjects[i].toString();
		}
		try {
			output.write(states + "\t" + symbols + "\t" + input + "\t"
					+ actions);
			output.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean analysis() {
		while (true) {
			actions = "";
			char c = list1.peekFirst();
			int i = getOrder(c);
			if(i==-1)                //��������ǳ��˹涨���ս���ͷ��ս������ķ��ţ�����false
				return false;
			String string2 = table[stack2.peek()][i];
			if (string2.trim().equals("".trim()))
				return false;
			else if (string2.equals("accept")) {
				actions+="accept";
				display();
				return true;
			} else if (string2.charAt(0) == 'S') { // �ƽ�
				String s = string2.substring(1); // ȡS�����״̬��
				int n = Integer.parseInt(s);
				System.out.println("Shift " + s);
				actions += "Shift to state " + s;
				display();
				list1.pollFirst(); // ��������ﵯ����һ���ַ������Ѹ��ַ���symbolStack,ͬʱ��StateStackѹ��ȡ�õ�״̬��
				stack2.push(n);
				stack.push(c);

			} else if (string2.charAt(0) == 'r') { // ��Լ
				String s = string2.substring(1); // ȡr����Ĳ���ʽ���
				int n = Integer.parseInt(s);
				System.out.println("Reduce " + s);
				actions += "Reduce by production " + s;
				display();
				sequenceOfP.addElement(n);

				int n2 = production.getNumOfP(n);// ȡ����ʽ�Ҳ����ַ�������Ӧ����SymbolStack��StateStack�е������ĸ�����
				for (int i1 = 0; i1 < n2; i1++) {
					stack.pop();
					stack2.pop();
				}

				char _char1 = production.getProduction(n).charAt(0);// ��ȡ����ʽ��ߵķ��ս����ѹ��SymbolStack
				stack.push(_char1);
				String s1 = table[stack2.peek()][getOrder(_char1)];// ����goto�ֱ��ҵ����ս���͵�ǰ״̬��Ӧ�ı�ţ�ѹ��StateStack
				if (s1.trim().equals(""))
					return false;
				else
					stack2.push(Integer.parseInt(s1));
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SyntaxAnanlyzer ananlyzer = new SyntaxAnanlyzer();
		ananlyzer.getText();
	}

}
