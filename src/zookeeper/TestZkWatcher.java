package zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 测试持续监听
 * @author leisi-NO1
 *
 */
public class TestZkWatcher {

	 static ZooKeeper zk=null;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		zk=new ZooKeeper("172.16.238.101:2181", 2000, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println("节点："+event.getPath()+"发生了"+event.getType());
				try {
					//zk.exists("/java", true); 两个监听器监听的范围不一样
					zk.getChildren("/java", true);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		zk.create("/java", "sdsds".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//创建监听 此监听只监听一次就失效，要想持续监听需要在监听回调方法process方法里再次创建监听
		zk.getChildren("/java", true);
		Thread.sleep(Long.MAX_VALUE);
	}

}
