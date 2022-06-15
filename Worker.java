public class Worker {

    private final OnTaskDoneListener doneCallback;
    private final OnTaskErrorListener errorCallback;

    public Worker(OnTaskDoneListener doneCallback, OnTaskErrorListener errorCallback) {
        this.doneCallback = doneCallback;
        this.errorCallback = errorCallback;
    }

    public void start() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
                if (i == 33) {
                    errorCallback.onError("Task " + i + " is error");
                }
                doneCallback.onDone("Task " + i + " is done");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
