package zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class SimpleZkClient {
	
	private  ZooKeeper zkclient=null;
	
	@Before
	public void init() throws Exception{
				//connectString  zk服务器的地址
				//sessionTimeout 连接超时时间
				//watcher  监听器
				 zkclient = new ZooKeeper("172.16.238.101:2181", 2000, new Watcher() {
					/**
					 * zookeeper节点发生变化时，会通知客户端，客户端会调用process方法，将接收到的event传给process方法
					 */
					@Override
					public void process(WatchedEvent event) {
						// TODO Auto-generated method stub
						System.out.println("节点"+event.getPath()+"发生了事件："+event.getType());
					}
				});
	}
	
	@Test
	public void CreateNode() throws Exception, KeeperException, InterruptedException{
		//创建节点
		//PERSISTENT永久节点
		//EPHEMERAL临时节点
		String path = zkclient.create("/itheima", "你好".getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		String path1 = zkclient.create("/itheima/cloud", "70".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("创建了一个永久节点："+path+"  创建了一个临时节点"+path1);
		Thread.sleep(Long.MAX_VALUE);
	}

/**
 * 获取节点内容
 * @throws Exception
 */
	@Test
	public void getNode() throws Exception{
		//获取节点内容
		byte[] data = zkclient.getData("/itheima", true, null);
		System.out.println(new String(data,"utf-8"));
		
	}
	
	/**
	 * 修改节点数据
	 * @throws Exception
	 */
	@Test
	public void editNode() throws Exception{
		//修改节点数据(-1指修改数据时匹配所有版本)
		zkclient.setData("/itheima", "哈哈你好啊".getBytes("utf-8"), -1);
		//查看节点数据
		byte[] bs = zkclient.getData("/itheima", true, null);
		System.out.println(new String(bs,"utf-8"));
	}
	
	/**
	 * 删除节点
	 * @param args
	 * @throws Exception 
	 */
	@Test
	public void delete() throws Exception{
		Stat exists = zkclient.exists("/itheima", true);
		System.out.println(exists!=null ? "存在":"不存在");
		//删除节点
		zkclient.delete("/itheima", -1);
		Stat exists2 = zkclient.exists("/itheima", true);
		System.out.println(exists2==null ? "删除了" : "删除失败了");
	}
	
	public static void main(String[] args) throws  Exception {
		//connectString  zk服务器的地址
		//sessionTimeout 连接超时时间
		//watcher  监听器
		ZooKeeper zkclient = new ZooKeeper("172.16.238.101:2181", 2000, new Watcher() {
			/**
			 * zookeeper节点发生变化时，会通知客户端，客户端会调用process方法，将接收到的event传给process方法
			 */
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println("节点"+event.getPath()+"发生了事件："+event.getType());
			}
		});
		
		//获取“/”目录下的子节点列表，并对“/”注册监听器
		List<String> children = zkclient.getChildren("/", true);
		for (String s : children) {
			System.out.println(s);
		}
	}
}
