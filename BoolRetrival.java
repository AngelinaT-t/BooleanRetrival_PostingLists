
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BoolRetrival {
	
	class FreqAndId{
		private int freq;
		private List<Integer> postingList;
		
		public FreqAndId() {
			// TODO Auto-generated constructor stub
			freq = 0;
			postingList = new ArrayList<Integer>();
		}
		private void addFreq() {
			freq ++;
		}
		private void adddocId(int id) {
			postingList.add(id);
			postingList.sort(null);
		}
	}
	
	public Map<String,FreqAndId> dir; 
	static Formatter formatter = new Formatter(System.out);
	
	public BoolRetrival() {
		// TODO Auto-generated constructor stub
		dir = new TreeMap<String,FreqAndId>(new Comparator<String>(){
             public int compare(String o1, String o2) {
                 //如果有空值，直接返回0
            	 	o1 = o1.toLowerCase();//将term的值转成小写
            	 	o2 = o2.toLowerCase();
                 if (o1 == null || o2 == null)
                     return 0; 
                return String.valueOf(o1).compareTo(String.valueOf(o2));//然后进行比较
             }
		 });
	}
	
	public void Insert(String term,int docID) {//term为单词(索引)，docID为所在文档
		if(!dir.containsKey(term)) {//如果字典里没有这个单词，则直接加入
			FreqAndId fId = new FreqAndId();//建立包含频率freq和postingList的对象
			fId.addFreq();//freq设置为1
			fId.adddocId(docID);//postingList加入docID
			dir.put(term, fId);//和term一起加入到字典中，按照升序插入
		}
		else {//如果字典里包含了这个term
			FreqAndId fId = dir.get(term);//获取这个term所对应的object信息
			if(!fId.postingList.contains(docID)) {//是否已经包含这个docID,不包含则进入
				fId.addFreq();//freq++
				fId.adddocId(docID);//postingList加入新的docID
				dir.put(term, fId);//新的信息插入时为替代原来的信息(map Key值唯一)
			}
		}		
	}
	
	public void Intersection(String t1,String t2) {
		int i,j;
		FreqAndId fid1 = dir.get(t1);//根据term值获取map中对应的value值，返回为一个Object
		FreqAndId fid2 = dir.get(t2);//
		
		List<Integer> l1 = fid1.freq > fid2.freq //较长的List命名为L1
				? fid1.postingList : fid2.postingList;
		List<Integer> l2 = fid1.freq < fid2.freq //较短的List命名为L2
				? fid1.postingList : fid2.postingList;
		List<Integer> list = new ArrayList<Integer>();//保存合并的结果
		
		int len1 = l1.size(); //object的freq属性代表着postingList的长度
		int len2 = l2.size();
		
		for(i = 0,j = 0; i<len1 && j<len2;) {//直到遍历完其中一个postingList
			
			while(l1.get(i) < l2.get(j)) {//找到l1第一个大于等于l2当前元素的位置
				i++;
			}
			int id1 = l1.get(i);
			int id2 = l2.get(j);
			//System.out.println("i = "+l1.get(i)+"  j = "+l2.get(j));
			if( id1 == id2 ) {
				list.add(id2);//相等则加入合并列表
				i++;					//L1，L2均取下一个元素
				j++;
				//System.out.println("i = "+l1.get(i)+"  j = "+l2.get(j));
			}
			else if( id1 > id2 ){
				j++;//大于则找到L2下一个比L1当前元素大于等于的元素
			}
		}
		
		System.out.print("Intersection: ");
		for (int tmp : list) {	//输出结果
		    System.out.print("-->"+tmp);   
        }
	}
	
	public void print() {
		formatter.format("%-10s %-10s %-10s\n", "term", "doc.freq", "posting lists");
		for (String s : dir.keySet()) {
			formatter.format("%-15s",s);
				FreqAndId fId = dir.get(s);
				formatter.format("%-10s",fId.freq);
				 for (int tmp : fId.postingList) { 
					    System.out.print("-->"); 
						formatter.format("%-1s",tmp);  
			        }
				  System.out.println(""); 
			}
	}
	
	public static void main(String[] args) {
		
		BoolRetrival br = new BoolRetrival();
			
		br.Insert("I",1);
		br.Insert("did",1);
		br.Insert("enact",1);
		br.Insert("julius",1);
		br.Insert("caesar",1);
		br.Insert("I",1);
		br.Insert("was",1);
		br.Insert("killed",1);
		br.Insert("i'",1);
		br.Insert("the",1);
		br.Insert("capital",1);
		br.Insert("brutus",1);
		br.Insert("killed",1);
		br.Insert("me",1);
		br.Insert("so",2);
		br.Insert("let",2);
		br.Insert("it",2);
		br.Insert("be",2);
		br.Insert("with",2);
		br.Insert("caesar",2);
		br.Insert("the",2);
		br.Insert("noble",2);
		br.Insert("brutus",2);
		br.Insert("hath",2);
		br.Insert("told",2);
		br.Insert("you",2);
		br.Insert("caesar",2);
		br.Insert("was",2);
		br.Insert("ambitious",2);
		
		br.print();
		
		br.Insert("Brutus",1);
		br.Insert("Brutus",2);
		br.Insert("Brutus",4);
		br.Insert("Brutus",11);
		br.Insert("Brutus",31);
		br.Insert("Brutus",45);
		br.Insert("Brutus",173);
		br.Insert("Brutus",174);

		br.Insert("Calpurnia",2);
		br.Insert("Calpurnia",31);
		br.Insert("Calpurnia",54);
		br.Insert("Calpurnia",101);
		
		//br.print();
		//br.Intersection("brutus","Calpurnia");
		
	}
}
