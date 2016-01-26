package jhe.com.sunshine.soap.requests.objects;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jens on 26.01.16.
 */
public class MenuWeek {

    List<MenueDay> menuDays = new ArrayList<>();

    public List<MenueDay> getMenuDays() {
        return menuDays;
    }

    public void setMenuDays(List<MenueDay> menuDays) {
        this.menuDays = menuDays;
    }
}
