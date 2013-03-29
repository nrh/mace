import junit.framework.*;
import java.util.*;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.ServerConfig;
import java.io.IOException;

import com.spotify.mace.*;

public class StorageTests extends TestCase {
  public void testStartStopZooKeeper() throws Throwable {
    Properties startupProperties = new Properties();
    startupProperties.put("dataDir","/tmp");
    startupProperties.put("clientPort",2181);

    QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
    try {
      quorumConfiguration.parseProperties(startupProperties);
    } catch(Exception e) {
      throw new RuntimeException(e);
    }

    final ZooKeeperServerMain zooKeeperServer = new ZooKeeperServerMain();
    final ServerConfig configuration = new ServerConfig();
    configuration.readFrom(quorumConfiguration);

    new Thread() {
      public void run() {
        try {
          zooKeeperServer.runFromConfig(configuration);
        } catch (IOException e) {
          System.out.println(e);
        }
      }
    }.start();

    Thread.sleep(500);
//    try {
//      zooKeeperServer.shutdown();
//    } catch (IOException e) {
//      System.out.println(e);
 //   }
  }
}


