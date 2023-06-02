package java.fel.cvut.cz.reservation_management_system.configuration;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config().setInstanceName("hz")
                .addMapConfig(new MapConfig().setName("reservations")
                        .setEvictionConfig(new EvictionConfig()
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                .setEvictionPolicy(EvictionPolicy.LRU)).setTimeToLiveSeconds(120));

        return Hazelcast.newHazelcastInstance(config);
    }

}
