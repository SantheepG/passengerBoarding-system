package com.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Cabin {

    /**
     * Cabin class which holds passengers
     * holds no. of passengers, expenses & related getters & setters
     */

    private int cabinNo;
    protected Passenger[] arr;
    private int passengerCount;

    public Cabin(int cabinNo){
        this.cabinNo = cabinNo;
        this.arr = new Passenger[2];
        this.passengerCount = 0;
    }

    public int getCabinNo() {
        return cabinNo;
    }

    public void setCabinNo(int cabinNo) {
        this.cabinNo = cabinNo;
    }

    public int getArrLength() {
        return arr.length;
    }

    public void setArr(Passenger[] arr) {
        this.arr = arr;
    }

    public int passengerCount(){
        return passengerCount;
    }

    public int getExpense(){
        int expense=0;
        for(Passenger e: arr){
            expense += e.getExpenses();
        }
        return expense;
    }

    public boolean isCabinEmpty(){
        return passengerCount == 0;
    }

    public boolean isCabinFull(){
        return passengerCount == arr.length;
    }

    public int getTotalExpense(){
        int totalCost = 0;
        for(Passenger p : arr){
            if (p != null) {
                totalCost += p.getExpenses();
            }


        }
        return totalCost;
    }
    public void addPassenger(){
        Scanner input = new Scanner(System.in);
        if(!isCabinFull()){
            while(true) {
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
                    arr[passengerCount] = p;
                    passengerCount++;
                    System.out.println("Added successfully!");
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
            }
        }
        else{
            System.out.println("Cabin is full!");
        }
    }

    public ArrayList getPassengers(){
        ArrayList<String> passengers = new ArrayList<>();
        for(Passenger p: arr){
            if(p!=null){
                passengers.add(p.getName());
            }
        }
        return passengers;
    }

    public void deletePassenger(Object p, boolean queue){
        Scanner input = new Scanner(System.in);
        int selection = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i]!=null){
                System.out.println(i+1+ " - " +arr[i].getfName()+" "+arr[i].getSurName());
            }
        }
        System.out.print("Enter passenger number to delete:");
        selection = input.nextInt();
        if(queue){
            arr[selection-1] = null;
            passengerCount--;
            arr[passengerCount] = (Passenger) p;
            passengerCount++;
        }
        else{
            arr[selection-1] = null;
            passengerCount--;
        }
        System.out.println("Passenger deleted success");
    }


    @Override
    public String toString() {
        return "Cabin " + cabinNo +
                ", arr=" + Arrays.toString(arr) +
                ", passengerCount=" + passengerCount +
                '}';
    }
}
