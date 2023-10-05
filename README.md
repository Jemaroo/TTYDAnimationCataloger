# TTYDAnimationCataloger
A tool to extract animation data from TTYD's and SPM's animation files

I also have been posting the extracted information with descriptions here: https://drive.google.com/drive/folders/1D-M9QLmiZRplz35xYeh3wlsSt9IPH8Jb?usp=drive_link 

UPDATED (10/4/23): Moved the excel file into a new Combined folder, when run, will also combine all the csvs into a seperate file as well as individually. Fixed another issue where larger Call Names wouldn't display, it should now work for all lengths.

UPDATED (9/27/23): Fixed an issue where smaller Call Names wouldn't display.

UPDATED (5/24/23): Finished adding compatibility for converting an entire folder at once, bug fixes.

UPDATED (11/25/22): Fixed a bug where some 0's would be missing from the initial animation data location values.

UPDATED (8/28/22): Full release version Fixed a bug where some 0's would be missing from the animation data offset values, made the script a jar file to easily run with a batch, removed older versions
 
UPDATED (8/23/22): Fixed a few bugs with recognizing names, added functions for the additional animation slots tool (can be toggled on and off by uncommenting a line)

Huge thanks to PistonMiner for documentation and tools on TTYD's animation formats https://github.com/PistonMiner/ttyd-tools


============USAGE============

1: Download the Latest Release

2: Should Contain: Run.bat, TTYDAnimationCataloger.jar, and the output folder with the Combined folder and OPEN CSV's HERE sheet.

3: Paste the "a" folder from the root of your game's files into the same folder as the files listed above

4: Remove the 2 .bin files in the a folder

5: Change the name of the "a" folder to "input"

6: Run Run.bat

7: The csvs with data should be in the output folder and the combined file should be in the Combined folder, import the data into an excel spreadsheet to see it properly
