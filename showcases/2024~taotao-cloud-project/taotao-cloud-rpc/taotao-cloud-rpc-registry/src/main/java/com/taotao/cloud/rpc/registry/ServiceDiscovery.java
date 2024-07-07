package com.taotao.cloud.rpc.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * client用于发现server节点的变化 并且实现负载均衡<br>
 *
 * @author shuigedeng
 * @version v1.0.0
 */
public class ServiceDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private volatile List<String> dataList = new ArrayList<>();
    private String registryAddress;

//    public ServiceDiscovery(String registryAddress) {
//        this.registryAddress = registryAddress;
//        ZooKeeper zk = connectZooKeeper();
//        if (null != zk) {
//            watchNode(zk);
//        }
//    }

//    /**
//     * 连接zk
//     *
//     * @return org.apache.zookeeper.ZooKeeper
//     * @author shuigedeng
//     * @date 2020/2/27 14:01
//     */
//    private ZooKeeper connectZooKeeper() {
//        ZooKeeper zk = null;
//        try {
//            zk = new ZooKeeper(registryAddress, Constants.ZK_SESSION_TIMOUT, event -> {
//                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
//                    countDownLatch.countDown();
//                }
//            });
//            countDownLatch.await();
//        } catch (Exception e) {
//            logger.error("zk连接错误");
//        }
//        return zk;
//    }

    /**
     * 监听节点
     *
     * @param zk zk
     * @return void
     * @author shuigedeng
     * @date 2024.06
     */
//    private void watchNode(final ZooKeeper zk) {
//        try {
//            List<String> nodeList = zk.getChildren(Constants.ZK_REGISTRY_PATH, watchedEvent -> {
//                if (Watcher.Event.EventType.NodeChildrenChanged == watchedEvent.getType()) {
//                    watchNode(zk);
//                }
//            });
//            List<String> dataList = new ArrayList<>();
//            if (null != nodeList) {
//                for (String node : nodeList) {
//                    byte[] data = zk.getData(Constants.ZK_REGISTRY_PATH + "/" + node, false, null);
//                    dataList.add(new String(data));
//                }
//            }
//            this.dataList = dataList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("zk监听数据失败");
//        }
//    }

    /**
     * 服务发现
     *
     * @return java.lang.String
     * @author shuigedeng
     * @date 2024.06
     */
    public String discover() {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            //负载均衡策略  使用随机方式获取服务
            data = size > 1 ? dataList.get(ThreadLocalRandom.current().nextInt(size)) : dataList.get(0);
        }
        return data;
    }

}
