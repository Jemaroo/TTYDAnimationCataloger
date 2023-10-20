import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main 
{   
    public static String inputFilePath = "";
    public static String outputFilePath = "";

    /**
     * @Author Jemaroo
     * @Function Will attempt to read the given file, and set data into a spreadsheet
     */
    public static void main(String[] args)
    {
        inputFilePath = args[0];
        outputFilePath = args[1];
        File root = new File(inputFilePath);

        //Creating an ArrayList to store the files found
        ArrayList<File> files = new ArrayList<File>();

        //Creating an counter for how many files are in the directory
        int readableFiles = root.listFiles().length;

        //Creating a temporary array to store the files found to add to ArrayList
        File[] tempFiles = new File[readableFiles];

        //Setting the temporary array to store the files found
        tempFiles = root.listFiles();

        //Adding the files from the temporary array to the ArrayList
        for(int i = 0; i < readableFiles; i++)
        {
            if(tempFiles[i].getName().charAt((int)(tempFiles[i].getName().length() - 1)) != '-')
            {
                files.add(tempFiles[i]);
            }
        }

        try
        {
            //Making Combined Output File
            File combinationOutput = new File (outputFilePath + "\\Combined\\combinedData.csv");
            Path combinedPath = Paths.get(combinationOutput.getPath());
            Files.writeString(combinedPath, "\n", StandardOpenOption.CREATE_NEW);

            for(int i = 0; i < files.size(); i++)
            {
                //Inport File
                //File file = new File(inputFilePath);
                byte[] data = readData(files.get(i));

                String[] formattedData = new String[data.length];

                for(int j = 0; j < data.length; j++)
                {
                    formattedData[j] = String.format("%x", data[j]);
                }

                //Loop getting Call Location, Animation Data Offset, Call Name, and Index
                //Place data into spreadsheet
                setData(data, formattedData, files.get(i), combinedPath);

                //Spacing out sections in combined
                Files.writeString(combinedPath, "" + '\n' + '\n', StandardOpenOption.APPEND);
            }
        }
        catch(IOException e)
        {
            System.out.println("There was an Error Reading the Output File");
        }

        //File file = new File(inputFilePath);
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

            f1.close();

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
    private static void setData(byte[] data, String[] formattedData, File Original, Path combinedPath)
    {
        try
        {
            //Making Output File
            File output = new File((outputFilePath + "\\" + Original.getName() + ".csv"));
            Path standardPath = Paths.get(output.getPath());

            //Writing Top Row
            Files.writeString(standardPath, "Additional Animation Slots Tool, Call Location 0x, Animation Data Offset 0x, Call Name, Index, Animation Description, \n", StandardOpenOption.CREATE_NEW);
            Files.writeString(combinedPath, Original.getName() + '\n' + "Additional Animation Slots Tool, Call Location 0x, Animation Data Offset 0x, Call Name, Index, Animation Description, \n", StandardOpenOption.APPEND);

            //Determining how many animations are present
            String animationCount = "";

            if(formattedData[328].equals("0"))
            {
                animationCount = animationCount + "00";
            }
            else
            {
                animationCount = animationCount + formattedData[328];
            }

            if(formattedData[329].equals("0"))
            {
                animationCount = animationCount + "00";
            }
            else
            {
                animationCount = animationCount + formattedData[329];
            }

            if(formattedData[330].equals("0"))
            {
                animationCount = animationCount + "00";
            }
            else
            {
                animationCount = animationCount + formattedData[330];
            }

            if(formattedData[331].equals("0"))
            {
                animationCount = animationCount + "00";
            }
            else
            {
                animationCount = animationCount + formattedData[331];
            }

            int animationCountFormatted = Integer.parseInt(animationCount, 16);

            //Variables for animation placement
            int animationCallStartModifier = 428;
            String animationCallStart = "";

            if(formattedData[animationCallStartModifier].equals("0"))
            {
                animationCallStart = animationCallStart + "00";
            }
            else if(Integer.parseInt(formattedData[animationCallStartModifier], 16) < 16)
            {
                animationCallStart = animationCallStart + "0" + formattedData[animationCallStartModifier];
            }
            else
            {
                animationCallStart = animationCallStart + formattedData[animationCallStartModifier];
            }

            if(formattedData[animationCallStartModifier + 1].equals("0"))
            {
                animationCallStart = animationCallStart + "00";
            }
            else if(Integer.parseInt(formattedData[animationCallStartModifier + 1], 16) < 16)
            {
                animationCallStart = animationCallStart + "0" + formattedData[animationCallStartModifier + 1];
            }
            else
            {
                animationCallStart = animationCallStart + formattedData[animationCallStartModifier + 1];
            }

            if(formattedData[animationCallStartModifier + 2].equals("0"))
            {
                animationCallStart = animationCallStart + "00";
            }
            else if(Integer.parseInt(formattedData[animationCallStartModifier + 2], 16) < 16)
            {
                animationCallStart = animationCallStart + "0" + formattedData[animationCallStartModifier + 2];
            }
            else
            {
                animationCallStart = animationCallStart + formattedData[animationCallStartModifier + 2];
            }

            if(formattedData[animationCallStartModifier + 3].equals("0"))
            {
                animationCallStart = animationCallStart + "00";
            }
            else if(Integer.parseInt(formattedData[animationCallStartModifier + 3], 16) < 16)
            {
                animationCallStart = animationCallStart + "0" + formattedData[animationCallStartModifier + 3];
            }
            else
            {
                animationCallStart = animationCallStart + formattedData[animationCallStartModifier + 3];
            }
            
            int animationCallStartFormatted = Integer.parseInt(animationCallStart, 16);
            animationCallStart = Integer.toHexString(animationCallStartFormatted);
            int animationNameBound = animationCallStartFormatted + 60;
            int animationDataOffsetModifier = 0;
            String convertedHex = "";
            int tempConverter = 0;
            String[] splitConvertedHex = new String[3];
            splitConvertedHex[1] = null;
            String temp = "";
            String animationDataOffset = "";

            String description = "";

            //Writing Information 
            for(int i = 0; i < animationCountFormatted; i++)
            {
                if(i == 0)
                {
                    temp = "0,";
                }
                else
                {
                    temp = ",";
                }

                //Get Call Location
                temp = temp + animationCallStart + ",";

                //Get Animation Data Offset
                animationDataOffsetModifier = animationCallStartFormatted + 60;

                if(formattedData[animationDataOffsetModifier].equals("0"))
                {
                    animationDataOffset = animationDataOffset + "00";
                }
                else if(Integer.parseInt(formattedData[animationDataOffsetModifier], 16) < 16)
                {
                    animationDataOffset = animationDataOffset + "0" + formattedData[animationDataOffsetModifier];
                }
                else
                {
                    animationDataOffset = animationDataOffset + formattedData[animationDataOffsetModifier];
                }

                if(formattedData[animationDataOffsetModifier + 1].equals("0"))
                {
                    animationDataOffset = animationDataOffset + "00";
                }
                else if(Integer.parseInt(formattedData[animationDataOffsetModifier + 1], 16) < 16)
                {
                    animationDataOffset = animationDataOffset + "0" + formattedData[animationDataOffsetModifier + 1];
                }
                else
                {
                    animationDataOffset = animationDataOffset + formattedData[animationDataOffsetModifier + 1];
                }

                if(formattedData[animationDataOffsetModifier + 2].equals("0"))
                {
                    animationDataOffset = animationDataOffset + "00";
                }
                else if(Integer.parseInt(formattedData[animationDataOffsetModifier + 2], 16) < 16)
                {
                    animationDataOffset = animationDataOffset + "0" + formattedData[animationDataOffsetModifier + 2];
                }
                else
                {
                    animationDataOffset = animationDataOffset + formattedData[animationDataOffsetModifier + 2];
                }

                if(formattedData[animationDataOffsetModifier + 3].equals("0"))
                {
                    animationDataOffset = animationDataOffset + "00";
                }
                else if(Integer.parseInt(formattedData[animationDataOffsetModifier + 3], 16) < 16)
                {
                    animationDataOffset = animationDataOffset + "0" + formattedData[animationDataOffsetModifier + 3];
                }
                else
                {
                    animationDataOffset = animationDataOffset + formattedData[animationDataOffsetModifier + 3];
                }

                tempConverter = Integer.parseInt(animationDataOffset, 16);
                animationDataOffset = Integer.toHexString(tempConverter);
                //temp = temp + animationDataOffset + ",";
                temp = temp + "=DEC2HEX(HEX2DEC(\"" + animationDataOffset + "\") + ($A$2 * 64)),";

                //Get Call Name
                for(int j = animationCallStartFormatted; j < animationNameBound && !formattedData[j].equals("0"); j++)
                {
                    convertedHex = convertedHex + hexToAscii(formattedData[j]);
                }
                temp = temp + convertedHex + ",";

                //Placing Index, Animation Description, and endline
                temp = temp + i + ",";

                splitConvertedHex = convertedHex.split("_");

                switch(splitConvertedHex[splitConvertedHex.length - 2])
                {
                    case "Z": {description = "Static?"; break;}
                    case "S": {description = "Idle?"; break;}
                    case "R": {description = "Running?"; break;}
                    case "W": {description = "Walking?"; break;}
                    case "J": {description = "Jumping?"; break;}
                    case "D": {description = "Getting Hurt?"; break;}
                    case "N": {description = "Sleeping?"; break;}
                    case "K": {description = "Dizzy?"; break;}
                    case "T": {description = "Talking?"; break;}
                    case "C": {description = "Thinking?"; break;}
                    case "G": {description = "Ducking"; break;}
                    case "Q": {description = "Getting Hurt (Again?)?"; break;}
                    case "Y": {description = "Static Getting Hurt?"; break;}
                    case "A": {description = "Attacking?"; break;}
                    case "X": {description = "Tired?"; break;}
                    default: {description = ""; break;}
                }

                temp = temp + description + "\n";

                //Writing Data
                Files.writeString(standardPath, temp, StandardOpenOption.APPEND);
                Files.writeString(combinedPath, temp, StandardOpenOption.APPEND);

                //Setting Next Variables
                animationCallStartFormatted = animationCallStartFormatted + 64;
                animationCallStart = Integer.toHexString(animationCallStartFormatted);
                animationNameBound = animationCallStartFormatted + 60;
                animationDataOffsetModifier = 0;
                convertedHex = "";
                description = "";
                temp = "";
                animationDataOffset = "";
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