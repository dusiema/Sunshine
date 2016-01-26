package jhe.com.sunshine.soap.requests.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jens on 26.01.16.
 */
public class MenueNode {
    String menuBezeichnung;
    Integer bestellteMenge;

    public Integer getBestellteMenge() {
        return bestellteMenge;
    }

    public void setBestellteMenge(Integer bestellteMenge) {
        this.bestellteMenge = bestellteMenge;
    }

    public String getMenuBezeichnung() {
        return menuBezeichnung;
    }

    public void setMenuBezeichnung(String menuBezeichnung) {
        this.menuBezeichnung = menuBezeichnung;
    }
}
