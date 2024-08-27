import java.util.*;

class Property{
    double askingPrice;
    int assetClass;
    int locationClass;
    double additionalIncome;
    int units;
    double avgRental;
    double vacancy;
    double expenses;
    double capRate;
    double expectedValue;
    double DSCR;
    double NOI;

    public Property(double askingPrice, int assetClass, int locationClass, double additionalIncome, int units, double avgRental, double vacancy, double expenses, double capRate, double expectedValue, double DSCR, double NOI){
        this.askingPrice = askingPrice;
        this.assetClass = assetClass;
        this.locationClass = locationClass;
        this.additionalIncome = additionalIncome;
        this.units = units;
        this.avgRental = avgRental;
        this.vacancy = vacancy;
        this.expenses = expenses;
        this.NOI = NOI;
        this.capRate = capRate;
        this.expectedValue = expectedValue;
        this.DSCR = DSCR;
    }

    public void print(){
        System.out.println("---------------------------------------------");
        System.out.printf("\tNOI: %.2f\n", NOI);
        System.out.printf("\tExpected Value: %.2f\n", expectedValue);
        System.out.printf("\tDSCR: %.2f\n", DSCR);
        if (askingPrice>expectedValue){
            System.out.printf("\tThis deal IS NOT worth it since the asking price of this property, %.2f is greater than the expected value, %.2f.\n",
                        askingPrice, expectedValue);
        }
        else {
            System.out.printf("\tThis deal IS worth it since the asking price of this property, %.2f is less than or equal to the expected value, %.2f.\n",
                        askingPrice, expectedValue);
        }
        System.out.println("---------------------------------------------");
    }
        
}

public class Underwriting {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        Property[] properties = new Property[100];
        System.out.println("CRE UNDERWRITER");
        System.out.println();
        int choice;
        int i = 0;

        do {
            System.out.println("Choose an option:");
            System.out.println("1: Enter property information");
            System.out.println("2: Exit program");
            System.out.println();

            System.out.println("\tEnter your selection: ");
            try {
                choice = scan.nextInt();
            }   catch (InputMismatchException e){
                System.out.println("Invalid input, try again");
                choice = -1;
            }
            scan.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter property information:");
                    properties[i] = createProperty(properties, i);
                    i++;
                    break;

                case 2:
                    break;
                default:
                    break;
            }
        } while (choice != 2);
        System.out.println();

        printReport(properties, i);

        scan.close();
    }
    
    private static void printReport(Property[] properties, int n){
        for (int i = 0; i < n; i++) {
            System.out.println("Property " + (i + 1) + ":");
            properties[i].print();
            System.out.println();

        }
    }

    private static Property createProperty(Property[] properties, int i){
        Scanner sc = new Scanner(System.in);
        double askingPrice;
        int assetClass;
        int locationClass;
        double additionalIncome;
        int units;
        double avgRental;
        int vacancyKnown;
        double vacancy=0;
        double expenses;
        double capRate;
        double expectedValue;
        double DSCR;
        double NOI;

        System.out.println("\t Asking price of property: ");
        askingPrice = sc.nextDouble();
        
        System.out.println("\t Asset Class of property [1 Class A | 2 Class B | 3 Class C]: ");
        assetClass = sc.nextInt();
        while (assetClass > 3 || assetClass < 1){
                System.out.println("\t INVALID ASSET CLASS");
                System.out.println("\t Asset Class of property [1 Class A | 2 Class B | 3 Class C]: ");
                assetClass = sc.nextInt();
        }
        
        
        // do while loop
        System.out.println("\t Location class of property [1 Class A | 2 Class B | 3 Class C]:");
        locationClass = sc.nextInt();
        while (locationClass > 3 || locationClass < 1){
            System.out.println("\t INVALID LOCATION CLASS");
            System.out.println("\t Locaiton Class of property [1 Class A | 2 Class B | 3 Class C]: ");
            locationClass = sc.nextInt();
        }

        System.out.println("\t Additional Income (amenities, valet, trash, premium parking, build backs, etc.) [0 if none, enter in percentage]:");
        additionalIncome = sc.nextDouble();

        System.out.println("\t Number of units in the property: ");
        units = sc.nextInt();

        System.out.println("\t Average going rate for rentals in the property: ");
        avgRental = sc.nextDouble();

        // do while loop here
        System.out.println("\t Vacancy Rate [known/given enter 1 | unknown enter 2]:");
        vacancyKnown = sc.nextInt();
        while (vacancyKnown > 2 || vacancyKnown < 1){
            System.out.println("\t INVALID INPUT");
            System.out.println("\t Vacancy Rate [known/given enter 1 | unknown enter 2]:");
            vacancyKnown = sc.nextInt();
        }

        if (vacancyKnown == 1){
            System.out.println("\t Enter given Vacancy Rate [enter in percentage]: ");
            vacancy = sc.nextDouble();
        }
        else if (vacancyKnown == 2){
            if (assetClass == 1){
                vacancy = 0.95;
            }
            else if (assetClass == 2 || assetClass == 3){
                vacancy = 0.90;
            }
        }
        System.out.println("\t Expense rate for the property (taxes, insurance, utilities, admin, advertising legal, accounting, payroll, management, turnover, repairs, maintenance, and reserves) [if unknown good figure is between 35% - 60%, enter in percentage]:");
        expenses = sc.nextDouble();

        System.out.println("\t Capitalization Rate for the property [enter in percentage]: ");
        capRate = sc.nextDouble();

        System.out.println("\t Annual debt service/mortgage on property:");
        double debt = sc.nextDouble();

        expectedValue = units*avgRental*12;

        if (additionalIncome>0){
            additionalIncome = additionalIncome/100;
            additionalIncome = additionalIncome+1.00;
            expectedValue = additionalIncome*expectedValue;
        }
        if (vacancyKnown==1){
            vacancy = vacancy/100;
            vacancy = 1-vacancy;
            expectedValue = expectedValue*vacancy;
        }
        else if (vacancyKnown == 2){
            expectedValue = expectedValue*vacancy;
        }
        expenses = expenses/100;
        expectedValue = expectedValue*expenses;

        NOI = expectedValue;

        capRate = capRate/100;
        expectedValue = expectedValue/capRate;
        
        DSCR = NOI/debt;
        
        System.out.println("Property Added");
        System.out.println();
        // sc.close();
        return new Property(askingPrice, assetClass, locationClass, additionalIncome, units, avgRental, vacancy, expenses, capRate, expectedValue, DSCR, NOI);
    }
}
