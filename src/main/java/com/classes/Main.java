package com.classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     *
     * @author gsantheep
     *
     */

    // declarization & initialization of cabin & circular class
    static Cabin[] cabins = new Cabin[2];
    static CircularQueue queue = new CircularQueue<>(2);

    public static void main(String[] args) {

        for(int i=0; i<2; i++){
            cabins[i] = new Cabin(i+1);
        }

        // Cmd menu
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("""
                    \n-----------------------------
                     CRUISE SHIP BOARDING SYSTEM
                    _____________________________   
                     A: Add a Customer  
                     V: View Cabins           
                     E: Display Empty cabins
                     D: Delete customer from cabin
                     F: Find cabin from customer name
                     S: Store program data into file
                     O: View passengers (Ordered alphabetically by name).
                     T: View expenses
                     Q: View Queue
                     
                     C: Exit
                    _____________________________
                    >>>  """);

            String option = input.next();
            option = option.toUpperCase();

            switch (option) {
                case "A":
                    try{
                        if(!cabinsFull()){
                            for(Cabin c:cabins){
                                System.out.println("Cabin "+c.getCabinNo());
                            }
                            System.out.print("Enter cabin number: ");
                            int cabinNo = input.nextInt();
                            cabins[cabinNo-1].addPassenger();
                        }
                        else{
                            System.out.print("Cabins are full. Would you like to add in Queue? y(yes) / n(no) : ");
                            String selection = input.next().toLowerCase();
                            if(selection.equals("y")){
                                enQueue();
                            }
                        }
                    }
                    catch (Exception e){
                        System.out.println("Invalid input");
                        break;
                    }
                    break;

                case "V":
                    for(Cabin c: cabins){
                        System.out.println("_____________________________" +
                                "\nCabin "
                                +c.getCabinNo() +" (spots left: "
                                +(c.getArrLength() - c.passengerCount())
                                +")\n"+c.getPassengers() );
                    }
                    break;

                case "E":
                    System.out.print("""
                    -----------------------------
                            Empty Cabins
                    _____________________________   
                    """);
                    for(Cabin c: cabins){
                        if (c.isCabinEmpty()){
                            System.out.println("Cabin "+c.getCabinNo());
                        }
                    }
                    break;

                case "D":
                    try{
                        for(Cabin c:cabins){

                            System.out.println("_____________________________" +
                                    "\nCabin "+c.getCabinNo() + "\n" +c.getPassengers());

                        }
                        System.out.print("_____________________________" +
                                "\nEnter cabin number: ");
                        int cabinNo = input.nextInt();
                        if(!queue.isEmpty()){
                            cabins[cabinNo-1].deletePassenger(queue.front(),true);
                            queue.dequeue();
                        }
                        else{
                            cabins[cabinNo-1].deletePassenger(queue.front(),false);
                        }
                    }
                    catch (Exception e){
                        System.out.println("Invalid input");
                        break;
                    }
                    break;

                case "F":
                    try{
                        System.out.print("Enter Full name / First name / surname to search : ");
                        String name = input.next();
                        for(int num : findCabin(name,cabins)){
                            System.out.println("Cabin " + num);
                        }

                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;

                case "S":
                    saveToFile();
                    break;

                case "O":
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 0; i < cabins.length; i++) {
                        for (int j = 0; j < cabins[i].arr.length; j++) {
                            if(cabins[i].arr[j] != null){
                                names.add(cabins[i].arr[j].getName());
                            }

                        }
                    }
                    quickSort(names, 0, names.size()-1);

                    for (String name:names){
                        System.out.println(name);
                    }
                    break;
                case "T":
                    viewExpenses();
                    break;
                case "Q":
                    System.out.println(queue.toString());
                    break;
                case "C":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    // to check whether all the cabins are filled
    public static boolean cabinsFull() {
        for (Cabin c : cabins) {
            if (!c.isCabinFull()) {
                return false;
            }
        }
        return true;
    }

    // adding passengers to queue
    public static void enQueue(){
        Scanner input = new Scanner(System.in);
        if(!queue.isFull()){
            try {
                System.out.print("Enter First name: ");
                String fname = input.next();
                System.out.print("Enter surname: ");
                String sname = input.next();
                System.out.print("Enter age: ");
                int age = input.nextInt();
                System.out.print("Enter contact number: ");
                String phone = input.next();
                System.out.print("Enter email id: ");
                String email = input.next();
                System.out.print("Enter expense: ");
                int expense = input.nextInt();

                Passenger p = new Passenger(fname, sname, age, phone,email, expense);
                queue.enqueue(p);

                System.out.println("Added successfully!");

            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
        else{
            System.out.println("Sorry, queue is full.");
        }
    }

    // To find cabin number based on passenger name search
    public static ArrayList<Integer> findCabin(String name, Cabin[] cabins) {
        ArrayList<Integer> cabinNums = new ArrayList<>();

        for (int i = 0; i < cabins.length; i++) {

            for (int j = 0; j < cabins[i].arr.length; j++) {
                if(cabins[i].arr[j] != null){
                    if ((cabins[i].arr[j].getfName().equals(name)) ||
                            (cabins[i].arr[j].getSurName().equals(name)) ||
                            (cabins[i].arr[j].getName().equals(name))) {
                        cabinNums.add(i + 1);
                    }
                }
            }
        }
        return cabinNums;
    }

    // Quick sort to view passenger names in alphabetic order
    public static void quickSort(ArrayList<String> arr, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(arr, low, high);
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    public static int partition(ArrayList<String> arr, int low, int high) {
        String pivot = arr.get(high);
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (arr.get(j).compareTo(pivot) < 0) {
                i++;
                String temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        String temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);

        return i + 1;
    }

    // to get the expenses for each cabin and person
    public static void viewExpenses(){
        for(Cabin c : cabins){
            System.out.println("-----------------------------\nCabin "
                    + c.getCabinNo() + " (Total cost: " + c.getTotalExpense() + ")\n");
            for (int i = 0; i<c.arr.length; i++){
                if(c.arr[i] != null){
                    System.out.println("Name: " + c.arr[i].getName() +" | Expense: " + c.arr[i].getExpenses() );
                }
            }
        }
    }

    //saving data toa external file
    public static void saveToFile(){
        try {
            FileWriter writer = new FileWriter("output.txt");

            for(Cabin c: cabins){
                writer.write("\nCabin" + c.getCabinNo() + "");
                for(Passenger p: c.arr){
                    if(p != null){
                        writer.write("\n" + p.getfName() + "/" +
                                p.getSurName() + "/" +
                                p.getAge()+ "/" + p.getEmail() + "/"
                                + p.getPhoneNo() + "/" + p.getExpenses());
                    }
                }
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
