package ai.openfabric.api.service;

import ai.openfabric.api.exception.WorkNotFoundException;
import ai.openfabric.api.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkerService {
    void startWorker(String id) throws Exception;
    String stopWorker(String id) throws WorkNotFoundException;
    Worker getWorkerInfo(String id) throws WorkNotFoundException;
    // I make this void because I have not implement it
    void getWorkerStatistics(String id) throws WorkNotFoundException;
    Page<Worker> getWorkers(Pageable pageable);
    Worker       createWorker(Worker worker);

}
