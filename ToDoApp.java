import java.util.ArrayList;
import java.util.Scanner;

public class ToDoApp {
    private static ArrayList <Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

 public static void main(String[] args) {
    while (true) {
        showMenu(); 
        String option = scanner.nextLine(); 

        switch (option) {
            case "1":
                addTask();
                break;
            case "2":
                showTasks();
                break;
            case "3":
                markTaskDone();
                break;
            case "4":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }
}

    private static void showMenu() {
        System.out.println("\n=== ToDo List ===");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Mark Task as Done");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addTask() {
        System.out.print("Enter task description: ");
        String desc = scanner.nextLine();
        tasks.add(new Task(desc));
        System.out.println("Task added!");
    }

    private static void showTasks() {
        System.out.println("\nYour Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i));
        }
    }

    private static void markTaskDone() {
        showTasks();
        System.out.print("Enter task number to mark as done: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markDone();
            System.out.println("Task marked as done.");
        } else {
            System.out.println("Invalid task number.");
        }
    }
}