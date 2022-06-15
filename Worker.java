import java.sql.Time;

public class Worker {

    private final OnTaskDoneListener callback;

    public Worker(OnTaskDoneListener callback){
        this.callback = callback;
    }

    public void start(){
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
                callback.onDone("Task" + i + " is done");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
