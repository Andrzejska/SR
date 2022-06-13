// forked from https://zookeeper.apache.org/doc/r3.5.7/javaExample.html

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class DataMonitor implements Watcher, StatCallback {
    ZooKeeper zk;
    String znode;
    boolean dead;
    DataMonitorListener listener;

    public DataMonitor(ZooKeeper zk, String znode, DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.listener = listener;
        watch();
    }

    public void watch() {
        zk.exists(znode, true, this, null);
        int countChildren = this.countChildren(znode);

        if (countChildren == -1) {
            System.out.println("Node " + znode + " does not exist");
        } else {
            System.out.println("There are " + countChildren + " node(s) countChildren " + znode);
        }
    }

    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    listener.closing(Code.SessionExpired);
                    break;
            }
        } else {
            if (path != null) {
                // Something has changed on the node, let's find out
                watch();
            }
        }
    }

    private int countChildren(String path) {
        int below = 0;
        try {
            List<String> children = zk.getChildren(path, true);
            for (String child : children) {
                below += 1 + this.countChildren(path + "/" + child);
            }
        } catch (KeeperException e) {
            return -1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return below;
    }

    public interface DataMonitorListener {
        /**
         * The existence status of the node has changed.
         */
        void exists(boolean exists);

        /**
         * The ZooKeeper session is no longer valid.
         *
         * @param rc the ZooKeeper reason code
         */
        void closing(int rc);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc) {
            case Code.Ok:
                exists = true;
                break;
            case Code.NoNode:
                exists = false;
                break;
            case Code.SessionExpired:
            case Code.NoAuth:
                dead = true;
                listener.closing(rc);
                return;
            default:
                // Retry errors
                zk.exists(znode, true, this, null);
                return;
        }

        listener.exists(exists);
    }
}
