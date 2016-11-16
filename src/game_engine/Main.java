package game_engine;

public class Main {

    public static void main(String[] args) {
        UserInterface.PrintFirstMenu();
        UserInterface.PrintSecondMenu();
        long StartTime = System.currentTimeMillis();  //GAMETIME CLOCK

        UserInterface.GetUserChoice(5,eTurn.ROW);
        System.out.println("Hello World!");

        long endTime = System.currentTimeMillis();  //GAMETIME CLOCK
        long Time = endTime - StartTime;            //GAMETIME CLOCK
        double totalTime = Time / 1000.0;           //GAMETIME CLOCK
        UserInterface.ShowStatistics(5,totalTime,8,9);

    }
}
