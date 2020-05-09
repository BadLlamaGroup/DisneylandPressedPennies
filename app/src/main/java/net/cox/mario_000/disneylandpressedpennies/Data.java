package net.cox.mario_000.disneylandpressedpennies;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mario_000 on 6/25/2016.
 */
public interface Data
{
    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////                          /////////////////////////
    /////////////////////////         - Coin -         /////////////////////////
    /////////////////////////                          /////////////////////////
    /////////////////////////      PARAMETERS:         /////////////////////////
    /////////////////////////  STRING - TITLE OF COIN  /////////////////////////
    /////////////////////////  STRING - IMAGE NAME     /////////////////////////
    /////////////////////////  DATE - DATE COLLECTED   /////////////////////////
    /////////////////////////                          /////////////////////////
    ////////////////////////////////////////////////////////////////////////////


    ////////////////
    // DISNEYLAND //
    ////////////////

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////     MAIN STREET COINS     ///////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] pennyArcade1Quarter = new Coin[]{
            new Coin( "Peter Pan - Walt Quote: \"Leave Today\"", "penny_arcade_peter_pan", null ),
            new Coin( "Mickey - Walt Quote: \"Do The Impossible\"", "penny_arcade_mickey", null ),
            new Coin( "Castle - Walt Quote: \"If You Can Dream It\"", "penny_arcade_castle", null )
    };

    Coin[] pennyArcade2Penny = new Coin[]{
            new Coin( "Mary Poppins with umbrella", "penny_arcade_2_mary_with_umbrella", null ),
            new Coin( "Mary Poppins flying", "penny_arcade_2_mary_flying", null ),
            new Coin( "Mary Poppins with penguins", "penny_arcade_2_mary_penguins", null )
    };

    Coin[] pennyArcadeNickel2020Nickel = new Coin[]{
            new Coin( "Mickey 2020", "penny_arcade_nickel_2020_mickey_2020", null ),
            new Coin( "Matterhorn 2020", "penny_arcade_nickel_2020_matterhorn_2020", null ),
            new Coin( "Castle 2020", "penny_arcade_nickel_2020_castle_2020", null )
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

    Coin[] fairyTaleTreasureQuarter = new Coin[]{
            new Coin( "Kristoff, Sven, Olaf", "fairy_tale_frozen", null ),
            new Coin( "Elsa & Anna", "fairy_tale_sisters", null ),
            new Coin( "Olaf", "fairy_tale_olaf", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////     FANTASY LAND COINS     //////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

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

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////     TOONTOWN COINS     //////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] gagFactoryDime = new Coin[]{
            new Coin( "Baby Goofy", "gag_factory_goofy", null ),
            new Coin( "Baby Mickey and Pluto", "gag_factory_pluto_mickey", null ),
            new Coin( "Baby Minnie Mouse", "gag_factory_minnie", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////     ADVENTURELAND COINS     /////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

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

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////     FRONTIERLAND COINS     //////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

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

    Coin[] westwardHoPenny = new Coin[]{
            new Coin( "Big Thunder Train \"I.M. LOCO\"", "westward_ho_loco", null ),
            new Coin( "Big Thunder Train \"U.B. BOLD\"", "westward_ho_bold", null ),
            new Coin( "Big Thunder Train \"U.R. DARING\"", "westward_ho_daring", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    //////////////////////     NEW ORLEANS SQUARE COINS     /////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] piecesOfEightPenny = new Coin[]{
            new Coin( "Dead Men Tell No Tales - Anchor", "pieces_of_eight_anchor", null ),
            new Coin( "Pirates of the Caribbean - Skull", "pieces_of_eight_skull", null ),
            new Coin( "Pirate Face & Crossed Swords", "pieces_of_eight_face", null )
    };

    Coin[] portRoyalQuarter = new Coin[]{
            new Coin( "Phineas Queeg", "port_royal_phineas_queeg", null ),
            new Coin( "Ezra Dobbins", "port_royal_ezra_dobbins", null ),
            new Coin( "Gus Gracey", "port_royal_gus_gracey", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////     CRITTER COUNTRY COINS     //////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

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


    /////////////////////////////////////////////////////////////////////////////////
    //////////////////////////     TOMORROWLAND COINS     ///////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] buzzExit1Penny = new Coin[]{
            new Coin( "Astro Orbitor", "buzz_exit_1_astro", null ),
            new Coin( "Space Mountain Logo", "buzz_exit_1_space", null ),
            new Coin( "Monorail", "buzz_exit_1_monorail", null )
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




    //////////////////////////
    // CALIFORNIA ADVENTURE //
    //////////////////////////


    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////     BUENA VISTA STREET COINS     ////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] kingswellPenny = new Coin[]{
            new Coin( "Dory", "kingswell_dory", null ),
            new Coin( "Destiny the Whale Shark & Dory", "kingswell_destiny", null ),
            new Coin( "Hank and Dory", "kingswell_hank", null )
    };

    Coin[] kingswell2Penny = new Coin[]{
            new Coin( "Red Car Trolley", "trolley_1_red_car", null ),
            new Coin( "Mickey Mouse on Trolley", "trolley_1_mickey", null ),
            new Coin( "Disney California Adventure", "trolley_1_california", null )
    };

    Coin[] katzPenny = new Coin[]{
            new Coin( "World of Color", "katz_world_of_color", null ),
            new Coin( "The Little Mermaid Adventure", "katz_little_mermaid", null ),
            new Coin( "Midway Mania!", "katz_midway", null )
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


    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////     HOLLYWOOD LAND COINS     //////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] offPagePenny = new Coin[]{
            new Coin( "Low N Slow Car Club", "off_the_page_low_slow", null ),
            new Coin( "Mickey and Minnie at Carthay Circle", "off_the_page_carthay", null ),
            new Coin( "Goofy's Sky School", "off_the_page_goofys", null )
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

    /////////////////////////////////////////////////////////////////////////////////
    //////////////////////////      CARS LAND COINS       ///////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


    Coin[] ramonesPenny = new Coin[]{
            new Coin( "Cars Land", "ramones_cars", null ),
            new Coin( "Radiator Springs Chamber of Commerce", "ramones_chamber_com", null ),
            new Coin( "Champions Speed Shop", "ramones_champions_shop", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////     GRIZZLY PEAK AIRFIELD COINS     //////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] humphreyPenny = new Coin[]{
            new Coin( "Pilot Mickey Porthole", "humphreys_pilot", null ),
            new Coin( "Pilot Mickey Waving", "humphreys_waving", null ),
            new Coin( "Soarin' Logo Over Mickey", "humphreys_soarin", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    //////////////////     GRIZZLY PEAK RECREATIONAL AREA COINS     /////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] rushinRiverQuarter = new Coin[]{
            new Coin( "Russell", "rushin_russell", null ),
            new Coin( "Dug", "rushin_dug", null ),
            new Coin( "UP House", "rushin_house", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////     PIXAR PIER COINS     ////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

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


    /////////////////////
    // DOWNTOWN DISNEY //
    /////////////////////


    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////     WORLD OF DISNEY COINS     ///////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] WodFirstMachinePenny = new Coin[]{
            new Coin( "Mickey Mouse", "wod_center_mickey", null ),
            new Coin( "Dancing Goofy", "wod_center_goofy", null ),
            new Coin( "Donald Duck", "wod_center_donald", null )
    };

    Coin[] WodSecondMachinePenny = new Coin[]{
            new Coin( "Pooh", "wod_east_pooh", null ),
            new Coin( "Dumbo", "wod_east_dumbo", null ),
            new Coin( "Pinocchio", "wod_east_pinocchio", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////     MONORAIL COINS     //////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] wetzelPenny = new Coin[]{
            new Coin( "Downtown Disney Tinker Bell", "wetzels_tinkerbell", null ),
            new Coin( "Downtown Disney Pirate Mickey", "wetzels_pirate_mickey", null ),
            new Coin( "Downtown Disney Captain Hook", "wetzels_hook", null ),
            new Coin( "Downtown Disney Sorcerer Mickey", "wetzels_mickey", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////     APRICOT LANE SHOP COINS     /////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] tortillaPenny = new Coin[]{
            new Coin( "Downtown Disney Donald Duck", "tortilla_donald", null ),
            new Coin( "Downtown Disney Pirate Mickey #2", "tortilla_pirate_mickey", null ),
            new Coin( "Downtown Disney Artist Minnie", "tortilla_minnie", null ),
            new Coin( "Downtown Disney Lightning McQueen", "tortilla_mcqueen", null )
    };

    /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////     DISNEYLAND HOTEL COINS     //////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

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

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////     PARADISE PIER HOTEL COINS     /////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    Coin[] paradiseHotelPenny = new Coin[]{
            new Coin( "Minnie Splashing Mickey", "paradise_hotel_minnie", null ),
            new Coin( "Paradise Pier Hotel Logo", "paradise_hotel_logo", null ),
            new Coin( "Mickey on the Beach", "paradise_hotel_mickey", null )
    };




    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////                          /////////////////////////
    /////////////////////////       - MACHINE -        /////////////////////////
    /////////////////////////                          /////////////////////////
    /////////////////////////       PARAMETERS:        /////////////////////////
    /////////////////////////  STRING - LAND           /////////////////////////
    /////////////////////////  STRING - MACHINE NAME   /////////////////////////
    /////////////////////////  STRING - TYPE OF COIN   /////////////////////////
    /////////////////////////  STRING - MACHINE IMAGE  /////////////////////////
    /////////////////////////  STRING - BACKSTAMP      /////////////////////////
    /////////////////////////  STRING - COIN PREVIEW   /////////////////////////
    /////////////////////////  COIN[] - COINS IN MAC   /////////////////////////
    /////////////////////////  LATLNG - LOCATION       /////////////////////////
    /////////////////////////                          /////////////////////////
    ////////////////////////////////////////////////////////////////////////////


    /////////////////////////
    // DISNEYLAND MACHINES //
    /////////////////////////

    Machine[] mainCoins = new Machine[]{
            new Machine( "Main Street", "Penny Arcade #1", "Quarter", "penny_arcade_mac", "penny_arcade_backstamp", "penny_arcade_preview", pennyArcade1Quarter, new LatLng( 33.811302, -117.919092 ) ),
            new Machine( "Main Street", "Penny Arcade #2", "Penny", "penny_arcade_2_mac", "penny_arcade_2_backstamp", "penny_arcade_2_preview", pennyArcade2Penny, new LatLng( 33.811272, -117.919092 ) ),
            new Machine( "Main Street", "Penny Arcade - Nickel 2020", "Nickel", "penny_arcade_nickel_2020_mac", "disneyland_backstamp", "penny_arcade_nickel_2020_preview", pennyArcadeNickel2020Nickel, new LatLng( 33.811242, -117.919092 ) ),
            new Machine( "Main Street", "Opera House Exit #1", "Penny", "opera_1_mac", "opera_backstamp", "opera_1_preview", operaExit1Penny, new LatLng( 33.810270, -117.918520 ) ),
            new Machine( "Main Street", "Opera House Exit #2", "Penny", "opera_2_mac", "opera_backstamp", "opera_2_preview", operaExit2Penny, new LatLng( 33.810270, -117.918490 ) ),
            new Machine( "Main Street", "Opera House Exit #3", "Penny", "opera_3_mac", "opera_backstamp", "opera_3_preview", operaExit3Penny, new LatLng( 33.810270, -117.918460 ) ),
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
            new Machine( "Frontierland", "Westward Ho Trading Post", "Penny", "westward_ho_mac", "frontierland_backstamp", "westward_ho_preview", westwardHoPenny, new LatLng( 33.812154, -117.919752 ) )
    };

    Machine[] newOrleansCoins = new Machine[]{
            new Machine( "New Orleans Square", "Pieces of Eight Gift Shop", "Penny", "pieces_of_eight_mac", "pieces_of_eight_backstamp", "pieces_of_eight_preview", piecesOfEightPenny, new LatLng( 33.811074, -117.921083 ) ),
            new Machine( "New Orleans Square", "Port Royal Curios & Curiosities", "Quarter", "port_royal_mac", "port_royal_50th_backstamp", "port_royal_50th_preview", portRoyalQuarter, new LatLng( 33.811177, -117.921046 ) )
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

    ///////////////////////////////////
    // CALIFORNIA ADVENTURE MACHINES //
    ///////////////////////////////////

    Machine[] buenaVistaCoins = new Machine[]{
            new Machine( "Buena Vista Street", "Kingswell Camera Shop", "Penny", "kingswell_mac", "kingswell_backstamp", "kingswell_preview", kingswellPenny, new LatLng( 33.808261, -117.919217 ) ),
            new Machine( "Buena Vista Street", "Kingswell Camera Shop #2", "Penny", "trolley_treats_1_mac", "buena_vista_backstamp", "trolley_treats_1_preview", kingswell2Penny, new LatLng( 33.808261, -117.919177 ) ),
            new Machine( "Buena Vista Street", "Julius Katz & Sons", "Penny", "katz_mac", "buena_vista_backstamp", "julius_katz_preview", katzPenny, new LatLng( 33.808172, -117.919027 ) ),
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

    //////////////////////////////
    // DOWNTOWN DISNEY MACHINES //
    //////////////////////////////

    Machine[] worldDisneyCoins = new Machine[]{
            new Machine( "World of Disney", "First Machine", "Penny", "wod_mac", "wod_backstamp", "wod_east_door_preview", WodFirstMachinePenny, new LatLng( 33.8086341, -117.9211732 ) ),
            new Machine( "World of Disney", "Second Machine", "Penny", "wod_mac", "wod_backstamp", "wod_restrooms_preview", WodSecondMachinePenny, new LatLng( 33.8086787, -117.9207830 ) )
    };

    Machine[] wetzelsCoins = new Machine[]{
            new Machine( "Near Wetzel's Pretzels", "Between Wetzel's & Haagen-Dazs", "Penny", "wetzels_mac", "blank_backstamp", "wetzels_preview", wetzelPenny, new LatLng( 33.809168, -117.922508 ) ),
    };

    Machine[] tortillaCoins = new Machine[]{
            new Machine( "Near Tortilla Jo's", "Outside Tortilla Jo's", "Penny", "tortilla_jo_mac", "blank_backstamp", "tortilla_jo_preview", tortillaPenny, new LatLng( 33.8092105, -117.9238112 ) ),
    };

    Machine[] grandCalifornianCoins = new Machine[]{
    };

    Machine[] disneylandHotelCoins = new Machine[]{
            new Machine( "Disneyland Hotel", "Fantasia Gift Shop", "Penny", "fantasia_mac", "disneyland_hotel_backstamp", "fantasia_preview", fantasiaPenny, new LatLng( 33.808986, -117.927126 ) ),
            new Machine( "Disneyland Hotel", "Goofy's Kitchen #1", "Penny", "goofys_1_2_mac", "disneyland_hotel_backstamp", "goofys_kitchen_1_preview", goofysKitchen1Penny, new LatLng( 33.808546, -117.927317 ) ),
            new Machine( "Disneyland Hotel", "Goofy's Kitchen #2", "Penny", "goofys_1_2_mac", "disneyland_hotel_backstamp", "goofys_kitchen_2_preview", goofysKitchen2Penny, new LatLng( 33.808546, -117.927277 ) ),
    };

    Machine[] paradiseHotelCoins = new Machine[]{
            new Machine( "Paradise Pier Hotel", "1st Floor, Near PCH Grill", "Penny", "paradise_hotel_mac", "paradise_hotel_backstamp", "paradise_hotel_preview", paradiseHotelPenny, new LatLng( 33.806219, -117.924649 ) ),
    };





    /////////////////////////////////////////////////
    ///////////////                   ///////////////
    ///////////////      RETIRED      ///////////////
    ///////////////                   ///////////////
    /////////////////////////////////////////////////



    //////////////////////////////
    // RETIRED DISNEYLAND COINS //
    //////////////////////////////

    Coin[] retiredPennyArcade2Penny_1_20_19 = new Coin[]{
            new Coin( "Miguel and Dante", "retired_penny_arcade_2_coco_1_20_19", null ),
            new Coin( "Remy and Linguini", "retired_penny_arcade_2_delicious_1_20_19", null ),
            new Coin( "Woody and Buzz", "retired_penny_arcade_2_toy_story_1_20_19", null )
    };

    Coin[] retiredPennyArcadeHolidayNickel_1_7_19 = new Coin[]{
            new Coin( "Holiday Mickey 2018", "retired_penny_arcade_holiday_mickey_1_7_19", null ),
            new Coin( "Holiday Minnie 2018", "retired_penny_arcade_holiday_minnie_1_7_19", null ),
            new Coin( "Holiday Donald 2018", "retired_penny_arcade_holiday_donald_1_7_19", null )
    };

    Coin[] retiredPennyArcadeHoliday2019Nickel_1_13_20 = new Coin[]{
            new Coin( "Minnie and Mickey in love", "penny_arcade_holiday_2019_minnie_and_mickey_in_love", null ),
            new Coin( "Mickey Noel", "penny_arcade_holiday_2019_mickey_noel", null ),
            new Coin( "Minnie joy", "penny_arcade_holiday_2019_minnie_joy", null )
    };

    Coin[] retiredPennyArcade2Penny_8_8_18 = new Coin[]{
            new Coin( "Main Street Electrical Parade America", "retired_penny_arcade_2_america_8_8_18", null ),
            new Coin( "Main Street Electrical Parade Pete's Dragon", "retired_penny_arcade_2_dragon_8_8_18", null ),
            new Coin( "Main Street Electrical Parade Mushroom", "retired_penny_arcade_2_butterfly_8_8_18", null )
    };

    Coin[] retiredPennyArcade3Nickel_1_19_18 = new Coin[]{
            new Coin( "Minnie & Mickey Happy Holidays 2017", "retired_penny_arcade_3_mickey_2017_1_19_18", null ),
            new Coin( "Donald Season's Greetings 2017", "retired_penny_arcade_3_santa_donald_1_19_18", null ),
            new Coin( "Tinker Bell Happy New Year 2018", "retired_penny_arcade_3_tinkerbell_2018_1_19_18", null )
    };

    Coin[] retiredOperaExit4Nickel_1_15_18 = new Coin[]{
            new Coin( "Gibson Girl Ice Cream", "opera_4_gibson", null ),
            new Coin( "Jolly Holiday Bakery Cafe", "opera_4_jolly_holiday", null ),
            new Coin( "Little Red Wagon Corn Dogs", "opera_4_corn_dogs", null )
    };

    Coin[] retiredOperaExit4Nickel_1_4_16 = new Coin[]{
            new Coin( "2016 Goofy", "retired_opera_4_goofy_1_4_16", null ),
            new Coin( "2016 Pluto", "retired_opera_4_pluto_1_4_16", null ),
            new Coin( "2016 Donald", "retired_opera_4_donald_1_4_16", null )
    };

    Coin[] retiredBonanzaSidewalkPenny_1_20_19 = new Coin[]{
            new Coin( "Edna & Jack Jack", "retired_bonanza_sidewalk_edna_1_20_19", null ),
            new Coin( "Russel & Dug", "retired_bonanza_sidewalk_russel_dug_1_20_19", null ),
            new Coin( "Marlin & Nemo", "retired_bonanza_sidewalk_marlin_nemo_1_20_19", null )
    };

    Coin[] retiredBonanzaSidewalkPenny_8_2_18 = new Coin[]{
            new Coin( "Mickey at Rivers of America", "retired_bonanza_sidewalk_rivers_8_2_18", null ),
            new Coin( "Fly Fisherman Mickey", "retired_bonanza_sidewalk_fisherman_8_2_18", null ),
            new Coin( "Mark Twain Riverboat", "retired_bonanza_sidewalk_mark_twain_8_2_18", null )
    };

    Coin[] retiredPortRoyalQuarter_8_25_18 = new Coin[]{
            new Coin( "Haunted Mansion Banner", "retired_port_royal_banner_8_25_18", null ),
            new Coin( "Haunted Mansion Logo", "retired_port_royal_logo_8_25_18", null ),
            new Coin( "Haunted Mansion Ride", "retired_port_royal_ride_8_25_18", null )
    };

    Coin[] retiredPortRoyalQuarter_1_10_19 = new Coin[]{
            new Coin( "Nightmare Before Christmas - Jack Skellington", "retired_port_royal_jack_skellington_1_10_19", null ),
            new Coin( "Nightmare Before Christmas - Oogie Boogie", "retired_port_royal_oogie_boogie_1_10_19", null ),
            new Coin( "Nightmare Before Christmas - Zero", "retired_port_royal_zero_1_10_19", null )
    };

    Coin[] retiredBuzzExit1Penny_3_3_17 = new Coin[]{
            new Coin( "Buzz Lightyear #1", "retired_buzz_exit_1_buzz_3_3_17", null ),
            new Coin( "Little Green Men #1", "retired_buzz_exit_1_lgm_3_3_17", null ),
            new Coin( "Zurg #1", "retired_buzz_exit_1_zurg_3_3_17", null )
    };

    Coin[] retiredOperaExit4Nickel_9_11_19 = new Coin[]{
            new Coin( "2018 The Walt Disney Story", "retired_opera_exit_4_walt_story_9_11_19", null ),
            new Coin( "2018 Main Street Penny Arcade", "retired_opera_exit_4_penny_arcade_9_11_19", null ),
            new Coin( "2018 Main Street Cinema", "retired_opera_exit_4_cinema_9_11_19", null )
    };


    ////////////////////////////////////////
    // RETIRED CALIFORNIA ADVENTURE COINS //
    ////////////////////////////////////////

    Coin[] retiredKingswell2Nickel_1_24_18 = new Coin[]{
            new Coin( "Alien Ornaments Season's Greetings 2017", "retired_kingswell_2_alien_2017_1_24_18", null ),
            new Coin( "Tow Mater Holiday Cheer 2017", "retired_kingswell_2_tow_mater_2017_1_24_18", null ),
            new Coin( "McQueen 95 Happy New Year 2018", "retired_kingswell_2_mcqueen_2018_1_24_18", null )
    };

    Coin[] retiredTrolley2Nickel_1_24_18 = new Coin[]{
            new Coin( "2017 Pacific Wharf", "retired_trolley_treats_2_pacific_wharf_1_24_18", null ),
            new Coin( "2017 California Screamin'", "retired_trolley_treats_2_screamin_1_24_18", null ),
            new Coin( "2017 Bug's Land", "retired_trolley_treats_2_bugs_land_1_24_18", null )
    };

    Coin[] retiredStudioStoreDime_2_22_17 = new Coin[]{
            new Coin( "Anna", "retired_studio_anna_2_22_17", null ),
            new Coin( "Snowman Olaf", "retired_studio_olaf_2_22_17", null ),
            new Coin( "Elsa", "retired_studio_elsa_2_22_17", null )
    };

    Coin[] retiredTreasuresPenny_1_8_18 = new Coin[]{
            new Coin( "Duffy the Disney Bear", "retired_treasures_duffy_1_8_18", null ),
            new Coin( "Mickey and Duffy", "retired_treasures_mickey_duffy_1_8_18", null ),
            new Coin( "Bandleader Mickey", "retired_treasures_bandleader_1_8_18", null )
    };

    Coin[] retiredSideshowPenny_1_8_18 = new Coin[]{
            new Coin( "Angel", "retired_sideshow_angel_1_8_18", null ),
            new Coin( "Stitch & Angel heart", "retired_sideshow_stitch_angel_1_8_18", null ),
            new Coin( "Stitch", "retired_sideshow_stitch_1_8_18", null )
    };

    Coin[] retiredTrolleyNickel_9_11_19 = new Coin[]{
            new Coin( "2018 Grizzly Peak", "retired_trolley_treats_grizzly_9_11_19", null ),
            new Coin( "2018 Mickey's Fun Wheel", "retired_trolley_treats_mickey_fun_wheel_9_11_19", null ),
            new Coin( "2018 Red Car Trolley", "retired_trolley_treats_red_car_trolley_9_11_19", null )
    };

    Coin[] retiredTrolleyHolidayNickel_9_11_19 = new Coin[]{
            new Coin( "2018 Mickey with mistletoe", "retired_trolley_treats_holiday_mickey_9_11_19", null ),
            new Coin( "2018 Minnie waiting for a kiss", "retired_trolley_treats_holiday_minnie_9_11_19", null ),
            new Coin( "2018 Donald with Christmas gift", "retired_trolley_treats_holiday_donald_9_11_19", null )
    };

    Coin[] retiredKingswellHoliday2019Nickel_1_13_20 = new Coin[]{
            new Coin( "Celebrate the Season - Tinker Bell", "kingswell_holiday_2019_tinker_bell", null ),
            new Coin( "Snow Bros - Chip N Dale", "kingswell_holiday_2019_chip_n_dale", null ),
            new Coin( "Mickey feeding reindeer", "kingswell_holiday_2019_mickey_feeding_reindeer", null )
    };

    Coin[] retiredTrolleyTreatsYear2019Nickel_1_13_20 = new Coin[]{
            new Coin( "2019 Mickey", "retired_trolley_treats_2019_mickey_1_13_20", null ),
            new Coin( "2019 Minnie", "retired_trolley_treats_2019_minnie_1_13_20", null ),
            new Coin( "2019 Pluto", "retired_trolley_treats_2019_pluto_1_13_20", null )
    };


    ///////////////////////////////////
    // RETIRED DOWNTOWN DISNEY COINS //
    ///////////////////////////////////


    Coin[] retiredAnnaElsaNickel_3_18_18 = new Coin[]{
            new Coin( "Snow White", "retired_frozen_shop_snow_white_3_18_18", null ),
            new Coin( "Cinderella", "retired_frozen_shop_cinderella_3_18_18", null ),
            new Coin( "Ariel", "retired_frozen_shop_ariel_3_18_18", null )
    };

    Coin[] retiredRainforestLobby1Penny_6_19_18 = new Coin[]{
            new Coin( "Tree Frog Logo", "retired_rainforest_1_frog_6_19_18", null ),
            new Coin( "Hippo", "retired_rainforest_1_hippo_6_19_18", null ),
            new Coin( "Lioness and Cub", "retired_rainforest_1_lion_6_19_18", null ),
            new Coin( "Snake in Tree", "retired_rainforest_1_snake_6_19_18", null )
    };

    Coin[] retiredRainforestLobby2Penny_6_19_18 = new Coin[]{
            new Coin( "Tree Frog", "retired_rainforest_2_frog_6_19_18", null ),
            new Coin( "Angel Fish", "retired_rainforest_2_fish_6_19_18", null ),
            new Coin( "Parrot", "retired_rainforest_2_parrot_6_19_18", null ),
            new Coin( "Snake", "retired_rainforest_1_snake_6_19_18", null )
    };

    Coin[] retiredRainforestLobby3Quarter_6_19_18 = new Coin[]{
            new Coin( "Rhino", "retired_rainforest_3_rhino_6_19_18", null ),
            new Coin( "Aye-aye", "retired_rainforest_3_aye_6_19_18", null ),
            new Coin( "Leopard", "retired_rainforest_3_leopard_6_19_18", null ),
            new Coin( "Lemur", "retired_rainforest_3_lemur_6_19_18", null )
    };

    Coin[] retiredEspnShopPenny_6_2_18 = new Coin[]{
            new Coin( "ESPN", "retired_espn_logo_6_2_18", null ),
            new Coin( "ANGELS \"A\" Logo", "retired_espn_angels_6_2_18", null ),
            new Coin( "ESPN ZONE", "retired_espn_zone_6_2_18", null ),
            new Coin( "DODGERS", "retired_espn_dodgers_6_2_18", null )
    };

    Coin[] retiredWhiteWaterPenny_3_6_19 = new Coin[]{
            new Coin( "Chip N Dale", "retired_white_water_chip_dale_1_26_19", null ),
            new Coin( "Goofy Fishing", "retired_white_water_goofy_1_26_19", null ),
            new Coin( "Mickey Air Travel", "retired_white_water_mickey_1_26_19", null )
    };



    //////////////////////
    // RETIRED MACHINES //
    //////////////////////


    Machine[] retiredDisneylandMachines = new Machine[]{
            new Machine( "Main Street", "Opera House Exit #4 - Retired 1/4/16", "Nickel", "retired_opera_4_mac_1_4_16", "retired_opera_4_backstamp_1_4_16", "retired_opera_4_preview_1_4_16", retiredOperaExit4Nickel_1_4_16, new LatLng( 33.810270, -117.918430 ) ),
            new Machine( "Main Street", "Opera House Exit #4 - Retired 1/15/18", "Nickel", "retired_opera_4_mac_9_11_19", "opera_4_backstamp", "retired_opera_4_preview_1_15_18", retiredOperaExit4Nickel_1_15_18, new LatLng( 33.810270, -117.918430 ) ),
            new Machine( "Main Street", "Penny Arcade #2 - Retired 8/8/18", "Penny", "retired_penny_arcade_2_mac_8_8_18", "retired_penny_arcade_2_backstamp_8_8_18", "retired_penny_arcade_2_preview_8_8_18", retiredPennyArcade2Penny_8_8_18, new LatLng( 33.811262, -117.919072 ) ),
            new Machine( "Main Street", "Penny Arcade #2 - Retired 1/20/19", "Penny", "retired_penny_arcade_2_mac_1_20_19", "retired_pixar_fest_backstamp_1_20_19", "retired_penny_arcade_2_preview_1_20_19", retiredPennyArcade2Penny_1_20_19, new LatLng( 33.811202, -117.919072 ) ),
            new Machine( "Main Street", "Penny Arcade #3 - Retired 1/19/18", "Nickel", "retired_penny_arcade_3_mac_1_19_18", "retired_penny_arcade_3_backstamp_1_19_18", "retired_penny_arcade_3_preview_1_19_18", retiredPennyArcade3Nickel_1_19_18, new LatLng( 33.811262, -117.919102 ) ),
            new Machine( "Main Street", "Penny Arcade Holiday - Retired 1/7/19", "Nickel", "retired_penny_arcade_holiday_mac_1_7_19", "retired_penny_arcade_holiday_backstamp_1_7_19", "retired_penny_arcade_holiday_preview_1_7_19", retiredPennyArcadeHolidayNickel_1_7_19, new LatLng( 33.811262, -117.919112 ) ),
            new Machine( "Main Street", "Penny Arcade - Holiday 2019 - Retired 1/13/20", "Nickel", "retired_penny_arcade_holiday_mac_1_13_20", "retired_penny_arcade_holiday_2019_backstamp_1_13_20", "retired_penny_arcade_holiday_2019_preview_1_13_20", retiredPennyArcadeHoliday2019Nickel_1_13_20, new LatLng( 33.811242, -117.919092 ) ),
            new Machine( "New Orleans Square", "Port Royal Curios & Curiosities - Retired 8/25/18", "Quarter", "retired_port_royal_mac", null, "retired_port_royal_preview_8_25_18", retiredPortRoyalQuarter_8_25_18, new LatLng( 33.811177, -117.921046 ) ),
            new Machine( "New Orleans Square", "Port Royal Curios & Curiosities - Retired 1/10/19", "Quarter", "retired_port_royal_mac", "retired_port_royal_nightmare_before_christmas_backstamp_1_10_19", "retired_port_royal_preview_1_10_19", retiredPortRoyalQuarter_1_10_19, new LatLng( 33.811177, -117.921046 ) ),
            new Machine( "Frontierland", "Bonanza Outfitters Sidewalk - Retired 8/2/18", "Penny", "retired_bonanza_sidewalk_mac", "frontierland_backstamp", "retired_old_bonanza_sidewalk_preview_8_2_18", retiredBonanzaSidewalkPenny_8_2_18, new LatLng( 33.812090, -117.919961 ) ),
            new Machine( "Frontierland", "Bonanza Outfitters Sidewalk - Retired 1/20/19", "Penny", "retired_bonanza_sidewalk_mac", "retired_pixar_fest_backstamp_1_20_19", "retired_bonanza_sidewalk_preview_1_20_19", retiredBonanzaSidewalkPenny_1_20_19, new LatLng( 33.812090, -117.919961 ) ),
            new Machine( "Tomorrowland", "Buzz Lightyear Exit #1 - Retired 3/3/17", "Penny", "buzz_exit_1_2_mac", "buzz_exit_1_backstamp", "retired_buzz_exit_1_preview_3_3_17", retiredBuzzExit1Penny_3_3_17, new LatLng( 33.812229, -117.917520 ) ),
            new Machine( "Main Street", "Opera House Exit #4 - Retired 9/11/19", "Nickel", "retired_opera_4_mac_9_11_19", "retired_opera_exit_4_backstamp_9_11_19", "retired_opera_4_preview_9_11_19", retiredOperaExit4Nickel_9_11_19, new LatLng( 33.810270, -117.918430 ) )
    };

    Machine[] retiredCaliforniaMachines = new Machine[]{
            new Machine( "Buena Vista Street", "Trolley Treats - Retired 9/11/19", "Nickel", "retired_trolley_treats_mac_9_11_19", "retired_trolley_treats_backstamp_9_11_19", "retired_trolley_treats_preview_9_11_19", retiredTrolleyNickel_9_11_19, new LatLng( 33.807985, -117.919082 ) ),
            new Machine( "Buena Vista Street", "Trolley Treats #2 - Retired 1/24/18", "Nickel", "retired_trolley_treats_2_mac_1_24_18", "retired_california_adventure_nickel_backstamp_1_24_18", "retired_trolley_treats_2_preview_1_24_18", retiredTrolley2Nickel_1_24_18, new LatLng( 33.807985, -117.919042 ) ),
            new Machine( "Buena Vista Street", "Trolley Treats Holiday - Retired 9/11/19", "Nickel", "retired_trolley_treats_holiday_mac_9_11_19", "retired_trolley_treats_holiday_backstamp_9_11_19", "retired_trolley_treats_holiday_preview_9_11_19", retiredTrolleyHolidayNickel_9_11_19, new LatLng( 33.807985, -117.919112 ) ),
            new Machine( "Buena Vista Street", "Jolly Trolley Treats - Year 2019 - Retired 1/13/20", "Nickel", "retired_trolley_treats_2019_mac_1_13_20", "california_adventure_backstamp", "retired_trolley_treats_2019_preview_1_13_20", retiredTrolleyTreatsYear2019Nickel_1_13_20, new LatLng( 33.807985, -117.919112 ) ),
            new Machine( "Buena Vista Street", "Kingswell Camera Shop #2 - Retired 1/24/18", "Nickel", "retired_kingswell_2_mac_1_24_18", "retired_kingswell_2_backstamp_1_24_18", "retired_kingswell_2_preview_1_24_18", retiredKingswell2Nickel_1_24_18, new LatLng( 33.808261, -117.919247 ) ),
            new Machine( "Buena Vista Street", "Kingswell Camera Shop - Holiday 2019 - Retired 1/13/20", "Nickel", "kingswell_mac", "retired_kingswell_holiday_2019_backstamp_1_13_20", "retired_kingswell_holiday_2019_preview_1_13_20", retiredKingswellHoliday2019Nickel_1_13_20, new LatLng( 33.808261, -117.919247 ) ),
            new Machine( "Hollywood Land", "Studio Store - Retired 2/22/17", "Dime", "retired_studio_mac_2_22_17", "retired_studio_backstamp_2_22_17", "retired_studio_store_preview_2_22_17", retiredStudioStoreDime_2_22_17, new LatLng( 33.808089, -117.917663 ) ),
            new Machine( "Paradise Pier", "Treasures In Paradise - Retired 1/8/18", "Penny", "retired_treasures_in_paradise_mac_1_8_18", "retired_treasures_backstamp_1_8_18", "retired_treasures_in_paradise_preview_1_8_18", retiredTreasuresPenny_1_8_18, new LatLng( 33.805332, -117.920737 ) ),
            new Machine( "Paradise Pier", "Sideshow Shirts - Retired 1/8/18", "Penny", "retired_sideshow_mac_1_8_18", "retired_sideshow_backstamp_1_8_18", "retired_sideshow_preview_1_8_18", retiredSideshowPenny_1_8_18, new LatLng( 33.804927, -117.922604 ) )
    };

    Machine[] retiredDowntownMachines = new Machine[]{
            new Machine( "Anna & Elsa's Boutique", "The Dream Boutique - Retired 3/18/18", "Nickel", "retired_frozen_shop_mac_3_18_18", "retired_frozen_shop_princess_backstamp_3_18_18", "retired_frozen_shop_preview_3_18_18", retiredAnnaElsaNickel_3_18_18, new LatLng( 33.808713, -117.922590 ) ),
            new Machine( "Rainforest Cafe", "Lobby #1 - Retired 6/19/18", "Penny", "retired_rainforest_1_mac_6_19_18", "blank_backstamp", "retired_rainforest_1_preview_6_19_18", retiredRainforestLobby1Penny_6_19_18, new LatLng( 33.809381, -117.924896 ) ),
            new Machine( "Rainforest Cafe", "Lobby #2 - Retired 6/19/18", "Penny", "retired_rainforest_2_mac_6_19_18", "blank_backstamp", "retired_rainforest_2_preview_6_19_18", retiredRainforestLobby2Penny_6_19_18, new LatLng( 33.809381, -117.924856 ) ),
            new Machine( "Rainforest Cafe", "Lobby #3 - Retired 6/19/18", "Quarter", "retired_rainforest_3_mac_6_19_18", "blank_quarter_backstamp", "retired_rainforest_3_preview_6_19_18", retiredRainforestLobby3Quarter_6_19_18, new LatLng( 33.809381, -117.924816 ) ),
            new Machine( "ESPN Zone", "ESPN Gift Shop - Retired 6/2/18", "Penny", "retired_espn_mac_6_2_18", "blank_backstamp", "retired_espn_preview_6_2_18", retiredEspnShopPenny_6_2_18, new LatLng( 33.809171, -117.925359 ) ),
            new Machine( "Grand Californian Hotel", "White Water Snacks - Retired 1/26/19", "Penny", "retired_white_water_mac_1_26_19", "retired_white_water_backstamp_1_26_19", "retired_white_water_preview_1_26_19", retiredWhiteWaterPenny_3_6_19, new LatLng( 33.807552, -117.921556 ) )
    };





    Machine[] defaultMac = new Machine[]{
            new Machine( "N/A", "Unknown", "N/A", "", "", "", null, null )
    };


    ////////////////////
    // MACHINE GROUPS //
    ////////////////////

    Machine[][] disneyMachines = new Machine[][]{
            mainCoins, tomorrowCoins, fantasyCoins, toontownCoins, frontierCoins, adventureCoins, critterCountryCoins, newOrleansCoins
    };

    Machine[][] calMachines = new Machine[][]{
            buenaVistaCoins, grizzlyPeakAirfieldsCoins, grizzlyPeakAreaCoins, pixarPierCoins, hollywoodCoins, carsLandCoins
    };
    Machine[][] downtownMachines = new Machine[][]{
            worldDisneyCoins, wetzelsCoins, tortillaCoins, disneylandHotelCoins, paradiseHotelCoins
    };

    Machine[][] retiredMachines = new Machine[][]{
            retiredDisneylandMachines, retiredCaliforniaMachines, retiredDowntownMachines
    };


    String[] waltQuotes = new String[]{
            "wisdom_0", "wisdom_1", "wisdom_2", "wisdom_3", "wisdom_4", "wisdom_5", "wisdom_6",
            "wisdom_7", "wisdom_8", "wisdom_9", "wisdom_10", "wisdom_11", "wisdom_12", "wisdom_13",
            "wisdom_14", "wisdom_15", "wisdom_16", "wisdom_17", "wisdom_18", "wisdom_19", "wisdom_20",
            "wisdom_21", "wisdom_22", "wisdom_23", "wisdom_24", "wisdom_25"
    };
}