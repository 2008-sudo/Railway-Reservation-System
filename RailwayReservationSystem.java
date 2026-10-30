import java.util.*;

class Train {
    int trainNo, ac = 3, sleeper = 3, general = 3;
    String name, source, destination;

    Train(int no, String name, String source, String destination) {
        trainNo = no;
        this.name = name;
        this.source = source;
        this.destination = destination;
    }

    void display() {
        System.out.println(trainNo + " " + name + " "
                + source + " to " + destination);
    }

    int seats(String type) {
        if (type.equalsIgnoreCase("AC")) return ac;
        if (type.equalsIgnoreCase("Sleeper")) return sleeper;
        if (type.equalsIgnoreCase("General")) return general;
        return -1;
    }

    void update(String type, int n) {
        if (type.equalsIgnoreCase("AC")) ac += n;
        else if (type.equalsIgnoreCase("Sleeper")) sleeper += n;
        else if (type.equalsIgnoreCase("General")) general += n;
    }
}

class Passenger {
    String name;
    int age;

    Passenger(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Ticket {
    int id;
    Passenger p;
    String type, status;

    Ticket(int id, Passenger p, String type, String status) {
        this.id = id;
        this.p = p;
        this.type = type;
        this.status = status;
    }

    void display() {
        System.out.println(id + " " + p.name + " " + p.age
                + " " + type + " " + status);
    }
}

class BookingService {
    Train train;
    ArrayList<Ticket> list = new ArrayList<>();
    int id = 1001;

    BookingService(Train train) {
        this.train = train;
    }

    void book(String name, int age, String type) {
        int s = train.seats(type);

        if (age <= 0 || s == -1) {
            System.out.println("Invalid details");
            return;
        }

        String status = s > 0 ? "Confirmed" : "Waiting List";
        if (s > 0) train.update(type, -1);

        Ticket t = new Ticket(id++, new Passenger(name, age), type, status);
        list.add(t);
        t.display();
    }

    void cancel(int num) {
        for (Ticket t : list) {
            if (t.id == num && !t.status.equals("Cancelled")) {
                if (t.status.equals("Confirmed"))
                    train.update(t.type, 1);

                t.status = "Cancelled";
                System.out.println("Ticket cancelled");
                return;
            }
        }
        System.out.println("Ticket not found");
    }

    void history() {
        for (Ticket t : list) t.display();
    }
}

public class RailwayReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Train train = new Train(101, "Bangalore Express",
                "Bangalore", "Chennai");
        BookingService service = new BookingService(train);

        int ch = 0;

        do {
            System.out.println("\n1.Train 2.Seats 3.Book 4.Cancel 5.History 6.Exit");

            try {
                ch = sc.nextInt();

                switch (ch) {
                    case 1:
                        train.display();
                        break;

                    case 2:
                        System.out.println("AC: " + train.ac
                                + ", Sleeper: " + train.sleeper
                                + ", General: " + train.general);
                        break;

                    case 3:
                        sc.nextLine();
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Class (AC/Sleeper/General): ");
                        String type = sc.nextLine();
                        service.book(name, age, type);
                        break;

                    case 4:
                        System.out.print("Ticket number: ");
                        service.cancel(sc.nextInt());
                        break;

                    case 5:
                        service.history();
                        break;

                    case 6:
                        System.out.println("Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                sc.nextLine();
                ch = 0;
            }
        } while (ch != 6);

        sc.close();
    }
}
