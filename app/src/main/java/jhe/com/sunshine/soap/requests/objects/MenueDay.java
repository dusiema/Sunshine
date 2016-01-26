package jhe.com.sunshine.soap.requests.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jens on 26.01.16.
 */
public class MenueDay {

    String dayString;
    Boolean bestellbar;
    List<MenueNode> menueNodes = new ArrayList<>();

    public Boolean getBestellbar() {
        return bestellbar;
    }

    public void setBestellbar(Boolean bestellbar) {
        this.bestellbar = bestellbar;
    }

    public String getDayString() {
        return dayString;
    }

    public void setDayString(String dayString) {
        this.dayString = dayString;
    }

    public List<MenueNode> getMenueNodes() {
        return menueNodes;
    }

    public void setMenueNodes(List<MenueNode> menueNodes) {
        this.menueNodes = menueNodes;
    }
}
