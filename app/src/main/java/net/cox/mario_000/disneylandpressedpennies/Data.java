package net.cox.mario_000.disneylandpressedpennies;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mario_000 on 6/25/2016.
 */
public interface Data
{
    // Coins
    // Params - String Title of Coin, String Image of Coin, Date date collected

    // DISNEYLAND

    // MAIN STREET COINS

    Coin[] pennyArcadeQuarter = new Coin[]{
            new Coin( "Peter Pan - Walt Quote: \"Leave Today\"", "penny_arcade_peter_pan", null ),
            new Coin( "Mickey - Walt Quote: \"Do The Impossible\"", "penny_arcade_mickey", null ),
            new Coin( "Castle - Walt Quote: \"If You Can Dream It\"", "penny_arcade_castle", null )
    };
    Coin[] pennyArcade2Penny = new Coin[]{
            new Coin( "Miguel and Dante", "penny_arcade_2_coco", null ),
            new Coin( "Remy and Linguini", "penny_arcade_2_delicious", null ),
            new Coin( "Woody and Buzz", "penny_arcade_2_toy_story", null )
    };

    Coin[] retiredPennyArcade2Penny = new Coin[]{
            new Coin( "Main Street Electrical Parade America", "penny_arcade_2_america", null ),
            new Coin( "Main Street Electrical Parade Pete's Dragon", "penny_arcade_2_dragon", null ),
            new Coin( "Main Street Electrical Parade Mushroom", "penny_arcade_2_butterfly", null )
    };
    Coin[] retiredPennyArcade3Nickel = new Coin[]{
            new Coin( "Minnie & Mickey Happy Holidays 2017", "penny_arcade_3_mickey_2017", null ),
            new Coin( "Donald Season's Greetings 2017", "penny_arcade_3_santa_donald", null ),
            new Coin( "Tinker Bell Happy New Year 2018", "penny_arcade_3_tinkerbell_2018", null )
    };
    Coin[] operaExit1Penny = new Coin[]{
            new Coin( "Fantasyland Tinker Bell", "opera_1_bell", null ),
            new Coin( "Toontown Mickey", "opera_1_mickey", null ),
            new Coin( "Adventureland Donald", "opera_1_donald", null )
    };

    Coin[] operaExit2Penny = new Coin[]{
            new Coin( "Pirate Goofy", "opera_2_goofy", null ),
            new Coin( "Mickey Frontierland", "opera_2_mickey", null ),
            new Coin( "Pluto Tomorrowland", "opera_2_pluto", null )
    };

    Coin[] operaExit3Penny = new Coin[]{
            new Coin( "Main Street USA", "opera_3_main_street", null ),
            new Coin( "Let The Memories Begin", "opera_3_memories", null ),
            new Coin( "Br'er Fox Critter Country", "opera_3_fox", null )
    };

    Coin[] retiredOperaExit4Nickel = new Coin[]{
            new Coin( "Gibson Girl Ice Cream", "opera_4_gibson", null ),
            new Coin( "Jolly Holiday Bakery Cafe", "opera_4_jolly_holiday", null ),
            new Coin( "Little Red Wagon Corn Dogs", "opera_4_corn_dogs", null )
    };

    Coin[] oldOperaExit4Nickel = new Coin[]{
            new Coin( "2016 Goofy", "old_opera_4_goofy", null ),
            new Coin( "2016 Pluto", "old_opera_4_pluto", null ),
            new Coin( "2016 Donald", "old_opera_4_donald", null )
    };

    Coin[] operaExit4Nickel = new Coin[]{
            new Coin( "2018 The Walt Disney Story", "opera_exit_4_walt_story", null ),
            new Coin( "2018 Main Street Penny Arcade", "opera_exit_4_penny_arcade", null ),
            new Coin( "2018 Main Street Cinema", "opera_exit_4_cinema", null )
    };

    Coin[] fairyTaleTreasureQuarter = new Coin[]{
            new Coin( "Kristoff, Sven, Olaf", "fairy_tale_frozen", null ),
            new Coin( "Elsa & Anna", "fairy_tale_sisters", null ),
            new Coin( "Olaf", "fairy_tale_olaf", null )
    };

    //FANTASYLAND COINS

    Coin[] royalReceptionQuarter = new Coin[]{
            new Coin( "Beauty and the Beast", "royal_reception_beauty_beast", null ),
            new Coin( "Friends of Belle", "royal_reception_friends", null ),
            new Coin( "Belle", "royal_reception_belle", null )
    };

    Coin[] smallWorldQuarter = new Coin[]{
            new Coin( "Small World", "small_world_toy_shop_attraction", null ),
            new Coin( "Small World Clock", "small_world_toy_shop_clock", null ),
            new Coin( "Small World Dolls", "small_world_toy_shop_dolls", null )
    };

    //TOONTOWN COINS

    Coin[] gagFactoryDime = new Coin[]{
            new Coin( "Baby Goofy", "gag_factory_goofy", null ),
            new Coin( "Baby Mickey and Pluto", "gag_factory_pluto_mickey", null ),
            new Coin( "Baby Minnie Mouse", "gag_factory_minnie", null )
    };

    //ADVENTURELAND COINS

    Coin[] bazaar1Penny = new Coin[]{
            new Coin( "Minnie Jungle Cruise", "bazaar_1_minnie", null ),
            new Coin( "Jungle Cruise Boat", "bazaar_1_jungle_boat", null ),
            new Coin( "Mickey Jungle Cruise", "bazaar_1_mickey", null )
    };

    Coin[] bazaar2Penny = new Coin[]{
            new Coin( "Indiana Jones Hat and Whip", "bazaar_2_hat", null ),
            new Coin( "Indiana Jones Logo", "bazaar_2_indiana_jones", null ),
            new Coin( "Indy Crossed Swords, Hat, and Whip", "bazaar_2_swords", null )
    };

    Coin[] bazaar3Penny = new Coin[]{
            new Coin( "Moana", "bazaar_3_moana", null ),
            new Coin( "Maui", "bazaar_3_maui", null ),
            new Coin( "Pua", "bazaar_3_pua", null )
    };

    //FRONTIERLAND COINS

    Coin[] pioneerPenny = new Coin[]{
            new Coin( "Cowboy Donald", "pioneer_donald", null ),
            new Coin( "Cowgirl Minnie", "pioneer_minnie", null ),
            new Coin( "Mickey On Horseback", "pioneer_mickey", null )
    };

    Coin[] bonanzaPenny = new Coin[]{
            new Coin( "Cowboy Donald #2", "bonanza_donald", null ),
            new Coin( "Cowboy Mickey", "bonanza_mickey", null ),
            new Coin( "Cowboy Goofy", "bonanza_goofy", null )
    };

    Coin[] bonanzaSidewalkPenny = new Coin[]{
            new Coin( "Edna & Jack Jack", "bonanza_sidewalk_edna", null ),
            new Coin( "Russel & Dug", "bonanza_sidewalk_russel_dug", null ),
            new Coin( "Marlin & Nemo", "bonanza_sidewalk_marlin_nemo", null )
    };

    Coin[] retiredBonanzaSidewalkPenny = new Coin[]{
            new Coin( "Mickey at Rivers of America", "bonanza_sidewalk_rivers", null ),
            new Coin( "Fly Fisherman Mickey", "bonanza_sidewalk_fisherman", null ),
            new Coin( "Mark Twain Riverboat", "bonanza_sidewalk_mark_twain", null )
    };

    Coin[] westwardHoPenny = new Coin[]{
            new Coin( "Big Thunder Train \"I.M. LOCO\"", "westward_ho_loco", null ),
            new Coin( "Big Thunder Train \"U.B. BOLD\"", "westward_ho_bold", null ),
            new Coin( "Big Thunder Train \"U.R. DARING\"", "westward_ho_daring", null )
    };

    //NEW ORLEANS SQUARE COINS

    Coin[] piecesOfEightPenny = new Coin[]{
            new Coin( "Dead Men Tell No Tales - Anchor", "pieces_of_eight_anchor", null ),
            new Coin( "Pirates of the Caribbean - Skull", "pieces_of_eight_skull", null ),
            new Coin( "Pirate Face & Crossed Swords", "pieces_of_eight_face", null )
    };

    Coin[] portRoyalQuarter = new Coin[]{
            new Coin( "Haunted Mansion Banner", "port_royal_banner", null ),
            new Coin( "Haunted Mansion Logo", "port_royal_logo", null ),
            new Coin( "Haunted Mansion Ride", "port_royal_ride", null )
    };

    //CRITTER COUNTRY

    Coin[] poohCorner1Penny = new Coin[]{
            new Coin( "Winnie the Pooh", "pooh_corner_1_pooh", null ),
            new Coin( "Tigger", "pooh_corner_1_tigger", null ),
            new Coin( "Rabbit", "pooh_corner_1_rabbit", null )
    };

    Coin[] poohCorner2Penny = new Coin[]{
            new Coin( "Pooh & Piglet", "pooh_corner_2_pooh", null ),
            new Coin( "Pooh & Hunny", "pooh_corner_2_hunny", null ),
            new Coin( "Pooh & Butterfly", "pooh_corner_2_butterfly", null )
    };

    Coin[] splashExitPenny = new Coin[]{
            new Coin( "Mickey", "splash_exit_mickey", null ),
            new Coin( "Br'er Bear", "splash_exit_brer_bear", null ),
            new Coin( "Splash Mountain Ride", "splash_exit_ride", null )
    };


    //TOMORROWLAND COINS

    Coin[] buzzExit1Penny = new Coin[]{
            new Coin( "Astro Orbitor", "buzz_exit_1_astro", null ),
            new Coin( "Space Mountain Logo", "buzz_exit_1_space", null ),
            new Coin( "Monorail", "buzz_exit_1_monorail", null )
    };

    Coin[] retiredBuzzExit1Penny = new Coin[]{
            new Coin( "Buzz Lightyear #1", "buzz_exit_1_buzz_retired", null ),
            new Coin( "Little Green Men #1", "buzz_exit_1_lgm_retired", null ),
            new Coin( "Zurg #1", "buzz_exit_1_zurg_retired", null )
    };

    Coin[] buzzExit2Penny = new Coin[]{
            new Coin( "Buzz Lightyear #2", "buzz_exit_2_buzz", null ),
            new Coin( "Little Green Men #2", "buzz_exit_2_lgm", null ),
            new Coin( "Zurg #2", "buzz_exit_2_zurg", null )
    };

    Coin[] starTraderExit1Quarter = new Coin[]{
            new Coin( "The Rebel Force Logo", "star_trader_1_logo", null ),
            new Coin( "The Rebel Force Rey", "star_trader_1_rey", null ),
            new Coin( "The Rebel Force Chewbacca", "star_trader_1_chewbacca", null )
    };

    Coin[] starTraderExit2Quarter = new Coin[]{
            new Coin( "The First Order Kylo Ren", "star_trader_2_kylo", null ),
            new Coin( "The First Order Logo", "star_trader_2_logo", null ),
            new Coin( "The First Order Storm Trooper", "star_trader_2_phasma", null )
    };


    // CALIFORNIA ADVENTURE


    // BUENA VISTA STREET

    Coin[] kingswellPenny = new Coin[]{
            new Coin( "Dory", "kingswell_dory", null ),
            new Coin( "Destiny the Whale Shark & Dory", "kingswell_destiny", null ),
            new Coin( "Hank and Dory", "kingswell_hank", null )
    };

    Coin[] kingswell2NickelOld = new Coin[]{
            new Coin( "Alien Ornaments Season's Greetings 2017", "kingswell_2_alien_2017", null ),
            new Coin( "Tow Mater Holiday Cheer 2017", "kingswell_2_tow_mater_2017", null ),
            new Coin( "McQueen 95 Happy New Year 2018", "kingswell_2_mcqueen_2018", null )
    };

    Coin[] katzPenny = new Coin[]{
            new Coin( "World of Color", "katz_world_of_color", null ),
            new Coin( "The Little Mermaid Adventure", "katz_little_mermaid", null ),
            new Coin( "Midway Mania!", "katz_midway", null )
    };

    Coin[] kingswell2Penny = new Coin[]{
            new Coin( "Red Car Trolley", "trolley_1_red_car", null ),
            new Coin( "Mickey Mouse on Trolley", "trolley_1_mickey", null ),
            new Coin( "Disney California Adventure", "trolley_1_california", null )
    };

    Coin[] ramonesPenny = new Coin[]{
            new Coin( "Cars Land", "ramones_cars", null ),
            new Coin( "Radiator Springs Chamber of Commerce", "ramones_chamber_com", null ),
            new Coin( "Champions Speed Shop", "ramones_champions_shop", null )
    };

    Coin[] retiredTrolley2Nickel = new Coin[]{
            new Coin( "2017 Pacific Wharf", "trolley_treats_2_pacific_wharf", null ),
            new Coin( "2017 California Screamin'", "trolley_treats_2_screamin", null ),
            new Coin( "2017 Bug's Land", "trolley_treats_2_bugs_land", null )
    };

    Coin[] trolleyNickel = new Coin[]{
            new Coin( "2018 Grizzly Peak", "trolley_treats_grizzly", null ),
            new Coin( "2018 Mickey's Fun Wheel", "trolley_treats_mickey_fun_wheel", null ),
            new Coin( "2018 Red Car Trolley", "trolley_treats_red_car_trolley", null )
    };

    Coin[] fiveDime1Penny = new Coin[]{
            new Coin( "Walt & Mickey Storytellers", "five_dime_1_storytellers", null ),
            new Coin( "Oswald", "five_dime_1_oswald", null ),
            new Coin( "Carthay Circle Theater", "five_dime_1_carthay", null )
    };

    Coin[] fiveDime2Penny = new Coin[]{
            new Coin( "Radiator Springs Racers", "five_dime_2_racers", null ),
            new Coin( "Luigi's Rollickin' Roadsters", "five_dime_2_luigis", null ),
            new Coin( "Mater's Junkyard Jamboree", "five_dime_2_maters", null )
    };

    Coin[] bigTopPenny = new Coin[]{
            new Coin( "Mickey and Minnie Out for a Drive", "big_top_driving", null ),
            new Coin( "Pie Eyed Mickey Red Car Trolley Logo", "big_top_logo", null ),
            new Coin( "Mickey Buena Vista Street", "big_top_welcomes", null )
    };

//    // HOLLYWOOD LAND

    Coin[] offPagePenny = new Coin[]{
            new Coin( "Low N Slow Car Club", "off_the_page_low_slow", null ),
            new Coin( "Mickey and Minnie at Carthay Circle", "off_the_page_carthay", null ),
            new Coin( "Goofy's Sky School", "off_the_page_goofys", null )
    };

    Coin[] studioStoreDimeOld = new Coin[]{
            new Coin( "Anna", "studio_anna", null ),
            new Coin( "Snowman Olaf", "studio_olaf", null ),
            new Coin( "Elsa", "studio_elsa", null )
    };

    Coin[] goneHollyWoodQuarter = new Coin[]{
            new Coin( "Star Wars Luke", "gone_hollywood_luke", null ),
            new Coin( "Star Wars Han Solo", "gone_hollywood_han", null ),
            new Coin( "Star Wars C3P0 and R2D2", "gone_hollywood_c3po", null )
    };

    Coin[] goneHollyWood2Quarter = new Coin[]{
            new Coin( "Star Wars Yoda", "gone_hollywood_2_yoda", null ),
            new Coin( "Star Wars Leia", "gone_hollywood_2_leia", null ),
            new Coin( "Star Wars Luke and Vader", "gone_hollywood_2_luke_vader", null )
    };

    Coin[] goneHollyWood3Quarter = new Coin[]{
            new Coin( "Captain America", "gone_hollywood_3_captain_america", null ),
            new Coin( "Black Widow", "gone_hollywood_3_black_widow", null ),
            new Coin( "Spiderman", "gone_hollywood_3_spiderman", null )
    };

    Coin[] collectorsGifts = new Coin[]{
            new Coin( "Peter Quill - Star Lord", "collectors_gifts_peter", null ),
            new Coin( "Pet Rodent Rocket", "collectors_gifts_rocket", null ),
            new Coin( "Flora Colossus Groot", "collectors_gifts_groot", null )
    };


//    // GRIZZLY PEAK AIRFIELD

    Coin[] humphreyPenny = new Coin[]{
            new Coin( "Pilot Mickey Porthole", "humphreys_pilot", null ),
            new Coin( "Pilot Mickey Waving", "humphreys_waving", null ),
            new Coin( "Soarin' Logo Over Mickey", "humphreys_soarin", null )
    };

//    // GRIZZLY PEAK RECREATIONAL AREA

    Coin[] rushinRiverQuarter = new Coin[]{
            new Coin( "Russell", "rushin_russell", null ),
            new Coin( "Dug", "rushin_dug", null ),
            new Coin( "UP House", "rushin_house", null )
    };

    // PIXAR PIER

    Coin[] knicksKnacksPenny = new Coin[]{
            new Coin( "Mr. Incredible", "knicks_knacks_mr_incredible", null ),
            new Coin( "Elastigirl", "knicks_knacks_mrs_incredible", null ),
            new Coin( "Violet and Dash", "knicks_knacks_violet_dash", null )
    };

    Coin[] bingBongPenny = new Coin[]{
            new Coin( "Joy", "bing_bong_joy", null ),
            new Coin( "Bing Bong", "bing_bong_bing_bong", null ),
            new Coin( "Anger", "bing_bong_anger", null )
    };

    // PARADISE PIER -- RETIRED

    Coin[] retiredTreasuresPenny = new Coin[]{
            new Coin( "Duffy the Disney Bear", "treasures_duffy", null ),
            new Coin( "Mickey and Duffy", "treasures_mickey_duffy", null ),
            new Coin( "Bandleader Mickey", "treasures_bandleader", null )
    };

    Coin[] retiredSideshowPenny = new Coin[]{
            new Coin( "Angel", "sideshow_angel", null ),
            new Coin( "Stitch & Angel heart", "sideshow_stitch_angel", null ),
            new Coin( "Stitch", "sideshow_stitch", null )
    };


    // DOWNTOWN DISNEY


    // WORLD OF DISNEY

    Coin[] WodCenterDoorPenny = new Coin[]{
            new Coin( "Mickey Mouse", "wod_center_mickey", null ),
            new Coin( "Dancing Goofy", "wod_center_goofy", null ),
            new Coin( "Donald Duck", "wod_center_donald", null )
    };

    Coin[] WodEastDoorPenny = new Coin[]{
            new Coin( "Pooh", "wod_east_pooh", null ),
            new Coin( "Dumbo", "wod_east_dumbo", null ),
            new Coin( "Pinocchio", "wod_east_pinocchio", null )
    };

    // ANNA & ELSA'S

    Coin[] annaElsaNickelOld = new Coin[]{
            new Coin( "Snow White", "frozen_shop_snow_white", null ),
            new Coin( "Cinderella", "frozen_shop_cinderella", null ),
            new Coin( "Ariel", "frozen_shop_ariel", null )
    };

    // RAINFOREST CAFE

    Coin[] rainforestLobby1Penny = new Coin[]{
            new Coin( "Tree Frog Logo", "rainforest_1_frog", null ),
            new Coin( "Hippo", "rainforest_1_hippo", null ),
            new Coin( "Lioness and Cub", "rainforest_1_lion", null ),
            new Coin( "Snake in Tree", "rainforest_1_snake", null )
    };

    Coin[] rainforestLobby2Penny = new Coin[]{
            new Coin( "Tree Frog", "rainforest_2_frog", null ),
            new Coin( "Angel Fish", "rainforest_2_fish", null ),
            new Coin( "Parrot", "rainforest_2_parrot", null ),
            new Coin( "Snake", "rainforest_1_snake", null )
    };

    Coin[] rainforestLobby3Quarter = new Coin[]{
            new Coin( "Rhino", "rainforest_3_rhino", null ),
            new Coin( "Aye-aye", "rainforest_3_aye", null ),
            new Coin( "Leopard", "rainforest_3_leopard", null ),
            new Coin( "Lemur", "rainforest_3_lemur", null )
    };

    // ESPN ZONE

    Coin[] espnShopPenny = new Coin[]{
            new Coin( "ESPN", "espn_logo", null ),
            new Coin( "ANGELS \"A\" Logo", "espn_angels", null ),
            new Coin( "ESPN ZONE", "espn_zone", null ),
            new Coin( "DODGERS", "espn_dodgers", null )
    };

    // MONORAIL

    Coin[] wetzelPenny = new Coin[]{
            new Coin( "Downtown Disney Tinker Bell", "wetzels_tinkerbell", null ),
            new Coin( "Downtown Disney Pirate Mickey", "wetzels_pirate_mickey", null ),
            new Coin( "Downtown Disney Captain Hook", "wetzels_hook", null ),
            new Coin( "Downtown Disney Sorcerer Mickey", "wetzels_mickey", null )
    };

    // APRICOT LANE SHOP

    Coin[] tortillaPenny = new Coin[]{
            new Coin( "Downtown Disney Donald Duck", "tortilla_donald", null ),
            new Coin( "Downtown Disney Pirate Mickey #2", "tortilla_pirate_mickey", null ),
            new Coin( "Downtown Disney Artist Minnie", "tortilla_minnie", null ),
            new Coin( "Downtown Disney Lightning McQueen", "tortilla_mcqueen", null )
    };

    // GRAND CALIFORNIAN

    Coin[] whiteWaterPenny = new Coin[]{
            new Coin( "Chip N Dale", "white_water_chip_dale", null ),
            new Coin( "Goofy Fishing", "white_water_goofy", null ),
            new Coin( "Mickey Air Travel", "white_water_mickey", null )
    };

    // DISNEYLAND HOTEL

    Coin[] fantasiaPenny = new Coin[]{
            new Coin( "Mad Donald", "fantasia_donald", null ),
            new Coin( "Goofy", "fantasia_goofy", null ),
            new Coin( "Proud Mickey", "fantasia_mickey", null )
    };

    Coin[] goofysKitchen1Penny = new Coin[]{
            new Coin( "Bellman Mickey", "goofys_kitchen_1_mickey", null ),
            new Coin( "Chef Goofy", "goofys_kitchen_1_goofy", null ),
            new Coin( "Stitch with Luggage", "goofys_kitchen_1_stitch", null )
    };

    Coin[] goofysKitchen2Penny = new Coin[]{
            new Coin( "Remy", "goofys_kitchen_2_remy", null ),
            new Coin( "Colette and Linguini", "goofys_kitchen_2_linguini", null ),
            new Coin( "Ratatouille", "goofys_kitchen_2_ratatouille", null )
    };

    // PARADISE PIER HOTEL

    Coin[] paradiseHotelPenny = new Coin[]{
            new Coin( "Minnie Splashing Mickey", "paradise_hotel_minnie", null ),
            new Coin( "Paradise Pier Hotel Logo", "paradise_hotel_logo", null ),
            new Coin( "Mickey on the Beach", "paradise_hotel_mickey", null )
    };


    // MACHINES
    // Params - String land, String machineName, String typeCoin, String machineImg, String backstampImg, String coinPreview, Coin[] coins, LatLng location

    // DISNEYLAND

    Machine[] mainCoins = new Machine[]{
            new Machine( "Main Street", "Penny Arcade #1", "Penny", "penny_arcade_mac", "penny_arcade_backstamp", "penny_arcade_preview", pennyArcadeQuarter, new LatLng( 33.811262, -117.919002 ) ),
            new Machine( "Main Street", "Penny Arcade #2", "Penny", "penny_arcade_2_mac", "pixar_fest_backstamp", "penny_arcade_2_preview", pennyArcade2Penny, new LatLng( 33.811262, -117.919072 ) ),
            new Machine( "Main Street", "Opera House Exit #1", "Penny", "opera_1_mac", "opera_backstamp", "opera_1_preview", operaExit1Penny, new LatLng( 33.810270, -117.918520 ) ),
            new Machine( "Main Street", "Opera House Exit #2", "Penny", "opera_2_mac", "opera_backstamp", "opera_2_preview", operaExit2Penny, new LatLng( 33.810270, -117.918490 ) ),
            new Machine( "Main Street", "Opera House Exit #3", "Penny", "opera_3_mac", "opera_backstamp", "opera_3_preview", operaExit3Penny, new LatLng( 33.810270, -117.918460 ) ),
            new Machine( "Main Street", "Opera House Exit #4", "Nickel", "opera_4_mac", "opera_exit_4_backstamp", "opera_4_preview", operaExit4Nickel, new LatLng( 33.810270, -117.918430 ) ),
            new Machine( "Main Street", "Fairy Tale Treasures", "Quarter", "fairy_tale_mac", "fairy_tale_backstamp", "fairy_tale_treasures_preview", fairyTaleTreasureQuarter, new LatLng( 33.812617, -117.919533 ) )
    };

    Machine[] fantasyCoins = new Machine[]{
            new Machine( "Fantasyland", "Royal Reception", "Quarter", "royal_reception_mac", "royal_reception_backstamp", "royal_reception_preview", royalReceptionQuarter, new LatLng( 33.812829, -117.919048 ) ),
            new Machine( "Fantasyland", "Small World Toy Shop", "Quarter", "small_world_mac", "fantasyland_backstamp", "small_world_preview", smallWorldQuarter, new LatLng( 33.814501, -117.918127 ) )
    };

    Machine[] toontownCoins = new Machine[]{
            new Machine( "Toontown", "Gag Factory", "Dime", "gag_factory_mac", "gag_factory_backstamp", "gag_factory_preview", gagFactoryDime, new LatLng( 33.815465, -117.918257 ) )
    };

    Machine[] adventureCoins = new Machine[]{
            new Machine( "Adventureland", "Bazaar Exit #1", "Penny", "bazaar_1_mac", "adventureland_backstamp", "bazaar_1_preview", bazaar1Penny, new LatLng( 33.811671, -117.919930 ) ),
            new Machine( "Adventureland", "Bazaar Exit #2", "Penny", "bazaar_2_mac", "bazaar_2_backstamp", "bazaar_2_preview", bazaar2Penny, new LatLng( 33.811671, -117.919910 ) ),
            new Machine( "Adventureland", "Bazaar Exit #3", "Penny", "bazaar_3_mac", "bazaar_3_backstamp", "bazaar_3_preview", bazaar3Penny, new LatLng( 33.811671, -117.919890 ) )
    };

    Machine[] frontierCoins = new Machine[]{
            new Machine( "Frontierland", "Pioneer Mercantile", "Penny", "pioneer_mac", "frontierland_backstamp", "pioneer_preview", pioneerPenny, new LatLng( 33.812073, -117.919809 ) ),
            new Machine( "Frontierland", "Bonanza Outfitters", "Penny", "bonanza_outfitters_mac", "bonanza_backstamp", "bonanza_outfitters_preview", bonanzaPenny, new LatLng( 33.812084, -117.919902 ) ),
            new Machine( "Frontierland", "Bonanza Outfitters Sidewalk", "Penny", "bonanza_sidewalk_mac", "pixar_fest_backstamp", "bonanza_sidewalk_preview", bonanzaSidewalkPenny, new LatLng( 33.812090, -117.919961 ) ),
            new Machine( "Frontierland", "Westward Ho Trading Post", "Penny", "westward_ho_mac", "frontierland_backstamp", "westward_ho_preview", westwardHoPenny, new LatLng( 33.812154, -117.919752 ) )
    };

    Machine[] newOrleansCoins = new Machine[]{
            new Machine( "New Orleans Square", "Pieces of Eight Gift Shop", "Penny", "pieces_of_eight_mac", "pieces_of_eight_backstamp", "pieces_of_eight_preview", piecesOfEightPenny, new LatLng( 33.811074, -117.921083 ) ),
            new Machine( "New Orleans Square", "Port Royal Curios & Curiosities", "Quarter", "port_royal_mac", null, "port_royal_preview", portRoyalQuarter, new LatLng( 33.811177, -117.921046 ) )
    };

    Machine[] critterCountryCoins = new Machine[]{
            new Machine( "Critter Country", "Pooh Corner #1", "Penny", "pooh_corner_1_mac", "pooh_corner_1_backstamp", "pooh_corner_1_preview", poohCorner1Penny, new LatLng( 33.812110, -117.923210 ) ),
            new Machine( "Critter Country", "Pooh Corner #2", "Penny", "pooh_corner_2_mac", "critter_country_backstamp", "pooh_corner_2_preview", poohCorner2Penny, new LatLng( 33.812110, -117.923180 ) ),
            new Machine( "Critter Country", "Splash Mountain Exit", "Penny", "splash_exit_mac", "splash_exit_backstamp", "splash_exit_preview", splashExitPenny, new LatLng( 33.811808, -117.923164 ) ),
    };

    Machine[] tomorrowCoins = new Machine[]{
            new Machine( "Tomorrowland", "Buzz Lightyear Exit #1", "Penny", "buzz_exit_1_2_mac", "buzz_exit_1_backstamp", "buzz_exit_1_preview", buzzExit1Penny, new LatLng( 33.812229, -117.917520 ) ),
            new Machine( "Tomorrowland", "Buzz Lightyear Exit #2", "Penny", "buzz_exit_1_2_mac", "buzz_exit_2_backstamp", "buzz_exit_2_preview", buzzExit2Penny, new LatLng( 33.812239, -117.917495 ) ),
            new Machine( "Tomorrowland", "Star Trader #1", "Quarter", "star_trader_1_2_mac", "star_trader_backstamp", "star_trader_1_preview", starTraderExit1Quarter, new LatLng( 33.811889, -117.917367 ) ),
            new Machine( "Tomorrowland", "Star Trader #2", "Quarter", "star_trader_1_2_mac", "star_trader_backstamp", "star_trader_2_preview", starTraderExit2Quarter, new LatLng( 33.811869, -117.917347 ) )
    };

    // CALIFORNIA ADVENTURE

    Machine[] buenaVistaCoins = new Machine[]{
            new Machine( "Buena Vista Street", "Kingswell Camera Shop", "Penny", "kingswell_mac", "kingswell_backstamp", "kingswell_preview", kingswellPenny, new LatLng( 33.808261, -117.919217 ) ),
            new Machine( "Buena Vista Street", "Kingswell Camera Shop #2", "Penny", "trolley_treats_1_mac", "buena_vista_backstamp", "trolley_treats_1_preview", kingswell2Penny, new LatLng( 33.808261, -117.919177 ) ),
            new Machine( "Buena Vista Street", "Julius Katz & Sons", "Penny", "katz_mac", "buena_vista_backstamp", "julius_katz_preview", katzPenny, new LatLng( 33.808172, -117.919027 ) ),
            new Machine( "Buena Vista Street", "Trolley Treats", "Nickel", "trolley_treats_mac", "trolley_treats_backstamp", "trolley_treats_preview", trolleyNickel, new LatLng( 33.807985, -117.919082 ) ),
            new Machine( "Buena Vista Street", "Los Feliz Five & Dime #1", "Penny", "five_dime_1_mac", "buena_vista_backstamp", "five_dime_1_preview", fiveDime1Penny, new LatLng( 33.808353, -117.918642 ) ),
            new Machine( "Buena Vista Street", "Los Feliz Five & Dime #2", "Penny", "five_dime_2_mac", "buena_vista_backstamp", "five_dime_2_preview", fiveDime2Penny, new LatLng( 33.808353, -117.918602 ) ),
            new Machine( "Buena Vista Street", "Big Top Toys", "Penny", "big_top_toys_mac", "buena_vista_backstamp", "big_top_preview", bigTopPenny, new LatLng( 33.808222, -117.918702 ) )
    };

    Machine[] hollywoodCoins = new Machine[]{
            new Machine( "Hollywood Land", "Off The Page Gifts", "Penny", "off_the_page_mac", "hollywood_tower_backstamp", "off_the_page_preview", offPagePenny, new LatLng( 33.807557, -117.917951 ) ),
            new Machine( "Hollywood Land", "Gone Hollywood Gifts", "Quarter", "gone_hollywood_mac", "gone_hollywood_backstamp", "gone_hollywood_preview", goneHollyWoodQuarter, new LatLng( 33.807748, -117.918346 ) ),
            new Machine( "Hollywood Land", "Gone Hollywood Gifts #2", "Quarter", "gone_hollywood_2_mac", "gone_hollywood_backstamp", "gone_hollywood_2_preview", goneHollyWood2Quarter, new LatLng( 33.807748, -117.918306 ) ),
            new Machine( "Hollywood Land", "Gone Hollywood Gifts #3", "Quarter", "gone_hollywood_3_mac", "avengers_backstamp", "gone_hollywood_3_preview", goneHollyWood3Quarter, new LatLng( 33.807748, -117.918266 ) ),
            new Machine( "Hollywood Land", "Collector's Warehouse Gifts", "Penny", "collectors_gifts_mac", "collectors_gifts_backstamp", "collectors_gifts_preview", collectorsGifts, new LatLng( 33.806821, -117.916798 ) )
    };

    Machine[] carsLandCoins = new Machine[]{
            new Machine( "Cars Land", "Ramone's", "Penny", "ramones_mac", "cars_land_backstamp", "ramones_preview", ramonesPenny, new LatLng( 33.805421, -117.918751 ) )
    };

    Machine[] grizzlyPeakAirfieldsCoins = new Machine[]{
            new Machine( "Grizzly Peak Airfield", "Humphrey's Service", "Penny", "humphreys_service_mac", "humphreys_backstamp", "humphreys_preview", humphreyPenny, new LatLng( 33.807933, -117.920154 ) )
    };

    Machine[] grizzlyPeakAreaCoins = new Machine[]{
            new Machine( "Grizzly Peak Recreational Area", "Rushin' River Outfitters", "Quarter", "rushin_river_outfitters_mac", "rushin_backstamp", "rushin_river_preview", rushinRiverQuarter, new LatLng( 33.807331, -117.920974 ) ),
    };

    Machine[] pixarPierCoins = new Machine[]{
            new Machine( "Pixar Pier", "Knick's Knacks", "Penny", "knicks_knacks_mac", "pixar_backstamp", "knicks_knacks_preview", knicksKnacksPenny, new LatLng( 33.805332, -117.920737 ) ),
            new Machine( "Pixar Pier", "Bing Bong's Sweet Stuff", "Penny", "bing_bong_mac", "pixar_backstamp", "bing_bong_preview", bingBongPenny, new LatLng( 33.804927, -117.922604 ) )
    };

    // DOWNTOWN DISNEY

    Machine[] worldDisneyCoins = new Machine[]{
            new Machine( "World of Disney", "East Door", "Penny", "wod_east_mac", "wod_backstamp", "wod_restrooms_preview", WodEastDoorPenny, new LatLng( 33.8086787, -117.9207830 ) ),
            new Machine( "World of Disney", "Center Door", "Penny", "wod_center_mac", "wod_backstamp", "wod_east_door_preview", WodCenterDoorPenny, new LatLng( 33.8086341, -117.9211732 ) )
    };

    Machine[] wetzelsCoins = new Machine[]{
            new Machine( "Near Wetzel's Pretzels", "Between Wetzel's & Haagen-Dazs", "Penny", "wetzels_mac", "blank_backstamp", "wetzels_preview", wetzelPenny, new LatLng( 33.809168, -117.922508 ) ),
    };

    Machine[] tortillaCoins = new Machine[]{
            new Machine( "Near Tortilla Jo's", "Outside Tortilla Jo's", "Penny", "tortilla_jo_mac", "blank_backstamp", "tortilla_jo_preview", tortillaPenny, new LatLng( 33.8092105, -117.9238112 ) ),
    };

    Machine[] grandCalifornianCoins = new Machine[]{
            new Machine( "Grand Californian Hotel", "White Water Snacks", "Penny", "white_water_mac", "white_water_backstamp", "white_water_preview", whiteWaterPenny, new LatLng( 33.807552, -117.921556 ) ),
    };

    Machine[] disneylandHotelCoins = new Machine[]{
            new Machine( "Disneyland Hotel", "Fantasia Gift Shop", "Penny", "fantasia_mac", "disneyland_hotel_backstamp", "fantasia_preview", fantasiaPenny, new LatLng( 33.808986, -117.927126 ) ),
            new Machine( "Disneyland Hotel", "Goofy's Kitchen #1", "Penny", "goofys_1_2_mac", "disneyland_hotel_backstamp", "goofys_kitchen_1_preview", goofysKitchen1Penny, new LatLng( 33.808546, -117.927317 ) ),
            new Machine( "Disneyland Hotel", "Goofy's Kitchen #2", "Penny", "goofys_1_2_mac", "disneyland_hotel_backstamp", "goofys_kitchen_2_preview", goofysKitchen2Penny, new LatLng( 33.808546, -117.927277 ) ),
    };

    Machine[] paradiseHotelCoins = new Machine[]{
            new Machine( "Paradise Pier Hotel", "1st Floor, Near PCH Grill", "Penny", "paradise_hotel_mac", "paradise_hotel_backstamp", "paradise_hotel_preview", paradiseHotelPenny, new LatLng( 33.806219, -117.924649 ) ),
    };


    Machine[] retiredDisneylandMachines = new Machine[]{
            new Machine( "Main Street", "Opera House Exit #4 - Retired", "Nickel", "old_opera_4_mac", "old_opera_4_backstamp", "old_opera_4_preview", oldOperaExit4Nickel, new LatLng( 33.810270, -117.918430 ) ),
            new Machine( "Main Street", "Opera House Exit #4 - Retired #2", "Nickel", "opera_4_mac", "opera_4_backstamp", "old_opera_4_preview_1", retiredOperaExit4Nickel, new LatLng( 33.810270, -117.918430 ) ),
            new Machine( "Main Street", "Penny Arcade #2 - Retired", "Penny", "old_penny_arcade_2_mac", "penny_arcade_2_backstamp", "old_penny_arcade_2_preview", retiredPennyArcade2Penny, new LatLng( 33.811262, -117.919072 ) ),
            new Machine( "Main Street", "Penny Arcade #3 - Retired", "Nickel", "penny_arcade_3_mac", "penny_arcade_3_backstamp", "penny_arcade_3_preview", retiredPennyArcade3Nickel, new LatLng( 33.811262, -117.919102 ) ),
            new Machine( "Frontierland", "Bonanza Outfitters Sidewalk - Retired", "Penny", "bonanza_sidewalk_mac", "frontierland_backstamp", "old_bonanza_sidewalk_preview", retiredBonanzaSidewalkPenny, new LatLng( 33.812090, -117.919961 ) ),
            new Machine( "Tomorrowland", "Buzz Lightyear Exit #1 - Retired", "Penny", "buzz_exit_1_2_mac", "buzz_exit_1_backstamp", "buzz_exit_1_preview_retired", retiredBuzzExit1Penny, new LatLng( 33.812229, -117.917520 ) )
    };

    Machine[] retiredCaliforniaMachines = new Machine[]{
            new Machine( "Buena Vista Street", "Trolley Treats - Retired", "Nickel", "trolley_treats_2_mac", "california_adventure_nickel_backstamp", "trolley_treats_2_preview", retiredTrolley2Nickel, new LatLng( 33.807985, -117.919042 ) ),
            new Machine( "Buena Vista Street", "Kingswell Camera Shop #2 - Retired", "Nickel", "kingswell_2_mac", "kingswell_2_backstamp", "kingswell_2_preview", kingswell2NickelOld, new LatLng( 33.808261, -117.919247 ) ),
            new Machine( "Hollywood Land", "Studio Store - Retired", "Dime", "studio_mac", "studio_backstamp", "studio_store_preview", studioStoreDimeOld, new LatLng( 33.808089, -117.917663 ) ),
            new Machine( "Paradise Pier", "Treasures In Paradise - Retired", "Penny", "treasures_in_paradise_mac", "treasures_backstamp", "treasures_in_paradise_preview", retiredTreasuresPenny, new LatLng( 33.805332, -117.920737 ) ),
            new Machine( "Paradise Pier", "Sideshow Shirts - Retired", "Penny", "sideshow_mac", "sideshow_backstamp", "sideshow_preview", retiredSideshowPenny, new LatLng( 33.804927, -117.922604 ) )
    };

    Machine[] retiredDowntownMachines = new Machine[]{
            new Machine( "Anna & Elsa's Boutique", "The Dream Boutique - Retired", "Nickel", "frozen_shop_mac", "frozen_shop_princess_backstamp", "frozen_shop_preview", annaElsaNickelOld, new LatLng( 33.808713, -117.922590 ) ),
            new Machine( "Rainforest Cafe", "Lobby #1 - Retired", "Penny", "rainforest_1_mac", "blank_backstamp", "rainforest_1_preview", rainforestLobby1Penny, new LatLng( 33.809381, -117.924896 ) ),
            new Machine( "Rainforest Cafe", "Lobby #2 - Retired", "Penny", "rainforest_2_mac", "blank_backstamp", "rainforest_2_preview", rainforestLobby2Penny, new LatLng( 33.809381, -117.924856 ) ),
            new Machine( "Rainforest Cafe", "Lobby #3 - Retired", "Quarter", "rainforest_3_mac", "blank_quarter_backstamp", "rainforest_3_preview", rainforestLobby3Quarter, new LatLng( 33.809381, -117.924816 ) ),
            new Machine( "ESPN Zone", "ESPN Gift Shop - Retired", "Penny", "espn_mac", "blank_backstamp", "espn_preview", espnShopPenny, new LatLng( 33.809171, -117.925359 ) )
    };

    Machine[] defaultMac = new Machine[]{
            new Machine( "N/A", "Unknown", "N/A", "", "", "", null, null )
    };

    Machine[][] disneyMachines = new Machine[][]{
            mainCoins, tomorrowCoins, fantasyCoins, toontownCoins, frontierCoins, adventureCoins, critterCountryCoins, newOrleansCoins
    };

    Machine[][] calMachines = new Machine[][]{
            buenaVistaCoins, grizzlyPeakAirfieldsCoins, grizzlyPeakAreaCoins, pixarPierCoins, hollywoodCoins, carsLandCoins
    };
    Machine[][] downtownMachines = new Machine[][]{
            worldDisneyCoins, wetzelsCoins, tortillaCoins, disneylandHotelCoins, grandCalifornianCoins, paradiseHotelCoins
    };

    Machine[][] retiredMachines = new Machine[][]{
            retiredDisneylandMachines, retiredCaliforniaMachines, retiredDowntownMachines
    };
}
