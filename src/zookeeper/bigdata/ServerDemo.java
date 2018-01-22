package zookeeper.bigdata;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ServerDemo {

	private static final String groupNode= "/servers";
	
	/**
	 * 用于向zk集群注册本服务的节点信息
	 * @param hostName
	 * @throws Exception
	 */
	public void registerZK(String hostName) throws Exception{
		//向zookeeper中注册服务
		ZooKeeper zk = new ZooKeeper("172.16.238.101:2181", 2000, null);
		
		String path = zk.create(groupNode+"/server", hostName.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println("服务器"+hostName+"注册了一个子节点："+path);
	}
	
	public void hadle(String hostName) throws Exception{
		System.out.println("-----服务器"+hostName+"开始处理自己的业务了");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	public static void main(String[] args) throws Exception {
		// 调用registerZk方法注册信息
		ServerDemo sd=new ServerDemo();
		sd.registerZK(args[0]);
		//处理自己的业务
		sd.hadle(args[0]);
	}

}
