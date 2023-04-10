package ai.openfabric.api.model;


import com.github.dockerjava.api.model.Container;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity()
@Data
public class Worker extends Datable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    @GenericGenerator(name = "of-uuid", strategy = "ai.openfabric.api.model.IDGenerator")
    @Getter
    @Setter
    public String id;
    public String name;
    private String ipAddress;
    private Integer port;
    private String status;
    private String containerId;
    private String image;
    private String imageId;
    private String state;
    private String command;
    public static Worker create(Container container) {
        Worker worker = new Worker();
        worker.setContainerId(container.getId());
        worker.setImage(container.getImage());
        worker.setImageId(container.getImageId());
        worker.setState(container.getState());
        worker.setStatus(container.getStatus());
        worker.setCommand(container.getCommand());
        worker.setName(container.getImage());

        return worker;
    }

}
