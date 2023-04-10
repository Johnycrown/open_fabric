package ai.openfabric.api.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfig {
    @Bean
    public DockerClient dockerClient() {
       DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://localhost:2375").withDockerTlsVerify(false).build();
//
//        DockerClient client = DockerClientBuilder.getInstance(config).build();
//        return client;
        //DefaultDockerClientConfig.Builder config = DefaultDockerClientConfig.createDefaultConfigBuilder();
//        DockerClient dockerClient = DockerClientBuilder
//                .getInstance(config)
//                .build();
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        return dockerClient;
    }
}
