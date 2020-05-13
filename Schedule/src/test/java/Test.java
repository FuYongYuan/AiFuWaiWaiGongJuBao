import schedule.*;
import schedule.service.JobService;

import java.time.Duration;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        JobService jobService = new JobService();
        jobService.addJob(job);
        jobService.start();
    }

    static Job job = Job.with(() -> {
        System.out.println(LocalDateTime.now());
    }).when(
         Minute.at(0,59).with(
                 Hour.at(11)
         )
    );
}
