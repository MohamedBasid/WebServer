package loadbalancer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Apps {
    private static List<Integer> SERVERS = new ArrayList<>();
    private static AtomicInteger count = new AtomicInteger(0);
    static {
        SERVERS.add(9090);
        SERVERS.add(9091);
    }

    public static int getHost(){
        int server = SERVERS.get(count.get()%SERVERS.size());
        count.incrementAndGet();
        return server;
    }
}
