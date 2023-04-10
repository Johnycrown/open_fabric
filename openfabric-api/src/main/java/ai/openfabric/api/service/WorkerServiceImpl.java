package ai.openfabric.api.service;

import ai.openfabric.api.exception.WorkNotFoundException;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.repository.WorkerRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.StopContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.yahoo.elide.core.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;
    private final DockerClient dockerClient;




    @Override
    public void startWorker(String id) throws Exception {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new WorkNotFoundException("Worker  with id " + id + " not found"));
        if (worker.getStatus().equalsIgnoreCase("Running")) throw new BadRequestException("Worker is already running");
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(worker.getName())
                .withName(worker.getName())
                .withExposedPorts(ExposedPort.tcp(worker.getPort()))
                .withHostConfig(new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(worker.getPort()), new ExposedPort(worker.getPort()))));
        try {
            String containerId = createContainerCmd.exec().getId();
            dockerClient.startContainerCmd(containerId).exec();
            worker.setStatus("Running");
            worker.setId(containerId);
            workerRepository.save(worker);
        }catch(Exception ex){
            ex.printStackTrace();
        }




    }

    @Override
    public String stopWorker(String id) throws WorkNotFoundException {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new WorkNotFoundException("Worker  with id " + id + " not found"));
        try {
            StopContainerCmd stopContainerCmd = dockerClient.stopContainerCmd(worker.getContainerId());
            stopContainerCmd.exec();
        } catch (Exception e) {
            e.printStackTrace();
        }
        worker.setStatus("Stopped");
        workerRepository.save(worker);

        return "Worker has been stopped successfully.";
    }

    @Override
    public Worker getWorkerInfo(String id) throws WorkNotFoundException {
        Worker worker = workerRepository.findById(id).orElseThrow(() -> new WorkNotFoundException("Worker  with id " + id + " not found"));
        InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(worker.getId()).exec();
        worker.setStatus(inspectContainerResponse.getState().getStatus());
        return worker;
    }

    @Override
    public void getWorkerStatistics(String id) throws WorkNotFoundException {

        // I am unable to implement this because I could not access Stats class and  ContainerStatsCallback from java docker api integration provided

    }
    public Page<Worker> getWorkers(Pageable pageable) {
        return workerRepository.findAll(pageable);
    }




}
