package jhe.com.sunshine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import jhe.com.sunshine.soap.requests.objects.MenueNode;

/**
 * Created by jens on 15.02.16.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuNodeViewHolder> {

    @Override
    public MenuNodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MenuNodeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MenuNodeViewHolder extends RecyclerView.ViewHolder {

        public MenuNodeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
