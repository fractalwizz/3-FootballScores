package barqsoft.footballscores.data;

public enum League {
    PREMIER_LEAGUE(445),
    CHAMPIONSHIP(446),
    LEAGUE_1(447),
    LEAGUE_2(448),
    EREDIVISIE(449),
    LIGUE1(450),
    LIGUE2(451),
    BUNDESLIGA1(452),
    BUNDESLIGA2(453),
    BUNDESLIGA3(454),
    PRIMERA_DIVISION(455),
    SERIE_A(456),
    PRIMERA_LIGA(457),
    DFB_POKAL(458),
    SERIE_B(459),
    CHAMPIONS_LEAGUE(464),
    DEFAULT(999);

    private int code;

    League(int code) { this.code = code; }
    public int code() { return this.code; }
    public String str() { return String.valueOf(this.code); }
    public static League get(int code) {
        for (League i : values()) {
            if (i.code == code) { return i; }
        }
        return DEFAULT;
    }
}