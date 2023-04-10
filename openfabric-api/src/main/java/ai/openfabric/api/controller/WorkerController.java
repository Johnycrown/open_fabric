package ai.openfabric.api.controller;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.repository.WorkerRepository;
import ai.openfabric.api.service.WorkerService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${node.api.path}/worker")
public class WorkerController {
    private final WorkerService workerService;
    private final WorkerRepository workerRepository;

    public WorkerController(WorkerService workerService, WorkerRepository workerRepository) {
        this.workerService = workerService;
        this.workerRepository = workerRepository;
    }


    @PostMapping(path = "/hello")
    public @ResponseBody String hello(@RequestBody String name) {
        return "Hello!" + name;
    }

    @PostMapping(path = "/start")
    public ResponseEntity<?>  startContainer(@RequestBody String id) throws Exception {
        workerService.startWorker(id);
        return ResponseEntity.ok().body("worker has been succesfully started");

    }
    @GetMapping(path ="/list-of-workers")
    public ResponseEntity<?>  fetchImage(Pageable pageable){
        return ResponseEntity.ok().body(workerService.getWorkers(pageable));
    }
    @PostMapping(path = "/create")
    public ResponseEntity<?> createWorker(@RequestBody Worker worker){
        return ResponseEntity.ok().body(workerService.createWorker(worker));
    }


}
