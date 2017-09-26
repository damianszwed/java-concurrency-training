package pl.training.concurrency.ex011;

public class App {

    public static void main(String[] args) {
        Meeting meeting = new Meeting(2);
        new Thread(meeting).start();
        meeting.add("Jan");
        meeting.add("Maria");
    }

}
