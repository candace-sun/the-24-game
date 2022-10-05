import java.util.*;
import java.io.*;

public class The24Game {
   private int[] numbers;
   private String solution;
   private int numSolutions;
   private boolean parenSolution2, parenSolution1, parenSolution3a, parenSolution3b;
   
   //constructor
   public The24Game() {
      numbers = new int[4];
      solution = "";
      numSolutions = 0;
      parenSolution2 = false;
      parenSolution1 = false;
      addRandNums();
      while(!verify())
         addRandNums();
   }
   
   //creates a new random 4-number set
   public void addRandNums() {
      for(int x = 0; x < 4; x++) 
         numbers[x] = (int)((Math.random()*9)+1);
   }

   /*checks if the given 4-number set can make 24 - 
   with 24 possible arrangements of the 4 numbers and
   64 possible arrangements of the 4 operators between the 4 numbers
   */
   public boolean verify() {
      ArrayList<Integer[]> numArrangements = allArrangements();
      for(int x = 0; x < numArrangements.size(); x++)
         if(verifyHelper(numArrangements.get(x)))
            return true;
      return false;
   }
   
   public boolean verifyHelper(Integer[] arrangement) {
      double subtotal1 = arrangement[0];
      for(int x = 0; x < 4; x++) { 
         double subtotal2 = processNums(x, subtotal1, arrangement[1]);
         if(subtotal2 < 0) 
            return false;
         for(int y = 0; y < 4; y++) {
            double subtotal3 = processNums(y, subtotal2, arrangement[2]);
            if(subtotal3 < 0) 
               return false;
            for(int z = 0; z < 4; z++) {
               double subtotal4 = processNums(z, subtotal3, arrangement[3]);
               if(subtotal4 == 24.0) {
                  int[] numOperators = new int[]{x,y,z};
                  if(numSolutions==0)
                     updateSolution(arrangement, numOperators);  
                  numSolutions++;               
               }                   
            }
         }
      }
      
      for(int x = 0; x < 4; x++) { //(a+b)+(c+d)
         double paren1 = processNums(x, arrangement[0], arrangement[1]);
         for(int y = 0; y < 4; y++) {
            double paren2 = processNums(y, arrangement[2], arrangement[3]);
            for(int z = 0; z < 4; z++) {
               if(processNums(z, paren1, paren2) == 24.0) {
                  int[] numOperators = new int[]{x,y,z};
                  if(numSolutions==0) {
                     parenSolution2 = true;
                     updateSolution(arrangement, numOperators); 
                  } 
                  numSolutions++; //this overcounts some solutions so idk
               }
            }
         }
      }
      
      for(int x = 0; x < 4; x++) { //a+(b+c)+d
         double paren1 = processNums(x, arrangement[1], arrangement[2]);
         for(int y = 0; y < 4; y++) {
            double sub2 = processNums(y, arrangement[0], paren1);
            for(int z = 0; z < 4; z++) {
               if(processNums(z, sub2, arrangement[3]) == 24.0) {
                  int[] numOperators = new int[]{x,y,z};
                  if(numSolutions==0) {
                     parenSolution1 = true;
                     updateSolution(arrangement, numOperators); 
                  } 
                  numSolutions++; //this overcounts some solutions so idk
               }
            }
         }
      }
      for(int x = 0; x < 4; x++) { //a+(b+(c+d)) NEED TO FIX MY HEAD HURTS
         double paren1 = processNums(x, arrangement[2], arrangement[3]);
         for(int y = 0; y < 4; y++) {
            double sub2 = processNums(y, arrangement[0], paren1);
            for(int z = 0; z < 4; z++) {
               if(processNums(z, sub2, arrangement[3]) == 24.0) {
                  int[] numOperators = new int[]{x,y,z};
                  if(numSolutions==0) {
                     parenSolution3a = true;
                     updateSolution(arrangement, numOperators); 
                  } 
                  numSolutions++; //this overcounts some solutions so idk
               }
            }
         }
      }
            
      if(numSolutions > 0)
         return true;
      return false;
   }
   
   //returns all 24 permutations of the 4 numbers in an ArrayList
   public ArrayList<Integer[]> allArrangements() {
   
      ArrayList<Integer[]> arrangements = new ArrayList<Integer[]>(); 
      for(int x = 0; x < 4; x++) { //x is the index of the first number (stored in numbers)
         ArrayList<Integer> temp1 = new ArrayList<Integer>(); //stores 3 remaining nums
         for(int x1 = 0; x1 < 4; x1++) //adds the numbers not picked yet into temp1
            if(x1!=x)
               temp1.add(numbers[x1]); 
         
         for(int y = 0; y < 3; y++) { //y is the index of the second number (stored in temp1)
            ArrayList<Integer> temp2 = new ArrayList<Integer>(); //stores 2 remaining nums
            for(int y2 = 0; y2 < 3; y2++) //adds the numbers not picked yet into temp2
               if(y2!=y)
                  temp2.add(temp1.get(y2));
            
            for(int z = 0; z < 2; z++) { //z is the index of the third number (stored in temp2)
               int num4 = 0; //stores remaining num
               for(int z3 = 0; z3 < 2; z3++) { //adds number not picked yet into num4
                  if(z3!=z)
                     num4 = temp2.get(z3);
               }
               Integer[] a = new Integer[]{numbers[x], temp1.get(y), temp2.get(z), num4};
               arrangements.add(a);
            }
         }
      }
      return arrangements;
   }
   
   //based on the value of x, applies operators to one and two and returns total
   public double processNums(int x, double one, double two) {
      if(x==0)
         return one+two;
      if(x==1)
         return one-two;
      if(x==2)
         return one*two;
      else {
         if(two == 0)
            return -1;
         return one/two;
      }
   }
   
   public String getProblem() {
      String problem = "Problem: Make 24 with ";
      ArrayList<Integer> numz = new ArrayList<Integer>();
      for(int n : numbers)
         numz.add(n);
      problem+=numz+".";
      return problem;
   }
   
   public void updateSolution(Integer[] solutionOrder, int[] numOperators) {
      String[] strOperators = new String[3];
      for(int x = 0; x < 3; x++) {
         if(numOperators[x] == 0)
            strOperators[x] = "Add";
         if(numOperators[x] == 1)
            strOperators[x] = "Subtract";
         if(numOperators[x] == 2)
            strOperators[x] = "Multiply";
         if(numOperators[x] == 3)
            strOperators[x] = "Divide";
      }
      
      if(parenSolution) { //paren solution string
         if(strOperators[0].equals("Add"))
            solution = strOperators[0]+" "+solutionOrder[0]+" and "+solutionOrder[1]+"; ";
         else
            solution = strOperators[0]+" "+solutionOrder[0]+" by "+solutionOrder[1]+"; ";
      
         if(strOperators[1].equals("Add"))
            solution += strOperators[1]+" "+solutionOrder[2]+" and "+solutionOrder[3]+"; ";
         else
            solution += strOperators[1]+" "+solutionOrder[2]+" and "+solutionOrder[3]+"; ";
      
         if(strOperators[2].equals("Add"))
            solution += "Add those together.";       
         else
            solution += strOperators[2]+" those.";
      } 
      
      //end paren solution string
      else {
         if(strOperators[0].equals("Add"))
            solution = strOperators[0]+" "+solutionOrder[0]+" and "+solutionOrder[1]+"; ";
         else
            solution = strOperators[0]+" "+solutionOrder[0]+" by "+solutionOrder[1]+"; ";
      
         if(strOperators[1].equals("Add"))
            solution += strOperators[1]+" that to " +solutionOrder[2]+"; ";
         else
            solution += strOperators[1]+" that by " +solutionOrder[2]+"; ";
      
         if(strOperators[2].equals("Add"))
            solution += strOperators[2]+" that to " +solutionOrder[3]+".";       
         else
            solution += strOperators[2]+" that by " +solutionOrder[3]+".";          
      }
   } 
   
   public String getSolution() {
      return solution;
   }
   
   public int[] getNums() {
      return numbers;
   }
   
   public boolean getParenSolution() {
      return parenSolution;
   }
   
   public void setEasy() {
      addRandNums();
      solution="";
      numSolutions=0;
      while(!verify()&&numSolutions>20)
         addRandNums();
   }
   
   public void setMedium() {
      addRandNums();
      solution="";
      numSolutions=0;
      while(!verify() && (numSolutions<20 && numSolutions<10))
         addRandNums();
   }
   
   public void setHard() {
      addRandNums();
      solution="";
      numSolutions=0;
      while(!verify() && (numSolutions<10 && numSolutions>3))
         addRandNums();
   }
   
   public void setDemon() {
      addRandNums();
      solution="";
      numSolutions=0;
      while(!verify() && (numSolutions<3 && numSolutions>=1))
         addRandNums();
   }
}