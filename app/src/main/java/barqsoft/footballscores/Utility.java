package barqsoft.footballscores;

import android.content.Context;

import barqsoft.footballscores.data.League;

public class Utility {
    public static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getLeague(int league_num, Context context) {
        League i = League.get(league_num);

        switch (i) {
            case PREMIER_LEAGUE: return context.getString(R.string.premier_league);
            case CHAMPIONSHIP: return context.getString(R.string.championship);
            case LEAGUE_1: return context.getString(R.string.league_1);
            case LEAGUE_2: return context.getString(R.string.league_2);
            case EREDIVISIE: return context.getString(R.string.eredivisie);
            case LIGUE1: return context.getString(R.string.ligue1);
            case LIGUE2: return context.getString(R.string.ligue2);
            case BUNDESLIGA1: return context.getString(R.string.bundesliga1);
            case BUNDESLIGA2: return context.getString(R.string.bundesliga2);
            case BUNDESLIGA3: return context.getString(R.string.bundesliga3);
            case PRIMERA_DIVISION: return context.getString(R.string.primera_division);
            case SERIE_A: return context.getString(R.string.serie_a);
            case PRIMERA_LIGA: return context.getString(R.string.primera_liga);
            case DFB_POKAL: return context.getString(R.string.dfb_pokal);
            case SERIE_B: return context.getString(R.string.serie_b);
            case CHAMPIONS_LEAGUE: return context.getString(R.string.champion_league);
            default: return context.getString(R.string.get_league_unknown);
        }
    }

    public static String getMatchDay(int match_day, int league_num, Context context) {
        if (league_num == League.CHAMPIONS_LEAGUE.code()) {
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
            case "1. FC Köln" : return R.drawable.fc_koln;
            case "1. FC Nürnberg" : return R.drawable.fc_nurnberg;
            case "1. FC Union Berlin" : return R.drawable.fc_union_berlin;
            case "1. FSV Mainz 05" : return R.drawable.fsv_mainz;
            case "AC Chievo Verona" : return R.drawable.ac_chievo;
            case "AC Milan" : return R.drawable.ac_milan;
            case "ACF Fiorentina" : return R.drawable.acf_fiorentina;
            case "AFC Bournemouth" : return R.drawable.afc_bournermouth;
            case "Arminia Bielefeld" : return R.drawable.arminia_bielefeld;
            case "Arsenal FC" : return R.drawable.arsenal;
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "AS Roma" : return R.drawable.as_roma;
            case "Aston Villa FC" : return R.drawable.aston_villa;
            case "Atalanta BC" : return R.drawable.atalanta_bc;
            case "Athletic Club" : return R.drawable.athletic_club;
            case "Bayer Leverkusen" : return R.drawable.bayer_leverkusen;
            case "Bologna FC" : return R.drawable.bologna_fc;
            case "Bor. Mönchengladbach" : return R.drawable.bor_monchengladbach;
            case "Borussia Dortmund" : return R.drawable.borussia_dordmund;
            case "Carpi FC" : return R.drawable.carpi_fc;
            case "Chelsea" : return R.drawable.chelsea_fc;
            case "Chelsea FC" : return R.drawable.chelsea_fc;
            case "Club Atlético de Madrid" : return R.drawable.club_atletico;
            case "Crystal Palace FC" : return R.drawable.crystal_palace_fc;
            case "Eintracht Braunschweig" : return R.drawable.eintracht_braunschweig;
            case "Eintracht Frankfurt" : return R.drawable.eintracht_frankfurt;
            case "Empoli FC" : return R.drawable.empoli_fc;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "FC Augsburg" : return R.drawable.fc_augsburg;
            case "FC Barcelona" : return R.drawable.fc_barcelona;
            case "FC Bayern München" : return R.drawable.bayern_munchen;
            case "FC Ingolstadt 04" : return R.drawable.fc_ingolstadt;
            case "FC Internazionale Milano" : return R.drawable.internazionale_milano;
            case "FC Schalke 04" : return R.drawable.fc_schalke;
            case "FC St. Pauli" : return R.drawable.fc_st_pauli;
            case "Fortuna Düsseldorf" : return R.drawable.fortuna_dusseldorf;
            case "Frosinone Calcio" : return R.drawable.frosinone_calcio;
            case "FSV Frankfurt" : return R.drawable.fsv_frankfurt;
            case "FSV Mainz" : return R.drawable.fsv_mainz;
            case "Genoa CFC" : return R.drawable.genoa_cfc;
            case "Getafe CF" : return R.drawable.getafe_cf;
            case "Granada CF" : return R.drawable.granada_cf;
            case "Hamburger SV" : return R.drawable.hamburger_sv;
            case "Hannover 96" : return R.drawable.hannover_96;
            case "Hellas Verona FC" : return R.drawable.hellas_verona;
            case "Hertha BSC" : return R.drawable.hertha_bsc;
            case "Juventus Turin" : return R.drawable.juventus_turin;
            case "Karlsruher SC" : return R.drawable.karlsruher_sc;
            case "Leicester City FC" : return R.drawable.leicester_city_fc_hd_logo;
            case "Levante UD" : return R.drawable.levante_ud;
            case "Liverpool FC" : return R.drawable.liverpool_fc;
            case "Málaga CF" : return R.drawable.malaga_cf;
            case "Manchester City" : return R.drawable.manchester_city;
            case "Manchester City FC" : return R.drawable.manchester_city;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "MSV Duisburg" : return R.drawable.msv_duisburg;
            case "Newcastle United FC" : return R.drawable.newcastle_united;
            case "Norwich City FC" : return R.drawable.norwich_city;
            case "Rayo Vallecano de Madrid" : return R.drawable.rayo_vallecano;
            case "RC Celta de Vigo" : return R.drawable.rc_celta;
            case "RC Deportivo La Coruna" : return R.drawable.rc_deportivo;
            case "RCD Espanyol" : return R.drawable.rcd_espanyol;
            case "Real Betis" : return R.drawable.real_betis;
            case "Real Madrid CF" : return R.drawable.real_madrid;
            case "Real Sociedad de Fútbol" : return R.drawable.real_sociedad;
            case "Red Bull Leipzig" : return R.drawable.red_bull_leipzig;
            case "SC Freiburg" : return R.drawable.sc_freiburg;
            case "SC Paderborn 07" : return R.drawable.sc_paderborn07;
            case "SD Eibar" : return R.drawable.sd_eibar;
            case "Sevilla FC" : return R.drawable.sevilla_fc;
            case "Southampton FC" : return R.drawable.southampton_fc;
            case "Sporting Gijón" : return R.drawable.sporting_gijon;
            case "SpVgg Greuther Fürth" : return R.drawable.spvgg_greuther;
            case "SS Lazio" : return R.drawable.ss_lazio;
            case "SSC Napoli" : return R.drawable.ssc_napoli;
            case "Stoke City FC" : return R.drawable.stoke_city;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "SV Darmstadt 98" : return R.drawable.sv_darmstadt;
            case "SV Sandhausen" : return R.drawable.sv_sandhausen;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Swansea City FC" : return R.drawable.swansea_city_afc;
            case "Torino FC" : return R.drawable.torino_fc;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "TSG 1899 Hoffenheim" : return R.drawable.tsg_hoffenheim;
            case "TSV 1860 München" : return R.drawable.tsv_munchen;
            case "UC Sampdoria" : return R.drawable.uc_sampdoria;
            case "UD Las Palmas" : return R.drawable.ud_las_palmas;
            case "Udinese Calcio" : return R.drawable.udinese_calcio;
            case "US Cittá di Palermo" : return R.drawable.us_citta;
            case "US Sassuolo Calcio" : return R.drawable.us_sassuolo;
            case "Valencia CF" : return R.drawable.valencia_cf;
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