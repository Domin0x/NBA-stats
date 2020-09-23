package com.domin0x.NBARadars.stats;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "per_game_stats")
public class PerGameStats implements StatLine {

    @EmbeddedId
    private PerGameStatsId id;
    @Column(name = "games_played")
    private int gamesPlayed;
    @Column(name = "mp")
    private BigDecimal mp;
    private BigDecimal fgm;
    private BigDecimal fga;
    private BigDecimal fg3m;
    private BigDecimal fg3a;
    private BigDecimal ftm;
    private BigDecimal fta;
    private BigDecimal oreb;
    private BigDecimal dreb;
    private BigDecimal reb;
    private BigDecimal ast;
    private BigDecimal blk;
    private BigDecimal stl;
    private BigDecimal tov;
    private BigDecimal pf;
    private BigDecimal pts;
    private BigDecimal fg_pct;
    private BigDecimal fg3_pct;
    private BigDecimal ft_pct;

    public PerGameStats() {
    }

    public PerGameStatsId getId() {
        return id;
    }

    public void setId(PerGameStatsId id) {
        this.id = id;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public BigDecimal getMp() {
        return mp;
    }

    public void setMp(BigDecimal mp) {
        this.mp = mp;
    }

    public BigDecimal getFgm() {
        return fgm;
    }

    public void setFgm(BigDecimal fgm) {
        this.fgm = fgm;
    }

    public BigDecimal getFga() {
        return fga;
    }

    public void setFga(BigDecimal fga) {
        this.fga = fga;
    }

    public BigDecimal getFg3m() {
        return fg3m;
    }

    public void setFg3m(BigDecimal fg3m) {
        this.fg3m = fg3m;
    }

    public BigDecimal getFg3a() {
        return fg3a;
    }

    public void setFg3a(BigDecimal fg3a) {
        this.fg3a = fg3a;
    }

    public BigDecimal getFtm() {
        return ftm;
    }

    public void setFtm(BigDecimal ftm) {
        this.ftm = ftm;
    }

    public BigDecimal getFta() {
        return fta;
    }

    public void setFta(BigDecimal fta) {
        this.fta = fta;
    }

    public BigDecimal getOreb() {
        return oreb;
    }

    public void setOreb(BigDecimal oreb) {
        this.oreb = oreb;
    }

    public BigDecimal getDreb() {
        return dreb;
    }

    public void setDreb(BigDecimal dreb) {
        this.dreb = dreb;
    }

    public BigDecimal getReb() {
        return reb;
    }

    public void setReb(BigDecimal reb) {
        this.reb = reb;
    }

    public BigDecimal getAst() {
        return ast;
    }

    public void setAst(BigDecimal ast) {
        this.ast = ast;
    }

    public BigDecimal getBlk() {
        return blk;
    }

    public void setBlk(BigDecimal blk) {
        this.blk = blk;
    }

    public BigDecimal getStl() {
        return stl;
    }

    public void setStl(BigDecimal stl) {
        this.stl = stl;
    }

    public BigDecimal getTurnovers() {
        return tov;
    }

    public void setTurnovers(BigDecimal tov) {
        this.tov = tov;
    }

    public BigDecimal getPf() {
        return pf;
    }

    public void setPf(BigDecimal pf) {
        this.pf = pf;
    }

    public BigDecimal getPts() {
        return pts;
    }

    public void setPts(BigDecimal pts) {
        this.pts = pts;
    }

    public BigDecimal getFg_pct() {
        return fg_pct;
    }

    public void setFg_pct(BigDecimal fg_pct) {
        this.fg_pct = fg_pct;
    }

    public BigDecimal getFg3_pct() {
        return fg3_pct;
    }

    public void setFg3_pct(BigDecimal fg3_pct) {
        this.fg3_pct = fg3_pct;
    }

    public BigDecimal getFt_pct() {
        return ft_pct;
    }

    public void setFt_pct(BigDecimal ft_pct) {
        this.ft_pct = ft_pct;
    }

    @Override
    public String toString() {
        return "PerGameStats{" +
                "id=" + id +
                ", gamesPlayed=" + gamesPlayed +
                ", mp=" + mp +
                ", fgm=" + fgm +
                ", fga=" + fga +
                ", fg3m=" + fg3m +
                ", fg3a=" + fg3a +
                ", ftm=" + ftm +
                ", fta=" + fta +
                ", oreb=" + oreb +
                ", dreb=" + dreb +
                ", reb=" + reb +
                ", ast=" + ast +
                ", blk=" + blk +
                ", stl=" + stl +
                ", tov=" + tov +
                ", pf=" + pf +
                ", pts=" + pts +
                ", fg_pct=" + fg_pct +
                ", fg3_pct=" + fg3_pct +
                ", ft_pct=" + ft_pct +
                '}';
    }
}
