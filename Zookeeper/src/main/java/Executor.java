// forked from https://zookeeper.apache.org/doc/r3.5.7/javaExample.html

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener
{
    private String znode;
    private String exec[];
    private DataMonitor dm;
    private ZooKeeper zk;
    private Process process;

    public Executor(String hostPort, String znode, String exec[]) throws IOException {
        this.znode = znode;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 3000, this);
        dm = new DataMonitor(zk, znode, this);
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: Executor hostPort znode program [args ...]");
            System.exit(2);
        }
        String hostPort = args[0];
        String znode = args[1];
        String exec[] = new String[args.length - 2];
        System.arraycopy(args, 2, exec, 0, exec.length);
        Executor executor = null;
        try {
            executor = new Executor(hostPort, znode, exec);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                br.readLine();
                executor.printTree();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void process(WatchedEvent event) {
        dm.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void exists(boolean exists) {
        if (exists) {
            this.startProcess();
        } else {
            this.stopProcess();
        }
    }

    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    private void startProcess() {
        if (process == null) {
            System.out.println("Starting child");
            try {
                process = Runtime.getRuntime().exec(exec[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopProcess() {
        if (process != null) {
            System.out.println("Stopping child");
            process.destroy();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            process = null;
        }
    }

    private void printTree() {
        System.out.println("Tree structure:");
        printTreeInner(znode, 0);
    }

    private void printTreeInner(String path, int indent) {
        try {
            List<String> children = zk.getChildren(path, false);
            System.out.println("\t".repeat(indent) + "* " + path);
            for (String child : children) {
                printTreeInner(path + "/" + child, indent+1);
            }
        } catch (KeeperException ignored) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
