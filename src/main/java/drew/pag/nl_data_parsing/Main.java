package drew.pag.nl_data_parsing;

import drew.pag.nl_data_parsing.fish.Fish;
import drew.pag.nl_data_parsing.fish.FishSpawnWeight;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author drewpag
 */
public class Main {
    
    final static String[] bugs = new String[]{"Common Butterfly", "Yellow Butterfly", "Tiger Butterfly", "Peacock Butterfly",
        "Monarch Butterfly", "Emperor Butterfly", "Agrias Butterfly", "Raja Brooke Butterfly", "Birdwing Butterfly", "Moth",
        "Oak Silk Moth", "Honeybee", "Bee", "Long Locust", "Migratory Locust", "Rice Grasshopper", "Mantis", "Orchid Mantis",
        "Brown Cicada", "Robust Cicada", "Giant Cicada", "Walker Cicada", "Evening Cicada", "Cicada Shell", "Lantern Fly",
        "Red Dragonfly", "Darner Dragonfly", "Banded Dragonfly", "Petaltail Dragonfly", "Ant", "Pondskater", "Diving Beetle",
        "Stinkbug", "Snail", "Cricket", "Bell Cricket", "Grasshopper", "Mole Cricket", "Walking Leaf", "Walking Stick", "Bagworm",
        "Ladybug", "Violin Beetle", "Longhorn Beetle", "Tiger Beetle", "Dung Beetle", "Wharf Roach", "Hermit Crab", "Firefly",
        "Fruit Beetle", "Scarab Beetle", "Jewel Beetle", "Miyama Stag", "Saw Stag", "Giant Stag", "Rainbow Stag", "Cyclommatus Stag",
        "Golden Stag", "Horned Dynastid", "Horned Atlas", "Horned Elephant", "Horned Hercules", "Goliath Beetle", "Flea", "Pill Bug",
        "Mosquito", "Fly", "House Centipede", "Centipede", "Spider", "Tarantula", "Scorpion", "Snowflake", "No Spawn"};
    /**
     * NL data doesn't have acre type ID with the spawn data... so do it yourself
     * -1 = no spawn
     * 0 = river
     * 1 = lake
     * 2 = waterfall
     * 3 = pond
     * 4 = river mouth
     * 5 = ocean
     * 6 = ocean with rain/snow
     */
    // fins size 7, eel/ribbon eel size 8 for distinguishing purposes
    final static Fish[] fish = new Fish[]{
        new Fish(0, "Bitterling", 1, 0),
        new Fish(1, "Pale Chub", 1, 0),
        new Fish(2, "Crucian Carp", 2, 0),
        new Fish(3, "Dace", 3, 0),
        new Fish(4, "Barbel Steed", 3, 0),
        new Fish(5, "Carp", 4, 0),
        new Fish(6, "Koi", 4, 0),
        new Fish(7, "Goldfish", 1, 0),
        new Fish(8, "Pop-eyed Goldfish", 1, 0),
        new Fish(9, "Killifish", 1, 3),
        new Fish(10, "Crawfish", 2, 3),
        new Fish(11, "Soft-shelled Turtle", 3, 0),
        new Fish(12, "Tadpole", 1, 3),
        new Fish(13, "Frog", 2, 3),
        new Fish(14, "Freshwater Goby", 2, 0),
        new Fish(15, "Loach", 2, 0),
        new Fish(16, "Catfish", 4, 1),
        new Fish(17, "Eel", 8, 0),
        new Fish(18, "Giant Snakehead", 5, 1),
        new Fish(19, "Bluegill", 2, 0),
        new Fish(20, "Yellow Perch", 3, 0),
        new Fish(21, "Black Bass", 4, 0),
        new Fish(22, "Pike", 5, 0),
        new Fish(23, "Pond Smelt", 2, 0),
        new Fish(24, "Sweetfish", 3, 0),
        new Fish(25, "Cherry Salmon", 3, 0),
        new Fish(26, "Char", 3, 2),
        new Fish(27, "Rainbow Trout", 4, 0),
        new Fish(28, "Stringfish", 6, 0),
        new Fish(29, "Salmon", 4, -1),
        new Fish(30, "King Salmon", 6, -1),
        new Fish(31, "Mitten Crab", 2, 0),
        new Fish(32, "Guppy", 1, 0),
        new Fish(33, "Nibble fish", 2, 0),
        new Fish(34, "Angelfish", 2, 0),
        new Fish(35, "Neon Tetra", 1, 0),
        new Fish(36, "Piranha", 2, 0),
        new Fish(37, "Arowana", 4, 0),
        new Fish(38, "Dorado", 5, 0), 
        new Fish(39, "Gar", 6, 1), 
        new Fish(40, "Arapaima", 6, 0),
        new Fish(41, "Saddled Bichir", 4, 0),
        new Fish(42, "Sea Butterfly", 1, 5),
        new Fish(43, "Sea horse", 1, 5), 
        new Fish(44, "Clown fish", 1, 5), 
        new Fish(45, "Surgeonfish", 2, 5), 
        new Fish(46, "Butterflyfish", 2, 5),
        new Fish(47, "Napoleonfish", 6, 5), 
        new Fish(48, "Zebra Turkeyfish", 3, 5), 
        new Fish(49, "Blowfish", 3, 5),
        new Fish(50, "Puffer Fish", 3, 5),
        new Fish(51, "Horse Mackerel", 2, 5),
        new Fish(52, "Barred Knifejaw", 3, 5),
        new Fish(53, "Sea Bass", 5, 5),
        new Fish(54, "Red Snapper", 3, 5),
        new Fish(55, "Dab", 3, 5),
        new Fish(56, "Olive Flounder", 4, 5),
        new Fish(57, "Squid", 3, 5),
        new Fish(58, "Moray Eel", 5, 5),
        new Fish(59, "Ribbon Eel", 8, 5),
        new Fish(60, "Football Fish", 4, 5),
        new Fish(61, "Tuna", 6, 5),
        new Fish(62, "Blue Marlin", 6, 5),
        new Fish(63, "Giant Trevally", 5, 5),
        new Fish(64, "Ray", 5, 5),
        new Fish(65, "Ocean Sunfish", 7, 5),
        new Fish(66, "Hammerhead Shark", 7, 5),
        new Fish(67, "Shark", 7, 5),
        new Fish(68, "Saw Shark", 7, 5),
        new Fish(69, "Whale Shark", 7, 5),
        new Fish(70, "Oarfish", 6, 5),
        new Fish(71, "Coelacanth", 6, 6),
        new Fish(72, "Can", 2, -1), 
        new Fish(73, "Boot", 3, -1), 
        new Fish(74, "Tire", 4, -1),
        new Fish(107, "No Spawn", 0, -1)
    };
    
    // Seaweed has ID 0x4C = 76
    final static int SEA_CREATURES_ID_OFFSET = 76;
    final static String[] seaCreatures = new String[] {"Seaweed", "Sea Grapes", "Sea Urchin", "Acorn Barnacle", "Oyster", "Turban Shell",
                                                "Abalone", "Ear Shell", "Clam", "Pearl Oyster", "Scallop", "Sea Anemone", "Sea Star",
                                                "Sea Cucumber", "Sea Slug", "Flatworm", "Mantis Shrimp", "Sweet Shrimp", "Tiger Prawn",
                                                "Spiny Lobster", "Lobster", "Snow Crab", "Horsehair Crab", "Red King Crab", "Spider Crab",
                                                "Octopus", "Spotted Garden Eel", "Chambered Nautilus", "Horseshoe Crab", "Giant Isopod"};
    
    // the index of each time range corresponds to its internal ID
    final static String[] bugTimes = new String[]{"11PM - 4AM", "4AM - 8AM", "8AM - 4PM", "4PM - 5PM", "5PM - 7PM", "7PM - 11PM"};
    final static String[] fishTimes = new String[]{"4AM - 9AM, 4PM - 9PM", "9AM - 4PM", "9PM - 4AM"};
    
    // river fish start addr, ocean fish start addr, island fish start addr
    final static int[] FISH_ADDRS = new int[] {0x85ff50, 0x860fb8, 0x863748};
    final static int SEA_CREATURES_ADDR = 0x861f78;
    final static int SEA_CREATURES_ISLAND_ADDR = 0x864288;
    final static int BUGS_TOP_LEVEL_ADDR = 0x86c2fc;
    final static int BUGS_ISLAND_TOP_LEVEL_ADDR = 0x86e81c;
    // Ghidra is slightly  off.. first fish addr above is 76F6F8 in HxD...
    // and the bugs are also off from the fish by 4 bytes!?
    final static int ADDR_OFFSET = 0xF0858;
    
    // size of each data region
    final static int RIVER_FISH_REGION_SIZE = 100;
    final static int OCEAN_FISH_REGION_SIZE = 96;
    final static int SEA_CREATURES_REGION_SIZE = 88;
    final static int SEA_CREATURES_ISLAND_REGION_SIZE = 72;
    final static int BUG_REGION_SIZE=132;
    final static int BUG_ISLAND_REGION_SIZE=124;
    
    final static String[] monthNames = new String[] {"January", "February", "March", "April", "May", "June", "July",
                                                        "August", "September", "October", "November", "December", "Island"};
    
    final static String[] fishMonthNames = new String[] {"January", "February", "March", "April", "May", "June", "July", "August (1-15)",
                                                        "August (16-31)", "September (1-15)", "September (16-30)", "October", "November",
                                                        "December", "Island"};
    
    static String[] fishAcreIds = new String[] {"River", "Lake", "Waterfall", "Pond", "River Mouth", "Ocean (rain/snow)", "Ocean"};
    
    static Map<Integer, Map<Integer, List<FishSpawnWeight>>> riverFishSpawnWeightMap = new HashMap<>();
    static Map<Integer, Map<Integer, List<FishSpawnWeight>>> oceanFishSpawnWeightMap = new HashMap<>();
    
    static Double[][] fishSpawnWeightArray = new Double[76][45];
    static Double[][] fishShadowBasedArray = new Double[76][45];
    static Double[][] bugBasePercentagesArray = new Double[74][78];
    static Double[][] seaCreaturesBasePercentagesArray = new Double[31][45];
    
    static String elfPathStr = "C:/Users/drewp/ExeFS.elf";
    static String fishPercentagesCsv = "C:/Users/drewp/Desktop/nl_fish_percentages.csv";
    static String fishShadowPercentagesCsv = "C:/Users/drewp/Desktop/nl_fish_shadow_percentages.csv";
    static String bugPercentagesCsv = "C:/Users/drewp/Desktop/nl_bug_percentages.csv";
    static String seaCreaturePercentagesCsv = "C:/Users/drewp/Desktop/nl_sea_creature_percentages.csv";

    public static void main(String[] args) {
        
        for(int i=0; i < 76; i++){
            for(int j = 0; j < 45; j++){
                fishSpawnWeightArray[i][j] = 0.0;
                fishShadowBasedArray[i][j] = 0.0;
            }
        }
        
        for(int i=0; i < 74; i++){
            for(int j = 0; j < 78; j++){
                bugBasePercentagesArray[i][j] = 0.0;
            }
        }
        
        for(int i = 0; i < 31; i++){
            for(int j = 0; j < 45; j++){
                seaCreaturesBasePercentagesArray[i][j] = 0.0;
            }
        }
        
        // display the pane, and get the file path + options (?) back
//        String dolPathStr = LoadDolDialog.display();
        
        if(elfPathStr.equals("")){
            // user canceled... gg
            System.exit(0);
        }
        
//        String bugs = parseBugData(elfPathStr);
//        System.out.println(bugs);
        String fish = parseFishData(elfPathStr);
        System.out.println(fish);
//        String seaCreatures = parseSeaCreatureData(elfPathStr);
//        System.out.println(seaCreatures);
        
        // process all of the fish spawn weights
        String riverSpawnWeights = processRiverFishSpawnWeights();
//        String oceanSpawnWeights = processOceanFishSpawnWeights();
        System.out.println(riverSpawnWeights);
//        System.out.println(oceanSpawnWeights);
        
        // write %s and shadow-based% to .csv files
        writeFishToCsv();
//        writeBugsToCsv();
//        writeSeaCreaturesToCsv();
    }
    
    private static String parseBugData(String dolPathStr){
        StringBuilder result = new StringBuilder();
        
        try{
            Path dolPath = Paths.get(dolPathStr);
            
            byte[] data = null;
            try {
                data = Files.readAllBytes(dolPath);
            } catch (IOException ex) {
                System.out.println("Exception while reading ww.bin byte array:\n" + ex);
                System.exit(0);
            }
            
            ByteBuffer bb = ByteBuffer.wrap(data);
            // little
            bb.order(ByteOrder.LITTLE_ENDIAN);
            
            for(int monthId = 0; monthId < 13; monthId++){
                StringBuilder sb = new StringBuilder();
                sb.append("\n").append(monthNames[monthId]).append(":\n");                
                                
                // for each time of day
                for(int timeOfDayId = 0; timeOfDayId < 6; timeOfDayId++){
                    
                    sb.append("\n").append(bugTimes[timeOfDayId]).append("\n");
                    
                    // handle Island-specific data region size...
                    long bugAddr =  (monthId == 12) ?
                            BUGS_ISLAND_TOP_LEVEL_ADDR + (BUG_ISLAND_REGION_SIZE * timeOfDayId) - ADDR_OFFSET
                            : BUGS_TOP_LEVEL_ADDR + (BUG_REGION_SIZE * ((monthId*6) + timeOfDayId)) - ADDR_OFFSET;
                    
//                    System.out.println("Ghidra addr for " + monthNames[monthId] + ": " + (BUGS_TOP_LEVEL_ADDR + (BUG_REGION_SIZE * ((monthId*6) + timeOfDayId))));

                    int bugIndex = 0;
                    int lastBugId = 0;
                    int lastSpawnRange = 0;
                    
                    while(lastSpawnRange < 1000){

                        byte[] bugBytes = new byte[4];
                        bb.get((int) (bugAddr + (bugIndex * 4)), bugBytes, 0, 4);

                        // first byte is the bug ID
                        int bugId = Byte.toUnsignedInt(bugBytes[0]);
                        // checks for end of segment, to skip over the filler 0 bytes
                        if(bugId < lastBugId){
                            lastSpawnRange = 1000;
                            break;
                        }

                        // upper spawn range
                        int upperSpawnRange = ((bugBytes[3] & 0xff) << 8) | (bugBytes[2] & 0xff);

                        int spawnWeight = upperSpawnRange - lastSpawnRange;

                        String bugName = bugId == 83 ? "No Spawn" : bugs[bugId];

                        sb.append(String.format("%1$18s", bugName)).append("\t").append(spawnWeight).append("\n");
                        
                        // store the percentage (/1000.0) in the array at the proper place...
                        int bugArrIndex = bugId == 83 ? 73 : bugId;
                        bugBasePercentagesArray[bugArrIndex][(monthId*6) + timeOfDayId] = spawnWeight / 1000.0;
                        
                        lastSpawnRange = upperSpawnRange;
                        lastBugId = bugId;
                        bugIndex++;
                    }
                }
                
                result.append(sb);
            }
            
//            System.out.println(result);
            
            return result.toString();
            
        } catch(Exception ex){
            System.out.println("Exception " + ex);
            ex.printStackTrace();
            
            return result.toString();
        }
    }
    
    private static String parseFishData(String dolPathStr){
        
        StringBuilder result = new StringBuilder();
        
        try{
            Path binPath = Paths.get(dolPathStr);
            
            byte[] data = null;
            try {
                data = Files.readAllBytes(binPath);
            } catch (IOException ex) {
                System.out.println("Exception while reading ww.bin byte array:\n" + ex);
                System.exit(0);
            }
            
            ByteBuffer bb = ByteBuffer.wrap(data);
            // little
            bb.order(ByteOrder.LITTLE_ENDIAN);
            
            for(int monthId = 0; monthId < 15; monthId ++){
                StringBuilder sb = new StringBuilder();
                sb.append("\n").append(fishMonthNames[monthId]).append(":\n");
                
                // maps containing the 3 spawn weight lists for each time of day
                // one for river, one for ocean
                Map<Integer, List<FishSpawnWeight>> riverMonthlySpawnWeightsMap = new HashMap<>();
                Map<Integer, List<FishSpawnWeight>> oceanMonthlySpawnWeightsMap = new HashMap<>();
                
                ArrayList<FishSpawnWeight> riverEveningWeights = new ArrayList<>();
                ArrayList<FishSpawnWeight> riverDayWeights = new ArrayList<>();
                ArrayList<FishSpawnWeight> riverNightWeights = new ArrayList<>();
                ArrayList<FishSpawnWeight> oceanEveningWeights = new ArrayList<>();
                ArrayList<FishSpawnWeight> oceanDayWeights = new ArrayList<>();
                ArrayList<FishSpawnWeight> oceanNightWeights = new ArrayList<>();
                
                riverMonthlySpawnWeightsMap.put(0, riverEveningWeights);
                riverMonthlySpawnWeightsMap.put(1, riverDayWeights);
                riverMonthlySpawnWeightsMap.put(2, riverNightWeights);
                oceanMonthlySpawnWeightsMap.put(0, oceanEveningWeights);
                oceanMonthlySpawnWeightsMap.put(1, oceanDayWeights);
                oceanMonthlySpawnWeightsMap.put(2, oceanNightWeights);
                
                // for each time of day
                for(int timeOfDayId = 0; timeOfDayId < 3; timeOfDayId++){
                    
                    sb.append("\n").append(fishTimes[timeOfDayId]).append("\n");

                    // do the river fish, then the ocean fish
                    for(int isOcean = 0; isOcean < 2; isOcean++){
                        
                        long fishAddr;
                        
                        // if we've reached the Island fish data
                        if(monthId == 14){
                            fishAddr = FISH_ADDRS[2] + (96*timeOfDayId) - ADDR_OFFSET;
                            isOcean = 2;
                        } else{
                            sb.append((isOcean == 0) ? "River" : "Ocean").append("\n");
                            int offset = (isOcean == 0) ? RIVER_FISH_REGION_SIZE : OCEAN_FISH_REGION_SIZE;
                            fishAddr =  FISH_ADDRS[isOcean] + (offset * ((monthId*3) + timeOfDayId)) - ADDR_OFFSET;
                        }
                        
                        int fishId = -1;
                        int lastFishId = -1;
                        int fishIndex = 0;
                        int lastSpawnRange = 0;
                        while(lastSpawnRange < 100){
                            
                            byte[] fishBytes = new byte[4];
                            bb.get((int) (fishAddr + (fishIndex * 4)), fishBytes, 0, 4);
                            
                            // first byte is the fish ID
                            fishId = Byte.toUnsignedInt(fishBytes[0]);
                            // checks for end of segment, to skip over the filler 0 bytes
                            if(fishId < lastFishId){
                                lastSpawnRange = 100;
                                break;
                            }

                            // upper spawn range
                            int upperSpawnRange = ((fishBytes[3] & 0xff) << 8) | (fishBytes[2] & 0xff);
                            
                            int spawnWeight = upperSpawnRange - lastSpawnRange;
                            
                            String fishName = (fishId == 107 ? "No Spawn" : fish[fishId].getName());
                            
                            int acreId;
                            // handle unique acre types:
                            // salmon/king salmon actually always show up at the river mouth in this game...
                            // can/boot/tire, depending on if this is river data or ocean data
                            switch (fishId) {
                                case 29:
                                case 30:
                                    acreId = 4;  
                                    break;
                                case 72:
                                case 73:
                                case 74:
                                    acreId = (isOcean == 0) ? 0 : 5;
                                    break;
                                case 107:
                                    acreId = -1;
                                    break;
                                default:
                                    acreId = fish[fishId].getAcreType();
                                    break;
                            }
                            
                            // add the spawn weight for this month and time of day to this fish's list
                            FishSpawnWeight weight = new FishSpawnWeight(fishId, monthId, timeOfDayId, acreId, spawnWeight);

                            // add this spawn weight to the specific fish (not currently used)
                            if(fishId == 107){
                                fish[75].addSpawnWeight(weight);
                            } else{
                                fish[fishId].addSpawnWeight(weight);
                            }

                            // add this spawn weight to the corresponding spawn weight list
                            if(isOcean == 0){
                                riverMonthlySpawnWeightsMap.get(timeOfDayId).add(weight);
                            } else{
                                oceanMonthlySpawnWeightsMap.get(timeOfDayId).add(weight);
                            }
                            
                            sb.append(String.format("%1$18s", fishName)).append("\t").append(spawnWeight);
//                            if(( (isOcean == 0) && acreId != 0) || ((isOcean == 1) && acreId != 6)){
//                                sb.append("\t").append(fishAcreIds[acreId]);
//                            }
                            sb.append("\n");
                            
                            lastSpawnRange = upperSpawnRange;
                            lastFishId = fishId;
                            fishIndex++;
                        }
                    }
                }
                
                result.append(sb);
                
                // add the spawn weight maps for this month to the master maps
                riverFishSpawnWeightMap.put(monthId, riverMonthlySpawnWeightsMap);
                oceanFishSpawnWeightMap.put(monthId, oceanMonthlySpawnWeightsMap);
            }
            
//            System.out.println(result);
            
            return result.toString();
            
        } catch(Exception ex){
            System.out.println("Exception " + ex);
            ex.printStackTrace();
            
            return result.toString();
        }
    }
    
    private static String parseSeaCreatureData(String elfPathStr){
        
        StringBuilder result = new StringBuilder();
        
        try{
            Path binPath = Paths.get(elfPathStr);
            
            byte[] data = null;
            try {
                data = Files.readAllBytes(binPath);
            } catch (IOException ex) {
                System.out.println("Exception while reading .elf byte array:\n" + ex);
                System.exit(0);
            }
            
            ByteBuffer bb = ByteBuffer.wrap(data);
            // little
            bb.order(ByteOrder.LITTLE_ENDIAN);
            
            for(int monthId = 0; monthId < 15; monthId++){
                StringBuilder sb = new StringBuilder();
                sb.append("\n").append(fishMonthNames[monthId]).append(":\n");
                
                // for each time of day
                for(int timeOfDayId = 0; timeOfDayId < 3; timeOfDayId++){
                    
                    sb.append("\n").append(fishTimes[timeOfDayId]).append("\n");
                        
                    long seaCreatureAddr;

                    // if we've reached the Island fish data
                    if(monthId == 14){
                        seaCreatureAddr = SEA_CREATURES_ISLAND_ADDR + (SEA_CREATURES_ISLAND_REGION_SIZE*timeOfDayId) - ADDR_OFFSET;
                    } else{
                        seaCreatureAddr =  SEA_CREATURES_ADDR + (SEA_CREATURES_REGION_SIZE * ((monthId*3) + timeOfDayId)) - ADDR_OFFSET;
                    }

                    int seaCreatureId = -1;
                    int lastSeaCreatureId = -1;
                    int seaCreatureIndex = 0;
                    int lastSpawnRange = 0;
                    while(lastSpawnRange < 100){

                        byte[] seaCreatureBytes = new byte[4];
                        bb.get((int) (seaCreatureAddr + (seaCreatureIndex * 4)), seaCreatureBytes, 0, 4);

                        // first byte is the 
                        seaCreatureId = Byte.toUnsignedInt(seaCreatureBytes[0]);
                        // checks for end of segment, to skip over the filler 0 bytes
                        if(seaCreatureId < lastSeaCreatureId){
                            lastSpawnRange = 100;
                            break;
                        }

                        // upper spawn range
                        int upperSpawnRange = ((seaCreatureBytes[3] & 0xff) << 8) | (seaCreatureBytes[2] & 0xff);

                        int spawnWeight = upperSpawnRange - lastSpawnRange;
                        
                        String seaCreatureName = (seaCreatureId == 107 ? "No Spawn" : seaCreatures[seaCreatureId - SEA_CREATURES_ID_OFFSET]);
                        
//                        System.out.println(seaCreatureName + " upperSpawnRange " + upperSpawnRange + ", lastSpawnRange " + lastSpawnRange);
                        
                        // store the percentage (/100.0) in the array at the proper place...
                        int seaCreatureArrIndex = seaCreatureId == 107 ? 30 : seaCreatureId - SEA_CREATURES_ID_OFFSET;
                        seaCreaturesBasePercentagesArray[seaCreatureArrIndex][(monthId*3) + timeOfDayId] = spawnWeight / 100.0;
                        sb.append(String.format("%1$18s", seaCreatureName)).append("\t").append(spawnWeight);
//                            if(( (isOcean == 0) && acreId != 0) || ((isOcean == 1) && acreId != 6)){
//                                sb.append("\t").append(fishAcreIds[acreId]);
//                            }
                        sb.append("\n");

                        lastSpawnRange = upperSpawnRange;
                        lastSeaCreatureId = seaCreatureId;
                        seaCreatureIndex++;
                    }
                }
                
                result.append(sb);
            }
            
//            System.out.println(result);
            
            return result.toString();
            
        } catch(Exception ex){
            System.out.println("Exception " + ex);
            ex.printStackTrace();
            
            return result.toString();
        }
    }
    
    private static String processRiverFishSpawnWeights(){
        
        StringBuilder sb = new StringBuilder();
        
        for(int monthId = 0; monthId < 15; monthId++){
            
            sb.append("\n").append(fishMonthNames[monthId]).append(":\n");
            
            Map<Integer, List<FishSpawnWeight>> monthMap = riverFishSpawnWeightMap.get(monthId);
            
            for(int timeOfDayId = 0; timeOfDayId < 3; timeOfDayId++){
                
                sb.append("\n").append(String.format("%-20s", fishTimes[timeOfDayId]))
                        .append("\tBase %\t\tShadow Based %\n");                
                
                ArrayList<FishSpawnWeight> weights = (ArrayList) monthMap.get(timeOfDayId);
                
                // first, get the total spawn weights
                double totalRiverSpawnWeight = 0;
                double riverSpawnWeight = 0;
                double lakeSpawnWeight = 0;
                double waterfallSpawnWeight = 0;
                double pondSpawnWeight = 0;
                
                // also the shadow-based spawn weights
                double SSRiverSpawnWeight = 0;
                double SSPondSpawnWeight = 0;
                double SPondSpawnWeight = 0;
                double SRiverSpawnWeight = 0;
                double MRiverSpawnWeight = 0;
                double MWaterfallSpawnWeight = 0;
                double LRiverSpawnWeight = 0;
                double LLakeSpawnWeight = 0;
                double LLRiverSpawnWeight = 0;
                double LLLakeSpawnWeight = 0;
                double LLLRiverSpawnWeight = 0;
                double LLLLakeSpawnWeight = 0;
                
                for(FishSpawnWeight fsw: weights){
                    int w = fsw.getSpawnWeight();
//                    System.out.println("weight " + w + " for fish ID " + fsw.getFishId());
                    
                    switch(fsw.getAcreId()){
                        
                        // river
                        case 0:
                            riverSpawnWeight += w;
                            break;
                        
                        // Lake
                        case 1:
                            lakeSpawnWeight += w;
                            break;
                            
                        // Waterfall
                        case 2:
                            waterfallSpawnWeight += w;
                            break;
                            
                        // Pond
                        case 3:
                            pondSpawnWeight += w;
                            break;
                    }
                    
                    switch(fish[fsw.getFishId()].getSize()){
                        // Tiny (SS)
                        case 1:
                        switch (fsw.getAcreId()) {
                            case -1:
                                break;
                            case 0:
                            default:
                                SSRiverSpawnWeight += w;
                                break;
                            case 3:
                                SSPondSpawnWeight += w;
                                break;
                            
                            }
                            break;
                            
                        // Small (S)
                        case 2:
                            switch (fsw.getAcreId()){
                                case -1:
                                    break;
                                case 0:
                                default:
                                    SRiverSpawnWeight += w;
                                    break;
                                case 3:
                                    SPondSpawnWeight += w;
                                    break;
                            }
                            
                            break;
                            
                        // Medium (M)
                        case 3:
                            switch (fsw.getAcreId()) {
                                case -1:
                                    break;
                                case 0:
                                default:
                                    MRiverSpawnWeight += w;
                                    break;
                                case 2:
                                    MWaterfallSpawnWeight += w;
                                    break;
                                }
                            break;
                            
                        // Large (L)
                        case 4:
                            switch (fsw.getAcreId()) {
                                case -1:
                                    break;
                                case 0:
                                default:
                                    LRiverSpawnWeight += w;
                                    break;
                                case 1:
                                    LLakeSpawnWeight += w;
                                    break;
                                }
                            break;
                        // Extra Large (LL)
                        case 5:
                            if(fsw.getAcreId() == 0){
                                LLRiverSpawnWeight += w;
                            } else if(fsw.getAcreId() == 1){
                                LLLakeSpawnWeight += w;
                            }
                            break;
                            
                        // Huge (LLL)
                        case 6:
                            if(fsw.getAcreId() == 0){
                                LLLRiverSpawnWeight += w;
                            } else if(fsw.getAcreId() == 1){
                                LLLLakeSpawnWeight += w;
                            }
                            break;
                    }
                }
                
                // seems like all spawn weights matter when determining river fish... so why did I separate them...
                totalRiverSpawnWeight = riverSpawnWeight + lakeSpawnWeight + waterfallSpawnWeight + pondSpawnWeight;
//                System.out.println("totalRiverSpawnWeight: " + totalRiverSpawnWeight);
                
                // then, calculate the percentage for each individual fish
                for(FishSpawnWeight fsw: weights){
                    sb.append(String.format("%1$20s", fish[fsw.getFishId()].getName())).append("\t")
                            .append(String.format("%.1f", (100.0 * (fsw.getSpawnWeight() / totalRiverSpawnWeight))));
//                            .append("%");
                    
                    // handle the shadow-based %
                    double totalWeightToUse = 0;
                    
                    switch(fish[fsw.getFishId()].getSize()){
                        // Tiny (SS)
                        case 1:
                        switch (fsw.getAcreId()) {
                            case 0:
                            default:
                                totalWeightToUse = SSRiverSpawnWeight;
                                break;
                            case 3:
                                totalWeightToUse = SSPondSpawnWeight;
                                break;
                            }
                            break;

                            
                        // Small (S)
                        case 2:
                            switch (fsw.getAcreId()) {
                                case 0:
                                default:
                                    totalWeightToUse = SRiverSpawnWeight;
                                    break;
                                case 3:
                                    totalWeightToUse = SPondSpawnWeight;
                                    break;
                            }
                            break;
                            
                        // Medium (M)
                        case 3:
                        switch (fsw.getAcreId()) {
                            case 0:
                            default:
                                totalWeightToUse = MRiverSpawnWeight;
                                break;
                            case 2:
                                // In this game, no other fish besides char can spawn in the waterfall acre?
                                totalWeightToUse = MWaterfallSpawnWeight;
                                break;
                        }
                            break;

                            
                        // Large (L)
                        case 4:
                            switch (fsw.getAcreId()) {
                                case 0:
                                default:
                                    totalWeightToUse = LRiverSpawnWeight;
                                    break;
                                case 1:
                                    totalWeightToUse = LRiverSpawnWeight + LLakeSpawnWeight;
                            }
                            break;
                            
                        // Extra Large (LL)
                        case 5:
                            if(fsw.getAcreId() == 0){
                                totalWeightToUse = LLRiverSpawnWeight;
                            } else if(fsw.getAcreId() == 1){
                                totalWeightToUse = LLRiverSpawnWeight + LLLakeSpawnWeight;
                            }
                            break;
                            
                        // Huge (LLL)
                        case 6:
                            if(fsw.getAcreId() == 0){
                                totalWeightToUse = LLLRiverSpawnWeight;
                            } else if(fsw.getAcreId() == 1){
                                totalWeightToUse = LLLRiverSpawnWeight + LLLLakeSpawnWeight;
                            }
                            break;
                            
                        // Eel (lol)
                        case 8:
                            totalWeightToUse = fsw.getSpawnWeight();
                            break;
                    }
                    
                    double shadowBasedPercent = (100.0 * (fsw.getSpawnWeight() / totalWeightToUse));
                    
                    sb.append("\t\t").append(String.format("%.2f", shadowBasedPercent));
                            
                    if(fsw.getAcreId() != 0){
                        sb.append("\t").append(fishAcreIds[fsw.getAcreId()]);
                    }
                    sb.append("\n");
                    
                    // add the fish spawn weight and shadow based % to the appropriate entry in the 2d arrays
                    int colIndex = (monthId * 3) + timeOfDayId;
                    
                    fishSpawnWeightArray[fsw.getFishId()][colIndex] = 1.0*fsw.getSpawnWeight();
                    fishShadowBasedArray[fsw.getFishId()][colIndex] = shadowBasedPercent;
                }
            }
        }
        
        return sb.toString();
    }
    
    private static String processOceanFishSpawnWeights(){
        
        StringBuilder sb = new StringBuilder();
        
        for(int monthId = 0; monthId < 15; monthId++){
            
            sb.append("\n").append(fishMonthNames[monthId]).append(":\n");
            
            Map<Integer, List<FishSpawnWeight>> monthMap = oceanFishSpawnWeightMap.get(monthId);
            
            for(int timeOfDayId = 0; timeOfDayId < 3; timeOfDayId++){
                
                sb.append("\n").append(String.format("%-20s", fishTimes[timeOfDayId]))
                        .append("\tBase %\t\tShadow Based %\n");                
                
                ArrayList<FishSpawnWeight> weights = (ArrayList) monthMap.get(timeOfDayId);
                
                // first, get the total spawn weights
                double totalOceanSpawnWeight = 0;
                double oceanSpawnWeight = 0;
                double oceanRainSpawnWeight = 0;
                double riverMouthSpawnWeight = 0;
                
                // also the shadow-based spawn weights
                double SSSpawnWeight = 0;
                double SSpawnWeight = 0;
                double MSpawnWeight = 0;
                double LOceanSpawnWeight = 0;
                double LRiverMouthSpawnWeight = 0;
                double LLSpawnWeight = 0;
                double LLLOceanSpawnWeight = 0;
                double LLLOceanRainSpawnWeight = 0;
                double LLLRiverMouthSpawnWeight = 0;
                double finSpawnWeight = 0;
                double size8SpawnWeight = 0;
                
                for(FishSpawnWeight fsw: weights){
                    int w = fsw.getSpawnWeight();
                    int fishId = fsw.getFishId();
                    int fishSize = (fishId == 107 ? -1 : fish[fishId].getSize());
//                    System.out.println("weight " + w + " for fish ID " + fsw.getFishId());
                    
                    switch(fsw.getAcreId()){
                        
                        // River Mouth
                        case 4:
                            riverMouthSpawnWeight += w;
                            break;
                        
                        // Ocean
                        case 5:
                            oceanSpawnWeight += w;
                            break;
                            
                        // Ocean Rain
                        case 6:
                            oceanRainSpawnWeight += w;
                            break;
                    }
                    
                    switch(fishSize){
                        // Tiny (SS)
                        case 1:
                            SSSpawnWeight += w;
                            break;
                            
                        // Small (S)
                        case 2:
                            SSpawnWeight += w;
                            break;
                            
                        // Medium (M)
                        case 3:
                            MSpawnWeight += w;
                            break;
                            
                        // Large (L)
                        case 4:
                            if(fsw.getAcreId() == 4){
                                LRiverMouthSpawnWeight += w;
                            } else{
                                LOceanSpawnWeight += w;
                            }
                            break;
                            
                        // Extra Large (LL)
                        case 5:
                            LLSpawnWeight += w;
                            break;
                            
                        // Huge (LLL)
                        case 6:
                            if(fsw.getAcreId() == 4){
                                LLLRiverMouthSpawnWeight += w;
                            } else if(fsw.getAcreId() == 5){
                                // don't include Coelancanth in shadow-based percentages...
                                LLLOceanSpawnWeight += w;
                            } else if(fsw.getAcreId() == 6){
                                LLLOceanRainSpawnWeight += w;
                            }
                            break;
                            
                        // Sharks (finned)
                        case 7:
                            finSpawnWeight += w;
                            break;
                            
                        // ribbon eel
                        case 8:
                            size8SpawnWeight += w;
                            break;
                    }
                }
                
                // combine the spawn weights
                totalOceanSpawnWeight = oceanSpawnWeight + oceanRainSpawnWeight + riverMouthSpawnWeight;
//                System.out.println("totalOceanSpawnWeight: " + totalOceanSpawnWeight);
                
                // then, calculate the percentage for each individual fish
                for(FishSpawnWeight fsw: weights){
                    
                    int fishId = fsw.getFishId();
                    int fishSize = (fishId == 107 ? -1 : fish[fishId].getSize());
                    
                    String fishName = fsw.getFishId() == 107 ? "No Spawn" : fish[fsw.getFishId()].getName();
                    sb.append(String.format("%1$20s", fishName)).append("\t")
                            .append(String.format("%.1f", (1.0 * fsw.getSpawnWeight())));
                    // in NL also, the total spawn weight does not always add up to 100 - but the game can roll a "no spawn".
//                            .append(String.format("%.1f", (100.0 * (fsw.getSpawnWeight() / totalOceanSpawnWeight))));
//                            .append("%");
                    
                    // handle the shadow-based %
                    double totalWeightToUse = 0;
                    
                    switch(fishSize){
                        // Tiny (SS)
                        case 1:
                            totalWeightToUse = SSSpawnWeight;
                            break;
                            
                        // Small (S)
                        case 2:
                            totalWeightToUse = SSpawnWeight;
                            break;
                            
                        // Medium (M)
                        case 3:
                            totalWeightToUse = MSpawnWeight;
                            break;
                            
                        // Large (L)
                        case 4:
                            if(fsw.getAcreId() == 4){
                                totalWeightToUse = LRiverMouthSpawnWeight + LOceanSpawnWeight;
                            } else{
                                totalWeightToUse = LOceanSpawnWeight;
                            }
                            break;
                            
                        // Extra Large (LL)
                        case 5:
                            totalWeightToUse = LLSpawnWeight;
                            break;
                            
                        // Huge (LLL)
                        case 6:
                            if(fsw.getAcreId() == 4){
                                totalWeightToUse = LLLRiverMouthSpawnWeight + LLLOceanSpawnWeight;
                            } else if(fsw.getAcreId() == 5){
                                totalWeightToUse = LLLOceanSpawnWeight;
                            } else if(fsw.getAcreId() == 6){
                                totalWeightToUse = LLLOceanSpawnWeight + LLLOceanRainSpawnWeight;
                            }
                            break;
                            
                        // Sharks (finned)
                        case 7:
                            totalWeightToUse = finSpawnWeight;
                            break;
                            
                        // Ribbon Eel
                        case 8:
                            totalWeightToUse = fsw.getSpawnWeight();
                            break;
                            
                        // So "No Spawn" doesn't put Infinity in the sheet
                        case -1:
                            totalWeightToUse = -1.0;
                            break;
                    }
                    
                    double shadowBasedPercent = (100.0 * (fsw.getSpawnWeight() / totalWeightToUse));
                    
                    sb.append("\t\t").append(String.format("%.2f", shadowBasedPercent));
                            
                    if(fsw.getAcreId() == 4){
                        sb.append("\t").append(fishAcreIds[fsw.getAcreId()]);
                    } else if(fsw.getAcreId() == 6){
                        sb.append("\t").append("Rain/Snow");
                    }
                    sb.append("\n");
                    
                    // add the fish spawn weight and shadow based % to the appropriate entry in the 2d arrays
                    int colIndex = (monthId * 3) + timeOfDayId;
                    
                    int idRow = fishId == 107 ? 75 : fishId;
                    
                    fishSpawnWeightArray[idRow][colIndex] = 1.0*fsw.getSpawnWeight();
                    fishShadowBasedArray[idRow][colIndex] = shadowBasedPercent;
                }
            }
        }
        
        return sb.toString();
    }
    
    static class MonthPair{
        String month;
        String address;
        
        public MonthPair(String m, String addr){
            this.month = m;
            this.address = addr;
        }
        
        public String getMonth(){
            return month;
        }
        
        public String getAddress(){
            return address;
        }
    }
    
    private static void writeFishToCsv(){
        
        // Regular percentages
        try (PrintWriter pw = new PrintWriter(fishPercentagesCsv)) {
            for(int fishIndex = 0; fishIndex < fishSpawnWeightArray.length; fishIndex++){
                String csvLine = getCsvLineFromDoubles(fishSpawnWeightArray[fishIndex]);
                pw.println(csvLine);
            }
        } catch(Exception ex){
            System.out.println("Exception writing bug percentages to .csv");
            ex.printStackTrace();
        }
        
        // Shadow-based percentages
        try (PrintWriter pw = new PrintWriter(fishShadowPercentagesCsv)) {
            for(int fishIndex = 0; fishIndex < fishShadowBasedArray.length; fishIndex++){
                String csvLine = getCsvLineFromDoubles(fishShadowBasedArray[fishIndex]);
                pw.println(csvLine);
            }
        } catch(Exception ex){
            System.out.println("Exception writing bug percentages to .csv");
            ex.printStackTrace();
        }
    }
    
    private static void writeBugsToCsv(){
        try (PrintWriter pw = new PrintWriter(bugPercentagesCsv)) {
            for(int bugIndex = 0; bugIndex < bugBasePercentagesArray.length; bugIndex++){
                String csvLine = getCsvLineFromDoubles(bugBasePercentagesArray[bugIndex]);
                pw.println(csvLine);
            }
        } catch(Exception ex){
            System.out.println("Exception writing bug percentages to .csv");
            ex.printStackTrace();
        }
    }
    
    private static void writeSeaCreaturesToCsv(){
        try (PrintWriter pw = new PrintWriter(seaCreaturePercentagesCsv)) {
            for(int seaCreatureIndex = 0; seaCreatureIndex < seaCreaturesBasePercentagesArray.length; seaCreatureIndex++){
                String csvLine = getCsvLineFromDoubles(seaCreaturesBasePercentagesArray[seaCreatureIndex]);
                pw.println(csvLine);
            }
        } catch(Exception ex){
            System.out.println("Exception printing writing sea creature percentages to .csv");
            ex.printStackTrace();
        }
    }
    
    private static String getCsvLine(String[] data){
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }
    
    private static String getCsvLineFromDoubles(Double[] data){
        return Stream.of(data)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
