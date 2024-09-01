public class Timer extends Thread {
    private ClientController clientController;
    private boolean isOn;

    public Timer(ClientController clientController) {
        this.clientController = clientController;
        isOn = true;
    }

    @Override
    public void run() {
        while (isOn) {
            try {
                sleep(1000);
                if (isOn) {
                    clientController.tickTimer();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTimer() {
        isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }
}
