package jhe.com.sunshine.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jhe.com.sunshine.R;
import jhe.com.sunshine.soap.requests.objects.MenueDay;
import jhe.com.sunshine.soap.requests.objects.MenueNode;

/**
 * Created by jens on 15.02.16.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuNodeViewHolder> {

    List<MenueNode> menueNodes;

    public MenuAdapter(List<MenueNode> menueNodes) {
        this.menueNodes = menueNodes;
    }

    @Override
    public MenuNodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu, parent, false);
        MenuNodeViewHolder menuNodeViewHolder = new MenuNodeViewHolder(v);
        return menuNodeViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuNodeViewHolder holder, int position) {
        MenueNode menueNode = menueNodes.get(position);
        if (menueNode.getBestellteMenge() > 0) {
            holder.menu.setCardBackgroundColor(Color.rgb(109, 192, 102)); // greenich
        }
        holder.menuTitle.setText(menueNode.getMenuBezeichnung());
    }

    @Override
    public int getItemCount() {
        return menueNodes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class MenuNodeViewHolder extends RecyclerView.ViewHolder {

        CardView menu;
        TextView menuTitle;


        public MenuNodeViewHolder(View itemView) {
            super(itemView);
            menu = (CardView) itemView.findViewById(R.id.menu);
            menuTitle = (TextView) itemView.findViewById(R.id.menu_title);
        }
    }
}
