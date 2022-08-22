import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
//import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;

//TODO:
//Make Input/Output Location accessible outside of code

public class Main 
{   
    public static String inputFilePath = "PUT DIRECT PATH HERE"; // <------------------------------------------------------------
    public static String outputFilePath = "PUT DIRECT PATH HERE"; // <------------------------------------------------------------

    /**
     * @Author Jemaroo
     * @Function Will attempt to read the given file, and set data into a spreadsheet
     */
    public static void main(String[] args)
    {

        /* Scanner userInput = new Scanner(System.in);
        System.out.println("Enter Animation File Location: ");
        String userInputData = userInput.nextLine();
        System.out.println("Location is: " + userInputData);
        inputFilePath = userInputData; */

        //Inport File
        File file = new File(inputFilePath);
        byte[] data = readData(file);
        String[] formattedData = new String[data.length];
        
        for(int i = 0; i < data.length; i++)
        {
            formattedData[i] = String.format("%x", data[i]);
        }

        //Loop getting Call Location, Animation Data Offset, Call Name, and Index
        //Place data into spreadsheet
        setData(data, formattedData, file);

        System.out.println("Done!");
    }

    /**
     * @Author Jemaroo
     * @Function Will attempt to read the given file
     */
    private static byte[] readData(File file)
    {
        try 
        {
            FileInputStream f1 = new FileInputStream(file);

            byte[] data = new byte[(int)file.length()];

            f1.read(data);

            return data;
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("There was an Error Opening the Input File");
        }
        catch (IOException e)
        {
            System.out.println("There was an Error Reading the Input File");
        }
    
        return new byte[0];
    }

    /**
     * @Author Jemaroo
     * @Function Will attempt to set data into a spreadsheet
     */
    private static void setData(byte[] data, String[] formattedData, File Original)
    {
        try
        {
            //Making Output File
            File output = new File((outputFilePath + "\\" + Original.getName() + ".csv"));
            Path test = Paths.get(output.getPath());

            //Writing Top Row
            Files.writeString(test, "Additional Animation Slots Tool, Call Location 0x, Animation Data Offset 0x, Call Name, Index, Animation Description, \n", StandardOpenOption.CREATE_NEW);

            //Determining how many animations are present
            String animationCount = (formattedData[328] + formattedData[329] + formattedData[330] + formattedData[331]);
            int animationCountFormatted = Integer.parseInt(animationCount, 16);

            //Variables for animation placement
            int animationCallStartModifier = 428;
            String animationCallStart = formattedData[animationCallStartModifier] + formattedData[animationCallStartModifier + 1] + formattedData[animationCallStartModifier + 2] + formattedData[animationCallStartModifier + 3];
            int animationCallStartFormatted = Integer.parseInt(animationCallStart, 16);
            animationCallStart = Integer.toHexString(animationCallStartFormatted);
            int animationNameBound = animationCallStartFormatted + 60;
            int animationDataOffsetModifier = 0;
            String convertedHex = "";
            int tempConverter = 0;
            String[] splitConvertedHex = new String[3];

            String description = "";

            //Writing Information 
            for(int i = 0; i < animationCountFormatted; i++)
            {
                String temp = ",";

                //Get Call Location
                temp = temp + animationCallStart + ",";

                //Get Animation Data Offset
                animationDataOffsetModifier = animationCallStartFormatted + 60;
                String animationDataOffset = formattedData[animationDataOffsetModifier] + formattedData[animationDataOffsetModifier + 1] + formattedData[animationDataOffsetModifier + 2] + formattedData[animationDataOffsetModifier + 3];
                tempConverter = Integer.parseInt(animationDataOffset, 16);
                animationDataOffset = Integer.toHexString(tempConverter);
                temp = temp + animationDataOffset + ",";

                //Get Call Name
                for(int j = animationCallStartFormatted; j < animationNameBound && !formattedData[j].equals("0"); j++)
                {
                    convertedHex = convertedHex + hexToAscii(formattedData[j]);
                }
                temp = temp + convertedHex + ",";

                //Placing Index, Animation Description, and endline
                temp = temp + i + ",";

                splitConvertedHex = convertedHex.split("_");

                switch(splitConvertedHex[1])
                {
                    case "Z":
                    {
                        description = "Static?";
                        break;
                    }
                    case "S":
                    {
                        description = "Idle?";
                        break;
                    }
                    case "R":
                    {
                        description = "Running?";
                        break;
                    }
                    case "W":
                    {
                        description = "Walking?";
                        break;
                    }
                    case "J":
                    {
                        description = "Jumping?";
                        break;
                    }
                    case "D":
                    {
                        description = "Tired?";
                        break;
                    }
                    case "N":
                    {
                        description = "Sleeping?";
                        break;
                    }
                    case "X":
                    {
                        description = "Dizzy?";
                        break;
                    }
                    case "T":
                    {
                        description = "Talking?";
                        break;
                    }
                    case "C":
                    {
                        description = "Thinking?";
                        break;
                    }
                    case "G":
                    {
                        description = "Ducking";
                        break;
                    }
                    default:
                    {
                        description = "";
                        break;
                    }
                }
                temp = temp + description + "\n";

                //Setting Next Variables
                animationCallStartFormatted = animationCallStartFormatted + 64;
                animationCallStart = Integer.toHexString(animationCallStartFormatted);
                animationNameBound = animationCallStartFormatted + 60;
                animationDataOffsetModifier = 0;
                convertedHex = "";
                description = "";

                //Writing Data
                Files.writeString(test, temp, StandardOpenOption.APPEND);
            }
            
            
        }
        catch (FileNotFoundException e) 
        {
            System.out.println("There was an Error Opening the Output File");
        }
        catch (IOException e)
        {
            System.out.println("There was an Error Reading the Output File");
        }
    }

    /**
     * @Author baeldung
     * @Function Cut the Hex value in 2 char groups, Convert it to base 16 Integer using Integer.parseInt(hex, 16) and cast to char, Append all chars in a StringBuilder
     * @Source https://www.baeldung.com/java-convert-hex-to-ascii
     */
    private static String hexToAscii(String hexStr) 
    {
        StringBuilder output = new StringBuilder("");
        
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        
        return output.toString();
    }
}