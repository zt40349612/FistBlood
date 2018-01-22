package zookeeper.bigdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ClientDemo {

	 ZooKeeper zk=null;
	volatile List <String> listServer=null;
	/**
	 * 获取在线的服务列表 
	 * @throws Exception
	 */
	public void initClient() throws Exception{
		zk=new ZooKeeper("172.16.238.101:2181", 2000, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
			try {
				System.out.println(event.getType());
				if(EventType.None==event.getType()){
					
				}else{
					getData1();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		getData();
		
	}
	
	public void getData() throws Exception{
		List <String> list=new ArrayList<>();
		//获取servers下的子节点
		List<String> children = zk.getChildren("/servers", true);
		//遍历获取每个子节点所保存的信息--主机名
		for (String s : children) {
			
			byte[] data = zk.getData("/servers/"+s, false, null);
			String str = new String(data,"utf-8");
			list.add(str);
			System.out.println("data当前在线的服务节点有："+str);
		}
		listServer=list;
		System.out.println("服务器更新列表完成");
		System.out.println("getdata");
		
	}
	
	public void getData1() throws Exception{
		List <String> list=new ArrayList<>();
		//获取servers下的子节点
		List<String> children = zk.getChildren("/servers", true);
		//遍历获取每个子节点所保存的信息--主机名
		for (String s : children) {
			
			byte[] data = zk.getData("/servers/"+s, false, null);
			String str = new String(data,"utf-8");
			list.add(str);
			System.out.println("getData1当前在线的服务节点有："+str);
		}
		listServer=list;
		System.out.println("getdata1");
	}
	
	public void hadle() throws InterruptedException{
		System.out.println("客户端开始处理自己的业务······");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ClientDemo clientDemo=new ClientDemo();
		clientDemo.initClient();
		clientDemo.hadle();
	}

}
