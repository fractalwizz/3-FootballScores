package barqsoft.footballscores;

import android.content.Context;

public class Utility {
    public static final String LOG_TAG = Utility.class.getSimpleName();

    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static String getLeague(int league_num, Context context) {
        switch (league_num) {
            case SERIE_A : return context.getString(R.string.seria_a);
            case PREMIER_LEGAUE : return context.getString(R.string.premierleague);
            case CHAMPIONS_LEAGUE : return context.getString(R.string.champions_league);
            case PRIMERA_DIVISION : return context.getString(R.string.primeradivison);
            case BUNDESLIGA : return context.getString(R.string.bundesliga);
            default: return context.getString(R.string.get_league_unknown);
        }
    }

    public static String getMatchDay(int match_day, int league_num, Context context) {
        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return context.getString(R.string.group_stage_match);
            } else if(match_day == 7 || match_day == 8) {
                return context.getString(R.string.first_knockout_round);
            } else if(match_day == 9 || match_day == 10) {
                return context.getString(R.string.quarter_final);
            } else if(match_day == 11 || match_day == 12) {
                return context.getString(R.string.semi_final);
            } else {
                return context.getString(R.string.final_text);
            }
        } else {
            return context.getString(R.string.format_matchday, String.valueOf(match_day));
        }
    }

    public static String getScores(int homegoals,int awaygoals) {
        if (homegoals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(homegoals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname) {
        if (teamname == null) { return R.drawable.no_icon; }

        switch (teamname) {
            //TODO add more crests
            case "1. FC Heidenheim 1846" : return R.drawable.fc_heidenheim;
            case "1. FC Kaiserslautern" : return R.drawable.fc_kaiserslautern;
            case "1. FC Nürnberg" : return R.drawable.fc_nurnberg;
            case "1. FC Union Berlin" : return R.drawable.fc_union_berlin;
            case "1. FSV Mainz 05" : return R.drawable.fsv_mainz;
            case "ACF Fiorentina" : return R.drawable.acf_fiorentina;
            case "Arminia Bielefeld" : return R.drawable.arminia_bielefeld;
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Aston Villa FC" : return R.drawable.aston_villa;
            case "Athletic Club" : return R.drawable.athletic_club;
            case "Bayer Leverkusen" : return R.drawable.bayer_leverkusen;
            case "Bologna FC" : return R.drawable.bologna_fc;
            case "Bor. Mönchengladbach" : return R.drawable.bor_monchengladbach;
            case "Borussia Dortmund" : return R.drawable.borussia_dordmund;
            case "Club Atlético de Madrid" : return R.drawable.club_atletico;
            case "Crystal Palace FC" : return R.drawable.crystal_palace_fc;
            case "Eintracht Frankfurt" : return R.drawable.eintracht_frankfurt;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "FC Augsburg" : return R.drawable.fc_augsburg;
            case "FC Bayern München" : return R.drawable.bayern_munchen;
            case "FC Ingolstadt 04" : return R.drawable.fc_ingolstadt;
            case "FC Schalke 04" : return R.drawable.fc_schalke;
            case "Fortuna Düsseldorf" : return R.drawable.fortuna_dusseldorf;
            case "Genoa CFC" : return R.drawable.genoa_cfc;
            case "Getafe CF" : return R.drawable.getafe_cf;
            case "Hannover 96" : return R.drawable.hannover_96;
            case "Hertha BSC" : return R.drawable.hertha_bsc;
            case "Leicester City FC" : return R.drawable.leicester_city_fc_hd_logo;
            case "Liverpool FC" : return R.drawable.liverpool_fc;
            case "Málaga CF" : return R.drawable.malaga_cf;
            case "Manchester City" : return R.drawable.manchester_city;
            case "Manchester City FC" : return R.drawable.manchester_city;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "MSV Duisburg" : return R.drawable.msv_duisburg;
            case "Newcastle United FC" : return R.drawable.newcastle_united;
            case "Norwich City FC" : return R.drawable.norwich_city;
            case "Rayo Vallecano de Madrid" : return R.drawable.rayo_vallecano;
            case "RC Deportivo La Coruna" : return R.drawable.rc_deportivo;
            case "RCD Espanyol" : return R.drawable.rcd_espanyol;
            case "Real Sociedad de Fútbol" : return R.drawable.real_sociedad;
            case "SC Freiburg" : return R.drawable.sc_freiburg;
            case "SC Paderborn 07" : return R.drawable.sc_paderborn07;
            case "SD Eibar" : return R.drawable.sd_eibar;
            case "Southampton FC" : return R.drawable.southampton_fc;
            case "Sporting Gijón" : return R.drawable.sporting_gijon;
            case "SS Lazio" : return R.drawable.ss_lazio;
            case "Stoke City FC" : return R.drawable.stoke_city;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "SV Sandhausen" : return R.drawable.sv_sandhausen;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Swansea City FC" : return R.drawable.swansea_city_afc;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "TSV 1860 München" : return R.drawable.tsv_munchen;
            case "UD Las Palmas" : return R.drawable.ud_las_palmas;
            case "VfB Stuttgart" : return R.drawable.vfb_stuttgart;
            case "VfL Bochum" : return R.drawable.vfl_bochum;
            case "VfL Wolfsburg" : return R.drawable.vfl_wolfsburg;
            case "Villarreal CF" : return R.drawable.villarreal_cf;
            case "Watford FC" : return R.drawable.watford_fc;
            case "Werder Bremen" : return R.drawable.werder_bremen;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "West Bromwich Albion FC" : return R.drawable.west_bromwich_albion_hd_logo;
            case "West Ham United FC" : return R.drawable.west_ham;
            default: return R.drawable.no_icon;
        }
    }
}